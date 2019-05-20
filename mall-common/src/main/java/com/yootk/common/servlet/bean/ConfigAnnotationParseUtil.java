package com.yootk.common.servlet.bean;

import com.yootk.common.annotation.Controller;
import com.yootk.common.annotation.Repository;
import com.yootk.common.annotation.RequestMapping;
import com.yootk.common.annotation.Service;
import com.yootk.common.util.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 在一个Action里面会提供有多个路径，所以每一个控制层的Action都有可能返回更多的数据内容
 */
public class ConfigAnnotationParseUtil {
    // 控制层的Map集合：key = 访问路径、value = ControllerRequestMapping（key = Action类、value = Method类）
    private Map<String,ControllerRequestMapping> controllerMapResult = new HashMap<>() ;
    // 业务层和数据层的Map集合：key = 设置的名称、value = 对应的类型
    private Map<String,Class> nameAndTypeMap = new HashMap<>() ;
    // 业务层Map集合：key = 业务子类实现的所有接口，value = 子类Class
    private Map<Class,Class> serviceInterfaceAndClassMap = new HashMap<>() ;
    // 数据层Map集合：key = 数据子类实现的所有接口，value = 子类Class
    private Map<Class,Class> daoInterfaceAndClassMap = new HashMap<>() ;
    private String parentUrl ; // 保存父路径
    private Class<?> clazz ;    // 传递到此类中的可能是任意的类型
    public ConfigAnnotationParseUtil(Class<?> clazz) {
        this.clazz = clazz ;    // 要获取全部拥有“@Controller注解的类”
        this.classHandle();
    }
    private void handleMappingMethod() {    // 进行控制层之中每一个映射方法的信息获取
        if (this.parentUrl == null) {
            this.parentUrl = "" ;
        }
        Method methods[] = this.clazz.getDeclaredMethods() ; // 得到当前类中的全部Method类对象
        for (Method method : methods) { // 循环每一个方法
            if (method.isAnnotationPresent(RequestMapping.class)) { // 当前的方法上提供有RequestMapping映射处理
                RequestMapping mapping = method.getAnnotation(RequestMapping.class) ;
                String path = this.parentUrl + mapping.value() ; // 完整路径
                // 保存当前Class类型的目的是为了方便反射进行Action类对象的实例化，从而进行方法的反射调用
                this.controllerMapResult.put(path,new ControllerRequestMapping(this.clazz,method)) ;
            }
        }
    }
    private void classHandle() {    // 设置一个类的处理操作，进行各种注解的区分
        Annotation annotations [] = this.clazz.getAnnotations() ; // 获取类上全部的Annotation定义
        for (Annotation anot : annotations) {   // 进行所有Annontation的迭代
            if (anot.annotationType().equals(Controller.class)) {   // 可以在当前类中发现有“@Controller”注解
                try {
                    // 获取在类上定义的“@RequestMapping”注解对象，利用此对象可以获取父路径
                    RequestMapping mapping = this.clazz.getAnnotation(RequestMapping.class); // 获取指定的Annotation
                    this.parentUrl = mapping.value(); // 获取父路径
                    if (this.parentUrl.lastIndexOf("/") == -1) {    // 没有配置“最后的“/””
                        this.parentUrl += "/";
                    }
                } catch (Exception e) {}
                this.handleMappingMethod(); // 找到控制层里面每一个方法的注解配置
            } else if (anot.annotationType().equals(Service.class)) {   // 业务层子类
                Service service = this.clazz.getAnnotation(Service.class) ; // 获取当前Service注解
                if ("none".equals(service.value())) {   // 没有设置名字，自动设置一个名字
                    nameAndTypeMap.put(StringUtil.firstLower(this.clazz.getSimpleName()),this.clazz) ;
                } else {
                    nameAndTypeMap.put(service.value(),this.clazz) ; // 直接获取名称来操作
                }
                Class<?> clazzInterfaces [] = this.clazz.getInterfaces() ; // 获得所有的接口类型
                for (int x = 0 ; x < clazzInterfaces.length ; x ++) {
                    serviceInterfaceAndClassMap.put(clazzInterfaces[x],this.clazz) ;    // 存储了接口和类型的关联
                }
            } else if (anot.annotationType().equals(Repository.class)) {
                Repository repository = this.clazz.getAnnotation(Repository.class) ; // 获取当前Service注解
                if ("none".equals(repository.value())) {   // 没有设置名字，自动设置一个名字
                    nameAndTypeMap.put(StringUtil.firstLower(this.clazz.getSimpleName()),this.clazz) ;
                } else {
                    nameAndTypeMap.put(repository.value(),this.clazz) ; // 直接获取名称来操作
                }
                Class<?> clazzInterfaces [] = this.clazz.getInterfaces() ; // 获得所有的接口类型
                for (int x = 0 ; x < clazzInterfaces.length ; x ++) {
                    daoInterfaceAndClassMap.put(clazzInterfaces[x],this.clazz) ;    // 存储了接口和类型的关联
                }
            }
        }
    }
    /**
     * 该操作是根据提供的程序类获取所有的Controller类的映射的转换结果
     * @return 是一个包含有路径以及Action和Method的Map集合
     */
    public Map<String,ControllerRequestMapping> getControllerMapping() {
        return this.controllerMapResult ;
    }

    /**
     * 此时返回可以根据名称查询对应类型的Map集合
     * @return 包含有名称和类型映射的关联
     */
    public Map<String,Class> getNameMap() {
        return this.nameAndTypeMap ;
    }

    /**
     * 获取所有业务层的映射对象的定义操作
     * @return 包含有接口和类型映射的Map集合
     */
    public Map<Class,Class> getServiceMapping() {
        return this.serviceInterfaceAndClassMap ;
    }

    /**
     * 获取所有数据层映射对象定义操作
     * @return 包含有数据接口和类型映射的Map集合
     */
    public Map<Class, Class> getDaoMapping() {
        return daoInterfaceAndClassMap;
    }
}
