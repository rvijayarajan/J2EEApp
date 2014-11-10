   <%@ page language="java" errorPage="/error.jsp" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
	<body>

 <table width="100%" cellpadding="0" cellspacing="0" border="0" >
	  <tr>
	     <td align="bottom" background="images/TOP2.jpg">     
	       <table width="100%">
	         <tr>	     
	           <td valign="bottom">
	             <table width="45%" height="70" cellpadding="0" cellspacing="0" border="0">
	               <tr>	              
	                 <td align="center" valign="bottom" height="70%" colspan="2">
	                   <span style="font-family:Arial Black;font-weight:bold;font-size:10pt">

					<!-- 	 <a href="
						  <c:url value='/editJcdw.html?method=gotoFind'/> 
						 " target="mainFrame"/>
						 -->
						   <c:out value="${dwmc}"/>
	                
	                   </span>
	                 </td>
	               </tr>
	               <tr>
				   <td width="10"></td>
	                <td align="left" valign="center" height="30%">
	                   <span style="font-size:10pt">
	                     <fmt:message key="label.username"/>:<c:out value="${username}"/>
	                   </span>
	                 </td> 
	               <tr>	          
	             </table>	        
	           </td>      
	      
	           <td valign="bottom">
	              <table width="30%" height="70" cellpadding="0" cellspacing="0" border="0" align="right">	        
	               <tr valign="top">
	         
		         <td width="50%" align="right" valign="center" height="25%">
				  	 <a href="
						  <c:url value='/editSysuser.html?method=gotoModpass'/>"
					target="mainFrame"	   />
						
			    <span style="font-family:Arial Black;font-weight:bold;font-size:10pt;color:yellow">
	                    
	                   <fmt:message key="mainpage.modifypassword"/>
	                   </span>
	             	 </td>   
	                 <td width="50%" align="right" valign="center" height="25%">
             <html-el:link page="/download/download.html?method=saveBB&EXCELNAME=czsc.rar&SAVEFN=czsc.rar&PATH=download">
				 <span style="font-family:Arial Black;font-weight:bold;font-size:10pt;color:yellow">
	                    
	                   <fmt:message key="mainpage.document"/>
	                   </span>
	              </html-el:link> 
		         </td>	
		       </tr>		   
		        <tr>
		         <td width = "50%" align="right" valign="center" height="25%">
		         <a href="
						  javascript:quit()" 
					   />	  
			    <span style="font-family:Arial Black;font-weight:bold;font-size:10pt;color:yellow">
	                    
	                   <fmt:message key="mainpage.killsession"/>
	                   </span>
	                </td>   
	              <td width = "50%" align="right" valign="center" height="25%">
		        
			   <html:link forward="logout" target="_top">   
			    <span style="font-family:Arial Black;font-weight:bold;font-size:10pt;color:yellow">
	                    
	                   <fmt:message key="mainpage.logout"/>
	                   </span>
	                 </html:link>
		         </td>
		       </tr>		   
			   <tr>
		                <td colspan =2 align="right" valign="center" height="50%">
            	     <span style="font-family:Arial Black;font-weight:bold;font-size:10pt;color:red">
	                    
	                   <fmt:message key="mainpage.illuminate"/>
	                   </span>
	                   </td>
		       </tr>		   
		      
	             </table>	      
	           </td>	      
	         </tr>
	       </table>	        
	     </td>
	   </tr>
	</table>  
<form  method="post" action="/buptlab/editJbxx.html" id="jbxxForm">
</form>
</body>
<script>
function quit(){
form = document.forms[0];
form.action ='<c:url value="/"/>' +'KillSession.html' + "?method=" +'kill';


if(confirm("关闭此窗口，你确定吗？")){
      form.submit();
      window.parent.opener=null;
	  window.parent.close();
}

}

</script>