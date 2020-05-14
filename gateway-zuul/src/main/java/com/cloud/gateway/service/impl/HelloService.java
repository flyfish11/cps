package com.cloud.gateway.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HelloService {


    public String sayHello(String name) {
        log.info("调用 sayHello");
        try {
            // 线程休眠1.5秒 ，超时降级
            Thread.sleep(15000);
            return "Hello " + name + " !";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return e.getMessage();

        }
    }



}