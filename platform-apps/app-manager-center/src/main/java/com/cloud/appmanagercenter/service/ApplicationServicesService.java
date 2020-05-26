package com.cloud.appmanagercenter.service;

import com.cloud.common.utils.R;
import com.cloud.model.appmanagercenter.ApplicationServices;
import com.cloud.model.common.Page;

import java.util.Map;

/**
 * @Classname ApplicationServicesService
 * @Description 应用服务接口层
 * @Author yulj
 * @Date: 2019/07/04 09:09
 */
public interface ApplicationServicesService {

    /**
     * 新增应用服务关联数据
     *
     * @param applicationServices 应用服务关联数据对象
     * @return
     */
    R save(ApplicationServices applicationServices);

    /**
     * 更新应用服务关联数据
     *
     * @param applicationServices 应用服务关联数据对象
     * @return
     */
    R update(ApplicationServices applicationServices);

    /**
     * 根据id查询应用服务关联记录
     *
     * @param id 主键id
     * @return
     */
    ApplicationServices findById(Integer id);

    /**
     * 删除应用服务关联数据
     *
     * @param id
     * @return 主键id
     */
    R delete(Integer id);

    /**
     * 查询应用服务关联记录
     *
     * @param params 查询条件Map
     * @return
     */
    Page<ApplicationServices> listBySelective(Map<String, Object> params);

}
