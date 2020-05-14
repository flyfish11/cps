package com.cloud.common.enums;

/**
 * @Classname AppStatusEnum
 * @Description 工业App运行状态枚举
 * @Author yulj
 * @Date: 2019/12/23 11:13 上午
 */
public enum AppStatusEnum {

    NOT_RUNNING(0, "未运行"),
    RUNNING(1, "运行中");

    private Integer code;

    private String msg;

    AppStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
