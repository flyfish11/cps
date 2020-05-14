package com.cloud.log.consumer;

import com.cloud.log.service.SysLogService;
import com.cloud.model.log.SysLog;
import com.cloud.model.log.constants.LogQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消费日志
 *
 * @author com.hlxd
 */
@Component
@RabbitListener(queues = LogQueue.LOG_QUEUE) // 监听队列
@Slf4j
public class LogConsumer {

    @Resource
    private SysLogService sysLogService;

    /**
     * 处理消息
     *
     * @param sysLog
     */
    @RabbitHandler
    public void logHandler(SysLog sysLog) {
        try {
            sysLogService.save(sysLog);
        } catch (Exception e) {
            log.error("保存日志失败，日志：{}，异常：{}", sysLog, e);
        }
    }

}
