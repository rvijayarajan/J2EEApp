package org.appfuse.service;

import java.util.List;
import org.appfuse.dao.OpRegisterDAO;
import org.appfuse.model.OpRegister;


public interface OpRegisterManager {
	public void setOpRegisterDAO(OpRegisterDAO dao);
	
	public List getOpRegisters(OpRegister op);
	
	public OpRegister getOpRegister(Long id);
	
	public void saveOpRegister(OpRegister op);
	
	public void removeOpRegistere(Long id);
	
}
