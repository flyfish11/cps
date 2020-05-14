package com.cloud.oauth.feign;

import com.cloud.common.utils.R;
import com.cloud.model.user.bo.LdapUserBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloud.model.user.LoginAppUser;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@FeignClient(value = "user-center",fallback = UserClientFallback.class)
public interface UserClient {

    @GetMapping(value = "/user/queryByAccount", params = "account")
    R findByAccount(@RequestParam("account") String account);

    @GetMapping("/wechat/login-check")
    public void wechatLoginCheck(@RequestParam("tempCode") String tempCode, @RequestParam("openid") String openid);

    @PostMapping("/ldap/authenticate")
    public R authenticate( @RequestBody LdapUserBO ldapUserBO);
}
