package com.cloud.platformuser.service.impl;

import com.cloud.common.constants.CommonConstants;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.enums.UserStatusEnum;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.PageUtil;
import com.cloud.common.utils.R;
import com.cloud.model.common.Page;
import com.cloud.model.platformuser.*;
import com.cloud.model.platformuser.bo.*;
import com.cloud.model.user.LoginAppUser;
import com.cloud.platformuser.dao.*;
import com.cloud.platformuser.dto.AppUserDto;
import com.cloud.platformuser.exception.UserCenterException;
import com.cloud.platformuser.feign.AppManagerCenterFeignClient;
import com.cloud.platformuser.service.LdapUserService;
import com.cloud.platformuser.service.SysUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private SysMenuDao sysMenuDao;

    @Resource
    private SysDictItemDao sysDictItemDao;

    @Autowired
    private SysDeptDao deptDao;

    @Autowired
    private LdapUserService ldapUserService;

    @Autowired
    private AppManagerCenterFeignClient appManagerCenterFeignClient;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackForClassName = {"UserCenterException", "Exception"})
    @CacheEvict(value = "userData", allEntries = true, beforeInvocation = true)
    public R addSysUser(SysUserAddBO sysUserAddBO) throws UserCenterException {
        // 账号及手机号校验
        SysUser userByAccount = this.sysUserDao.selectOneByAccount(sysUserAddBO.getAccount());
        if (userByAccount != null) {
            return R.failed("账号已存在！");
        }
        SysUser userByPhone = this.sysUserDao.selectOneByPhone(sysUserAddBO.getPhone());
        if (userByPhone != null) {
            return R.failed("电话已被使用！");
        }
        SysUser userByEmail = this.sysUserDao.selectOneByEmail(sysUserAddBO.getEmail());
        if (userByEmail != null) {
            return R.failed("邮箱已被使用！");
        }

        SysUser sysUser = sysUserAddBOToSysUser(sysUserAddBO);
        // 保存用户信息
        this.sysUserDao.insertSelective(sysUser);
        log.info("添加用户：{}", sysUserAddBO);
        // 保存用户角色关联信息
        this.userRoleDao.saveUserRoles(sysUser.getId(), sysUser.getRoleid());
        // 缓存用户数据
        if (redisTemplate.hasKey(CommonConstants.CACHE_USER_KEY)) {
            redisTemplate.opsForHash().put(CommonConstants.CACHE_USER_KEY, sysUser.getId().toString(), sysUser.getName());
        } else {
            cacheAllUserData();
        }

        // 注册用户信息到 LDAP 服务器
        LdapUserBO ldapUserBO =
                LdapUserBO.builder().account(sysUserAddBO.getAccount())
                        .password(sysUserAddBO.getPassword())
                        .build();
        R registerResult = this.ldapUserService.register(ldapUserBO);
        if (registerResult.getCode() == CommonConstants.SUCCESS) {
            Map<String, Integer> resultMap = Maps.newHashMap();
            resultMap.put("userId", sysUser.getId());
            return R.ok(resultMap, ResultEnum.ADD_OPERATE_SUCCESS.getMessage());
        }

        log.error(ResultEnum.REGISTER_LDAP_USER_ERROR.getMessage());
        throw new UserCenterException(ResultEnum.LOGOUT_LDAP_USER_ERROR.getMessage());
    }

    private SysUser sysUserAddBOToSysUser(SysUserAddBO sysUserAddBO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserAddBO, sysUser);
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        // 设置默认角色及用户状态
        if (sysUser.getStatus() == null) {
            sysUser.setStatus(UserStatusEnum.OK.getCode());
        }
        if (StringUtils.isBlank(sysUser.getRoleid())) {
            sysUser.setRoleid(CommonConstants.DEFAULT_ROLE_ID);
        }
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            sysUser.setCreateBy(user.getUsername());
            sysUser.setUpdateBy(user.getUsername());
        }
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateTime(sysUser.getCreateTime());
        sysUser.setPasswordUpdateTime(sysUser.getCreateTime());
        return sysUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "userData", allEntries = true, beforeInvocation = true)
    public R updateSysUser(SysUserUpdateBO sysUserUpdateBO) {
        // 若用户更新信息中包含手机号和邮箱，则进行校验
        if (StringUtils.isNotBlank(sysUserUpdateBO.getPhone())) {
            SysUser userByPhone = this.sysUserDao.selectOneByPhoneAndIdNot(sysUserUpdateBO.getPhone(), sysUserUpdateBO.getId());
            if (userByPhone != null) {
                return R.failed("电话已被使用！");
            }
        }
        if (StringUtils.isNotBlank(sysUserUpdateBO.getEmail())) {
            SysUser userByEmail = this.sysUserDao.selectOneByEmailAndIdNot(sysUserUpdateBO.getEmail(), sysUserUpdateBO.getId());
            if (userByEmail != null) {
                return R.failed("邮箱已被使用！");
            }
        }
        SysUser sysUser = sysUserUpdateBOToSysUser(sysUserUpdateBO);
        sysUserDao.updateByIdSelective(sysUser);
        // 缓存用户数据
        if (redisTemplate.hasKey(CommonConstants.CACHE_USER_KEY)) {
            redisTemplate.opsForHash().put(CommonConstants.CACHE_USER_KEY, sysUser.getId().toString(), sysUser.getName());
        } else {
            cacheAllUserData();
        }

        log.info("修改用户：{}", sysUser);
        return R.ok(ResultEnum.UPDATE_OPERATE_SUCCESS.getMessage());
    }

    private SysUser sysUserUpdateBOToSysUser(SysUserUpdateBO sysUserUpdateBO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserUpdateBO, sysUser);
        if (StringUtils.isNotBlank(sysUserUpdateBO.getPassword())) {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
            sysUser.setPasswordUpdateTime(new Date());
        }
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            sysUser.setUpdateBy(user.getUsername());
        }
        sysUser.setUpdateTime(new Date());
        return sysUser;
    }

    @Override
    @Transactional(rollbackForClassName = {"UserCenterException", "Exception"})
    @CacheEvict(value = "userData", allEntries = true, beforeInvocation = true)
    public R deleteById(Integer id) {
        SysUser sysUser = this.sysUserDao.selectById(id);
        // 移除用户信息
        this.sysUserDao.deleteById(id);
        // 移除缓存用户数据
        if (redisTemplate.hasKey(CommonConstants.CACHE_USER_KEY)) {
            redisTemplate.opsForHash().delete(CommonConstants.CACHE_USER_KEY, id.toString());
        }
        // 删除用户角色关联数据
        this.userRoleDao.deleteUserRole(id, sysUser.getRoleid());
        // 删除用户应用关联数据
        R deleteResult = this.appManagerCenterFeignClient.deleteByUserId(id);
        if (deleteResult.getCode() == CommonConstants.FAIL) {
            log.error(" 删除用户应用关联数据失败！");
            throw new UserCenterException(ResultEnum.DELETE_OPERATE_EXCEPTION.getMessage());
        }
        // 删除应用用户同步关联数据
        R destroyResult = this.appManagerCenterFeignClient.removeByUserId(id);
        if (destroyResult.getCode() == CommonConstants.FAIL) {
            log.error(" 删除应用用户同步关联数据失败！");
            throw new UserCenterException(ResultEnum.DELETE_OPERATE_EXCEPTION.getMessage());
        }

        // 注销用户到 LDAP 服务器
        R logoutResult = this.ldapUserService.logout(sysUser.getAccount());
        if (logoutResult.getCode() == CommonConstants.SUCCESS) {
            return R.ok(ResultEnum.DELETE_OPERATE_SUCCESS.getMessage());
        }

        log.error(ResultEnum.LOGOUT_LDAP_USER_ERROR.getMessage());
        throw new UserCenterException(ResultEnum.LOGOUT_LDAP_USER_ERROR.getMessage());
    }

    @Override
    public LoginAppUser findByAccount(String account) {
        return getLoginAppUser(account, CommonConstants.MICRO_SERVICE_CENTER_ID);
    }

    @Override
    public R findById(Integer id) {
        SysUser sysUser = sysUserDao.selectById(id);
        return R.ok(sysUser);
    }

    /**
     * 给用户设置角色
     */
    @Transactional
    @Override
    public void setRoleToUser(Integer id, Set<String> roleIds) {
        SysUser sysUser = sysUserDao.selectById(id);
        if (sysUser == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        userRoleDao.deleteUserRole(id, null);
        if (!CollectionUtils.isEmpty(roleIds)) {
            roleIds.forEach(roleId -> userRoleDao.saveUserRoles(id, roleId));
        }
        log.info("修改用户：{}的角色，{}", sysUser.getAccount(), roleIds);
    }

    @Override
    public R updatePassword(UpdatePwdBO updatePwdBO) {
        if (!checkPwd(updatePwdBO.getNewPassword())) {
            return R.failed("密码长度需为8位或8位以上，内容需包含大小写字母和数字！");
        }
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user == null) {
            return R.failed("获取当前登录用户失败！");
        }
        SysUser sysUser = sysUserDao.selectById(user.getId());
        if (!passwordEncoder.matches(updatePwdBO.getSourcePassword(), sysUser.getPassword())) {
            return R.failed("原密码错误！");
        }
        // 修改数据库中用户密码
        SysUserUpdateBO sysUserUpdateBO = new SysUserUpdateBO();
        sysUserUpdateBO.setId(sysUser.getId());
        sysUserUpdateBO.setPassword(updatePwdBO.getNewPassword());
        updateSysUser(sysUserUpdateBO);
        log.info("用户修改密码：{}", sysUserUpdateBO);

        // 更新 LDAP 中账户密码
        LdapUserUpdateBO ldapUserUpdateBO = LdapUserUpdateBO.builder()
                .account(sysUser.getAccount())
                .sourcePassword(updatePwdBO.getSourcePassword())
                .newPassword(updatePwdBO.getNewPassword())
                .build();
        R updateResult = this.ldapUserService.update(ldapUserUpdateBO);
        if (updateResult.getCode() == CommonConstants.SUCCESS) {
            return R.ok(ResultEnum.UPDATE_OPERATE_SUCCESS.getMessage());
        }

        log.error(ResultEnum.UPDATE_LDAP_USER_ERROR.getMessage());
        throw new UserCenterException(ResultEnum.UPDATE_LDAP_USER_ERROR.getMessage());
    }

    @Override
    public R resetPassword(Integer id) {
        SysUser sysUser = sysUserDao.selectById(id);
        if (sysUser == null) {
            return R.failed(ResultEnum.CAN_NOT_FIND_RECORD.getMessage());
        }

        // 修改数据库中用户密码
        SysUserUpdateBO sysUserUpdateBO = new SysUserUpdateBO();
        sysUserUpdateBO.setId(id);
        sysUserUpdateBO.setPassword(CommonConstants.DEFAULT_USER_PASSWORD);
        updateSysUser(sysUserUpdateBO);
        log.info("用户修改密码：{}", sysUserUpdateBO);

        // 更新 LDAP 中账户密码
        LdapUserUpdateBO ldapUserUpdateBO = LdapUserUpdateBO.builder()
                .account(sysUser.getAccount())
                .newPassword(CommonConstants.DEFAULT_USER_PASSWORD)
                .build();
        R updateResult = this.ldapUserService.resetPassword(ldapUserUpdateBO);
        if (updateResult.getCode() == CommonConstants.SUCCESS) {
            return R.ok(ResultEnum.RESET_OPERATE_SUCCESS.getMessage());
        }

        log.error(ResultEnum.UPDATE_LDAP_USER_ERROR.getMessage());
        throw new UserCenterException(ResultEnum.UPDATE_LDAP_USER_ERROR.getMessage());

    }

    @Override
    public Page<AppUserDto> findUsers(Map<String, Object> params) {
        long total = sysUserDao.count(params);
        PageUtil.pageUtil(params);

        List<AppUserDto> userDtoList = Lists.newArrayList();
        if (total > 0) {
            List<SysUser> list = sysUserDao.findData(params);

            if (!list.isEmpty()) {
                List<SysDictItem> sexList = this.sysDictItemDao.queryByDictType("sys_sex");
                Map<String, String> sexMap = sexList.stream().collect(Collectors.toMap(SysDictItem::getValue, SysDictItem::getLabel));

                for (SysUser sysUser : list) {
                    AppUserDto appUserDto = new AppUserDto();
                    BeanUtils.copyProperties(sysUser, appUserDto);

                    String sexValue = sexMap.get(sysUser.getSex().toString());
                    appUserDto.setSexValue(sexValue);
                    appUserDto.setCreateTime(sysUser.getCreateTime());
                    if (sysUser.getDeptid() != null) {
                        SysDept dept = this.deptDao.selectById(sysUser.getDeptid());
                        if (null != dept) {
                            appUserDto.setDeptName(dept.getFullname());
                        }
                    }
                    // 清空密码
                    appUserDto.setPassword("");
                    userDtoList.add(appUserDto);
                }
            }
        }
        return new Page<>(total, userDtoList);
    }

    @Override
    public Set<SysRole> findRolesByUserId(Integer userId) {
        return userRoleDao.findRolesByUserId(userId);
    }

    @Override
    public List<Integer> getUserIdInRoleIds(String... roleIds) {
        List<Integer> list = Lists.newArrayList();
        if (roleIds.length > 0) {
            list = this.userRoleDao.findUserIdInRoleId(Arrays.asList(roleIds));
        }
        return list;
    }

    @Override
    public LoginAppUser getUserInfoWithApp(String applicationId) {
        String account = AppUserUtil.getLoginAppUser().getAccount();
        return getLoginAppUser(account, applicationId);
    }

    @Override
    @Cacheable(value = "userData", key = "targetClass + methodName +#p0", unless = "#result == null")
    public String findNamesInIdRange(Integer... userIds) {
        StringBuilder userNames = new StringBuilder();
        List<SysUser> sysUserList = sysUserDao.selectAllByIdIn(Arrays.asList(userIds));
        sysUserList.forEach(sysUser -> {
            userNames.append(sysUser.getName() + ",");
        });
        if (userNames.length() > 0) {
            userNames.deleteCharAt(userNames.length() - 1);
        }
        return userNames.toString();
    }

    public LoginAppUser getLoginAppUser(String account, String applicationId) {
        SysUser appUser = sysUserDao.selectOneByAccount(account);
        if (appUser != null) {
            LoginAppUser loginAppUser = new LoginAppUser();
            BeanUtils.copyProperties(appUser, loginAppUser);

            // 设置当前应用id
            loginAppUser.setApplicationId(applicationId);

            Set<SysRole> sysRoles = userRoleDao.findRolesByUserId(appUser.getId());
            // 设置角色
            loginAppUser.setSysRoles(sysRoles);
            if (!CollectionUtils.isEmpty(sysRoles)) {
                Set<String> roleIds = sysRoles.parallelStream()
                        .map(SysRole::getId)
                        .collect(Collectors.toSet());
                List<SysMenu> menus = sysMenuDao.selectAllAuthority(roleIds, applicationId);
                Set<String> permissions = menus
                        .stream()
                        .filter(menu -> StringUtils.isNotBlank(menu.getPerms()))
                        .map(SysMenu::getPerms)
                        .collect(Collectors.toSet());
                // 自然排序
                permissions.stream().sorted(Comparator.naturalOrder());
                // 设置权限集合
                loginAppUser.setPermissions(permissions);
            }
            return loginAppUser;
        }
        return null;
    }

    @Override
    public List<SysUser> getUserInfoInUserIds(Integer... userIds) {
        List<SysUser> userList = Lists.newArrayList();
        if (userIds.length > 0) {
            userList = this.sysUserDao.selectAllByIdIn(Arrays.asList(userIds));
        }
        return userList;
    }

    @Override
    public int cacheAllUserData() {
        Map<String, Object> queryMap = Maps.newHashMap();
        List<SysUser> sysUserList = this.sysUserDao.findData(queryMap);
        if (sysUserList.size() > 0) {
            if (redisTemplate.hasKey(CommonConstants.CACHE_USER_KEY)) {
                redisTemplate.delete(CommonConstants.CACHE_USER_KEY);
            }
            Map<String, String> userDataMap = Maps.newHashMap();
            sysUserList.forEach(sysUser -> {
                userDataMap.put(sysUser.getId().toString(), sysUser.getName());
            });
            redisTemplate.opsForHash().putAll(CommonConstants.CACHE_USER_KEY, userDataMap);
        }

        return sysUserList.size();
    }

    @Override
    public R updateProductRegisterCount(SysUserUpdateBO sysUserUpdateBO) {
        Integer updateCount = sysUserUpdateBO.getProductRegisterCount();
        if (updateCount == null) {
            return R.failed(ResultEnum.PARAM_ERROR);
        }
        SysUser sourceSysUser = this.sysUserDao.selectById(sysUserUpdateBO.getId());
        Integer sourceCount = sourceSysUser.getProductRegisterCount();
        updateCount = sourceCount + updateCount;

        // 最终的数量要为正整数，才更新数据
        if (!sourceCount.equals(updateCount) && updateCount > 0) {
            SysUser sysUser = new SysUser();
            sysUser.setId(sysUserUpdateBO.getId());
            sysUser.setProductRegisterCount(updateCount);
            this.sysUserDao.updateByIdSelective(sysUser);
        }
        return R.ok(ResultEnum.ADD_OPERATE_SUCCESS.getMessage());
    }

    @Override
    @Cacheable(value = "userData", key = "targetClass + methodName +#p0", unless = "#result == null")
    public SysUser queryByUserId(Integer userId) {
        return this.sysUserDao.selectById(userId);
    }

    /**
     * 校验规则：
     * 1. 密码长度要等于或大于8位；
     * 2. 密码内容要同时包含大小写字母和数字，支持使用特殊字符；
     */
    private Boolean checkPwd(String newPassword) {
        // 判断密码是否包含数字：包含返回1，不包含返回0
        int i = newPassword.matches(".*\\d+.*") ? 1 : 0;
        // 判断密码是否包含小写字母：包含返回1，不包含返回0
        int j = newPassword.matches(".*[a-z]+.*") ? 1 : 0;
        // 判断密码是否包含大写字母：包含返回1，不包含返回0
        int k = newPassword.matches(".*[A-Z]+.*") ? 1 : 0;
        if (i + j + k < 3 || newPassword.length() < 8) {
            return false;
        }
        return true;
    }

}
