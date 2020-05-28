package com.cloud.platformuser.service;

import com.cloud.model.platformuser.SysDict;
import com.cloud.model.common.Page;
import java.util.Map;


/**
 * 字典表(SysDict)表服务接口
 *
 * @author makejava
 * @since 2019-09-18 16:31:39
 */
public interface SysDictService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysDict queryById(String id);

    /**
     * 查询所有
     * @param params 查询参数（包括分页<start ,length>）
     * @return 返回对象集合
     */
    Page<SysDict> queryAllByLimit(Map<String, Object> params);

    /**
     * 新增数据
     *
     * @param sysDict 实例对象
     * @return 实例对象
     */
    SysDict insert(SysDict sysDict);

    /**
     * 修改数据
     *
     * @param sysDict 实例对象
     * @return 实例对象
     */
    SysDict update(SysDict sysDict);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}