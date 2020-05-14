package com.cloud.appmanagercenter.component;

import com.cloud.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Classname GlobalExceptionHandlerResolver
 * @Description <h1>全局异常处理器</h1>
 * @Author yulj
 * @Date: 2020/04/09 3:45 下午
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerResolver {

    /**
     * 全局异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public R handleGlobalException(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return R.failed(e.getLocalizedMessage());
    }

}
