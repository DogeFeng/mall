package com.yootk.common.reflect;

import java.lang.reflect.Method;

public class MethodUtil {
    private MethodUtil() {}

    /**
     * 根据提供的方法名称获取方法对应的Method对象实例
     * @param clazz 要进行反射操作的Class
     * @param method_name 方法名称
     * @return 如果该方法存在，则返回Method实例，如果不存在则返回null
     */
    public static Method getMethod(Class<?> clazz, String method_name) {
        Method methods [] = clazz.getDeclaredMethods() ;
        for (Method method : methods) {
            if (method_name.equals(method.getName())) {
                return method ;
            }
        }
        return null ;
    }
}
