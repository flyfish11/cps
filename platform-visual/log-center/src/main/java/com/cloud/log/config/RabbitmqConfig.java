package com.cloud.log.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloud.model.log.constants.LogQueue;

/**
 * rabbitmq配置
 * 
 * @author com.hlxd
 *
 */
@Configuration
public class RabbitmqConfig {

	/**
	 * 声明队列
	 * 
	 * @return
	 */
	@Bean
	public Queue logQueue() {
		return new Queue(LogQueue.LOG_QUEUE);
	}
}
