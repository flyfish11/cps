package com.cloud.common.enums;

/**
 * @Classname IsActive
 * @Description 政务微信用户是否激活枚举
 * @Author yulj
 * @Date: 2019/12/12 3:53 下午
 */
public enum IsActiveEnum {

    ACTIVED(1, "已激活"), UNACTIVE(0, "未激活");

    int code;
    String message;

    IsActiveEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (IsActiveEnum ms : IsActiveEnum.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
