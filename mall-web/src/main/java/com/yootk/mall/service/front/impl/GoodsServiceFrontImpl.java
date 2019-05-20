package com.yootk.mall.service.front.impl;

import java.util.HashMap;
import java.util.Map;

import com.yootk.common.annotation.Autowired;
import com.yootk.common.annotation.Service;
import com.yootk.common.service.abs.AbstractService;
import com.yootk.mall.dao.IGoodsDAO;
import com.yootk.mall.service.front.IGoodsServiceFront;

@Service
public class GoodsServiceFrontImpl extends AbstractService implements IGoodsServiceFront {
	@Autowired
	private IGoodsDAO goodsDAO;

	@Override
	public Map<String, Object> list(long currentPage, int lineSize, String column, String keyWord) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (super.isEmpty(column, keyWord)) { // 不需要进行模糊查询
			map.put("allGoods", this.goodsDAO.findSplit(currentPage, lineSize));
			map.put("allRecorders", this.goodsDAO.getAllCount());
		} else {
			map.put("allGoods", this.goodsDAO.findSplit(currentPage, lineSize, column, keyWord));
			map.put("allRecorders", this.goodsDAO.getAllCount(column, keyWord));
		}
		return map;
	}

}
