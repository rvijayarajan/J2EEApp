package exportexcel;

/**
 * �ṩ�Ե������ݵ����ú���ȡ
 * @author zs
 * @version 1.0
 */

public class ExportData {

    private short rowNum;//�к�
    private short colNum;//�к�
    private String data;//����ֵ�������ֶ�����

    /**
     * �����к�
     * @return Returns the colNum.
     */
    public short getColNum() {
        return colNum;
    }
    /**
     * �����к�
     * @param colNum The colNum to set.
     */
    public void setColNum(short colNum) {
        this.colNum = colNum;
    }
    /**
     * ������ֵ���ֶ���
     * @return Returns the data.
     */
    public String getData() {
        return data;
    }
    /**
     * ������ֵ���ֶ���
     * @param data The data to set.
     */
    public void setData(String data) {
        this.data = data;
    }
    /**
     * �����к�
     * @return Returns the rowNum.
     */
    public short getRowNum() {
        return rowNum;
    }
    /**
     * �����к�
     * @param rowNum The rowNum to set.
     */
    public void setRowNum(short rowNum) {
        this.rowNum = rowNum;
    }

}
