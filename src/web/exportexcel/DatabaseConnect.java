/********************************************************************************************************
 * 系统名称：外国留学生管理系统
 * 程序名称：DatabaseConnectPool.java
 * 程序类型：JavaBean
 * 功能简述：获得数据库连接和连接池Bean
 * 作    者：langx
 * 公    司：北京邮电大学自动化研究所
 * 完成时间：
 * 修 改 人：
 * 修改内容：
 * 修改日期：
 ********************************************************************************************************/

package exportexcel;

import java.sql.*;

import javax.naming.*;
import javax.sql.*;

public class DatabaseConnect {

	String DBUrl = "jdbc:oracle:thin:@192.168.0.89:1521:orcl"; // URL
	String DBDriver = "oracle.jdbc.driver.OracleDriver"; // DRIVER
	String DBUser = "ww"; // 用户名
	String DBPassword = "ww"; // 密码

    private static String DBDataSourceName = "java:comp/env/jdbc/lxsDB"; // 数据源

    public DatabaseConnect() {
    }

    /**
     * 设置密码
     * @param DBUser
     */
    public void setDBUser( String DBUser ){
        this.DBUser = DBUser;
    }

    /**
     * 设置密码
     * @param DBPassword
     */
    public void setDBPassword( String DBPassword ){
        this.DBPassword = DBPassword;
    }

    /**
     * 设置连接URL
     * @param DBUrl
     */
    public void setDBUrl( String DBUrl ){
        this.DBUrl = DBUrl;
    }

    /**
     * 设置连接驱动
     * @param DBDriver
     */
    public void setDBDriver( String DBDriver ){
        this.DBDriver = DBDriver;
    }

    /**
    * 功能描叙:
    *    直接利用驱动和数据库url建立数据库连接
    * @throws Exception
     * */
    public Connection getConnection() throws Exception{
        Connection conn = null;
        try {
            //加载数据库驱动，直接获取连接
            Class.forName(DBDriver).newInstance();
            conn = DriverManager.getConnection(DBUrl,DBUser,DBPassword);

            // 设置数据库非自动提交模式
            conn.setAutoCommit(false);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return conn;
    }


    /**
     * 功能描叙:
     *   采用Datasource JNDI绑定技术，从连接池中获取数据库连接
     * @throws NamingException
     *   数据源名称错误或者服务器数据源没有配置好
     * @throws SQLException
     *   从数据源获取连接发生错误
     * @throws Exception
     *   获取的连接仍然为空
     * @return
     *   返回获取的连接
     */
    public static Connection getPooledConnection() throws NamingException,
        SQLException, Exception
    {
        Connection connDataSrc = null;
        try {
            //获取naming操作的初始环境

            InitialContext initCtx = new InitialContext();
            //根据设定好的名称获取DataSource
            DataSource ds = (DataSource)initCtx.lookup(DBDataSourceName);

            //成功获取DataSource后，生成连接
            if (ds != null) {
                connDataSrc = ds.getConnection();
                // 设置数据库非自动提交模式
                connDataSrc.setAutoCommit(true);
            }
        } catch (javax.naming.NamingException nameex) {
            return null;
        } catch (SQLException sqlex) {
            return null;
        }
        if (connDataSrc == null) {
            return null;
        }
        return connDataSrc;
    }
}