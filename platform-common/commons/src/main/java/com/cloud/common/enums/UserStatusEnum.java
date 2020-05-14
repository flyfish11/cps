package com.cloud.common.enums;

/**
 * @Classname UserStatus
 * @Description 系统用户状态枚举
 * @Author yulj
 * @Date: 2019/12/12 4:04 下午
 */
public enum UserStatusEnum {
    OK(1, "启用"),
    FREEZED(0, "禁用");

    int code;
    String message;

    UserStatusEnum(int code, String message) {
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
            for (UserStatusEnum us : UserStatusEnum.values()) {
                if (us.getCode() == value) {
                    return us.getMessage();
                }
            }
            return "";
        }
    }
}
