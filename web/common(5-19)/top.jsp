<%@ include file="/common/taglibs.jsp"%>
type= <%=request.getParameter("from")%>
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
		  <% if(request.getParameter("from") != null &&(request.getParameter("from").equals("edit_many") || request.getParameter("from").equals("del_many_one") )){ %>
			<a href=<c:out value="${path}" />?method=edit onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3','','<c:url value="/images/common/icons/new_over.gif"/>',1)">
			<img src="<c:url value="/images/common/icons/new_on.gif"/>" name="Image3" width="60" height="20"></a>
		  <%}else{%>
		    <img src="<c:url value="/images/common/icons/new_off.gif"/>" name="Image3" width="60" height="20">
		  <%}%>	
		  

		  <a href=<c:out value="${path}" />?method=gotoFind onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image51','','<c:url value="/images/common/icons/search_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/search_on.gif"/>" name="Image51" width="60" height="20" border="0" id="Image51"></a>
		  
		  <img src="<c:url value="/images/common/icons/edit_off.gif"/>" width="60" height="20">
			
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
		  
		  </td>
        </tr>
        <tr>
          <td width="95%" height="20" colspan="2">&nbsp;</td>
        </tr>
</c:when>
<c:when test="${ptype=='view'}">
		 <tr>
          <td height="20" colspan="2">&nbsp; </td>
        </tr>
        <tr>
          <td width="5%" height="20">&nbsp;</td>
          <td height="20">

		  <a href=<c:out value="${path}" />?method=edit onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3','','<c:url value="/images/common/icons/new_over.gif"/>',1)">
		  <img src="<c:url value="/images/common/icons/new_on.gif"/>" name="Image3" width="60" height="20"></a>

		  
		  <a href=<c:out value="${path}" />?method=gotoFind onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image51','','<c:url value="/images/common/icons/search_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/search_on.gif"/>" name="Image51" width="60" height="20" border="0" id="Image51"></a>

		  <%if (request.getParameter("from") != null &&((request.getParameter("from").equals("view") ||    request.getParameter("from").equals("save")))){ %>
			<a href="javascript:edit()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image7','','<c:url value="/images/common/icons/edit_over.gif"/>',1)">
		  <% }else{ %>
			<a href="javascript:pl_edit()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image7','','<c:url value="/images/common/icons/edit_over.gif"/>',1)">
		  <% } %>

		  <img src="<c:url value="/images/common/icons/edit_on.gif"/>" name="Image7" width="60" height="20" border="0" id="Image7"></a>

		  <%if (request.getParameter("from") != null && (request.getParameter("from").equals("edit") || request.getParameter("from").equals("view") || request.getParameter("from").equals("save"))){ %>
			<a href="javascript:del()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','<c:url value="/images/common/icons/del_over.gif"/>',1)">
		  <% }else if (request.getParameter("from") != null && request.getParameter("from").equals("save_many")) { %>
			<a href="javascript:pl_del_one()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','<c:url value="/images/common/icons/del_over.gif"/>',1)">
		  <% } else { %>
			<a href="javascript:pl_del()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','<c:url value="/images/common/icons/del_over.gif"/>',1)">
		  <% } %>
		  <img src="<c:url value="/images/common/icons/del_on.gif"/>" name="Image8" width="60" height="20"></a>	
		  </td>
        </tr>
        <tr>
          <td width="95%" height="20" colspan="2">&nbsp;</td>
        </tr>
</c:when>
<c:when test="${ptype == 'list'}">

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

		  <a href="javascript:modify()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image9','','<c:url value="/images/common/icons/del_over.gif"/>',1)">
		  <img src="<c:url value="/images/common/icons/del_on.gif"/>" name="Image9" width="60" height="20"></a></td>
        </tr>
        <tr>
          <td width="95%" height="20" colspan="2">&nbsp;</td>
        </tr>

</c:when>
<c:when test="${ptype=='sts'}">

 <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="20" colspan="2">&nbsp; </td>
        </tr>
        <tr>
          <td width="5%" height="20">&nbsp;</td>
          <td height="20">
		  <a href=<c:out value="${path}"/>?method=edit onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3','','<c:url value="/images/common/icons/new_over.gif"/>',1)">
		  <img src="<c:url value="/images/common/icons/new_on.gif"/>" name="Image3" width="60" height="20"></a>

		  <a href=<c:out value="${path}"/>?method=gotoFind onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image51','','<c:url value="/images/common/icons/search_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/search_on.gif"/>" name="Image51" width="60" height="20" border="0" id="Image51"></a>
		  
		 <img src="<c:url value="/images/common/icons/edit_off.gif"/>" width="60" height="20">
		  
		  <img src="<c:url value="/images/common/icons/del_off.gif"/>" width="60" height="20"></td>
        </tr>
        <tr>
          <td width="95%" height="20" colspan="2">&nbsp;</td>
        </tr>
</c:when>


</c:choose>
        
</table>



