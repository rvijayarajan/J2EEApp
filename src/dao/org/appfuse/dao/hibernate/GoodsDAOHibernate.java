package org.appfuse.dao.hibernate;

import java.util.List;

import org.appfuse.dao.GoodsDAO;
import org.appfuse.model.Goods;
import org.springframework.orm.ObjectRetrievalFailureException;

public class GoodsDAOHibernate extends BaseDAOHibernate implements GoodsDAO {
	
	public Goods getGood(Long goodsid){
		Goods good=(Goods) getHibernateTemplate().get(Goods.class, goodsid);
		if (good==null){
            log.warn("uh oh, sbsy with id '" + goodsid + "' not found...");
            throw new ObjectRetrievalFailureException(Goods.class, goodsid);
        }
		return good;
	}
	
	public List getGoods(Goods goods){
		if (goods==null)
			return getHibernateTemplate().find("from Goods");
		else{
			KillSQLInject ksi = new KillSQLInject(getHibernateTemplate());
			ksi.addLikeItem("a.Goodname", goods.getGoodname());
			ksi.addLikeItem("a.rfidID", goods.getRfidID());
			ksi.addLikeItem("a.kind", goods.getRfidID());
			ksi.addLikeItem("a.spec", goods.getSpec());

			return ksi.find("from Goods as a");
		}
		
	}
	
	public void removeGoods(final Long goodsid){
		getHibernateTemplate().delete(getGood(goodsid));
	}
	
	public void saveGoods(final Goods goods){
		getHibernateTemplate().saveOrUpdate(goods);
		
	}
}
