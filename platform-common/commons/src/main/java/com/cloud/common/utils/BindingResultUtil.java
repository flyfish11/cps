package com.cloud.common.utils;

import com.google.common.collect.Maps;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

/**
 * @Classname BindingResultUtil
 * @Description <h1>BEAN业务对象校验工具类</h1>
 * @Author yulj
 * @Date: 2020/03/02 2:11 下午
 */
public class BindingResultUtil {

    public static Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = Maps.newHashMap();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            // 发生验证错误所对应的某一个属性
            String errorField = error.getField();
            // 验证错误的信息
            String errorMsg = error.getDefaultMessage();
            map.put(errorField, errorMsg);
        }
        return map;
    }

}