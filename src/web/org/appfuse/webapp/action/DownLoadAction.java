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
		//      ��ȡValueList��صĶ���
	//	WebApplicationContext context = WebApplicationContextUtils
	//			.getWebApplicationContext(getServlet().getServletContext());
	/*	ValueListHandler vlh = (ValueListHandler) context.getBean(
				"valueListHandler", ValueListHandler.class)*/;

		String listname = (String) request.getSession()
				.getAttribute("listname");

		ValueList valueList = (ValueList) request.getSession().getAttribute(
				"valueListForExport");
		//�õ���ѯ������BeanInfo
		ValueListInfo infoAll = valueList.getValueListInfo();
		
		infoAll.setPagingPage(1);  //�������õ�һҳ�����򣬻ᵼ��Ϊ��

		/*int pagenum = infoAll.getPagingNumberPer();

		//����һ�ε����������������ֵ
		infoAll.setPagingNumberPer(50000);
*/     
	  String queryReturn = infoAll.getQuery();	
	  String sqlreturn =infoAll.getSqlreturn();
	  Map   whereClause = infoAll.getFilters();
		//�������ڵ���
		String excelName = null;

		excelName = (String) request.getParameter(EXCELNAME);

		String path = request.getParameter(PATH); //�õ�����·��
		
       List list = changeSql(whereClause,queryReturn,sqlreturn);
       

		//��ÿҳ��ʾ��������ԭ�����򣬻���ַ�ҳ���Ե�����



	}
	/**
	 * ר�����ڻ��㵥λ��Ϣ��excel����
	 * @param request
	 */
	public void expXlsJcdwInfo(HttpServletRequest request) {
		//      ��ȡValueList��صĶ���
	//	WebApplicationContext context = WebApplicationContextUtils
	//			.getWebApplicationContext(getServlet().getServletContext());
	/*	ValueListHandler vlh = (ValueListHandler) context.getBean(
				"valueListHandler", ValueListHandler.class)*/;

		String listname = (String) request.getSession()
				.getAttribute("listname");

		ValueList valueList = (ValueList) request.getSession().getAttribute(
				"valueListForExportJcdwinfo");
		//�õ���ѯ������BeanInfo
		ValueListInfo infoAll = valueList.getValueListInfo();
		
		infoAll.setPagingPage(1);  //�������õ�һҳ�����򣬻ᵼ��Ϊ��

		/*int pagenum = infoAll.getPagingNumberPer();

		//����һ�ε����������������ֵ
		infoAll.setPagingNumberPer(50000);
*/     
	  String queryReturn = infoAll.getQuery();	
	  String sqlreturn =infoAll.getSqlreturn();
	  Map   whereClause = infoAll.getFilters();
		//�������ڵ���
		String excelName = null;

		excelName = (String) request.getParameter(EXCELNAME);

		String path = request.getParameter(PATH); //�õ�����·��
		
       List list = changeSql(whereClause,queryReturn,sqlreturn);
       

		//��ÿҳ��ʾ��������ԭ�����򣬻���ַ�ҳ���Ե�����



	}
	


	public ActionForward saveBB(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			//ȡ�������·��

			String saveFN = request.getParameter(SAVEFN);
			String path = request.getParameter(PATH); //�����ص��ļ����ڵ�·��
			if (isNullOrSpace(saveFN)) {
				saveFN = "��ѯ���.xls";
			}

			String fn = "attachment; filename=" + saveFN; //�����ΪUniCode������ַ���

			String excelName = (String) request.getParameter(EXCELNAME);
			System.out.println(fn);
			//�ѱ��⡢����д���������
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
			//ȡ�������·��

			String saveFN = request.getParameter(SAVEFN);
			String path = request.getParameter(PATH); //�����ص��ļ����ڵ�·��
			if (isNullOrSpace(saveFN)) {
				saveFN = (String)request.getAttribute(SAVEFN);
				if(isNullOrSpace(saveFN)){
					saveFN = "��ѯ���.zip";
				}
			}
			

			String fn = "attachment; filename=" + saveFN; //�����ΪUniCode������ַ���

			String zipName = (String) request.getAttribute(EXCELNAME);
			System.out.println(fn);
			//�ѱ��⡢����д���������
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

	//  ��������
	/*public ActionForward fjDownLoad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String fjmcini = (String) request.getParameter("fjmc"); //�����ΪUniCode������ַ���;
			//          ȡ�������·��
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
			//�ѱ��⡢����д���������
			response.setHeader("Content-Disposition", new String(fjmc
					.getBytes("GBK"), "ISO-8859-1"));
			createOutput(response.getOutputStream(), fjlj);
			String fjid =(String) request.getParameter("fjid");
		//  String fjlj = (String) request.getParameter("fjlj"); //�����ΪUniCode������ַ���;
			//          ȡ�������·��
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
			//�ѱ��⡢����д���������
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
		createDir(path); //���ָ����·�������ڣ��򴴽�
		return path;
	}*/
	//  ��������
	/*public ActionForward picDownLoad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String fjlj = (String) request.getParameter("fjlj"); //�����ΪUniCode������ַ���;
			//          ȡ�������·��
			  FjManager mgr = (FjManager) getBean("fjManager");
		        Fj newfj = new Fj();
		        newfj.setFjlj(fjlj);
		        List fjlist = mgr.getFjs(newfj);
		        if(fjlist.size()>0)
		            newfj=(Fj)fjlist.get(0);
			String fjmc = newfj.getFjmc() ;
			System.out.println(fjmc);
			String fjid =(String) request.getParameter("fjid");
			//       ȡ�������·��
				  FjManager mgr = (FjManager) getBean("fjManager");
			        Fj newfj = new Fj();
			        newfj = mgr.getFj(fjid);
			      
			    String fjlj  = newfj.getFjlj();   
				String fjmc = newfj.getFjmc() ;
			//	 fjmc = "attachment; filename=" + fjmc;
				System.out.println(fjmc);
			//�ѱ��⡢����д���������
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
		createDir(path); //���ָ����·�������ڣ��򴴽�
		return path;
	}*/
	//����ļ�
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
				System.out.println("�ļ�û�з��֣�-" + realpath);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("�ļ���д�쳣��-" + realpath);
			}
		} finally {
			m_input.close();
			out.flush();
			out.close();
		}
	}
}