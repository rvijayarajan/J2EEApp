package org.appfuse.service;

import java.util.List;

import org.appfuse.dao.GoodsDAO;
import org.appfuse.model.Goods;

public interface GoodsManager extends Manager {
	
	public void setGoodsDAO(GoodsDAO dao);
	
	public Goods getGood(final String goodsid);
	
	public List getGoods(Goods goods);
	
	public void removeGoods(final String goodsid);
	
	public String saveGoods(final Goods goods);
}
