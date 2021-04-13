
package exportexcel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

/**
 * 涉及规范性检查相关规范的操作
 * 创建日期 2006-10-12
 * @author zeng
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class CriterionData {
    //  这里全局变量不能使用static修饰符！
    public Hashtable criterionDataTable = new Hashtable();//存放规范数据，key是列名+规范值，value是规范值
	
    public Hashtable conditionTable= new Hashtable();//存放必填条件，key是列名+必填条件值，value是必填条件
    
    public Hashtable specialDataTable = new Hashtable();//存放特殊检查数据
    
    /**
     * 从数据库中查询检查所需的规范数据
     * @param name存放字段属性的hash表
     */
    public void getCriteDatabase(Hashtable name)
    {
        criterionDataTable.clear();//清空规范数据表
        Connection con= null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con=DatabaseConnect.getPooledConnection();
            if(con!=null)
            {
                for(int i=1;i<=name.size();i++)
                {
                    ExportColumn ec = (ExportColumn)name.get(String.valueOf(i));//得到字段属性
                    String condition=ec.getCriteCondition();//规范数据查询条件
                    if(!condition.equals("0"))//如果存在查询条件，则将其指示的规范数据查出来，存放在criterionDataTable中
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
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        finally
        {
            try {
                if(!con.isClosed())
                    con.close();
            } catch (SQLException e) {
                // TODO 自动生成 catch 块
                e.printStackTrace();
            }
        }
        
        

        return;
    }
    
    /**
     * 从txt文件读取检查所需的规范数据
     * 本方法并不直接操作txt文件，而是name（name是从txt文件获得的）
     * @param name存放字段属性的hash表
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
                int tag=criteData.indexOf(",");//规范数据，以‘,’隔开
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
     * 从txt得到的必填条件
     * 本方法针对必填性可变的字段，如当经费办法为中国政府奖学金时，csc编号必填
     * 本方法并不直接操作txt文件，而是name（name是从txt文件获得的）
     * @param name存放字段属性的hash表
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
     * 从数据库取得必填条件
     * @param condition 条件sql
     * @param chinesedata 列中文名（不是作为必填条件的列，而是应用了必填条件的列）
     */private void getConditionDatabase(String condition, String chinesedata) {
        // TODO 自动生成方法存根
        Connection con= null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con=DatabaseConnect.getPooledConnection();
            if(con!=null)
            {
                if(!condition.equals("0"))//如果存在查询条件，则将其指示的必填条件查出来，存放在conditionTable中
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
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        finally
        {
            try {
                if(!con.isClosed())
                    con.close();
            } catch (SQLException e) {
                // TODO 自动生成 catch 块
                e.printStackTrace();
            }
        }
        
        

        return;
    }

    /**
     * 获取短期生检查要用到的特殊数据：跟团来的短期生所属短期团组的来华日期和国籍
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
                    //key是团组名称+来华日期
                    //value是团组名称+国籍
                }
            }
        } catch (Exception e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        finally
        {
            try {
                if(!con.isClosed())
                    try {
                        con.close();
                    } catch (SQLException e) {
                        // TODO 自动生成 catch 块
                        e.printStackTrace();
                    }
            } catch (SQLException e) {
                // TODO 自动生成 catch 块
                e.printStackTrace();
            }
        }
    }
}
