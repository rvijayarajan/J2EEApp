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
 * <p>Title: �й�ϵͳ���ݵ���</p>
 * <p>Description: ����ѧ���й�ϵͳ���ݿ�����ȡ�������������ݣ����й淶�Լ�飬������Ӧ��excel�ļ�������������ݽӿڹ淶��</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author zs
 * @version 1.0
 */
public class DatabaseExport
    {
  private int COUNT = 500; //Ϊ�����ڴ����ģ���ÿCOUNT��һ��д��EXCEL�ļ���ͬʱ��ձ������ݵ�hash��
  private int COLNUM = 0; //������
  private int ROWNUM = 0; //�������������ֶ����У�
  private Hashtable data = new Hashtable(); //���ڴ洢�����ݿ����������
//  private Hashtable name = new Hashtable(); //���ڴ洢�����ݿ�������ֶ���
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
  private Hashtable name = new Hashtable();//��txt�ļ��������ֶ�����
  private String DETAIL=null;

  
  /**
 * @return Returns the dETAIL.
 */
public String getDETAIL() {
    return DETAIL;
}



/**
   * ��ʼ������excel�ļ�����ĸ�ʽ
   *
   */public void initExcel(){
      style.clear();
      /**
       * �����ǳ�ʼ����Ԫ����ʽ
       * �ò�ͬ����ʽ��ʾ��ͬ�����ݴ���
       */
      HSSFCellStyle style1 = null; //���ɵ�Excel�ļ��ĵ�Ԫ����ʽ1
      HSSFCellStyle style2 = null; //���ɵ�Excel�ļ��ĵ�Ԫ����ʽ2
      HSSFCellStyle style3 = null; //���ɵ�Excel�ļ��ĵ�Ԫ����ʽ3
      HSSFCellStyle style4 = null; //���ɵ�Excel�ļ��ĵ�Ԫ����ʽ4
      HSSFCellStyle style5 = null; //���ɵ�Excel�ļ��ĵ�Ԫ����ʽ5
      HSSFCellStyle style6 = null; //���ɵ�Excel�ļ��ĵ�Ԫ����ʽ6
      HSSFCellStyle style7 = null; //���ɵ�Excel�ļ��ĵ�Ԫ����ʽ7
      HSSFCellStyle style8 = null; //���ɵ�Excel�ļ��ĵ�Ԫ����ʽ8
      
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
      //style1���ڱ�ʾ��������Ϊ�ա��Ĵ���
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
   * ���캯��
   */
  public DatabaseExport() {
    super();
    setSb();
  }

  /**
   * setExportSetting��ʹ�øú�����������ִ�б����������������Ϣ
   * @param fileName���ļ�����������������Ը��ļ����洢
   * @param year���ϱ����
   * @param filepath:·�������Ǳ����ļ���Ŀ¼
   *
   */
  public void setExportSetting( //String viewName,
                               String fileName, int year,String filepath) {
    //this.exportSetting.setOwner(owner);@param owner�����ݿ��û���
    //this.exportSetting.setViewName(viewName);
      //@param viewName����ͼ����ͨ������ͼ��ѯ�����Ϲ淶�����ݣ����ø���ͼ��������ݺ�tableName��ָʾ�ı�ṹ��ͬ
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
            // TODO �Զ����� catch ��
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO �Զ����� catch ��
            e.printStackTrace();
        } catch (NullPointerException e) {
            // TODO �Զ����� catch ��
            e.printStackTrace();
        }catch (Exception e) {
            // TODO �Զ����� catch ��
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
   * ��ѯ�ϱ�״̬
   * @return �ϱ�״̬
   �ϱ�������״̬��
   1����Уδ�ϱ�����ʼ̬��
   0����У���ϱ�����ίδ��˻����δͨ��
   7����ί���ͨ��
   8����ί���ϱ���������δ��˻����δͨ��
   9�������������ϻ�ǿ�ƹر��ϱ�
   ���г�����ר�õ��м�̬�����ݿⲢ�����õ���
   2����У����δ����
   3���д���Ķ�������ͻ�����Ϣ
   4���д���Ķ�������
   5���д���Ļ�����Ϣ
   6���޴������ݣ���ʾ�������ϱ���
   0���޴�������
   10����̨���������쳣
   30����У�ϱ�ʧ��

   */
  public int checkState(){
      /*if(SB==Integer.MIN_VALUE)
          setSb();
          �ڹ����������Ѿ�������setSb()*/
      String sql="select t.sjsbzt from datacenter.t_yxztsy t where t.sbdw=? and t.sbsypid="+SB;
      Connection conn=null;
      PreparedStatement ps=null;
      ResultSet rs=null;
      int flag = 2;//��������ݿ���������ݣ�2���ǳ���Ĭ�ϳ�ʼ̬
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
   * �����ϱ�״̬
   * @param flag ���º��״̬
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
   * �ɵ�λid�õ�ԺУ����
   * @param dwid ��λid
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
              //System.out.println("û�������λ");
          }
          ps.close();
          rs.close();
    } catch (Exception e) {
        // TODO �Զ����� catch ��
        e.printStackTrace();
    }
    finally{
        try {
            if(!conn.isClosed())
            {
                conn.close();
            }
        } catch (SQLException e) {
            // TODO �Զ����� catch ��
            e.printStackTrace();
        }
    }
      
      
      
  }
  
  /**
   * ��ʼ�� �淶�Լ�� �����������
   * @param filepath ����ֶ���Ϣ��txt�ļ�·��
   * @param dwid ��λid
   */
  public void getColumn(String filepath,String dwid){
      ReadTxt rt= new ReadTxt();
      name=rt.readtxt(filepath);//��ȡ�ֶ���Ϣ
      COLNUM = name.size();//��ȡ�ֶθ���
      cd.getCriteDatabase(name);//��ʼ���淶����
      cd.getCriteTxt(name);//��ʼ���淶����
      cd.getConditionTxt(name);//��ʼ��������������
      cd.getSpecialData(dwid);//��ʼ���������������
  }

  /**
   * �����ݿ��ѯ�����Ϲ淶������
   * @param sql ��ѯ���
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
          String next_szyx = ""; //��һ������ԺУ
          String fileName = this.exportSetting.getFileName();
          int count=1;
          while (rs.next()) { //���ζ�����ѯ��������
              rownum++;
              String pre_szyx = next_szyx;
              next_szyx = rs.getString("yxmc");
              //yxbm=rs.getString("yxbm");
              if ( ( (rownum % COUNT == 1) && rownum != 1)||( (!next_szyx.equals(pre_szyx)) && (!pre_szyx.equals(""))) ) {
                  ROWNUM += (rownum-1);
                  if(this.exportSetting.getFileName().indexOf("error")!=-1){
                      this.writeExcel(wb);
                      this.specialCheck(wb);
                      this.checkYjlxsj(wb);//���Ԥ����Уʱ��ı�����
                  }
                  else
                      this.writeExcelNoCheck(wb);
                  data.clear();
                  if(( (!next_szyx.equals(pre_szyx)) && (!pre_szyx.equals(""))))
                  {
                      count=1;
                      if (fileName.indexOf("error") != -1) {
                          //System.out.println("��ǰԺУΪ" + pre_szyx + ",�ѵ�����������" + ROWNUM +
                          //"��");
                          correctTag=false;
                      }
                      else {
                          //System.out.println("��ǰԺУΪ" + pre_szyx + ",�ѵ�����ȷ����" + ROWNUM +
                          //"��");
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
                  //ed���ڴ洢���ݡ���ţ���Excel�ļ��е��кţ�����Excel�ļ��е��к�
                  ExportColumn column = (ExportColumn) name.get(String.valueOf(i));
                  //column��data��keyΪ�кŵ�value��������ֶ�����ExportData
                  try {
                      ed.setData(rs.getString(column.getData())); //����
                      ed.setColNum( (short) i); //�к�
                      ed.setRowNum(rownum); //�к�
                  }
                  catch (SQLException ex) {
                      ex.printStackTrace();
                  }
                  
                  data.put(column.getData() + String.valueOf(ed.getRowNum()), ed);
                  //key���ֶ���+�к�
                  
              }
              
          }
          if (rownum>=1) {
              ROWNUM += rownum;
              this.exportSetting.setFileName(fileName + next_szyx + String.valueOf(count) + ".xls");
              if(this.exportSetting.getFileName().indexOf("error")!=-1){
                  this.writeExcel(wb);
                  this.specialCheck(wb);
                  this.checkYjlxsj(wb);//���Ԥ����Уʱ��ı�����
              }
              else
                  this.writeExcelNoCheck(wb);
              saveFile(wb,this.exportSetting.getFilepath());
              wb.removeSheetAt(0);
              data.clear();
              if (fileName.indexOf("error") != -1) {
                  //System.out.println("��ǰԺУΪ" + next_szyx + ",�ѵ�����������" + ROWNUM + "��");
                  correctTag=false;
              }
              else {
                  //System.out.println("��ǰԺУΪ" + next_szyx + ",�ѵ�����ȷ����" + ROWNUM + "��");
              }
          }
          else
          {
              //System.out.println("û�з���Ҫ������ݡ�");
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
   * ���Ԥ����Уʱ��ı�����
   * ��ѧ�����Ϊ����������˶ʿ�о�������ʿ�о���ʱ��ѧ����ѧרҵһ��ѧ��Ϊҽѧ��,
   * ѧ��״̬Ϊ������Ԥ����Уʱ��Ϊ�����
   * @param wb
   */private void checkYjlxsj(HSSFWorkbook wb) {
    
       HSSFSheet st=wb.getSheetAt(0);
       ExportColumn ec = (ExportColumn) name.get("22"); //��name��ȡ���ֶ���
       for(int i=1;i<=ROWNUM;i++)
       {
           HSSFRow row =st.getRow(i);
           if(row!=null)
           {
               HSSFCell cell=row.getCell((short)21);//Ԥ����Уʱ��
               if((cell==null||cell.getStringCellValue().equals(""))&&row.getCell((short)4)!=null&&row.getCell((short)2)!=null&&row.getCell((short)12)!=null)//��������=book3�����-1����Ϊhssfworkbook�Ǵ�0��ʼ�����ģ�
               {//������Ԥ����Уʱ�䡢ѧ����𡢶���ѧ�ơ�ѧ��״̬
                   //��Ԥ��Уʱ��Ϊ�ջ�ո񣬶���������ǿյ�ʱ����ý���������
                   String xslb=row.getCell((short)4).getStringCellValue();
                   String ejxk=row.getCell((short)2).getStringCellValue();
                   String xszt=row.getCell((short)12).getStringCellValue();
                   if((xslb.equals("������")||xslb.equals("˶ʿ�о���")||xslb.equals("��ʿ�о���"))&&xszt.equals("����"))
                   {
                       if(ejxk!=null)
                           //book3�ĵ����ڶ����ʾ�Ƿ���������һ���ʾ������������ȡ��������ʱ��û���ж��Ƿ�����˰�Ԥ����Уʱ��
                           //����Ϊ�Ǳ���б���������������������ж���ѧ����������������ж�
                           if(cd.conditionTable.containsKey(ec.getChinesedata()+ejxk))
                           {
                               cell.setCellStyle((HSSFCellStyle)style.get(String.valueOf(1)));//style1��ʾ������Ϊ��
                           }
                   }
               }
           }
       }
}



/**
   * �������Ĳ��淶����д��EXCEL�ļ�
   * @param wb ��д�����ݵ�excel�ļ�
   */
  public void writeExcel(HSSFWorkbook wb) {
    HSSFSheet st = null;
    
      st = wb.createSheet();
      /**
       * д�ֶ�������һ�У�
       */
      st.createRow(0);
      for (int i = 1; i <= COLNUM; i++) {

        HSSFCell cell = st.getRow(0).createCell( (short) (i - 1));
        ExportColumn ec = (ExportColumn) name.get(String.valueOf(i)); //��data��ȡ���ֶ���
        if (ec.getData() != null) {
          cell.setCellType(HSSFCell.CELL_TYPE_STRING);
          cell.setEncoding(HSSFCell.ENCODING_UTF_16);
          cell.setCellValue(ec.getChinesedata());
        }
      }
    int startRow = ( (ROWNUM % COUNT) == 0) ? COUNT : (ROWNUM % COUNT);
    for (int i = 1; i <= startRow; i++) {
      st.createRow(i); //������
    }
    /**
     * д���ݣ�ÿ��дһ�У�ѭ��COLNUM-1��
     * 
     */
    for (int i = 1; i <= COLNUM; i++) {
        ExportColumn ec = (ExportColumn) name.get(String.valueOf(i)); //��name��ȡ���ֶ���
        
        
      for (int j = 1; j <= startRow; j++) {
        HSSFCell cell = st.getRow(j).createCell( (short) (i - 1));
        ExportData cellData = (ExportData) data.get(ec.getData() +
            String.valueOf(j)); //���ֶ������к�Ϊkey����data��ȡ������

        short errorCode=0;
        if(cellData.getData()==null||cellData.getData().equals(""))
        {
            
            if(ec.getChinesedata().equals("����ʱ��"))
            {
                ExportData cellData_TLQX = (ExportData) data.get("tlqx" +
                        String.valueOf(j)); //���ֶ������к�Ϊkey����data��ȡ������
                ExportData cellData_DQTZ=(ExportData) data.get("dqtz"+String.valueOf(j));
                
                if(cellData_TLQX!=null&&cellData_DQTZ!=null)
                {
                    String tlqx="";
                    String dqtz="";
                    tlqx=cellData_TLQX.getData();
                    dqtz=cellData_DQTZ.getData();
                    if(tlqx!=null&&dqtz!=null&&tlqx.equals("����")&&
                            !(dqtz.equals("������")||dqtz.equals("")))//������Ķ������ı���ʱ�����
                        
                        ec.setIs_Nullable((short)1);
                    else
                        ec.setIs_Nullable((short)2);
                    
                }
                else
                    ec.setIs_Nullable((short)2);
            }
            if(ec.getIs_Nullable()==2)//����������
            {
                String nullableCondition=ec.getNullableCondion();
                int tag=nullableCondition.indexOf("=");
                if(tag!=-1)
                {
                    String col=nullableCondition.substring(0,tag);
                    ExportColumn ec_condition=(ExportColumn)name.get(col);//������
                    ExportData cellData_condition=(ExportData)data.get(ec_condition.getData()+String.valueOf(j));//������+�к�Ϊkey��ȡ������
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
           * ���ܵĴ��������Ϸ�ʽ�У�
           * 0�����޴�
           * 1����������Ϊ�ա���1
           * 2����������󳤶ȡ���2
           * 3�������淶��淶����3
           * 4�������ڻ������Ͳ��淶����4
           * 5����������󳤶ȡ����淶��淶����5
           * 6����������󳤶ȡ����ڻ������Ͳ��淶����6 
           */
          if(errorCode!=0)
          cell.setCellStyle((HSSFCellStyle)style.get(String.valueOf(errorCode)));
        

      }

    }
    


  }
  
  /**
   * �������Ĺ淶����д��excel�ļ�
   * @param wb ��д�����ݵ�excel�ļ�
   */
  public void writeExcelNoCheck(HSSFWorkbook wb){
      HSSFSheet st = null;
      
      st = wb.createSheet();
      /**
       * д�ֶ�������һ�У�
       */
      st.createRow(0);
      for (int i = 1; i <= COLNUM; i++) {

        HSSFCell cell = st.getRow(0).createCell( (short) (i - 1));
        ExportColumn ec = (ExportColumn) name.get(String.valueOf(i)); //��data��ȡ���ֶ���
        if (ec.getData() != null) {
          cell.setCellType(HSSFCell.CELL_TYPE_STRING);
          cell.setEncoding(HSSFCell.ENCODING_UTF_16);
          cell.setCellValue(ec.getChinesedata());
        }
      }
      int startRow = ( (ROWNUM % COUNT) == 0) ? COUNT : (ROWNUM % COUNT);
      for (int i = 1; i <= startRow; i++) {
        st.createRow(i); //������
      }
      /**
       * д���ݣ�ÿ��дһ�У�ѭ��COLNUM-1��
       * 
       */
      for (int i = 1; i <= COLNUM; i++) {
          ExportColumn ec = (ExportColumn) name.get(String.valueOf(i)); //��name��ȡ���ֶ���
          
          
        for (int j = 1; j <= startRow; j++) {
          HSSFCell cell = st.getRow(j).createCell( (short) (i - 1));
          ExportData cellData = (ExportData) data.get(ec.getData() +
              String.valueOf(j)); //���ֶ������к�Ϊkey����data��ȡ������
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
   * ������Ҫ������ݲ���ϸ�����ݿ⣬�����ô洢����TONGJIBYDYNAMIC(ԺУ����,�ϱ�����)����ͳ�ƽ�������������ݿ�
   * @param year �ϱ����
   * @param xgr �޸���
   * @return �ɹ�/���ɹ�
   */public boolean saveData (int year,String xgr)
  {
      /*if(DETAIL==null)
          setSb();
       �ڹ����������Ѿ�������setSb()*/
      /*String sql_del1="delete from "+DETAIL+".t_jbxx where szyx='"+yxbm+"'";
      String sql_del2="delete from "+DETAIL+".t_dqtzgj where pid in (select id from "+DETAIL+".t_dqtz where szyx='"+yxbm+"')";
      String sql_del3="delete from "+DETAIL+".t_dqtz where szyx='"+yxbm+"'";
      String sql_del4="update datacenter.t_yxztsy t set t.sjsbzt=1 where t.sbdw='"+yxbm+"'";
      *///�洢�����ݿ�
      boolean flag=false;
      //insert into "+DETAIL+".t_jbxx select * from v_crite_jbxx_tg where dwid=?
      String sql1 = "insert into "+DETAIL+".t_dqtz(PID,TZMC,LHRQ,SZYX,YX,ZY,EJXK,YJXK,LXRQ," +
      "LXRXM,LXRDZ,LXRZJLX,LXRZJHM,LXRZJYXQZ,LXRDH,LXREMAIL,BZ," +
      "JCXGR,XGR,XGSJ,SZYXMC) " +"select ID,TZMC,LHRQ," +
      "(select c.bm from bm_jcdw c where c.id=t.szyx) as szyx ," +//���Ǳ��룡
      "(select b.mc from bm_yx b where b.id=t.yx) as yx," +
      "(select mc from bm_zyml a where a.id=t.zy) as zy," +
      "(select xk from bm_zyml a where a.id=t.zy) as ejxk," +
      "(select yijimc from v_bm_xk where erjimc=(select xk from bm_zyml a where a.id=t.zy)) as yjxk," +
      "LXRQ,LXRXM,LXRDZ,LXRZJLX,LXRZJHM,LXRZJYXQZ," +
      "LXRDH,LXREMAIL,BZ,'ϵͳ����Ա',XGR,XGSJ, " +
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
      "RS,BZ,XGR,XGSJ,'ϵͳ�޸���' from t_dqtz_gj   t where t.pid in "+
      "(select h.id from t_dqtz h where h.lhrq<'"+ (year + 1) + "-01-01'" +
      " and h.lxrq>='" + year + "-01-01' "+
      "and (select c.bm from bm_jcdw c where c.id=h.szyx) = '"+yxbm+"')";
      //��bj��jflb��zdxslb��������Ϊ����
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
      "BDYX,YWSKYYMC,BDKSRQ,BDJZRQ,'ϵͳ����Ա',XGR,XGSJ,SZYXMC,YJLXSJ " +
      "from v_allinfo t"+
      " where t.bdsj<'" + (year + 1) + "-01-01'" +
      " and (t.lxsj>='" + year + "-01-01' or lxsj is null)"
      +" and t.xszt in (3,4,5,6) "
      +" and (select h.bm from bm_jcdw h where h.id=t.szyx)='"+yxbm+"'";
      /*if(SB==Integer.MIN_VALUE)
          setSb();�ڹ����������Ѿ�������setSb()*/
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
              setSb();�ڹ����������Ѿ�������setSb()*/
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
          // TODO �Զ����� catch ��
          e1.printStackTrace();
          flag=false;
      } catch (SQLException e1) {
          // TODO �Զ����� catch ��
          e1.printStackTrace();
          flag=false;
          try {
            conn.rollback();
        } catch (SQLException e) {
            // TODO �Զ����� catch ��
            e.printStackTrace();
        }
      } catch (Exception e1) {
          // TODO �Զ����� catch ��
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
//check the unique; ��λ�ô���ʱ�� 1
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
   * �����ϱ�
   * ���ô洢����datacenter.DEL_TONGJI_BYDYNAMIC��ԺУ���룬�ϱ����Σ�
   * �ڸô洢�����У�ɾ����ָ��ԺУ��ϸ�����ݿ��е�������ݣ����޸��ϱ�״̬
   * @return �����ɹ�/���ɹ�
   */
  public boolean cancelSave()
  {
      //�����洢
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
              setSb();�ڹ����������Ѿ�������setSb()*/
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
   * ����Excel�ļ�
   * @param wb���ļ�
   * @param filepath:�ļ����·��
   */
  private void saveFile(HSSFWorkbook wb,String filepath) {
    FileOutputStream fileOut = null;

    try {
      fileOut = new FileOutputStream(filepath+this.exportSetting.getFileName());
    }
    catch (FileNotFoundException ex) {
      //System.out.print("����Ĳ����޷�ִ��,��ȷ��ָ��·����ȷ�����ļ��ѹر�!");
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
   * ���������
   * @param wb �����ڼ���excel�ļ�
   * ע���ֶ�˳����book3��book4����������ı���book3��book4���ֶ�˳��ҲҪ�Գ��������Ӧ���޸�
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
                  if(bdsj!=null&&lxsj!=null)//����ʱ�����������Уʱ��
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
                        // TODO �Զ����� catch ��
                        //e.printStackTrace();--����ת�����ɹ�������֮ǰ��������������ﲻ������
                    }
                  }
                  if(lxsj!=null)//ֻ����У״̬�¿�������Уʱ��
                  {
                      if(row.getCell((short)12)!=null)
                      {
                          String xszt=row.getCell((short)12).getStringCellValue();
                          xszt=(xszt==null)?"":xszt;
                          if(!(xszt.equals("��У")||lxsj.equals("")))
                          {
                              HSSFCell lxsjCell=row.getCell((short)11);
                              HSSFCell xsztCell=row.getCell((short)12);
                              lxsjCell.setCellStyle((HSSFCellStyle)style.get("8"));
                              xsztCell.setCellStyle((HSSFCellStyle)style.get("8"));
                          }
                      }
                  }
              }
              //��������йصļ��
              if(row.getCell((short)5)!=null&&row.getCell((short)7)!=null)
              {
                  String tlqx=row.getCell((short)5).getStringCellValue();
                  if(tlqx.equals("����"))//������Ķ�������������ͱ���ʱ���������鱣��һ��
                  {
                      String dqtz=row.getCell((short)7).getStringCellValue();
                      if(dqtz!=null&&!(dqtz.equals("������"))&&!(dqtz.equals("")))
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
      //��ô�Ƚϣ�
      
  }

  /**
   * ���ָ��������ݣ����ɹ淶�Լ�������ļ�
   * @param filepath ����excel�ļ� �����ļ���·��
   * @param templet ��ȡtxt�ļ� �����ļ���·��
   * @param dwid ��λid
   * @param year �ϱ����
   * @return
   */public int insertData(String filepath, String templet, String dwid,int year) {
      //int year = 2006;//�ϱ����
      String condition = "";//��ѯ�����������ϱ���ȶ���
      //String condition_forupdate= "";//���ϱ����ݵĲ�ѯ����������ʱ�����
      String condition_cqs_forupdate="";//���ϱ����������ݵĲ�ѯ����������ʱ�����
      String condition_dqs_forupdate="";//���ϱ����������ݵĲ�ѯ����������ʱ�����
      //String filepath ="";//�����ļ��洢λ��
      //filepath = "d:/";
      File fs = new File(filepath);
      int ret = 2;
      
      if(!fs.exists())
      {
          //System.out.println("�ļ�·�������ڣ������ԣ�");
          //return 4;
          try {
            fs.createNewFile();
        } catch (IOException e) {
            // TODO �Զ����� catch ��
            e.printStackTrace();
            return 30;
        }
      }//���·��
      
      //String dwid = "92";//��λid
      getBM(Integer.parseInt(dwid));//��id�õ�bm
      
      ret = checkState();
      if(ret == 1){//����ϱ�״̬
          
          //��year��dwid����condition
          initExcel();//��ʼ�������ļ��Ĳ��ֲ���
          ReadTxt rt=new ReadTxt();
          
          //����Ϊ������Ϣ
          //�淶����
          
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
          if(dwid.equals("")||dwid==null||dwid.equals("0"))//������
          {
              condition_cqs_forupdate = " and (t.bdsj is not null and t.bdsj<'" + (year + 1) + "-01-01'" +
              " and (t.lxsj>='" + year + "-01-01' or t.lxsj is null)) and t.xszt in (3,4,5,6) and t.tlqx=1";
          }
          else
          {
              condition_cqs_forupdate = " and (t.bdsj is not null and  t.bdsj<'" + (year + 1) + "-01-01'" +
              " and (t.lxsj>='" + year + "-01-01' or t.lxsj is null)) and t.xszt in (3,4,5,6)"+" and t.tlqx=1 and t.szyx="+dwid;
          }
          if(dwid.equals("")||dwid==null||dwid.equals("0"))//������
          {
              condition_dqs_forupdate = " and (t.bdsj is not null and t.bdsj<'" + (year + 1) + "-01-01'" +
              " and (t.lxsj>='" + year + "-01-01' or t.lxsj is null)) and t.xszt in (3,4,5,6) and t.tlqx=2";
          }
          else
          {
              condition_dqs_forupdate = " and (t.bdsj is not null and  t.bdsj<'" + (year + 1) + "-01-01'" +
              " and (t.lxsj>='" + year + "-01-01' or t.lxsj is null)) and t.xszt in (3,4,5,6)"+" and t.tlqx=2 and t.szyx="+dwid;
          }
          this.setExportSetting("jbxx_cqs_correct", year,filepath);//������
          getColumn(templet + "book3.txt",dwid);
          
          //System.out.println("��ʼ������ȷ���ݣ�");
          String jbxx=rt.readfile(templet+"jbxx.txt");
          String sql_jbxx=jbxx+condition + " order by dwid,pid";
          String sql_jbxx_forupdate=jbxx+condition_cqs_forupdate+ " order by dwid,pid";
          getData(sql_jbxx_forupdate);
          
          this.setExportSetting("jbxx_dqs_correct", year,filepath);//������
          getColumn(templet + "book3.txt",dwid);
          sql_jbxx_forupdate=jbxx+condition_dqs_forupdate+ " order by dwid,pid";
          getData(sql_jbxx_forupdate);
          
          //���淶����
          this.setExportSetting("jbxx_error", year,filepath);
          //System.out.println("��ʼ�����������ݣ�");
          String sql_no_jbxx=rt.readfile(templet+"no_jbxx.txt")+ " and t.id not in (select pid from ("+sql_jbxx+"))"
          +condition + " order by dwid,pid";
          getData(sql_no_jbxx);
          
          boolean correctTag_jbxx=correctTag;
          correctTag=true;
          //����Ϊ��������
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
          //�淶����
          this.setExportSetting("dqtz_correct", year,filepath);
          getColumn(templet + "book4.txt",dwid);
          //System.out.println("��ʼ������ȷ���ݣ�");
          String sql_dqtz=rt.readfile(templet+"dqtz.txt")+condition + " order by dwid,pid";
          getData(sql_dqtz);
          
          //���淶����
          this.setExportSetting("dqtz_error", year,filepath);
          //System.out.println("��ʼ�����������ݣ�");
          String sql_no_dqtz=rt.readfile(templet+"no_dqtz.txt")+ " and t.id not in (select pid from ("+sql_dqtz+"))"
          +condition + " order by dwid,pid";
          getData(sql_no_dqtz);
          boolean correctTag_dqtz=correctTag;
          
          if(correctTag_jbxx&&correctTag_dqtz){//correctTag���Ƿ��д������ݣ�fileTag���Ƿ�������
              //saveData(year);//�������ݣ���������ͳ�ƵĴ洢����
              ret = 0;
          }//cancelSave();//�����ϱ�
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
          //System.out.println("�������ϱ�");
      }
      return ret;
  }
  
  /**
   * �����λ�ϱ�״̬Ϊ0�����ϱ�������ɾ���ϱ�����
   * @param dwid ��λid
   * @param year �ϱ����
   * @return
   */
   public int removeData(String dwid, int year) {
      int ret = 2;
      
      getBM(Integer.parseInt(dwid));//��id�õ�bm
      ret = checkState();
      if(ret == 0){//����ϱ�״̬
    	  if(cancelSave()){
    		  ret = 1;
    	  }
      }
      else
      {
          //System.out.println("��������");
      }
      return ret;
}
      
      

 
      
      public static void main(String args[]) {
          
          //DatabaseExport de = new DatabaseExport();
          //de.run("d:/", ".", "92",2006);
          //System.out.print("�ɹ���������Enter�˳�");
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
