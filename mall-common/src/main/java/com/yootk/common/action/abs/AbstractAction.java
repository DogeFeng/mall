package com.yootk.common.action.abs;

import com.yootk.common.servlet.web.ModuleAndView;
import com.yootk.common.servlet.web.MultipartFile;
import com.yootk.common.servlet.web.ServletObject;
import com.yootk.common.util.ResourceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public abstract class AbstractAction {
    public static final String PATH_ATTRIBUTE_NAME = "path" ;
    public static final String MSG_ATTRIBUTE_NAME = "msg" ;
    private static final String PAGES_BASENAME = "com.yootk.mall.action.resource.Page" ;
    private static ResourceBundle pageResourceBundle ;
    static {
        try {
            pageResourceBundle = ResourceBundle.getBundle(PAGES_BASENAME) ;
        } catch (Exception e) {}
    }
    private String getPageResource(String key) {
        try {
            return pageResourceBundle.getString(key) ;
        } catch (Exception e) {
            return null ;
        }
    }
    public String getTempPath() {
        return "/tmp" ;
    }

    /**
     * 实现所有上传文件的存储
     * @param files 描述所有的上传文件
     * @return 返回每个文件保存的路径信息
     */
    public List<String> saveUploadFile(MultipartFile... files) {
        List<String> fileNames = new ArrayList<>() ;
        String uploadDir = this.getUploadDir() ;
        if (!uploadDir.endsWith("/")) {
            uploadDir = uploadDir + "/" ;
        }
        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID() + "." + file.getContentType().substring(file.getContentType().lastIndexOf("/") + 1) ;
            String savePath = ServletObject.getApplication().getRealPath(uploadDir) + fileName ;
            if(file.transfer(savePath)) {   // 文件转存
                fileNames.add(fileName) ;
            }
        }
        return fileNames ;
    }
    /**
     * 获取每一个Action所需要的上传文件的父目录
     * @return 上传的父目录
     */
    public abstract String getUploadDir() ;
    /**
     * 实现信息输出
     * @param obj 要输出的信息内容
     */
    public void print(Object obj) {
        try {
            ServletObject.getResponse().getWriter().println(obj);
        } catch (IOException e) {
        }
    }
    /**
     * 得到跳转页面
     * @return 跳转显示页
     */
    public String getForwardPage() {
        return this.getPageResource("forward.page");
    }

    /**
     * 获取登陆后显示的首页信息
     * @return 返回首页路径
     */
    public String getIndexPage() {
        return this.getPageResource("index.page");
    }
    /**
     * 得到跳转路径
     * @param key
     * @return
     */
    public String getPage(String key) {
        return this.getPageResource(this.getClass().getName() + "." + key) ;
    }

}
