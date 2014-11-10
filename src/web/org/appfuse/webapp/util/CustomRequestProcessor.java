package org.appfuse.webapp.util;

import java.io.IOException;

import java.util.Iterator;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.RequestProcessor;

import org.appfuse.model.OpRegister;
import org.appfuse.model.RoleApp;
import org.appfuse.model.SysUser;
import org.appfuse.service.RoleAppManager;
import org.appfuse.service.SysUserManager;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * <p>
 * Title: struts������Զ����RequestProcessor
 * </p>
 * <p>

 * processorClass="org.appfuse.webapp.util.CustomRequestProcessor" />
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * @author ��Ρ
 * @version 1.0
 */

public class CustomRequestProcessor extends RequestProcessor {
	private static ApplicationContext ctx = null;

	public CustomRequestProcessor() {
	}

	/**
��ȡ��
	 * @param request
	 *            HttpServletRequest http�����request
	 * @param response
	 *            HttpServletResponse https
	 * @return boolean
	 */
	protected boolean processPreprocess(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuffer urlBuffer = new StringBuffer(request.getRequestURI());
		int startIndex = urlBuffer.indexOf("//");
		if (startIndex != -1)
			urlBuffer.replace(startIndex, startIndex + 1, "");
		String url = urlBuffer.toString();

		if (request.getSession().getAttribute("sessionWasInvalid") == null
				&& request.getSession().getAttribute("sessionin") == null) {
			
			request.getSession().setAttribute("sessionWasInvalid", "true");

			String context = request.getContextPath();
			String redirect = context + "/newModify.jsp";
			try {
				response.sendRedirect(redirect);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}

		String method = request.getParameter("method");
		if ("edit_many".equals(method))
			method = "edit"; //���޸�����
		if ("deleteMore".equals(method))
			method = "delete"; //�û�����ɾ������
		String from = request.getParameter("from");

		if ("view".equals(from) && "edit".equals(method))
			method = "gotoFind"; //�鿴����
		String context = request.getContextPath();
		String fn = request.getParameter("fn");
		String module = request.getParameter("module");
		String noMenu = request.getParameter("noMenu");
		//		��Ȩ������
		String userName = (String) request.getSession()
				.getAttribute("username");
		if ("admin".equals(userName) && url.indexOf("editSysuser") == -1) {
			return true;
		} else {

			//log.fatal("��ʼ��Ȩ������");
			String href = url;
			//log.fatal("userName:" + userName);
			if (method != null && !"".equals(method))
				href = url + "?method=" + method;

			if (fn != null && !"".equals(fn) && !"null".equals(fn))
				href = href + "&fn=" + fn;

			if (module != null && !"".equals(module) && !"null".equals(module)
					&& (method == null || "".equals(method)))
				href += "?module=" + module;
		
			href = href.substring(context.length(), href.length());
			
/*			FuncallocManager fmgr = (FuncallocManager) getBean("funcallocManager");
			Node node = new Node();
			node.setLocation(href);*/
			boolean isCheck = false;
			
			if ("/menuTree.html?method=getMenuTreeItem".equals(href) || "/signup.html".equals(href)||"/saveUser.html".equals(href)  )
				isCheck = true; 
			else{
				 SysUserManager mgr=(SysUserManager)getBean("sysUserManager");
				 SysUser user= mgr.getSysUser(userName);
				 RoleAppManager mgrRole = (RoleAppManager)getBean("roleAppManager");
				 RoleApp role=mgrRole.getRoleApp(user.getRoleApp().getRolename());
				 Set<OpRegister> ops = role.getOpregisters();
				 
				 String cdurl="";
				for( Iterator ita=ops.iterator(); ita.hasNext();){
					OpRegister op = (OpRegister) ita.next();
					cdurl = op.getLoc();
					if (cdurl==null) continue;
					else
						cdurl="/"+cdurl;
					
					if (cdurl.equals(href)){
						isCheck = true; 
						break;
					}
				}
				 
			}

			if (isCheck == false) {
				String redirect = context + "/null.jsp" + "?noMenu=" + noMenu;

				//log.fatal("redirect::" + redirect);
				try {
					response.sendRedirect(redirect);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;

			} else {
				return true;
			}

		}
	}

    public Object getBean(String name) {
        if (ctx == null) {
            ctx = WebApplicationContextUtils
                    .getRequiredWebApplicationContext(servlet
                            .getServletContext());
        }
        return ctx.getBean(name);
    }
}