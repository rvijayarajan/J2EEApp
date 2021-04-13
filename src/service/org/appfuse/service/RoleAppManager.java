package org.appfuse.service;

import java.util.List;

import org.appfuse.dao.RoleAppDAO;
import org.appfuse.model.RoleApp;


public interface RoleAppManager {
	public void setRoleAppDAO(RoleAppDAO dao);
	
	public List getRolesApp(RoleApp role);
	
	public RoleApp getRoleApp(String rolename);
	
	public void saveRoleApp(RoleApp role);
	
	public void removeRole(String rolename);
}