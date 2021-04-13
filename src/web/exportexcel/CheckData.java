package exportexcel;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Hashtable;

/**
 * ������ݹ淶��
 * @author zeng
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class CheckData {
    /**
     * ������ݹ淶�ԣ����ؼ��������
     * @param dataToCheck��Ҫ��������
     * @param colNum�����������ֶ����
     * @param name����ֶ����Ե�hash��
     * @param cd��Ź淶���ݵ�hash��
     * @return ���������
    **/
    public static short checkData(String dataToCheck,short colNum,Hashtable name,CriterionData cd)
    {
        ExportColumn ec = (ExportColumn)name.get(String.valueOf(colNum));//�õ��ֶ�����
        short errorCode=0;
        /*
         * ��鷽����
         * �����ж������Ƿ�Ϊ�ա�
         * ����ǿգ��ж��Ƿ�������Ǳ�����������+1���Ҳ��ý�������ļ��
         * �жϳ��ȣ������������󳤶�+2��
         * ����Ǵ��淶��жϹ淶�ԣ����淶+3��
         * ��������ڻ������ͣ�����ʽ�����淶+4��
         * ���ش������
         * 
         * ���ܵĴ��������Ϸ�ʽ�У�
         * 0�����޴�
         * 1����������Ϊ�ա���1
         * 2����������󳤶ȡ���2
         * 3�������淶��淶����3
         * 4�������ڻ������Ͳ��淶����4
         * 5����������󳤶ȡ����淶��淶����5
         * 6����������󳤶ȡ����ڻ������Ͳ��淶����6
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
                //���淶����
                if(!cd.criterionDataTable.containsKey(ec.getChinesedata()+dataToCheck))
                    
                    errorCode+=3;
            }
            short dataType=ec.getDataType();
            if(dataType==1)
            {
                //����
                try {
                    Integer.parseInt(dataToCheck);
                } catch (NumberFormatException e) {
                    errorCode+=4;
                    //e.printStackTrace();
                }
            }
            else if(dataType==2)
            {//����
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
