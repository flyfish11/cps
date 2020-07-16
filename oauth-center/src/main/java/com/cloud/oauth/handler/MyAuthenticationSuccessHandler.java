package com.cloud.oauth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功的处理器
 *
 * @author fishfly
 */
@Component("myAuthenticationSuccessHandler")
@Slf4j
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Value("${defaultRedirectUri}")
    private String defaultRedirectUri ;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException, ServletException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        //其他服务跳转登录，不存在回调地址，直接跳转微服务主页面
        if (savedRequest == null) {
            redirectStrategy.sendRedirect(request, response, defaultRedirectUri);
        }
        else {
            super.onAuthenticationSuccess(request, response, authentication);
        }


    }
}
