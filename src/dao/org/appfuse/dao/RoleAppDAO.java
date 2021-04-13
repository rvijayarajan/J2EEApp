package org.appfuse.dao;

import java.util.List;

import org.appfuse.model.RoleApp;


public interface RoleAppDAO extends DAO {

    public RoleApp getAppRole(String rolename);

    public List  getAppRoles(RoleApp role);

    public void saveAppRole(final RoleApp role);

    public void removeAppRole(final String rolename);
    
}