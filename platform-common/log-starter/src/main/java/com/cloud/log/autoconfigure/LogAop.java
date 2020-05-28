package com.cloud.log.autoconfigure;

import cn.hutool.json.JSONUtil;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.SysLogUtils;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.SysLog;
import com.cloud.model.log.constants.LogQueue;
import com.cloud.model.user.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * aop实现日志
 *
 * @author com.hlxd
 */
@Aspect
@Slf4j
public class LogAop {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Around(value = "@annotation(com.cloud.model.log.LogAnnotation)")
    public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
        SysLog sysLog = SysLogUtils.getSysLog();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (loginAppUser != null) {
            sysLog.setCreateBy(loginAppUser.getUsername());
        }
        sysLog.setCreateTime(new Date());

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
        sysLog.setServiceId(logAnnotation.serviceId());
        sysLog.setTitle(logAnnotation.title());

        if (logAnnotation.recordParam()) {
            String[] paramNames = methodSignature.getParameterNames();// 参数名
            if (paramNames != null && paramNames.length > 0) {
                Object[] args = joinPoint.getArgs();// 参数值

                Map<String, Object> params = new HashMap<>();
                for (int i = 0; i < paramNames.length; i++) {
                    params.put(paramNames[i], args[i]);
                }

                try {
                    sysLog.setParams(JSONUtil.toJsonStr(params));
                } catch (Exception e) {
                    log.error("记录参数失败：{}", e.getMessage());
                }
            }
        }

        Long startTime = System.currentTimeMillis();
        try {
            // 发送异步日志事件，调用原方法
            Object object = joinPoint.proceed();
            Long endTime = System.currentTimeMillis();
            sysLog.setType(Boolean.FALSE);
            sysLog.setExecuteTime(String.valueOf(endTime - startTime));
            return object;
        } catch (Exception e) {
            Long endTime = System.currentTimeMillis();
            sysLog.setType(Boolean.TRUE);
            sysLog.setExecuteTime(String.valueOf(endTime - startTime));
            sysLog.setException(e.getMessage());
            throw e;
        } finally {
            // 异步将Log对象发送到队列
            CompletableFuture.runAsync(() -> {
                try {
                    amqpTemplate.convertAndSend(LogQueue.LOG_QUEUE, sysLog);
                    log.info("发送日志到队列：{}", sysLog);
                } catch (Exception e2) {
                    log.error(e2.getMessage());
                }
            });
        }
    }

}
