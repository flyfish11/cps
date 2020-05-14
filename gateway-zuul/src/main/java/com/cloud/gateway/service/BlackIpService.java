package com.cloud.gateway.service;

import com.cloud.common.utils.R;
import com.cloud.model.common.Page;
import com.cloud.model.gateway.BlackIp;
import com.cloud.model.gateway.bo.BlackIpAddBO;
import com.cloud.model.gateway.bo.BlackIpUpdateBO;

import java.util.Map;

/**
 * @Classname BlackIpService
 * @Description <h1>黑名单表服务层</h1>
 * @Author yulj
 * @Date: 2020/04/17 5:43 下午
 */
public interface BlackIpService {

    R save(BlackIpAddBO blackIpAddBO);

    R update(BlackIpUpdateBO blackIp);

    R delete(String id);

    Page<BlackIp> findList(Map<String, Object> params);

    R findAllBlackIPs();

}
