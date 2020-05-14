package com.cloud.gateway.hystrix;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.cloud.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Zuul的FallBack回退机制
 * 在默认情况下，经过Zuul的请求都会被Hystrix包裹，即Zuul的所有请求在Hystrix Command中执行，所以Zuul本身就具有断路器的功能
 * 当zuul 路由的服务出现异常或者挂掉，断路器断开
 */
@Component
@Slf4j
public class MyFallback implements FallbackProvider {

    //针对哪一个路由进行降级， return 可以写*
   /* @Override
    public String getRoute() {
        return "service-center";
    }*/

    @Override
    public String getRoute() {
        return "*";//api服务id，如果需要所有调用都支持回退，则return "*"或return null
    }

    //降级时处理方式
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {
                log.info("close()");
            }

            //业务降级处理方式
            @Override
            public InputStream getBody() throws IOException {
                log.info("服务异常");
                R response = R.failed("服务异常");
                String result = JSONUtil.toJsonStr(response);
                return new ByteArrayInputStream(result.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}