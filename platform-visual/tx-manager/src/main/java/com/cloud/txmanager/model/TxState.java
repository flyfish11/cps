package com.cloud.txmanager.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *@author LCN on 2017/7/1.
 */
@Setter
@Getter
public class TxState {

	/**
	 * socket ip
	 */
	private String ip;
	/**
	 * socket port
	 */
	private int port;

	/**
	 * max connection
	 */
	private int maxConnection;

	/**
	 * now connection
	 */
	private int nowConnection;


	/**
	 * transaction_netty_heart_time
	 */
	private int transactionNettyHeartTime;

	/**
	 * transaction_netty_delay_time
	 */
	private int transactionNettyDelayTime;


	/**
	 * redis_save_max_time
	 */
	private int redisSaveMaxTime;


	/**
	 * 回调地址
	 */
	private String notifyUrl;

	/**
	 * 自动补偿
	 */
	private boolean isCompensate;

	/**
	 * 补偿尝试时间
	 */
	private int compensateTryTime;

	/**
	 * slb list
	 */
	private List<String> slbList;

	/**
	 * 自动补偿间隔时间
	 */
	private int compensateMaxWaitTime;

}
