package com.cloud.appmanagercenter.service;

import com.cloud.common.utils.R;
import com.cloud.model.appmanagercenter.Application;
import com.cloud.model.appmanagercenter.bo.AppAdminBO;
import com.cloud.model.appmanagercenter.bo.AppUsePersonBO;
import com.cloud.model.appmanagercenter.bo.ApplicationAddBO;
import com.cloud.model.appmanagercenter.bo.ApplicationUpdateBO;
import com.cloud.model.appmanagercenter.vo.AppClassificationVO;
import com.cloud.model.appmanagercenter.vo.ApplicationDetailVO;
import com.cloud.model.common.Page;

import java.util.List;
import java.util.Map;

/**
 * @Classname ApplicationService
 * @Description <h1>应用表服务层</h1>
 * @Author yulj
 * @Date: 2020/04/22 8:35 下午
 */
public interface ApplicationService {

    /**
     * <h2>分页查询应用分页数据</h2>
     *
     * @param params 查询条件Map
     * @return
     */
    Page<ApplicationDetailVO> list(Map<String, Object> params);

    /**
     * <h2>新增应用表数据</h2>
     *
     * @param applicationAddBO 应用新增数据对象
     * @return
     */
    R add(ApplicationAddBO applicationAddBO);

    /**
     * <h2>修改应用表数据</h2>
     *
     * @param applicationUpdateBO 应用更新数据对象
     * @return
     */
    R update(ApplicationUpdateBO applicationUpdateBO);

    /**
     * <h2>删除应用表数据</h2>
     *
     * @param id 应用id
     * @return
     */
    R delete(Integer id);

    /**
     * <h2>根据应用id查询应用信息</h2>
     *
     * @param id 应用id
     * @return
     */
    R load(Integer id);

    /**
     * <h2>分级授权分页查询</h2>
     *
     * @param params 查询条件Map
     * @return
     */
    Page<ApplicationDetailVO> gradeAuthorizeList(Map<String, Object> params);

    /**
     * 修改应用管理员
     *
     * @param appAdminBO
     * @return
     */
    R updateAppAdmin(AppAdminBO appAdminBO);

    /**
     * <h1>更新应用使用人员</h1>
     *
     * @param appUsePersonBO 应用使用人员业务对象
     * @return
     */
    R setAppUsePerson(AppUsePersonBO appUsePersonBO);

    /**
     * 根据应用id查询应用信息
     *
     * @param id 应用id
     * @return
     */
    Application loadById(Integer id);

    /**
     * <h2>首页导航栏应用列表</h2>
     *
     * @param appClassification 应用分类
     * @return
     */
    R navigationBarApps(Integer appClassification);

    /**
     * <h2>首页平台/应用列表</h2>
     *
     * @return
     */
    R indexList();

    /**
     * <h2>首页应用详情</h2>
     *
     * @param appId 应用id
     * @return
     */
    R indexLoad(Integer appId);

    /**
     * <h2>首页应用统计</h2>
     *
     * @return
     */
    R statistics();

    /**
     * <h2>应用分类查询</h2>
     *
     * @return
     */
    List<AppClassificationVO> getAppClassification();

    /**
     * 根据应用类别查询应用列表
     *
     * @param appType
     * @return
     */
    R listByType(String appType);

}
