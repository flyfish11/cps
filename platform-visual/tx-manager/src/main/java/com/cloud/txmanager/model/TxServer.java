package com.cloud.txmanager.model;

import lombok.Getter;
import lombok.Setter;

/**
 *@author LCN on 2017/7/1.
 */
@Setter
@Getter
public class TxServer {

	private String ip;
	private int port;
	private int heart;
	private int delay;
	private int compensateMaxWaitTime;

	public static TxServer format(TxState state) {
		TxServer txServer = new TxServer();
		txServer.setIp(state.getIp());
		txServer.setPort(state.getPort());
		txServer.setHeart(state.getTransactionNettyHeartTime());
		txServer.setDelay(state.getTransactionNettyDelayTime());
		txServer.setCompensateMaxWaitTime(state.getCompensateMaxWaitTime());
		return txServer;
	}

}
