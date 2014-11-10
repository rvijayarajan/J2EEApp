package org.appfuse.dao;

import java.util.List;

import org.appfuse.model.Goods;

public interface GoodsDAO extends DAO {
	public Goods getGood(Long goodsid);
	
	public List getGoods(Goods goods);
	
	public void removeGoods(final Long goodsid);
	
	public void saveGoods(final Goods goods);
	
}
