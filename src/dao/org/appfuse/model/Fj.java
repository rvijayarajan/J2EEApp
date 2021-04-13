/*
 *附件 () 
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.appfuse.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
//import org.apache.struts.upload.FormFile;

/**
 * @author llh
 * 
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="t_fj" dynamic-update="true" dynamic-insert="true"
 */
public class Fj extends BaseObject implements Serializable {

    private Long id; //id

    private Long pid; //pid (对应的是表名所属的id)

    private String bm; //表名 (保存的是数据库表的名字)

    private String fjmc; //附件名称

    private String fjlj; //附件路径

    private String xglx; //相关类型

    private String bz; //备注

    private String xgr; //修改人

    private String xgsj; //修改时间

    private String xgsjks; //修改时间起

    private String xgsjjs; //修改时间止

//    private FormFile file;



    /**
     * @return Returns the id.
     * @hibernate.id column="id" generator-class="sequence"
     *               unsaved-value="null"
     * @hibernate.generator-param name="sequence" value= "SEQ_T_FJ_ID"
     */
    public Long getId() {
        return id;
    }

    /**
     * @return Returns the pid (对应的是表名所属的id).
     * @struts.validator type="required"
     * @hibernate.property column="pid" not-null="true"
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @return Returns the 表名 (保存的是数据库表的名字).
     * @struts.validator type="required"
     * @hibernate.property column="bm" length="255" not-null="true"
     */
    public String getBm() {
        return bm;
    }

    /**
     * @return Returns the 附件名称.
     * @hibernate.property column="fjmc" length="255"
     */
    public String getFjmc() {
        return fjmc;
    }

    /**
     * @return Returns the 附件路径.
     * @hibernate.property column="fjlj" length="255"
     */
    public String getFjlj() {
        return fjlj;
    }

    /**
     * @return Returns the 相关类型.
     * @hibernate.property column="xglx" length="1"
     */
    public String getXglx() {
        return xglx;
    }

    /**
     * @return Returns the 备注.
     * @hibernate.property column="bz" length="255"
     */
    public String getBz() {
        return bz;
    }

    /**
     * @return Returns the 修改人.
     * @struts.validator type="required"
     * @hibernate.property column="xgr" length="20" not-null="true"
     */
    public String getXgr() {
        return xgr;
    }

    /**
     * @return Returns the 修改时间.
     * @struts.validator type="required"
     * @hibernate.property column="xgsj" length="19" not-null="true"
     */
    public String getXgsj() {
        return xgsj;
    }

    /**
     * @return Returns the 修改时间起.
     */
    public String getXgsjks() {
        return xgsjks;
    }

    /**
     * @return Returns the 修改时间止.
     */
    public String getXgsjjs() {
        return xgsjjs;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param pid
     *            (对应的是表名所属的id) The pid to set.
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * @param 表名
     *            (保存的是数据库表的名字) The bm to set.
     */
    public void setBm(String bm) {
        this.bm = bm;
    }

    /**
     * @param 附件名称
     *            The fjmc to set.
     */
    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    /**
     * @param 附件路径
     *            The fjlj to set.
     */
    public void setFjlj(String fjlj) {
        this.fjlj = fjlj;
    }

    /**
     * @param 相关类型
     *            The xglx to set.
     */
    public void setXglx(String xglx) {
        this.xglx = xglx;
    }

    /**
     * @param 备注
     *            The bz to set.
     */
    public void setBz(String bz) {
        this.bz = bz;
    }

    /**
     * @param 修改人
     *            The xgr to set.
     */
    public void setXgr(String xgr) {
        this.xgr = xgr;
    }

    /**
     * @param 修改时间
     *            The xgsj to set.
     */
    public void setXgsj(String xgsj) {
        this.xgsj = xgsj;
    }

    /**
     * @param 修改时间起
     *            The xgsjks to set.
     */
    public void setXgsjks(String xgsjks) {
        this.xgsjks = xgsjks;
    }

    /**
     * @param 修改时间止
     *            The xgsjjs to set.
     */
    public void setXgsjjs(String xgsjjs) {
        this.xgsjjs = xgsjjs;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof Fj)) {
            return false;
        }
        Fj rhs = (Fj) object;
        return new EqualsBuilder().append(this.id, rhs.id).append(this.pid,
                rhs.pid).append(this.bm, rhs.bm).append(this.fjmc, rhs.fjmc)
                .append(this.fjlj, rhs.fjlj).append(this.xglx, rhs.xglx)
                .append(this.bz, rhs.bz).append(this.xgr, rhs.xgr).append(
                        this.xgsj, rhs.xgsj).append(this.xgsjks, rhs.xgsjks)
                .append(this.xgsjjs, rhs.xgsjjs).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).append(this.pid).append(
                this.bm).append(this.fjmc).append(this.fjlj).append(this.xglx)
                .append(this.bz).append(this.xgr).append(this.xgsj).append(
                        this.xgsjks).append(this.xgsjjs).toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("pid",
                this.pid).append("bm", this.bm).append("fjmc", this.fjmc)
                .append("fjlj", this.fjlj).append("xglx", this.xglx).append(
                        "bz", this.bz).append("xgr", this.xgr).append("xgsj",
                        this.xgsj).append("xgsjks", this.xgsjks).append(
                        "xgsjjs", this.xgsjjs).toString();
    }
}
