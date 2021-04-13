package exportexcel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;

import javax.naming.NamingException;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.appfuse.webapp.action.BaseAction;


/**
 * <p>Title: 托管系统数据导出</p>
 * <p>Description: 从留学生托管系统数据库中提取符合条件的数据，进行规范性检查，生成相应的excel文件（必须符合数据接口规范）</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author zs
 * @version 1.0
 */
public class DatabaseExport
    {
  private int COUNT = 500; //为降低内存消耗，按每COUNT条一次写入EXCEL文件，同时清空保存数据的hash表
  private int COLNUM = 0; //列总数
  private int ROWNUM = 0; //行总数（包括字段名行）
  private Hashtable data = new Hashtable(); //用于存储从数据库读到的数据
//  private Hashtable name = new Hashtable(); //用于存储从数据库读到的字段名
  private HSSFWorkbook wb = new HSSFWorkbook();
  private ExportSetting exportSetting = new ExportSetting();
  private Hashtable style = new Hashtable();
  private boolean correctTag=true;
  private boolean fileTag=false;
  private int SB=Integer.MIN_VALUE;
  public int YEAR=Integer.MIN_VALUE;
  public String BZ=null;
  private String yxbm="";
  private CriterionData cd = new CriterionData();
  private Hashtable name = new Hashtable();//从txt文件读到的字段属性
  private String DETAIL=null;

  
  /**
 * @return Returns the dETAIL.
 */
public String getDETAIL() {
    return DETAIL;
}



/**
   * 初始化生成excel文件所需的格式
   *
   */public void initExcel(){
      style.clear();
      /**
       * 以下是初始化单元格样式
       * 用不同的样式表示不同的数据错误
       */
      HSSFCellStyle style1 = null; //生成的Excel文件的单元格样式1
      HSSFCellStyle style2 = null; //生成的Excel文件的单元格样式2
      HSSFCellStyle style3 = null; //生成的Excel文件的单元格样式3
      HSSFCellStyle style4 = null; //生成的Excel文件的单元格样式4
      HSSFCellStyle style5 = null; //生成的Excel文件的单元格样式5
      HSSFCellStyle style6 = null; //生成的Excel文件的单元格样式6
      HSSFCellStyle style7 = null; //生成的Excel文件的单元格样式7
      HSSFCellStyle style8 = null; //生成的Excel文件的单元格样式8
      
      style1 = wb.createCellStyle();
      style2 = wb.createCellStyle();
      style3 = wb.createCellStyle();
      style4 = wb.createCellStyle();
      style5 = wb.createCellStyle();
      style6 = wb.createCellStyle();
      style7 = wb.createCellStyle();
      style8 = wb.createCellStyle();
      
      //HSSFFont font1 = wb.createFont();
      /*
      HSSFFont font2 = wb.createFont();
      HSSFFont font3 = wb.createFont();
      HSSFFont font4 = wb.createFont();
      HSSFFont font5 = wb.createFont();
      HSSFFont font6 = wb.createFont();
      HSSFFont font7 = wb.createFont();
      */
      style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      style1.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
      //style1用于表示“必填项为空”的错误
      /*
      font2.setColor(HSSFColor.DARK_YELLOW.index);
      font2.setBoldweight((short)1);
      style2.setFont(font2);
      font3.setColor(HSSFColor.GREEN.index);
      font3.setBoldweight((short)1);
      style3.setFont(font3);
      font4.setColor(HSSFColor.BROWN.index);
      font4.setBoldweight((short)1);
      style4.setFont(font4);
      font5.setColor(HSSFColor.BLUE.index);
      font5.setBoldweight((short)1);
      style5.setFont(font5);
      font6.setColor(HSSFColor.CORAL.index);
      font6.setBoldweight((short)1);
      style6.setFont(font6);
      font7.setColor(HSSFColor.RED.index);
      font7.setBoldweight((short)1);
      style7.setFont(font7);
      */
      style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      style2.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
      style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      style3.setFillForegroundColor(HSSFColor.GREEN.index);
      style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      style4.setFillForegroundColor(HSSFColor.BROWN.index);
      style5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      style5.setFillForegroundColor(HSSFColor.BLUE.index);
      style6.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      style6.setFillForegroundColor(HSSFColor.CORAL.index);
      style7.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      style7.setFillForegroundColor(HSSFColor.RED.index);
      style8.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      style8.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
      
      style.put("1",style1);
      style.put("2",style2);
      style.put("3",style3);
      style.put("4",style4);
      style.put("5",style5);
      style.put("6",style6);
      style.put("7",style7);
      style.put("8",style8);
  }

  /**
   * 构造函数
   */
  public DatabaseExport() {
    super();
    setSb();
  }

  /**
   * setExportSetting：使用该函数可以设置执行本程序所需的所有信息
   * @param fileName：文件名。将查出的数据以该文件名存储
   * @param year：上报年度
   * @param filepath:路径。这是保存文件的目录
   *
   */
  public void setExportSetting( //String viewName,
                               String fileName, int year,String filepath) {
    //this.exportSetting.setOwner(owner);@param owner：数据库用户名
    //this.exportSetting.setViewName(viewName);
      //@param viewName：视图名，通过该视图查询到符合规范的数据，且用该视图查出的数据和tableName所指示的表结构相同
    this.exportSetting.setFileName(fileName);
    this.exportSetting.setYear(year);
    this.exportSetting.setFilepath(filepath);
 

  }
  
  private void setSb()
  {
      
      String sql="select id,cfwz,fwmc,bz from datacenter.t_sbsy where id=(select max(id) from datacenter.t_sbsy)";
      Connection conn=null;
      PreparedStatement ps=null;
      ResultSet rs=null;
     try {
            conn=DatabaseConnect.getPooledConnection();
              ps=conn.prepareStatement(sql);
              rs=ps.executeQuery();
              if(rs.next())
              {
                  SB=rs.getInt(1);
                  DETAIL=rs.getString(2);
                  YEAR=Integer.parseInt(rs.getString(3));
                  BZ=rs.getString(4);
              }else
                  throw new NullPointerException();
        } catch (NamingException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } catch (NullPointerException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }catch (Exception e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }finally{
            try{
                rs.close();
                ps.close();
                if(!conn.isClosed())
                conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
      
  }
  
  
  /**
   * 查询上报状态
   * @return 上报状态
   上报的五种状态：
   1：高校未上报（初始态）
   0：高校已上报，教委未审核或审核未通过
   7：教委审核通过
   8：教委已上报，教育部未审核或审核未通过
   9：教育部审核完毕或强制关闭上报
   另有程序中专用的中间态（数据库并不会用到）
   2：高校数据未建立
   3：有错误的短期团组和基本信息
   4：有错误的短期团组
   5：有错误的基本信息
   6：无错误数据，显示“继续上报”
   0：无错误数据
   10：后台程序运行异常
   30：高校上报失败

   */
  public int checkState(){
      /*if(SB==Integer.MIN_VALUE)
          setSb();
          在构建函数中已经调用了setSb()*/
      String sql="select t.sjsbzt from datacenter.t_yxztsy t where t.sbdw=? and t.sbsypid="+SB;
      Connection conn=null;
      PreparedStatement ps=null;
      ResultSet rs=null;
      int flag = 2;//如果从数据库读不到数据，2就是程序默认初始态
      try {
          conn=DatabaseConnect.getPooledConnection();
          ps=conn.prepareStatement(sql);
          ps.setString(1,yxbm);
          rs=ps.executeQuery();
          while(rs.next())
          {
              flag = rs.getInt(1); 
          }
    } catch (Exception e) {
        e.printStackTrace();
    }finally{
    	try{
    		rs.close();
            ps.close();
            if(!conn.isClosed())
            conn.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    return flag;
  }
  
  /**
   * 更新上报状态
   * @param flag 更新后的状态
   */public void updateState(String flag){
      String sql="update datacenter.t_yxztsy t set t.sjsbzt = ? where t.sbdw=?";
      Connection conn=null;
      PreparedStatement ps=null;
      ResultSet rs=null;
      try {
          conn=DatabaseConnect.getPooledConnection();
          ps=conn.prepareStatement(sql);
          ps.setString(1, flag);
          ps.setString(2,yxbm);
          rs=ps.executeQuery();
      } catch (Exception e) {
          e.printStackTrace();
    }finally{
    	try{
    		rs.close();
            ps.close();
            if(!conn.isClosed())
                conn.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
  }
  
  /**
   * 由单位id得到院校编码
   * @param dwid 单位id
   */
  public void getBM(int dwid)
  {
      
      String sql="select t.bm from bm_jcdw t where t.id=?";
      Connection conn=null;
      PreparedStatement ps=null;
      ResultSet rs=null;
      
      try {
          conn=DatabaseConnect.getPooledConnection();
          ps=conn.prepareStatement(sql);
          ps.setInt(1,dwid);
          rs=ps.executeQuery();
          if(rs.next())
          {
              yxbm=rs.getString(1);
          }
          else
          {
              //System.out.println("没有这个单位");
          }
          ps.close();
          rs.close();
    } catch (Exception e) {
        // TODO 自动生成 catch 块
        e.printStackTrace();
    }
    finally{
        try {
            if(!conn.isClosed())
            {
                conn.close();
            }
        } catch (SQLException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
    }
      
      
      
  }
  
  /**
   * 初始化 规范性检查 所需相关数据
   * @param filepath 存放字段信息的txt文件路径
   * @param dwid 单位id
   */
  public void getColumn(String filepath,String dwid){
      ReadTxt rt= new ReadTxt();
      name=rt.readtxt(filepath);//获取字段信息
      COLNUM = name.size();//获取字段个数
      cd.getCriteDatabase(name);//初始化规范数据
      cd.getCriteTxt(name);//初始化规范数据
      cd.getConditionTxt(name);//初始化条件必填数据
      cd.getSpecialData(dwid);//初始化短期生检查数据
  }

  /**
   * 从数据库查询出符合规范的数据
   * @param sql 查询语句
   */
  private void getData(String sql) {
      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
          conn=DatabaseConnect.getPooledConnection();
          
          ps = conn.prepareStatement(sql);
          rs = ps.executeQuery();
          short rownum = 0;
          ROWNUM=0;
          fileTag=false;
          //short fileNum = 1;
          String next_szyx = ""; //第一个所在院校
          String fileName = this.exportSetting.getFileName();
          int count=1;
          while (rs.next()) { //依次读出查询到的数据
              rownum++;
              String pre_szyx = next_szyx;
              next_szyx = rs.getString("yxmc");
              //yxbm=rs.getString("yxbm");
              if ( ( (rownum % COUNT == 1) && rownum != 1)||( (!next_szyx.equals(pre_szyx)) && (!pre_szyx.equals(""))) ) {
                  ROWNUM += (rownum-1);
                  if(this.exportSetting.getFileName().indexOf("error")!=-1){
                      this.writeExcel(wb);
                      this.specialCheck(wb);
                      this.checkYjlxsj(wb);//检查预计离校时间的必填性
                  }
                  else
                      this.writeExcelNoCheck(wb);
                  data.clear();
                  if(( (!next_szyx.equals(pre_szyx)) && (!pre_szyx.equals(""))))
                  {
                      count=1;
                      if (fileName.indexOf("error") != -1) {
                          //System.out.println("当前院校为" + pre_szyx + ",已导出错误数据" + ROWNUM +
                          //"条");
                          correctTag=false;
                      }
                      else {
                          //System.out.println("当前院校为" + pre_szyx + ",已导出正确数据" + ROWNUM +
                          //"条");
                      }
                  }
                  this.exportSetting.setFileName(fileName + pre_szyx + String.valueOf(count)+ ".xls");
                  saveFile(wb,this.exportSetting.getFilepath());
                  wb.removeSheetAt(0);
                  count++;
                  

                 rownum=1;     
                  
              }          

              for (int i = 1; i <= COLNUM; i++) {
                  ExportData ed = new ExportData();
                  //ed用于存储数据、序号（在Excel文件中的列号）、在Excel文件中的行号
                  ExportColumn column = (ExportColumn) name.get(String.valueOf(i));
                  //column是data中key为列号的value，即存放字段名的ExportData
                  try {
                      ed.setData(rs.getString(column.getData())); //数据
                      ed.setColNum( (short) i); //列号
                      ed.setRowNum(rownum); //行号
                  }
                  catch (SQLException ex) {
                      ex.printStackTrace();
                  }
                  
                  data.put(column.getData() + String.valueOf(ed.getRowNum()), ed);
                  //key是字段名+行号
                  
              }
              
          }
          if (rownum>=1) {
              ROWNUM += rownum;
              this.exportSetting.setFileName(fileName + next_szyx + String.valueOf(count) + ".xls");
              if(this.exportSetting.getFileName().indexOf("error")!=-1){
                  this.writeExcel(wb);
                  this.specialCheck(wb);
                  this.checkYjlxsj(wb);//检查预计离校时间的必填性
              }
              else
                  this.writeExcelNoCheck(wb);
              saveFile(wb,this.exportSetting.getFilepath());
              wb.removeSheetAt(0);
              data.clear();
              if (fileName.indexOf("error") != -1) {
                  //System.out.println("当前院校为" + next_szyx + ",已导出错误数据" + ROWNUM + "条");
                  correctTag=false;
              }
              else {
                  //System.out.println("当前院校为" + next_szyx + ",已导出正确数据" + ROWNUM + "条");
              }
          }
          else
          {
              //System.out.println("没有符合要求的数据。");
          }
          try {
              rs.close();
              ps.close();
              if(!conn.isClosed())
                  conn.close();
          }
          catch (SQLException ex2) {
              ex2.printStackTrace();
          }
          
      }
      catch (Exception e) {
          e.printStackTrace();
      }finally{
        try{
            rs.close();
            ps.close();
            if(!conn.isClosed())
                conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
  }


  /**
   * 检查预计离校时间的必填性
   * 在学生类别为：本科生、硕士研究生、博士研究生时，学生所学专业一级学科为医学类,
   * 学生状态为报到后，预计离校时间为必填项；
   * @param wb
   */private void checkYjlxsj(HSSFWorkbook wb) {
    
       HSSFSheet st=wb.getSheetAt(0);
       ExportColumn ec = (ExportColumn) name.get("22"); //从name中取出字段名
       for(int i=1;i<=ROWNUM;i++)
       {
           HSSFRow row =st.getRow(i);
           if(row!=null)
           {
               HSSFCell cell=row.getCell((short)21);//预计离校时间
               if((cell==null||cell.getStringCellValue().equals(""))&&row.getCell((short)4)!=null&&row.getCell((short)2)!=null&&row.getCell((short)12)!=null)//这个的序号=book3的序号-1，因为hssfworkbook是从0开始计数的！
               {//依次是预计离校时间、学生类别、二级学科、学生状态
                   //当预计校时间为空或空格，而其他三项不是空的时候才用进行这项检查
                   String xslb=row.getCell((short)4).getStringCellValue();
                   String ejxk=row.getCell((short)2).getStringCellValue();
                   String xszt=row.getCell((short)12).getStringCellValue();
                   if((xslb.equals("本科生")||xslb.equals("硕士研究生")||xslb.equals("博士研究生"))&&xszt.equals("报到"))
                   {
                       if(ejxk!=null)
                           //book3的倒数第二项表示是否必填，倒数第一项表示必填条件，获取必填条件时并没有判断是否必填，因此把预计离校时间
                           //设置为非必填、有必填条件，便于在这里进行二级学科这个必填条件的判断
                           if(cd.conditionTable.containsKey(ec.getChinesedata()+ejxk))
                           {
                               cell.setCellStyle((HSSFCellStyle)style.get(String.valueOf(1)));//style1表示必填项为空
                           }
                   }
               }
           }
       }
}



/**
   * 将检查过的不规范数据写入EXCEL文件
   * @param wb 待写入数据的excel文件
   */
  public void writeExcel(HSSFWorkbook wb) {
    HSSFSheet st = null;
    
      st = wb.createSheet();
      /**
       * 写字段名（第一行）
       */
      st.createRow(0);
      for (int i = 1; i <= COLNUM; i++) {

        HSSFCell cell = st.getRow(0).createCell( (short) (i - 1));
        ExportColumn ec = (ExportColumn) name.get(String.valueOf(i)); //从data中取出字段名
        if (ec.getData() != null) {
          cell.setCellType(HSSFCell.CELL_TYPE_STRING);
          cell.setEncoding(HSSFCell.ENCODING_UTF_16);
          cell.setCellValue(ec.getChinesedata());
        }
      }
    int startRow = ( (ROWNUM % COUNT) == 0) ? COUNT : (ROWNUM % COUNT);
    for (int i = 1; i <= startRow; i++) {
      st.createRow(i); //创建行
    }
    /**
     * 写数据，每次写一列，循环COLNUM-1次
     * 
     */
    for (int i = 1; i <= COLNUM; i++) {
        ExportColumn ec = (ExportColumn) name.get(String.valueOf(i)); //从name中取出字段名
        
        
      for (int j = 1; j <= startRow; j++) {
        HSSFCell cell = st.getRow(j).createCell( (short) (i - 1));
        ExportData cellData = (ExportData) data.get(ec.getData() +
            String.valueOf(j)); //以字段名和行号为key，从data中取出数据

        short errorCode=0;
        if(cellData.getData()==null||cellData.getData().equals(""))
        {
            
            if(ec.getChinesedata().equals("报到时间"))
            {
                ExportData cellData_TLQX = (ExportData) data.get("tlqx" +
                        String.valueOf(j)); //以字段名和行号为key，从data中取出数据
                ExportData cellData_DQTZ=(ExportData) data.get("dqtz"+String.valueOf(j));
                
                if(cellData_TLQX!=null&&cellData_DQTZ!=null)
                {
                    String tlqx="";
                    String dqtz="";
                    tlqx=cellData_TLQX.getData();
                    dqtz=cellData_DQTZ.getData();
                    if(tlqx!=null&&dqtz!=null&&tlqx.equals("短期")&&
                            !(dqtz.equals("无团组")||dqtz.equals("")))//有团组的短期生的报到时间必填
                        
                        ec.setIs_Nullable((short)1);
                    else
                        ec.setIs_Nullable((short)2);
                    
                }
                else
                    ec.setIs_Nullable((short)2);
            }
            if(ec.getIs_Nullable()==2)//有条件必填
            {
                String nullableCondition=ec.getNullableCondion();
                int tag=nullableCondition.indexOf("=");
                if(tag!=-1)
                {
                    String col=nullableCondition.substring(0,tag);
                    ExportColumn ec_condition=(ExportColumn)name.get(col);//列属性
                    ExportData cellData_condition=(ExportData)data.get(ec_condition.getData()+String.valueOf(j));//以列名+行号为key，取出数据
                    if(cellData_condition!=null)
                        if(cellData_condition.getData()!=null)
                            if(cd.conditionTable.containsKey(ec.getChinesedata()+cellData_condition.getData()))
                                
                                errorCode=1;
                }
            }
            else if(ec.getIs_Nullable()==1)
                errorCode=1;
            
            
        }
        else
        {
            errorCode=CheckData.checkData(cellData.getData(),ec.getColNum(),name,cd);            
        }

        
          cell.setCellType(HSSFCell.CELL_TYPE_STRING);
          cell.setEncoding(HSSFCell.ENCODING_UTF_16);
          
          if(cellData.getData()!=null)
          {
              
              String cellValue = cellData.getData().trim();
              
              cell.setCellValue(cellValue);
          }
          else
              cell.setCellValue("");
          /**
           * 可能的错误代码组合方式有：
           * 0——无错
           * 1——必填项为空——1
           * 2——超过最大长度——2
           * 3——待规范项不规范——3
           * 4——日期或数字型不规范——4
           * 5——超过最大长度、待规范项不规范——5
           * 6——超过最大长度、日期或数字型不规范——6 
           */
          if(errorCode!=0)
          cell.setCellStyle((HSSFCellStyle)style.get(String.valueOf(errorCode)));
        

      }

    }
    


  }
  
  /**
   * 将检查过的规范数据写入excel文件
   * @param wb 带写入数据的excel文件
   */
  public void writeExcelNoCheck(HSSFWorkbook wb){
      HSSFSheet st = null;
      
      st = wb.createSheet();
      /**
       * 写字段名（第一行）
       */
      st.createRow(0);
      for (int i = 1; i <= COLNUM; i++) {

        HSSFCell cell = st.getRow(0).createCell( (short) (i - 1));
        ExportColumn ec = (ExportColumn) name.get(String.valueOf(i)); //从data中取出字段名
        if (ec.getData() != null) {
          cell.setCellType(HSSFCell.CELL_TYPE_STRING);
          cell.setEncoding(HSSFCell.ENCODING_UTF_16);
          cell.setCellValue(ec.getChinesedata());
        }
      }
      int startRow = ( (ROWNUM % COUNT) == 0) ? COUNT : (ROWNUM % COUNT);
      for (int i = 1; i <= startRow; i++) {
        st.createRow(i); //创建行
      }
      /**
       * 写数据，每次写一列，循环COLNUM-1次
       * 
       */
      for (int i = 1; i <= COLNUM; i++) {
          ExportColumn ec = (ExportColumn) name.get(String.valueOf(i)); //从name中取出字段名
          
          
        for (int j = 1; j <= startRow; j++) {
          HSSFCell cell = st.getRow(j).createCell( (short) (i - 1));
          ExportData cellData = (ExportData) data.get(ec.getData() +
              String.valueOf(j)); //以字段名和行号为key，从data中取出数据
          cell.setCellType(HSSFCell.CELL_TYPE_STRING);
          cell.setEncoding(HSSFCell.ENCODING_UTF_16);
          
          if(cellData.getData()!=null)
          {
              
              String cellValue = cellData.getData().trim();
              
              cell.setCellValue(cellValue);
          }
          else
              cell.setCellValue("");
        }
      }
  } 
  /**
   * 将符合要求的数据插入细节数据库，并调用存储过程TONGJIBYDYNAMIC(院校编码,上报批次)，将统计结果插入中心数据库
   * @param year 上报年度
   * @param xgr 修改人
   * @return 成功/不成功
   */public boolean saveData (int year,String xgr)
  {
      /*if(DETAIL==null)
          setSb();
       在构建函数中已经调用了setSb()*/
      /*String sql_del1="delete from "+DETAIL+".t_jbxx where szyx='"+yxbm+"'";
      String sql_del2="delete from "+DETAIL+".t_dqtzgj where pid in (select id from "+DETAIL+".t_dqtz where szyx='"+yxbm+"')";
      String sql_del3="delete from "+DETAIL+".t_dqtz where szyx='"+yxbm+"'";
      String sql_del4="update datacenter.t_yxztsy t set t.sjsbzt=1 where t.sbdw='"+yxbm+"'";
      *///存储到数据库
      boolean flag=false;
      //insert into "+DETAIL+".t_jbxx select * from v_crite_jbxx_tg where dwid=?
      String sql1 = "insert into "+DETAIL+".t_dqtz(PID,TZMC,LHRQ,SZYX,YX,ZY,EJXK,YJXK,LXRQ," +
      "LXRXM,LXRDZ,LXRZJLX,LXRZJHM,LXRZJYXQZ,LXRDH,LXREMAIL,BZ," +
      "JCXGR,XGR,XGSJ,SZYXMC) " +"select ID,TZMC,LHRQ," +
      "(select c.bm from bm_jcdw c where c.id=t.szyx) as szyx ," +//都是编码！
      "(select b.mc from bm_yx b where b.id=t.yx) as yx," +
      "(select mc from bm_zyml a where a.id=t.zy) as zy," +
      "(select xk from bm_zyml a where a.id=t.zy) as ejxk," +
      "(select yijimc from v_bm_xk where erjimc=(select xk from bm_zyml a where a.id=t.zy)) as yjxk," +
      "LXRQ,LXRXM,LXRDZ,LXRZJLX,LXRZJHM,LXRZJYXQZ," +
      "LXRDH,LXREMAIL,BZ,'系统管理员',XGR,XGSJ, " +
      "(select c.mc from bm_jcdw c where c.id=t.szyx) as szyxmc  "+
      "from t_dqtz t "+
      " where lhrq<'" + (year + 1) + "-01-01'" +
      " and lxrq>='" + year + "-01-01' "+
      "and (select c.bm from bm_jcdw c where c.id=t.szyx) = '"+yxbm+"'";
      String sql2="insert into "+DETAIL+".t_dqtzgj(PID,TZPID,GJ,ZM," +
      "RS,BZ,XGR,XGSJ,JCXGR) select " +
      "(select e.id from "+DETAIL+".t_dqtz e where e.pid=t.pid) as tzpid,ID," +
      "(select a.mc from bm_gj a where a.id=t.gj) as gj," +
      "(select a.zm from bm_gj a where a.id=t.gj) as zm," +
      "RS,BZ,XGR,XGSJ,'系统修改人' from t_dqtz_gj   t where t.pid in "+
      "(select h.id from t_dqtz h where h.lhrq<'"+ (year + 1) + "-01-01'" +
      " and h.lxrq>='" + year + "-01-01' "+
      "and (select c.bm from bm_jcdw c where c.id=h.szyx) = '"+yxbm+"')";
      //将bj，jflb，zdxslb编码修正为名称
      String sql3="insert into "+DETAIL+".t_jbxx(DQTZPID,PID,NO_20X,DYBZ,DYSJ," +
      "SQBH,XH,KH,ZWXM,X,M,GJ,ZM,PQG,PQTJ,DQB,HZHM,XB,HYZK,CSRQ," +
      "CSDD,JTDZ,JTDH,ZHXL,ZY,GZHXXDW,LHXXZY,YJXK,EJXK,XX_KSRQ," +
      "XX_JSRQ,XZQ,XZZ,HYBXXX,YWHYBXXX,BXHY_KSRQ,BXHY_JSRQ,XSLB," +
      "CCMC,CSC_NO,TJDWHDH,JFBF,JFBF_QEJXJ,JFBF_BF_XF,JFBF_BF_ZSF," +
      "JFBF_BF_YLF,JFBF_BF_JCF,JFBF_QT,ZHSWDBRHDH,JFLY_JXJ," +
      "JFLY_ZF,JFLY_QT,JJDBRHJG,JFLB,JFQK,JFWB,JFLY,TLQX,ZJ," +
      "MY,YX,SZYX,BJ,TC,ZP,ZDXSLB,ZDJS,XSZT,HYNL,HSKCJ,YYNL," +
      "QTYY,JHR,ZHQS,BZ,SQSJ,LQSJ,BDSJ,BYSJ,LXSJ,LXYY,XZZJLXFS," +
      "JSXX,LL,YWBDYX,QZLB,SKYY,BDYX,YWSKYY,BDKSRQ,BDJZRQ," +
      "JCXGR,XGR,XGSJ,SZYXMC,YJLXSJ) "+
      "select (select e.id from "+DETAIL+".t_dqtz e where e.pid=t.dqtzpid) as DQTZPID," +
      "ID,NO_20X,DYBZ,DYSJ,SQBH,XH,KH,ZWXM,X,M,GJmc as gj," +
      "ZM,PQG,PQTJ,DQB,HZHM,XB,HYZK,CSRQ,CSDD,JTDZ,JTDH,ZHXL,ZY,GZHXXDW," +
      "zymlmc as lhxxzy,YJXKmc as yjxk,xkmc as ejxk,XX_KSRQ,XX_JSRQ,XZQ," +
      "XZZ,HYBXXX,YWHYBXXX,BXHY_KSRQ,BXHY_JSRQ,XSLBMC,CCMC,/*xxxs,*/CSC_NO," +
      "TJDWHDH,JFBF,JFBF_QEJXJ,JFBF_BF_XF,JFBF_BF_ZSF,JFBF_BF_YLF,JFBF_BF_JCF," +
      "JFBF_QT,ZHSWDBRHDH,JFLY_JXJ,JFLY_ZF,JFLY_QT,JJDBRHJG,JFLBMC as jflb,JFQK,JFWB," +
      "JFLYmc as jfly,TLQXmc as tlqx,ZJ,MY,YXmc as yx," +
      "(select h.bm from bm_jcdw h where h.id=t.szyx) as szyx,BJMC as bj,TC,ZP," +
      "zdxslbmc as zdxslb,ZDJS,XSZTmc as xszt,HYNL,HSKCJ,YYNL,QTYY,JHR,ZHQS,BZ,SQSJ,LQSJ," +
      "BDSJ,BYSJ,LXSJ,ydmc as lxyy,XZZJLXFS,JSXX,LL,YWBDYX,QZLBmc as qzlb,SKYYMC," +
      "BDYX,YWSKYYMC,BDKSRQ,BDJZRQ,'系统管理员',XGR,XGSJ,SZYXMC,YJLXSJ " +
      "from v_allinfo t"+
      " where t.bdsj<'" + (year + 1) + "-01-01'" +
      " and (t.lxsj>='" + year + "-01-01' or lxsj is null)"
      +" and t.xszt in (3,4,5,6) "
      +" and (select h.bm from bm_jcdw h where h.id=t.szyx)='"+yxbm+"'";
      /*if(SB==Integer.MIN_VALUE)
          setSb();在构建函数中已经调用了setSb()*/
      String sql4="update datacenter.t_yxztsy t set t.sjsbzt=0,t.xgsj=TO_CHAR(SYSDATE,'yyyy-mm-dd hh24:mi:ss'),t.xgr='"+xgr+"' where t.sbdw='"+yxbm+"' and t.sbsypid="+SB;
      //ID,PID,TZPID,GJ,ZM,RS,BZ,XGR,XGSJ,JCXGR,JCXGSJ
      //ID,PID,TZMC,LHRQ,SZYX,YX,ZY,XK,LXRQ,LXRXM,LXRDZ,LXRZJLX,LXRZJHM,LXRZJYXQZ,LXRDH,LXREMAIL,BZ,SJDRSJ,JCXGR,JCXGSJ,XGR,XGSJ
//ID,DQTZPID,PID,NO_20X,DYBZ,DYSJ,SQBH,XH,KH,ZWXM,X,M,GJ,ZM,PQG,PQTJ,DQB,HZHM,XB,HYZK,CSRQ,CSDD,JTDZ,JTDH,ZHXL,ZY,GZHXXDW,LHXXZY,YJXK,EJXK,XX_KSRQ,XX_JSRQ,XZQ,XZZ,HYBXXX,YWHYBXXX,BXHY_KSRQ,BXHY_JSRQ,XSLB,CCMC,XXXS,CSC_NO,TJDWHDH,JFBF,JFBF_QEJXJ,JFBF_BF_XF,JFBF_BF_ZSF,JFBF_BF_YLF,JFBF_BF_JCF,JFBF_QT,ZHSWDBRHDH,JFLY_JXJ,JFLY_ZF,JFLY_QT,JJDBRHJG,JFLB,JFQK,JFWB,JFLY,TLQX,ZJ,MY,YX,SZYX,BJ,TC,ZP,ZDXSLB,ZDJS,XSZT,HYNL,HSKCJ,YYNL,QTYY,JHR,ZHQS,BZ,SQSJ,LQSJ,BDSJ,BYSJ,LXSJ,LXYY,XZZJLXFS,JSXX,LL,YWBDYX,QZLB,SKYY,BDYX,YWSKYY,BDKSRQ,BDJZRQ,SJDRSJ,JCXGR,JCXGSJ,XGR,XGSJ
      
      Connection conn = null;

      try {
          conn=DatabaseConnect.getPooledConnection();
          conn.setAutoCommit(false);
          Statement stmt = conn.createStatement();
          if(this.cancelSave())
          {
         /* stmt.addBatch(sql_del1);
          stmt.addBatch(sql_del2);
          stmt.addBatch(sql_del3);
          stmt.addBatch(sql_del4);*/
          /*System.out.println(sql1);
          System.out.println(sql2);
          System.out.println(sql3);
          System.out.println(sql4);*/
          stmt.addBatch(sql1);
          stmt.addBatch(sql2);
          stmt.addBatch(sql3);
          stmt.addBatch(sql4);
          stmt.executeBatch();
          stmt.close();
          conn.commit();
          
          CallableStatement cs = conn.prepareCall("{call datacenter.TONGJIBYDYNAMIC(?,?)}");

          cs.setString(1,yxbm);
         /* if(SB==Integer.MIN_VALUE)
              setSb();在构建函数中已经调用了setSb()*/
          cs.setInt(2,SB);
          cs.execute();
          conn.commit();
          
          cs.close();
          
          if(!conn.isClosed())
              conn.close();
          flag=true;
          }
              /*ps4.setString(1,yxbm);
              ps4.execute();
              ps4.close();
              
              cs= conn.prepareCall("{call datacenter.TONGJIBYDYNAMIC(?,?)}");
              
              //cs.registerOutParameter(2,Types.VARCHAR);
              cs.setString(1,yxbm);
              cs.setInt(2,SB);
              cs.execute();
              cs.close();
              
              //ps.close();
             // rs.close();
              conn.commit();
              conn.close();*/

      } catch (NamingException e1) {
          // TODO 自动生成 catch 块
          e1.printStackTrace();
          flag=false;
      } catch (SQLException e1) {
          // TODO 自动生成 catch 块
          e1.printStackTrace();
          flag=false;
          try {
            conn.rollback();
        } catch (SQLException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
      } catch (Exception e1) {
          // TODO 自动生成 catch 块
          e1.printStackTrace();
          flag=false;
      }
      finally{
          try {
              if(!conn.isClosed())
                  conn.close();
          } catch (SQLException e) {
              e.printStackTrace();
              flag=false;
          }
      }
      return flag;

      
  }
  
/*  public String addDymb(List list)
  throws Exception
{
//check the unique; 行位置存入时减 1
Statement stmtB = conn.createStatement();
for (int i=0;i<list.size();i++){
  StringBuffer sql = new StringBuffer();
  mbInfo info = (mbInfo) list.get(i);
  sql.append("select 1 from tb_mb_define where id='")
      .append(info.getId()).append("' and hwz=")
      .append(info.getHwz()).append("-1 and lwz=")
      .append(info.getLwz());
  //System.out.println(sql.toString());
  rs = stmt.executeQuery(sql.toString());

  if (!rs.next()){
    StringBuffer str = new StringBuffer();
    str.append("insert into tb_mb_define(id,zdmc,ywm,hwz,lwz,bz,xgz,xgsj) values('")
        .append(info.getId()).append("','")
        .append(info.getZdmc()).append("','")
        .append(info.getZdywm()).append("',")
        .append(info.getHwz()).append("-1,'")
        .append(info.getLwz()).append("','")
        .append(info.getBz()).append("','")
        .append(info.getXgz()).append("',sysdate)");
    stmtB.addBatch(str.toString());
  }
}
stmtB.executeBatch();

return null;
}*/
  /**
   * 撤销上报
   * 调用存储过程datacenter.DEL_TONGJI_BYDYNAMIC（院校编码，上报批次）
   * 在该存储过程中，删除了指定院校在细节数据库中的相关数据，并修改上报状态
   * @return 撤销成功/不成功
   */
  public boolean cancelSave()
  {
      //撤销存储
      //String sql1="delete from "+DETAIL+".t_jbxx where szyx='"+yxbm+"'";
      //String sql2="delete from "+DETAIL+".t_dqtzgj where pid in (select id from "+DETAIL+".t_dqtz where szyx='"+yxbm+"')";
      //String sql3="delete from "+DETAIL+".t_dqtz where szyx='"+yxbm+"'";
      //String sql4="update datacenter.t_yxztsy t set t.sjsbzt=1,t.xgsj=TO_CHAR(SYSDATE,'yyyy-mm-dd hh24:mi:ss') where t.sbdw='"+yxbm+"' and t.sbsypid="+SB;
      
      
      Connection conn = null;
      //PreparedStatement ps = null;
      //ResultSet rs = null;
      
      try {
          conn=DatabaseConnect.getPooledConnection();
          conn.setAutoCommit(false);
          CallableStatement cs = conn.prepareCall("{call datacenter.DEL_TONGJI_BYDYNAMIC(?,?)}");
          //Statement stmt = conn.createStatement();
          //stmt.addBatch(sql1);
          //stmt.addBatch(sql2);
          //stmt.addBatch(sql3);
          //stmt.addBatch(sql4);
          //stmt.executeBatch();
          cs.setString(1,yxbm);
          /*if(SB==Integer.MIN_VALUE)
              setSb();在构建函数中已经调用了setSb()*/
          cs.setInt(2,SB);
          cs.execute();
          conn.commit();
          cs.close();
          //stmt.close();
          if(!conn.isClosed())
              conn.close();
          return true;
          
      } catch (SQLException e) {
          e.printStackTrace();
          return false;
      } catch (Exception e) {
          e.printStackTrace();
          return false;
      }
      finally{
          try{
              if(!conn.isClosed())
                  conn.close();
          }
          catch(SQLException e)
          {
              e.printStackTrace();
              return false;
          }
      }
  }

  /**
   * 保存Excel文件
   * @param wb：文件
   * @param filepath:文件存放路径
   */
  private void saveFile(HSSFWorkbook wb,String filepath) {
    FileOutputStream fileOut = null;

    try {
      fileOut = new FileOutputStream(filepath+this.exportSetting.getFileName());
    }
    catch (FileNotFoundException ex) {
      //System.out.print("请求的操作无法执行,请确认指定路径正确，且文件已关闭!");
      return;
    }
    if (wb != null) {
      try {
        wb.write(fileOut);
        fileOut.close();
      }
      catch (IOException ex1) {
        ex1.printStackTrace();
      }
      finally {
        try {
          fileOut.close();
        }
        catch (IOException ex) {
        }
      }
    }

  }
  
  
  /**
   * 特殊规则检查
   * @param wb ：用于检查的excel文件
   * 注：字段顺序由book3、book4而定，如果改变了book3、book4的字段顺序，也要对程序进行相应的修改
   */public void specialCheck(HSSFWorkbook wb)
  {
      
      HSSFSheet st=wb.getSheetAt(0);
      for(int i=1;i<=ROWNUM;i++)
      {
          HSSFRow row =st.getRow(i);
          if(row!=null)
          {
              if(row.getCell((short)8)!=null&&row.getCell((short)11)!=null)
              {
                  String bdsj=row.getCell((short)8).getStringCellValue();
                  String lxsj=row.getCell((short)11).getStringCellValue();
                  if(bdsj!=null&&lxsj!=null)//报到时间必须早于离校时间
                  {
                      try {
                        Date date_BDSJ=DateFormat.getDateInstance().parse(bdsj);
                        Date date_LXSJ=DateFormat.getDateInstance().parse(lxsj);
                        if(date_BDSJ.after(date_LXSJ))
                        {
                            HSSFCell bdsjCell=row.getCell((short)8);
                            HSSFCell lxsjCell=row.getCell((short)11);
                            bdsjCell.setCellStyle((HSSFCellStyle)style.get("8"));
                            lxsjCell.setCellStyle((HSSFCellStyle)style.get("8"));
                        }
                    } catch (ParseException e) {
                        // TODO 自动生成 catch 块
                        //e.printStackTrace();--日期转换不成功，会在之前检查出来，因此这里不作处理
                    }
                  }
                  if(lxsj!=null)//只有离校状态下可以填离校时间
                  {
                      if(row.getCell((short)12)!=null)
                      {
                          String xszt=row.getCell((short)12).getStringCellValue();
                          xszt=(xszt==null)?"":xszt;
                          if(!(xszt.equals("离校")||lxsj.equals("")))
                          {
                              HSSFCell lxsjCell=row.getCell((short)11);
                              HSSFCell xsztCell=row.getCell((short)12);
                              lxsjCell.setCellStyle((HSSFCellStyle)style.get("8"));
                              xsztCell.setCellStyle((HSSFCellStyle)style.get("8"));
                          }
                      }
                  }
              }
              //与短期生有关的检查
              if(row.getCell((short)5)!=null&&row.getCell((short)7)!=null)
              {
                  String tlqx=row.getCell((short)5).getStringCellValue();
                  if(tlqx.equals("短期"))//有团组的短期生，其国籍和报到时间必须和团组保持一致
                  {
                      String dqtz=row.getCell((short)7).getStringCellValue();
                      if(dqtz!=null&&!(dqtz.equals("无团组"))&&!(dqtz.equals("")))
                      {
                          if(row.getCell((short)3)!=null)//gj
                          {
                              String gj=row.getCell((short)3).getStringCellValue();
                              if(gj!=null&&!(gj.equals("")))
                                  
                                  if(!cd.specialDataTable.containsValue(dqtz+gj))
                                      
                                  {
                                      HSSFCell gjCell=row.getCell((short)3);
                                      gjCell.setCellStyle((HSSFCellStyle)style.get("7"));
                                  }
                          }
                          if(row.getCell((short)8)!=null)//bdsj
                          {
                              String bdsj=row.getCell((short)8).getStringCellValue();
                              if(bdsj!=null&&!(bdsj.equals("")))
                                  
                                  if(!cd.specialDataTable.containsKey(dqtz+bdsj))
                                      
                                  {
                                      HSSFCell gjCell=row.getCell((short)8);
                                      gjCell.setCellStyle((HSSFCellStyle)style.get("7"));
                                  }
                          }
                      }
                  }
              }
          }
      }
      //怎么比较？
      
  }

  /**
   * 检查指定年度数据，生成规范性检查结果与文件
   * @param filepath 生成excel文件 所在文件夹路径
   * @param templet 读取txt文件 所在文件夹路径
   * @param dwid 单位id
   * @param year 上报年度
   * @return
   */public int insertData(String filepath, String templet, String dwid,int year) {
      //int year = 2006;//上报年度
      String condition = "";//查询条件，根据上报年度而定
      //String condition_forupdate= "";//可上报数据的查询条件，报到时间必填
      String condition_cqs_forupdate="";//可上报长期生数据的查询条件，报到时间必填
      String condition_dqs_forupdate="";//可上报短期生数据的查询条件，报到时间必填
      //String filepath ="";//生成文件存储位置
      //filepath = "d:/";
      File fs = new File(filepath);
      int ret = 2;
      
      if(!fs.exists())
      {
          //System.out.println("文件路径不存在，请重试！");
          //return 4;
          try {
            fs.createNewFile();
        } catch (IOException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
            return 30;
        }
      }//检查路径
      
      //String dwid = "92";//单位id
      getBM(Integer.parseInt(dwid));//由id得到bm
      
      ret = checkState();
      if(ret == 1){//检查上报状态
          
          //由year和dwid生成condition
          initExcel();//初始化生成文件的部分参数
          ReadTxt rt=new ReadTxt();
          
          //以下为基本信息
          //规范数据
          
          if(dwid.equals("")||dwid==null||dwid.equals("0"))
          {
              condition = " and t.bdsj is null or (t.bdsj is not null and t.bdsj<'" + (year + 1) + "-01-01'" +
              " and (t.lxsj>='" + year + "-01-01' or t.lxsj is null)) and t.xszt in (3,4,5,6)";
          }
          else
          {
              condition = " and (t.bdsj is null or (t.bdsj is not null and  t.bdsj<'" + (year + 1) + "-01-01'" +
              " and (t.lxsj>='" + year + "-01-01' or t.lxsj is null))) and t.xszt in (3,4,5,6)"+" and t.szyx="+dwid;
          }
/*          if(dwid.equals("")||dwid==null||dwid.equals("0"))
          {
              condition_forupdate = " and (t.bdsj is not null and t.bdsj<'" + (year + 1) + "-01-01'" +
              " and (t.lxsj>='" + year + "-01-01' or t.lxsj is null)) and t.xszt in (3,4,5,6)";
          }
          else
          {
              condition_forupdate = " and (t.bdsj is not null and  t.bdsj<'" + (year + 1) + "-01-01'" +
              " and (t.lxsj>='" + year + "-01-01' or t.lxsj is null)) and t.xszt in (3,4,5,6)"+" and t.szyx="+dwid;
          }*/
          if(dwid.equals("")||dwid==null||dwid.equals("0"))//长期生
          {
              condition_cqs_forupdate = " and (t.bdsj is not null and t.bdsj<'" + (year + 1) + "-01-01'" +
              " and (t.lxsj>='" + year + "-01-01' or t.lxsj is null)) and t.xszt in (3,4,5,6) and t.tlqx=1";
          }
          else
          {
              condition_cqs_forupdate = " and (t.bdsj is not null and  t.bdsj<'" + (year + 1) + "-01-01'" +
              " and (t.lxsj>='" + year + "-01-01' or t.lxsj is null)) and t.xszt in (3,4,5,6)"+" and t.tlqx=1 and t.szyx="+dwid;
          }
          if(dwid.equals("")||dwid==null||dwid.equals("0"))//短期生
          {
              condition_dqs_forupdate = " and (t.bdsj is not null and t.bdsj<'" + (year + 1) + "-01-01'" +
              " and (t.lxsj>='" + year + "-01-01' or t.lxsj is null)) and t.xszt in (3,4,5,6) and t.tlqx=2";
          }
          else
          {
              condition_dqs_forupdate = " and (t.bdsj is not null and  t.bdsj<'" + (year + 1) + "-01-01'" +
              " and (t.lxsj>='" + year + "-01-01' or t.lxsj is null)) and t.xszt in (3,4,5,6)"+" and t.tlqx=2 and t.szyx="+dwid;
          }
          this.setExportSetting("jbxx_cqs_correct", year,filepath);//长期生
          getColumn(templet + "book3.txt",dwid);
          
          //System.out.println("开始导出正确数据：");
          String jbxx=rt.readfile(templet+"jbxx.txt");
          String sql_jbxx=jbxx+condition + " order by dwid,pid";
          String sql_jbxx_forupdate=jbxx+condition_cqs_forupdate+ " order by dwid,pid";
          getData(sql_jbxx_forupdate);
          
          this.setExportSetting("jbxx_dqs_correct", year,filepath);//短期生
          getColumn(templet + "book3.txt",dwid);
          sql_jbxx_forupdate=jbxx+condition_dqs_forupdate+ " order by dwid,pid";
          getData(sql_jbxx_forupdate);
          
          //不规范数据
          this.setExportSetting("jbxx_error", year,filepath);
          //System.out.println("开始导出错误数据：");
          String sql_no_jbxx=rt.readfile(templet+"no_jbxx.txt")+ " and t.id not in (select pid from ("+sql_jbxx+"))"
          +condition + " order by dwid,pid";
          getData(sql_no_jbxx);
          
          boolean correctTag_jbxx=correctTag;
          correctTag=true;
          //以下为短期团组
          if(dwid.equals("")||dwid==null||dwid.equals("0"))
          {
              condition = " and t.lhrq is null or (t.lhrq is not null and t.lhrq<'" + (year + 1) + "-01-01'" +
              " and (t.lxrq>='" + year + "-01-01' or t.lxrq is null))";
          }
          else
          {
              condition = " and (t.lhrq is null or (t.lhrq is not null and  t.lhrq<'" + (year + 1) + "-01-01'" +
              " and (t.lxrq>='" + year + "-01-01' or t.lxrq is null)))"+" and t.szyx="+dwid;
          }
          //规范数据
          this.setExportSetting("dqtz_correct", year,filepath);
          getColumn(templet + "book4.txt",dwid);
          //System.out.println("开始导出正确数据：");
          String sql_dqtz=rt.readfile(templet+"dqtz.txt")+condition + " order by dwid,pid";
          getData(sql_dqtz);
          
          //不规范数据
          this.setExportSetting("dqtz_error", year,filepath);
          //System.out.println("开始导出错误数据：");
          String sql_no_dqtz=rt.readfile(templet+"no_dqtz.txt")+ " and t.id not in (select pid from ("+sql_dqtz+"))"
          +condition + " order by dwid,pid";
          getData(sql_no_dqtz);
          boolean correctTag_dqtz=correctTag;
          
          if(correctTag_jbxx&&correctTag_dqtz){//correctTag：是否有错误数据；fileTag：是否有数据
              //saveData(year);//保存数据，调用用于统计的存储过程
              ret = 0;
          }//cancelSave();//撤销上报
          else if(correctTag_jbxx){
              ret = 4;
          }
          else if(correctTag_dqtz){
              ret=5;
          }
          else{
              ret=3;
          }
      }
      else
      {
          //System.out.println("不允许上报");
      }
      return ret;
  }
  
  /**
   * 如果单位上报状态为0（已上报），则删除上报数据
   * @param dwid 单位id
   * @param year 上报年度
   * @return
   */
   public int removeData(String dwid, int year) {
      int ret = 2;
      
      getBM(Integer.parseInt(dwid));//由id得到bm
      ret = checkState();
      if(ret == 0){//检查上报状态
    	  if(cancelSave()){
    		  ret = 1;
    	  }
      }
      else
      {
          //System.out.println("不允许撤销");
      }
      return ret;
}
      
      

 
      
      public static void main(String args[]) {
          
          //DatabaseExport de = new DatabaseExport();
          //de.run("d:/", ".", "92",2006);
          //System.out.print("成功导出！按Enter退出");
          //Input.readString();
         // 
    	  File f = new File("e:/1.txt");
    	  if(f.exists()){
    		  //System.out.println(f.getPath());
    	  }
      }

    /**
     * @return Returns the yxbm.
     */
    public String getYxbm() {
        return yxbm;
    }


      
  }
