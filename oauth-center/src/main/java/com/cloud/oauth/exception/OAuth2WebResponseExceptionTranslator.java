package com.cloud.oauth.exception;

import com.cloud.common.enums.ResultEnum;
import com.cloud.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname OAuth2WebResponseExceptionTranslator
 * @Description 自定义oauth2异常提示
 * @Author yulj
 * @Date: 2020/04/07 04:39 下午
 */
@Slf4j
public class OAuth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    public static final String ACCESS_TOKEN_URI = "/oauth/token";

    public static final String INVALIDGRANTEXCEPTION = "InvalidGrantException";

    @Override
    public ResponseEntity translate(Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        log.info("request uri={}", requestURI);
        String className = e.getClass().getName();
        String message = e.getMessage();
        String errorMsg = e.getMessage();
        if (requestURI.equals(ACCESS_TOKEN_URI) && className.contains(INVALIDGRANTEXCEPTION)) {
            if ("Bad credentials".contains(message)) {
                errorMsg = ResultEnum.BAD_CREDENTIALS.getMessage();
            } else if ("User is disabled".contains(message)) {
                errorMsg = ResultEnum.ACCOUNT_DISABLED.getMessage();
            } else if ("User account is locked".contains(message)) {
                errorMsg = ResultEnum.ACCOUNT_LOCKED.getMessage();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(R.failed(errorMsg));
    }

}
