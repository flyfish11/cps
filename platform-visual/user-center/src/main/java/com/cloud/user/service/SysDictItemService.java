package com.cloud.user.service;

import com.cloud.model.common.Page;
import com.cloud.model.user.SysDictItem;

import java.util.List;
import java.util.Map;


/**
 * 字典项(SysDictItem)表服务接口
 *
 * @author makejava
 * @since 2019-09-18 16:31:39
 */
public interface SysDictItemService {

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
     * 查询所有
     *
     * @param params 查询参数（包括分页<start ,length>）
     * @return 返回对象集合
     */
    Page<SysDictItem> queryAllByLimit(Map<String, Object> params);

    /**
     * 新增数据
     *
     * @param sysDictItem 实例对象
     * @return 实例对象
     */
    SysDictItem insert(SysDictItem sysDictItem);

    /**
     * 修改数据
     *
     * @param sysDictItem 实例对象
     * @return 实例对象
     */
    SysDictItem update(SysDictItem sysDictItem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */

    boolean deleteById(String id);

    /**
     * 通过字典表 type 查询字典表项数据
     *
     * @param dictType
     * @return
     */
    List<SysDictItem> queryByDictType(String dictType);

    /**
     * 通过字典表 type 查询字典下拉选
     *
     * @param dictType 字典类型
     * @return
     */
    Map<String, String> findDictSelectByType(String dictType);

}