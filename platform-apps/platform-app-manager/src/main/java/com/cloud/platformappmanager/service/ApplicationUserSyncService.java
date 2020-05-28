package com.cloud.platformappmanager.service;

import com.cloud.common.utils.R;
import com.cloud.model.platformappmanager.bo.ApplicationUserSyncBO;
import com.cloud.model.platformappmanager.vo.ApplicationUserSyncVO;
import com.cloud.model.common.Page;

import java.util.Map;

/**
 * @Classname ApplicationUserSyncService
 * @Description <h1>应用用户同步服务层</h1>
 * @Author yulj
 * @Date: 2020/04/24 8:15 下午
 */
public interface ApplicationUserSyncService {

    /**
     * 销毁应用用户同步数据
     *
     * @param userId 用户id
     * @return
     */
    R removeApplicationUserSync(Integer userId);

    /**
     * <h2>应用用户同步数据分页查询</h2>
     *
     * @param params 查询条件Map
     * @return
     */
    Page<ApplicationUserSyncVO> pageList(Map<String, Object> params);

    /**
     * <h1>更新应用权限</h1>
     *
     * @param appAuthoritys
     * @return
     */
    R updateApplicationUserSync(ApplicationUserSyncBO... appAuthoritys);

}
