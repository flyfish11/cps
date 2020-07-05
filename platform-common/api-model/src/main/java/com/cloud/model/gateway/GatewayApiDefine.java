package com.cloud.model.gateway;

import lombok.Data;
import lombok.ToString;

/**
 * @Classname GatewayApiDefine
 * @Description 网关路由规则表
 * @Author yulj
 * @Date: 2019/07/16 21:34
 */
@Data
@ToString
public class GatewayApiDefine {

    private String id;

    private String path;

    private String serviceId;

    private String url;

    private Boolean retryable;

    private Boolean enabled;

    private Integer stripPrefix;

    private String apiName;

    private String roleId;
}