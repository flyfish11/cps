package com.cloud.txmanager.manager.service;

import com.cloud.txmanager.model.TxServer;
import com.cloud.txmanager.model.TxState;

/**
 * @author LCN on 2017/11/11
 */
public interface MicroService {

    String TMKEY = "tx-manager";

    TxServer getServer();

    TxState getState();

}
