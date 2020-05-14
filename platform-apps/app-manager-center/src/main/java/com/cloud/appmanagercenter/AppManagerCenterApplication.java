package com.cloud.appmanagercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Classname AppManagerCenterApplication
 * @Description <h1>主启动程序</h1>
 * @Author yulj
 * @Date: 2020/04/21 11:12 上午
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
@SpringBootApplication
public class AppManagerCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppManagerCenterApplication.class, args);
    }

}
