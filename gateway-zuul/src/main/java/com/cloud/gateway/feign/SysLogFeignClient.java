package com.cloud.gateway.feign;

import com.cloud.common.utils.R;
import com.cloud.model.log.SysLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Classname SysLogFeignClient
 * @Description <h1>日志管理FeignClient</h1>
 * @Author yulj
 * @Date: 2020/04/15 12:03 上午
 */
@FeignClient("log-center")
public interface SysLogFeignClient {

    @RequestMapping(value = "/logs/add", method = RequestMethod.POST)
    R save(@RequestBody SysLog sysLog);

}
