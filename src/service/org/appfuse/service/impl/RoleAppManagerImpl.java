package org.appfuse.service.impl;

import java.util.List;
import org.appfuse.dao.RoleAppDAO;
import org.appfuse.model.RoleApp;
import org.appfuse.service.RoleAppManager;
public class RoleAppManagerImpl extends BaseManager implements RoleAppManager {

	private RoleAppDAO dao;
	public RoleApp getRoleApp(String rolename) {
		return dao.getAppRole(rolename);
	}

	public List getRolesApp(RoleApp role) {
		return dao.getAppRoles(role);
	}

	public void removeRole(String rolename) {
		dao.removeAppRole(rolename);
	}

	public void saveRoleApp(RoleApp role) {
		dao.saveAppRole(role);

	}

	public void setRoleAppDAO(RoleAppDAO dao) {
		this.dao = dao;

	}

}
