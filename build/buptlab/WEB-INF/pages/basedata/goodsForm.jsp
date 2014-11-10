<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
	
<%@ include file="/common/taglibs.jsp"%>
<%
	request.setAttribute("ptype","form");
	request.setAttribute("path","editgoods.html");
%>

<html>
<head>

<title><fmt:message key="goodsFrom.title"/></title>
</head>

<html:form action="editgoods.html" method="post" styleId="goodsForm"
    onsubmit="return validateGoodsForm(this)">
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

</table>
</html:form>


<html:javascript formName="goodsForm" cdata="false"
    dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" 
    src="<html:rewrite page="/scripts/validator.jsp"/>"></script>

<script>
function CheckInput(){
//return true;
return CheckAllLength();

}
</script>

<script type="text/javascript">
  function CheckAllLength(){
  
    if (CheckLength("goodname","<商品名称>","20")
  		&&CheckLength("rfidID","<RFID标签号>","16"))
      return true;
    else
      return false;
  }

</script>
