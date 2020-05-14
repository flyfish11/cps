package com.cloud.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Classname TemplateEntityTree
 * @Description <h1>模板实体树</h1>
 * @Author yulj
 * @Date: 2020/02/24 5:46 下午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateTree extends TreeNodeString {

    private String label;

    private String name;

    private String nodeType;

}
