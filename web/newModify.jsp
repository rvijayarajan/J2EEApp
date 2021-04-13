<%@ page language="java" errorPage="/error.jsp" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
<%if("true".equals(request.getSession().getAttribute("sessionWasInvalid") ) ){ %>
<script type="text/javascript" >
top.window.location.href="frameset.jsp"
</script>
<%
request.getSession().setAttribute("sessionWasInvalid", "false");
}%>

<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="Page.files/filelist.xml">
<style id="Book1_1679_Styles">
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
.font51679
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font61679
	{color:windowtext;
	font-size:9.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font71679
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"Times New Roman", serif;
	mso-font-charset:0;}
.font81679
	{color:black;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Verdana, sans-serif;
	mso-font-charset:0;}
.font91679
	{color:black;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.xl151679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl221679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:white;
	font-size:9.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Verdana, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:#333399;
	mso-pattern:auto none;
	white-space:normal;}
.xl231679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:#FFCC99;
	mso-pattern:auto none;
	white-space:nowrap;}
.xl241679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:justify;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:#FFFF99;
	mso-pattern:auto none;
	white-space:normal;}
.xl251679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"Times New Roman", serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:justify;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:#CCFFFF;
	mso-pattern:auto none;
	white-space:normal;}
.xl261679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"Times New Roman", serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:justify;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:#FFFF99;
	mso-pattern:auto none;
	white-space:normal;}
.xl271679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:normal;}
.xl281679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:9.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:#FFFF99;
	mso-pattern:auto none;
	white-space:normal;}
.xl291679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:9.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:#CCFFFF;
	mso-pattern:auto none;
	white-space:normal;}
.xl301679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:9.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:#FFFF99;
	mso-pattern:auto none;
	white-space:normal;}
.xl311679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:9.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:#CCFFFF;
	mso-pattern:auto none;
	white-space:normal;}
.xl321679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl331679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:normal;}
.xl341679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl351679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:right;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:normal;}
.xl361679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:right;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl371679
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:none;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
ruby
	{ruby-align:left;}
rt
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-char-type:none;}
.font616791 {color:windowtext;
	font-size:9.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
--></style>
</head>

<body>
<!--[if !excel]>　　<![endif]-->
<!--下列信息由 Microsoft Office Excel 的“发布为网页”向导生成。-->
<!--如果同一条目从 Excel 中重新发布，则所有位于 DIV 标记之间的信息均将被替换。-->
<!----------------------------->
<!--“从 EXCEL 发布网页”向导开始-->
<!----------------------------->

<div id="Book1_1679"  align=center x:publishsource="Excel">

<table x:str border=0 cellpadding=0 cellspacing=0 width=823 style='border-collapse:
 collapse;table-layout:fixed;width:618pt'>
 <col width=48 style='mso-width-source:userset;mso-width-alt:1536;width:36pt'>
 <col class=xl271679 width=72 style='width:54pt'>
 <col width=273 style='mso-width-source:userset;mso-width-alt:8736;width:205pt'>
 <col width=430 style='mso-width-source:userset;mso-width-alt:13760;width:323pt'>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl321679 width=48 style='height:14.25pt;width:36pt'></td>
  <td class=xl331679 width=72 style='width:54pt'></td>
  <td class=xl341679 width=273 style='width:205pt'>来华学生信息管理系统<font
  class="font81679">4.5</font><font class="font91679">升级内容</font></td>
  <td class=xl321679 width=430 style='width:323pt'></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl221679 width=48 style='height:14.25pt;width:36pt'>序号</td>
  <td class=xl221679 width=72 style='border-left:none;width:54pt'>模块名称</td>
  <td class=xl221679 width=273 style='border-left:none;width:205pt'>功能描述</td>
  <td class=xl221679 width=430 style='border-left:none;width:323pt'>业务规则</td>
 </tr>
 <tr height=99 style='mso-height-source:userset;height:74.25pt'>
  <td height=99 class=xl231679 style='height:74.25pt;border-top:none' x:num>1</td>
  <td class=xl281679 width=72 style='border-top:none;border-left:none;
  width:54pt'>数据查询之基本信息</td>
  <td class=xl241679 width=273 style='border-top:none;border-left:none;
  width:205pt'>上层用户<font class="font51679">能对下属教委及院校上报的学生详细信息进行查询</font></td>
  <td class=xl241679 width=430 style='border-top:none;border-left:none;
  width:323pt'>1.<font class="font61679">上报索引、教委及院校</font><font
  class="font51679">为必填，查询条件、生成excel选项为选填。如果用户选择了“</font><font class="font61679">具体院校</font><font
  class="font51679">”，则</font><font class="font61679">具体院校名称</font><font
  class="font51679">要求必填。<br>
    2.系统会对具体院校是否属于当前用户所在单位或所选教委管辖范围进行检查。<br>
    3.提供可选择excel生成项的功能。<br>
    4.部分字段有放大镜，放大镜内容只取教育部的</font><font class="font61679">规范数据</font><font
  class="font51679">。</font></td>
 </tr>
 <tr height=105 style='mso-height-source:userset;height:78.75pt'>
  <td height=105 class=xl231679 style='height:78.75pt;border-top:none' x:num>2</td>
  <td class=xl291679 width=72 style='border-top:none;border-left:none;
  width:54pt'>数据查询之短期团组</td>
  <td class=xl251679 width=273 style='border-top:none;border-left:none;
  width:205pt'>上层用户<font class="font51679">能对下属教委及院校上报的短期团组信息进行查询</font></td>
  <td class=xl251679 width=430 style='border-top:none;border-left:none;
  width:323pt'>1.<font class="font61679">上报索引、教委及院校</font><font
  class="font51679">为必填，查询条件、生成</font><font class="font71679">excel</font><font
  class="font51679">选项为选填。如果用户选择了</font><font class="font71679">“</font><font
  class="font61679">具体院校</font><font class="font71679">”</font><font
  class="font51679">，则</font><font class="font61679">具体院校名称</font><font
  class="font51679">要求必填。<br>
    </font><font class="font71679">2.</font><font class="font51679">系统会对具体院校是否属于当前用户所在单位或所选教委管辖范围进行检查。<br>
    </font><font class="font71679">3.</font><font class="font51679">部分字段有放大镜，除</font><font
  class="font61679">团组名称</font><font class="font51679">以外，放大镜内容只取教育部的</font><font
  class="font61679">规范数据</font><font class="font51679">。</font><font
  class="font61679">团组名称</font><font class="font51679">放大镜必须在用户选择了教委及院校之后，才能使用。</font></td>
 </tr>
 <tr height=109 style='mso-height-source:userset;height:81.75pt'>
  <td height=109 class=xl231679 style='height:81.75pt;border-top:none' x:num>3</td>
  <td class=xl281679 width=72 style='border-top:none;border-left:none;
  width:54pt'>数据查询之长期生查询</td>
  <td class=xl241679 width=273 style='border-top:none;border-left:none;
  width:205pt'>上层用户<font class="font51679">能对下属教委及院校上报的长期生信息进行查询</font></td>
  <td class=xl261679 width=430 style='border-top:none;border-left:none;
  width:323pt'>1.<font class="font51679">长期生信息指的是</font><font class="font61679">停留期限</font><font
  class="font51679">为</font><font class="font71679">“</font><font
  class="font61679">长期</font><font class="font71679">”</font><font
  class="font51679">的</font><font class="font61679">基本信息</font><font
  class="font51679">。<br>
    </font><font class="font71679">2.</font><font class="font61679">上报索引、教委及院校</font><font
  class="font51679">为必填，查询条件、生成</font><font class="font71679">excel</font><font
  class="font51679">选项为选填。如果用户选择了</font><font class="font71679">“</font><font
  class="font61679">具体院校</font><font class="font71679">”</font><font
  class="font51679">，则</font><font class="font61679">具体院校名称</font><font
  class="font51679">要求必填。<br>
    </font><font class="font71679">3.</font><font class="font51679">系统会对具体院校是否属于当前用户所在单位或所选教委管辖范围进行检查。<br>
    </font><font class="font71679">4.</font><font class="font51679">部分字段有放大镜，放大镜内容只取教育部的</font><font
  class="font61679">规范数据</font><font class="font51679">。</font></td>
 </tr>
 <tr height=116 style='mso-height-source:userset;height:87.0pt'>
   <td height=116 class=xl231679 style='height:87.0pt;border-top:none' x:num>4</td>
   <td class=xl291679 style='border-top:none;border-left:none;
  width:54pt'>通信录</td>
   <td class=xl251679 style='border-top:none;border-left:none;
  width:205pt'>增加了对基层使用单位的通信录查询功能</td>
   <td class=xl251679 style='border-top:none;border-left:none;
  width:323pt'><p>1.教育部能查询所有使用单位的通信录</p>
     <p>2.各级教委只能查询所管辖范围内的单位的通信录</p></td>
 </tr>
 <tr  style='mso-height-source:userset;height:87.0pt' height=116>
   <td height=116 class="xl231679"  style='height:87.0pt;border-top:none' x:num>5</td>
   <td class=xl281679 style='border-top:none;border-left:none;
  width:54pt'>数据访问审核</td>
   <td class=xl241679 style='border-top:none;border-left:none;
  width:205pt'><p><span class="font61679">即时监控</span>，对基层使用单位的使用情况的实时监控。</p>
     <p><span class="font61679">访问记录查询</span>，对基层使用单位的使用情况进行查询</p></td>
   <td class=xl241679 style='border-top:none;border-left:none;
  width:323pt'><p>1.教育部能<span class="font61679">监控</span>或<span class="font61679">查询</span>所有使用单位的使用情况。</p>
     <p>2.各级教委只能<span class="font616791">监控</span>或<span class="font616791">查询</span>所管辖范围内的单位的使用情况；</p></td>
 </tr>
 <tr height=116 style='mso-height-source:userset;height:87.0pt'>
  <td height=116 class=xl231679 style='height:87.0pt;border-top:none' x:num>6</td>
  <td class=xl291679 width=72 style='border-top:none;border-left:none;
  width:54pt'>数据查询之短期生查询</td>
  <td class=xl251679 width=273 style='border-top:none;border-left:none;
  width:205pt'>上层用户<font class="font51679">能对下属教委及院校上报的短期生信息进行查询</font></td>
  <td class=xl251679 width=430 style='border-top:none;border-left:none;
  width:323pt'>1.<font class="font51679">短期生是指基本信息中</font><font
  class="font61679">停留期限</font><font class="font51679">为“</font><font
  class="font61679">短期</font><font class="font51679">”的学生和</font><font
  class="font61679">短期团组</font><font class="font51679">中的学生。<br>
    </font><font class="font71679">2.</font><font class="font61679">上报索引、教委及院校</font><font
  class="font51679">为必填，查询条件、生成</font><font class="font71679">excel</font><font
  class="font51679">选项为选填。如果用户选择了</font><font class="font71679">“</font><font
  class="font61679">具体院校</font><font class="font71679">”</font><font
  class="font51679">，则</font><font class="font61679">具体院校名称</font><font
  class="font51679">要求必填。<br>
    </font><font class="font71679">3.</font><font class="font51679">系统会对具体院校是否属于当前用户所在单位或所选教委管辖范围进行检查。<br>
    </font><font class="font71679">4.</font><font class="font51679">部分字段有放大镜，除</font><font
  class="font61679">团组名称</font><font class="font51679">以外，放大镜内容只取教育部的</font><font
  class="font61679">规范数据</font><font class="font51679">。</font><font
  class="font61679">团组名称</font><font class="font51679">放大镜必须在用户选择了教委及院校之后，才能使用。</font></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl151679 style='height:14.25pt'></td>
  <td class=xl271679 width=72 style='width:54pt'></td>
  <td colspan=2 rowspan=2 class=xl371679>联系电话：010-62282889 010-62285635　</td>
 </tr>
 <tr height=33 style='mso-height-source:userset;height:24.75pt'>
  <td height=33 class=xl151679 style='height:24.75pt'></td>
  <td class=xl271679 width=72 style='width:54pt'></td>
 </tr>
 <tr height=32 style='mso-height-source:userset;height:24.0pt'>
  <td height=32 class=xl151679 style='height:24.0pt'></td>
  <td class=xl271679 width=72 style='width:54pt'></td>
  <td class=xl151679></td>
  <td class=xl361679>全国留学生来华管理信息系统项目组</td>
 </tr>
 <tr height=38 style='height:28.5pt'>
  <td height=38 class=xl151679 style='height:28.5pt'></td>
  <td class=xl271679 width=72 style='width:54pt'></td>
  <td class=xl151679></td>
  <td class=xl351679 width=430 style='width:323pt'>2007-8-24<br>
    <span style='mso-spacerun:yes'>&nbsp;</span><br>
    </td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=48 style='width:36pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=273 style='width:205pt'></td>
  <td width=430 style='width:323pt'></td>
 </tr>
 <![endif]>
</table>

</div>


<!----------------------------->
<!--“从 EXCEL 发布网页”向导结束-->
<!----------------------------->
</body>

</html>