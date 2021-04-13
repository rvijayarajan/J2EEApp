/*
 * �������� 2005-7-12
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
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
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת�� ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
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

		//����ֶζ���Ϊnull������Ϊ���ַ�������ֱ���˳�
		if ((fieldValue == null) || (fieldValue.length() == 0))
			return;

		//ƴ��where�Ӿ�
		if (whereSQL.length() > 0)
			whereSQL.append(" and ");
		String s = fieldName + (isLike ? " like ?" : "=?");
		whereSQL.append(s);

		//��Ӳ�ѯֵ
		if (isLike) {
			s = "%" + fieldValue + "%";
			values.add(s);
		} else
			values.add(fieldValue);

		//��Ӳ�������
		types.add(fieldType);
	}

	private void doAddObjectItem(String fieldName, Object fieldValue, Type fieldType) {

		//����ֶζ���Ϊnull����ֱ���˳�
		if (fieldValue == null)
			return;

		//ƴ��where�Ӿ�
		if (whereSQL.length() > 0)
			whereSQL.append(" and ");
		String s = fieldName + "=?";
		whereSQL.append(s);

		//��Ӳ�ѯֵ
		values.add(fieldValue);

		//��Ӳ�������
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
		//ƴ�����յ�sql���
		StringBuffer hql = new StringBuffer(fromSQL);
		if (whereSQL.length() > 0) {
			if (fromSQL.toLowerCase().indexOf("where") == -1)
				hql.append(" where ");
			hql.append(whereSQL);
		}

		//���where����׺�Ӿ䣬����order by ��group by�����
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