package com.cloud.file.config;

import com.cloud.file.service.MinioTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname MinioConfig
 * @Description MinioConfig相关Bean配置
 * @Author yulj
 * @Date: 2019/06/29 11:04
 */
@Configuration
public class MinioConfig {

    @Bean
    @ConditionalOnMissingBean(MinioTemplate.class)
    @ConditionalOnProperty(name = "minio.url")
    MinioTemplate template() {
        return new MinioTemplate();
    }
}
