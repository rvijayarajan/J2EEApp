package org.appfuse.service.impl;

import java.util.List;

import org.appfuse.dao.GoodsDAO;
import org.appfuse.model.Goods;
import org.appfuse.service.GoodsManager;

public class GoodsManagerImpl extends BaseManager implements GoodsManager {

	private GoodsDAO dao;

	public void setGoodsDAO(GoodsDAO dao) {
		this.dao = dao;
	}

	public Goods getGood(final String goodsid) {
		// TODO Auto-generated method stub
		return dao.getGood(new Long(goodsid));
	}

	public List getGoods(Goods goods) {
		// TODO Auto-generated method stub
		return dao.getGoods(goods);
	}

	public void removeGoods(final String goodsid) {
		// TODO Auto-generated method stub
		dao.removeGoods(new Long(goodsid));
	}

	public String saveGoods(final Goods goods) {
		dao.saveGoods(goods);
		return goods.getGoodsID().toString();
	}

}
