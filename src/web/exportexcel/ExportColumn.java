package exportexcel;
/**
 * �ֶ���Ϣ
 * �ṩ���ֶε����Ե����ú���ȡ
 * @author zeng
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class ExportColumn extends ExportData {

    private String chinesedata;//�ֶ�������������дexcel��ͷ
    private short dataType;//�ֶ����ͣ�0Ϊ�ַ�����1Ϊ���֣�2Ϊ����
    private short dataLength;//�ֶγ���
    private short is_crite;//�Ƿ���Ҫ�淶
    private String criteCondition;//�淶����
    private String criteData;//�淶����
    private String nullableCondion;//��������
    private short is_Nullable;//�Ƿ���0Ϊ�Ǳ��1Ϊ���2Ϊ��������
    
    public ExportColumn() {
        super();
    }

    /**
     * @return Returns the chinesedata.
     */
    public String getChinesedata() {
        return chinesedata;
    }

    /**
     * @param chinesedata The chinesedata to set.
     */
    public void setChinesedata(String chinesedata) {
        this.chinesedata = chinesedata;
    }

    /**
     * @return Returns the criteCondition.
     */
    public String getCriteCondition() {
        return criteCondition;
    }

    /**
     * @param criteCondition The criteCondition to set.
     */
    public void setCriteCondition(String criteCondition) {
        this.criteCondition = criteCondition;
    }

    /**
     * @return Returns the criteData.
     */
    public String getCriteData() {
        return criteData;
    }

    /**
     * @param criteData The criteData to set.
     */
    public void setCriteData(String criteData) {
        this.criteData = criteData;
    }

    /**
     * @return Returns the dataLength.
     */
    public short getDataLength() {
        return dataLength;
    }

    /**
     * @param dataLength The dataLength to set.
     */
    public void setDataLength(short dataLength) {
        this.dataLength = dataLength;
    }

    /**
     * @return Returns the dataType.
     */
    public short getDataType() {
        return dataType;
    }

    /**
     * @param dataType The dataType to set.
     */
    public void setDataType(short dataType) {
        this.dataType = dataType;
    }

    /**
     * @return Returns the is_Nullable.
     */
    public short getIs_Nullable() {
        return is_Nullable;
    }

    /**
     * @param is_Nullable The is_Nullable to set.
     */
    public void setIs_Nullable(short is_Nullable) {
        this.is_Nullable = is_Nullable;
    }

    /**
     * @return Returns the nullableCondion.
     */
    public String getNullableCondion() {
        return nullableCondion;
    }

    /**
     * @param nullableCondion The nullableCondion to set.
     */
    public void setNullableCondion(String nullableCondion) {
        this.nullableCondion = nullableCondion;
    }

    /**
     * @return Returns the is_crite.
     */
    public short getIs_crite() {
        return is_crite;
    }

    /**
     * @param is_crite The is_crite to set.
     */
    public void setIs_crite(short is_crite) {
        this.is_crite = is_crite;
    }


}
