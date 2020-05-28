package com.cloud.oauth.feign;

import com.cloud.common.utils.R;
import com.cloud.model.platformuser.bo.LdapUserBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "platform-user",fallback = UserClientFallback.class)
public interface UserClient {

    @GetMapping(value = "/user/queryByAccount", params = "account")
    R findByAccount(@RequestParam("account") String account);

    @GetMapping("/wechat/login-check")
    public void wechatLoginCheck(@RequestParam("tempCode") String tempCode, @RequestParam("openid") String openid);

    @PostMapping("/ldap/authenticate")
    public R authenticate( @RequestBody LdapUserBO ldapUserBO);
}
