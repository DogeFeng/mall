package com.yootk.common.servlet.bean;

import com.yootk.common.annotation.Autowired;
import com.yootk.common.factory.ObjectFactory;

import java.lang.reflect.Field;

public class DependObject {
    // 如果是业务层注入控制层，则targetObject描述的是Action实例
    // 如果是数据层注入业务层，则targetObject描述的是业务层实例
    private Object targetObject ; // 表示要进行注入的管理类
    public DependObject(Object targetObject) {
        this.targetObject = targetObject ; // 保存目标的操作对象
    }
    private void injectDAO() {  // 实现DAO对象注入
        Field fields [] = this.targetObject.getClass().getDeclaredFields() ; // 获取所有的成员对象
        for (int x = 0 ; x < fields.length; x ++) {
            if (fields[x].isAnnotationPresent(Autowired.class)) {   // 如果拥有“@Autowired”注解
                Autowired autowired = fields[x].getAnnotation(Autowired.class) ;
                Class<?> injectClazz = null ;
                if ("none".equals(autowired.name())) {  // 没有设置名字，则按照类型注入
                    injectClazz = ScannerPackageUtil.getDaoMap().get(fields[x].getType()) ; // 根据类型获取
                } else {    // 有名字则根据名称注入
                    injectClazz = ScannerPackageUtil.getByNameMap().get(autowired.name()) ; // 根据名称获取注入Class
                }
                if (injectClazz != null) {  // 已经找到了注入类型
                    try {
                        fields[x].setAccessible(true); // 取消封装
                        fields[x].set(this.targetObject, ObjectFactory.getDAOInstance(injectClazz));
                    } catch (Exception e) {}
                }
            }
        }
    }
    public void injectService() { // 实现对象注入
        Field fields [] = this.targetObject.getClass().getDeclaredFields() ; // 获取所有的成员对象
        for (int x = 0 ; x < fields.length; x ++) {
            if (fields[x].isAnnotationPresent(Autowired.class)) {   // 如果拥有“@Autowired”注解
                Autowired autowired = fields[x].getAnnotation(Autowired.class) ;
                Class<?> injectClazz = null ;
                if ("none".equals(autowired.name())) {  // 没有设置名字，则按照类型注入
                    injectClazz = ScannerPackageUtil.getServiceMap().get(fields[x].getType()) ; // 根据类型获取
                } else {    // 有名字则根据名称注入
                    injectClazz = ScannerPackageUtil.getByNameMap().get(autowired.name()) ; // 根据名称获取注入Class
                }
                if (injectClazz != null) {  // 已经找到了注入类型
                    try {
                        fields[x].setAccessible(true); // 取消封装
                        fields[x].set(this.targetObject, ObjectFactory.getServiceInstance(injectClazz));
                        // 业务层对象注入完成之后应该继续考虑注入数据层的对象
                        new DependObject(ObjectFactory.getOrignServiceObject()).injectDAO();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
