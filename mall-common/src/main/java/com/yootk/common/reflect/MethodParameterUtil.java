package com.yootk.common.reflect;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

public class MethodParameterUtil {
    /**
     * 根据指定的类型以及方法名称获取参数的完整信息
     * @param clazz 要解析的Class类
     * @param method_name 要进行处理的方法名称
     * @return 一个包含有参数名称以及类型对应的Map集合
     */
    public static Map<String,Class> getMethodParameter(Class<?> clazz, String method_name) {
        Map<String,Class> map = new LinkedHashMap<>() ; // 必须有序存储Map
        if (clazz == null || method_name == null || "".equals(method_name)) {
            return map ;
        }
        try {
            // 2、如果要想进行操作则一定要获取相应的Method对象，主要是进行二进制文件的读取定位
            Method method = MethodUtil.getMethod(clazz, method_name); // 获取要操作的方法
            // 3、原始的反射支持只能够获取参数的类型
            // 4、如果要进行二进制数据的读取，那么必须依靠类名称来进行读取，所以此时需要明确得到一个类分析工具
            ClassPool classPool = ClassPool.getDefault();    // javassist提供的类
            ClassPath classPath = new ClassClassPath(clazz) ;	// 将传递进来的Action的类型设置到ClassPath之中
            classPool.insertClassPath(classPath) ;	// 修改了javassist的CLASSPATH
            // 5、当获取了ClassPool之后，里面会存在有多个二进制的数据的信息，所以要获取一个指定类型的内容
            CtClass ctClass = classPool.get(clazz.getName()); // 获取待分析的程序类
            // 6、依据要使用的方法进行二进制资源的定位；
            CtMethod ctMethod = ctClass.getDeclaredMethod(method_name); // 根据方法名称进行二进制定位
            // 7、依据二进制的定位信息获取二进制的字节内容
            MethodInfo methodInfo = ctMethod.getMethodInfo(); // 找到一个方法的二进制区域
            // 8、依据方法的二进制数据区获取对应所有字节二进制编码属性内容
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();   // 读取二进制的字节码信息
            // 9、根据读取到的编码属性的内容，来获取本地变量列表数据
            LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute) codeAttribute
                    .getAttribute(LocalVariableAttribute.tag); // 获取局部变量属性
            // 10、在获取方法内容的时候还需要考虑方法是否为static型，因为如果static与非static类型有字节读取的差距
            int position = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1; // 是否为static型
            // 11、获取每一个参数对应的名称
            Class<?> parameterTypes [] = method.getParameterTypes() ;
            for (int x = 0; x < parameterTypes.length; x++) {
                map.put(localVariableAttribute.variableName(x + position),parameterTypes[x]) ;
            }
        } catch (Exception e) {}
        return map ;
    }
    public static Map<String,Class> getMethodParameter(Class<?> clazz, Method method) {
        Map<String,Class> map = new LinkedHashMap<>() ; // 必须有序存储Map
        if (method == null) {
            return map ;
        }
        try {
            // 3、原始的反射支持只能够获取参数的类型
            // 4、如果要进行二进制数据的读取，那么必须依靠类名称来进行读取，所以此时需要明确得到一个类分析工具
            ClassPool classPool = ClassPool.getDefault();    // javassist提供的类
            // 5、当获取了ClassPool之后，里面会存在有多个二进制的数据的信息，所以要获取一个指定类型的内容
            CtClass ctClass = classPool.get(clazz.getName()); // 获取待分析的程序类
            ClassPath classPath = new ClassClassPath(clazz) ;	// 将传递进来的Action的类型设置到ClassPath之中
            classPool.insertClassPath(classPath) ;	// 修改了javassist的CLASSPATH
            // 6、依据要使用的方法进行二进制资源的定位；
            CtMethod ctMethod = ctClass.getDeclaredMethod(method.getName()); // 根据方法名称进行二进制定位
            // 7、依据二进制的定位信息获取二进制的字节内容
            MethodInfo methodInfo = ctMethod.getMethodInfo(); // 找到一个方法的二进制区域
            // 8、依据方法的二进制数据区获取对应所有字节二进制编码属性内容
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();   // 读取二进制的字节码信息
            // 9、根据读取到的编码属性的内容，来获取本地变量列表数据
            LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute) codeAttribute
                    .getAttribute(LocalVariableAttribute.tag); // 获取局部变量属性
            // 10、在获取方法内容的时候还需要考虑方法是否为static型，因为如果static与非static类型有字节读取的差距
            int position = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1; // 是否为static型
            // 11、获取每一个参数对应的名称
            Class<?> parameterTypes [] = method.getParameterTypes() ;
            for (int x = 0; x < parameterTypes.length; x++) {
                map.put(localVariableAttribute.variableName(x + position),parameterTypes[x]) ;
            }
        } catch (Exception e) {}
        return map ;
    }
}
