package com.cloud.txmanager.compensate.model;

import com.cloud.txmanager.netty.model.TxGroup;
import lombok.Getter;
import lombok.Setter;

/**
 * @author LCN on 2017/11/11
 */
@Setter
@Getter
public class TransactionCompensateMsg {

    private long currentTime;

    private String groupId;

    private String model;

    private String address;

    private String uniqueKey;

    private String className;

    private String methodStr;

    private String data;

    private int time;

    private int startError;

    private TxGroup txGroup;

    private int state;

    public TransactionCompensateMsg(long currentTime, String groupId, String model, String address,
                                    String uniqueKey, String className,
                                    String methodStr, String data, int time, int state, int startError) {
        this.currentTime = currentTime;
        this.groupId = groupId;
        this.model = model;
        this.uniqueKey = uniqueKey;
        this.className = className;
        this.methodStr = methodStr;
        this.data = data;
        this.time = time;
        this.address = address;
        this.state = state;
        this.startError = startError;
    }

}
