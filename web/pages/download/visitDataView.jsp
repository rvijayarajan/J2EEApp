<%@ page language="java" errorPage="/error.jsp" pageEncoding="GBK" contentType="text/html;charset=gbk" %>

<%@ include file="/common/taglibs.jsp"%>
<script>
 var dataList = new Array();
 <% String[] dataList  =(String[])request.getAttribute("dataList");

 for(int i=0;i<dataList.length;i++) {%>
 
   dataList[<%=i%>]="<%=dataList[i]%>";   
   
 
 <%}%>
 </script>
<title><fmt:message key="bjDetail.title"/></title>
<content tag="heading"><fmt:message key="bjDetail.heading"/></content>
   <html   xmlns:v="http://www.eglic.com/" >   
  <head>   
  
 <!-- <meta http-equiv="refresh" content="3"> -->
  
  <style   type="text/css">   
  v\:*   {behavior:url(#default#VML);}   
  </style>   
  
    <script>
  function makeLineChart(data,endTime){
  //����ÿ�����Ŀ��
 
	var height=340; //400-40
  	var width = 600; //700-100
  	var startX = 100;
  	var startY = 400;
	var barwidth= Math.floor(width/data.length);//��ø�������������,��������������.
	var maxdata = Math.max.apply(this,data);
	//ͼ����������ӣ�scale*data[i]�������θ߶ȡ�
	var scale = height/maxdata;
	var j= data.length;
	var outstr,outstrend;
	outstr='<v:PolyLine filled="false" Points="';
	for(var i=0;i<j;i++){
		var x = barwidth*i + startX;
		var y = startY - data[i]*scale;
	 	outstr = outstr + x + ','+ y + ' ';
	 	var outstrtmp='<v:line style="position:absolute" from="'+x +',400" to="'+x+','+ y +'" title="'
	 	+ data[i] +'">'+ '<v:stroke dashstyle="Dot"/>' + '<v:stroke color="green"/>' + ' </v:line>';
	 	
	 	document.write(outstrtmp); 
	 	
	 	if (i==j-1)
	 		outstrend='<label style="position:absolute;left:'+ (x-startX) +';top:360">'+ endTime +'</>';
	} 
	outstr = outstr +  '" style="position:absolute">';
	outstr = outstr +  '<v:stroke color="red"/>';
	outstr = outstr +  '</v:PloyLine>';
	//alert(outstr);
	document.write(outstr); 
	document.write(outstrend); 
	return true;
  }
  </script>
  
  
  
  </head>   
  <body>  
<!--������-->
 <v:line   style="position:absolute"   from="100,400"   to="100,40"   >
<v:stroke EndArrow="Classic"/>
<v:stroke color="blue"/>
<v:stroke StrokeWeight="4"/>
</v:line> 
<v:RoundRect style="position:absolute;width:75;height:30px;left:10;top:80">
<v:shadow on="T" type="single" color="#b3b3b3" offset="5px,5px"/>
<v:TextBox inset="5pt,5pt,5pt,5pt" style="font-size:10.2pt;">����Ƶ��</v:TextBox>
</v:RoundRect> 

<!--������-->
 <v:line   style="position:absolute"   from="100,400"   to="720,400" title ="ʱ����"  >
<v:stroke EndArrow="Classic"/>
<v:stroke color="blue"/>
</v:line>
<v:RoundRect style="position:absolute;width:70;height:30px;left:680;top:460">
<v:shadow on="T" type="single" color="#b3b3b3" offset="5px,5px"/>
<v:TextBox inset="5pt,5pt,5pt,5pt" style="font-size:10.2pt;">ʱ�� T</v:TextBox>
</v:RoundRect> 


	<script>

	makeLineChart(dataList,'18:25');
 	
	</script>

  </body>   
  </html>
  
 