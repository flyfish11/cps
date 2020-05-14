package com.cloud.gateway.filter;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.cloud.common.utils.R;
import com.cloud.gateway.utils.CookieUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
@Slf4j
@Component
public class UniqueLoginFilter extends ZuulFilter
{


    @Value("${isUniqueLogin}")
    private Boolean isUniqueLogin;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String UNKNOWN = "unknown"; //


    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();


        String authorization = request.getHeader("authorization");

        if (StringUtils.isNotBlank(authorization)) {

            String token = authorization.substring(7, authorization.length());
            String value = redisTemplate.opsForValue().get(token);

            String cookieValue = CookieUtils.getCookieValue(request, token);
            if (StringUtils.isBlank(cookieValue) || cookieValue.equals(value)) {
                ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                R<Object> failed = R.failed("该账号已在其他地方登陆,请重新登陆");
                ctx.getResponse().setCharacterEncoding("UTF-8");
                ctx.setResponseBody(JSONUtil.toJsonStr(failed));
                ctx.setSendZuulResponse(true);

            }
        }
        return null;
    }

    @Override
    public boolean shouldFilter() {

        return isUniqueLogin;//
    }

    @Override
    public int filterOrder() {
        return -1;// 优先级为0，数字越大，优先级越低
    }

    @Override
    public String filterType() {
        return "pre";// 前置过滤器
    }


}
