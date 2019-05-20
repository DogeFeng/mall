package com.yootk.common.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD,ElementType.FIELD})// 该注解针对于方法和成员
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    public String name() default "none" ;   // 如果不写此内容则表示根据类型进行注入管理
}
