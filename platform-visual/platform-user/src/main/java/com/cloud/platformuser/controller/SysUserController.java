package com.cloud.platformuser.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.cloud.common.constants.CommonConstants;
import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.BindingResultUtil;
import com.cloud.common.utils.R;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.platformuser.SysRole;
import com.cloud.model.platformuser.SysUser;
import com.cloud.model.platformuser.bo.SysUserAddBO;
import com.cloud.model.platformuser.bo.SysUserUpdateBO;
import com.cloud.model.platformuser.bo.UpdatePwdBO;
import com.cloud.model.platformuser.vo.SysUserVo;
import com.cloud.platformuser.dto.AppUserDto;
import com.cloud.platformuser.exception.UserCenterException;
import com.cloud.platformuser.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Api(tags = "用户管理")
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 提醒更新密码天数
     */
    @Value("${user.remind-update-password-days}")
    private Long remindUpdatePasswordDays;

    /**
     * 当前登录用户 LoginAppUser
     *
     * @return
     */
    @ApiOperation(value = "当前登录用户", notes = "当前登录用户信息")
    @GetMapping("/current")
    public SysUserVo getLoginAppUser() {
        SysUser loginAppUser = AppUserUtil.getSysUser();
        SysUserVo sysUserVo = new SysUserVo();
        if (null != loginAppUser) {
            loginAppUser = this.sysUserService.queryByUserId(loginAppUser.getId());
            BeanUtils.copyProperties(loginAppUser, sysUserVo);
            if (Objects.nonNull(sysUserVo.getPasswordUpdateTime())) {
                // 上次更新密码到今天距离的天数
                long between = DateUtil.between(sysUserVo.getPasswordUpdateTime(), new Date(), DateUnit.DAY, true);
                if (!Objects.equals(between, 0L)) {
                    sysUserVo.setLastUpdatedPasswordDays(between);
                    sysUserVo.setRemindUpdatePassword(between >= remindUpdatePasswordDays);
                }
            }
        }
        return sysUserVo;
    }

    @GetMapping("/userInfo")
    @ApiImplicitParam(name = "applicationId", value = "应用id", required = true, dataType = "int", paramType = "query")
    @ApiOperation(value = "获取当前App登录用户(包含权限信息)", notes = "获取当前App登录的用户(包含权限信息)")
    public LoginAppUser getUserInfoWithApp(@RequestParam String applicationId) {
        return this.sysUserService.getUserInfoWithApp(applicationId);
    }

    @ApiOperation(value = "查询当前登录用户信息", notes = "通过账号查询当前登录用户")
    @ApiImplicitParam(name = "account", value = "别名account", required = true, dataType = "String", paramType = "query")
    @GetMapping(value = "/queryByAccount", params = "account")
    public R findByAccount(@RequestParam String account) {
        LoginAppUser user = sysUserService.findByAccount(account);
        return R.ok(user);
    }

    @ApiOperation(value = "查询用户数据列表", notes = "通过查询条件分页查询用户列表")
    @ApiImplicitParam(name = "params", value = "查询条件", dataType = "Map", paramType = "query")
    @GetMapping("/act/users")
    public Page<AppUserDto> actFindUsers(@RequestParam Map<String, Object> params) {
        return sysUserService.findUsers(params);
    }

    @ApiOperation(value = "分页查询用户", notes = "分页查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "condition", value = "账号/姓名/手机号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deptId", value = "部门id", dataType = "String", paramType = "query")
    })
    @GetMapping("/list")
    public Result list(@ApiIgnore @RequestParam Map<String, Object> params) {
        Page<AppUserDto> users = sysUserService.findUsers(params);
        return ResultUtil.success(users.getTotal(), users.getData());
    }

    @ApiOperation(value = "用户详情", notes = "根据用户id查询用户详情")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "query")
    @GetMapping("/load")
    public R findUserById(@RequestParam Integer userId) {
        return sysUserService.findById(userId);
    }

    /**
     * 添加数据
     *
     * @param sysUserAddBO 插入的数据实体
     * @return
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "新增用户记录")
    @ApiOperation(value = "新增用户", notes = "新增用户", response = R.class)
    @PostMapping("/add")
    public R add(@Valid @RequestBody SysUserAddBO sysUserAddBO, @ApiIgnore BindingResult bindingResult) throws UserCenterException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return this.sysUserService.addSysUser(sysUserAddBO);
    }

    /**
     * 管理后台修改用户
     *
     * @param sysUserUpdateBO
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "修改用户记录")
    @ApiOperation(value = "修改用户", notes = "管理后台修改用户", response = R.class)
    @PostMapping("/modify")
    public R modify(@Valid @RequestBody SysUserUpdateBO sysUserUpdateBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return sysUserService.updateSysUser(sysUserUpdateBO);
    }

    /**
     * 根据id删除数据
     *
     * @param id
     * @return
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "删除用户记录")
    @ApiOperation(value = "删除用户", notes = "根据用户id删除用户", response = R.class)
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "Integer")
    @PostMapping("/remove")
    public R remove(@RequestParam Integer id) {
        return this.sysUserService.deleteById(id);
    }

    /**
     * 修改密码
     *
     * @param updatePwdBO
     * @param bindingResult
     * @return
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "修改用户密码")
    @ApiOperation(value = "修改密码", notes = "修改密码", response = R.class)
    @PostMapping("/updatePassword")
    public R updatePassword(@Valid @RequestBody UpdatePwdBO updatePwdBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return sysUserService.updatePassword(updatePwdBO);
    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "用户重置密码")
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @PostMapping(value = "/resetPassword")
    public R resetPassword(@RequestParam Integer id) {
        return this.sysUserService.resetPassword(id);
    }

    /**
     * @param map 请求参数（必须办含用户id 和 角色id数组）
     * @return
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "用户分配角色")
    @ApiOperation(value = "分配角色", notes = "管理后台给用户分配角色")
    @PostMapping("/assignRoles")
    public Result setRoleToUser(@RequestBody Map<String, Object> map) {
        if (map.containsKey("id") && map.containsKey("roleIds")) {
            Integer id = (Integer) map.get("id");
            Set<String> roleIds = new HashSet((ArrayList<String>) map.get("roleIds"));
            sysUserService.setRoleToUser(id, roleIds);
            return ResultUtil.success();
        } else {
            return ResultUtil.error(1, "参数不完整");
        }
    }

    /**
     * 获取用户的角色
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取用户的角色", notes = "通过用户id获取用户的角色")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int", paramType = "path")
    @GetMapping("/getRolesByUserId")
    public Result findRolesByUserId(@RequestParam Integer id) {
        Set<SysRole> rolesByUserId = sysUserService.findRolesByUserId(id);
        List<String> collect = rolesByUserId.stream().map(SysRole::getId).collect(Collectors.toList());
        return ResultUtil.success(collect);
    }

    @ApiIgnore
    @GetMapping("/getUserIdInRoleIds")
    public List<Integer> getUserIdInRoleIds(@RequestParam String... roleIds) {
        return this.sysUserService.getUserIdInRoleIds(roleIds);
    }

    /**
     * @param userIds
     * @return
     */
    @ApiIgnore
    @GetMapping("/getNameInUserIds")
    public String getNameInUserIds(@RequestParam Integer... userIds) {
        if (!redisTemplate.hasKey(CommonConstants.CACHE_USER_KEY)) {
            sysUserService.cacheAllUserData();
        }
        return this.sysUserService.findNamesInIdRange(userIds);
    }

    /**
     * <h2>根据用户id集合查询用户信息</h2>
     *
     * @param userIds
     * @return
     */
    @ApiOperation(value = "根据用户id数组获取用户信息", notes = "根据用户id数组获取用户信息")
    @ApiImplicitParam(name = "userIds", value = "用户id数组", required = true, dataType = "int[]", paramType = "query", example = "1,2,3")
    @GetMapping("/getUserInfoInUserIds")
    public List<SysUser> getUserInfoInUserIds(@RequestParam Integer... userIds) {
        return this.sysUserService.getUserInfoInUserIds(userIds);
    }

    /**
     * 更新用户应用注册数
     *
     * @param sysUserUpdateBO
     */
    @ApiIgnore
    @PostMapping("/updateProductRegisterCount")
    public R updateProductRegisterCount(@Valid @RequestBody SysUserUpdateBO sysUserUpdateBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return sysUserService.updateProductRegisterCount(sysUserUpdateBO);
    }

}
