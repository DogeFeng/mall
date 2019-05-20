package com.yootk.common.servlet;

import com.yootk.common.servlet.bean.*;
import com.yootk.common.servlet.web.ModuleAndView;
import com.yootk.common.servlet.web.ServletObject;
import com.yootk.common.util.ParameterUtil;
import com.yootk.common.util.ResourceUtil;
import com.yootk.common.validate.ValidationRuleUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    private String errorPage404 = null ;
    private String errorPage500 = null ;
    private String validationBaseName = null ; // 保存验证规则的文件路径
    private String errorPageBaseName = null ; // 保存验证规则的文件路径
    private String messageBaseName = null ; // 保存验证规则的文件路径

    @Override
    public void init(ServletConfig config) throws ServletException {
        String scanPackages = config.getInitParameter("scanPackages") ; // 定义扫描包
        ScannerPackageUtil.scannerHandle(this.getClass(),scanPackages);
        this.errorPage404 = config.getInitParameter("404") ;
        this.errorPage500 = config.getInitParameter("500") ;
        this.validationBaseName = config.getInitParameter("validationBaseName") ;
        this.messageBaseName = config.getInitParameter("messageBaseName") ;
        this.errorPageBaseName = config.getInitParameter("errorPageBaseName") ;
        if (this.messageBaseName != null) {
            ResourceUtil.setMessageBaseName(this.messageBaseName);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dispatcherPath = null ;    // 服务器端跳转路径
        String path = request.getServletPath().substring(0,request.getServletPath().lastIndexOf(".action")) ;
        if (!ScannerPackageUtil.getActionMap().containsKey(path)) { // 现在要查询的路径不存在
            dispatcherPath = this.errorPage404 ;
        } else {
            // 获取的ControllerRequestMapping对象实例，包含有ActionObject以及Method反射对象
            ControllerRequestMapping mapping = ScannerPackageUtil.getActionMap().get(path);
            try {
                ServletObject.setRequest(request);
                ServletObject.setResponse(response);

                // Action实例化之后才可以实现最终方法的调用
                Object actionObject = mapping.getActionClazz().getDeclaredConstructor().newInstance();
                ParameterUtil pu = null ; // 定义参数的接收对象
                String uploadPath = ActionUtil.getTemp(actionObject) ; // 获取上传路径
                if (uploadPath == null) {
                    pu = new ParameterUtil(request) ; // 默认构造方法实例化
                } else {
                    pu = new ParameterUtil(request,uploadPath) ; // 设置上传目录
                }
                ServletObject.setParameterUtil(pu);
                Map<String,String> errors = null ; // 错误信息
                if (this.validationBaseName == null || "".equals(this.validationBaseName)) {    // 现在没有验证需求
                    // 正常执行后续的Action调用
                } else {    // 如果此时存在有验证需求，当验证通过之后再进行后续的处理
                    String rule = ValidationRuleUtil.getValidateRule(this.validationBaseName,mapping);
                    if (rule != null) {
                        errors = ValidationRuleUtil.validate(rule) ;    // 接收错误信息
                    }
                }

                if (errors == null || errors.size() == 0) {
                    // 将实例化好的Action类的对象传递给依赖管理的对象类“DependObject”
                    new DependObject(actionObject).injectService();    // 控制层注入业务层实例
                    // 在进行方法调用的时候需要考虑返回值的问题，而且经过分析只有三类可能的返回结果
                    Object params[] = ActionParameterUtil.getMethodParameterValue(actionObject, mapping.getActionMethod());
                    Object returnValue = mapping.getActionMethod().invoke(actionObject, params);    // 反射调用Action类的方法
                    if (returnValue != null) {  // 当前的Action有返回任何数据
                        if (returnValue instanceof String) {    // 现在返回的类型为字符串
                            dispatcherPath = (String) returnValue; // 接收跳转的返回路径
                        }
                        if (returnValue instanceof ModuleAndView) {
                            ModuleAndView mav = (ModuleAndView) returnValue;
                            dispatcherPath = mav.getView(); // 获取跳转的路径
                        }
                    }
                } else {    // 现在有错误信息
                    if (this.errorPageBaseName == null || "".equals(this.errorPageBaseName)) {  // 没有特指错误页
                        dispatcherPath = this.errorPage500 ; // 全局错误页
                    } else {
                        dispatcherPath = ValidationRuleUtil.getValidateRule(this.errorPageBaseName,mapping) ;
                        if (dispatcherPath == null || "".equals(dispatcherPath)) {
                            dispatcherPath = this.errorPage500 ; // 全局错误页
                        }
                    }
                    request.setAttribute("errors",errors); // 保存所有的错误提示信息
                }
                ServletObject.clean();
            } catch (Exception e) {
                e.printStackTrace();
                dispatcherPath = this.errorPage500 ;
            }
        }
        if (dispatcherPath != null) {   // 此时有了跳转路径
            request.getRequestDispatcher(dispatcherPath).forward(request,response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
