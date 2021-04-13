package org.appfuse.dao.hibernate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.appfuse.Resource;
import org.appfuse.dao.Vist_LogDAO;
import org.appfuse.model.Visit_Log;
import org.springframework.orm.ObjectRetrievalFailureException;

public class Visit_LogHibernate extends BaseDAOHibernate
	implements Vist_LogDAO
{

	public List getVisit_Log(Visit_Log visit_Log) {
		
		return getHibernateTemplate().find("from Visit_Log");
	}

	public Visit_Log getVisit_Log(Long id) {
		Visit_Log visit_Log = (Visit_Log) getHibernateTemplate().get(Visit_Log.class, id);
	    if (visit_Log == null) {
	        log.warn("username , Visit_Log with id '" + id + "' not found...");
	        throw new ObjectRetrievalFailureException(Visit_Log.class, id);
	    }
	
	    return visit_Log;		
		
	}

	public void saveVisit_Log(final Visit_Log visit_Log) {
		getHibernateTemplate().saveOrUpdate(visit_Log);
		
	}

	public void removeVisit_Log(Long id) {
		getHibernateTemplate().delete(getVisit_Log(id));
		
	}

	public List getVisit_Logs(Visit_Log log) {
	    if(log == null){
            return getHibernateTemplate().find("from Visit_Log");
        }else{
            KillSQLInject ksi = new KillSQLInject(getHibernateTemplate());
            
            ksi.addStringItem("a.username",log.getUsername());
            
            return ksi.find("from Visit_Log as a");
        }
	}
	/**
	 * 返回当天到查询之前的全部访问次数／
	 * @return
	 */
	public int getVisitData(String condition) {
		 String dataname=Resource.getUsr_database();//基层数据库name
	        
		    StringBuffer sqlgetValue= new StringBuffer("select count(*) from ");
		    sqlgetValue.append(dataname).append(".t_visit_log  t ") ; 
		   sqlgetValue.append(" where t.visit_time > to_char(sysdate,'yyyy-mm-dd')");
		 //  sqlgetValue.append(" group by substr(t.visit_time,1,16) order by  substr(t.visit_time,1,16)");
		 if(condition!=null)
			 sqlgetValue.append(condition);
		   String sql = sqlgetValue.toString();
		   //要求必须有数据上报状态这个字段
		   Connection con = null;
		   Statement stat=null;
		   Session session = null;
		   ResultSet resultSet = null;
		   int visitData=-1;
		    try {

			 
			 	 session = getHibernateTemplate().getSessionFactory()
						.openSession();
				con = session.connection();
				stat =con.createStatement();
				 
				resultSet = stat.executeQuery(sql);
				while(resultSet.next())
				{visitData=resultSet.getInt(1);
					 
				}
				
				stat.close();
				con.commit();
		//		con.close();
				
				session.close();

			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (stat!=null)
						stat.close();
			/*		if (!con.isClosed())
						con.close();*/
					if(session.isOpen())
						session.close();
				} catch (Exception e) {
				//	e.printStackTrace();
				}
			}
            return  visitData;
        }
}
