package com.cloud.gateway.filter;

import com.cloud.gateway.dao.BlackIpDao;
import com.cloud.model.gateway.BlackIp;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 黑名单IP拦截<br>
 * 黑名单ip变化不会太频繁，<br>
 * 考虑到性能，我们不实时掉接口从别的服务获取了，<br>
 * 而是定时把黑名单ip列表同步到网关层,
 * <p>
 * zuul 四种过滤器
 * ①PRE:这种过滤器在请求被路由之前调用。可利用这种过滤器实现身份验证、在集群中选择请求的微服务，记录调试信息等。
 * ②ROUTING:这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache?HttpClient或Netflix?Ribbon请求微服务。
 * ③POST:这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP header、收集统计信息和指标、将响应从微服务发送给客户端等。
 * ④ERROR:在其他阶段发送错误时执行该过滤器。
 * 除了上面的标准过滤器以外，Zuul还允许我们自定义过滤器类型。比如我们可以自定义一个STATIC类型的过滤器，直接在Zuul中生成响应，而不将请求转发到后端的微服务。
 *
 * @author com.hlxd
 */
@Component
@Slf4j
public class BlackIPAccessFilter extends ZuulFilter {

    private static final String UNKNOWN = "unknown"; //

    @Autowired
    private BlackIpDao blackIpDao;

    /**
     * 黑名单列表
     */
    private Set<String> blackIPs = new HashSet<>();

    @Override
    public boolean shouldFilter() {
        if (blackIPs.isEmpty()) {
            return false;
        }

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String ip = getIpAddress(request);

        return blackIPs.contains(ip);// 判断ip是否在黑名单列表里
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        requestContext.setResponseBody("black ip");
        requestContext.setSendZuulResponse(false);

        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * pre 过滤器
     * 这种过滤器在请求被路由之前调用。
     * 可利用这种过滤器实现身份验证、在集群中选择请求的微服务，记录调试信息等。
     *
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 定时同步黑名单IP
     */
    @Scheduled(cron = "${cron.black-ip}")
    public void syncBlackIPList() {
        try {
            List<BlackIp> list = this.blackIpDao.findAll();
            blackIPs = list.stream()
                    .map(BlackIp::getIp)
                    .collect(Collectors.toSet());
            log.info("黑名单同步成功！！！");
        } catch (Exception e) {
            // do nothing
        }
    }

    /**
     * 获取请求的真实ip
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
