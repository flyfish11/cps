package com.cloud.platformuser.service.impl;

import com.cloud.common.constants.CommonConstants;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.ResultUtil;
import com.cloud.common.utils.TreeUtil;
import com.cloud.common.utils.UUIDUtils;
import com.cloud.common.vo.MenuTree;
import com.cloud.model.common.Result;
import com.cloud.model.common.ZTreeNode;
import com.cloud.model.platformuser.SysMenu;
import com.cloud.model.platformuser.SysRole;
import com.cloud.model.platformuser.bo.SysMenuAddBO;
import com.cloud.model.platformuser.bo.SysMenuUpdateBO;
import com.cloud.model.platformuser.LoginAppUser;
import com.cloud.platformuser.dao.SysMenuDao;
import com.cloud.platformuser.service.SysMenuService;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuDao sysMenuDao;

    @Override
    public int deleteById(String id) {
        return sysMenuDao.deleteById(id);
    }

    @Override
    public Result insertSelective(SysMenuAddBO sysMenuAddBO) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuAddBO, sysMenu);
        String menuId = UUIDUtils.getUUID().trim();
        sysMenu.setId(menuId);
        sysMenu.setCreateTime(new Date());
        sysMenu.setUpdateTime(sysMenu.getCreateTime());
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (loginAppUser != null) {
            sysMenu.setCreateBy(loginAppUser.getAccount());
        }
        //若pId为空，则为应用标识菜单
        if (StringUtils.isBlank(sysMenu.getPId())) {
            sysMenu.setPId(CommonConstants.DEFAULT_MENU_PID);
            sysMenu.setApplicationId(menuId);
        } else {
            if (sysMenu.getPId().equals(CommonConstants.DEFAULT_MENU_PID)) {
                sysMenu.setApplicationId(menuId);
            } else {
                SysMenu parentMenu = this.sysMenuDao.selectById(sysMenu.getPId());
                sysMenu.setApplicationId(parentMenu.getApplicationId());
            }
        }
        sysMenuDao.insertSelective(sysMenu);
        return ResultUtil.success("新增成功");
    }

    @Override
    public SysMenu findById(String menuId) {
        SysMenu menu = sysMenuDao.selectById(menuId);
        if (StringUtils.isNotBlank(menu.getPId()) && !"0".equals(menu.getPId())) {
            SysMenu pMenu = sysMenuDao.selectById(menu.getPId());
            if (pMenu != null) {
                menu.setPIdTitle(pMenu.getTitle());
            }
        }
        return menu;
    }

    @Override
    public int updateMenu(SysMenuUpdateBO sysMenuUpdateBO) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuUpdateBO, sysMenu);
        sysMenu.setUpdateTime(new Date());
        if (Objects.isNull(sysMenu.getPId())) {
            sysMenu.setPId(CommonConstants.DEFAULT_MENU_PID);
        }
        return sysMenuDao.updateByIdSelective(sysMenu);
    }

    @Override
    public List<SysMenu> findSysMenu(Map<String, Object> params) {
        params.put("menuScope", Boolean.TRUE);
        return sysMenuDao.selectSysMenu(params);
    }


    @Override
    public List<ZTreeNode> getZtreesMenu(String menuType, Boolean menuScope) {
        return sysMenuDao.selectZtreesMenu(menuType, menuScope);
    }

    @Override
    public List<SysMenu> findByRoles(Set<String> roleIds, String applicationId) {
        return sysMenuDao.selectByRoles(roleIds, applicationId);
    }

    @Override
    public List<ZTreeNode> menuTreeListByroleId(String roleId) {

        return sysMenuDao.menuTreeListByroleId(roleId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setMenuToRole(String roleId, Set<String> menuIds) {
        sysMenuDao.delete(roleId, null);

        if (!CollectionUtils.isEmpty(menuIds)) {
            menuIds.forEach(menuId -> sysMenuDao.setMenuToRole(roleId, menuId));
        }
    }

    @Override
    public List<MenuTree> menuTree(Map<String, Object> params) {
        List<SysMenu> sysMenus = this.sysMenuDao.selectSysMenu(params);
        List<MenuTree> treeList = sysMenus.stream().filter(m -> m.getPId() != null).map(menu -> {
            MenuTree node = new MenuTree();
            menu = findById(menu.getId());
            BeanUtils.copyProperties(menu, node);
            node.setIndex(menu.getPath());
            node.setLabel(menu.getTitle());
            return node;
        }).collect(Collectors.toList());
        Set<String> ids = Sets.newHashSet();
        ids.add("0");
        return TreeUtil.menuTree(treeList, ids);
    }

    @Override
    public List<MenuTree> menuTree(String applicationId) {
        Map<String, Object> params = new HashMap<>();
        params.put("applicationId", applicationId);
        params.put("menuType", "C");
        List<SysMenu> sysMenus = this.sysMenuDao.selectSysMenu(params);
        List<MenuTree> treeList = sysMenus.stream().filter(m -> m.getPId() != null).map(menu -> {
            MenuTree node = new MenuTree();
            menu = findById(menu.getId());
            BeanUtils.copyProperties(menu, node);
            node.setIndex(menu.getPath());
            node.setLabel(menu.getTitle());
            return node;
        }).collect(Collectors.toList());
        Set<String> ids = Sets.newHashSet();
        ids.add("0");
        return TreeUtil.menuTree(treeList, ids);
    }

    @Override
    public SysMenu getAppRootMenu(String applicationId) {
        return this.sysMenuDao.selectByApplicationIdAndPId(applicationId, "0");
    }

    @Override
    public List<MenuTree> getAppMunus(String applicationId) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Set<SysRole> roles = loginAppUser.getSysRoles();
        if (CollectionUtils.isEmpty(roles)) {
            return Collections.emptyList();
        }
        List<SysMenu> sysMenus = findByRoles(roles.parallelStream().map(SysRole::getId).collect(Collectors.toSet()), applicationId);
        dealMenuData(sysMenus);

        List<MenuTree> treeList = sysMenus.stream().filter(m -> m.getPId() != null).map(menu -> {
            MenuTree node = new MenuTree();
            BeanUtils.copyProperties(menu, node);
            return node;
        }).collect(Collectors.toList());

        SysMenu appRootMenu = getAppRootMenu(applicationId);
        Set<String> ids = sysMenus.stream().filter(m -> appRootMenu.getId().equals(m.getPId())).map(SysMenu::getId).collect(Collectors.toSet());
        return TreeUtil.menuTreeNew(treeList, ids);
    }

    @Override
    public List<String> menuTreeByroleId(String roleId) {
        return this.sysMenuDao.menuTreeByroleId(roleId);
    }

    private void dealMenuData(List<SysMenu> menus) {
        if (!menus.isEmpty()) {
            for (int i = 0; i < menus.size(); i++) {
                if (StringUtils.isNotBlank(menus.get(i).getPath())) {
                    menus.get(i).setIndex(menus.get(i).getPath());
                }
            }
        }
    }

    private void setChild(SysMenu menu, List<SysMenu> menus) {
        List<SysMenu> child = menus.stream().filter(m -> m.getPId().equals(menu.getId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(child)) {
            menu.setSubs(child);
        }
    }
}
