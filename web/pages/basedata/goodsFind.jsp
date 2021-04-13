<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
	
	<%@ include file="/common/taglibs.jsp"%>
	<%@ include file="/common/taglibs.jsp"%>

<%
	request.setAttribute("pat","editSysuser.html");
	request.setAttribute("ptype","find");
	request.setAttribute("path","editgoods.html");
%>

<title><fmt:message key="goodsFrom.title"/></title>
<content tag="heading"><fmt:message key="goodsForm.find.heading"/></content>

<html:form action="editgoods.html" method="post" styleId="goodsForm"
    onsubmit="return validateNodeForm(this)">
    
<table class="detail">

<html:hidden property="goodsID"/>

    <tr>
        <th>
            <buptlab:label key="goodsForm.goodname"/>
        </th>
        <td>
            <html:text property="goodname" styleId="goodname"/>
            <html:errors property="goodname"/>
        </td>
    </tr>

    <tr>
        <th>
            <buptlab:label key="goodsForm.rfidID"/>
        </th>
        <td>
            <html:text property="rfidID" styleId="rfidID"/>
            <html:errors property="rfidID"/>
        </td>
    </tr>

    <tr>
        <th>
            <buptlab:label key="goodsForm.kind"/>
        </th>
        <td>
            <html:text property="kind" styleId="kind"/>
            <html:errors property="kind"/>
        </td>
    </tr>

    <tr>
        <th>
            <buptlab:label key="goodsForm.spec"/>
        </th>
        <td>
            <html:text property="spec" styleId="spec"/>
            <html:errors property="spec"/>
        </td>
    </tr>

    <tr>
        <th>
            <buptlab:label key="goodsForm.price"/>
        </th>
        <td>
            <html:text property="price" styleId="price"/>
            <html:errors property="price"/>
        </td>
    </tr>

    <tr>
        <th>
            <buptlab:label key="goodsForm.weight"/>
        </th>
        <td>
            <html:text property="weight" styleId="weight"/>
            <html:errors property="weight"/>
        </td>
    </tr>

    <tr>
        <th>
            <fmt:message key="xgr"/>
        </th>
        <td>
            <html:text property="xgr" styleId="xgr"/>
            <html:errors property="xgr"/>
        </td>
    </tr>

   

    <tr>
        <th>
             <fmt:message key="xgsjks"/>
        </th>
        <td>
            <html:text property="xgsjks"  onkeypress="JavaScript:makeDate('xgsjks')"  onkeydown="JavaScript:stop()"/>
            <html:img pageKey="calendar.image" 
            onclick="javascript:popUpCalendar(this, document.forms[0].xgsjks)"/>
            <html:errors property="xgsjks"/>
        </td>
    </tr>

    <tr>
        <th>
             <fmt:message key="xgsjjs"/>
        </th>
        <td>
            <html:text property="xgsjjs"  onkeypress="JavaScript:makeDate('xgsjjs')"  onkeydown="JavaScript:stop()"/>
            <html:img pageKey="calendar.image" 
            onclick="javascript:popUpCalendar(this, document.forms[0].xgsjjs)"/>
            <html:errors property="xgsjjs"/>
        </td>
    </tr>
</table>
</html:form>

<html:javascript formName="goodsForm" cdata="false"
    dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" 
    src="<html:rewrite page="/scripts/validator.jsp"/>"></script>

<script>
function CheckInput(){
		return true;
/*
	if(<%--CheckNumber('bm',"<编码>")&&--%>

		&&CheckDate("xgsjks","修改时间起")
		&&CheckDate("xgsjjs","修改时间止")
		&&Compare('xgsjks','xgsjjs'))
		return true;
	else
		return false;
*/
}
</script>

