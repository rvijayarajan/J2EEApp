package org.appfuse.dao.hibernate;

import java.util.List;

import org.appfuse.dao.RoleAppDAO;
import org.appfuse.model.RoleApp;

public class RoleAppDAOHibernate extends BaseDAOHibernate implements RoleAppDAO {

	public RoleApp getAppRole(String rolename) {

		return (RoleApp) getHibernateTemplate().get(RoleApp.class,rolename);
	}

	public List getAppRoles(RoleApp role) {
		if (role==null)
			return getHibernateTemplate().find("from RoleApp");
		else {
			KillSQLInject ksi = new KillSQLInject(getHibernateTemplate());
			ksi.addLikeItem("rolename", role.getRolename());
			ksi.addLikeItem("description", role.getDescription());
			return ksi.find(" from RoleApp");
		}
	}

	public void removeAppRole(final String rolename) {
		Object roleapp = getHibernateTemplate().load(RoleApp.class,rolename);
		getHibernateTemplate().delete(roleapp);
	}

	public void saveAppRole(final RoleApp role) {
		getHibernateTemplate().saveOrUpdate(role);

	}

}
