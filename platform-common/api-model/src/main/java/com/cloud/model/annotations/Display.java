package com.cloud.model.annotations;

import java.lang.annotation.*;

/**
 * @Classname Display
 * @Description 自定义注解 前端是否显示该属性
 * @Author yulj
 * @Date: 2019/08/16 19:42
 */
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Display {
}
