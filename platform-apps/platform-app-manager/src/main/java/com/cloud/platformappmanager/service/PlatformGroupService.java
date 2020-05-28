package com.cloud.platformappmanager.service;

import com.cloud.common.utils.R;
import com.cloud.model.platformappmanager.PlatformGroup;
import com.cloud.model.platformappmanager.bo.PlatformGroupAddBO;
import com.cloud.model.platformappmanager.bo.PlatformGroupUpdateBO;
import com.cloud.model.common.Page;

import java.util.Map;

/**
 * @Classname PlatformGroupService
 * @Description <h1>平台类型表服务层</h1>
 * @Author yulj
 * @Date: 2020/04/22 2:56 下午
 */
public interface PlatformGroupService {

    /**
     * <h2>分页查询平台类型分页数据</h2>
     *
     * @param params 查询条件Map
     * @return
     */
    Page<PlatformGroup> list(Map<String, Object> params);

    /**
     * <h2>新增平台类型表数据</h2>
     *
     * @param platformGroupAddBO 平台类型新增数据对象
     * @return
     */
    R add(PlatformGroupAddBO platformGroupAddBO);

    /**
     * <h2>修改平台类型表数据</h2>
     *
     * @param platformGroupUpdateBO 平台类型更新数据对象
     * @return
     */
    R update(PlatformGroupUpdateBO platformGroupUpdateBO);

    /**
     * <h2>删除平台类型表数据</h2>
     *
     * @param id 平台类型id
     * @return
     */
    R delete(Integer id);

    /**
     * <h2>查询平台类型下拉选</h2>
     *
     * @return
     */
    R queryPlatformSelect();

    /**
     * <h2>根据id查询平台类型表数据</h2>
     *
     * @param id 平台类型id
     * @return
     */
    R load(Integer id);

    /**
     * <h2>查询所有平台类型</h2>
     *
     * @return
     */
    Map<String, String> queryAllPlatformGroup();

}
