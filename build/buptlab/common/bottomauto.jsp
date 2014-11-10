<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>

<%@ include file="/common/taglibs.jsp"%>
<% request.setAttribute("configName", "microsoftLook");%>


<script for="document" event="onkeydown">
<c:choose>




<c:when test="${ptype=='find'}">
		
			<%if (request.getParameter("enter")==null&&request.getParameter("zf")==null){%> 
				
              if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='')
				event.keyCode=9;
   
	         
		<%  }else{%>
			    <c:choose>
			    <c:when test="${path=='editZc.html'}">
	                         if(event.keyCode==13&&event.srcElement.href=='javascript:find()')
				   {
							}
						  
						  
				else	 if(event.keyCode==13&& !(event.srcElement.type=='text'&& event.srcElement.name=='gjmc')
						  &&!(event.srcElement.type=='text'&& event.srcElement.name=='xslbmc')
						   &&!(event.srcElement.type=='text'&& event.srcElement.name=='zymlmc')
						    &&!(event.srcElement.type=='text'&& event.srcElement.name=='yxmc')
							 &&!(event.srcElement.type=='text'&& event.srcElement.name=='bjmc')
							  &&!(event.srcElement.type=='text'&& event.srcElement.name=='fylbmc')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='jxjmc')
			                     &&!(event.srcElement.type=='text'&& event.srcElement.name=='tfsjmc')
			                        &&!(event.srcElement.type=='text'&& event.srcElement.name=='xkmc')
			                          &&!(event.srcElement.type=='text'&& event.srcElement.name=='xk')
			    )
					     
							 find();
		 

				</c:when>
				<c:when test="${path=='editFb.html'}">
                     if(event.keyCode==13&&event.srcElement.href=='javascript:find()')
				   {
							}
						  
						  
				else	 if(event.keyCode==13&& !(event.srcElement.type=='text'&& event.srcElement.name=='gjmc')
						  &&!(event.srcElement.type=='text'&& event.srcElement.name=='xslbmc')
						   &&!(event.srcElement.type=='text'&& event.srcElement.name=='zymlmc')
						    &&!(event.srcElement.type=='text'&& event.srcElement.name=='yxmc')
							 &&!(event.srcElement.type=='text'&& event.srcElement.name=='bjmc')
							  &&!(event.srcElement.type=='text'&& event.srcElement.name=='fylbmc')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='jxjmc')
					              &&!(event.srcElement.type=='text'&& event.srcElement.name=='fb_bjmc')
							              &&!(event.srcElement.type=='text'&& event.srcElement.name=='xkmc')   &&!(event.srcElement.type=='text'&& event.srcElement.name=='xk')
					 )
			              
							 find();
					
                </c:when>
				<c:when test="${path=='editJfwb.html'}">
				    if(event.keyCode==13&&event.srcElement.href=='javascript:find()')
				   {
							}
						  
						  
				else	 if(event.keyCode==13&& !(event.srcElement.type=='text'&& event.srcElement.name=='gjmc')
						  &&!(event.srcElement.type=='text'&& event.srcElement.name=='xslbmc')
						   &&!(event.srcElement.type=='text'&& event.srcElement.name=='zymlmc')
						    &&!(event.srcElement.type=='text'&& event.srcElement.name=='yxmc')
							 &&!(event.srcElement.type=='text'&& event.srcElement.name=='bjmc')
							  &&!(event.srcElement.type=='text'&& event.srcElement.name=='fylbmc')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='jxjmc')
					             &&!(event.srcElement.type=='text'&& event.srcElement.name=='tfsjmc')
							        &&!(event.srcElement.type=='text'&& event.srcElement.name=='xkmc')
				)
					      
							 find();
                </c:when>
                <c:otherwise>
				
					         if(event.keyCode==13&&event.srcElement.href=='javascript:find()')
				   {
						}
						  
						  
				else	if(event.keyCode==13&& !(event.srcElement.type=='text'&& event.srcElement.name=='gjmc')
						  &&!(event.srcElement.type=='text'&& event.srcElement.name=='xslbmc')
						   &&!(event.srcElement.type=='text'&& event.srcElement.name=='zymlmc')
						    &&!(event.srcElement.type=='text'&& event.srcElement.name=='yxmc')
							 &&!(event.srcElement.type=='text'&& event.srcElement.name=='bjmc')
							  &&!(event.srcElement.type=='text'&& event.srcElement.name=='fylbmc')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='jxjmc')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='tfsjmc')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='lclb')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='jthdmc') 
							        &&!(event.srcElement.type=='text'&& event.srcElement.name=='rchdmc') 
							  	     &&!(event.srcElement.type=='text'
									 &&event.srcElement.name=='xshdmc') 
							  &&!(event.srcElement.type=='text'&& event.srcElement.name=='xkmc')
			                          &&!(event.srcElement.type=='text'&& event.srcElement.name=='xk') 
					  )
			              
							 find();
					
				</c:otherwise>
				</c:choose>
			 <%}%>  
	
		
</c:when>



<c:when test="${ptype=='xkEdit1'}">
		  if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='')
				event.keyCode=9;
   
				
		
</c:when>
<c:when test="${ptype=='xkEdit2'}">
			  if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='')
			event.keyCode=9;
   
				
		
</c:when>
<c:when test="${ptype=='fyEdit1'}">
			  if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='')
			event.keyCode=9;
   
				 
</c:when>

<c:when test="${ptype=='fyEdit2'}">
			  if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='')
			event.keyCode=9;
   
				 
		
</c:when>

<c:when test="${ptype=='fyEdit3'}">
			  if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='')
			event.keyCode=9;
   
				 
		
</c:when>

<c:when test="${ptype=='fyEdit21'}">
		  if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='')
		event.keyCode=9;
   
				 
</c:when>

<c:when test="${ptype=='xkEdit4'}">
		  if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='')
			event.keyCode=9;
   
				  
		
</c:when>
<c:when test="${ptype=='xkEdit3'}">
			  if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='')
			event.keyCode=9;
   
				
		
</c:when>

<c:when test="${ptype=='xkFind'}">
	
		    if(event.keyCode==13&&event.srcElement.href=='javascript:find()')
				   {
							}
						  
						  
				else	 if(event.keyCode==13&& !(event.srcElement.type=='text'&& event.srcElement.name=='gjmc')
						  &&!(event.srcElement.type=='text'&& event.srcElement.name=='xslbmc')
						   &&!(event.srcElement.type=='text'&& event.srcElement.name=='zymlmc')
						    &&!(event.srcElement.type=='text'&& event.srcElement.name=='yxmc')
							 &&!(event.srcElement.type=='text'&& event.srcElement.name=='bjmc')
							  &&!(event.srcElement.type=='text'&& event.srcElement.name=='fylbmc')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='jxjmc')
								 &&!(event.srcElement.type=='text'&& event.srcElement.name=='tfsjmc')
								   &&!(event.srcElement.type=='text'&& event.srcElement.name=='lclb')
					                &&!(event.srcElement.type=='text'&& event.srcElement.name=='xkmc')
					)
				         
							 find();
			 
</c:when>

<c:when test="${ptype=='fyFind'}">

		
					       if(event.keyCode==13&&event.srcElement.href=='javascript:find()')
				   {
							}
						  
						  
				else	 if(event.keyCode==13&& !(event.srcElement.type=='text'&& event.srcElement.name=='gjmc')
						  &&!(event.srcElement.type=='text'&& event.srcElement.name=='xslbmc')
						   &&!(event.srcElement.type=='text'&& event.srcElement.name=='zymlmc')
						    &&!(event.srcElement.type=='text'&& event.srcElement.name=='yxmc')
							 &&!(event.srcElement.type=='text'&& event.srcElement.name=='bjmc')
							  &&!(event.srcElement.type=='text'&& event.srcElement.name=='fylbmc')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='jxjmc')
								  &&!(event.srcElement.type=='text'&& event.srcElement.name=='tfsjmc')
								    &&!(event.srcElement.type=='text'&& event.srcElement.name=='lclb')
					                  &&!(event.srcElement.type=='text'&& event.srcElement.name=='xkmc')
					 )
			
							 find();
			
		
</c:when>

<c:when test="${ptype=='form'}">
		  if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='')
				event.keyCode=9;
   
				
		
</c:when>
<c:when test="${ptype=='handlist'}">
 if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='')
				event.keyCode=9;

</c:when>
</c:choose>
</script>
<c:choose>
<c:when test="${ptype=='listsjcx'}">
	<div align="center">
		 <a href=<c:out value="${path}" />?method=gotoFind onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image171','','<c:url value="/images/common/icons/back_over.gif"/>',1)">
		 <img src="<c:url value="/images/common/icons/back_on.gif"/>" name="Image171" width="60" height="20" border="0" id="Image171"></a>
	</div>
</c:when>
</c:choose>
<script>
function quit(){
form = document.forms[0];
form.action =getMethod('KillSession.html','kill');
form.submit();
 window.close();


}

</script>

