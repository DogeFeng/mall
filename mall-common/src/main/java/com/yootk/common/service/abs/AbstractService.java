package com.yootk.common.service.abs;

public abstract class AbstractService {
    /**
     * 判断给定的数据内容是否为空
     * @param args 判断数据是否为空
     * @return 如果为null（或者为“""”）返回true，表示内容是空
     */
    public boolean isEmpty(String ... args) {
        for (String str : args) {
            if (str == null || "".equals(str)) {
                return true ;
            }
        }
        return false ;
    }
}
