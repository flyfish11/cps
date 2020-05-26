package com.cloud.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname BaseServices
 * @Description 基础服务配置类
 * @Author yulj
 * @Date: 2019/10/12 5:47 下午
 */
@Data
@Component
@ConfigurationProperties
public class BaseServicesProperties {

    private List<ServiceInfo> baseServices;
}
