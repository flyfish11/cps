package com.cloud.service.config;

import lombok.Data;

/**
 * @Classname ServiceInfo
 * @Description 微服务基础信息
 * @Author yulj
 * @Date: 2019/10/12 5:52 下午
 */
@Data
public class ServiceInfo {

    private String serviceId;

    private Integer port;
}
