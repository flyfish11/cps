package com.cloud.oauth.adauthenication;

import com.cloud.common.constants.CommonConstants;
import com.cloud.common.utils.R;
import com.cloud.model.platformuser.bo.LdapUserBO;
import com.cloud.oauth.feign.UserClient;
import com.cloud.oauth.oauth2.LoginAttemptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class AdCheckFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private AuthenticationFailureHandler adAuthenticationFailureHandler;
    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private UserClient userClient;

    private Set<String> urls = new HashSet<>();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urls.add("/adLogin");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 当请求为登陆时 并且是post请求时 才进行过滤 进行密码校验
        if (StringUtils.equals("/adLogin", request.getRequestURI()) && StringUtils.equals(request.getMethod(), "POST")) {
            try {

                //validate(new ServletWebRequest(request));
                String account = request.getParameter("account");
                String password = request.getParameter("password");
//
//                if (loginAttemptService.isBlocked(account)){
//                    loginAttemptService.loginSucceeded(account);
//                    adAuthenticationFailureHandler.onAuthenticationFailure(request, response, new AdAuthenticationException("用户已被锁定"));
//                    return;
//                }
                LdapUserBO ldapUser = LdapUserBO.builder().account(account).password(password).build();
                R authenticate = userClient.authenticate(ldapUser);
                if (authenticate.getCode() != CommonConstants.SUCCESS) {
                    adAuthenticationFailureHandler.onAuthenticationFailure(request, response, new AdAuthenticationException(authenticate.getMsg()));
                    return;
                }
            } catch (Exception e) {
                log.error("【LDAP登录异常】{}", e.getMessage());
                adAuthenticationFailureHandler.onAuthenticationFailure(request, response, new AdAuthenticationException("LDAP连接失败，请检验LDAP服务是否正常！"));
                return;
            }
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
