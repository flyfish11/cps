package com.cloud.user.controller;

import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.user.SysRole;
import com.cloud.user.dto.SysRoleDto;
import com.cloud.user.service.SysMenuService;
import com.cloud.user.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@Api(tags = "角色管理")
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 管理后台添加角色
     *
     * @param sysRole
     * @return
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "新增角色记录")
    @ApiOperation(value = "管理后台添加角色", notes = "新增角色")
    @ApiImplicitParam(name = "sysRole", value = "角色实体", required = true, dataType = "SysRole", paramType = "query")
    @PostMapping("/add")
    public Result save(@RequestBody SysRole sysRole) {
        if (StringUtils.isBlank(sysRole.getCode())) {
            throw new IllegalArgumentException("角色code不能为空");
        }
        if (StringUtils.isBlank(sysRole.getName())) {
            sysRole.setName(sysRole.getCode());
        }
        this.sysRoleService.save(sysRole);
        return ResultUtil.success();
    }

    /**
     * 管理后台删除角色
     *
     * @param id
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "删除角色记录")
    @ApiOperation(value = "删除角色", notes = "根据角色id删除角色")
    @ApiImplicitParam(name = "id", value = "角色id", required = false, dataType = "String", paramType = "path")
    @GetMapping("/remove")
    public Result deleteRole(@RequestParam String id) {
        sysRoleService.deleteRole(id);
        return ResultUtil.success();

    }

    /**
     * 管理后台修改角色
     *
     * @param sysRole 角色实体
     * @return
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "修改角色记录")
    @ApiOperation(value = "修改角色", notes = "根据角色实体修改角色")
    @ApiImplicitParam(name = "sysRole", value = "角色实体", required = true, dataType = "SysRole", paramType = "query")
    @PostMapping("/update")
    public Result update(@RequestBody SysRole sysRole) {
        if (StringUtils.isBlank(sysRole.getName())) {
            throw new IllegalArgumentException("角色名不能为空");
        }
        sysRoleService.update(sysRole);
        return ResultUtil.success();
    }

    /**
     * 给角色分配权限
     *
     * @param map
     * @return
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "角色分配权限")
    @ApiOperation(value = "给角色分配权限", notes = "管理后台给角色分配权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "permissionIds", value = "permissionIds", required = true, dataType = "Set<String>", paramType = "query")
    })
    @PostMapping("/authorize")
    public Result authorize(@RequestBody Map<String, Object> map) {

        if (map.containsKey("id") && map.containsKey("permissionIds")) {
            String id = (String) map.get("id");
            Set<String> permissionIds = new HashSet((ArrayList<String>) map.get("permissionIds"));
            sysMenuService.setMenuToRole(id, permissionIds);

            return ResultUtil.success();
        } else {
            return ResultUtil.error(1, "参数不完整");
        }
    }

    /**
     * @param id 角色ID
     * @return
     */
    @ApiOperation(value = "获取角色的权限", notes = "根据角色id获取角色的权限")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "String", paramType = "path")
    @GetMapping("/getById")
    public Result findById(@RequestParam String id) {
        SysRole sysRole = sysRoleService.findById(id);
        return ResultUtil.success(sysRole);
    }

    /**
     * 搜索角色
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "搜索角色", notes = "根据关键词搜索角色")
    @ApiImplicitParam(name = "params", value = "搜索关键词", required = false, dataType = "Map", paramType = "query")
    @GetMapping("/findAll")
    public Result findRoles(@RequestParam Map<String, Object> params) {
        Page<SysRoleDto> roles = sysRoleService.findRoles(params);
        return ResultUtil.success(roles.getTotal(), roles.getData());
    }

    /**
     * 流程引擎获取角色
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "搜索角色", notes = "根据关键词搜索角色")
    @ApiImplicitParam(name = "params", value = "搜索关键词", required = false, dataType = "Map", paramType = "query")
    @GetMapping("/act/roles")
    public Page<SysRoleDto> actFindRoles(@RequestParam Map<String, Object> params) {
        return sysRoleService.findRoles(params);
    }

    @ApiOperation(value = "搜索角色", notes = "根据关键词搜索角色")
    @ApiImplicitParam(name = "params", value = "搜索关键词", required = false, dataType = "Map", paramType = "query")
    @GetMapping("/organize/roles")
    public Page<SysRoleDto> userFindRoles(@RequestParam Map<String, Object> params) {
        return sysRoleService.findRoles(params);
    }

}
