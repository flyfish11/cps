package com.cloud.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "oauth-center", fallback = Oauth2ClientFallback.class)
public interface Oauth2Client {

    /**
     * 获取access_token
     *
     * @param parameters
     * @return
     */
    @PostMapping(path = "/oauth/token")
    Map<String, Object> postAccessToken(@RequestParam Map<String, Object> parameters);

    @PostMapping(path = "/adLogin")
    Map<String, Object> adLlogin(@RequestParam Map<String, Object> parameters);

    /**
     * 删除access_token和refresh_token
     *
     * @param token
     */
    @GetMapping(path = "/oauth/check_token")
    Map<String, Object> checkToken(@RequestParam("token") String token);

    /**
     * 删除access_token和refresh_token
     *
     * @param
     */
    @DeleteMapping(path = "/remove_token")
    void removeToken(@RequestParam("access_token") String accessToken);

}
