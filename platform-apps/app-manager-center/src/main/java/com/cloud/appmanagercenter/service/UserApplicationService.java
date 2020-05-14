package com.cloud.appmanagercenter.service;

import com.cloud.common.utils.R;

import java.util.Map;

/**
 * @Classname UserApplicationService
 * @Description <h1>用户应用表服务层</h1>
 * @Author yulj
 * @Date: 2020/04/24 9:23 上午
 */
public interface UserApplicationService {

    /**
     * <h2>我的应用</h2>
     *
     * @param params 查询条件
     * @return
     */
    R myApps(Map<String, Object> params);

    /**
     * <h2>校验应用用户</h2>
     *
     * @param appId   应用id
     * @param account 用户账号
     * @return
     */
    R checkAppUser(Integer appId, String account);

    /**
     * <h2>用户访问应用</h2>
     *
     * @param id 应用id
     * @return
     */
    R visitApp(Integer id);

    /**
     * <h2>查询用户最近访问应用</h2>
     *
     * @param appCounts 最近访问应用数
     * @return
     */
    R findRecentVisit(Integer appCounts);

    /**
     * <h1>清空用户最近访问应用</h1>
     *
     * @return
     */
    R clearRecentVisit();

    /**
     * 根据用户 id 删除用户应用关联数据
     *
     * @param userId
     * @return
     */
    R deleteByUserId(Integer userId);

}
