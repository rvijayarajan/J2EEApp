<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>


<%
	request.setAttribute("ptype","list");
	request.setAttribute("path","goods.html");

%>

<title><fmt:message key="goodsList.title"/></title>

<%--<meta name="decorator" content="second"/>--%>

<content tag="heading"><fmt:message key="goodsList.heading"/></content>
<html:form  action="editgoods.html?method=edit" method="post" styleId="privateForm">



<vlh:root id="goodsID" configName="microsoftLook" value="list_goods" url="goods.html?method=find&" includeParameters="*">
<vlh:paging showSummary="true"/>  
    <table width="1000" class="classicLook" cellspacing="0" cellpadding="0">
	<c:if test="${list_goods.valueListInfo.totalNumberOfEntries != 0}">

	
      <vlh:row bean="Goods">
      
     <vlh:attribute name="id"><%=("player-"+GoodsRowNumber)%></vlh:attribute>
     
      <vlh:attribute name="align" value="center" />
      
     <vlh:checkbox name="ids" property="goodsid" />
     
  
      
     <vlh:column titleKey="goodsForm.goodname"  property="goodname"  sortable="desc"/>
     
     <vlh:column titleKey="goodsForm.rfidID"  property="rfidid"  sortable="desc"/>
     


    <vlh:column titleKey="goodsForm.kind"  property="kind"  sortable="desc"/>

    <vlh:column titleKey="goodsForm.spec"  property="spec"  sortable="desc"/>
    
    <vlh:column titleKey="goodsForm.price"  property="price"  sortable="desc"/>
    
    <vlh:column titleKey="goodsForm.weight"  property="weight"  sortable="desc"/>
 

 
	<vlh:controls title="修改" >
                <vlh:action url="editgoods.html?">
                  <vlh:addParam property="goodsid" temp="false"/>
                  <vlh:addParam name="method" value="edit" temp="false" />
				  <vlh:addParam name="from" value="edit" temp="false" />


				<html:img pageKey="update.image" />
                </vlh:action>
		</vlh:controls>

		<vlh:controls title="查看" >
                <vlh:action url="editgoods.html?">
                  <vlh:addParam property="goodsid" temp="false"/>
				  <vlh:addParam name="method" value="edit" temp="false" />
				  <vlh:addParam name="from" value="view" temp="false" />

				<html:img pageKey="view.image" />
                </vlh:action>
       </vlh:controls>
      </vlh:row>
	  </c:if>
    </table>
  </vlh:root>
  <c:if test="${list_goods.valueListInfo.totalNumberOfEntries != 0}">
<%@ include file="/common/exportexcel.jsp"%> 
  </c:if>
</html:form>
<script>

setIds();  //为选中的id显示勾选的图标

</script>
<!--   注释掉这段代码         
<script type="text/javascript">

    highlightTableRows("goodsList");

</script>
-->
<script>
function CheckInput(){
   //    alert("16");
 return true;
      
}
</script>
  
    
      