<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			var thead = [ [ {
				field : 'roleName',
				width : 200,
				align : 'center',
				title : '角色名称'
			}, {
				field : 'roleDesc',
				width : 290,
				align : 'center',
				title : '角色描述'
			}, {
				field : 'createTimeStr',
				width : 200,
				align : 'center',
				title : '创建时间'
			}, {
				field : 'createUsr',
				width : 200,
				align : 'center',
				title : "操作员"
			} ] ];
			$('#tt').datagrid({
				title : '角色管理',
				iconCls : 'easyui-icon-ok',
				width : '100%',
				height : 'auto',
				fit : true,
				pageList : [ 15, 20, 30, 40, 50 ],
				pageSize : 15,
				striped : true,
				url : 'role/list.jspx',
				loadMsg : '正在加载信息.....',
				sortName : 'id',
				sortOrder : 'asc',
				remoteSort : false,
				toolbar : '#tb',
				frozenColumns : [ [ {
					field : 'id',
					checkbox : true
				} ] ],
				columns : thead,
				pagination : true,
				rownumbers : true
			});
			$('#tt').datagrid('getPager').pagination({
				beforePageText : '第',
				afterPageText : '页  , 共{pages}页',
				displayMsg : '从{from}到{to}条记录，共{total}条记录',
				onBeforeRefresh : function(pageNumber, pageSize) {
					$(this).pagination('loading');
					$(this).pagination('loaded');
				},
			});
		});
	</script>
	<table id="tt">
	</table>
	<div id="tb" style="height: 28px; display: none;">
		<div style="margin: 2px;">
			<auth:a cssClass="easyui-linkbutton"
				onclick="addTab('role/add.jspx')" iconCls="easyui-icon-add"
				text="添加" />
			<auth:a cssClass="easyui-linkbutton" funcSign="role.delete"
				onclick="deletebyIds('#tt','role/delete.jspx')"
				iconCls="easyui-icon-cut" text="删除" />
			<auth:a cssClass="easyui-linkbutton" funcSign="role.edit"
				onclick="edit('#tt','role/edit')" iconCls="easyui-icon-edit"
				text="编辑" />
			<span style="float: right;"><input class="easyui-searchbox"
				data-options="prompt:'请输入角色名称',searcher:doSearch"
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
<%--
	function deletebyIds() {
			if (!confirm("确认删除信息?")) {
				return false;
			}
	<%--获取多条记录 
		var rows = $("#tt").datagrid("getSelections");
			if (rows && rows.length > 0) {
				var idss = new Array();
				for ( var i = 0; i < rows.length; i++) {
					idss.push(rows[i].id);
				}
				$.post("role/delete.jspx", {
					ids : idss.toString()
				}, function(data) {
					if (data.success) {
						$('#tt').datagrid('reload');
						promotdialog(data.msg);
					} else {
						promotdialog(data.msg);
					}
				});
			} else {
				promotdialog("请选择删除的角色信息");
			}
		}


<div
			style="border-top: 1px solid #19446a; border-bottom: 1px solid #19446a; padding: 5px;">
			<input class="x-form-text" style="width: 180px;" /> <a href="#"
				class="easyui-linkbutton" shape="rect" iconCls="easyui-icon-search">Search</a>
		</div>
		<div id="w" style="display: none;" class="easyui-window"
		title="Modal Window"
		data-options="modal:true,closed:true,iconCls:'icon-save'"
		style="width: 500px; height: 200px; padding: 10px;">
		The window content. plain="true" <a href="javascript:void(0)"
			class="easyui-linkbutton" onclick="alert('fasfsaf')">Open</a>
	</div>
		 --%>
</html>