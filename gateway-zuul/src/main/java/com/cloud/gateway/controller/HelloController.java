package com.cloud.gateway.controller;

import com.cloud.gateway.service.impl.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * 服务限流功能测试
 * 对服务 helloService.sayHello 接口进行限流
 */
@RestController
public class HelloController
{

    @Autowired
    private HelloService helloService;



//    @HystrixCommand(fallbackMethod = "helloError", commandProperties = {
//            // 该属性用来设置HystrixCommand.run()执行的隔离策略。默认为THREAD。
//            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
//            // 用来配置HystrixCommand执行的超时时间，单位为毫秒。
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
//            // 该属性用来配置HystrixCommand.run()的执行是否启用超时时间
//            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
//            // 用来设置在滚动时间窗中，断路器熔断的最小请求数。
//            // 例如，默认该值为20的时候，如果滚动时间窗（默认10秒）
//            // 内仅收到19个请求，即使这19个请求都失败了，断路器也不会打开。
//            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10")
//    }, threadPoolProperties = {
//            @HystrixProperty(name = "coreSize", value = "5"),
//            @HystrixProperty(name = "maximumSize", value = "5"),
//            @HystrixProperty(name = "maxQueueSize", value = "10")
//    })
   // @HystrixCommand(fallbackMethod = "helloError")
    @GetMapping ("hello")
    public ResponseEntity<List<String>> sayHello(String name){

        List<String> strings = new ArrayList<>();
        strings.add("1234556");
        strings.add("12345567");
        strings.add("12345568");
        strings.add("12345569");
        strings.add("12345561");

        //return helloService.sayHello(name);

        return  new ResponseEntity(strings, HttpStatus.OK);
    }

    public String helloError(String name) {
        return name;
    }
}
