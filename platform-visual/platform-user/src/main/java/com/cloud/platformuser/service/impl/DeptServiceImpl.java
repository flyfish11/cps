package com.cloud.platformuser.service.impl;

import com.cloud.common.constants.CommonConstants;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.R;
import com.cloud.common.utils.TreeUtil;
import com.cloud.common.vo.DeptTree;
import com.cloud.model.platformuser.LoginAppUser;
import com.cloud.model.platformuser.SysDept;
import com.cloud.model.platformuser.SysUser;
import com.cloud.model.platformuser.bo.SysDeptAddBO;
import com.cloud.model.platformuser.bo.SysDeptUpdateBO;
import com.cloud.platformuser.dao.SysDeptDao;
import com.cloud.platformuser.dao.SysUserDao;
import com.cloud.platformuser.feign.AppManagerCenterFeignClient;
import com.cloud.platformuser.service.DeptService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

/**
 * 部门服务
 *
 * @author yulj
 * @create: 2019/05/06 14:42
 */

@Service
@Slf4j
public class DeptServiceImpl implements DeptService {

    @Resource
    private SysDeptDao deptDao;

    @Resource
    private SysUserDao userDao;

    @Autowired
    private AppManagerCenterFeignClient appManagerFeignClient;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<SysDept> treeTable(Map<String, Object> param) {
        List<SysDept> allDept = deptDao.findAllDept(param);
        List<SysDept> collect = allDept.stream().filter(a -> a.getPid() == 0).collect(Collectors.toList());

        for (SysDept dept : collect) {
            TreeUtil.setChild(dept, allDept);
        }
        return collect;
    }

    @Override
    public SysDept load(Integer id) {
        return this.deptDao.selectById(id);
    }

    @Override
    @Cacheable(value = "deptData", key = "targetClass + methodName +#p0", unless = "#result == null")
    public String getDeptNames(Integer... deptIds) {
        StringBuilder deptNames = new StringBuilder();
        List<SysDept> deptList = deptDao.findAllDeptInRange(asList(deptIds));
        deptList.forEach(sysDept -> {
            deptNames.append(sysDept.getFullname() + ",");
        });
        if (deptNames.length() > 0) {
            deptNames.deleteCharAt(deptNames.length() - 1);
        }
        return deptNames.toString();
    }

    /**
     * 查询全部部门树
     *
     * @return 树
     */
    @Override
    @Cacheable(value = "deptData", key = "targetClass + methodName +#p0", unless = "#result == null")
    public List<DeptTree> elementTree(String deptName) {
        Map<String, Object> param = new HashMap<>();
        param.put("deptName", deptName);
        return getDeptTree(this.deptDao.findAllDept(param), null, true);
    }

    /**
     * 构建部门树
     *
     * @param depts 部门
     * @return
     */
    private List<DeptTree> getDeptTree(List<SysDept> depts, List<SysUser> appUsers, Boolean rangeFlag) {
        List<DeptTree> allDeptTreeList = Lists.newArrayList();
        List<DeptTree> deptTreeList = Lists.newArrayList();
        Set<Integer> range = Sets.newHashSet();
        depts.forEach(sysDept -> {
            DeptTree node = new DeptTree();
            node.setId(sysDept.getId());
            node.setParentId(sysDept.getPid());
            node.setLabel(sysDept.getSdeptname());
            deptTreeList.add(node);
            range.add(sysDept.getPid());
        });
        allDeptTreeList.addAll(deptTreeList);

        if (appUsers != null) {
            List<DeptTree> deptUserTreeList = appUsers.stream()
                    .filter(user -> user.getDeptid() != null)
                    .map(user -> {
                        DeptTree node = new DeptTree();
                        node.setId(user.getId() + CommonConstants.USER_ID_ACCUMULATE);
                        node.setParentId(user.getDeptid());
                        node.setLabel(user.getName());
                        return node;
                    }).collect(Collectors.toList());
            allDeptTreeList.addAll(deptUserTreeList);
        }

        if (Boolean.FALSE.equals(rangeFlag)) {
            range.clear();
            range.add(CommonConstants.ROOT_DEPT_ID);
        } else {
            Set<Integer> tempRemove = Sets.newHashSet();
            for (DeptTree deptTree : deptTreeList) {
                if (range.contains(deptTree.getParentId()) && range.contains(deptTree.getId())) {
                    tempRemove.add(deptTree.getId());
                }
            }
            range.removeAll(tempRemove);
        }
        return TreeUtil.build(allDeptTreeList, range);
    }

    /**
     * 查询部门人员树
     *
     * @return
     */
    @Override
    public List<DeptTree> selectUserTree() {
        Map<String, Object> param = new HashMap<>();
        List<SysDept> deptList = this.deptDao.findAllDept(param);
        List<SysUser> userList = this.userDao.findAllByStatus(CommonConstants.USER_ACTIVE_STATUS);
        return getDeptTree(deptList, userList, false);
    }

    @Override
    public R selectUserTreeInRange(String applicationId) {
        List<SysDept> deptList = Lists.newArrayList();
        List<SysUser> userList = Lists.newArrayList();
        List<Integer> deptIds = null;
        R r = this.appManagerFeignClient.load(applicationId);
        List<Integer> checkedIds = new ArrayList<>();
        if (r.getData() != null && StringUtils.isNotBlank(r.getData().toString())) {
            Map<String, Object> applicationMap = (Map<String, Object>) r.getData();
            String authorityArea = applicationMap.get("authorityArea").toString();
            String allowDeptUser = applicationMap.get("allowDeptUser").toString();
            Integer appType = Integer.valueOf(applicationMap.get("appType").toString());

            if (StringUtils.isNotBlank(authorityArea)) {
                List<String> strings = asList(authorityArea.split(","));
                deptIds = strings.stream().map(Integer::valueOf).collect(Collectors.toList());
                if (!deptIds.isEmpty()) {
                    deptList = this.deptDao.findAllDeptInRange(deptIds);
                    userList = this.userDao.findAllUserInDeptRange(deptIds);
                }
            }
            if (StringUtils.isNotBlank(allowDeptUser)) {
                checkedIds = stream(allowDeptUser.split(","))
                        .map(s -> Integer.parseInt(s.trim()))
                        .collect(Collectors.toList());
            }
        }
        List<DeptTree> deptTree = getDeptTree(deptList, userList, true);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("deptTree", deptTree);
        resultMap.put("checkedIds", checkedIds);
        return R.ok(resultMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "deptData", allEntries = true, beforeInvocation = true)
    public R insert(SysDeptAddBO sysDeptAddBO) {
        List<SysDept> sysDeptList = deptDao.selectAllByFullname(sysDeptAddBO.getFullname());
        if (!sysDeptList.isEmpty()) {
            return R.failed("部门全称已存在！");
        }
        SysDept sysDept = sysDeptAddBOToSysDept(sysDeptAddBO);
        deptDao.insertSelective(sysDept);
        // 缓存部门数据
        if (redisTemplate.hasKey(CommonConstants.CACHE_DEPT_KEY)) {
            redisTemplate.opsForHash().put(CommonConstants.CACHE_DEPT_KEY, sysDept.getId().toString(), sysDept.getFullname());
        } else {
            cacheAllDeptData();
        }

        return R.ok(ResultEnum.ADD_OPERATE_SUCCESS.getMessage());
    }

    /**
     * 部门新增业务对象转部门实体对象
     *
     * @param sysDeptAddBO 部门新增业务对象
     * @return
     */
    private SysDept sysDeptAddBOToSysDept(SysDeptAddBO sysDeptAddBO) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(sysDeptAddBO, sysDept);
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            sysDept.setCreateBy(user.getUsername());
            sysDept.setUpdateBy(user.getUsername());
        }
        sysDept.setCreateTime(new Date());
        sysDept.setUpdateTime(sysDept.getCreateTime());
        return sysDept;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "deptData", allEntries = true, beforeInvocation = true)
    public R update(SysDeptUpdateBO sysDeptUpdateBO) {
        if (StringUtils.isNotBlank(sysDeptUpdateBO.getFullname())) {
            SysDept oldSysDept = deptDao.selectOneByFullnameAndIdNot(sysDeptUpdateBO.getFullname(), sysDeptUpdateBO.getId());
            if (oldSysDept != null) {
                return R.failed("部门全称已存在！");
            }
        }
        SysDept sysDept = sysDeptUpdateBOToSysDept(sysDeptUpdateBO);
        deptDao.updateByIdSelective(sysDept);
        // 更新缓存部门数据
        if (redisTemplate.hasKey(CommonConstants.CACHE_DEPT_KEY)) {
            redisTemplate.opsForHash().put(CommonConstants.CACHE_DEPT_KEY, sysDept.getId().toString(), sysDept.getFullname());
        } else {
            cacheAllDeptData();
        }

        return R.ok(ResultEnum.UPDATE_OPERATE_SUCCESS.getMessage());
    }

    /**
     * 部门修改业务对象转部门实体对象
     *
     * @param sysDeptUpdateBO 部门修改业务对象
     * @return
     */
    private SysDept sysDeptUpdateBOToSysDept(SysDeptUpdateBO sysDeptUpdateBO) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(sysDeptUpdateBO, sysDept);
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            sysDept.setUpdateBy(user.getUsername());
        }
        sysDept.setUpdateTime(new Date());
        return sysDept;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "deptData", allEntries = true, beforeInvocation = true)
    public R deleteById(Integer id) {
        deptDao.deleteById(id);
        // 移除缓存用户数据
        if (redisTemplate.hasKey(CommonConstants.CACHE_DEPT_KEY)) {
            redisTemplate.opsForHash().delete(CommonConstants.CACHE_DEPT_KEY, id.toString());
        }

        return R.ok(ResultEnum.DELETE_OPERATE_SUCCESS.getMessage());
    }

    @Override
    public int cacheAllDeptData() {
        Map<String, Object> queryMap = Maps.newHashMap();
        List<SysDept> sysDeptList = this.deptDao.findAllDept(queryMap);
        if (sysDeptList.size() > 0) {
            if (redisTemplate.hasKey(CommonConstants.CACHE_DEPT_KEY)) {
                redisTemplate.delete(CommonConstants.CACHE_DEPT_KEY);
            }
            Map<String, String> deptDataMap = Maps.newHashMap();
            sysDeptList.forEach(sysUser -> {
                deptDataMap.put(sysUser.getId().toString(), sysUser.getFullname());
            });
            redisTemplate.opsForHash().putAll(CommonConstants.CACHE_DEPT_KEY, deptDataMap);
        }

        return sysDeptList.size();
    }

}
