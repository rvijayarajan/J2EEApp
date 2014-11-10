<jsp:useBean id="menuTree" scope="page" class="org.appfuse.webapp.menutree.Tree" />
<%@page import=" java.util.Set"%>
<%@page import="org.appfuse.model.OpRegister"%>

<%
	Set<OpRegister> ops=(Set<OpRegister>) request.getSession().getAttribute("ops");

	try{
			menuTree.reload(ops);
     		menuTree.showMenu(out);
      }
      catch(OutOfMemoryError e){

      	out.print("��failure!");
      }
%>
