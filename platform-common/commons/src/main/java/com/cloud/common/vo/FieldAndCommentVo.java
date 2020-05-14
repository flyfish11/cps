package com.cloud.common.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Classname FieldAndCommentVo
 * @Description 实体属性及属性描述对应关系
 * @Author yulj
 * @Date: 2019/08/17 11:11
 */
@Data
@Builder
public class FieldAndCommentVo {

    /**
     * 属性描述（中文名称）
     */
    private String label;

    /**
     * 属性名
     */
    private String prop;

    /**
     * 是否为时间字段
     */
    private Boolean isDateField;
}
