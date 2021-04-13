package exportexcel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * ��ȡ�ļ�����ز���
 * �������� 2006-7-11
 * @author zs
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class ReadTxt {
    
   
/**
 * ��ָ��·����Txt�ļ��ж����ֶ���������������ż�������Ϣ
 * txt�ļ�Ӧ����ĸ�ʽ�ǣ�ÿһ������Ϊ��ţ��ֶ�������������
 * �ֶ����ͣ��ַ���int�����ڣ����ֶγ��ȣ��Ƿ���Ҫ�淶���淶�������淶���ݣ��Ƿ�����������
 * ���á�*������
 * @param filepath
 * @return ����ֶ����Ե�hash��
 */
    public  Hashtable readtxt(String filepath) {
       Hashtable name = new Hashtable(); //���ڴ洢��txt�ļ��������ֶ�������š�������
       
        // TODO �Զ����ɷ������
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
                        
                        ec.setRowNum( (short) 0); //�к�
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
                        System.out.println("txt�ļ���������");
                        System.exit(0);
                    }
                    

                }
            }
            br.close();
            return name;
        } catch (FileNotFoundException e) {
            
            // TODO �Զ����� catch ��
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO �Զ����� catch ��
            e.printStackTrace();
            return null;
        }   
    }
    
    /**
     * ��ָ���ļ��ж�ȡÿһ�е���Ϣ����Ϊһ��string����
     * @param filepath�ļ�·��
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
            // TODO �Զ����� catch ��
            e.printStackTrace();
        } catch (IOException e) {
            // TODO �Զ����� catch ��
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
