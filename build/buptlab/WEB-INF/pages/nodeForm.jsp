<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
	
<%@ include file="/common/taglibs.jsp"%>

<%
	request.setAttribute("ptype","form");
	request.setAttribute("path","editNode.html");
%>
<title><fmt:message key="nodeDetail.title"/></title>
<content tag="heading"><fmt:message key="nodeDetail.heading"/></content>

<html:form action="editNode" method="post" styleId="nodeForm"
    focus="bm" onsubmit="return validateNodeForm(this)">
<input type="hidden" name="from"value="<%=request.getParameter("from")%>" />
<table class="detail">

<html:hidden property="id"/>

    <tr>
        <th>
            <buptlab:label key="nodeForm.nodecode"/>
        </th>
        <td>
            <html:text property="nodecode" styleId="nodecode"/>
            <html:errors property="nodecode"/>
        </td>
    </tr>

    <tr>
        <th>
            <buptlab:label key="nodeForm.parent_id"/>
        </th>
        <td>
            <html:text property="parent_id" styleId="parent_id"/>
            <html:errors property="parent_id"/>
        </td>
    </tr>

    <tr>
        <th>
            <buptlab:label key="nodeForm.func_node_name"/>
        </th>
        <td>
            <html:text property="func_node_name" styleId="func_node_name"/>
            <html:errors property="func_node_name"/>
        </td>
    </tr>

    <tr>
        <th>
            <buptlab:label key="nodeForm.czmc"/>
        </th>
        <td>
            <html:text property="czmc" styleId="czmc"/>
            <html:errors property="czmc"/>
        </td>
    </tr>

    <tr>
        <th>
            <buptlab:label key="nodeForm.location"/>
        </th>
        <td>
            <html:text property="location" styleId="location"/>
            <html:errors property="location"/>
        </td>
    </tr>

    

</table>
</html:form>

<html:javascript formName="nodeForm" cdata="false"
    dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" 
    src="<html:rewrite page="/scripts/validator.jsp"/>"></script>

<script>
function CheckInput(){
if(validateNodeForm(form)&&CheckAllLength()&&CheckNumber('nodecode',"<功能点编码>"))
return true;
else
return false;
}
</script>

<script type="text/javascript">
  function CheckAllLength(){
    if (CheckLength("nodecode","<功能点编码>","10")
  		&&CheckLength("func_node_name","<功能点名称>","50")
  		&&CheckLength("czmc","<操作名称>","50")
  		&&CheckLength("location","<链接>","255")
  		&&CheckLength("cdbz","<菜单标记>","1"))
      return true;
    else
      return false;
  }
</script>
