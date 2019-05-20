package com.yootk.common.servlet.web;

import java.io.*;

public class MultipartFile extends File {
    private String contentType ; // 保存文件类型
    private String originFileName; // 原始文件名称
    public MultipartFile(String path) {
        super(path) ;
    }
    public MultipartFile(File parent, String child) {
        super(parent,child) ;
    }

    /**
     * 将当前的文件内容直接转存到指定的目录之中（输出文件的完整路径）
     * @param savePath 完整路径
     * @return 保存成功返回true，否则返回false
     */
    public boolean transfer(String savePath) {
        boolean flag = false ;
        InputStream input = null ;
        OutputStream output = null ;
        try {
            File outFile = new File(savePath) ;
            input = new FileInputStream(this) ;
            output = new FileOutputStream(outFile) ;
            input.transferTo(output) ; // 直接输出二进制数据
            flag = true ; // 修改成功的标志位
        } catch (Exception e) {
        } finally {
            if (input != null) {
                try {
                    input.close() ;
                } catch (IOException e) {
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                }
            }
        }
        return flag ;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }
    public String getContentType() {
        return contentType;
    }
    public String getOriginFileName() {
        return originFileName;
    }
}
