package org.appfuse.webapp.action;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListInfo;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
 

/**
 * Action class to handle CRUD on a Bxxx object
 * 
 * 
 * @struts.action name="download" path="/download/download" scope="request"
 *                parameter="method"
 * 
 * @struts.action-forward name="succ" path="/WEB-INF/pages/filedown.jsp"
 */
public class DownLoadAction extends BaseAction {
	/**
	 * @param request
	 * @param jbxxlist
	 * @param vlh
	 * @param valueList
	 */
	public void expXls(HttpServletRequest request) {
		//      获取ValueList相关的东西
	//	WebApplicationContext context = WebApplicationContextUtils
	//			.getWebApplicationContext(getServlet().getServletContext());
	/*	ValueListHandler vlh = (ValueListHandler) context.getBean(
				"valueListHandler", ValueListHandler.class)*/;

		String listname = (String) request.getSession()
				.getAttribute("listname");

		ValueList valueList = (ValueList) request.getSession().getAttribute(
				"valueListForExport");
		//得到查询条件的BeanInfo
		ValueListInfo infoAll = valueList.getValueListInfo();
		
		infoAll.setPagingPage(1);  //必须设置第一页，否则，会导出为空

		/*int pagenum = infoAll.getPagingNumberPer();

		//设置一次导出的数据量的最大值
		infoAll.setPagingNumberPer(50000);
*/     
	  String queryReturn = infoAll.getQuery();	
	  String sqlreturn =infoAll.getSqlreturn();
	  Map   whereClause = infoAll.getFilters();
		//以下用于导出
		String excelName = null;

		excelName = (String) request.getParameter(EXCELNAME);

		String path = request.getParameter(PATH); //得到保存路径
		
       List list = changeSql(whereClause,queryReturn,sqlreturn);
       

		//将每页显示的行数复原，否则，会出现翻页不对的问题



	}
	/**
	 * 专门用于基层单位信息的excel导出
	 * @param request
	 */
	public void expXlsJcdwInfo(HttpServletRequest request) {
		//      获取ValueList相关的东西
	//	WebApplicationContext context = WebApplicationContextUtils
	//			.getWebApplicationContext(getServlet().getServletContext());
	/*	ValueListHandler vlh = (ValueListHandler) context.getBean(
				"valueListHandler", ValueListHandler.class)*/;

		String listname = (String) request.getSession()
				.getAttribute("listname");

		ValueList valueList = (ValueList) request.getSession().getAttribute(
				"valueListForExportJcdwinfo");
		//得到查询条件的BeanInfo
		ValueListInfo infoAll = valueList.getValueListInfo();
		
		infoAll.setPagingPage(1);  //必须设置第一页，否则，会导出为空

		/*int pagenum = infoAll.getPagingNumberPer();

		//设置一次导出的数据量的最大值
		infoAll.setPagingNumberPer(50000);
*/     
	  String queryReturn = infoAll.getQuery();	
	  String sqlreturn =infoAll.getSqlreturn();
	  Map   whereClause = infoAll.getFilters();
		//以下用于导出
		String excelName = null;

		excelName = (String) request.getParameter(EXCELNAME);

		String path = request.getParameter(PATH); //得到保存路径
		
       List list = changeSql(whereClause,queryReturn,sqlreturn);
       

		//将每页显示的行数复原，否则，会出现翻页不对的问题



	}
	


	public ActionForward saveBB(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			//取得虚拟的路径

			String saveFN = request.getParameter(SAVEFN);
			String path = request.getParameter(PATH); //待下载的文件所在的路径
			if (isNullOrSpace(saveFN)) {
				saveFN = "查询结果.xls";
			}

			String fn = "attachment; filename=" + saveFN; //必须改为UniCode编码的字符串

			String excelName = (String) request.getParameter(EXCELNAME);
			System.out.println(fn);
			//把标题、内容写到输出流中
			response.setHeader("Content-Disposition", new String(fn
					.getBytes("GB2312"), "ISO-8859-1"));
			createOutput(response.getOutputStream(), getPath(getDir(request)
					+ path)
					+ excelName);
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return null;
	}

	//
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			//取得虚拟的路径

			String saveFN = request.getParameter(SAVEFN);
			String path = request.getParameter(PATH); //待下载的文件所在的路径
			if (isNullOrSpace(saveFN)) {
				saveFN = (String)request.getAttribute(SAVEFN);
				if(isNullOrSpace(saveFN)){
					saveFN = "查询结果.zip";
				}
			}
			

			String fn = "attachment; filename=" + saveFN; //必须改为UniCode编码的字符串

			String zipName = (String) request.getAttribute(EXCELNAME);
			System.out.println(fn);
			//把标题、内容写到输出流中
			response.setHeader("Content-Disposition", new String(fn
					.getBytes("utf-8"), "ISO-8859-1"));
			createOutput(response.getOutputStream(), getPath(getDir(request)
					+ path)
					+ zipName);
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return null;
	}

	//  附件下载
	/*public ActionForward fjDownLoad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String fjmcini = (String) request.getParameter("fjmc"); //必须改为UniCode编码的字符串;
			//          取得虚拟的路径
			String fjmc = "attachment; filename=" + fjmcini;
			  FjManager mgr = (FjManager) getBean("fjManager");
		        Fj newfj = new Fj();
		        newfj.setFjmc(fjmcini);
		        newfj.setXgr(getXgr(request));
		        List fjlist = mgr.getFjs(newfj);
		        if(fjlist.size()>0)
		            newfj=(Fj)fjlist.get(0);
			String fjlj = newfj.getFjlj() ;
			System.out.println(fjmc);
			//把标题、内容写到输出流中
			response.setHeader("Content-Disposition", new String(fjmc
					.getBytes("GBK"), "ISO-8859-1"));
			createOutput(response.getOutputStream(), fjlj);
			String fjid =(String) request.getParameter("fjid");
		//  String fjlj = (String) request.getParameter("fjlj"); //必须改为UniCode编码的字符串;
			//          取得虚拟的路径
			  FjManager mgr = (FjManager) getBean("fjManager");
		        Fj newfj = new Fj();
		        newfj = mgr.getFj(fjid);
		       newfj.setFjlj(fjlj);
		        List fjlist = mgr.getFjs(newfj);
		        if(fjlist.size()>0)
		            newfj=(Fj)fjlist.get(0);
		    String fjlj  = newfj.getFjlj();   
			String fjmc = newfj.getFjmc() ;
			 fjmc = "attachment; filename=" + fjmc;
			System.out.println(fjmc);
			//把标题、内容写到输出流中
		response.setHeader("Content-Disposition", new String(fjmc
				.getBytes("GBK"), "ISO-8859-1"));
			createOutput(response.getOutputStream(), fjlj);
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return null;
	}*/
	/*private String getUserDirFj(HttpServletRequest request) {
		String path = getPath(getDir(request) +"fj\\"+ request.getSession().getAttribute("dwmc"));
		createDir(path); //如果指定的路径不存在，则创建
		return path;
	}*/
	//  附件下载
	/*public ActionForward picDownLoad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String fjlj = (String) request.getParameter("fjlj"); //必须改为UniCode编码的字符串;
			//          取得虚拟的路径
			  FjManager mgr = (FjManager) getBean("fjManager");
		        Fj newfj = new Fj();
		        newfj.setFjlj(fjlj);
		        List fjlist = mgr.getFjs(newfj);
		        if(fjlist.size()>0)
		            newfj=(Fj)fjlist.get(0);
			String fjmc = newfj.getFjmc() ;
			System.out.println(fjmc);
			String fjid =(String) request.getParameter("fjid");
			//       取得虚拟的路径
				  FjManager mgr = (FjManager) getBean("fjManager");
			        Fj newfj = new Fj();
			        newfj = mgr.getFj(fjid);
			      
			    String fjlj  = newfj.getFjlj();   
				String fjmc = newfj.getFjmc() ;
			//	 fjmc = "attachment; filename=" + fjmc;
				System.out.println(fjmc);
			//把标题、内容写到输出流中
			response.setHeader("Content-Disposition", new String(fjmc
				.getBytes("GBK"), "ISO-8859-1"));
			createOutput(response.getOutputStream(), fjlj);
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return null;
	}*/
/*	private String getUserDirPic(HttpServletRequest request) {
       	String path = getPath(getDir(request) +"pic\\"+request.getSession().getAttribute("dwmc"));
		createDir(path); //如果指定的路径不存在，则创建
		return path;
	}*/
	//输出文件
	public void createOutput(OutputStream out, String realpath)
			throws IOException {
		int b;
		BufferedInputStream m_input = null;
		try {
			try {
				m_input = new BufferedInputStream(new FileInputStream(realpath));
				while ((b = m_input.read()) != -1) {
					out.write(b);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("文件没有发现：-" + realpath);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("文件读写异常：-" + realpath);
			}
		} finally {
			m_input.close();
			out.flush();
			out.close();
		}
	}
}