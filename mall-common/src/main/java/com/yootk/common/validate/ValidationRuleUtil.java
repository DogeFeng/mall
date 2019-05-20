package com.yootk.common.validate;

import com.yootk.common.servlet.bean.ControllerRequestMapping;
import com.yootk.common.servlet.web.ServletObject;
import com.yootk.common.util.ResourceUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ValidationRuleUtil {
    public static Map<String,String> validate(String rule) {   // 进行操作验证
        Map<String,String> errors = new HashMap<>() ; // 保存错误信息
        String ruleResults [] = rule.split("\\|") ; // 拆分验证规则
        for (int x = 0 ; x < ruleResults.length ; x ++) {
            String temp [] = ruleResults[x].split(":") ; // 第一个内容为参数名称、第二个是规则
            if (!check(temp[1],temp[0])) {
                // 参数名称作为key、资源文件中的错误内容作为value
                errors.put(temp[0], ResourceUtil.getMessage("validation.error." + temp[1])) ;
            }
        }
        return errors ;
    }

    /**
     * 实现一个数据验证的检测
     * @param paramName 要接收的参数
     * @param regular 验证规则（string、int、long、double、boolean、date、datetime、rand、int[]、long[]、string[]）
     * @return 验证成功返回true
     */
    private static boolean check(String regular,String paramName) {
        String value[] = ServletObject.getParameterUtil().getParameterValues(paramName) ;
        switch(regular) {
            case "string":
                return ValidateRegular.STRING.check(value) ;
            case "int":
                return ValidateRegular.INT.check(value) ;
            case "long":
                return ValidateRegular.LONG.check(value) ;
            case "double" :
                return ValidateRegular.DOUBLE.check(value) ;
            case "boolean":
                return ValidateRegular.BOOLEAN.check(value) ;
            case "date":
                return ValidateRegular.DATE.check(value) ;
            case "datetime":
                return ValidateRegular.DATETIME.check(value) ;
            case "rand":
                return ValidateRegular.RAND.check(value) ;
            case "int[]":
                return ValidateRegular.INTS.check(value) ;
            case "long[]" :
                return ValidateRegular.LONGS.check(value) ;
            case "string[]" :
                return ValidateRegular.STRINGS.check(value) ;
            case "image":
                return ValidateRegular.IMAGE.check(paramName) ;
        }
        return false ;
    }
    public static String getValidateRule(String baseName, ControllerRequestMapping crm) {
        if (baseName == null) {
            return null ;
        }
        String rule = null ;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(baseName) ;
            String validationKey = crm.getActionClazz().getName() + "." + crm.getActionMethod().getName() ;
            rule = bundle.getString(validationKey); // 如果key不存在则抛出异常
        } catch (Exception e) {}
        return rule ;
    }
}
