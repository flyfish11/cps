package com.cloud.txmanager.api.service.impl;

import com.cloud.txmanager.api.service.ApiModelService;
import com.cloud.txmanager.manager.ModelInfoManager;
import com.cloud.txmanager.model.ModelInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LCN on 2017/11/13
 * @author LCN
 */
@Service
public class ApiModelServiceImpl implements ApiModelService {

    @Override
    public List<ModelInfo> onlines() {
        return ModelInfoManager.getInstance().getOnlines();
    }

}
