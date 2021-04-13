/*
 * �������� 2005-6-21
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package org.appfuse.webapp.util;

/**
 * @author szy
 * @serialData 2005-6-21
 * ��Excel�еĵ�Ԫ����ת��Ϊpoi���������ֵ��
 * ʹ��ʱ����ʹ�ô���ģʽ���ɣ�
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class ExcelUnitNoConvert {
	  //������ת����A - Z ����ĸ
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
	  //���ַ�ת��Ϊ����
	  /**
	   * @param str String�����ִ�Сд
	   * @return short �����ַ�����ֵ��0��ʼ
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

	  //��ĸת��������
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
	  //����ת������ĸ
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
	   * ������
	   * @param args
	   */
	  public static void main(String[] args){
	  	System.out.println(ExcelUnitNoConvert.convetToLong("ae"));
	  	
	  }
}
