package org.appfuse.webapp.action;

import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.appfuse.model.OpRegister;
import org.appfuse.model.RoleApp;
import org.appfuse.model.SysUser;
import org.appfuse.service.RoleAppManager;
import org.appfuse.service.SysUserManager;
import org.appfuse.webapp.menutree.Tree;

/**
 * author szy date 2010-8-1
 * 
 * @struts.action name="menuTreeForm" path="/menuTree" scope="request"
 *                parameter="method"
 */
public class MenuTree extends BaseAction {

	public ActionForward getMenuTreeItem(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String username = (String) request.getSession()
				.getAttribute("username");
		if (username != null || username.length() > 0) {
			SysUserManager mgr = (SysUserManager) getBean("sysUserManager");
			SysUser user = mgr.getSysUser(username);
			RoleAppManager mgrRole = (RoleAppManager) getBean("roleAppManager");
			RoleApp role = mgrRole.getRoleApp(user.getRoleApp().getRolename());
			Set<OpRegister> ops = role.getOpregisters();
				
			Tree tree = new Tree();
			tree.reload(ops);
			String menuXmlStr =tree.getMenuStream() ;
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(menuXmlStr);
		}
		return null;
	}
}
