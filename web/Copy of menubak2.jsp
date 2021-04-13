<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" errorPage="/error.jsp" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<jsp:useBean id="menuTree" scope="page" class="org.appfuse.webapp.menutree.Tree" />
<%@page import=" java.util.Set"%>
<%@page import="org.appfuse.model.OpRegister"%>

 <head>
  <title> Menu </title>
	<style type="text/css">
	body{
		margin:0px;
		padding:0px;
	}
	ul,li {
		margin:0px;
		padding:0px;
		list-style-type:none;
		font-size:12px;
	}
	a{display:block;		text-decoration:none;	}
	img{border:none;	}

	#narBar{width:155px;		border:1px solid #0000ff;	}

	.item1{	background:url(menuImages/menubar_bg.gif) repeat-x left top;}

	.item1 a.title {height:31px;line-height:31px;font-size:14px;	}
	.item1 a.title img{	padding:5px 0px;	vertical-align:middle;		border:none;	}
	
	
	.item2 a.title{		height:28px;		line-height:28px;		font-size:12px;	}
	
	.item2 a.title img{		padding:5px 0px;		vertical-align:middle;		border:none;	}

	.option{		display:block;	}
	.option li a{		height:25px;		line-height:25px;	}
	.option li a img{		padding:0px;		vertical-align:middle;		border:none;	}

</style>
<script language="javascript" type="text/javascript" src="scripts/jquery.js"></script>
<script language="javascript" type="text/javascript">
  <!--
	$(document).ready(function(){
		//alert($(".item1").length);
		
		$("#option1").height(document.documentElement.clientHeight - 4*32);
	});
	
	//submenu-behavior
	function fold(obj)
	{
		if ("block" == $(obj).next().css("display"))
		{
			$(obj).next().css("display","none");
		}
		else
		{
			$(obj).next().css("display","block");
		}
	}

	//mainmenu-behavior
	function scroll(index)
	{
		for (var i=0; i<$(".item1").length ;i++ )
		{
			$("#option" + (i+1)).css("display","none");
		}
		
		$("#option" + index).css("display","block");
		$("#option" + index).height(document.documentElement.clientHeight - 4*32);
	}
	
  //-->
  </script>
 </head>

 <body>
  <div>
	<ul id="narBar">
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
	</ul>
  </div>
 </body>
</html>
