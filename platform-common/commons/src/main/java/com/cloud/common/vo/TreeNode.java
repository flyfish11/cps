package com.cloud.common.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname TreeNode
 * @Description <h1>通用树节点</h1>
 * @Author yulj
 * @Date: 2019/07/04 17:12
 */

@Data
public class TreeNode {

    protected int id;

    protected int parentId;

    protected List<TreeNode> children = new ArrayList<TreeNode>();

    public void add(TreeNode node) {
        children.add(node);
    }

}