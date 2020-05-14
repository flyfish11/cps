package com.cloud.gateway.feign;

import com.cloud.common.constants.CommonConstants;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Oauth2ClientFallback implements Oauth2Client {

    @Override
    public Map<String, Object> postAccessToken(Map<String, Object> parameters) {

        Map<String, Object> map = new HashMap();
        map.put("code", CommonConstants.FAIL);
        map.put("msg", "oauth-center feign 调用异常");
        map.put("data", null);

        return map;
    }

    @Override
    public Map<String, Object> adLlogin(Map<String, Object> parameters) {
        Map<String, Object> map = new HashMap();
        map.put("code", CommonConstants.FAIL);
        map.put("msg", "oauth-center feign 调用异常");
        map.put("data", null);
        return map;
    }

    @Override
    public Map<String, Object> checkToken(String Token) {
        Map<String, Object> map = new HashMap();
        map.put("code", CommonConstants.FAIL);
        map.put("msg", "oauth-center feign 调用异常");
        map.put("data", null);
        return map;
    }

    @Override
    public void removeToken(String accessToken) {

    }

}
