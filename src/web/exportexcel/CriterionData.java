
package exportexcel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

/**
 * �漰�淶�Լ����ع淶�Ĳ���
 * �������� 2006-10-12
 * @author zeng
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class CriterionData {
    //  ����ȫ�ֱ�������ʹ��static���η���
    public Hashtable criterionDataTable = new Hashtable();//��Ź淶���ݣ�key������+�淶ֵ��value�ǹ淶ֵ
	
    public Hashtable conditionTable= new Hashtable();//��ű���������key������+��������ֵ��value�Ǳ�������
    
    public Hashtable specialDataTable = new Hashtable();//�������������
    
    /**
     * �����ݿ��в�ѯ�������Ĺ淶����
     * @param name����ֶ����Ե�hash��
     */
    public void getCriteDatabase(Hashtable name)
    {
        criterionDataTable.clear();//��չ淶���ݱ�
        Connection con= null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con=DatabaseConnect.getPooledConnection();
            if(con!=null)
            {
                for(int i=1;i<=name.size();i++)
                {
                    ExportColumn ec = (ExportColumn)name.get(String.valueOf(i));//�õ��ֶ�����
                    String condition=ec.getCriteCondition();//�淶���ݲ�ѯ����
                    if(!condition.equals("0"))//������ڲ�ѯ����������ָʾ�Ĺ淶���ݲ�����������criterionDataTable��
                    {
                        ps=con.prepareStatement(condition);
                        rs=ps.executeQuery(condition);
                        while(rs.next())
                        {
                            criterionDataTable.put(ec.getChinesedata()+rs.getString(1),rs.getString(1));
                        }
                        ps.close();
                        rs.close();
                    }
                    
                }
            }
        } catch (Exception e) {
            // TODO �Զ����� catch ��
            e.printStackTrace();
        }
        finally
        {
            try {
                if(!con.isClosed())
                    con.close();
            } catch (SQLException e) {
                // TODO �Զ����� catch ��
                e.printStackTrace();
            }
        }
        
        

        return;
    }
    
    /**
     * ��txt�ļ���ȡ�������Ĺ淶����
     * ����������ֱ�Ӳ���txt�ļ�������name��name�Ǵ�txt�ļ���õģ�
     * @param name����ֶ����Ե�hash��
     */
    public void getCriteTxt(Hashtable name){
        for(int i=1;i<=name.size();i++)
        {
            ExportColumn ec = (ExportColumn)name.get(String.valueOf(i));
            String criteData=ec.getCriteData();
            if(!criteData.equals("0"))
            {
                while(criteData.trim().length()>0)
                {
                int tag=criteData.indexOf(",");//�淶���ݣ��ԡ�,������
                if(tag!=-1)
                {
                    criterionDataTable.put(ec.getChinesedata()+criteData.substring(0,tag),criteData.substring(0,tag));
                    criteData=criteData.substring(tag+1);
                }
                else
                {
                    criterionDataTable.put(ec.getChinesedata()+criteData,criteData);
                    criteData="";
                }
                }
                
            }
            
        }
    }
	
	/**
     * ��txt�õ��ı�������
     * ��������Ա����Կɱ���ֶΣ��統���Ѱ취Ϊ�й�������ѧ��ʱ��csc��ű���
     * ����������ֱ�Ӳ���txt�ļ�������name��name�Ǵ�txt�ļ���õģ�
     * @param name����ֶ����Ե�hash��
	 */
    public void getConditionTxt(Hashtable name){
        for(int i=1;i<=name.size();i++)
        {
            ExportColumn ec = (ExportColumn)name.get(String.valueOf(i));
            String nullableCondition=ec.getNullableCondion();
            int tag=nullableCondition.indexOf("=");
            if(tag!=-1)
            {
                //String col=nullableCondition.substring(0,tag);
                String condition=nullableCondition.substring(tag+1);
                if(condition.indexOf("select")==-1){
                    while(condition.length()>0)
                    {
                        int tag2=condition.indexOf(",");
                        if(tag2!=-1)
                        {
                            conditionTable.put(ec.getChinesedata()+condition.substring(0,tag),condition.substring(0,tag));
                            condition=condition.substring(tag+1);
                        }
                        else
                        {
                            conditionTable.put(ec.getChinesedata()+condition,condition);
                            condition="";
                        }
                    }
                }else{
                    getConditionDatabase(condition,ec.getChinesedata());
                }
            }
            
        }
    }
    
    /**
     * �����ݿ�ȡ�ñ�������
     * @param condition ����sql
     * @param chinesedata ����������������Ϊ�����������У�����Ӧ���˱����������У�
     */private void getConditionDatabase(String condition, String chinesedata) {
        // TODO �Զ����ɷ������
        Connection con= null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con=DatabaseConnect.getPooledConnection();
            if(con!=null)
            {
                if(!condition.equals("0"))//������ڲ�ѯ����������ָʾ�ı�������������������conditionTable��
                    {
                        ps=con.prepareStatement(condition);
                        rs=ps.executeQuery(condition);
                        while(rs.next())
                        {
                            conditionTable.put(chinesedata+rs.getString(1),rs.getString(1));
                        }
                        ps.close();
                        rs.close();
                    }
            }
        } catch (Exception e) {
            // TODO �Զ����� catch ��
            e.printStackTrace();
        }
        finally
        {
            try {
                if(!con.isClosed())
                    con.close();
            } catch (SQLException e) {
                // TODO �Զ����� catch ��
                e.printStackTrace();
            }
        }
        
        

        return;
    }

    /**
     * ��ȡ���������Ҫ�õ����������ݣ��������Ķ�������������������������ں͹���
     * @param dwid
     */
    public void getSpecialData(String dwid)
    {
        
        Connection con= null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql="select a.tzmc,a.lhrq,c.mc from t_dqtz a ,t_dqtz_gj b ,bm_gj c where a.id=b.pid and b.gj=c.id  and szyx ="+dwid;
        
        try {
            con=DatabaseConnect.getPooledConnection();
            if(con!=null)
            {
                ps=con.prepareStatement(sql);
                rs=ps.executeQuery();
                while(rs.next())
                {
                    String tzmc=rs.getString(1)==null?"":rs.getString(1);
                    String lhrq=rs.getString(2)==null?"":rs.getString(2);
                    String gj=rs.getString(3)==null?"":rs.getString(3);
                    specialDataTable.put(tzmc+lhrq,tzmc+gj);
                    //key����������+��������
                    //value����������+����
                }
            }
        } catch (Exception e) {
            // TODO �Զ����� catch ��
            e.printStackTrace();
        }
        finally
        {
            try {
                if(!con.isClosed())
                    try {
                        con.close();
                    } catch (SQLException e) {
                        // TODO �Զ����� catch ��
                        e.printStackTrace();
                    }
            } catch (SQLException e) {
                // TODO �Զ����� catch ��
                e.printStackTrace();
            }
        }
    }
}
