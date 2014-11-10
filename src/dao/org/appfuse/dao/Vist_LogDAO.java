package org.appfuse.dao;

import java.util.List;

import org.appfuse.model.Visit_Log;

public interface Vist_LogDAO extends DAO{
	
	public List  getVisit_Log(Visit_Log visit_Log);
	
	public Visit_Log getVisit_Log(final Long id);
	
	public void saveVisit_Log(Visit_Log visit_Log);
	
	public void removeVisit_Log(final Long id);

	public List  getVisit_Logs(Visit_Log log);
	
	public int getVisitData(String condition);
}
