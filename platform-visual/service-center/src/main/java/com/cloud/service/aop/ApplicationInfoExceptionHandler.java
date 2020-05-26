package com.cloud.service.aop;

import com.cloud.service.exception.ServiceCenterException;
import com.cloud.service.vo.ErrorResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 应用管理模块的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author yulj
 * @create: 2019/05/18 17:15
 */
@ControllerAdvice
@Order(-1)
@Slf4j
public class ApplicationInfoExceptionHandler {

    /**
     * 拦截AppManageException
     */
    @ExceptionHandler(ServiceCenterException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData applicationInfo(ServiceCenterException e) {
        log.error("服务中心异常:", e);
        return new ErrorResponseData(e.getCode(), e.getMessage());
    }
}
