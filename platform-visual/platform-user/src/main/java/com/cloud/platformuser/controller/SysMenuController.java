package com.cloud.platformuser.controller;

import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.R;
import com.cloud.common.utils.ResultUtil;
import com.cloud.common.vo.MenuTree;
import com.cloud.model.common.Result;
import com.cloud.model.common.ZTreeNode;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.platformuser.SysMenu;
import com.cloud.model.platformuser.bo.SysMenuAddBO;
import com.cloud.model.platformuser.bo.SysMenuUpdateBO;
import com.cloud.platformuser.service.SysMenuService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/sysMenu")
@Slf4j
@Api(tags = "菜单管理")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取所有菜单列表
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "菜单列表", notes = "根据关键词获取所有菜单列表")
    @GetMapping("/queryAll")
    public Result findSysMenu(@ApiIgnore @RequestParam Map<String, Object> params) {
        params.put("menuScope", Boolean.TRUE);
        List<MenuTree> menuTrees = sysMenuService.menuTree(params);
        return ResultUtil.success(menuTrees);
    }

    /**
     * 添加菜单
     *
     * @param sysMenuAddBO
     * @return
     */
    @ApiOperation(value = "添加菜单", notes = "添加菜单")
    @PostMapping("/add")
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "新增菜单")
    public Result addSysMenu(@Valid @RequestBody SysMenuAddBO sysMenuAddBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysMenuService.insertSelective(sysMenuAddBO);
    }

    /**
     * 根据菜单id查询菜单信息
     *
     * @param id
     * @return 菜单实体
     */
    @ApiOperation(value = "查询菜单信息", notes = "根据菜单id查询菜单信息")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "String", paramType = "path")
    @GetMapping("/getById")
    public Result getMenuById(@RequestParam String id) {
        SysMenu sysMenu = sysMenuService.findById(id);
        return ResultUtil.success(sysMenu);
    }

    /**
     * 获取下拉树菜单
     *
     * @return
     */
    @ApiOperation(value = "获取下拉树菜单", notes = "根据菜单类型（M目录 C菜单 F按钮）获取下拉树菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuType", value = "菜单类型", required = true, dataType = "String", paramType = "query"), @ApiImplicitParam(name = "menuScope", value = "菜单范围", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/selectZtree")
    public List<ZTreeNode> ztreeMenu(@RequestParam String menuType, @RequestParam Boolean menuScope) {
        return sysMenuService.getZtreesMenu(menuType, menuScope);
    }

    /**
     * 修改菜单信息
     *
     * @param sysMenuUpdateBO
     * @return
     */
    @ApiOperation(value = "修改菜单信息", notes = "根据菜单实体修改菜单信息")
    @PostMapping("/modify")
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "修改菜单")
    public Result updateMenu(@RequestBody SysMenuUpdateBO sysMenuUpdateBO) {
        sysMenuService.updateMenu(sysMenuUpdateBO);
        return ResultUtil.success(null);
    }

    /**
     * 根据用菜单id删除菜单
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除菜单", notes = "根据用菜单id删除菜单")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "String", paramType = "query")
    @GetMapping("/remove")
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "删除菜单")
    public Result deleteMenu(@RequestParam String id) {
        sysMenuService.deleteById(id);
        return ResultUtil.success(null);
    }

    /**
     * 获取当前用户树形菜单
     *
     * @return
     */
    @ApiOperation(value = "获取当前用户树形菜单", notes = "获取当前用户树形菜单")
    @GetMapping("/me")
    public List<MenuTree> findMyMenu(@RequestParam(required = false) String applicationId) {
        return this.sysMenuService.getAppMunus(applicationId);
    }

    /**
     * 获取当前用户树形菜单
     *
     * @return
     */
    @ApiOperation(value = "获取当前用户树形菜单", notes = "获取当前用户树形菜单")
    @GetMapping("/me/{applicationId}")
    public List<MenuTree> findMenu(@PathVariable String applicationId) {
        return this.sysMenuService.getAppMunus(applicationId);
    }

    /**
     * Ztree 数据结构
     * 根据角色id获取当前角色已分配的权限菜单
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "查询所有菜单权限节点", notes = "根据角色查询所有菜单权限节点并把当前角色拥有的权限设置为选中")
    @ApiImplicitParam(name = "roleId", value = "角色id", required = false, dataType = "String", paramType = "path")
    @GetMapping("/menuTreeListByRoleId")
    public Result menuTreeListByroleId(@RequestParam String roleId) {
        List<ZTreeNode> zTreeNodes = sysMenuService.menuTreeListByroleId(roleId);
        return ResultUtil.success(zTreeNodes);
    }

    /**
     * 查询角色拥有的权限
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "查询角色拥有的权限", notes = "根据角色id查询对应菜单")
    @GetMapping("/menuTreeByRoleId")
    public Result menuTreeByRoleId(@RequestParam String roleId) {
        List<MenuTree> menuTrees = this.sysMenuService.menuTree(new HashMap<>());
        List<String> strings = this.sysMenuService.menuTreeByroleId(roleId);
        return ResultUtil.success(0, menuTrees, strings);
    }

    /**
     * 查询应用对应菜单树
     *
     * @param applicationId
     * @return
     */
    @ApiOperation(value = "查询应用对应菜单列表", notes = "根据应用id查询对应菜单")
    @ApiImplicitParam(name = "applicationId", value = "应用id", required = true, dataType = "String", paramType = "path")
    @GetMapping("/tree/{applicationId}")
    public R menuTree(@PathVariable String applicationId) {
        return R.ok(this.sysMenuService.menuTree(applicationId));
    }

    /**
     * @return
     */
    @ApiOperation(value = "查询内部应用列表", notes = "查询菜单数据中内部应用列表")
    @GetMapping("/allInnerApplication")
    public Result allInnerApplication() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("pId", "0");
        params.put("menuScope", Boolean.TRUE);
        List<SysMenu> sysMenuList = sysMenuService.findSysMenu(params);
        return ResultUtil.success(sysMenuList);
    }

}