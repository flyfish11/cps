package com.cloud.service.exception;

import com.cloud.common.enums.AbstractBaseExceptionEnum;

/**
 * 应用管理模块异常处理
 *
 * @author yulj
 * @create: 2019/05/08 16:55
 */
public class ServiceCenterException extends RuntimeException {

    private Integer code;
    private String errorMessage;

    public ServiceCenterException(Integer code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public ServiceCenterException(AbstractBaseExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
