package com.cloud.common.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname MenuTreeNode
 * @Description <h1>菜单树节点</h1>
 * @Author yulj
 * @Date: 2019/07/09 15:28
 */

@Data
public class MenuTreeNode {

    protected String id;

    protected String pId;

    protected List<MenuTreeNode> children = new ArrayList<MenuTreeNode>();

    public void add(MenuTreeNode menuTreeNode) {
        children.add(menuTreeNode);
    }
}
