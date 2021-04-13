/*
 * Created on 2004-12-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.appfuse;    

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author ltf
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class NowDate {  

	//����һ��int��ʱ�䣬���͵�ǰ������ӻ���������õ�һ��������
	public static String strToDateByInt(int i, String operator) {
		//long j = 0;

		Date d = new Date();

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);

		//		System.out.println(d.getTime());

		if ("+".equals(operator)) {
			gc.add(GregorianCalendar.DATE, i);

		} else if ("-".equals(operator)) {
			gc.add(GregorianCalendar.DATE, (-i));
		}

		Date a = gc.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return sdf.format(a);

	}

	//������������⣺����ǵ�ǰ������2005��01��14����������ڼ�ȥ26��51��Ļ����õ��Ľ����2005��2�·��Ժ�����ڣ����Էϳ�������ʹ����������°汾
	//	public static String strToDateByInt(int i, String operator) {
	//		long j = 0;
	//
	//		Date d = new Date();
	//		
	//		if ("-".equals(operator)) {
	//			j = d.getTime() - i * 24 * 3600 * 1000;
	//		} else if ("+".equals(operator)) {
	//			j = d.getTime() + i * 24 * 3600 * 1000;
	//		}
	//		Date a = new Date(j);
	//
	//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	//
	//		// System.out.println(sdf.format(a));
	//		return sdf.format(a);
	//
	//	}

	static final String dateformat = "yyyy-MM-dd";
	 static final String xgsjformat = "yyyy-MM-dd HH:mm:ss";

	public static String getNowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		return sdf.format(new Date());
	}
	public static String getXgsj()  {
		
		SimpleDateFormat sdf = new SimpleDateFormat(xgsjformat);
		return sdf.format(new Date());
	}
	public static String getNowStrIsNullOrEmpty(String sj) {
		if ((sj == null) || ("".equals(sj))) {
			return getNowDate();
		} else {
			return sj;
			//			SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
			//			try {
			//				return sdf.format(sdf.parse(sj));
			//			} catch (ParseException e) {
			//				e.printStackTrace();
			//				return null;
			//			}
		}
	}

	public static String getNowStrIsNullOrEmpty(Date sj) {
		String dt = null;
		if ((sj == null) || ("".equals(sj.toString()))) {

			dt = getNowDate();
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
			dt = sdf.format(sj);
		}

		//        System.out.println("dt------------" + dt);

		return dt;
	}

	//  ���sjΪnull����""���򷵻ص�ǰֵ�����򷵻�ԭֵ
	public static Date getDateStrIsNullOrEmpty(String sj) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		try {
			return sdf.parse(getNowStrIsNullOrEmpty(sj));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	//���sjΪnull����""���򷵻ص�ǰֵ�����򷵻�ԭֵ
	public static Date getDateStrIsNullOrEmpty(Date sj) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		try {
			String dt = null;
			//            System.out.println("getDateStrIsNullOrEmpty====sj====" + sj);
			dt = getNowStrIsNullOrEmpty(sj);
			//            System.out.println("getDateStrIsNullOrEmpty====" + dt);
			return sdf.parse(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	//��һ���ַ�������ת��Ϊ"yyyyy-MM-dd"��ʽ���ַ�
	public static String getStrFromStr(String sj) {
		if (isNullOrSpace(sj)){
			return sj;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		Date dt = getDateStrIsNullOrEmpty(sj);

		return sdf.format(dt);

	}

	//��һ��Date�͵�����ת��Ϊ"yyyyy-MM-dd"��ʽ�ַ���
	public static String getStrFromDate(Date sj) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		Date dt = getDateStrIsNullOrEmpty(sj);

		return sdf.format(dt);
	}
    /**
     * �ж�һ���ַ����Ƿ�Ϊnull���ߡ���
     * 
     * @param s
     * @return
     */
    public static boolean isNullOrSpace(String s) {
        if (s != null && !s.trim().equals("") && !s.trim().equals("null")) {
            return false;
        } else
            return true;
    }

	/**
	 * ���CheckBoxû�б�ѡ�У���valueΪnull�������ת��Ϊ��0������ValueList���Ὣ����Ϊ��������SQL�У�
	 * �����ͻ��ѯ��ȫ�������ݣ����Ǵ���ģ����ԣ�Ӧ����Set��ʱ�����ת����
	 * @param value
	 * @return
	 */
	public static String getCheckNullValue(String value){
		if (isNullOrSpace(value)){
			return "0";
		}else{
			return "1";
		}
		
		
	}

}