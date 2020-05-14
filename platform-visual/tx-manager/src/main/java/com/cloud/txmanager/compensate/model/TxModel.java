package com.cloud.txmanager.compensate.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author LCN on 2017/11/12
 */
@Setter
@Getter
public class TxModel {

    private String time;

    private String className;

    private String method;

    private int executeTime;

    private String base64;

    private int state;

    private long order;

    private String key;

}
