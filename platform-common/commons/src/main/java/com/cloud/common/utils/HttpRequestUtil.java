package com.cloud.common.utils;

import cn.hutool.json.JSONUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

/**
 * @Classname HttpRequestUtil
 * @Description <h1>httpRequest 请求工具类</h1>
 * @Author yulj
 * @Date: 2020/04/21 8:48 下午
 */
public class HttpRequestUtil {

    public static HttpEntity<String> getRequestEntity(Map<String, Object> dataMap) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new HttpEntity<String>(JSONUtil.toJsonStr(dataMap), requestHeaders);
    }

    public static HttpEntity<String> getRequestEntity(String jsonStr) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new HttpEntity<String>(jsonStr, requestHeaders);
    }

}
