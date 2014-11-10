/*
 * 创建日期 2005-6-21
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package org.appfuse.webapp.util;

/**
 * @author szy
 * @serialData 2005-6-21
 * 将Excel中的单元格编号转换为poi填入的数字值；
 * 使用时，请使用代理模式即可；
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ExcelUnitNoConvert {
	  //将数字转换成A - Z 的字母
	  public static String convertToString(long num){
	    short i = (short)(( num/26 )-1);
	    short j =(short)( num % 26);
	    String tmp="";
	    if (i>=0)
	      tmp = convertToZm(i) + convertToZm(j);
	    else
	      tmp = convertToZm(j);
	    return tmp;
	  }
	  //将字符转换为数字
	  /**
	   * @param str String不区分大小写
	   * @return short 单个字符返回值从0开始
	   */
	  public static short convetToLong(String str){
	    short mlwz=0;
	    char[] tmp = str.toUpperCase().toCharArray();
	    if (str.toUpperCase().length()>1)
	      mlwz =(short)( (convertToSz( tmp[0])+1)*26 + convertToSz(tmp[1]));
	    else
	      mlwz =(short)( convertToSz(tmp[0]));

	    return mlwz;
	  }

	  //字母转换成数字
	  private static short convertToSz(char str){
	    short tmp=0;
	    switch (str){
	        case 'A': tmp=0;break;
	        case 'B': tmp=1;break;
	        case 'C': tmp=2;break;
	        case 'D': tmp=3;break;
	        case 'E': tmp=4;break;
	        case 'F': tmp=5;break;
	        case 'G': tmp=6;break;
	        case 'H': tmp=7;break;
	        case 'I': tmp=8;break;
	        case 'J': tmp=9;break;
	        case 'K': tmp=10;break;
	        case 'L': tmp=11;break;
	        case 'M': tmp=12;break;
	        case 'N': tmp=13;break;
	        case 'O': tmp=14;break;
	        case 'P': tmp=15;break;
	        case 'Q': tmp=16;break;
	        case 'R': tmp=17;break;
	        case 'S': tmp=18;break;
	        case 'T': tmp=19;break;
	        case 'U': tmp=20;break;
	        case 'V': tmp=21;break;
	        case 'W': tmp=22;break;
	        case 'X': tmp=23;break;
	        case 'Y': tmp=24;break;
	        case 'Z': tmp=25;break;
	    }
	    return tmp;
	  }
	  //数字转换成字母
	  private static String convertToZm(short str){
	    String tmp="";
	    switch (str){
	        case 0 : tmp="A"; break;
	        case 1 : tmp="B"; break;
	        case 2 : tmp="C"; break;
	        case 3 : tmp="D"; break;
	        case 4 : tmp="E"; break;
	        case 5 : tmp="F"; break;
	        case 6 : tmp="G"; break;
	        case 7 : tmp="H"; break;
	        case 8 : tmp="I"; break;
	        case 9 : tmp="J"; break;
	        case 10: tmp="K"; break;
	        case 11: tmp="L"; break;
	        case 12: tmp="M"; break;
	        case 13: tmp="N"; break;
	        case 14: tmp="O"; break;
	        case 15: tmp="P"; break;
	        case 16: tmp="Q"; break;
	        case 17: tmp="R"; break;
	        case 18: tmp="S"; break;
	        case 19: tmp="T"; break;
	        case 20: tmp="U"; break;
	        case 21: tmp="V"; break;
	        case 22: tmp="W"; break;
	        case 23: tmp="X"; break;
	        case 24: tmp="Y"; break;
	        case 25: tmp="Z"; break;
	    }
	    return tmp;
	  }	
	  /**
	   * 测试用
	   * @param args
	   */
	  public static void main(String[] args){
	  	System.out.println(ExcelUnitNoConvert.convetToLong("ae"));
	  	
	  }
}
