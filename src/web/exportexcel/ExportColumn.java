package exportexcel;
/**
 * 字段信息
 * 提供对字段的属性的设置和提取
 * @author zeng
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ExportColumn extends ExportData {

    private String chinesedata;//字段中文名，用于写excel表头
    private short dataType;//字段类型，0为字符串，1为数字，2为日期
    private short dataLength;//字段长度
    private short is_crite;//是否需要规范
    private String criteCondition;//规范条件
    private String criteData;//规范数据
    private String nullableCondion;//必填条件
    private short is_Nullable;//是否必填，0为非必填，1为必填，2为条件必填
    
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
