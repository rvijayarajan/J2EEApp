package org.appfuse.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * This class is used to represent available op_registers in the database.</p>
 *
 * @struts.form extends="BaseForm"
 * @hibernate.class table="OP_REGISTER"
 *  	dynamic-update="true" 
 *    	dynamic-insert="true" 
 */
public class OpRegister extends BaseObject implements  Serializable {

	private static final long serialVersionUID = 12231L;
	
	private Long id;	
	private Long pid;				
	private String cdmc;		
	private String loc;			
	private String cdjb;			//此字段已经没有用，今后有时间应该去掉，免得有其他问题
	
	private String hasDisp;			//是否显示为菜单 1:显示；0：不显示'
	private String hasControl;		//是否要系统来进行权限控制	1：需要，0：不需要'
	private String hasUse;			//是否现在正在使用	1：在使用的，0：暂时不用的
	private String iconUrl;			//菜单图标路径
	
	private Set roleapp=new HashSet();
	
    /**
     * @struts.validator type="required"
     * @hibernate.id column="id" 
     *   	 generator-class="increment" unsaved-value="null"
     */	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
    /**
     * Returns the pid.
     * @hibernate.property column="PID"
     */
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	
    /**
     * @return String
     * @struts.validator type="required"
     * @hibernate.property column="menuName" length="20" not-null="true"
     */
	public String getCdmc() {
		return cdmc;
	}
	public void setCdmc(String cdmc) {
		this.cdmc = cdmc;
	}
	
    /**
     * @return String
     * @struts.validator type="required"
     * @hibernate.property column="url" length="200" not-null="true"
     */	
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}

	public Set getRoleapp() {
		return roleapp;
	}
	public void setRoleapp(Set roleapp) {
		this.roleapp = roleapp;
	}
	
    /**
     * Returns the pid.
     * @hibernate.property column="cdjb"
     */
    public String getCdjb() {
		return cdjb;
	}
	public void setCdjb(String cdjb) {
		this.cdjb = cdjb;
	}
	
	/**
     * @return String
     * @struts.validator type="required"
     * @hibernate.property column="HASDISPLAY" length="1" not-null="true"
     */		
	public String getHasDisp() {
		return hasDisp;
	}
	public void setHasDisp(String hasDisp) {
		this.hasDisp = hasDisp;
	}
	
    /**
     * @return String
     * @struts.validator type="required"
     * @hibernate.property column="HASCONTROL" length="1" not-null="true"
     */		
	public String getHasControl() {
		return hasControl;
	}
	public void setHasControl(String hasControl) {
		this.hasControl = hasControl;
	}
	
    /**
     * @return String
     * @struts.validator type="required"
     * @hibernate.property column="HASUSE" length="1" not-null="true"
     */			
	public String getHasUse() {
		return hasUse;
	}
	public void setHasUse(String hasUse) {
		this.hasUse = hasUse;
	}
	
    /**
     * @return String
     * @hibernate.property column="ICON" length="400" 
     */			
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	
	public String toString() {
		return "<tree text=\"" + this.cdmc + "\"" + " action=\"" + loc + "\""
				+ " cdjb=\"" + "\"" + " hasChild=\"" + "\""
				+ " target=\"mainFrame\" >";

	}
 
	public boolean equals(Object object) {
		if (!(object instanceof OpRegister)) {
			return false;
		}
		OpRegister rhs = (OpRegister) object;
		return new EqualsBuilder().append(
				this.roleapp, rhs.roleapp).append(this.iconUrl, rhs.iconUrl)
				.append(this.hasControl, rhs.hasControl).append(this.cdmc,
						rhs.cdmc).append(this.loc, rhs.loc).append(this.hasDisp,
						rhs.hasDisp).append(this.pid, rhs.pid).append(this.id,
						rhs.id).append(this.hasUse, rhs.hasUse).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-500108919, -1277997467)
				.append(this.roleapp).append(this.iconUrl)
				.append(this.hasControl).append(this.cdmc).append(this.loc)
				.append(this.hasDisp).append(this.pid)
				.append(this.id).append(this.hasUse).toHashCode();
	}
}