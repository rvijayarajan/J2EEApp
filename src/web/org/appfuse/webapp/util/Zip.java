/*
 * 创建日期 2004-4-15
 *
 * 封装了压缩文件的方法，通过调用压缩文件的方法，可以将给定的文件压缩到一个Zip文件中，
 * 该Zip文件的名字为用户指定的。本函数不保证用户传入的路径和文件肯定存在
 * 限制：因为JDK的实现的问题，所以对于中文名的文件，压缩到压缩文件中的文件名为乱码，暂时无法解决
 * author：ltf
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
			String FileName = null; //存储带路径的文件名
			String Path = getPath(FilePath); //以“\\”结尾的路径
		//	boolean existFiles = false; //判断是否有要压缩的文件
//lx delete 这个判断根本就没有用上。
			// 到要保存的文件的输出流
			FileOutputStream dest = null; //new FileOutputStream(Path + ZipFileName);
			// 得到压缩输出流
			ZipOutputStream out = null;
			//new ZipOutputStream(new BufferedOutputStream(dest));
			//out.setMethod(ZipOutputStream.DEFLATED);  //设置方法

			byte data[] = new byte[BUFFER];

			for (int i = 0; i < FileNames.length; i++) {
				System.out.println("添加文件: " + FileNames[i]);

				FileName = Path + FileNames[i]; //文件全名（带路径）

				try {

					FileInputStream fi = new FileInputStream(FileName);
					//existFiles = true; //存在要压缩的文件
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
					System.out.println(FileName + "不存在");
					continue;
				}
			}
			if (out != null) {
				out.close();
			}
			System.out.println("结束");
			return 0; //成功
		} catch (Exception e) {
			e.printStackTrace();
			return -1; //失败

		}
	}
	//本函数的功能是将传入的路径添加“\\”,如果结尾不是“\\”，则要添加“\\”
	public static String getPath(String Path) {
		StringBuffer sf = new StringBuffer(Path); //接收输入的路径
		
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