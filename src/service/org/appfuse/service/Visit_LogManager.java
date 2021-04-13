package org.appfuse.service;

import java.util.List;

import org.appfuse.dao.Vist_LogDAO;
import org.appfuse.model.Visit_Log;

public interface Visit_LogManager extends Manager {
	
	public void setVist_LogDAO(Vist_LogDAO visit_LogDAO);
	
	public List getVisit_Log(Visit_Log visit_Log);
	
	public Visit_Log getVisit_Log(final Long id);
	
	public String saveVisit_Log(Visit_Log visit_Log);
	
	public void removeVisit_Log(final Long id);

	public List getVisit_Logs(Visit_Log log);
	public int getVisitData(String condition);  
}
