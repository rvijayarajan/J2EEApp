package org.appfuse.webapp.util;

/**
 * <p>Title: </p>
 * <p>Description: �ṩ�Ե������ݵ����Ե����ú���ȡ</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author zs
 * ��ˣ���־Զ
 * @version 1.0
 */

public class ExportData {

    private short rowNum;//�к�
    private short colNum;//�к�
    private String data;//����ֵ

    /**
     * @return Returns the colNum.
     */
    public short getColNum() {
        return colNum;
    }
    /**
     * @param colNum The colNum to set.
     */
    public void setColNum(short colNum) {
        this.colNum = colNum;
    }
    /**
     * @return Returns the data.
     */
    public String getData() {
        return data;
    }
    /**
     * @param data The data to set.
     */
    public void setData(String data) {
        this.data = data;
    }
    /**
     * @return Returns the rowNum.
     */
    public short getRowNum() {
        return rowNum;
    }
    /**
     * @param rowNum The rowNum to set.
     */
    public void setRowNum(short rowNum) {
        this.rowNum = rowNum;
    }

}
