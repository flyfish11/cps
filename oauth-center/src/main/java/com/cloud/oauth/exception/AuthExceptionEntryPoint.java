package com.cloud.oauth.exception;

import com.cloud.common.utils.R;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * tokon 无效 和无权限 异常处理类
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {
        Throwable cause = authException.getCause();
        R result;
        if (cause instanceof InvalidTokenException) {
            result = R.failed("无效的token");
        } else {
            result = R.failed("访问此资源需要完全的身份验证");
        }
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), result);
        } catch (Exception e) {
            throw new ServletException();
        }
    }

}