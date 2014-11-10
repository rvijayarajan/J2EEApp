package org.appfuse.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author szy
 * @hibernate.class table="t_visit_log" 
 * dynamic-update="true"
 * dynamic-insert="true"
 */
public class Visit_Log extends BaseObject implements Serializable{
	private Long id; //id
	
    private String username; //用户名
    
    private String source_IP; //用户名
   

    private String source_URL; //来源URL

    private String target_URL; //目标URL

    private String visit_Time; //访问时刻
    
    private String request_Id; //id属性
    
    private String method;//方法

    /**
	 * @param request_Id 要设置的 request_Id。
	 */
	public void setRequest_Id(String request_Id) {
		this.request_Id = request_Id;
	}

	/**
	 * @return 返回 method。
	 * @hibernate.property column="method" length="200" 
     */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method 要设置的 method。
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return 返回 request_Id。
	 * @hibernate.property column="request_Id" length="200" 
    
	 */
	public String getRequest_Id() {
		return request_Id;
	}

	/**
     * @return Returns the id.
     * @hibernate.id column="id" generator-class="sequence"
     *               unsaved-value="null"
     * @hibernate.generator-param name="sequence" value= "SEQ_T_VISIT_LOG_ID"
     */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
    /**
     * @return Returns the 来源URL.
     * @hibernate.property column="Source_URL" length="200" 
     */
	public String getSource_URL() {
		return source_URL;
	}

	public void setSource_URL(String source_URL) {
		this.source_URL = source_URL;
	}
	
	/**
     * @return Returns the 来源URL.
     * @hibernate.property column="Target_URL" length="200" 
     */
	public String getTarget_URL() {
		return target_URL;
	}

	public void setTarget_URL(String target_URL) {
		this.target_URL = target_URL;
	}

	/**
     * @return Returns the 用户名.
     * @hibernate.property column="username" length="60" 
     */	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    /**
     * @return Returns the 访问时间.
     * @hibernate.property column="Visit_Time" length="30"
     */
	public String getVisit_Time() {
		return visit_Time;
	}

	public void setVisit_Time(String visit_Time) {
		this.visit_Time = visit_Time;
	}

    /**
     * @return Returns the 来源IP.
     * @hibernate.property column="source_IP" length="30"
     */
	public String getSource_IP() {
		return source_IP;
	}

	public void setSource_IP(String source_IP) {
		this.source_IP = source_IP;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Visit_Log)) {
			return false;
		}
		Visit_Log rhs = (Visit_Log) object;
		return new EqualsBuilder().append(
				this.visit_Time, rhs.visit_Time).append(this.target_URL,
				rhs.target_URL).append(this.source_IP, rhs.source_IP).append(
				this.username, rhs.username).append(this.id, rhs.id).append(
				this.source_URL, rhs.source_URL).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1996182261, 1544560129)
				.append(this.visit_Time).append(
				this.target_URL).append(this.source_IP).append(this.username)
				.append(this.id).append(this.source_URL).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("visit_Time", this.visit_Time)
				.append("target_URL", this.target_URL).append("id", this.id)
				.append("source_URL", this.source_URL).append("username",
						this.username).append("source_IP", this.source_IP)
				.toString();
	}


    
    

}
