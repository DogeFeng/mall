package com.yootk.common.servlet.web;

public class PageUtil {
    private Long currentPage ;
    private Integer lineSize ;
    private String keyword ;
    private String column ;
    private String columnData ;
    private String url ;
    public PageUtil(String url) {
        this(url,null) ;
    }
    public PageUtil(String url,String columnData) {
        this.url = url ;
        this.columnData = columnData ;
        this.splitHandle();
    }
    private void splitHandle() {
        try {
            this.currentPage = Long.parseLong(ServletObject.getParameterUtil().getParameter("cp")) ;
        } catch (Exception e) {}
        try {
            this.lineSize = Integer.parseInt(ServletObject.getParameterUtil().getParameter("ls")) ;
        } catch (Exception e) {}
        this.column = ServletObject.getParameterUtil().getParameter("col") ;
        this.keyword = ServletObject.getParameterUtil().getParameter("kw") ;
        ServletObject.getRequest().setAttribute("currentPage",this.currentPage);
        ServletObject.getRequest().setAttribute("lineSize",this.lineSize);
        ServletObject.getRequest().setAttribute("column",this.column);
        ServletObject.getRequest().setAttribute("keyword",this.keyword);
        ServletObject.getRequest().setAttribute("url",this.url);
        ServletObject.getRequest().setAttribute("columnData",this.columnData);
    }

    public Long getCurrentPage() {
        if (this.currentPage == null) {
            return 1L ;
        }
        return currentPage;
    }

    public String getColumn() {
        return column;
    }

    public String getKeyword() {
        return keyword;
    }

    public Integer getLineSize() {
        if (this.lineSize == null) {
            return 10 ;
        }
        return lineSize;
    }
}
