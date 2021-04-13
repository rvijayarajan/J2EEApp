/*
 * Created on 2004-3-3
 *2004-7-15�޸�
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

package org.appfuse.webapp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ExcelLog {
  //	�������д��Excel�ļ��е����ݣ���ʱ����������ر��ļ�ʱ����������д��Excel�ļ���
  private FileOutputStream out = null;
  //	�����кŵı���
  private short rownum = 1;
  //����Workbook�ı���
  private HSSFWorkbook wb;
  //	����sheet�ı���
  private HSSFSheet s;
  //	   ����һ���ж������� declare a row object reference
  private HSSFRow r = null;
  //	   ����һ��cell�������� declare a cell object reference
  private HSSFCell c = null;
  //	   ����2��Cell����ʽ
  private HSSFCellStyle cs = null;
  private HSSFCellStyle csRed = null;
  private HSSFCellStyle csBlue = null;
  //	   ����2���������
  private HSSFFont f = null;
  private HSSFFont fRed = null;
  private HSSFFont fBlue = null;

  //����Excel�ļ���FN���ļ���
  public boolean createFile(String FN) {
    try {
      //�������������һ���ļ�
      out = new FileOutputStream(FN);
      //	   ����һ����workbook
      wb = new HSSFWorkbook();
      //	   ����һ����sheet
      s = wb.createSheet();

      //wb.setSheetName(0, "Data");
      wb.setSheetName(0, "����", HSSFWorkbook.ENCODING_UTF_16);

      return true;
    }
    catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  public boolean closeFile() {
    //	   ��workbookд���������
    //	   �ر����ǵ��ļ�
    try {
      wb.write(out);
      out.close();
      return true;
    }
    catch (IOException e) {
      e.printStackTrace();
      
      return false;
    }finally{
    	try {
			out.close();
		} catch (IOException e1) {
			// TODO �Զ����� catch ��
			e1.printStackTrace();
		}
    }

  }

  public void setColorFont() {
    //���ú�ɫ����ʽ
    csRed = wb.createCellStyle();
    fRed = wb.createFont();
    fRed.setFontHeightInPoints( (short) 12);
    fRed.setColor( (short) HSSFFont.COLOR_RED);
    csRed.setFont(fRed);
    //������ɫ����ʽ
    csBlue = wb.createCellStyle();
    fBlue = wb.createFont();
    fBlue.setFontHeightInPoints( (short) 12);
    //	  ��������  make it blue
    fBlue.setColor( (short) 0xc);
    csBlue.setFont(fBlue);
  }

  public void setFont() {
    //	   ����2��cell ��ʽ
    cs = wb.createCellStyle();

    //	   ����2��������� create 2 fonts objects
    f = wb.createFont();

    //	  ��������1 Ϊ 12 ������  set font 1 to 12 point type
    f.setFontHeightInPoints( (short) 12);

    //	  ����cell ��ʽ set cell stlye
    cs.setFont(f);
  }

  public boolean addRow(String[] sa) {
    try {
      
      if(cs==null)//5-23 add by zs
        //������ͨ����
      setFont();

      // ����һ�У�ע���һ�е��к�Ϊ0��
      r = s.createRow(rownum);
      rownum++;

      //  �������һ��null���飬�����һ������
      if (sa != null) {
        for (short cellnum = (short) 0; cellnum < sa.length; cellnum++) {

          // ����һ���ַ���cell  create a string cell (see why += 2 in the
          c = r.createCell( (short) (cellnum));

          // �������cell��ʽΪ���Ƕ���ĵ�һ����ʽ
          c.setCellStyle(cs);
          // ����cell���ַ�����ֵΪ��Test��
          c.setEncoding(HSSFCell.ENCODING_UTF_16);
          //��Cell������ַ�
          String cellValue = sa[cellnum]; //�õ������ֵ
          //���������
          if (isInt(cellValue)) {
            c.setCellValue(Integer.parseInt(cellValue.substring(1)));
          }
          else {
            c.setCellValue(cellValue); 
          }

        }
      }

      return true;
    }
    catch (RuntimeException e) {
      e.printStackTrace();
      return false;
    }

  }

  //�Ƿ�������
  private boolean isInt(String val) {
    String s = val; //��ʱ����

    if (!IsNullOrSpace(s) && s.length() > 1) {
      String firstV = s.substring(0, 1).trim(); //ȡ��ͷ2λ
      if (firstV.equals("@")) {
        return true;
      }
      else {
        // �������cell��ʽΪ��ͨ��ʽ
        return false;
      }
    }
    else {
      return false;
    }
  }

  public boolean addColorRow(String[] sa) {
    try {
      //������ͨ����
      setFont();
      //��������ɫ������
      setColorFont();

      // ����һ�У�ע���һ�е��к�Ϊ0��
      r = s.createRow(rownum);
      rownum++;

      //  �������һ��null���飬�����һ������
      if (sa != null) {
        for (short cellnum = (short) 0; cellnum < sa.length; cellnum++) {
          // ����һ���ַ���cell
          c = r.createCell( (short) (cellnum));
          String value = sa[cellnum]; //ȡ�������Ԫ�ص�ֵ
          // ����cell�ı���
          c.setEncoding(HSSFCell.ENCODING_UTF_16);

          String v = setCS(value); //����������ʽ

          //��Cell������ַ�
          c.setCellValue(v);
        }
      }

      return true;
    }
    catch (RuntimeException e) {
      e.printStackTrace();
      return false;
    }

  }

  //����������ʽ
  private String setCS(String val) {
    String s = val; //��ʱ����

    if (!IsNullOrSpace(s) && s.length() > 1) {
      String firstV = s.substring(0, 2).trim(); //ȡ��ͷ2λ
      if (firstV.equals("X:")) {
        c.setCellStyle(csRed); //��ɫ
        return s.substring(2);
      }
      else if (firstV.equals("D:")) {
        c.setCellStyle(csBlue); //��ɫ
        return s.substring(2);
      }
      else {
        // �������cell��ʽΪ��ͨ��ʽ
        c.setCellStyle(cs);
        return s;
      }
    }
    else {
      // �������cell��ʽΪ��ͨ��ʽ
      c.setCellStyle(cs);
      return s;
    }
  }

  //�������Ǹ�ʾ��������ֱ�ӵ��ñ����������Զ�����һ��excel�ļ�
  public static void test() {
  	
  	ExcelLog el = new ExcelLog();

    String FN = "workbook.xls"; //�ļ���
    String[] sa = {
        "��һ��", "�Ǹ�����", "����˵", "һ���˵ķ���̫������Ϊ����̫��", "@334"};
    String[] n = {
        "�����ұ㿪ʼ����һЩ����", "��Ψһ�����˵�����һ����һ�"};

    //����һ�����ļ����������ʧ�ܣ����˳��ú�����
    if (!el.createFile(FN)) {
      return;
    }

    el.addRow(sa);
    el.addRow(null);
    el.addRow(n);

    if (el.closeFile()) {
      System.out.println("�ɹ���");
    }
    else {
      System.out.println("ʧ�ܣ�");
    }

  }
    //ԭfunctions ��
  public static boolean IsNullOrSpace(String S) {
    return (S == null || S.trim().equals(""));
  }
  
  
  //��ָ��λ�ã�д��ָ������ֵ
  public boolean addField(String str, int rownum, int colnum) {
    try {
      //������ͨ����
      setFont();

      // ����һ�У�ע���һ�е��к�Ϊ0��
      r = s.createRow(rownum);
      rownum++;

      //  �������һ��null���飬�����һ������
      if (str != null) {
          // ����һ���ַ���
          c = r.createCell( (short) (colnum));

          // �������cell��ʽΪ���Ƕ���ĵ�һ����ʽ
          c.setCellStyle(cs);
          // ����cell���ַ�����ֵΪ��Test��
          c.setEncoding(HSSFCell.ENCODING_UTF_16);
          //��Cell������ַ�
          String cellValue = str; //�õ������ֵ
          //���������
          if (isInt(cellValue)) {
            c.setCellValue(Integer.parseInt(cellValue.substring(1)));
          }
          else {
            c.setCellValue(cellValue); 
          }
      }

      return true;
    }
    catch (RuntimeException e) {
      e.printStackTrace();
      return false;
    }

  }
  
  //��ģ���ļ�������װ�����У�Ȼ��д����
  public boolean loadFile(String allFN)
      
  {
    try {
		POIFSFileSystem fs   =
		    new POIFSFileSystem(new FileInputStream(allFN));
		wb = new HSSFWorkbook(fs);
		s = wb.getSheetAt(0);
		
		return true;

	} catch (FileNotFoundException e) {
		e.printStackTrace();
		return false;
	} catch (IOException e) {
		e.printStackTrace();
		return false;
	}

  }
  //�����е����ݱ���Ϊһ�����ļ�
  public boolean saveFile(String fileName){
	File f=new File(fileName);
	try {
		out = new FileOutputStream(f);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
		return false;
	}
	return closeFile();

  }

  //��������
  public static void main(String args[]){
  	
    saveAs();  //�������Ϊ
    
	test();    //�������ļ���д����

  }

/**
 * 
 */
private static void saveAs() {
	String FN = "c:\\ltf.xls"; //�ļ���
    String[] sa = {
        "��һ��", "�Ǹ�����", "����˵", "һ���˵ķ���̫������Ϊ����̫��", "@334"};
    String[] n = {
        "�����ұ㿪ʼ����һЩ����", "��Ψһ�����˵�����һ����һ�"};

    
    ExcelLog el = new ExcelLog();
    //����һ�����ļ����������ʧ�ܣ����˳��ú�����
    if (!el.loadFile(FN)) {
      return;
    }

    el.addField("������", 0, 0);
//    el.addRow(null);
    if (el.saveFile("c:\\ty.xls")) {
      System.out.println("�ɹ���");
    }
    else {
      System.out.println("ʧ�ܣ�");
    }
}

/**
 * �ղ�ʿ��ӵķ���
 * @author ltf
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */

/**
 * 
 * @param str			Ҫд�뵥Ԫ����ַ���
 * @param rownum		��Ԫ����к�
 * @param colnum		��Ԫ����кţ��ַ�����ʾ�������ִ�Сд
 * @return boolean 
 */
public boolean addField(String str, int rownum, String colnum) 
{
  try{
	  	HSSFRow hsRow = s.getRow(rownum-1);
	    HSSFCell hsCell = hsRow.getCell((short)ExcelUnitNoConvert.convetToLong(colnum));
	    if (hsCell == null)
	        hsCell = hsRow.createCell((short)1);
	    hsCell.setEncoding(HSSFCell.ENCODING_UTF_16);
	    hsCell.setCellValue(str);
	    return true;
  }    catch (RuntimeException e) {
      e.printStackTrace();
      return false;
  }
}



}