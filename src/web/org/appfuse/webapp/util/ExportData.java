package org.appfuse.webapp.util;

/**
 * <p>Title: </p>
 * <p>Description: 提供对导出数据的属性的设置和提取</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author zs
 * 审核：苏志远
 * @version 1.0
 */

public class ExportData {

    private short rowNum;//行号
    private short colNum;//列号
    private String data;//数据值

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
