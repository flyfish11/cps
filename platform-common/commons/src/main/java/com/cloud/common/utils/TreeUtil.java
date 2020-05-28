package com.cloud.common.utils;

import com.cloud.common.vo.MenuTree;
import com.cloud.common.vo.MenuTreeNode;
import com.cloud.common.vo.TreeNode;
import com.cloud.common.vo.TreeNodeString;
import com.cloud.model.platformuser.SysDept;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Classname TreeUtil
 * @Description tree工具类
 * @Author yulj
 * @Date: 2019/07/04 17:17
 */
@UtilityClass
public class TreeUtil
{

    /**
     * 两层循环实现建树
     *
     * @param treeNodes 传入的树节点列表
     * @return
     */
    public <T extends TreeNode> List<T> build(List<T> treeNodes, Set<Integer> roots) {

        List<T> trees = new ArrayList<>();

        for (T treeNode : treeNodes) {

            if (roots.contains(treeNode.getParentId())) {
                trees.add(treeNode);
            }

            for (T it : treeNodes) {
                if (it.getParentId() == treeNode.getId()) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 两层循环实现建树
     *
     * @param treeNodeStrings 传入的树节点列表
     * @return
     */
    public <T extends TreeNodeString> List<T> stringTreeBuild(List<T> treeNodeStrings, Set<String> roots) {

        List<T> trees = new ArrayList<>();

        for (T treeNodeString : treeNodeStrings) {

            if (roots.contains(treeNodeString.getParentId())) {
                trees.add(treeNodeString);
            }

            for (T it : treeNodeStrings) {
                if (it.getParentId().equals(treeNodeString.getId())) {
                    if (treeNodeString.getChildren() == null) {
                        treeNodeString.setChildren(new ArrayList<>());
                    }
                    treeNodeString.add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树(id、parentId为字符串)
     *
     * @param treeNodeNews
     * @return
     */
    public <T extends TreeNodeString> List<T> buildByRecursive(List<T> treeNodeNews, String root) {
        List<T> trees = new ArrayList<>();
        for (T treeNode : treeNodeNews) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodeNews));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId() == it.getParentId()) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    /**
     * 递归查找子节点(id、parentId为字符串)
     *
     * @param treeNodeStrings
     * @return
     */
    public <T extends TreeNodeString> T findChildren(T treeNodeString, List<T> treeNodeStrings) {
        for (T it : treeNodeStrings) {
            if (treeNodeString.getId().equals(it.getParentId())) {
                if (treeNodeString.getChildren() == null) {
                    treeNodeString.setChildren(new ArrayList<>());
                }
                treeNodeString.add(findChildren(it, treeNodeStrings));
            }
        }
        return treeNodeString;
    }

    /**
     * 构建菜单树
     *
     * @param menuTreeNodes
     * @param <T>
     * @return
     */
    public <T extends MenuTreeNode> List<T> menuTree(List<T> menuTreeNodes, Set<String> ids) {

        List<T> trees = new ArrayList<>();

        for (T menuTreeNode : menuTreeNodes) {

            if (ids.contains(menuTreeNode.getPId())) {
                trees.add(menuTreeNode);
            }

            for (T it : menuTreeNodes) {
                if (it.getPId().equals(menuTreeNode.getId())) {
                    if (menuTreeNode.getChildren() == null) {
                        menuTreeNode.setChildren(new ArrayList<>());
                    }
                    menuTreeNode.add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 构建菜单树
     *
     * @param menuTreeNodes
     * @param <T>
     * @return
     */
    public <T extends MenuTreeNode> List<T> menuTreeNew(List<T> menuTreeNodes, Set<String> ids) {

        List<T> trees = new ArrayList<>();

        for (T menuTreeNode : menuTreeNodes) {

            if (ids.contains(menuTreeNode.getId())) {
                trees.add(menuTreeNode);
            }

            for (T it : menuTreeNodes) {
                if (it.getPId().equals(menuTreeNode.getId())) {
                    if (menuTreeNode.getChildren() == null) {
                        menuTreeNode.setChildren(new ArrayList<>());
                    }
                    menuTreeNode.add(it);
                }
            }
        }
        return trees;
    }

    public void setChild(MenuTree menu, List<MenuTree> menus) {
        List<MenuTreeNode> child = menus.stream().filter(m -> m.getPId().equals(menu.getId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(child)) {
            menu.setChildren(child);child.forEach(m -> setChild((MenuTree) m, menus));

        }
    }

    public void setChild(SysDept dept, List<SysDept> depts) {
        List<SysDept> child = depts.stream().filter(m -> m.getPid().intValue() == dept.getId().intValue()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(child)) {
            dept.setChildren(child);
            child.forEach(m -> setChild(m, depts));

        }
    }

}