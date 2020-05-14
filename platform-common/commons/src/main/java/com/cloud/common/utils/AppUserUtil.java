package com.cloud.common.utils;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.SysUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Map;

public class AppUserUtil {

    private AppUserUtil() {
        throw new IllegalStateException("工具类");
    }

    /**
     * 获取登陆的 LoginAppUser
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static LoginAppUser getLoginAppUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
            authentication = oAuth2Auth.getUserAuthentication();

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                Map map = (Map) authenticationToken.getDetails();
                map = (Map) map.get("principal");

                return JSONUtil.toBean(JSONUtil.parseObj(map), LoginAppUser.class);
            }
        }

        return null;
    }

    public static SysUser getSysUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
            authentication = oAuth2Auth.getUserAuthentication();

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                Map map = (Map) authenticationToken.getDetails();
                map = (Map) map.get("principal");

                return JSONUtil.toBean(JSONUtil.parseObj(map), SysUser.class);
            }
        }

        return null;
    }
}
