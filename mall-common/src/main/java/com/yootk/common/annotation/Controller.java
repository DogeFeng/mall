package com.yootk.common.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE}) // 该注解可以应用在各种结构上（类）
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    // 保存类的名称信息， 如果没有设置，则使用类名称自动配置
    public String value() default "none" ;
}
