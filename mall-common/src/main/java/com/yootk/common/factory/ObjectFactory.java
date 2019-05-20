package com.yootk.common.factory;

import com.yootk.common.service.proxy.ServiceProxy;

public class ObjectFactory {
    private static final ThreadLocal<Object> SERVICE_OBJECTS = new ThreadLocal<>() ;
    private ObjectFactory() {}
    public static Object getDAOInstance(Class<?> clazz) {
        Object result = null ;
        try {
            result = clazz.getDeclaredConstructor().newInstance() ;
        } catch (Exception e) {}
        return result ;
    }
    public static Object getServiceInstance(Class<?> clazz) {
        Object result = null ;
        try {
            SERVICE_OBJECTS.set(clazz.getDeclaredConstructor().newInstance());
            result = new ServiceProxy().bind(SERVICE_OBJECTS.get());
        } catch (Exception e) {}
        return result ;
    }
    public static Object getOrignServiceObject() {
        return SERVICE_OBJECTS.get() ;
    }
}
