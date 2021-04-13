package org.appfuse.service;

import java.util.List;

import org.appfuse.model.SysUser;
import org.appfuse.dao.SysUserDAO;

public interface SysUserManager extends Manager {

    /**
     * Setter for DAO, convenient for unit testing
     */
    public void setSysUserDAO(SysUserDAO sysUserDAO);

    /**
     * Retrieves all of the sysUsers
     */
    public List getSysUsers(SysUser sysUser);

    /**
     * Gets sysUser's information based on id.
     * @param id the sysUser's id
     * @return sysUser populated sysUser object
     */
    public SysUser getSysUser(final Long id);

    /**
     * Saves a sysUser's information
     * @param sysUser the object to be saved
     */        
    public String saveSysUser(SysUser sysUser);

    /**
     * Removes a sysUser from the database by id
     * @param id the sysUser's id
     */    
    public void removeSysUser(final String id);
    
    public SysUser getSysUser(String username);
    
    public SysUser getSysUserByRealname(String realname);
}

