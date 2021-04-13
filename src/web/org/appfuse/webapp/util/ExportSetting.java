package org.appfuse.webapp.util;

/**
 * <p>Title: </p>
 * <p>Description: 提供对导出数据的相关参数的设置和提取</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author zs
 * 审核：苏志远
 * @version 1.0
 */

public class ExportSetting {
  private String owner = "LXSTG199"; //数据库用户名
 // private String tableName = "EXCELDATA"; //表名，该表的字段名与提取的数据一一对应
  private String viewName = "v_criterion"; //提取数据的视图
  private String fileName = "t_jbxx.xls"; //EXCEL文件名
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
   * 设置生成的EXCEL的文件名
   * @param fileName The fileName to set.
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileName() {
    return this.fileName;
  }

  /**
   * 设置数据库用户名
   * @param owner The owner to set.
   */
  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getOwner() {
    return this.owner;
  }


  /**
   * 设置视图名
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