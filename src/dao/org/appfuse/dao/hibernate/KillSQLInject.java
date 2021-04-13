/*
 * 创建日期 2005-7-12
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package org.appfuse.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.type.Type;

import org.springframework.orm.hibernate.HibernateTemplate;

/**
 * @author Administrator
 * 
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class KillSQLInject {
	private ArrayList values = new ArrayList(8);

	private ArrayList types = new ArrayList(8);

	private StringBuffer whereSQL = new StringBuffer();

	private HibernateTemplate ht;

	public KillSQLInject(HibernateTemplate ht) {
		super();
		this.ht = ht;
	}

	private void doAddStringItem(String fieldName, String fieldValue, Type fieldType,
			boolean isLike) {

		//如果字段对象为null，或者为空字符串，则直接退出
		if ((fieldValue == null) || (fieldValue.length() == 0))
			return;

		//拼凑where子句
		if (whereSQL.length() > 0)
			whereSQL.append(" and ");
		String s = fieldName + (isLike ? " like ?" : "=?");
		whereSQL.append(s);

		//添加查询值
		if (isLike) {
			s = "%" + fieldValue + "%";
			values.add(s);
		} else
			values.add(fieldValue);

		//添加参数类型
		types.add(fieldType);
	}

	private void doAddObjectItem(String fieldName, Object fieldValue, Type fieldType) {

		//如果字段对象为null，则直接退出
		if (fieldValue == null)
			return;

		//拼凑where子句
		if (whereSQL.length() > 0)
			whereSQL.append(" and ");
		String s = fieldName + "=?";
		whereSQL.append(s);

		//添加查询值
		values.add(fieldValue);

		//添加参数类型
		types.add(fieldType);
	}

	public void addLikeItem(String fieldName, String fieldValue) {
		doAddStringItem(fieldName, fieldValue, Hibernate.STRING, true);
	}

	public void addStringItem(String fieldName, String fieldValue) {
		doAddStringItem(fieldName, fieldValue, Hibernate.STRING, false);
	}

	public void addIntegerItem(String fieldName, Long fieldValue) {
		if (fieldValue != null)
			doAddObjectItem(fieldName, fieldValue, Hibernate.LONG);
	}

	public List find(String fromSQL, String whereEx) {
		//拼凑最终的sql语句
		StringBuffer hql = new StringBuffer(fromSQL);
		if (whereSQL.length() > 0) {
			if (fromSQL.toLowerCase().indexOf("where") == -1)
				hql.append(" where ");
			hql.append(whereSQL);
		}

		//填充where语句后缀子句，例如order by 或group by等语句
		if (whereEx != null){
			hql.append(' ');
			hql.append(whereEx);
		}
			

		Type[] typelist = new Type[types.size()];
		System.arraycopy(types.toArray(), 0, typelist, 0, types.size());

		return ht.find(hql.toString(), values.toArray(), typelist);
	}

	public List find(String fromSQL) {
		return find(fromSQL, null);
	}
}