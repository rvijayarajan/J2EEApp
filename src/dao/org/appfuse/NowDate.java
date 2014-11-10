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

	//传入一个int的时间，经和当前日期相加或者相减，得到一个新日期
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

	//这个方法有问题：如果是当前日期是2005－01－14，将这个日期减去26－51天的话，得到的结果是2005年2月份以后的日期，所以废除，建议使用上面的最新版本
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

	//  如果sj为null或者""，则返回当前值，否则返回原值
	public static Date getDateStrIsNullOrEmpty(String sj) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		try {
			return sdf.parse(getNowStrIsNullOrEmpty(sj));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	//如果sj为null或者""，则返回当前值，否则返回原值
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

	//将一个字符型数据转换为"yyyyy-MM-dd"形式的字符
	public static String getStrFromStr(String sj) {
		if (isNullOrSpace(sj)){
			return sj;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		Date dt = getDateStrIsNullOrEmpty(sj);

		return sdf.format(dt);

	}

	//将一个Date型的数据转换为"yyyyy-MM-dd"形式字符型
	public static String getStrFromDate(Date sj) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		Date dt = getDateStrIsNullOrEmpty(sj);

		return sdf.format(dt);
	}
    /**
     * 判断一个字符串是否为null或者“”
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
	 * 如果CheckBox没有被选中，则value为null，如果不转换为“0”，则ValueList不会将它作为条件传入SQL中，
	 * 这样就会查询出全部的数据，这是错误的，所以，应该在Set的时候进行转化。
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