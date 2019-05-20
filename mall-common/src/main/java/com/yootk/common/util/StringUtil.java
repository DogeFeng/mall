package com.yootk.common.util;

public class StringUtil {
    private StringUtil() {}
    public static String initcap(String str) {
        if (str == null || "".equals(str)) {
            return str ;
        }
        if (str.length() == 1) {
            return str.toLowerCase() ;
        }
        return str.substring(0,1).toUpperCase() + str.substring(1) ;
    }
    public static String firstLower(String str) {   // 首字母小写处理
        if (str == null || "".equals(str)) {
            return str ;
        }
        if (str.length() == 1) {
            return str.toLowerCase() ;
        }
        return str.substring(0,1).toLowerCase() + str.substring(1) ;
    }
}
