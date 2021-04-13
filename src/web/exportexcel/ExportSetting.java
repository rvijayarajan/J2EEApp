package exportexcel;

/**
 * <p>Title: </p>
 * <p>Description: 提供相关参数的设置和提取</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author zs
 * @version 1.1
 */

public class ExportSetting {
  //private String owner = "LXSTG199"; //数据库用户名
  private String viewName = "v_criterion"; //提取数据的视图
  private String fileName = "t_jbxx.xls"; //EXCEL文件名
  private int year = 1999;//年份
  private String filepath = "";//文件路径

  /**
   * 设置上报年度
   * @param year 上报年度
   */public void setYear(int year)
  {
    this.year=year;
  }

  /**
   * 获取上报年度
   * @return 上报年度
   */public int getYear()
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

  /**
   * 获取生成的excel文件名
   * @return 文件名
   */public String getFileName() {
    return this.fileName;
  }




  /**
   * 设置视图名
   * @param viewName The viewName to set.
   */
  public void setViewName(String viewName) {
    this.viewName = viewName;
  }

  /**
   * 获取视图名
   * @return 视图名
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