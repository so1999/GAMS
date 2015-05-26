<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<script type="text/javascript">
	$(function() {
		var fcolumn = [ [ {
			field : "id",
			checkbox : true
		} ] ];
		var columns = [ [ {
			field : "moduleName",
			title : "栏目名称",
			width : 160,
			align : 'center'
		}, {
			field : "moduleHref",
			title : "栏目Href",
			width : 200
		}, {
			field : "parentName",
			title : "上级栏目",
			width : 180
		}, {
			field : "moduleSign",
			title : "栏目标识",
			width : 100
		}, {
			field : "moduleDesc",
			title : "栏目描述",
			width : 300
		}, {
			field : "moduleOrder",
			title : "栏目排序"
		} ] ];
		datagrid("#tt", "栏目管理", "module/list.jspx", "#tb", "id", columns,
				fcolumn);
	});
	function collapse() {
		$('#cc').layout('expand', 'east');
	}
</script>
<div id="cc" class="easyui-layout" fit="true">
	<div data-options="region:'center',border:false" style="width: 800px;">
		<table id="tt">
		</table>
		<div id="tb" style="height: 28px; display: none;">
			<div style="margin: 2px;">
				<auth:a cssClass="easyui-linkbutton" 
					onclick="addTab('module/add.jspx')" iconCls="easyui-icon-add"
					text="添加" />
				<auth:a cssClass="easyui-linkbutton"  
					onclick="deletebyIds('#tt','module/delete.jspx')"
					iconCls="easyui-icon-cut" text="删除" />
				<auth:a cssClass="easyui-linkbutton" 
					onclick="edit('#tt','module/edit')" iconCls="easyui-icon-edit"
					text="编辑" />
				<auth:a cssClass="easyui-linkbutton" 
					onclick="collapse()" iconCls="easyui-icon-cut" text="浏览树型菜单" />
				<span style="float: right;"><input class="easyui-searchbox"
					data-options="prompt:'请输入栏目名称或者父栏目名称',searcher:doSearch"
					style="width: 300px; height: 24px;" /></span><span style="clear: both;"></span>
				<script>
					function doSearch(value) {
						$("#tt").datagrid("load", {
							queryString : value
						});
					}
				</script>
			</div>
		</div>
	</div>
	<div data-options="region:'east',collapsed:true" style="width: 200px;"
		title="栏目树形显示">
		<ul style="padding: 20px;" class="easyui-tree"
			data-options="url:'module/tree.jspx',animate:true,cascadeCheck:false,lines:true">
		</ul>
	</div>
</div>