package org.appfuse.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 *
 * @struts.form  include-all="true" extends="BaseForm"
 * @hibernate.class table="T_GOODS"
* dynamic-update="true"
 * dynamic-insert="true"
 */

public class Goods  extends BaseObject implements  Serializable{
	private static final long serialVersionUID = 1L;
	
	protected Long goodsID;
	protected String Goodname;
	protected String rfidID;
	protected String kind;
	protected String Spec;
	protected float price;
	protected float Weight;
	protected String xgr;
	protected String xgsj;
	protected String xgsjks; 
	protected String xgsjjs; 
	
    /**
     * @struts.validator type="required"
     * @hibernate.id column="goodsID" generator-class="sequence" unsaved-value="null"
	 * @hibernate.generator-param name="sequence" value="Seq_t_goods"
     */	
	public Long getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(Long goodsID) {
		this.goodsID = goodsID;
	}
	
    /**
     * Returns the pid.
     * @hibernate.property column="Goodname"
     */
	public String getGoodname() {
		return Goodname;
	}
	public void setGoodname(String goodname) {
		Goodname = goodname;
	}
	
    /**
     * Returns the pid.
     * @hibernate.property column="RfidID"
     */
	public String getRfidID() {
		return rfidID;
	}
	public void setRfidID(String rfidID) {
		this.rfidID = rfidID;
	}
	
    /**
     * Returns the pid.
     * @hibernate.property column="kind"
     */
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	
    /**
     * Returns the pid.
     * @hibernate.property column="Spec"
     */	
	public String getSpec() {
		return Spec;
	}
	public void setSpec(String spec) {
		Spec = spec;
	}
	
    /**
     * Returns the pid.
     * @hibernate.property column="Price"
     */	
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
    /**
     * Returns the pid.
     * @hibernate.property column="Weight"
     */		
	public float getWeight() {
		return Weight;
	}
	public void setWeight(float weight) {
		Weight = weight;
	}
	
	// add by szy at 2010-8-10
    /**
     * Returns the xgr.
     * 
     */		
	public String getXgr() {
		return xgr;
	}
	public void setXgr(String xgr) {
		this.xgr = xgr;
	}
	
    /**
     * Returns the xgsj.
     * 
     */		
	public String getXgsj() {
		return xgsj;
	}
	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}
	public String getXgsjks() {
		return xgsjks;
	}
	public void setXgsjks(String xgsjks) {
		this.xgsjks = xgsjks;
	}
	public String getXgsjjs() {
		return xgsjjs;
	}
	public void setXgsjjs(String xgsjjs) {
		this.xgsjjs = xgsjjs;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Goods)) {
			return false;
		}
		Goods rhs = (Goods) object;
		return new EqualsBuilder().append(
				this.xgr, rhs.xgr).append(this.Weight, rhs.Weight).append(
				this.xgsjks, rhs.xgsjks).append(this.Spec, rhs.Spec).append(
				this.kind, rhs.kind).append(this.xgsjjs, rhs.xgsjjs).append(
				this.price, rhs.price).append(this.rfidID, rhs.rfidID).append(
				this.xgsj, rhs.xgsj).append(this.goodsID, rhs.goodsID).append(
				this.Goodname, rhs.Goodname).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1772243289, 1764635039)
				.append(this.xgr).append(this.Weight).append(
				this.xgsjks).append(this.Spec).append(this.kind).append(
				this.xgsjjs).append(this.price).append(this.rfidID).append(
				this.xgsj).append(this.goodsID).append(this.Goodname)
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("goodname", this.getGoodname())
				.append("goodsID", this.goodsID).append("xgsj", this.xgsj)
				.append("xgsjks", this.xgsjks).append("xgsjjs", this.xgsjjs)
				.append("rfidID", this.rfidID).append("price", this.price)
				.append("xgr", this.xgr).append("weight", this.getWeight())
				.append("spec", this.getSpec()).append("kind", this.kind)
				.toString();
	}

}
