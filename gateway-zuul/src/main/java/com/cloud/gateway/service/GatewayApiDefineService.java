package com.cloud.gateway.service;

import com.cloud.common.utils.R;
import com.cloud.model.common.Page;
import com.cloud.model.gateway.GatewayApiDefine;
import com.cloud.model.gateway.bo.GatewayApiDefineAddBO;
import com.cloud.model.gateway.bo.GatewayApiDefineUpdateBO;

import java.util.Map;

/**
 * @Classname GatewayApiDefineService
 * @Description 路由规则service层
 * @Author yulj
 * @Date: 2019/07/16 21:42
 */
public interface GatewayApiDefineService {

    /**
     * 刷新路由规则缓存
     */
    void refreshRoute();

    /**
     * 分页查询路由规则数据
     *
     * @param params
     * @return
     */
    Page<GatewayApiDefine> pageList(Map<String, Object> params);

    /**
     * 新增路由规则记录
     *
     * @param gatewayApiDefine
     * @return
     */
    R add(GatewayApiDefineAddBO gatewayApiDefine);

    /**
     * 根据id查询路由规则信息
     *
     * @param id
     * @return
     */
    R findById(String id);

    /**
     * 根据serviceId 获取路由规则
     *
     * @param serviceId
     * @return
     */
    R findByServiceId(String serviceId);

    /**
     * 修改路由规则记录
     *
     * @param gatewayApiDefineUpdateBO
     * @return
     */
    R update(GatewayApiDefineUpdateBO gatewayApiDefineUpdateBO);

    /**
     * 删除路由规则记录
     *
     * @param id
     * @return
     */
    R delete(String id);

}