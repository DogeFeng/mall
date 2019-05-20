package com.yootk.common.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE,ElementType.METHOD}) // 该注解可以应用在类和方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    // 定义的是访问路径，这个路径不允许为空，如果为空，该方法无法与路径映射
    public String value() ;
}
