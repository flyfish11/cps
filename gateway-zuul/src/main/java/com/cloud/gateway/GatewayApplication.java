package com.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 网关系统
 *
 * @author com.hlxd
 */
@EnableFeignClients
// EnableZuulProxy 是一个组合注解
// 可以看到是有Hystrix的启动注解“@EnableCircuitBreaker”的，
// 这就进一步说明了Zuul中的所有请求都在Hystrix Command中执行。
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
//@EnableCircuitBreaker
@EnableHystrixDashboard
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}