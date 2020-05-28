package com.cloud.model.platformuser.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Classname CheckPasswordValidator
 * @Description <h1>自定义密码校验实现</h1>
 * @Author yulj
 * @Date: 2020/5/26 11:31 上午
 */
public class CheckPasswordValidator implements ConstraintValidator<CheckPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        /**
         * 校验规则：
         * 1. 密码长度要等于或大于8位；
         * 2. 密码内容要同时包含大小写字母和数字，支持使用特殊字符；
         */
        // 判断密码是否包含数字：包含返回1，不包含返回0
        int i = value.matches(".*\\d+.*") ? 1 : 0;
        // 判断密码是否包含小写字母：包含返回1，不包含返回0
        int j = value.matches(".*[a-z]+.*") ? 1 : 0;
        // 判断密码是否包含大写字母：包含返回1，不包含返回0
        int k = value.matches(".*[A-Z]+.*") ? 1 : 0;
        if (i + j + k < 3 || value.length() < 8) {
            return false;
        }
        return true;
    }

}
