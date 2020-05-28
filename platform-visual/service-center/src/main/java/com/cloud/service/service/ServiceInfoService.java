package com.cloud.service.service;

import com.cloud.model.platformappmanager.ServiceInfo;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.service.dto.ServiceInfoDto;

import java.io.IOException;
import java.util.Map;

/**
 * 服务数据接口
 *
 * @author yulj
 * @create: 2019/05/09 13:34
 */
public interface ServiceInfoService {

    /**
     * 查询服务列表数据
     *
     * @param params
     * @return
     */
    Page<ServiceInfoDto> list(Map<String, Object> params);

    /**
     * 新增服务信息
     *
     * @param serviceInfo
     * @return
     */
    Result add(ServiceInfo serviceInfo);

    /**
     * 新增服务信息
     *
     * @param serviceInfo
     * @return
     */
    Result update(ServiceInfo serviceInfo);

    /**
     * 通过id查询单条数据
     *
     * @param id
     * @return
     */
    ServiceInfo findById(Integer id);

    /**
     * 通过id更新jenkinlogs
     *
     * @param serviceInfo
     * @return
     */
    int updateById(ServiceInfo serviceInfo);

    /**
     * 根据服务id删除服务
     *
     * @param id 服务id
     * @return
     * @throws IOException 服务调用httpclient可能抛出 IOException
     */
    Result delete(Integer id) throws Exception;

}
