/*
 * �������� 2004-4-15
 *
 * ��װ��ѹ���ļ��ķ�����ͨ������ѹ���ļ��ķ��������Խ��������ļ�ѹ����һ��Zip�ļ��У�
 * ��Zip�ļ�������Ϊ�û�ָ���ġ�����������֤�û������·�����ļ��϶�����
 * ���ƣ���ΪJDK��ʵ�ֵ����⣬���Զ������������ļ���ѹ����ѹ���ļ��е��ļ���Ϊ���룬��ʱ�޷����
 * author��ltf
 */
package org.appfuse.webapp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
public class Zip { 
	static final int BUFFER = 2048;
	static String[] FileNames = { "2000001.xls", "2000002.xls", "2000003.xls", "2000004.xls", "2000005.xls", "2000006.xls", "2000007.xls", "2000008.xls", "2000009.xls", "2000010.xls" };
	public static void main(String argv[]) {
		ZipFiles("xxzm.zip", "C:\\Tomcat5028\\webapps\\lxs\\Matt\\", FileNames);
	}

	public static int ZipFiles(
		String ZipFileName,
		String FilePath,
		String[] FileNames) {
		try {
			BufferedInputStream origin = null;
			String FileName = null; //�洢��·�����ļ���
			String Path = getPath(FilePath); //�ԡ�\\����β��·��
		//	boolean existFiles = false; //�ж��Ƿ���Ҫѹ�����ļ�
//lx delete ����жϸ�����û�����ϡ�
			// ��Ҫ������ļ��������
			FileOutputStream dest = null; //new FileOutputStream(Path + ZipFileName);
			// �õ�ѹ�������
			ZipOutputStream out = null;
			//new ZipOutputStream(new BufferedOutputStream(dest));
			//out.setMethod(ZipOutputStream.DEFLATED);  //���÷���

			byte data[] = new byte[BUFFER];

			for (int i = 0; i < FileNames.length; i++) {
				System.out.println("����ļ�: " + FileNames[i]);

				FileName = Path + FileNames[i]; //�ļ�ȫ������·����

				try {

					FileInputStream fi = new FileInputStream(FileName);
					//existFiles = true; //����Ҫѹ�����ļ�
					if (out == null) {
						dest = new FileOutputStream(Path + ZipFileName);
						out = new ZipOutputStream(new BufferedOutputStream(dest));
					}
					origin = new BufferedInputStream(fi, BUFFER);

					ZipEntry entry = new ZipEntry(FileNames[i]);

					out.putNextEntry(entry);

					int count;
					while ((count = origin.read(data, 0, BUFFER)) != -1) {
						out.write(data, 0, count);
					}
					origin.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					System.out.println(FileName + "������");
					continue;
				}
			}
			if (out != null) {
				out.close();
			}
			System.out.println("����");
			return 0; //�ɹ�
		} catch (Exception e) {
			e.printStackTrace();
			return -1; //ʧ��

		}
	}
	//�������Ĺ����ǽ������·����ӡ�\\��,�����β���ǡ�\\������Ҫ��ӡ�\\��
	public static String getPath(String Path) {
		StringBuffer sf = new StringBuffer(Path); //���������·��
		
			if(isWin()){
			if (!Path.endsWith("\\")) {
				sf.append("\\");
			}
			}
			else if
				(Path.endsWith("\\")){
				int startIndex = sf.indexOf("\\");
				sf.replace(startIndex,startIndex+1,"");
			}
			else if(!Path.endsWith("/")){
			sf.append("/");	
			}
		
		return sf.toString();

	}

	/**
	 * @return
	 */
	 private static boolean isWin() {
  	  String OS = System.getProperty("os.name").toLowerCase();
  	  if(OS.indexOf("windows")>-1)
  		  return true;
  	  else return false;
	 }
  	
  }