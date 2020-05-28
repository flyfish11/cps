package com.cloud.oauth.feign;

import com.cloud.common.utils.R;
import com.cloud.model.platformuser.bo.LdapUserBO;

public class UserClientFallback implements UserClient
{
    @Override
    public R findByAccount(String account) {
        return R.failed("user-client feign 调用异常");
    }

    @Override
    public void wechatLoginCheck(String tempCode, String openid) {

    }

    @Override
    public R authenticate(LdapUserBO ldapUserBO) {
        return R.failed("user-client feign 调用异常");
    }
}
