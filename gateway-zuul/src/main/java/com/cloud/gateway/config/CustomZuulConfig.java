package com.cloud.gateway.config;

import com.cloud.gateway.dao.GatewayApiDefineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname CustomZuulConfig
 * @Description 自定义zuul配置
 * @Author yulj
 * @Date: 2019/07/16 20:34
 */
@Configuration
public class CustomZuulConfig {

    @Autowired
    private  ZuulProperties zuulProperties;

    @Autowired
    private ServerProperties server;

    @Autowired
    private GatewayApiDefineDao gatewayApiDefineDao;

    /**
     * 注入自定义的路由规则定位器Bean
     *
     * @return
     */
    @Bean
    public CustomRouteLocator routeLocator() {
        CustomRouteLocator routeLocator = new CustomRouteLocator(this.server.getServlet().getContextPath(), this.zuulProperties);
        routeLocator.setGatewayApiDefineDao(gatewayApiDefineDao);
        return routeLocator;
    }
}
