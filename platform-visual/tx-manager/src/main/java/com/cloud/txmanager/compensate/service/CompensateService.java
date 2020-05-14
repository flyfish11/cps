package com.cloud.txmanager.compensate.service;

import com.cloud.txmanager.compensate.model.TransactionCompensateMsg;
import com.cloud.txmanager.compensate.model.TxModel;
import com.cloud.txmanager.model.ModelName;
import com.cloud.txmanager.netty.model.TxGroup;
import com.lorne.core.framework.exception.ServiceException;

import java.util.List;

/**
 * @author LCN on 2017/11/11
 */
public interface CompensateService {

    boolean saveCompensateMsg(TransactionCompensateMsg transactionCompensateMsg);

    List<ModelName> loadModelList();

    List<String> loadCompensateTimes(String model);

    List<TxModel> loadCompensateByModelAndTime(String path);

    void autoCompensate(String compensateKey, TransactionCompensateMsg transactionCompensateMsg);

    boolean executeCompensate(String key) throws ServiceException;

    void reloadCompensate(TxGroup txGroup);

    boolean hasCompensate();

    boolean delCompensate(String path);

    TxGroup getCompensateByGroupId(String groupId);

}
