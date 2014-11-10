package org.appfuse.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.dao.DAO;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common methods that they might all use. Can be used for standard CRUD
 * operations.</p>
 *
 * <p><a href="BaseDAOHibernate.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseDAOHibernate extends HibernateDaoSupport implements DAO {
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * @see org.appfuse.dao.DAO#saveObject(java.lang.Object)
     */
    public void saveObject(Object o) {
        getHibernateTemplate().saveOrUpdate(o);
    }

    /**
     * @see org.appfuse.dao.DAO#getObject(java.lang.Class, java.io.Serializable)
     */
    public Object getObject(Class clazz, Serializable id) {
        Object o = getHibernateTemplate().get(clazz, id);

        if (o == null) {
            throw new ObjectRetrievalFailureException(clazz, id);
        }

        return o;
    }

    /**
     * @see org.appfuse.dao.DAO#getObjects(java.lang.Class)
     */
    public List getObjects(Class clazz) {
        return getHibernateTemplate().loadAll(clazz);
    }

    /**
     * @see org.appfuse.dao.DAO#removeObject(java.lang.Class, java.io.Serializable)
     */
    public void removeObject(Class clazz, Serializable id) {
        getHibernateTemplate().delete(getObject(clazz, id));
    }
//  得到拼凑的HQL的Where部分的 >= 条件
	protected String getHQLWhereGE(String hqlField, String hqlValue) {
		//如果hqlValue不为“”或者null则返回条件，否则返回“”
		if (!strIsNull(hqlValue)) {
			return " and " + hqlField + " >= '" + hqlValue + "' ";
		} else
			return "";
	}

	//得到拼凑的HQL的Where部分的 < 条件
	protected String getHQLWhereLt(String hqlField, String hqlValue) {
		//如果hqlValue不为“”或者null则返回条件，否则返回“”
		if (!strIsNull(hqlValue)) {
			return " and " + hqlField + " < '" + hqlValue + "' ";
		} else
			return "";
	}
//	判断一个字符串是否是null或者""或者"null"
	public static boolean strIsNull(String str) {
		if ((str == null) || ("".equals(str)) || ("null".equals(str))) {
			return true;
		} else
			return false;
	}
}
