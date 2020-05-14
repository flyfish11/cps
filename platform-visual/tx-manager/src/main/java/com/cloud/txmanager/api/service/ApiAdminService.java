package com.cloud.txmanager.api.service;

import com.cloud.txmanager.compensate.model.TxModel;
import com.cloud.txmanager.model.ModelName;
import com.cloud.txmanager.model.TxState;
import com.lorne.core.framework.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * @Classname ApiAdminService
 * @Description
 * @Author yulj
 * @Date: 2020/04/08 10:51 下午
 */
public interface ApiAdminService {

    TxState getState();

    /**
     * k/v 获取 值封装成map
     *
     * @return
     */
    List<Map<String, Object>> getMapState();

    String loadNotifyJson();

    List<ModelName> modelList();


    List<String> modelTimes(String model);

    List<TxModel> modelInfos(String path);

    boolean compensate(String path) throws ServiceException;

    boolean hasCompensate();

    boolean delCompensate(String path);

}
