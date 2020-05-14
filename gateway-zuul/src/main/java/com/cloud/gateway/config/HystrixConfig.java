package com.cloud.gateway.config;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 服务限流功能配置了类
 */
@Configuration
public class HystrixConfig {

  /*  *//**
     * 声明一个HystrixCommandAspect代理类，现拦截HystrixCommand的功能
     *//*
    @Bean
    public HystrixCommandAspect hystrixCommandAspect() {
        return new HystrixCommandAspect();
    }
*/
}