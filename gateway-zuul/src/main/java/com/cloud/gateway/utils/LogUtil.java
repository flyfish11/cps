package com.cloud.gateway.utils;

import com.cloud.gateway.feign.SysLogFeignClient;
import com.cloud.model.log.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class LogUtil {

    @Autowired
    private SysLogFeignClient logFeignClient;

    /**
     * 异步调用 FeignClient 记录日志
     *
     * @param sysLog
     */
    public void AsynSaveLog(SysLog sysLog) {
        CompletableFuture.runAsync(() -> {
            try {
                logFeignClient.save(sysLog);
            } catch (Exception e) {
                // do nothing
            }
        });
    }

}
