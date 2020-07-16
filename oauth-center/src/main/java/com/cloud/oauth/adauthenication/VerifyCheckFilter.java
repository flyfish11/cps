package com.cloud.oauth.adauthenication;

import com.cloud.oauth.feign.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class VerifyCheckFilter extends OncePerRequestFilter implements InitializingBean
{


    private Set<String> urls = new HashSet<>();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urls.add("/adLogin");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        AuthenticationFailureHandler adAuthenticationFailureHandler = new AdAuthenticationFailureHandler();

        // 当请求为登陆时 并且是post请求时 才进行过滤 进行密码校验

        if (StringUtils.equals("/adLogin", request.getRequestURI()) && StringUtils.equals(request.getMethod(), "POST")) {

            //validate(new ServletWebRequest(request));
            String code = request.getParameter("code");
            if (StringUtils.isBlank(code)) {
                adAuthenticationFailureHandler.onAuthenticationFailure(request, response, new AdAuthenticationException("验证码不能为空！"));
                return;
            }
            HttpSession session = request.getSession();
            Object sessionCode = session.getAttribute("code");
            if (!code.equalsIgnoreCase((String)sessionCode )) {
                 session.removeAttribute("code");
                adAuthenticationFailureHandler.onAuthenticationFailure(request, response, new AdAuthenticationException("验证码错误！"));
                return;
            }
            session.removeAttribute("code");

        }
        filterChain.doFilter(request, response);

    }


    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

}
