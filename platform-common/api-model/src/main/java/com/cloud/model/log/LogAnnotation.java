package com.cloud.model.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 *
 * @author com.hlxd
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

    /**
     * 微服务名称
     *
     * @return
     */
    String serviceId();

    /**
     * 日志主题
     *
     * @return
     */
    String title();

    /**
     * 记录参数<br>
     * 尽量记录普通参数类型的方法，和能序列化的对象
     *
     * @return
     */
    boolean recordParam() default true;

}
