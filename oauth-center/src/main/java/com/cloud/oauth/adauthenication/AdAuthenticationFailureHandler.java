package com.cloud.oauth.adauthenication;

import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fishfly
 * 集成授权码模式后不需要了
 * 使用系统默认处理
 */
@Component("adAuthenticationFailureHandler")
public class AdAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler
{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        Result error = ResultUtil.error(e.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(error));

    }
}
