package com.cloud.platformuser.dao;

import com.cloud.model.common.ZTreeNode;
import com.cloud.model.platformuser.SysMenu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface SysMenuDao {

    int deleteById(String menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectById(String menuId);

    List<SysMenu> selectSysMenu(Map<String, Object> params);

    List<ZTreeNode> selectZtreesMenu(@Param("menuType") String menuType, @Param("menuScope") Boolean menuScope);


    List<SysMenu> selectByRoles(@Param("roleIds") Set<String> roleIds, @Param("applicationId") String applicationId);

    List<SysMenu> selectAllAuthority(@Param("roleIds") Set<String> roleIds, @Param("applicationId") String applicationId);

    /**
     * 查询角色拥有的权限id
     * @param roleId
     * @return
     */
    List<String> menuTreeByroleId(String roleId);
    /**
     * 根据角色查询所有菜单权限节点，
     * 并把当前角色拥有的权限设置为选中
     *
     * @param roleId
     * @return 返回 ztree格式 数据
     */
    List<ZTreeNode> menuTreeListByroleId(String roleId);

    @Insert("insert into role_menu(roleId, menuId) values(#{roleId}, #{menuId})")
    int setMenuToRole(@Param("roleId") String roleId, @Param("menuId") String menuId);

    int delete(@Param("roleId") String roleId, @Param("menuId") String menuId);

    int updateByIdSelective(SysMenu sysMenu);

    SysMenu selectByApplicationIdAndPId(@Param("applicationId") String applicationId, @Param("pId") String pId);

    SysMenu selectByPerms(@Param("perms") String perms);

}