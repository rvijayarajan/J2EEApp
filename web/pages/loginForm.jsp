<%@ page language="java" errorPage="/error.jsp" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>

<%@ include file="/common/taglibs.jsp"%>

<div id="loginTable">
<%-- If you don't want to encrypt passwords programmatically, or you don't
     care about using SSL for the login, you can change this form's action
     to "j_security_check" --%>
<form method="post" id="loginForm" action="<c:url value="/authorize"/>" 
    onsubmit="saveUsername(this);return validateForm(this)">

<table align="center" class="login" >
<!--table width="100%"--> 
<tr> 
<td>
	<table align="center" width="65%">
    <tr  height="230">

        <td colspan="2">
            <c:if test="${param.error != null}">
            <div class="error" 
                style="margin-right: 0; margin-bottom: 3px; margin-top: 3px">
                    <img src="<c:url value="/images/iconWarning.gif"/>"
                        alt="<fmt:message key="icon.warning"/>" class="icon" />
                    <fmt:message key="errors.password.mismatch"/>
                </div>
            </c:if>
        </td>
    </tr>
    
    <br>
    <tr>
    
    <th rowspan="2"width="70%">
    <img src="images/login_3.jpg">
    </th>
         <td> 
            <input type="text" name="j_username" id="j_username" size="15" tabindex="1" />
        </td>
     
   

   

    </tr>
     <tr>
          <td>
            <input type="password" name="j_password" id="j_password" size="15" tabindex="2" />
          </td> 
           <td>
               <input type="image" src="images/login_2.jpg" name="login" id="login" tabindex="4" >
               <input type="hidden" name="j_uri" value="" />
           </td>    
    </tr>  
  </table> 
  
  <br><br><br><br>
  <br><br><br>
<table>
<tr  valign="bottom">
   <th width="40%">
   </th>     
   <td  ><img src="images/copyright.jpg">
</td> 
   </tr>
 </table>
  
  
   </td>
</tr>

</table>

</form>
</div>

<%@ include file="/scripts/login.js"%>
