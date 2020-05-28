package com.cloud.platformuser.dao;

import com.cloud.model.platformuser.SysDictItem;
import com.cloud.model.platformuser.vo.SysDictItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 字典项(SysDictItem)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-18 16:31:39
 */
@Mapper
public interface SysDictItemDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysDictItem queryById(String id);

    /**
     * 通过字典表ID查询字典表项数据
     *
     * @param dictId 主键
     * @return list
     */
    List<SysDictItem> queryByDictId(String dictId);

    /**
     * 通过字典表ID查询字典表项数据
     *
     * @param dictType 主键
     * @return list
     */
    List<SysDictItem> queryByDictType(String dictType);

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
    List<SysDictItem> queryAllByLimit(Map<String, Object> params);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysDictItem 实例对象
     * @return 对象列表
     */
    List<SysDictItem> queryAll(SysDictItem sysDictItem);

    /**
     * 新增数据
     *
     * @param sysDictItem 实例对象
     * @return 影响行数
     */
    int insert(SysDictItem sysDictItem);

    /**
     * 修改数据
     *
     * @param sysDictItem 实例对象
     * @return 影响行数
     */
    int update(SysDictItem sysDictItem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    /**
     * 根据类型查询字典项数据
     *
     * @param type    类型
     * @param delFlag 删除标记
     * @return
     */
    List<SysDictItemVO> findAllByTypeAndDelFlag(@Param("type") String type, @Param("delFlag") String delFlag);

}