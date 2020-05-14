package com.cloud.user.dao;

import com.cloud.model.user.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Classname SysUserDao
 * @Description <h1>用户表数据访问层</h1>
 * @Author yulj
 * @Date: 2020/04/21 3:47 下午
 */
@Mapper
public interface SysUserDao {

    int deleteById(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectById(Integer id);

    int updateByIdSelective(SysUser record);

    int updateById(SysUser record);

    int batchInsert(@Param("list") List<SysUser> list);

    List<SysUser> findAllByStatus(@Param("status") Integer status);

    /**
     * 查询所有用户((在应用可见范围部门内))
     *
     * @param deptIds
     * @return
     */
    List<SysUser> findAllUserInDeptRange(@Param("deptIds") Collection<Integer> deptIds);

    int count(Map<String, Object> params);

    /**
     * 分页查询用户
     *
     * @param params
     * @return
     */
    List<SysUser> findData(Map<String, Object> params);

    List<SysUser> selectAllByIdIn(@Param("idCollection") Collection<Integer> idCollection);

    SysUser selectOneByAccount(@Param("account") String account);

    SysUser selectOneByPhone(@Param("phone") String phone);

    SysUser selectOneByPhoneAndIdNot(@Param("phone") String phone, @Param("userId") Integer userId);

    SysUser selectOneByEmail(@Param("email")String email);

	SysUser selectOneByEmailAndIdNot(@Param("email")String email,@Param("userId")Integer userId);

}