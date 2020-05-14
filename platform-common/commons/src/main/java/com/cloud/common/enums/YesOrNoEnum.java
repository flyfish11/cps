package com.cloud.common.enums;

/**
 * @Classname YesOrNoEnum
 * @Description <h1>是否枚举</h1>
 * @Author yulj
 * @Date: 2020/02/14 11:45 上午
 */
public enum YesOrNoEnum {

    YES(1, "是"),
    NO(0, "否");

    Integer type;
    String description;

    YesOrNoEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String valueOf(Integer type) {
        if (type == null) {
            return "";
        } else {
            for (YesOrNoEnum yesOrNoEnum : YesOrNoEnum.values()) {
                if (yesOrNoEnum.getType() == type) {
                    return yesOrNoEnum.getDescription();
                }
            }
            return "";
        }
    }
}
