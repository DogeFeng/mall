package com.yootk.mall.action.front;

import com.yootk.common.action.abs.AbstractAction;
import com.yootk.common.annotation.Controller;
import com.yootk.common.annotation.RequestMapping;
import com.yootk.common.servlet.web.ModuleAndView;

@Controller
@RequestMapping("/pages/front/center/shopcar/")
public class ShopcarActionFront extends AbstractAction {
	@RequestMapping("shopcar_list")
	public ModuleAndView list() {
		ModuleAndView mav = new ModuleAndView(super.getPage("list.page")) ;
		return mav ;
	}

	@Override
	public String getUploadDir() {
		return "/upload/shopcar";
	}
}
