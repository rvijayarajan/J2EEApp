<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" errorPage="/error.jsp" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>

 <head>
  <title> Menu </title>
	<style type="text/css">
	body{
		margin:0px;
		padding:0px;
	}
	ul,li {
		margin:0px;
		padding:0px;
		list-style-type:none;
		font-size:12px;
	}
	a{display:block;		text-decoration:none;	}
	img{border:none;	}

	#narBar{width:155px;		border:1px solid #0000ff;	}

	.item1{	background:url(menuImages/menubar_bg.gif) repeat-x left top;}

	.item1 a.title {height:31px;line-height:31px;font-size:14px;	}
	.item1 a.title img{	padding:5px 0px;	vertical-align:middle;		border:none;	}
	
	
	.item2 a.title{		height:28px;		line-height:28px;		font-size:12px;	}
	
	.item2 a.title img{		padding:5px 0px;		vertical-align:middle;		border:none;	}

	.option{		display:block;	}
	.option li a{		height:25px;		line-height:25px;	}
	.option li a img{		padding:0px;		vertical-align:middle;		border:none;	}

</style>
<script language="javascript" type="text/javascript" src="scripts/jquery.js"></script>
<script language="javascript" type="text/javascript">
  <!--
	$(document).ready(function(){
		//alert($(".item1").length);
		
		$("#option1").height(document.documentElement.clientHeight - 4*32);
	});
	
	//submenu-behavior
	function fold(obj)
	{
		if ("block" == $(obj).next().css("display"))
		{
			$(obj).next().css("display","none");
		}
		else
		{
			$(obj).next().css("display","block");
		}
	}

	//mainmenu-behavior
	function scroll(index)
	{
		for (var i=0; i<$(".item1").length ;i++ )
		{
			$("#option" + (i+1)).css("display","none");
		}
		
		$("#option" + index).css("display","block");
		$("#option" + index).height(document.documentElement.clientHeight - 4*32);
	}
	
  //-->
  </script>
 </head>

 <body>
  <div>
	<ul id="narBar">
		<li class="item1"><a href="#" class="title" onclick="scroll(1);return false;"><img src="menuImages/info_search.gif" />��Ϣ��ѯ</a>
			<ul id="option1" class="option" style="display:block;">
				<li class="item2"><a href="#" class="title"><img src="menuImages/all_water_source_search.gif" />ȫ����ȾԴ</a></li>
				<li class="item2"><a href="#" class="title" onclick="fold(this);return false;"><img src="menuImages/gyyxb.gif"  />��ҵԴ���</a>
					<ul class="option" style="display:none;">
						<li><a href="#"><img src="menuImages/enterprise.gif" />��ҵ</a></li>
						<li><a href="#"><img src="menuImages/waste_water.gif" />��ˮ</a></li>
						<li><a href="#"><img src="menuImages/gouluyaolu.gif"  />��¯��Ҥ¯</a></li>
						<li><a href="#"><img src="menuImages/waste_gas.gif"  />����</a></li>
						<li><a href="#"><img src="menuImages/gufei.gif"  />�̷�</a></li>
						<li><a href="#"><img src="menuImages/weixiangufei.gif"  />Σ�շ���</a></li>
					</ul>
				</li>
				<li class="item2"><a href="#" class="title" onclick="fold(this);return false;"><img src="menuImages/gyyjb.gif" />��ҵԴ���</a>
					<ul class="option" style="display:none;">
						<li><a href="#"><img src="menuImages/enterprise.gif" border="0" />��ҵ</a></li>
						<li><a href="#"><img src="menuImages/waste_water.gif" border="0" />��ˮ</a></li>
						<li><a href="#"><img src="menuImages/waste_gas.gif" border="0" />����</a></li>
						<li><a href="#"><img src="menuImages/gufei.gif" border="0" />�̷�</a></li>
					</ul>
				</li>
			</ul>
		</li>
		<li class="item1"><a href="#" class="title" onclick="scroll(2);return false;"><img src="menuImages/data_static.gif"  />����ͳ��</a>
			<ul id="option2" class="option" style="display:none;">
				<li class="item2"><a href="#" class="title" onclick="fold(this);return false;"><img src="menuImages/kind_static.gif" border="0" />����ͳ��</a>
					<ul class="option" style="display:none;">
						<li><a href="#"><img src="menuImages/enterprise.gif" />��ҵ</a></li>
						<li><a href="#"><img src="menuImages/waste_water.gif" />��ˮ</a></li>
						<li><a href="#"><img src="menuImages/waste_gas.gif" />����</a></li>
						<li><a href="#"><img src="menuImages/gufei.gif" />�̷�</a></li>
						<li><a href="#"><img src="menuImages/weixiangufei.gif" />Σ�շ���</a></li>
					</ul>
				</li>
				<li class="item2"><a href="#" class="title" onclick="fold(this);return false;"><img src="menuImages/static.gif" border="0" />����ͳ��</a>
					<ul class="option" style="display:none;">
						<li><a href="#"><img src="menuImages/waste_water.gif" />��ˮ</a></li>
						<li><a href="#"><img src="menuImages/waste_gas.gif" />����</a></li>
						<li><a href="#"><img src="menuImages/gufei.gif" />�̷�</a></li>
					</ul>
				</li>
			</ul>
		</li>
		<li class="item1"><a href="#" class="title" onclick="scroll(3);return false;"><img src="menuImages/self_set.gif"  />��������</a>
			<ul id="option3" class="option" style="display:none;">
				<li><a href="#"><img src="menuImages/per_page_set.gif" />��ҳ������</a></li>
				<li><a href="#"><img src="menuImages/modify_pass.gif" />�����޸�</a></li>
			</ul>
		</li>
		<li class="item1"><a href="#" class="title" onclick="scroll(4);return false;"><img src="menuImages/system_setup.gif"  />ϵͳ����</a>
			<ul id="option4" class="option" style="display:none;">
				<li><a href="#"><img src="menuImages/depart_admin.gif" />���Ź���</a></li>
				<li><a href="#"><img src="menuImages/user_admin.gif" />�û�����</a></li>
				<li><a href="#"><img src="menuImages/role_admin.gif" />��ɫ����</a></li>
				<li><a href="#"><img src="menuImages/default_year_set.gif" />Ĭ���������</a></li>
				<li><a href="#"><img src="menuImages/data_import.gif" />���ݵ���</a></li>
			</ul>
		</li>
	</ul>
  </div>
 </body>
</html>
