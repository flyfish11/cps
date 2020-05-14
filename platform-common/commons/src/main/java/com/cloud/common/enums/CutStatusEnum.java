package com.cloud.common.enums;

/**
 * @Classname CutStatusEnum
 * @Description <h1>截取规则状态</h1>
 * @Author yulj
 * @Date: 2020/02/27 10:27 上午
 */
public enum CutStatusEnum {

    USING(1, "在用"),
    EXPIRED(0, "过期");

    int code;
    String message;

    CutStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (CutStatusEnum cs : CutStatusEnum.values()) {
                if (cs.getCode() == value) {
                    return cs.getMessage();
                }
            }
            return "";
        }
    }

}