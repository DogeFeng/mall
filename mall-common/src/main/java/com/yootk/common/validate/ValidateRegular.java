package com.yootk.common.validate;

import com.yootk.common.servlet.web.ServletObject;

public enum ValidateRegular {
    STRING{
        @Override
        public boolean check(String...value) {
            if (value == null || value.length == 0) {
                return false ;
            }
            if (value.length != 1) {
                return false ;
            }
            if (value == null || "".equals(value)) {
                return false ;
            }
            return true;
        }
    },BOOLEAN{
        @Override
        public boolean check(String...value) {
            if (ValidateRegular.STRING.check(value)) {  // 数据不为空
                return "true".equals(value[0]) || "false".equals(value[0]) ;
            }
            return false;
        }
    } , INT {
        @Override
        public boolean check(String...value) {
            if (ValidateRegular.STRING.check(value)) {  // 数据不为空
                return value[0].matches("\\d+") ;
            }
            return false;
        }
    }, LONG{
        @Override
        public boolean check(String...value) {
            if (ValidateRegular.STRING.check(value)) {  // 数据不为空
                return value[0].matches("\\d+") ;
            }
            return false;
        }
    }, DOUBLE{
        @Override
        public boolean check(String...value) {
            if (ValidateRegular.STRING.check(value)) {  // 数据不为空
                return value[0].matches("\\d+(\\.\\d+)?") ;
            }
            return false;
        }
    }, DATE{
        @Override
        public boolean check(String...value) {
            if (ValidateRegular.STRING.check(value)) {  // 数据不为空
                return value[0].matches("\\d{4}-\\d{2}-\\d{2}") ;
            }
            return false;
        }
    }, DATETIME{
        @Override
        public boolean check(String...value) {
            if (ValidateRegular.STRING.check(value)) {  // 数据不为空
                return value[0].matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}") ;
            }
            return false;
        }
    }, RAND{
        @Override
        public boolean check(String...value) {
            if (ValidateRegular.STRING.check(value)) {  // 数据不为空
                String rand = (String) ServletObject.getSession().getAttribute("rand") ;
                if (ValidateRegular.STRING.check(value)) {
                    return rand.equalsIgnoreCase(value[0]) ;
                }
            }
            return false;
        }
    }, INTS {
        @Override
        public boolean check(String...value) {
            if (value == null || value.length == 0) {
                return false ;
            }
            for (String str : value) {
                if (str == null || "".equals(str)) {
                    return false ;
                } else {
                    if (!str.matches("\\d+")) { // 不为空，但是不是数字所组成
                        return false ;
                    }
                }
            }
            return true;
        }
    }, LONGS {
        @Override
        public boolean check(String...value) {
            if (value == null || value.length == 0) {
                return false ;
            }
            for (String str : value) {
                if (str == null || "".equals(str)) {
                    return false ;
                } else {
                    if (!str.matches("\\d+")) { // 不为空，但是不是数字所组成
                        return false ;
                    }
                }
            }
            return true;
        }
    }, STRINGS {
        @Override
        public boolean check(String...value) {
            if (value == null || value.length == 0) {
                return false ;
            }
            for (String str : value) {
                if (str == null || "".equals(str)) {
                    return false ;
                }
            }
            return true;
        }
    },IMAGE{
        @Override
        public boolean check(String... value) { // 传递的是参数名称
            if (value == null) {
                return true ;
            }
            return ServletObject.getParameterUtil().uploadImageCheck(value[0]);
        }
    };
    public abstract boolean check(String ... value) ;   // 验证通过返回true，否则返回false
}
