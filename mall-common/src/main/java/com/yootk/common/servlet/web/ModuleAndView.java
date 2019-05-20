package com.yootk.common.servlet.web;

import java.util.Map;

public class ModuleAndView {
    private String view ; // 要跳转的显示层路径
    public ModuleAndView() {}
    public ModuleAndView(String view) {
        this.view = view ;
    }
    public ModuleAndView(String view,String name,Object value) {
        this.view = view ;
        this.add(name,value);
    }
    public ModuleAndView(String view,Map<String,Object> map) {
        this.view = view ;
        this.add(map);
    }
    public void setView(String view) {  // 修改或者重新设置跳转视图
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public void add(String name, Object value) { // 保存属性内容
        ServletObject.getRequest().setAttribute(name,value);
    }
    public void add(Map<String,Object> map) {   // 存放一组数据
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                ServletObject.getRequest().setAttribute(entry.getKey(), entry.getValue());
            }
        }
    }
}
