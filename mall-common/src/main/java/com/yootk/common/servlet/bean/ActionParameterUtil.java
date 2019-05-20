package com.yootk.common.servlet.bean;

import com.yootk.common.reflect.MethodParameterUtil;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 进行上传参数的处理，依据Action中方法定义名称获取相应的参数，以及进行相应的类型转换
 */
public class ActionParameterUtil {
    /**
     * 获取指定Action类对象中的指定方法参数对应的数据内容
     *
     * @return 方法内容的数组
     */
    public static Object[] getMethodParameterValue(Object actionObject, Method method) {
        Object[] result = null;
        Map<String, Class> params = MethodParameterUtil.getMethodParameter(actionObject.getClass(), method);
        if (params.size() == 0) {   // 现在没有任何的参数
            result = new Object[]{}; // 返回一个空数组
        } else {    // 现在有参数
            result = new Object[params.size()]; // 根据参数的个数创建数组
            int foot = 0; // 操作脚标
            for (Map.Entry<String, Class> entry : params.entrySet()) {
                if (isBasic(entry.getValue())) { // 基础类型
                    result[foot++] = DataTypeConverterUtil.convert(entry.getKey(), entry.getValue());
                } else if (isArray(entry.getValue())) { // 判断当前的参数类型是否为数组
                    result[foot ++] = DataTypeConverterUtil.convertArray(entry.getKey(),entry.getValue()) ;
                } else {    // 现在是一个VO类型
                    Object vo = null ;
                    try {
                        vo = entry.getValue().getDeclaredConstructor().newInstance(); // 反射实例化对象
                        DataTypeConverterUtil.setObjectFieldValue(vo); // 设置属性内容
                    } catch (Exception e) {}
                    result[foot ++] = vo ; // 将VO以参数的形式返回
                }
            }
        }
        return result;
}

    // 如果现在给定的类型属于常用的基础类型返回true
    private static boolean isBasic(Class<?> type) { // 判断给定的类型是否为常用基础类型（非VO）
        return "long".equals(type.getName()) || "java.lang.Long".equals(type.getName()) ||"int".equals(type.getName()) || "java.lang.Integer".equals(type.getName()) || "double".equals(type.getName()) || "java.lang.Double".equals(type.getName()) || "boolean".equals(type.getName()) || "java.lang.Boolean".equals(type.getName()) || "java.lang.String".equals(type.getName()) || "java.util.Date".equals(type.getName()) || "MultipartFile".equals(type.getName());
    }
    private static boolean isArray(Class<?> type) { // 判断当前的类型是否为数组
        return "int[]".equals(type.getSimpleName()) || "long[]".equals(type.getSimpleName()) || "String[]".equals(type.getSimpleName()) || "Integer[]".equals(type.getSimpleName()) || "Long[]".equals(type.getSimpleName()) || "MultipartFile[]".equals(type.getSimpleName()) ;
    }
}
