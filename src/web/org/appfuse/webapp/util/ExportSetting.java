package org.appfuse.webapp.util;

/**
 * <p>Title: </p>
 * <p>Description: �ṩ�Ե������ݵ���ز��������ú���ȡ</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author zs
 * ��ˣ���־Զ
 * @version 1.0
 */

public class ExportSetting {
  private String owner = "LXSTG199"; //���ݿ��û���
 // private String tableName = "EXCELDATA"; //�������ñ���ֶ�������ȡ������һһ��Ӧ
  private String viewName = "v_criterion"; //��ȡ���ݵ���ͼ
  private String fileName = "t_jbxx.xls"; //EXCEL�ļ���
  private int year = 1999;
  private String filepath = "";

  public ExportSetting() {
  }

  public void setYear(int year)
  {
    this.year=year;
  }

  public int getYear()
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

  public String getFileName() {
    return this.fileName;
  }

  /**
   * �������ݿ��û���
   * @param owner The owner to set.
   */
  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getOwner() {
    return this.owner;
  }


  /**
   * ������ͼ��
   * @param viewName The viewName to set.
   */
  public void setViewName(String viewName) {
    this.viewName = viewName;
  }

  public String getViewName() {
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