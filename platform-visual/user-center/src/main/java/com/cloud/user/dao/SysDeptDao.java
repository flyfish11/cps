package com.cloud.user.dao;

import com.cloud.model.common.ZTreeNode;
import com.cloud.model.user.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Classname SysDeptDao
 * @Description <h1>部门表数据访问层</h1>
 * @Author yulj
 * @Date: 2020/04/21 3:47 下午
 */
@Mapper
public interface SysDeptDao {

    int deleteById(Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectById(Integer id);

    int updateByIdSelective(SysDept record);

    int updateById(SysDept record);

    int batchInsert(@Param("list") List<SysDept> list);

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree();

    /**
     * 查询所有部门信息
     *
     * @return
     */
    List<SysDept> findAllDept(Map<String,Object> param);

    List<SysDept> findAllDeptInRange(@Param("deptIds") Collection<Integer> deptIds);

    List<SysDept> selectAllByFullname(@Param("fullname")String fullname);

    SysDept selectOneByFullnameAndIdNot(@Param("fullname")String fullname,@Param("deptId")Integer deptId);


}