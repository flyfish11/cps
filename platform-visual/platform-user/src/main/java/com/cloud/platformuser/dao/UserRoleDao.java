package com.cloud.platformuser.dao;

import com.cloud.model.platformuser.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 用户角色关系
 *
 * @author com.hlxd
 */
@Mapper
public interface UserRoleDao {

    int deleteUserRole(@Param("userId") Integer userId, @Param("roleId") String roleId);

    @Insert("insert into sys_role_user(userId, roleId) values(#{userId}, #{roleId})")
    int saveUserRoles(@Param("userId") Integer userId, @Param("roleId") String roleId);

    /**
     * 根据用户id获取角色
     *
     * @param userId
     * @return
     */
    @Select("select r.* from sys_role_user ru inner join sys_role r on r.id = ru.roleId where ru.userId = #{userId}")
    Set<SysRole> findRolesByUserId(Integer userId);

    List<Integer> findUserIdInRoleId(@Param("roleIdList") Collection<String> roleIdList);
}
