package com.yootk.mall.filter;

import com.yootk.common.servlet.web.CookieUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MemberCookieFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        if (session.getAttribute("mid") == null) {    // 用户未登录
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String info = CookieUtil.get("info", request);
            if (info != null) {
                String temp[] = info.split(":");
                session.setAttribute("mid", temp[0]);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } else {    // 用户已登录
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
