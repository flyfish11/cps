package com.cloud.txmanager.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 负载均衡模块信息
 *
 * @author LCN on 2017/12/5
 */
@Setter
@Getter
public class LoadBalanceInfo {

    private String groupId;

    private String key;

    private String data;

}
