package com.cloud.txmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Classname TxManagerApplication
 * @Description <h1>分布式事务管理主启动类</h1>
 * @Author yulj
 * @Date: 2020/04/08 5:28 下午
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TxManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxManagerApplication.class, args);
    }

}