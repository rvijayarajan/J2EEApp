/*
 *���� () 
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

    private Long pid; //pid (��Ӧ���Ǳ���������id)

    private String bm; //���� (����������ݿ�������)

    private String fjmc; //��������

    private String fjlj; //����·��

    private String xglx; //�������

    private String bz; //��ע

    private String xgr; //�޸���

    private String xgsj; //�޸�ʱ��

    private String xgsjks; //�޸�ʱ����

    private String xgsjjs; //�޸�ʱ��ֹ

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
     * @return Returns the pid (��Ӧ���Ǳ���������id).
     * @struts.validator type="required"
     * @hibernate.property column="pid" not-null="true"
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @return Returns the ���� (����������ݿ�������).
     * @struts.validator type="required"
     * @hibernate.property column="bm" length="255" not-null="true"
     */
    public String getBm() {
        return bm;
    }

    /**
     * @return Returns the ��������.
     * @hibernate.property column="fjmc" length="255"
     */
    public String getFjmc() {
        return fjmc;
    }

    /**
     * @return Returns the ����·��.
     * @hibernate.property column="fjlj" length="255"
     */
    public String getFjlj() {
        return fjlj;
    }

    /**
     * @return Returns the �������.
     * @hibernate.property column="xglx" length="1"
     */
    public String getXglx() {
        return xglx;
    }

    /**
     * @return Returns the ��ע.
     * @hibernate.property column="bz" length="255"
     */
    public String getBz() {
        return bz;
    }

    /**
     * @return Returns the �޸���.
     * @struts.validator type="required"
     * @hibernate.property column="xgr" length="20" not-null="true"
     */
    public String getXgr() {
        return xgr;
    }

    /**
     * @return Returns the �޸�ʱ��.
     * @struts.validator type="required"
     * @hibernate.property column="xgsj" length="19" not-null="true"
     */
    public String getXgsj() {
        return xgsj;
    }

    /**
     * @return Returns the �޸�ʱ����.
     */
    public String getXgsjks() {
        return xgsjks;
    }

    /**
     * @return Returns the �޸�ʱ��ֹ.
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
     *            (��Ӧ���Ǳ���������id) The pid to set.
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * @param ����
     *            (����������ݿ�������) The bm to set.
     */
    public void setBm(String bm) {
        this.bm = bm;
    }

    /**
     * @param ��������
     *            The fjmc to set.
     */
    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    /**
     * @param ����·��
     *            The fjlj to set.
     */
    public void setFjlj(String fjlj) {
        this.fjlj = fjlj;
    }

    /**
     * @param �������
     *            The xglx to set.
     */
    public void setXglx(String xglx) {
        this.xglx = xglx;
    }

    /**
     * @param ��ע
     *            The bz to set.
     */
    public void setBz(String bz) {
        this.bz = bz;
    }

    /**
     * @param �޸���
     *            The xgr to set.
     */
    public void setXgr(String xgr) {
        this.xgr = xgr;
    }

    /**
     * @param �޸�ʱ��
     *            The xgsj to set.
     */
    public void setXgsj(String xgsj) {
        this.xgsj = xgsj;
    }

    /**
     * @param �޸�ʱ����
     *            The xgsjks to set.
     */
    public void setXgsjks(String xgsjks) {
        this.xgsjks = xgsjks;
    }

    /**
     * @param �޸�ʱ��ֹ
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
