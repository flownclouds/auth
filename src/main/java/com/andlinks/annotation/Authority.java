package com.andlinks.annotation;

import java.lang.annotation.*;

/**
 * 用于在controller层的类和方法上标记所需权限的注解
 * Created by 王凯斌 on 2017/4/25.
 */
@Documented
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authority {

    /**
     * 设置是否校验该权限
     * @return
     */
    boolean validate() default true;

    /**
     * 需要校验的权限名称
     * @return
     */
    String name() default "";
}
