package org.appfuse.service.impl;

import java.util.List;

import org.appfuse.dao.Vist_LogDAO;
import org.appfuse.model.Visit_Log;
import org.appfuse.service.Visit_LogManager;

public class Visit_LogManagerImpl extends BaseManager 
	implements Visit_LogManager
{
	private Vist_LogDAO dao;
	
	public void setVist_LogDAO(Vist_LogDAO visit_LogDAO) {
		this.dao = visit_LogDAO;
		
	}

	public List getVisit_Log(Visit_Log visit_Log) {
		
		return dao.getVisit_Log(visit_Log);
	}

	public Visit_Log getVisit_Log(Long id) {
		
		return dao.getVisit_Log(id);
	}

	public String saveVisit_Log(Visit_Log visit_Log) {
		dao.saveVisit_Log(visit_Log);
		return visit_Log.getId().toString();
	}

	public void removeVisit_Log(Long id) {
		dao.removeVisit_Log(id);
	}

	public List getVisit_Logs(Visit_Log log) {
		 return dao.getVisit_Logs(log);
	}
	public int getVisitData(String condition) {
		 return dao.getVisitData(condition);
	}
}
