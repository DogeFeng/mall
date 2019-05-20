package com.yootk.mall.action.front;

import com.yootk.common.action.abs.AbstractAction;
import com.yootk.common.annotation.Autowired;
import com.yootk.common.annotation.Controller;
import com.yootk.common.annotation.RequestMapping;
import com.yootk.common.servlet.web.ModuleAndView;
import com.yootk.common.servlet.web.PageUtil;
import com.yootk.mall.service.front.IGoodsServiceFront;

@Controller
@RequestMapping("/pages/front/goods/")
public class GoodsActionFront extends AbstractAction {
	@Autowired
	private IGoodsServiceFront goodsService;
	@RequestMapping("goods_list")
	public ModuleAndView list() {
		ModuleAndView mav = new ModuleAndView(super.getPage("list.page"));
		PageUtil pu = new PageUtil(super.getPage("list.action"),"商品名称:name");
		try {
			mav.add(
					this.goodsService.list(pu.getCurrentPage(), pu.getLineSize(),pu.getColumn(), pu.getKeyword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@Override
	public String getUploadDir() {
		return "upload/goods";
	}
}
