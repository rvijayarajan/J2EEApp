package exportexcel;

/**
 * <p>Title: </p>
 * <p>Description: �ṩ��ز��������ú���ȡ</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author zs
 * @version 1.1
 */

public class ExportSetting {
  //private String owner = "LXSTG199"; //���ݿ��û���
  private String viewName = "v_criterion"; //��ȡ���ݵ���ͼ
  private String fileName = "t_jbxx.xls"; //EXCEL�ļ���
  private int year = 1999;//���
  private String filepath = "";//�ļ�·��

  /**
   * �����ϱ����
   * @param year �ϱ����
   */public void setYear(int year)
  {
    this.year=year;
  }

  /**
   * ��ȡ�ϱ����
   * @return �ϱ����
   */public int getYear()
  {
    return this.year;
  }

  /**
   * �������ɵ�EXCEL���ļ���
   * @param fileName The fileName to set.
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * ��ȡ���ɵ�excel�ļ���
   * @return �ļ���
   */public String getFileName() {
    return this.fileName;
  }




  /**
   * ������ͼ��
   * @param viewName The viewName to set.
   */
  public void setViewName(String viewName) {
    this.viewName = viewName;
  }

  /**
   * ��ȡ��ͼ��
   * @return ��ͼ��
   */public String getViewName() {
    return this.viewName;
  }

/**
 * @return Returns the filepath.
 */
public String getFilepath() {
    return filepath;
}

/**
 * @param filepath The filepath to set.
 */
public void setFilepath(String filepath) {
    this.filepath = filepath;
}

}