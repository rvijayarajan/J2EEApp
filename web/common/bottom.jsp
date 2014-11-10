<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>

<%@ include file="/common/taglibs.jsp"%>
<c:choose>
<c:when test="${ptype=='find'}">
	<div align="center">
		<a href="javascript:find()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image32','','<c:url value="/images/common/icons/apply_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/apply_on.gif"/>" name="Image32" width="60" height="20" border="0" /></a>
		<a href="javascript:clear()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image9','','<c:url value="/images/common/icons/rewrite_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/rewrite_on.gif"/>" name="Image9" width="60" height="20" border="0"></a>
	</div>
</c:when>
<c:when test="${ptype=='form'}">
	<table align="center"> 
     <tr>
        <td></td>
        <td class="buttonBar">  
       		<%if (request.getParameter("from") != null &&(request.getParameter("from").equals("del_many_one") ||request.getParameter("from").equals("list") || request.getParameter("from").equals("edit_many")||request.getParameter("from").equals("save_many"))){ %>
				<a href="javascript:pl_save()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image99','','<c:url value="/images/common/icons/save_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/save_on.gif"/>" name="Image99" width="60" height="20" border="0"></a>
				<a href="javascript:clear()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image9','','<c:url value="/images/common/icons/rewrite_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/rewrite_on.gif"/>" name="Image9" width="60" height="20" border="0"></a>			
			<% } else  {%>
			<a href="javascript:save()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image89','','<c:url value="/images/common/icons/save_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/save_on.gif"/>" name="Image89" width="60" height="20" border="0"></a>	
				<a href="javascript:clear()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image9','','<c:url value="/images/common/icons/rewrite_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/rewrite_on.gif"/>" name="Image9" width="60" height="20" border="0"></a>
			<% } %>
			
			<a href=<c:out value="${path}" />?method=gotoFind onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image71','','<c:url value="/images/common/icons/back_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/back_on.gif"/>" name="Image71" width="60" height="20" border="0" id="Image71"></a>
			</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
             <c:choose>
				<c:when test="${img_state=='next'}">
					<html:img style="cursor:pointer"pageKey="prior_off.image" />
					<%if (request.getParameter("from") != null &&(request.getParameter("from").equals("view_many"))){ %>
					<html:img style="cursor:pointer"pageKey="next.image" onclick="actionStr3('next', 'view_many')"/>
					<% } else {%>
					<html:img style="cursor:pointer"pageKey="next.image" onclick="actionStr3('next', 'edit_many')"/>
					<% } %>
				</c:when>
				<c:when test="${img_state=='prior'}">
					<%if (request.getParameter("from") != null &&(request.getParameter("from").equals("view_many"))){ %>
					<html:img style="cursor:pointer"pageKey="prior.image" onclick="actionStr3('prior', 'view_many')"/>
					<% } else {%>
					<html:img style="cursor:pointer"pageKey="prior.image" onclick="actionStr3('prior', 'edit_many')"/>
					<% } %>
					<html:img style="cursor:pointer"pageKey="next_off.image" />
				</c:when>
				<c:when test="${img_state=='both'}">
				    <%if (request.getParameter("from") != null &&(request.getParameter("from").equals("view_many"))){ %>
					<html:img style="cursor:pointer"pageKey="prior.image" onclick="actionStr3('prior', 'view_many')"/>
					<% } else {%>
					<html:img style="cursor:pointer"pageKey="prior.image" onclick="actionStr3('prior', 'edit_many')"/>
					<% } %>
					<%if (request.getParameter("from") != null &&(request.getParameter("from").equals("view_many"))){ %>
					<html:img style="cursor:pointer"pageKey="next.image" onclick="actionStr3('next', 'view_many')"/>
					<% } else {%>
					<html:img style="cursor:pointer"pageKey="next.image" onclick="actionStr3('next', 'edit_many')"/>
					<% } %>
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
					<a href="JavaScript:history.back();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image71','','<c:url value="/images/common/icons/back_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/back_on.gif"/>" name="Image71" width="60" height="20" border="0" id="Image71"></a>
				</c:when>
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

	<table align="center">
	   
		<c:choose>
			<c:when test="${path=='editSbgzgl.html'}">	
					<a href="JavaScript:pl_updatezt('asc');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image71','','<c:url value="/images/common/icons/affirm_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/affirm_on.gif"/>" name="Image71" width="60" height="20" border="0" id="Image71"></a>
				
					<a href="javascript:pl_updatezt('des')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image9','','<c:url value="/images/common/icons/retreat_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/retreat_on.gif"/>" name="Image9" width="60" height="20" border="0"></a>
		     	</c:when>
			</c:choose>
			 
		 <a href=<c:out value="${path}" />?method=gotoFind onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image171','','<c:url value="/images/common/icons/back_over.gif"/>',1)"><img src="<c:url value="/images/common/icons/back_on.gif"/>" name="Image171" width="60" height="20" border="0" id="Image171"></a>
	</table>
</c:when>
</c:choose>


<script>

function save(){
	actionStr2('save', 'save');
}
function pl_save(){
	actionStr2('save', 'save_many');
}
function edit(){
    actionStr2('edit','edit');
}
function pl_updatezt(parameter){
var form=document.forms[0];
	//var array=form.ids;
	var array=form.elements;
	var sumcheck=0;
	var sum=0;
	for(var i=0;i<array.length;i++)
	{
		if(array[i].type=="checkbox"){
			sumcheck++;
		if(array[i].checked)
			sum++;
		}
	}
	if(sum==0 && sumcheck > 0){
		alert("请先选择记录，再进行状态更新操作!");
		return;
	}
	else
		pl_updatezt_act(parameter);
}
function pl_updatezt_act(parameter){  
 var form=document.forms[0];

  var array = form.elements; 
  var sum = 0;
  for(var i=0; i<array.length; i++){
    var elem = array[i];
    if(elem.type=='checkbox'&& elem.checked){
      sum++;
    }
  }
  <%if(request.getAttribute("scdwbm")!=null){%>
  	var jwscdwbm = '<%= request.getAttribute("scdwbm")%>';
  if(jwscdwbm!=null&&jwscdwbm!='null')
  	form.action=getMethod('<c:out value="${path}"/>', 'update_zt') +  "&from=" + parameter+"&scdwbm="+jwscdwbm;
  else
   	form.action=getMethod('<c:out value="${path}"/>', 'update_zt') +  "&from=" + parameter;
 <%}else{%>
   	form.action=getMethod('<c:out value="${path}"/>', 'update_zt') +  "&from=" + parameter;
 <%}%>
    if(CheckInput())
	{
	form.submit();
	}
}

function pl_edit(){
	var form=document.forms[0];
	//var array=form.ids;
	var array=form.elements;
	var sumcheck=0;
	var sum=0;
	for(var i=0;i<array.length;i++)
	{
		if(array[i].type=="checkbox"){
			sumcheck++;
		if(array[i].checked)
			sum++;
		}
	}
	if(sum==0 && sumcheck > 0){
		alert("请先选择记录，再进行修改操作!");
		return;
	}
	else
		pl_edit_act();
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

//批量删除
function pl_del(){
	var form=document.forms[0];
	var sum=0;
	var array=form.elements;
	for(var i=0;i<array.length;i++){
		var e=array[i];
		if(e.type=='checkbox'&& e.name != ""&&e.checked){
			sum++;
		}
	}
	if(sum==0){
		alert("请先选择记录，再进行删除操作");
		return;
	}else{
	var jbxxdel = '<%= request.getAttribute("path")%>';
 
	if(jbxxdel=="editJbxx.html")
	   {
		if(confirm("你确定要删除选择的记录吗?该学生其他信息将一并被删除!"))
			pl_del_act();
//				alert("1");
//					alert("d");
		}
		else if(confirm("你确定要删除选择的记录吗?"))
			pl_del_act();
//			alert("1");
	}
	
}

function pl_del_act(){
	actionStr2( 'delete', 'del_many');
//				alert("2");
}

function pl_del_one(){
var jbxxdel ='<%= request.getAttribute("path")%>';
// 			alert("3");
	if(jbxxdel=="editJbxx.html")
	   {
		if(confirm("你确定要删除选择的记录吗?该学生其他信息将一并被删除!"))
			actionStr2( 'delete', 'del_many_one');
		}
	else if(confirm('确定要删除选择的记录吗?')){
	actionStr2( 'delete', 'del_many_one');
	}
}

function del(){
var jbxxdel = '<%= request.getAttribute("path")%>';
 
	if(jbxxdel=="editJbxx.html")
	   {
		if(confirm("你确定要删除选择的记录吗?该学生其他信息将一并被删除!"))
			actionStr2( 'delete', 'del');
//							alert("10");
		}
	 else if(confirm('确定要删除选择的记录吗?')){
		actionStr2( 'delete', 'del');
	}
}


function modify(){	
  var form = document.forms[0];   
  var array = form.elements;
  var sum = 0;
  for(var i=0; i<array.length; i++){
    var elem = array[i];
    if(elem.type=='checkbox' && elem.name != "" && elem.checked){
      sum++
    }
  }
  if(sum==0){
    alert("修改学生状态之前，必须选择记录!");
    return;
  }
  
  form.action=getMethod('<c:out value="${path}"/>', 'modSts');
  form.submit(); 

}


function add(){
   
    var form = document.forms[0];   
	var radios=form.xsztid;
	var sum=0;

	for(var i=0;i<radios.length;i++)
	{
		if(radios[i].checked)
			sum++;
	}

	if(sum==0)
	{
		alert("请选择学生状态!");
		return;
	}

	form.action=getMethod('editJbxx.html', 'cmModSts');

	if(CheckInput())
	{
	    form.submit();
	    clear();  
	}         

}

function getMethod(af, m){
	return '<c:url value="/"/>' + af + "?method=" + m;
}


function actionStr2(m,parameter){
	form=document.forms[0];

	//alert('<c:url value="/"/>' + af + "?method=" + m + "&from=" + parameter);
	// alert('<%= request.getAttribute("path")%>');
	form.action=getMethod('<c:out value="${path}"/>', m) +  "&from=" + parameter;
    if(CheckInput())
	{
	form.submit();
	}
}


function find(){
    var form = document.forms[0]; 

	form.action=getMethod('<c:out value="${path}"/>', 'find','');
	if(CheckInput())
    {
    form.submit();
    clear(form);    
    }
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
function actionStr3(m,parameter){
	form=document.forms[0];
	form.action=getMethod('<c:out value="${path}"/>', m) +  "&from=" + parameter;
	form.submit();
}
function actionStr4(m){
	form=document.forms[0];
	//alert('<c:url value="/"/>' + af + "?method=" + m + "&from=" + parameter);
	//alert('<%= request.getAttribute("path")%>');
	form.action=getMethod('<c:out value="${path}"/>', m);
    if(CheckInput())
	{
	form.submit();
	}
}
function pl_view(){
	var form=document.forms[0];
	//var array=form.ids;
	var array=form.elements;
	var sumcheck=0;
	var sum=0;
	for(var i=0;i<array.length;i++)
	{
		if(array[i].type=="checkbox"){
			sumcheck++;
		if(array[i].checked)
			sum++;
		}
	}
	if(sum==0 && sumcheck > 0){
		alert("请先选择记录，再进行查看操作!");
		return;
	}
	else
		pl_view_act();
}
function pl_view_act(){	
  actionStr2( 'view_many', 'view_many');
  
}
</script>