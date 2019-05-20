package com.yootk.common.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ResourceUtil {
    private static ResourceBundle messageResource ;
    public static void setMessageBaseName(String messageBaseName) {
        messageResource = ResourceBundle.getBundle(messageBaseName) ;
    }
    public static String getMessage(String key,String ... param) {
        try {
            String str = messageResource.getString(key) ;
            if (param != null && param.length > 0) {
                return MessageFormat.format(str,param) ;
            }
            return str ;
        } catch (Exception e) {
            return null ;
        }
    }
}
