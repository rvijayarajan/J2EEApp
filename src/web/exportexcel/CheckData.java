package exportexcel;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Hashtable;

/**
 * 检查数据规范性
 * @author zeng
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class CheckData {
    /**
     * 检查数据规范性，返回检查结果代码
     * @param dataToCheck需要检查的数据
     * @param colNum该数据所属字段序号
     * @param name存放字段属性的hash表
     * @param cd存放规范数据的hash表
     * @return 检查结果代码
    **/
    public static short checkData(String dataToCheck,short colNum,Hashtable name,CriterionData cd)
    {
        ExportColumn ec = (ExportColumn)name.get(String.valueOf(colNum));//得到字段属性
        short errorCode=0;
        /*
         * 检查方法：
         * 首先判断数据是否为空。
         * 如果是空，判断是否必填，如果是必填项，错误代码+1，且不用进行下面的检查
         * 判断长度，超过允许的最大长度+2；
         * 如果是待规范项，判断规范性，不规范+3；
         * 如果是日期或数字型，检查格式，不规范+4；
         * 返回错误代码
         * 
         * 可能的错误代码组合方式有：
         * 0——无错
         * 1——必填项为空——1
         * 2——超过最大长度——2
         * 3——待规范项不规范——3
         * 4——日期或数字型不规范——4
         * 5——超过最大长度、待规范项不规范——5
         * 6——超过最大长度、日期或数字型不规范——6
         */
        if(dataToCheck==null||dataToCheck.equals(""))
        {
            short is_nullable=ec.getIs_Nullable();
            if(is_nullable==1)
                errorCode+=1;
        }
        else
        {
            short dataLength=ec.getDataLength();
            if(dataToCheck.length()>dataLength)
                errorCode+=2;
            short is_crite=ec.getIs_crite();
            if(is_crite==1)
            {
                //检查规范数据
                if(!cd.criterionDataTable.containsKey(ec.getChinesedata()+dataToCheck))
                    
                    errorCode+=3;
            }
            short dataType=ec.getDataType();
            if(dataType==1)
            {
                //数字
                try {
                    Integer.parseInt(dataToCheck);
                } catch (NumberFormatException e) {
                    errorCode+=4;
                    //e.printStackTrace();
                }
            }
            else if(dataType==2)
            {//日期
                try {
                    DateFormat.getDateInstance().parse(dataToCheck);
                } catch (ParseException e) {
                    errorCode+=4;
                    //e.printStackTrace();
                }
            }
        }
        
        
        return errorCode;
        
    }


}
