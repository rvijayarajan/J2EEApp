<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>

<%@ include file="/common/taglibs.jsp"%>
<% request.setAttribute("configName", "microsoftLook");%>


<script for="document" event="onkeydown">
<c:choose>




<c:when test="${ptype=='find'}">
		
		
			    <c:choose>
			    <c:when test="${path=='editZc.html'}">
	                     if(event.keyCode==13&&event.srcElement.href=='javascript:find()')
				   {}
						  
						  
				else	 if(event.keyCode==13&& !(event.srcElement.type=='text'&& event.srcElement.name=='gjmc')
						  &&!(event.srcElement.type=='text'&& event.srcElement.name=='xslbmc')
						   &&!(event.srcElement.type=='text'&& event.srcElement.name=='zymlmc')
						    &&!(event.srcElement.type=='text'&& event.srcElement.name=='yxmc')
							 &&!(event.srcElement.type=='text'&& event.srcElement.name=='bjmc')
							  &&!(event.srcElement.type=='text'&& event.srcElement.name=='fylbmc')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='jxjmc')
								  &&!(event.srcElement.type=='text'&& event.srcElement.name=='ccmc')
		                            &&!(event.srcElement.type=='text'&& event.srcElement.name=='xkmc')
		                             &&!(event.srcElement.type=='text'&& event.srcElement.name=='xk')
		                      )    
		          
							
							 find();
				
				</c:when>
				<c:when test="${path=='editFb.html'}">
                          if(event.keyCode==13&&event.srcElement.href=='javascript:find()')
				   {}
						  
						  
				else	if(event.keyCode==13&& !(event.srcElement.type=='text'&& event.srcElement.name=='gjmc')
						  &&!(event.srcElement.type=='text'&& event.srcElement.name=='xslbmc')
						   &&!(event.srcElement.type=='text'&& event.srcElement.name=='zymlmc')
						    &&!(event.srcElement.type=='text'&& event.srcElement.name=='yxmc')
							 &&!(event.srcElement.type=='text'&& event.srcElement.name=='bjmc')
							  &&!(event.srcElement.type=='text'&& event.srcElement.name=='fylbmc')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='jxjmc')
					               &&!(event.srcElement.type=='text'&& event.srcElement.name=='ccmc')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='xkmc')
		                                &&!(event.srcElement.type=='text'&& event.srcElement.name=='xk')
		                           )    
		        
							
							 find();
				 
                </c:when>
				<c:when test="${path=='editJfwb.html'}">
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
								  &&!(event.srcElement.type=='text'&& event.srcElement.name=='ccmc')
			                 &&!(event.srcElement.type=='text'&& event.srcElement.name=='xkmc')
		                            )    
		         
							 find();
					
                </c:when>
					<c:when test="${path=='editSysuser.html'}">
					       if(event.keyCode==13&&event.srcElement.href=='javascript:findSysuser()')
				   {
							}
						  
						  
				else	if(event.keyCode==13&& !(event.srcElement.type=='text'&& event.srcElement.name=='gjmc')
						  &&!(event.srcElement.type=='text'&& event.srcElement.name=='xslbmc')
						   &&!(event.srcElement.type=='text'&& event.srcElement.name=='zymlmc')
						    &&!(event.srcElement.type=='text'&& event.srcElement.name=='yxmc')
							 &&!(event.srcElement.type=='text'&& event.srcElement.name=='bjmc')
							  &&!(event.srcElement.type=='text'&& event.srcElement.name=='fylbmc')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='jxjmc')
								  &&!(event.srcElement.type=='text'&& event.srcElement.name=='ccmc')
								  &&!(event.srcElement.type=='text'&& event.srcElement.name=='xkmc')
		                            )    
		       
							 findSysuser();
			
                </c:when>
                <c:otherwise>
             
			         <%if (!(request.getParameter("fn")!=null&&request.getParameter("fn").equals("fpxh"))){%>
				
                  if(event.keyCode==13&&event.srcElement.href=='javascript:find()')
				   {}
						  
						  
				else	 if(event.keyCode==13&& !(event.srcElement.type=='text'&& event.srcElement.name=='gjmc')
						  &&!(event.srcElement.type=='text'&& event.srcElement.name=='xslbmc')
						   &&!(event.srcElement.type=='text'&& event.srcElement.name=='zymlmc')
						    &&!(event.srcElement.type=='text'&& event.srcElement.name=='yxmc')
							 &&!(event.srcElement.type=='text'&& event.srcElement.name=='bjmc')
							  &&!(event.srcElement.type=='text'&& event.srcElement.name=='fylbmc')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='jxjmc')
					     &&!(event.srcElement.type=='text'&& event.srcElement.name=='ccmc')
					      &&!(event.srcElement.type=='text'&& event.srcElement.name=='gj')
					 &&!(event.srcElement.type=='text'&& event.srcElement.name=='xkmc')
		                               &&!(event.srcElement.type=='text'&& event.srcElement.name=='xk')
		                          )   
						 	   
				
							 find();
						 
							 
					
		<%  }else{%>	
              if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='')
				event.keyCode=9;
   
			 <%}%> 
			
					   
				</c:otherwise>
				</c:choose>
			 
	
		
</c:when>


<c:when test="${ptype=='form'}">
		  if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='')
				event.keyCode=9;
   
				
		
</c:when>
<c:when test="${pat=='editSysuser.html'}">
					             if(event.keyCode==13&&event.srcElement.href=='javascript:findSysuser()')
								 {}
						  
						  
				else		  if(event.keyCode==13&& !(event.srcElement.type=='text'&& event.srcElement.name=='gjmc')
						  &&!(event.srcElement.type=='text'&& event.srcElement.name=='xslbmc')
						   &&!(event.srcElement.type=='text'&& event.srcElement.name=='zymlmc')
						    &&!(event.srcElement.type=='text'&& event.srcElement.name=='yxmc')
							 &&!(event.srcElement.type=='text'&& event.srcElement.name=='bjmc')
							  &&!(event.srcElement.type=='text'&& event.srcElement.name=='fylbmc')
							    &&!(event.srcElement.type=='text'&& event.srcElement.name=='jxjmc')
					             &&!(event.srcElement.type=='text'&& event.srcElement.name=='ccmc')
								 &&!(event.srcElement.type=='text'&& event.srcElement.name=='xkmc')
		                            )
							 
			
	  {
									
						findSysuser();}
							
							 
			
</c:when>
<c:when test="${pa=='editSysuser.html'}">
					     if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='')
				event.keyCode=9;
</c:when>
</c:choose>
</script>
<script>
function quit(){
form = document.forms[0];
form.action =getMethod('KillSession.html','kill');
form.submit();
 window.close();


}

</script>
