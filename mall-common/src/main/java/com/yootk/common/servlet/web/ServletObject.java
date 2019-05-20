package com.yootk.common.servlet.web;

import com.yootk.common.util.ParameterUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletObject {
    private static final ThreadLocal<HttpServletRequest> THREAD_REQUEST = new ThreadLocal<>() ;
    private static final ThreadLocal<HttpServletResponse> THREAD_RESPONSE = new ThreadLocal<>() ;
    private static final ThreadLocal<ParameterUtil> THREAD_PUTIL = new ThreadLocal<>() ;
    public static void setRequest(HttpServletRequest request) {
        THREAD_REQUEST.set(request);
    }
    public static void setResponse(HttpServletResponse response) {
        THREAD_RESPONSE.set(response);
    }
    public static void setParameterUtil(ParameterUtil pu) {
        THREAD_PUTIL.set(pu);
    }
    public static HttpServletRequest getRequest() {
        return THREAD_REQUEST.get() ;
    }
    public static HttpServletResponse getResponse() {
        return THREAD_RESPONSE.get() ;
    }
    public static HttpSession getSession() {
        return THREAD_REQUEST.get().getSession() ;
    }
    public static ParameterUtil getParameterUtil() {
        return THREAD_PUTIL.get() ;
    }
    public static ServletContext getApplication() {
        return THREAD_REQUEST.get().getServletContext() ;
    }
    public static void clean() {
        THREAD_REQUEST.remove();
        THREAD_RESPONSE.remove();
        THREAD_PUTIL.get().clean();
        THREAD_PUTIL.remove();
    }
}
