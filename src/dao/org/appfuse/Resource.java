/*
 * �������� 2006-6-1
 *
 * TODO Ҫ��Ĵ���ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package org.appfuse; 



public class Resource {
	
	protected static String usr_database;
	
	protected static int defaulMenus;
	
	protected static int startMenuID;

    /**
     * @return Returns the usr_database.
     */
    public static String getUsr_database() {
        return usr_database;
    }

    /**
     * @param usr_database The usr_database to set.
     */
    public void setUsr_database(String usr_database) {
        Resource.usr_database = usr_database;
    }

	
    public static int getDefaulMenus() {
		return defaulMenus;
	}

	public  void setDefaulMenus(int defaulMenus) {
		Resource.defaulMenus = defaulMenus;
	}

	public static int getStartMenuID() {
		return startMenuID;
	}

	public  void setStartMenuID(int startMenuID) {
		Resource.startMenuID = startMenuID;
	}

  
}
