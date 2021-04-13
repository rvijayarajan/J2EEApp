package org.appfuse.dao;

import java.util.List;

import org.appfuse.model.SysUser;

public interface SysUserDAO extends DAO {

    /**
     * Retrieves all of the sysUsers
     */
    public List  getSysUsers(SysUser sysUser);

    /**
     * Gets sysUser's information based on id. An 
     * ObjectRetrievalFailureException Runtime Exception is thrown if 
     * nothing is found.
     * 
     * @param id the sysUser's id
     * @return sysUser populated sysUser object
     */
    public SysUser getSysUser(final Long id);

    /**
     * Saves a sysUser's information
     * @param sysUser the object to be saved
     * @return SysUser the persisted sysUser object
     */	
    public void saveSysUser(SysUser sysUser);

	/**
     * Removes a sysUser from the database by id
     * @param id the sysUser's id
     */
    public void removeSysUser(final Long id);
    
    public SysUser getSysUser(String userName);
    
    public SysUser getSysUserByRealname(String realname);
    
}

