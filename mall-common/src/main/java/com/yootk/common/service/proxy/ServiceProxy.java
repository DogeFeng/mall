package com.yootk.common.service.proxy;

import com.yootk.common.dbc.DatabaseConnection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ServiceProxy implements InvocationHandler {
    private Object target ; // 真实类对象
    public Object bind(Object target) {
        this.target = target ;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this) ;
    }
    private boolean isTransaction(String methodName) {  // 判断是否需要开启事务
        return methodName.startsWith("add") || methodName.startsWith("edit") || methodName.startsWith("delete") || methodName.startsWith("remove") || methodName.startsWith("create");
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result ;
        try {
            if (this.isTransaction(method.getName())) { // 要操作的业务方法存在有事务支持
                DatabaseConnection.getConnection().setAutoCommit(false);
            }
            result = method.invoke(this.target,args) ;
            if (this.isTransaction(method.getName())) { // 要操作的业务方法存在有事务支持
                DatabaseConnection.getConnection().commit();
            }
        } catch (Exception e) {
            if (this.isTransaction(method.getName())) { // 要操作的业务方法存在有事务支持
                DatabaseConnection.getConnection().rollback();
            }
            throw e ;
        } finally {
            DatabaseConnection.close();
        }
        return result;
    }
}
