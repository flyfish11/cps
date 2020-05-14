package com.cloud.oauth.controller;

import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.utils.R;
import com.cloud.model.log.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping
@Api(tags = "token管理")
public class OAuth2Controller {

    @Autowired
    private TokenStore tokenStore;

    /**
     * 当前登陆用户信息<br>
     * <p>
     * security获取当前登录用户的方法是SecurityContextHolder.getContext().getAuthentication()<br>
     * 返回值是接口org.springframework.security.core.Authentication，又继承了Principal<br>
     * 这里的实现类是org.springframework.security.oauth2.provider.OAuth2Authentication<br>
     * <p>
     * 因此这只是一种写法，下面注释掉的三个方法也都一样，这四个方法任选其一即可，也只能选一个，毕竟uri相同，否则启动报错<br>
     * 2018.05.23改为默认用这个方法，好理解一点
     *
     * @return
     */
    @ApiIgnore
    @GetMapping("/user-me")
    public Authentication principal() {
        log.info("鉴权进入");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("user-me:{}", authentication.getName());
        return authentication;
    }

    /**
     * 移除access_token和refresh_token
     *
     * @param principal
     * @param access_token
     * @return
     */
    @ApiOperation(value = "移除access_token", notes = "移除access_token和refresh_token", response = R.class)
    @ApiImplicitParam(name = "access_token", value = "access_token", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "/remove/token")
    @LogAnnotation(serviceId = ServiceIdsConstants.OAUTH_CENTER, title = "移除access_token")
    public R removeToken(@ApiIgnore Principal principal, @RequestParam("access_token") String access_token) {
        if (StringUtils.isBlank(access_token)) {
            return R.failed("access_token不能为空！");
        }
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(access_token);
        if (oAuth2AccessToken == null) {
            return R.failed("不合法的access_token！");
        }
        // 移除access_token
        tokenStore.removeAccessToken(oAuth2AccessToken);
        // 移除refresh_token
        if (oAuth2AccessToken.getRefreshToken() != null) {
            tokenStore.removeRefreshToken(oAuth2AccessToken.getRefreshToken());
        }

        return R.ok(ResultEnum.TOKEN_REMOVE_SUCCESS.getMessage());
    }

}
