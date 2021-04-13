package exportexcel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 读取文件的相关操作
 * 创建日期 2006-7-11
 * @author zs
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ReadTxt {
    
   
/**
 * 从指定路径的Txt文件中读入字段名、中文名、序号及其他信息
 * txt文件应满足的格式是：每一行依次为序号，字段名，中文名，
 * 字段类型（字符、int或日期），字段长度，是否需要规范，规范条件，规范数据，是否必填，必填条件
 * 并用“*”隔开
 * @param filepath
 * @return 存放字段属性的hash表
 */
    public  Hashtable readtxt(String filepath) {
       Hashtable name = new Hashtable(); //用于存储从txt文件读到的字段名，序号、中文名
       
        // TODO 自动生成方法存根
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            
            String nextLine = "0"; 
            name.clear();
            while(nextLine!=null)
            { 
                nextLine =br.readLine();
                if(nextLine!=null)
                {
                    ExportColumn ec = new ExportColumn();
                    List propertyList = new ArrayList();
                    while(nextLine.trim().length()>0)
                    {
                        int tag = nextLine.indexOf('*');
                        if(tag!=-1)
                        {
                            propertyList.add(nextLine.substring(0,tag));
                            nextLine=nextLine.substring(tag+1);
                        }
                        else
                        {
                            propertyList.add(nextLine);
                            nextLine="";
                        }
                    }
                    if(propertyList.size()==10)
                    {
                        
                        ec.setRowNum( (short) 0); //行号
                        ec.setColNum((short)Integer.parseInt(propertyList.get(0).toString()));
                        ec.setData(propertyList.get(1).toString());
                        ec.setChinesedata(propertyList.get(2).toString());
                        ec.setDataType((short)Integer.parseInt(propertyList.get(3).toString()));
                        ec.setDataLength((short)Integer.parseInt((propertyList.get(4).toString())));
                        ec.setIs_crite((short)Integer.parseInt(propertyList.get(5).toString()));
                        ec.setCriteCondition(propertyList.get(6).toString());
                        ec.setCriteData(propertyList.get(7).toString());
                        ec.setIs_Nullable((short)Integer.parseInt(propertyList.get(8).toString()));
                        ec.setNullableCondion(propertyList.get(9).toString());
                        name.put(propertyList.get(0).toString(),ec);
                    }else
                    {
                        System.out.println("txt文件内容有误！");
                        System.exit(0);
                    }
                    

                }
            }
            br.close();
            return name;
        } catch (FileNotFoundException e) {
            
            // TODO 自动生成 catch 块
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
            return null;
        }   
    }
    
    /**
     * 从指定文件中读取每一行的信息，作为一个string返回
     * @param filepath文件路径
     * @return
     */
    public String readfile(String filepath)
    {
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));

            String nextLine = ""; 
            while(nextLine!=null)
            {
                nextLine=br.readLine();
                if(nextLine!=null)
                    sb.append(nextLine+" ");
            }
            String sql=sb.toString();
            return sql;
        } catch (FileNotFoundException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        
        return null;
        

        
    }
    
/*    public static void main(String args[])
    {
        ReadTxt rt = new ReadTxt();
        rt.readfile("d:/1");
    }*/
}
