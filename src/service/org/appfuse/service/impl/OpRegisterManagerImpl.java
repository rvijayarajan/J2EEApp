package org.appfuse.service.impl;

import java.util.List;

import org.appfuse.dao.OpRegisterDAO;
import org.appfuse.model.OpRegister;
import org.appfuse.service.OpRegisterManager;
public class OpRegisterManagerImpl extends BaseManager implements
		OpRegisterManager {

	private OpRegisterDAO dao;
	
	public OpRegister getOpRegister(Long id) {
		return dao.getOpRegister(id);
	}

	public List getOpRegisters(OpRegister op) {
		return dao.getOpRegisters(op);
	}

	public void removeOpRegistere(Long id) {
		dao.removeOpRegister(id);

	}

	public void saveOpRegister(OpRegister op) {
		dao.saveOpRegister(op);

	}

	public void setOpRegisterDAO(OpRegisterDAO dao) {
		this.dao = dao;

	}

}
