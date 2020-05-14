package com.cloud.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Classname AgentGroupTree
 * @Description 政务微信应用树
 * @Author yulj
 * @Date: 2019/12/25 2:10 下午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AgentGroupTree extends TreeNode{

    private String label;

    private Integer nodeLevel;

    private Boolean disabled = Boolean.FALSE;
}
