package com.cloud.gateway.feign;


import com.cloud.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("platform-user")
public interface UserClient
{
    @GetMapping(value = "/user/queryByAccount", params = "account")
    R findByAccount(@RequestParam("account") String account);
}
