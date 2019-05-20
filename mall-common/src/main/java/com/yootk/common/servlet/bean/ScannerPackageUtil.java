package com.yootk.common.servlet.bean;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ScannerPackageUtil {
    // 此Map是在进行容器启动的时候实现内容填充的，不牵扯到多线程并发修改问题，只考虑到读取问题即可
    // HashMap在JDK 1.8之后进行了改进，如果数据的容量保存到了8位，则自动转为红黑树的结构，提高查询性能；
    // Map之中的key为访问的映射路径
    // value为ControllerRequestMapping接口实例，该类存储的是ActionClass以及对应路径的Method
    private static final Map<String,ControllerRequestMapping> ACTION_MAP = new HashMap<>() ;
    // 保存所有根据名称实现对象注入的集合信息（key = 类名称、value = 注解对应的Class实例）
    private static final Map<String,Class> BY_NAME_MAP = new HashMap<>() ;
    // 保存所有业务层的名称注入的集合信息（key = 接口、value = 子类）
    private static final Map<Class,Class> SERVICE_MAP = new HashMap<>() ;
    // 保存所有数据层的名称注入的集合信息（key = 接口、value = 子类）
    private static final Map<Class,Class> DAO_MAP = new HashMap<>() ;
    private ScannerPackageUtil() {}  // 构造方法私有化
    /**
     * 进行包扫描的处理
     * @param clazz 要进行路径扫描的基础类所在的Class对象，通过此对象获取基础路径
     * @param packages 要扫描的全部包的名称
     */
    public static void scannerHandle(Class<?> clazz, String packages) {
        if (packages == null || "".equals(packages)) {  // 发现当前的包不为空
            return ; // 操作直接结束
        }
        String resultPackages [] = packages.split(";") ; // 获取所有需要进行扫描配置的包
        String baseDir = clazz.getResource("/").getPath() ; // 获得基础扫描路径
        baseDir = baseDir.substring(1).replace("/",File.separator) ;
        for (int x = 0 ; x < resultPackages.length ; x ++) {    // 循环所有的子目录
            String subDir = resultPackages[x].replace(".", File.separator) ;
            File file = new File(baseDir,subDir) ; // 基本的控制目录
            listDirClass(file,baseDir); // 循环递归列表
        }
    }
    private static void listDirClass(File file,String baseDir) {
        if (file.isDirectory()) {   // 给定File是一个目录
            File result [] = file.listFiles() ; // 列出全部的内容
            if (result != null) {
                for (int x = 0 ; x < result.length ; x ++) {
                    listDirClass(result[x],baseDir);
                }
            }
        } else {
            if (file.isFile()) {    // 是一个文件
                if (file.getName().endsWith(".class")) {    // 现在要得到所有的class文件名称
                    String className = file.getAbsolutePath().replace(baseDir,"").replace(File.separator,".").replace(".class","") ;
                    try {   // 将ACTION_MAP的填充操作交给专门的工具类来完成
                        ConfigAnnotationParseUtil parseUtil = new ConfigAnnotationParseUtil(Class.forName(className)) ;
                        ACTION_MAP.putAll(parseUtil.getControllerMapping()) ;
                        BY_NAME_MAP.putAll(parseUtil.getNameMap());
                        SERVICE_MAP.putAll(parseUtil.getServiceMapping());
                        DAO_MAP.putAll(parseUtil.getDaoMapping()) ;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Map<String, ControllerRequestMapping> getActionMap() {
        return ACTION_MAP;
    }

    public static Map<Class, Class> getServiceMap() {
        return SERVICE_MAP;
    }

    public static Map<String, Class> getByNameMap() {
        return BY_NAME_MAP;
    }

    public static Map<Class, Class> getDaoMap() {
        return DAO_MAP;
    }
}
