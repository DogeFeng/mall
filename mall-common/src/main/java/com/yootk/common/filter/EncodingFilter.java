package com.yootk.common.filter;
import javax.servlet.*;
import java.io.IOException;
//<filter>
//<filter-name>EncodingFilter</filter-name>
//<filter-class>EncodingFilter</filter-class>
//<init-param>
//<param-name>charset</param-name>
//<param-value>UTF-8</param-value>
//</init-param>
//</filter>
//<filter-mapping>
//<filter-name>EncodingFilter</filter-name>
//<url-pattern>/*</url-pattern>   <!-- 过滤路径 -->
//    </filter-mapping>
public class EncodingFilter implements Filter {
    private String charset = "UTF-8" ; // 设置一个默认的编码
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig.getInitParameter("charset") != null) { // 外部设置了编码
            this.charset = filterConfig.getInitParameter("charset") ; // 使用外部设置的编码
        }
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(this.charset);  // 请求编码
        filterChain.doFilter(servletRequest,servletResponse);
        servletResponse.setCharacterEncoding(this.charset); // 回应编码
    }

}