package com.cloud.common.enums;

/**
 * @Classname AppTypeEnum
 * @Description 工业应用类型枚举
 * @Author yulj
 * @Date: 2019/07/23 15:47
 */
public enum AppTypeEnum {
    APP_BASE_IN_CPS(1, "基于工业互联网架构的工业应用"),
    APP_NORMAL(2, "非工业互联网架构的工业应用"),
    APP_VIRTUAL(3, "虚拟工业应用"),
    APP_MOBILE(4, "移动应用");

    private Integer code;

    private String msg;

    AppTypeEnum(Integer code, String msg) {
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
