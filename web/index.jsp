<%@ include file="/common/taglibs.jsp"%>

<%-- 
You can use this logic if you're running your app on 80 & 443,
but IE seems to have issues when running on non-standard ports
and spits up a Server Not Found error 
--%>

<c:if test="${applicationScope.secureLogin == 'true'}">
    <buptlab:secure/>
</c:if>
<%--
<c:redirect url="/mainMenu.html"/>
--%>


<c:redirect url="/frameset.jsp"/> 
