package org.appfuse.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.appfuse.model.OpRegister;
import org.appfuse.model.SysUser;
import org.appfuse.dao.SysUserDAO;

public class SysUserDAOHibernate extends BaseDAOHibernate implements SysUserDAO {

	/**
	 * @see org.appfuse.dao.SysUserDAO#getSysUsers(org.appfuse.model.SysUser)
	 */
	public List getSysUsers(SysUser sysUser) {
		// use the sysUser parameter to do futher filtering if you need to
		KillSQLInject ksi = new KillSQLInject(getHibernateTemplate());
		ksi.addIntegerItem("a.id", 
				sysUser.getId()==null?null:(Long.parseLong(sysUser.getId().toString())));
		return ksi.find("from SysUser as a");
	}

	/**
	 * @see org.appfuse.dao.SysUserDAO#getSysUser(final Long id)
	 */
	public SysUser getSysUser(final Long id) {
		if (id == null)
			return null;
		else {
			KillSQLInject ksi = new KillSQLInject(getHibernateTemplate());
			ksi.addIntegerItem("a.id", id);
			return (SysUser) ksi.find("from SysUser as a").get(0);
		}
	}

	/**
	 * @see org.appfuse.dao.SysUserDAO#saveSysUser(SysUser sysUser)
	 */
	public void saveSysUser(final SysUser sysUser) {
		getHibernateTemplate().saveOrUpdate(sysUser);
	}

	/**
	 * @see org.appfuse.dao.SysUserDAO#removeSysUser(final Long id)
	 */
	public void removeSysUser(final Long id) {
		getHibernateTemplate().delete(getSysUser(id));
	}

	public SysUser getSysUser(String userName) {
		if (isNullOrSpace(userName)) {
			return null;
		} else {
			KillSQLInject ksi = new KillSQLInject(getHibernateTemplate());
			ksi.addStringItem("username", userName);
			List sys = ksi.find(" from SysUser");
			if(0 != sys.size())
			{
				return (SysUser) ksi.find(" from SysUser").get(0);
			}
			else
				return null;
		}
	}

	public static boolean isNullOrSpace(String s) {
		if (s != null && !s.trim().equals("") && !s.trim().equals("null")) {
			return false;
		} else
			return true;
	}

	public SysUser getSysUserByRealname(String realname) {
		// TODO Auto-generated method stub
		if (strIsNull(realname))
			return null;
		else {
			KillSQLInject ksi = new KillSQLInject(getHibernateTemplate());
			ksi.addStringItem("a.realname", realname);
			List ls = ksi.find("from SysUser as a");
			if (ls.size() >= 1) {
				return (SysUser) ls.get(0);
			} else {
				return null;
			}
		}
	}

}