package com.yootk.common.servlet.bean;

import com.yootk.common.servlet.web.MultipartFile;
import com.yootk.common.servlet.web.ServletObject;
import com.yootk.common.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DataTypeConverterUtil {    // 实现数据类型的转换处理
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") ;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd") ;
    private static final ZoneId ZONE_ID = ZoneId.systemDefault() ;

    public static void setObjectFieldValue(Object obj) {
        Field fields[] = obj.getClass().getDeclaredFields(); // 获得类中拥有的全部的操作属性
        for (Field field : fields) {
            try {
                Method setMethod = obj.getClass().getDeclaredMethod("set" + StringUtil.initcap(field.getName()), field.getType());
                setMethod.invoke(obj, convert(field.getName(), field.getType()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static Object convertArray(String paramName,Class<?> type) {   // 转换为数组
        if (paramName == null || "".equals(paramName)) {
            return null ;
        } else {
            if ("int[]".equals(type.getSimpleName())) {
                String value [] = ServletObject.getParameterUtil().getParameterValues(paramName) ;
                int array [] = new int[value.length] ; // 依据传入的数组创建新的整型数组
                for (int x = 0 ; x < array.length ; x ++) {
                    if (value[x].matches("\\d+")) { // 是否为数字
                        array[x] = Integer.parseInt(value[x]) ; // 进行转换处理
                    }
                }
                return array ;
            } else if ("Integer[]".equals(type.getSimpleName())) {
                String value [] = ServletObject.getParameterUtil().getParameterValues(paramName) ;
                Integer array [] = new Integer[value.length] ; // 依据传入的数组创建新的整型数组
                for (int x = 0 ; x < array.length ; x ++) {
                    if (value[x].matches("\\d+")) { // 是否为数字
                        array[x] = Integer.parseInt(value[x]) ; // 进行转换处理
                    }
                }
                return array ;
            } else if ("long[]".equals(type.getSimpleName())) {
                String value [] = ServletObject.getParameterUtil().getParameterValues(paramName) ;
                long array [] = new long[value.length] ; // 依据传入的数组创建新的整型数组
                for (int x = 0 ; x < array.length ; x ++) {
                    if (value[x].matches("\\d+")) { // 是否为数字
                        array[x] = Long.parseLong(value[x]) ; // 进行转换处理
                    }
                }
                return array ;
            } else if ("Long[]".equals(type.getSimpleName())) {
                String value [] = ServletObject.getParameterUtil().getParameterValues(paramName) ;
                Long array [] = new Long[value.length] ; // 依据传入的数组创建新的整型数组
                for (int x = 0 ; x < array.length ; x ++) {
                    if (value[x].matches("\\d+")) { // 是否为数字
                        array[x] = Long.parseLong(value[x]) ; // 进行转换处理
                    }
                }
                return array ;
            } else if ("String[]".equals(type.getSimpleName())){
                String value [] = ServletObject.getParameterUtil().getParameterValues(paramName) ;
                return value ;
            } else if ("MultipartFile[]".equals(type.getSimpleName())){
                MultipartFile arr[] = new MultipartFile[ServletObject.getParameterUtil().getListUpload(paramName).size()] ;
                for (int x = 0 ; x < arr.length ; x ++) {
                    arr[x] = ServletObject.getParameterUtil().getListUpload(paramName).get(x) ;
                }
                return arr ;
            }
        }
        return null ;
    }

    public static Object convert(String paramName,Class<?> type) { // 接收的全部参数都是String
        if (paramName == null || "".equals(paramName)) {
            return null ;
        } else if ("int".equals(type.getName()) || "java.lang.Integer".equals(type.getName())) {
            String value = ServletObject.getParameterUtil().getParameter(paramName) ;
            if (value.matches("\\d+")) {    // 是否由数字所组成
                return Integer.parseInt(value) ;    // 将字符串转为整型数据
            }
            return 0 ;  // 默认返回类型
        } else if ("long".equals(type.getName()) || "java.lang.Long".equals(type.getName())) {
            String value = ServletObject.getParameterUtil().getParameter(paramName) ;
            if (value.matches("\\d+")) {    // 是否由数字所组成
                return Long.parseLong(value) ;    // 将字符串转为整型数据
            }
        } else if ("double".equals(type.getName()) || "java.lang.Double".equals(type.getName())) {
            String value = ServletObject.getParameterUtil().getParameter(paramName) ;
            if (value.matches("\\d+(\\.\\d+)?")) {    // 是否由数字所组成
                return Double.parseDouble(value) ;    // 将字符串转为整型数据
            }
        } else if ("boolean".equals(type.getName()) || "java.lang.Boolean".equals(type.getName())) {
            String value = ServletObject.getParameterUtil().getParameter(paramName) ;
            return Boolean.parseBoolean(value) ;    // 将字符串转为整型数据
        } else if ("java.util.Date".equals(type.getName())) {
            String value = ServletObject.getParameterUtil().getParameter(paramName) ;
            if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {    // 日期格式
                LocalDate localDate = LocalDate.parse(value,DATE_FORMATTER) ; // 日期转换
                Instant instant = localDate.atStartOfDay().atZone(ZONE_ID).toInstant() ;
                return Date.from(instant) ;
            } else if (value.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {    // 日期时间
                LocalDateTime localDateTime = LocalDateTime.parse(value,DATE_TIME_FORMATTER) ;
                Instant instant = localDateTime.atZone(ZONE_ID).toInstant() ;
                return Date.from(instant) ; // 将字符串转为日期时间
            } else {
                return null ;
            }
        } else if ("java.lang.String".equals(type.getName())) {
            String value = ServletObject.getParameterUtil().getParameter(paramName) ;
            return value ;  // 默认返回类型
        } else if ("MultipartFile".equals(type.getName())) {
            return ServletObject.getParameterUtil().getSingleUpload(paramName) ;
        }
        return null ;
    }

}
