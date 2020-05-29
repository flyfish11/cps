package com.cloud.gateway.controller;

import com.cloud.common.constants.CommonConstants;
import com.cloud.common.utils.R;
import com.cloud.common.utils.SysLogUtils;
import com.cloud.gateway.feign.Oauth2Client;
import com.cloud.gateway.feign.UserClient;
import com.cloud.gateway.utils.LogUtil;
import com.cloud.model.log.SysLog;
import com.cloud.model.platformuser.constants.CredentialType;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 登陆、刷新token、退出
 *
 * @author com.hlxd
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class TokenController {

    @Value("${isUniqueLogin}")
    private Boolean isUniqueLogin;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String CLIENT_ID_VALUE = "system";
    private static final String GRANT_TYPE_VALUE = "password";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String CLIENT_SECRET_VALUE = "system";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";

    @Autowired
    private Oauth2Client oauth2Client;

    @Autowired
    private UserClient userClient;

    @Autowired
    private LogUtil logUtil;

    /**
     * 系统登陆<br>
     * 根据用户名登录<br>
     * 采用oauth2密码模式获取access_token和refresh_token
     *
     * @param account
     * @param password
     * @return
     */
    @PostMapping("/sys/login")
    public Map<String, Object> login(String account, String password, String ip) {

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        Map<String, String[]> parameterMap = request.getParameterMap();

        Long startTime = System.currentTimeMillis();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, GRANT_TYPE_VALUE);
        parameters.put(OAuth2Utils.CLIENT_ID, CLIENT_ID_VALUE);
        parameters.put(CLIENT_SECRET, CLIENT_SECRET_VALUE);
        parameters.put(OAuth2Utils.SCOPE, "app");
        parameters.put(USERNAME, account + "|" + CredentialType.USERNAME.name());
        parameters.put(PASSWORD, password);
        Map<String, Object> tokenInfo = null;
        tokenInfo = oauth2Client.postAccessToken(parameters);

        Object expires_in = tokenInfo.get("expires_in");
        Object access_token = tokenInfo.get("access_token");
        if (expires_in != null && access_token != null) {
            long maxAge = Long.parseLong(String.valueOf(expires_in));
            redisTemplate.opsForValue().set(ip, access_token.toString(), maxAge, TimeUnit.SECONDS);
        }
        log.info("用户登录成功，用户名为 : {}", account);
        // 记录登录日志
        SysLog sysLog = buildLogData(account, "oauth-center", "用户名密码登录", startTime);
        logUtil.AsynSaveLog(sysLog);
        return tokenInfo;
    }

    @PostMapping("/sys/adLogin")
    public Map<String, Object> adLogin(String account, String password, String ip) {
        Map<String, Object> parameters = new HashMap<>();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            parameters.put("code", CommonConstants.FAIL);
            parameters.put("data", null);
            parameters.put("msg", "用户名或密码不能为空!");
            return parameters;
        }
        if (StringUtils.isBlank(ip)) {
            parameters.put("code", CommonConstants.FAIL);
            parameters.put("data", null);
            parameters.put("msg", "ip不能为空！");
            return parameters;
        }
        Long startTime = System.currentTimeMillis();
        parameters.put("account", account);
        parameters.put(PASSWORD, password);
        Map<String, Object> tokenInfo = oauth2Client.adLlogin(parameters);

        Object expires_in = tokenInfo.get("expires_in");
        Object access_token = tokenInfo.get("access_token");
        if (expires_in != null && access_token != null) {
            long maxAge = Long.parseLong(String.valueOf(expires_in));
            redisTemplate.opsForValue().set(CommonConstants.CACHE_TOKEN + ip, access_token.toString(), maxAge, TimeUnit.SECONDS);
        }
        // 记录登录日志
        SysLog sysLog = buildLogData(account, "oauth-center", "ad域认证登录", startTime);
        logUtil.AsynSaveLog(sysLog);
        return tokenInfo;
    }


    /**
     * 短信登录
     *
     * @param phone
     * @param key
     * @param code
     * @return
     */
    @PostMapping("/sys/login-sms")
    public Map<String, Object> smsLogin(String phone, String key, String code) {
        Long startTime = System.currentTimeMillis();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, GRANT_TYPE_VALUE);
        parameters.put(OAuth2Utils.CLIENT_ID, CLIENT_ID_VALUE);
        parameters.put(CLIENT_SECRET, CLIENT_SECRET_VALUE);
        parameters.put(OAuth2Utils.SCOPE, "app");
        // 为了支持多类型登录，这里在username后拼装上登录类型，同时为了校验短信验证码，我们也拼上code等
        parameters.put(USERNAME, phone + "|" + CredentialType.PHONE.name() + "|" + key + "|" + code + "|" + DigestUtils.md5Hex(key + code));
        // 短信登录无需密码，但security底层有密码校验，我们这里将手机号作为密码，认证中心采用同样规则即可
        parameters.put(PASSWORD, phone);
        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);

        // 记录登录日志
        SysLog sysLog = buildLogData(phone, "oauth-center", "手机号短信登录", startTime);
        logUtil.AsynSaveLog(sysLog);
        return tokenInfo;
    }

    /**
     * 检查token是否有效
     *
     * @param token
     * @return
     */
    @GetMapping("/checkToken")
    public R checkToken(@RequestParam String token) {
        R result = null;
        if (StringUtils.isBlank(token)) {
            result = R.failed("toke不能为空！");
        }
        Map<String, Object> map = oauth2Client.checkToken(token);
        Object code = map.get("code");

        if (code instanceof Integer && (Integer) code == 1) {
            result = R.ok(false, "token已失效!");
        } else {
            result = R.ok(map.get("active"), "token有效！");
        }
        return result;
    }

    /**
     * 微信登录
     *
     * @return
     */
    @PostMapping("/sys/login-wechat")
    public Map<String, Object> smsLogin(String openid, String tempCode) {
        Long startTime = System.currentTimeMillis();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, GRANT_TYPE_VALUE);
        parameters.put(OAuth2Utils.CLIENT_ID, CLIENT_ID_VALUE);
        parameters.put(CLIENT_SECRET, CLIENT_SECRET_VALUE);
        parameters.put(OAuth2Utils.SCOPE, "app");
        // 为了支持多类型登录，这里在username后拼装上登录类型，同时为了服务端校验，我们也拼上tempCode
        parameters.put(USERNAME, openid + "|" + CredentialType.WECHAT_OPENID.name() + "|" + tempCode);
        // 微信登录无需密码，但security底层有密码校验，我们这里将手机号作为密码，认证中心采用同样规则即可
        parameters.put(PASSWORD, tempCode);
        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);
        // 记录登录日志
        SysLog sysLog = buildLogData(openid, "oauth-center", "微信登录", startTime);
        logUtil.AsynSaveLog(sysLog);
        return tokenInfo;
    }

    /**
     * 系统刷新refresh_token
     *
     * @param refreshToken
     * @return
     */
    @PostMapping("/sys/refresh_token")
    public Map<String, Object> refreshToken(@RequestParam("refresh_token") String refreshToken) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "refresh_token");
        parameters.put(OAuth2Utils.CLIENT_ID, CLIENT_ID_VALUE);
        parameters.put(CLIENT_SECRET, CLIENT_SECRET_VALUE);
        parameters.put(OAuth2Utils.SCOPE, "app");
        parameters.put("refresh_token", refreshToken);
        return oauth2Client.postAccessToken(parameters);
    }

    /**
     * 根据皮获取token
     *
     * @param
     * @return
     */
    @GetMapping("/getTokenByIp")
    public R getTokenByIp(@RequestParam("ip") String ip) {
        Map<String, Object> result = new HashMap();
        String token = null;
        try {
            token = redisTemplate.opsForValue().get(CommonConstants.CACHE_TOKEN + ip);
        } catch (Exception e) {
            return R.failed(e.getMessage());
        }
        if (StringUtils.isEmpty(token)) {
            return R.ok(null, "token不存在！");
        }
        Map<String, Object> map = oauth2Client.checkToken(token);
        if (map.get("active") != null) {
            result.put("token", token);
            String account = map.get("user_name").toString();
            R r = userClient.findByAccount(account);
            Object data = r.getData();
            if (r.getCode() == CommonConstants.SUCCESS && data != null) {
                result.put("user", data);
            }
        }
        return R.ok(result);
    }


    /**
     * 退出
     *
     * @param accessToken
     */

    public void logout(String accessToken, @RequestHeader(required = false, value = "Authorization") String token) {
        if (StringUtils.isBlank(accessToken) && StringUtils.isNoneBlank(token)) {
            accessToken = token.substring(OAuth2AccessToken.BEARER_TYPE.length() + 1);

        }
        oauth2Client.removeToken(accessToken);
    }

    /**
     * 构建登录日志对象
     *
     * @param serviceId 微服务id
     * @param title     日志主题
     * @return
     */
    public SysLog buildLogData(String account, String serviceId, String title, Long startTime) {
        SysLog sysLog = SysLogUtils.getSysLog();
        sysLog.setType(Boolean.FALSE);
        sysLog.setCreateBy(account);
        sysLog.setServiceId(serviceId);
        sysLog.setTitle(title);
        Long endTime = System.currentTimeMillis();
        sysLog.setExecuteTime(String.valueOf(endTime - startTime));
        return sysLog;
    }

    /**
     * 获取用户在线数
     *
     * @return
     */
    @ApiOperation(value = "获取用户在线数", notes = "获取用户在线数", response = R.class)
    @GetMapping(value = "/getUserCount")
    public R getUserCount() {
        try {
            // 获取所有的key
            Set<String> keys = redisTemplate.keys(CommonConstants.CACHE_TOKEN + "*");
            // 批量获取数据
            List myObjectListRedis = redisTemplate.opsForValue().multiGet(keys);
            return R.ok(myObjectListRedis.size());
        } catch (Exception e) {
            return R.failed(e.getMessage());
        }
    }

}
