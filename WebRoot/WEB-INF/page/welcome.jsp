<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="auth" uri="authorize"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>资材管理系统GAMS V1.0</title>
<%@include file="common.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		addTab("usr/desk.jspx");
		var a = $("#menu li a");
		$("#menu li a").click(function() {
			a.removeClass("selected");
			$(this).addClass("selected");
			addTab($(this).attr("id"));
		});
	});
	function reload() {
		$('#panel').panel('refresh');
	}
	function home() {
		addTab("usr/desk.jspx");
	}
	function updatepswd() {
		withoutButtonWin("acusr/indexpswd.jspx");
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="north-header">
		<div style="height: 58px;" class="north-title">
		<div class="top-font" style="float: left;"></div>
	</div>
	</div>
	<div data-options="region:'west',split:true,border:false,tools:'#home'"
		title="菜单" style="width: 200px;">
		<div class="easyui-accordion" data-options="fit:true,border:false"
			style="margin-top: 2px;">
			<auth:div allowAuth="true" />
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="height: 20px; background: #5e6e7e;">
		<div style="text-align: center; color: gray;">
			<span style="color: #ffffff; padding-left: 10px;">Copyright
				&nbsp;© 2014</span> &nbsp; 版权所有
		</div>
	</div>
	<div id="panel"
		data-options="region:'center',loadingMessage:'正在加载',border:false,tools:'#tt'"
		class="easyui-panel" title="首页" style="padding: 5px;"></div>
	<div id="home">
		<a href="javascript:void(0)" class="easyui-icon-home"
			onclick="javascript:home()" title="桌面"></a>
	</div>
	<div id="tt">
		<div>
			<a href="javascript:void(0)" class="easyui-icon-edit"
				onclick="javascript:updatepswd()"></a><span
				style="margin-right: 20px; color: #ffffff; font-weight: bold; cursor: pointer;"
				onclick="updatepswd()">修改密码</span><a href="logout.jhtml"
				class="easyui-icon-exit" title="退出系统"></a><span
				style="margin-right: 20px; color: #ffffff; font-weight: bold; cursor: pointer;"
				onclick="location.href='logout.jhtml'">退出系统</span>
		</div>
	</div>
</body>
</html>
