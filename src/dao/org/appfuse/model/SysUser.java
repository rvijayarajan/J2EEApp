package org.appfuse.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
  	@author:  
  	@complete date:   
  	modifer:
  	modifer date:
  	
 	* @struts.form include-all="true" extends="BaseForm"
	 * @hibernate.class table="SYSUSER"
*/

public class SysUser extends BaseObject   implements Serializable{
	protected Long id;
	
	protected String userName;
	
	protected  RoleApp roleApp;
	
	protected String pass;
	
	protected String realName;
	
	protected String phone;
	
	protected String Email;
	
	protected String RfidLabel;
	
	/**
	 * @return Returns the id.
	 * @struts.form-field
	 * @hibernate.id column="id" generator-class="sequence" unsaved-value="null"
	 * @hibernate.generator-param name="sequence" value="Seq_sysuser"
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	   /**
     * Returns the username.
     * 
     * @return String
     * 
     * @struts.validator type="required"
	 * @hibernate.property column="username"
     */
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @struts.form-field
	 * @struts.validator type="required"
	 * @hibernate.many-to-one column="rolename"
	 *                        class="org.appfuse.model.RoleApp"
	 */
	public RoleApp getRoleApp() {
		return roleApp;
	}
	public void setRoleApp(RoleApp role) {
		this.roleApp = role;
	}
	
    /**
     * @return String
     * @struts.validator type="required"
     * @hibernate.property column="Password" length="6" 
     */	
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
    /**
     * @return String
     * @struts.validator type="required"
     * @hibernate.property column="realName" length="20" 
     */	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
    /**
     * @return String
     * @hibernate.property column="phone" length="20" 
     */		
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
    /**
     * @return String
     * @hibernate.property column="Mailbox" length="20" 
     */			
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
    /**
     * @return String
     * @hibernate.property column="RFID" length="16" 
     */		
	public String getRfidLabel() {
		return RfidLabel;
	}
	public void setRfidLabel(String labelID) {
		this.RfidLabel = labelID;
	}


	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysUser)) {
			return false;
		}
		SysUser rhs = (SysUser) object;
		return new EqualsBuilder().append(
				this.phone, rhs.phone).append(this.realName, rhs.realName)
				.append(this.RfidLabel, rhs.RfidLabel).append(this.userName,
						rhs.userName).append(this.Email, rhs.Email).append(
						this.pass, rhs.pass).append(this.roleApp, rhs.roleApp)
				.append(this.id, rhs.id)
				.isEquals();
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("phone", this.phone).append(
				"pass", this.pass).append("email", this.getEmail()).append(
				"rfidLabel", this.getRfidLabel()).append("realName",
				this.realName).append("roleApp", this.roleApp).append(
				"userName", this.userName)
				.append(this.id)
				.toString();
	}


	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1617115057, 2090982719)
				.append(this.id)
				.append(this.phone).append(this.realName)
				.append(this.RfidLabel).append(this.userName)
				.append(this.Email).append(this.pass).append(this.roleApp)
				.toHashCode();
	}

}
