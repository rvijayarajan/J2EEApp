<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
	
<%@ include file="/common/taglibs.jsp"%>

<%
	request.setAttribute("ptype","view");
	request.setAttribute("path","editgoods.html");
    request.setAttribute("ptype","list");
%>

<title><fmt:message key="goodsFrom.title"/></title>
<html:form action="editgoods.html" method="post" styleId="goodsForm"
    onsubmit="return validateGoodsForm(this)">
    
<input type="hidden" name="type" value="<%=request.getParameter("type")%>" />
<table class="detail">
   
	 <html:hidden property="goodsID"/>
 <html:hidden property="goodsID"/>

    <tr>
        <th>
            <buptlab:label key="goodsForm.goodname"/>
        </th>
        <td>
            <html:text property="goodname" styleId="goodname" disabled="true"/>
            <html:errors property="goodname"/>
        </td>
    </tr>

    <tr>
        <th>
            <buptlab:label key="goodsForm.rfidID" />
        </th>
        <td>
            <html:text property="rfidID" styleId="rfidID" disabled="true"/>
            <html:errors property="rfidID"/>
        </td>
    </tr>

    <tr>
        <th>
            <buptlab:label key="goodsForm.kind"/>
        </th>
        <td>
            <html:text property="kind" styleId="kind" disabled="true"/>
            <html:errors property="kind"/>
        </td>
    </tr>

    <tr>
        <th>
            <buptlab:label key="goodsForm.spec"/>
        </th>
        <td>
            <html:text property="spec" styleId="spec" disabled="true"/>
            <html:errors property="spec"/>
        </td>
    </tr>

    <tr>
        <th>
            <buptlab:label key="goodsForm.price"/>
        </th>
        <td>
            <html:text property="price" styleId="price" disabled="true"/>
            <html:errors property="price"/>
        </td>
    </tr>

    <tr>
        <th>
            <buptlab:label key="goodsForm.weight"/>
        </th>
        <td>
            <html:text property="weight" styleId="weight" disabled="true"/>
            <html:errors property="weight"/>
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
	//return CheckAllLength();

}
</script>
<script type="text/javascript" 
    src="<html:rewrite page="/scripts/validator.jsp"/>"></script-->

<script>
function CheckInput(){
 return true;
}
</script>
