package com.cloud.platformuser.service;

import com.cloud.common.utils.R;
import com.cloud.common.vo.DeptTree;
import com.cloud.model.platformuser.SysDept;
import com.cloud.model.platformuser.bo.SysDeptAddBO;
import com.cloud.model.platformuser.bo.SysDeptUpdateBO;

import java.util.List;
import java.util.Map;

/**
 * 部门服务
 *
 * @author yulj
 * @create: 2019/05/06 14:41
 */
public interface DeptService {

    /**
     * 获取树形列表
     */
    List<SysDept> treeTable(Map<String, Object> param);

    /**
     * 根据部门id查询部门名称
     *
     * @param id
     * @return
     */
    SysDept load(Integer id);

    /**
     * 根据部门ids查询部门名称
     *
     * @param deptIds
     * @return
     */
    String getDeptNames(Integer... deptIds);

    /**
     * 查询部门树
     *
     * @param deptName 部门名称
     * @return
     */
    List<DeptTree> elementTree(String deptName);

    /**
     * 查询部门人员树菜单
     *
     * @return 树
     */
    List<DeptTree> selectUserTree();

    /**
     * 查询部门人员树菜单(在应用可见范围部门内)
     *
     * @param applicationId
     * @return
     */
    R selectUserTreeInRange(String applicationId);

    /**
     * 新增数据
     *
     * @param sysDeptAddBO 部门新增业务对象
     * @return 实例对象
     */
    R insert(SysDeptAddBO sysDeptAddBO);

    /**
     * 修改数据
     *
     * @param sysDeptUpdateBO 部门修改业务对象
     * @return 实例对象
     */
    R update(SysDeptUpdateBO sysDeptUpdateBO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    R deleteById(Integer id);

    /**
     * 缓存所有用户数据
     *
     * @return
     */
    int cacheAllDeptData();

}
