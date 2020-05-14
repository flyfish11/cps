package com.cloud.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Classname DeptTree
 * @Description 部门树
 * @Author yulj
 * @Date: 2019/07/04 17:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends TreeNode {

    private String label;

}
