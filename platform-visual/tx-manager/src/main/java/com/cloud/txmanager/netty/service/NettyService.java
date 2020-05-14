package com.cloud.txmanager.netty.service;

/**
 * @author LCN on 2017/11/11
 */
public interface NettyService {

    IActionService getActionService(String action);

}
