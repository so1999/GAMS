<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			var fcolumn = [ [ {
				field : "id",
				checkbox : true
			} ] ];
			var columns = [ [ {
				field : "funcName",
				title : "功能名称",
				width : 200,
				align : 'center'
			}, {
				field : "moduleName",
				title : "所属栏目",
				width : 200,
				align : 'center'
			}, {
				field : "funcSign",
				title : "功能标识",
				width : 200,
				align : 'center'
			}, {
				field : "funcDesc",
				title : "功能介绍",
				width : 400
			} ] ];
			datagrid("#tt", "功能管理", "func/list.jspx", "#tb", "id", columns,
					fcolumn);
		});
	</script>
	<table id="tt">
	</table>
	<div id="tb" style="height: 28px; display: none;">
		<div style="margin: 2px;">
			<auth:a cssClass="easyui-linkbutton"  funcSign="func.add" allowAuth="false"
				onclick="addTab('func/add.jspx')" iconCls="easyui-icon-add"
				text="添加" />
			<auth:a cssClass="easyui-linkbutton"  funcSign="func.delete"
				onclick="deletebyIds('#tt','func/delete.jspx')"
				iconCls="easyui-icon-cut" text="删除" />
			<auth:a cssClass="easyui-linkbutton"  funcSign="func.edit"
				onclick="edit('#tt','func/edit')" iconCls="easyui-icon-edit"
				text="编辑" />
			<span style="float: right;"><input class="easyui-searchbox"
				data-options="prompt:'请输入功能名称',searcher:doSearch"
				style="width: 300px; height: 24px;" /></span> <span style="clear: both;"></span>
			<script>
				function doSearch(value) {
					$("#tt").datagrid("load", {
						queryString : value
					});
				}
			</script>
		</div>
	</div>
</body>
</html>