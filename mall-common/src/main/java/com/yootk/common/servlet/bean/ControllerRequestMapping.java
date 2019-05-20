package com.yootk.common.servlet.bean;

import java.lang.reflect.Method;

public class ControllerRequestMapping {
    private Class<?> actionClazz ;    // Action程序类的信息
    private Method actionMethod ; // 对应的操作方法

    public ControllerRequestMapping(Class<?> actionClazz, Method actionMethod) {
        this.actionClazz = actionClazz;
        this.actionMethod = actionMethod;
    }

    public Class<?> getActionClazz() {
        return actionClazz;
    }

    public Method getActionMethod() {
        return actionMethod;
    }


    @Override
    public String toString() {
        return "ControllerRequestMapping{" +
                "actionClazz=" + actionClazz +
                ", actionMethod=" + actionMethod +
                '}';
    }
}
