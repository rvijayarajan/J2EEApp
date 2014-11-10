package org.appfuse.service.impl;

import java.util.List;

import org.appfuse.model.SysUser;
import org.appfuse.dao.SysUserDAO;
import org.appfuse.service.SysUserManager;

public class SysUserManagerImpl extends BaseManager implements SysUserManager {
    private SysUserDAO dao;

    /**
     * Set the DAO for communication with the data layer.
     * @param dao
     */
    public void setSysUserDAO(SysUserDAO dao) {
        this.dao = dao;
    }

    /**
     * @see org.appfuse.service.SysUserManager#getSysUsers(org.appfuse.model.SysUser)
     */
    public List getSysUsers(final SysUser sysUser) {
        return dao.getSysUsers(sysUser);
    }

    /**
     * @see org.appfuse.service.SysUserManager#getSysUser(final String id)
     */
    public SysUser getSysUser(final Long id) {
        return dao.getSysUser(id);
    }

    /**
     * @see org.appfuse.service.SysUserManager#saveSysUser(SysUser sysUser)
     */    
    public String saveSysUser(final SysUser sysUser) {

    	dao.saveSysUser(sysUser);
    	return sysUser.getId().toString();
    }

    /**
     * @see org.appfuse.service.SysUserManager#removeSysUser(final Long id)
     */    
    public void removeSysUser(final String id) {
        dao.removeSysUser(new Long(id));
    }

	public SysUser getSysUser(String username) {
		return dao.getSysUser(username);
	}

	public SysUser getSysUserByRealname(String realname) {
		// TODO Auto-generated method stub
		return dao.getSysUserByRealname(realname);
	}
}