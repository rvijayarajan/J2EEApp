<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<% request.getSession().setAttribute("sessionin", "New");%>
<%-- 
<%if(session.getAttribute("dwid") !=null){%>
<script language="JavaScript"> 
window.open("editZz.html?method=find_gj&module=zzgj&noMenu=true","","location=no,scrollbars=yes,Resizable=yes,height=400,right=20")  ;

</script> 
<%}%> 
--%>
<title><fmt:message key="frameset.title"/></title>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>

<frameset rows="78,*" cols="*" frameborder="NO" border="0" framespacing="0">
  <frame src="head.jsp" name="topFrame"  marginheight="0" scrolling="NO" >
  <frameset rows="*" cols="175,*"  framespacing="0" >
  		<frame src="menuTree.html?method=getMenuTreeItem" name="leftFrame" >
 		<frame src="newModify.jsp" name="mainFrame"  >
 </frameset>

</frameset>

</html>
