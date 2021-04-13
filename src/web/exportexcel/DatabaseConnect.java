/********************************************************************************************************
 * ϵͳ���ƣ������ѧ������ϵͳ
 * �������ƣ�DatabaseConnectPool.java
 * �������ͣ�JavaBean
 * ���ܼ�����������ݿ����Ӻ����ӳ�Bean
 * ��    �ߣ�langx
 * ��    ˾�������ʵ��ѧ�Զ����о���
 * ���ʱ�䣺
 * �� �� �ˣ�
 * �޸����ݣ�
 * �޸����ڣ�
 ********************************************************************************************************/

package exportexcel;

import java.sql.*;

import javax.naming.*;
import javax.sql.*;

public class DatabaseConnect {

	String DBUrl = "jdbc:oracle:thin:@192.168.0.89:1521:orcl"; // URL
	String DBDriver = "oracle.jdbc.driver.OracleDriver"; // DRIVER
	String DBUser = "ww"; // �û���
	String DBPassword = "ww"; // ����

    private static String DBDataSourceName = "java:comp/env/jdbc/lxsDB"; // ����Դ

    public DatabaseConnect() {
    }

    /**
     * ��������
     * @param DBUser
     */
    public void setDBUser( String DBUser ){
        this.DBUser = DBUser;
    }

    /**
     * ��������
     * @param DBPassword
     */
    public void setDBPassword( String DBPassword ){
        this.DBPassword = DBPassword;
    }

    /**
     * ��������URL
     * @param DBUrl
     */
    public void setDBUrl( String DBUrl ){
        this.DBUrl = DBUrl;
    }

    /**
     * ������������
     * @param DBDriver
     */
    public void setDBDriver( String DBDriver ){
        this.DBDriver = DBDriver;
    }

    /**
    * ��������:
    *    ֱ���������������ݿ�url�������ݿ�����
    * @throws Exception
     * */
    public Connection getConnection() throws Exception{
        Connection conn = null;
        try {
            //�������ݿ�������ֱ�ӻ�ȡ����
            Class.forName(DBDriver).newInstance();
            conn = DriverManager.getConnection(DBUrl,DBUser,DBPassword);

            // �������ݿ���Զ��ύģʽ
            conn.setAutoCommit(false);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return conn;
    }


    /**
     * ��������:
     *   ����Datasource JNDI�󶨼����������ӳ��л�ȡ���ݿ�����
     * @throws NamingException
     *   ����Դ���ƴ�����߷���������Դû�����ú�
     * @throws SQLException
     *   ������Դ��ȡ���ӷ�������
     * @throws Exception
     *   ��ȡ��������ȻΪ��
     * @return
     *   ���ػ�ȡ������
     */
    public static Connection getPooledConnection() throws NamingException,
        SQLException, Exception
    {
        Connection connDataSrc = null;
        try {
            //��ȡnaming�����ĳ�ʼ����

            InitialContext initCtx = new InitialContext();
            //�����趨�õ����ƻ�ȡDataSource
            DataSource ds = (DataSource)initCtx.lookup(DBDataSourceName);

            //�ɹ���ȡDataSource����������
            if (ds != null) {
                connDataSrc = ds.getConnection();
                // �������ݿ���Զ��ύģʽ
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