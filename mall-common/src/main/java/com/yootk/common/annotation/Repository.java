package com.yootk.common.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE}) // 该注解可以应用在各种结构上（类）
@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {
    public String value() default "none" ;  // 保存名称
}
