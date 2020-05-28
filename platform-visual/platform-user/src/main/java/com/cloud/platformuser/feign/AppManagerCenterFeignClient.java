package com.cloud.platformuser.feign;

import com.cloud.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Classname AppManagerFeignClient
 * @Description 应用服务FeignClient
 * @Author yulj
 * @Date: 2019/07/24 16:03
 */
@FeignClient("platform-app-manager")
public interface AppManagerCenterFeignClient {

    @RequestMapping(value = "/application/load", method = RequestMethod.GET)
    R load(@RequestParam String id);

    @RequestMapping(value = "/userApplication/deleteByUserId", method = RequestMethod.POST)
    R deleteByUserId(@RequestParam Integer userId);

    @RequestMapping(value = "/applicationUserSync/removeByUserId", method = RequestMethod.POST)
    R removeByUserId(@RequestParam Integer userId);

}
