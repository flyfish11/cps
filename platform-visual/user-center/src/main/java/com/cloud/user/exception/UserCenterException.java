package com.cloud.user.exception;

/**
 * @Classname UserCenterException
 * @Description <h1>用户中心自定义异常</h1>
 * @Author yulj
 * @Date: 2020/04/21 9:21 下午
 */
public class UserCenterException extends RuntimeException {

    public UserCenterException(String message) {
        super(message);
    }

}
