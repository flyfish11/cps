package com.cloud.gateway.config;

import com.cloud.gateway.dao.GatewayApiDefineDao;
import com.cloud.model.gateway.GatewayApiDefine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname CustomRouteLocator
 * @Description 自定义路由规则定位器
 * @Author yulj
 * @Date: 2019/07/16 20:26
 */
@Slf4j
public class CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private GatewayApiDefineDao gatewayApiDefineDao;

    private ZuulProperties properties;

    public void setGatewayApiDefineDao(GatewayApiDefineDao gatewayApiDefineDao) {
        this.gatewayApiDefineDao = gatewayApiDefineDao;
    }

    public CustomRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<>();
        //从application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());

        //从db中加载路由信息
        routesMap.putAll(locateRoutesFromDB());

        LinkedHashMap<String, ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    private Map<String, ZuulRoute> locateRoutesFromDB() {
        Map<String, ZuulRoute> routes = new LinkedHashMap<>();
        List<GatewayApiDefine> gatewayApiDefines = this.gatewayApiDefineDao.selectAllByEnabled(Boolean.TRUE);
        for (GatewayApiDefine result : gatewayApiDefines) {
            if (org.apache.commons.lang3.StringUtils.isBlank(result.getPath())) {
                continue;
            }
            ZuulRoute zuulRoute = new ZuulRoute();
            try {
                org.springframework.beans.BeanUtils.copyProperties(result, zuulRoute);
            } catch (Exception e) {
                log.error("从数据库加载路由规则信息出错！", e);
            }
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }
}
