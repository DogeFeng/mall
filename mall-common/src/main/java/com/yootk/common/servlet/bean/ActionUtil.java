package com.yootk.common.servlet.bean;

import com.yootk.common.reflect.MethodUtil;

import java.lang.reflect.Method;

public class ActionUtil {
    private static final String UPLOAD_METHOD_NAME = "getUploadPath" ;
    private static final String TEMP_METHOD_NAME = "getTempPath" ;
    public static String getUpload(Object actionObject) {  // 获取指定Action中的upload路径
        String path = null ;
        Method method = MethodUtil.getMethod(actionObject.getClass(),UPLOAD_METHOD_NAME) ;
        if (method != null) {   // 找到了此方法
            try {
                path = (String) method.invoke(actionObject) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path ;
    }
    public static String getTemp(Object actionObject) {  // 获取指定Action中的upload路径
        String path = null ;
        Method method = MethodUtil.getMethod(actionObject.getClass(),TEMP_METHOD_NAME) ;
        if (method != null) {   // 找到了此方法
            try {
                path = (String) method.invoke(actionObject) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path ;
    }
}
