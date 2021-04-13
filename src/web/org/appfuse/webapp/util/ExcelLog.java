/*
 * Created on 2004-3-3
 *2004-7-15修改
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
  //	输出流，写入Excel文件中的数据，暂时存入这里，当关闭文件时，才真正的写入Excel文件。
  private FileOutputStream out = null;
  //	保存行号的变量
  private short rownum = 1;
  //引用Workbook的变量
  private HSSFWorkbook wb;
  //	引用sheet的变量
  private HSSFSheet s;
  //	   声明一个行对象引用 declare a row object reference
  private HSSFRow r = null;
  //	   声明一个cell对象引用 declare a cell object reference
  private HSSFCell c = null;
  //	   声明2个Cell的样式
  private HSSFCellStyle cs = null;
  private HSSFCellStyle csRed = null;
  private HSSFCellStyle csBlue = null;
  //	   声明2个字体变量
  private HSSFFont f = null;
  private HSSFFont fRed = null;
  private HSSFFont fBlue = null;

  //创建Excel文件，FN：文件名
  public boolean createFile(String FN) {
    try {
      //将输出流关联到一个文件
      out = new FileOutputStream(FN);
      //	   创建一个新workbook
      wb = new HSSFWorkbook();
      //	   创建一个新sheet
      s = wb.createSheet();

      //wb.setSheetName(0, "Data");
      wb.setSheetName(0, "数据", HSSFWorkbook.ENCODING_UTF_16);

      return true;
    }
    catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  public boolean closeFile() {
    //	   将workbook写到输出流中
    //	   关闭我们的文件
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
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		}
    }

  }

  public void setColorFont() {
    //设置红色的样式
    csRed = wb.createCellStyle();
    fRed = wb.createFont();
    fRed.setFontHeightInPoints( (short) 12);
    fRed.setColor( (short) HSSFFont.COLOR_RED);
    csRed.setFont(fRed);
    //设置蓝色的样式
    csBlue = wb.createCellStyle();
    fBlue = wb.createFont();
    fBlue.setFontHeightInPoints( (short) 12);
    //	  让它变蓝  make it blue
    fBlue.setColor( (short) 0xc);
    csBlue.setFont(fBlue);
  }

  public void setFont() {
    //	   创建2个cell 样式
    cs = wb.createCellStyle();

    //	   创建2个字体对象 create 2 fonts objects
    f = wb.createFont();

    //	  设置字体1 为 12 点类型  set font 1 to 12 point type
    f.setFontHeightInPoints( (short) 12);

    //	  设置cell 样式 set cell stlye
    cs.setFont(f);
  }

  public boolean addRow(String[] sa) {
    try {
      
      if(cs==null)//5-23 add by zs
        //设置普通字体
      setFont();

      // 创建一行，注意第一行的行号为0，
      r = s.createRow(rownum);
      rownum++;

      //  如果传入一个null数组，则插入一个空行
      if (sa != null) {
        for (short cellnum = (short) 0; cellnum < sa.length; cellnum++) {

          // 创建一个字符行cell  create a string cell (see why += 2 in the
          c = r.createCell( (short) (cellnum));

          // 设置这个cell样式为我们定义的第一个样式
          c.setCellStyle(cs);
          // 设置cell的字符串的值为“Test”
          c.setEncoding(HSSFCell.ENCODING_UTF_16);
          //向Cell中填充字符
          String cellValue = sa[cellnum]; //得到数组的值
          //如果是整数
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

  //是否是整数
  private boolean isInt(String val) {
    String s = val; //临时变量

    if (!IsNullOrSpace(s) && s.length() > 1) {
      String firstV = s.substring(0, 1).trim(); //取得头2位
      if (firstV.equals("@")) {
        return true;
      }
      else {
        // 设置这个cell样式为普通样式
        return false;
      }
    }
    else {
      return false;
    }
  }

  public boolean addColorRow(String[] sa) {
    try {
      //设置普通字体
      setFont();
      //设置有颜色的字体
      setColorFont();

      // 创建一行，注意第一行的行号为0，
      r = s.createRow(rownum);
      rownum++;

      //  如果传入一个null数组，则插入一个空行
      if (sa != null) {
        for (short cellnum = (short) 0; cellnum < sa.length; cellnum++) {
          // 创建一个字符行cell
          c = r.createCell( (short) (cellnum));
          String value = sa[cellnum]; //取得数组的元素的值
          // 设置cell的编码
          c.setEncoding(HSSFCell.ENCODING_UTF_16);

          String v = setCS(value); //设置字体样式

          //向Cell中填充字符
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

  //设置字体样式
  private String setCS(String val) {
    String s = val; //临时变量

    if (!IsNullOrSpace(s) && s.length() > 1) {
      String firstV = s.substring(0, 2).trim(); //取得头2位
      if (firstV.equals("X:")) {
        c.setCellStyle(csRed); //红色
        return s.substring(2);
      }
      else if (firstV.equals("D:")) {
        c.setCellStyle(csBlue); //蓝色
        return s.substring(2);
      }
      else {
        // 设置这个cell样式为普通样式
        c.setCellStyle(cs);
        return s;
      }
    }
    else {
      // 设置这个cell样式为普通样式
      c.setCellStyle(cs);
      return s;
    }
  }

  //本方法是个示例，可以直接调用本方法，会自动生成一个excel文件
  public static void test() {
  	
  	ExcelLog el = new ExcelLog();

    String FN = "workbook.xls"; //文件名
    String[] sa = {
        "那一年", "那个春天", "有人说", "一个人的烦恼太多是因为记性太好", "@334"};
    String[] n = {
        "于是我便开始忘掉一些事情", "但唯一忘不了的是那一年的桃花"};

    //创建一个新文件，如果创建失败，则退出该函数。
    if (!el.createFile(FN)) {
      return;
    }

    el.addRow(sa);
    el.addRow(null);
    el.addRow(n);

    if (el.closeFile()) {
      System.out.println("成功！");
    }
    else {
      System.out.println("失败！");
    }

  }
    //原functions 中
  public static boolean IsNullOrSpace(String S) {
    return (S == null || S.trim().equals(""));
  }
  
  
  //向指定位置，写入指定的数值
  public boolean addField(String str, int rownum, int colnum) {
    try {
      //设置普通字体
      setFont();

      // 创建一行，注意第一行的行号为0，
      r = s.createRow(rownum);
      rownum++;

      //  如果传入一个null数组，则插入一个空行
      if (str != null) {
          // 创建一个字符行
          c = r.createCell( (short) (colnum));

          // 设置这个cell样式为我们定义的第一个样式
          c.setCellStyle(cs);
          // 设置cell的字符串的值为“Test”
          c.setEncoding(HSSFCell.ENCODING_UTF_16);
          //向Cell中填充字符
          String cellValue = str; //得到数组的值
          //如果是整数
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
  
  //将模板文件的内容装入流中，然后写内容
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
  //将流中的内容保存为一个新文件
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

  //测试用例
  public static void main(String args[]){
  	
    saveAs();  //测试另存为
    
	test();    //测试新文件，写内容

  }

/**
 * 
 */
private static void saveAs() {
	String FN = "c:\\ltf.xls"; //文件名
    String[] sa = {
        "那一年", "那个春天", "有人说", "一个人的烦恼太多是因为记性太好", "@334"};
    String[] n = {
        "于是我便开始忘掉一些事情", "但唯一忘不了的是那一年的桃花"};

    
    ExcelLog el = new ExcelLog();
    //创建一个新文件，如果创建失败，则退出该函数。
    if (!el.loadFile(FN)) {
      return;
    }

    el.addField("李铁峰", 0, 0);
//    el.addRow(null);
    if (el.saveFile("c:\\ty.xls")) {
      System.out.println("成功！");
    }
    else {
      System.out.println("失败！");
    }
}

/**
 * 苏博士添加的方法
 * @author ltf
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */

/**
 * 
 * @param str			要写入单元格的字符串
 * @param rownum		单元格的行号
 * @param colnum		单元格的列号，字符串表示，不区分大小写
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