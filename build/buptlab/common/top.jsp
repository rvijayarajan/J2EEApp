<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>

<%@ include file="/common/taglibs.jsp"%>
<%--type= <%=request.getParameter("from")%>--%>
<% request.setAttribute("configName", "microsoftLook");%>

<table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
<%--首先,按照操作方法进行分类!其次在页面生成时,考虑转发路径!--%>
<c:choose>
<c:when test="${ptype=='find'}"> 
		<tr>
          <td height="20" colspan="2">&nbsp; </td>
        </tr>
        <tr>
          <td width="5%" height="20">&nbsp;</td>
          <td height="20">
		  <a href=<c:out value="${path}" />?method=edit onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3','','<c:url value="/images/common/icons/new_over.gif"/>',1)">
		  <img src="<c:url value="/images/common/icons/new_on.gif"/>" name="Image3" width="60" height="20"></a>
		  
		  <img src="<c:url value="/images/common/icons/search_off.gif"/>" width="60" height="20">
		  
		  <img src="<c:url value="/images/common/icons/edit_off.gif"/>" width="60" height="20">
		  
		  <img src="<c:url value="/images/common/icons/del_off.gif"/>" width="60" height="20"></td>
        </tr>
        <tr>
          <td width="95%" height="20" colspan="2">&nbsp;</td>
        </tr>
		
</c:when>

<c:when test="${ptype=='form'}">
				
		<tr>
          <td height="20" colspan="2">&nbsp;</td>
        </tr>
        <tr>
          <td width="5%" height="20">&nbsp;</td>
          <td height="20">
		  <%// if(request.getParameter("from") != null &&(request.getParameter("from").equals("edit_many") || request.getParameter("from").equals("del_many_one") )){ %>
		  
		  <% if(request.getParameter("from")!= null ) {%>
			<a href=<c:out value="${path}" />?method=edit onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3','','<c:url value="/images/common/icons/new_over.gif"/>',1)">
			<img src="<c:url value="/images/common/icons/new_on.gif"/>" name="Image3" width="60" height="20"></a>
		  <%}else{%>
		    <img src="<c:url value="/images/common/icons/new_off.gif"/>" name="Image3" width="60" height="20">
		  <%}%>
		  
		  <a href=<c:out value="${path}" />?method=gotoFind onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image51','','<c:url value="/images/common/icons/search_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/search_on.gif"/>" name="Image51" width="60" height="20" border="0" id="Image51"></a>
		  
		  <img src="<c:url value="/images/common/icons/edit_off.gif"/>" width="60" height="20">
		
		  <%--
		  <%if (request.getParameter("from") != null &&(request.getParameter("from").equals("list")|| request.getParameter("from").equals("del_many") )){ %>
			<a href="javascript:pl_del()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','<c:url value="/images/common/icons/del_over.gif"/>',1)">
			<img src="<c:url value="/images/common/icons/del_on.gif"/>" name="Image8" width="60" height="20"></a>
		  <% }else if (request.getParameter("from") != null && request.getParameter("from").equals("edit")){ %>
			<a href="javascript:del()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','<c:url value="/images/common/icons/del_over.gif"/>',1)">
			<img src="<c:url value="/images/common/icons/del_on.gif"/>" name="Image8" width="60" height="20"></a>
		  <% }else if (request.getParameter("from") != null && (request.getParameter("from").equals("edit_many")|| request.getParameter("from").equals("del_many_one"))){ %>
			<a href="javascript:pl_del_one()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','<c:url value="/images/common/icons/del_over.gif"/>',1)">
			<img src="<c:url value="/images/common/icons/del_on.gif"/>" name="Image8" width="60" height="20">
		  <%} else {%>
			<img src="<c:url value="/images/common/icons/del_off.gif"/>" name="Image8" width="60" height="20">
		  <% }%>
		  --%>
		  <%--暂时屏蔽删除功能,在发文模块中--%>
		  <a><img src="<c:url value="/images/common/icons/del_off.gif"/>" width="60" height="20"></a>
		  
		  </td>
        </tr>
        <tr>
          <td width="95%" height="20" colspan="2">&nbsp;</td>
        </tr>
</c:when>
<c:when test="${ptype=='view'&&path!='editDetailJbxx.html'&&path!='editDetailDqtz.html'}">
		 <tr>
          <td height="20" colspan="2">&nbsp; </td>
        </tr>
        <tr>
          <td width="5%" height="20">&nbsp;</td>
          <td height="20">

          <a href=<c:out value="${path}" />?method=edit onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3','','<c:url value="/images/common/icons/new_over.gif"/>',1)">
		  <img src="<c:url value="/images/common/icons/new_on.gif"/>" name="Image3" width="60" height="20"></a>
		  
		  <a href=<c:out value="${path}" />?method=gotoFind onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image51','','<c:url value="/images/common/icons/search_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/search_on.gif"/>" name="Image51" width="60" height="20" border="0" id="Image51"></a>
          
          <%  if(request.getParameter("copy")!=null&&request.getParameter("copy").equals("1")|| request.getParameter("jiaoyubu")!=null&&request.getParameter("jiaoyubu").equals("true")){ %>	
			<img src="<c:url value="/images/common/icons/edit_off.gif"/>" name="Image7" width="60" height="20">

		  <%}else if ((request.getAttribute("from")!=null)&&( !((String)request.getAttribute("from")).equals("save_many") )   
		  	||(request.getParameter("from") != null &&((request.getParameter("from").equals("view") ||    request.getParameter("from").equals("save"))))   ){ %>
			<a href="javascript:edit()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image7','','<c:url value="/images/common/icons/edit_over.gif"/>',1)">
			<img src="<c:url value="/images/common/icons/edit_on.gif"/>" name="Image7" width="60" height="20" border="0" id="Image7"></a>
		 
		  <% }else{ %>
			<a href="javascript:pl_edit()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image7','','<c:url value="/images/common/icons/edit_over.gif"/>',1)">
			<img src="<c:url value="/images/common/icons/edit_on.gif"/>" name="Image7" width="60" height="20" border="0" id="Image7"></a>
		  <% } %>

          <%--
          <%if(request.getParameter("jiaoyubu")!=null&&request.getParameter("jiaoyubu").equals("true")){ %>
			  <a><img src="<c:url value="/images/common/icons/del_off.gif"/>" width="60" height="20"></a>	
		   <% }else if( (request.getParameter("from") != null && request.getParameter("from").equals("save_many") )
				|| ( request.getAttribute("from")!=null && request.getAttribute("from").equals("save_many") )) { %>
			<a href="javascript:pl_del_one()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','<c:url value="/images/common/icons/del_over.gif"/>',1)">
		 <img src="<c:url value="/images/common/icons/del_on.gif"/>" name="Image8" width="60" height="20"></a>	
		  <%}else if( (request.getParameter("from") != null && (request.getParameter("from").equals("edit") || request.getParameter("from").equals("view") || request.getParameter("from").equals("save")) )
		   ||  ( request.getAttribute("from")!=null && ( request.getAttribute("from").equals("view") || request.getAttribute("from").equals("save") ) ) 
		  ){ %>
			<a href="javascript:del()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','<c:url value="/images/common/icons/del_over.gif"/>',1)">
		   <img src="<c:url value="/images/common/icons/del_on.gif"/>" name="Image8" width="60" height="20"></a>
		  <% } else { %>
			<a href="javascript:pl_del()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','<c:url value="/images/common/icons/del_over.gif"/>',1)">
		 <img src="<c:url value="/images/common/icons/del_on.gif"/>" name="Image8" width="60" height="20"></a>	
		  <% } %>
		  --%>
		  <%--暂时屏蔽删除功能,在发文模块中--%>
		  <a><img src="<c:url value="/images/common/icons/del_off.gif"/>" width="60" height="20"></a>
		
		  </td>
        </tr>
        <tr>
          <td width="95%" height="20" colspan="2">&nbsp;</td>
        </tr>
</c:when>
<c:when test="${ptype=='view'&&(path=='editDetailJbxx.html'||path=='editDetailDqtz.html')}">
		 <tr>
          <td height="20" colspan="2">&nbsp; </td>
        </tr>
        <tr>
          <td width="5%" height="20">&nbsp;</td>
          <td height="20">

          <img src="<c:url value="/images/common/icons/new_off.gif"/>" name="Image3" width="60" height="20">
		  
		  <a href=<c:out value="${path}" />?method=gotoFind onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image51','','<c:url value="/images/common/icons/search_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/search_on.gif"/>" name="Image51" width="60" height="20" border="0" id="Image51"></a>
          
          <img src="<c:url value="/images/common/icons/edit_off.gif"/>" name="Image7" width="60" height="20">

		  <a><img src="<c:url value="/images/common/icons/del_off.gif"/>" width="60" height="20"></a>
		
		  </td>
        </tr>
        <tr>
          <td width="95%" height="20" colspan="2">&nbsp;</td>
        </tr>
</c:when>
<c:when test="${ptype == 'list'&&path!='editDetailJbxx.html'&&path!='editDetailDqtz.html'}">

        <tr>
          <td height="20" colspan="2">&nbsp; </td>
        </tr>
        <tr>
          <td width="5%" height="20">&nbsp;</td>
          <td height="20">
		  
		  <a href=<c:out value="${path}" />?method=edit onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3','','<c:url value="/images/common/icons/new_over.gif"/>',1)">
		  <img src="<c:url value="/images/common/icons/new_on.gif"/>" name="Image3" width="60" height="20"></a>

		  <a href=<c:out value="${path}" />?method=gotoFind onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image51','','<c:url value="/images/common/icons/search_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/search_on.gif"/>" name="Image51" width="60" height="20" border="0" id="Image51"></a>
		  
		  <a href="javascript:pl_edit()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image7','','<c:url value="/images/common/icons/edit_over.gif"/>',1)">
		  <img src="<c:url value="/images/common/icons/edit_on.gif"/>" name="Image7" width="60" height="20"></a>
			
        
          <a href="javascript:pl_del()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','<c:url value="/images/common/icons/del_over.gif"/>',1)">
		  <img src="<c:url value="/images/common/icons/del_on.gif"/>" name="Image8" width="60" height="20"></a>
		  </td>
	
		  <%--暂时屏蔽删除功能,在发文模块中--%>
		   <%-- <a><img src="<c:url value="/images/common/icons/del_off.gif"/>" width="60" height="20"></a>
	        --%>
        </tr>
        <tr>
          <td width="95%" height="20" colspan="2">&nbsp;</td>
        </tr>

</c:when>
<c:when test="${ptype == 'list'&&(path=='editDetailJbxx.html'||path=='editDetailDqtz.html')}">

        <tr>
          <td height="20" colspan="2">&nbsp; </td>
        </tr>
        <tr>
          <td width="5%" height="20">&nbsp;</td>
          <td height="20">
		  
		  <img src="<c:url value="/images/common/icons/new_off.gif"/>" name="Image3" width="60" height="20">

		  <a href=<c:out value="${path}" />?method=gotoFind onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image51','','<c:url value="/images/common/icons/search_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/search_on.gif"/>" name="Image51" width="60" height="20" border="0" id="Image51"></a>
		  
		  <a href="javascript:pl_view()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image7','','<c:url value="/images/common/icons/examine_over.gif"/>',1)">
		  <img src="<c:url value="/images/common/icons/examine_on.gif"/>" name="Image7" width="60" height="20"></a>
			
      
          <a href="javascript:pl_del()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','<c:url value="/images/common/icons/del_over.gif"/>',1)">
		  <img src="<c:url value="/images/common/icons/del_on.gif"/>" name="Image8" width="60" height="20"></a>
		  </td>
	
		  <%--暂时屏蔽删除功能,在发文模块中--%>
		    <%-- <a><img src="<c:url value="/images/common/icons/del_off.gif"/>" width="60" height="20"></a>
	     --%>
        </tr>
        <tr>
          <td width="95%" height="20" colspan="2">&nbsp;</td>
        </tr>

</c:when>
</c:choose>
        
</table>

<script>

function setIds(){
	//alert("c");
	var a=new Array();
	var ids=new Array();
	var elem;
	//获得表单元素
	a=document.forms[0].elements;
	<%	
	if(session.getAttribute("sel_ids")!=null){
		String[] ids=(String[])session.getAttribute("sel_ids");
		for(int i=0;i<ids.length;i++){
	%>
		ids[<%=i%>]=<%=ids[i]%>;

	<%
		}
	}
		
	%>

	for(var i=0;i<a.length;i++){
		elem=a[i];
		for(var j=0;j<ids.length;j++){
			if(elem.type=='checkbox'&&elem.value==ids[j]){
				elem.checked=true;
			}
		}
	}

}
</script>
