package exportexcel;

/**
 * 提供对导出数据的设置和提取
 * @author zs
 * @version 1.0
 */

public class ExportData {

    private short rowNum;//行号
    private short colNum;//列号
    private String data;//数据值（或是字段名）

    /**
     * 返回列号
     * @return Returns the colNum.
     */
    public short getColNum() {
        return colNum;
    }
    /**
     * 设置列号
     * @param colNum The colNum to set.
     */
    public void setColNum(short colNum) {
        this.colNum = colNum;
    }
    /**
     * 返回数值或字段名
     * @return Returns the data.
     */
    public String getData() {
        return data;
    }
    /**
     * 设置数值或字段名
     * @param data The data to set.
     */
    public void setData(String data) {
        this.data = data;
    }
    /**
     * 返回行号
     * @return Returns the rowNum.
     */
    public short getRowNum() {
        return rowNum;
    }
    /**
     * 设置行号
     * @param rowNum The rowNum to set.
     */
    public void setRowNum(short rowNum) {
        this.rowNum = rowNum;
    }

}
