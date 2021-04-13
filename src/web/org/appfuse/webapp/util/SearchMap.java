
/*
 * �������� 2005-05-24
 *
 * TODO Ҫ��Ĵ���ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package org.appfuse.webapp.util;


import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import net.mlw.vlh.web.ValueListRequestUtil;

import org.appfuse.Constants;
import org.appfuse.NowDate;

import org.appfuse.webapp.action.BaseAction;
import org.appfuse.webapp.form.GoodsForm;

 
/**
 * @author szy
 * ��
 */
public class SearchMap extends BaseAction {
	//��map��������
	public static void addCondition(Map map, String s, String FieldName) {
		if ((s != null) && (!BaseAction.isNullOrSpace(s))) {
			map.put(FieldName, BaseAction.addLike(s));
		}
	}

	public static void addConditionNoLike(Map map, String s, String FieldName) {
		if (!BaseAction.isNullOrSpace(s)) {
			map.put(FieldName, s);
		}
	}


	public static void addLongCondition(Map map, Long value, String fieldName) {
		if (value != null)
			addConditionNoLike(map, value.toString(), fieldName);
	}

	public static boolean isNullString(String s) {
		return (s == null) || (s == "");
	}


	/**
	 * 
	 * @param map
	 * @param request
	 * @param str
	 */
	private static void addDateCondition(Map map, String value, String str) {
		if ((value != null) && (!isNullOrSpace(value))) {
			addConditionNoLike(map, NowDate.getStrFromStr(value), str);
		}
	}
	
	public static Map getGoodsMap(HttpServletRequest request, GoodsForm frmGoods) {
		Map map = ValueListRequestUtil.getRequestParameterMap(request,
				Constants.goodsID);
		addCondition(map,frmGoods.getGoodname(),"goodname");
		
		return map;
	}
}
