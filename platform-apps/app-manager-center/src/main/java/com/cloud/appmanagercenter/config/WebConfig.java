package com.cloud.appmanagercenter.config;

import com.cloud.common.constants.CommonConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Classname WebConfig
 * @Description Web相关Bean配置
 * @Author yulj
 * @Date: 2019/06/29 11:04
 */
@Configuration
public class WebConfig {

    /**
     * RestTemplate BEAN注入
     */
    @Bean
    public RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(CommonConstants.REQUEST_REMOTE_API_TIME_OUT);
        requestFactory.setReadTimeout(CommonConstants.REQUEST_REMOTE_API_TIME_OUT);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

}
