package com.cloud.user.service;

import com.cloud.common.vo.MenuTree;
import com.cloud.model.common.Result;
import com.cloud.model.common.ZTreeNode;
import com.cloud.model.user.SysMenu;
import com.cloud.model.user.bo.SysMenuAddBO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysMenuService {

    int deleteById(String id);

    Result insertSelective(SysMenuAddBO sysMenuAddBO);

    SysMenu findById(String menuId);

    int updateMenu(SysMenu record);

    List<SysMenu> findSysMenu(Map<String, Object> params);

    List<ZTreeNode> getZtreesMenu(String menuType, Boolean menuScope);

    List<SysMenu> findByRoles(Set<String> roleIds, String applicationId);

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeListByroleId(String roleId);


    void setMenuToRole(String roleId, Set<String> menuIds);

    List<MenuTree> menuTree(Map<String, Object> params);

    /**
     * 获取菜单数据（element）
     *
     * @param applicationId
     * @return
     */
    List<MenuTree> menuTree(String applicationId);

    /**
     * 获取应用的顶级菜单
     *
     * @param applicationId
     * @return
     */
    SysMenu getAppRootMenu(String applicationId);

    /**
     * 获取应用菜单(Vue版本)
     *
     * @param applicationId
     * @return
     */
    List<MenuTree> getAppMunus(String applicationId);


    List<String> menuTreeByroleId(String roleId);

}
