package org.appfuse.dao.hibernate;

import java.util.List;

import org.appfuse.Resource;
import org.appfuse.dao.OpRegisterDAO;
import org.appfuse.model.OpRegister;


public class OpRegisterDAOHibernate extends BaseDAOHibernate implements
		OpRegisterDAO {


	public OpRegister getOpRegister(Long id) {
		OpRegister op =(OpRegister) getHibernateTemplate().get(OpRegister.class,id);
		return op;
	}

	public List getOpRegisters(OpRegister opregister) {
		String startID=String.valueOf(Resource.getStartMenuID() );
		if (opregister==null)
			return getHibernateTemplate().find("from OpRegister as opr where id >=" +startID +" order by opr.id asc");
		else{
			KillSQLInject ksi = new KillSQLInject(getHibernateTemplate());
			ksi.addIntegerItem("id", new Long(opregister.getId()));
			ksi.addLikeItem("CDMC", opregister.getCdmc());
			return ksi.find("from OpRegister as opr where id >=" +startID +" order by opr.id asc");
		}
	}

	public void removeOpRegister(final Long id) {
		Object op =  getHibernateTemplate().load(OpRegister.class,id);
		getHibernateTemplate().delete(op);
	}

	public void saveOpRegister(final OpRegister opregister) {
		getHibernateTemplate().saveOrUpdate(opregister);
	}

}
