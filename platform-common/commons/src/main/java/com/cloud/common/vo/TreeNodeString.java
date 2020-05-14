package com.cloud.common.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname TreeNodeString
 * @Description <h1>通用树节点</h1>
 * @Author yulj
 * @Date: 2020/02/24 9:33 下午
 */
@Data
public class TreeNodeString {

    protected String id;

    protected String parentId;

    protected List<TreeNodeString> children = new ArrayList<>();

    public void add(TreeNodeString node) {
        children.add(node);
    }

}