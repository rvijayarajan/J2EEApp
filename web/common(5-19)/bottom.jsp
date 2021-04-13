<%@ include file="/common/taglibs.jsp"%>
mk = <%=request.getParameter("module")%>
<c:choose>
<c:when test="${ptype=='find'}">
	<div align="center">
		<a href="javascript:find()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image32','','<c:url value="/images/common/icons/apply_over.gif"/>',1)">
		<img src="<c:url value="/images/common/icons/apply_on.gif"/>" name="Image32" width="60" height="20" border="0" />
		</a>

       <a href="javascript:clear()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image9','','<c:url value="/images/common/icons/rewrite_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/rewrite_on.gif"/>" name="Image9" width="60" height="20" border="0">
	   </a>
       <a href=<c:out value="${path}" />?method=search&module=<%=request.getParameter("module")%> onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image71','','<c:url value="/images/common/icons/back_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/back_on.gif"/>" name="Image71" width="60" height="20" border="0" id="Image71">
	   </a>
	</div>
</c:when>
<c:when test="${ptype=='form'}">
	<table align="center"> 
     <tr>
        <td></td>
        <td class="buttonBar">  
			<%if (request.getParameter("from") != null &&(request.getParameter("from").equals("list") || request.getParameter("from").equals("edit_many"))){ %>
				<html:img pageKey="save.image" onclick="pl_save();"/>
			<% } else {%>
				<html:img pageKey="save.image" onclick="save();"/>
			<% } %>
			<a href="javascript:clear()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image9','','<c:url value="/images/common/icons/rewrite_over.gif"/>',1)">
			<img src="<c:url value="/images/common/icons/rewrite_on.gif"/>" name="Image9" width="60" height="20" border="0">
			</a>
			<a href=<c:out value="${path}" />?method=search&module=<%=request.getParameter("module")%> onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image71','','<c:url value="/images/common/icons/back_over.gif"/>',1)">
			<img src="<c:url value="/images/common/icons/back_on.gif"/>" name="Image71" width="60" height="20" border="0" id="Image71">
			</a>
		</td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td>
            <c:choose>
				<c:when test="${img_state=='next'}">
					<html:img pageKey="prior_off.image" />
					<html:img pageKey="next.image" onclick="actionStr2('next', 'edit_many')"/>
				</c:when>
				<c:when test="${img_state=='prior'}">
					<html:img pageKey="prior.image" onclick="actionStr2('prior', 'edit_many')"/>
					<html:img pageKey="next_off.image" />
				</c:when>
				<c:when test="${img_state=='both'}">
					<html:img pageKey="prior.image" onclick="actionStr2('prior', 'edit_many')"/>
					<html:img pageKey="next.image" onclick="actionStr2('next', 'edit_many')"/>
				</c:when>
			</c:choose>
        </td>
    </tr>
</table>
</c:when>
<c:when test="${ptype=='view'}">
	<table align="center">
		<tr>
			<td></td>
			<td class="buttonBar">  
			<c:choose>
				<c:when test="${type=='list'}">	
					<a href=<c:out value="${path}" />?method=search&module=<%=request.getParameter("module")%> onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image71','','<c:url value="/images/common/icons/back_over.gif"/>',1)">
					<img src="<c:url value="/images/common/icons/back_on.gif"/>" name="Image71" width="60" height="20" border="0" id="Image71">
					</a>
				</c:when>
				<c:otherwise>
					<a href=<c:out value="${path}" />?method=search&module=<%=request.getParameter("module")%> onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image71','','<c:url value="/images/common/icons/back_over.gif"/>',1)">
					<img src="<c:url value="/images/common/icons/back_on.gif"/>" name="Image71" width="60" height="20" border="0" id="Image71">
				</c:otherwise>
			</c:choose>
            </td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
            <c:choose>
				<c:when test="${img_state=='next'}">
					<html:img pageKey="prior_off.image" />
					 <html:img pageKey="next.image" onclick="actionStr2('next', 'edit_many')"/>
				</c:when>
				<c:when test="${img_state=='prior'}">
					<html:img pageKey="prior.image" onclick="actionStr2('prior', 'edit_many')"/>
					<html:img pageKey="next_off.image" />
				</c:when>
				<c:when test="${img_state=='both'}">
					<html:img pageKey="prior.image" onclick="actionStr2('prior', 'edit_many')"/>
					<html:img pageKey="next.image" onclick="actionStr2('next', 'edit_many')"/>
				</c:when>
			</c:choose>
        </td>
    </tr>
</table>
</c:when>
<c:when test="${ptype=='list'}">
	<div align="center">
		 <!-- 注意,在返回时应当传递module参数 -->
		 <a href=<c:out value="${path}" />?method=search&module=<%=request.getParameter("module")%> onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image71','','<c:url value="/images/common/icons/back_over.gif"/>',1)">
		 <img src="<c:url value="/images/common/icons/back_on.gif"/>" name="Image71" width="60" height="20" border="0" id="Image71">
		 </a>

			<%if (request.getParameter("module") !=null && !request.getParameter("module").equals("null")&&   (request.getAttribute("next")== null)){ %>
				<a href="javascript:pl_next()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image71','','<c:url value="/images/common/icons/back_over.gif"/>',1)">
				<img src="<c:url value="/images/common/icons/next_on.gif"/>" name="Image71" width="60" height="20" border="0" id="Image71">
				</a>
			<% } %>
	</div>
</c:when>

</c:choose>


<script>
function pl_next(){
	var form=document.forms[0];
	var array=form.elements;
	var sum=0;
	for(var i=0;i<array.length;i++){
		var e=array[i];
		if(e.type=='checkbox'&&e.checked)
			sum++;
	}
	if(sum==0){
		alert("you must choose than nextstep.");
		return;
	}else{
		pl_next_act();
	}
	
}

function pl_next_act(){
	form=document.forms[0];

	form.action=getMethod('<%=request.getParameter("module")%>', 'edit_many') +  "&from=" + "edit_many";

	form.submit();

}


function save(){
	actionStr2('save', 'save');
}

function pl_save(){
	actionStr2('save', 'save_many');
}



function edit(){
    actionStr2('edit','edit');
}

function pl_edit(){
	var form=document.forms[0];
	var array=form.elements;
	var sum=0;
	for(var i=0;i<array.length;i++){
		var e=array[i];	
		if(e.type=='checkbox'&&e.checked)
			sum++;
	}
	if(sum==0){
		alert("you must choose then edit.");
		return;
	}else{
		pl_edit_act();
	}

}
function pl_edit_act(){	
  /**var form = document.forms[0];   
  var array = form.elements;
  var sum = 0;
  for(var i=0; i<array.length; i++){
    var elem = array[i];
    if(elem.type=='checkbox'&& elem.checked){
      sum++;
    }
  }
  if(sum==0){
    alert("you must choose then edit!");
    return;
  }
  **/
  actionStr2( 'edit_many', 'edit_many');
  //form.action=getMethod('editJbxx.html', 'edit_many&type=edit_many');
  //form.submit();

}

function clear(){
    document.forms[0].reset();
}

function pl_del(){
	var form=document.forms[0];
	var array=form.elements;
	var sum=0;
	for(var i=0;i<array.length;i++){
		var e=array[i];
		if(e.type=='checkbox'&&e.checked)
			sum++;
	}
	if(sum==0){
		alert("you must choose than delete.");
		return;
	}else{
		pl_del_act();
	}
}

function pl_del_act(){
	/*
	var form=document.forms[0];
	var array = form.elements;
	var sum=0;
	for(var i=0;i<array.length;i++){
		var element=array[i];
		if(element.type=='checkbox'&&element.checked){
			sum++;
		}
	}
	if(sum==0){
		alert("you must choose then delete!");
		return;
	}
	*/
	//if(confirm("are you sure you will delete these "+sum+" items?")){
		actionStr2( 'delete', 'del_many');
		//form.action=getMethod('editJbxx.html','delete&type=del_many');
		//form.submit();	
	//}

}

function pl_del_one(){
	actionStr2( 'delete', 'del_many_one');
}

function del(){
	if(confirm('are you sure you will delete this item?')){
		actionStr2( 'delete', 'del');
	}
}


function setIds(){
	//alert("c");
	var a=new Array();
	var ids=new Array();
	var elem;
	//获得表单元素
	a=document.forms[0].elements;
	<%	
	if(session.getAttribute("sel_ids")!=null){
		String[] ids=(String[])session.getAttribute("sel_ids");
		for(int i=0;i<ids.length;i++){
	%>
		ids[<%=i%>]=<%=ids[i]%>;

	<%
		}
	}
		
	%>

	for(var i=0;i<a.length;i++){
		elem=a[i];
		for(var j=0;j<ids.length;j++){
			if(elem.type=='checkbox'&&elem.value==ids[j]){
				elem.checked=true;
			}
		}
	}

}



function modify(){	
  var form = document.forms[0];   
  var array = form.elements;
  var sum = 0;
  for(var i=0; i<array.length; i++){
    var elem = array[i];
    if(elem.type=='checkbox'&& elem.checked){
      sum++
    }
  }
  if(sum==0){
    alert("you must choose then modiefy sts!");
    return;
  }
  if(confirm("are you sure you will mod these "+sum+" items' sts？")){
    form.action=getMethod('<c:out value="${path}"/>', 'modSts');
	form.submit();
  }
}


function getMethod(af, m){
	return '<c:url value="/"/>' + af + "?method=" + m;
}
/*function actionStr(af, m){
	form = document.forms[0];
	form.action=getMethod(af, m);
	form.submit();
} 

function actionStr1(af,m,parameter){
	form=document.forms[0];

	//alert('<c:url value="/"/>' + af + "?method=" + m + "&from=" + parameter);
	form.action=getMethod(af, m) +  "&from=" + parameter;

	form.submit();
}
*/

function actionStr2(m,parameter){
	form=document.forms[0];

	//alert('<c:url value="/"/>' + af + "?method=" + m + "&from=" + parameter);
	//alert('<%= request.getAttribute("path")%>');
	form.action=getMethod('<c:out value="${path}"/>', m) +  "&from=" + parameter;
  //if(do_validation())
  //{
	form.submit();
  //}
}




function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

 function find(){
    var form = document.forms[0]; 

	form.action=getMethod('<c:out value="${path}"/>', 'find','');
    form.submit();
    clear(form);    

}


</script>