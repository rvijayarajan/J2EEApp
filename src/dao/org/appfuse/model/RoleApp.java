package org.appfuse.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This class is used to represent available app_roles in the database.</p>
 *
 * @author <a href="mailto:szyyyp@163.com">szyyyp</a>
 *
 * @struts.form extends="BaseForm"
 * @hibernate.class table="app_role"
 */

public class RoleApp extends BaseObject implements Serializable {
 
    private String rolename;
    private String description;

    private Set opregisters= new HashSet();			// collection of operations

    /**
     *  @struts.form-field
     * @struts.validator type="required"
     * @hibernate.id column="rolename"   
     *    generator-class="increment" unsaved-value="null"
     */
	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	/**
	 *  @struts.form-field
	 * @hibernate.property column="description"
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	   /**
     * @return Set
     * 
     * @hibernate.set
     * 			table="roleprevilige" 
     * 			cascade="save-update" lazy="false"
     * 			order-by="id asc"
     * @hibernate.collection-key column="rolename"
     * @hibernate.collection-many-to-many class="org.appfuse.model.OpRegister"
     *                                    column="id"
     */
	public Set getOpregisters() {
		return opregisters;
	}

	public void setOpregisters(Set opregisters) {
		this.opregisters = opregisters;
	}


	public String toString() {
		return new ToStringBuilder(this)
				.append("description", this.description).append("rolename",
						this.rolename).append("opregisters", this.opregisters)
				.toString();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof RoleApp)) {
			return false;
		}
		RoleApp rhs = (RoleApp) object;
		return new EqualsBuilder().append(
				this.rolename, rhs.rolename).append(this.opregisters,
				rhs.opregisters).append(this.description, rhs.description)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1752061743, -1166793281).append(this.rolename)
				.append(this.opregisters).append(this.description).toHashCode();
	}


}
