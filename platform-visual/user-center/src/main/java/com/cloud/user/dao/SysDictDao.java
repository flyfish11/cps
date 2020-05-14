package com.cloud.user.dao;

import com.cloud.model.user.SysDict;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;


/**
 * 字典表(SysDict)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-18 16:31:39
 */
@Mapper
public interface SysDictDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysDict queryById(String id);
    
     /**
     * 查询所有数据天条数
     *
     * @param params 查询参数
     * @return 返回数据条数
     */
    long count(Map<String, Object> params);

    /**
     * 查询所有
     *
     * @param params 查询参数（包括分页<start ,length>）
     * @return 返回对象集合
     */
    List<SysDict> queryAllByLimit(Map<String, Object> params);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysDict 实例对象
     * @return 对象列表
     */
    List<SysDict> queryAll(SysDict sysDict);

    /**
     * 新增数据
     *
     * @param sysDict 实例对象
     * @return 影响行数
     */
    int insert(SysDict sysDict);

    /**
     * 修改数据
     *
     * @param sysDict 实例对象
     * @return 影响行数
     */
    int update(SysDict sysDict);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}