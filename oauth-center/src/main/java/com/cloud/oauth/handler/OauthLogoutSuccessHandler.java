package com.cloud.oauth.handler;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author fishfly
 */
@Slf4j
public class OauthLogoutSuccessHandler implements LogoutSuccessHandler
{
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Value("${defaultRedirectUri}")
    private String defaultRedirectUri ;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String redirectUri = request.getParameter("redirect_uri");
        if (StrUtil.isNotEmpty(redirectUri)) {
            //重定向指定的地址
            redirectStrategy.sendRedirect(request, response, redirectUri);
        } else {
            redirectStrategy.sendRedirect(request, response, defaultRedirectUri);
        }
    }
}
