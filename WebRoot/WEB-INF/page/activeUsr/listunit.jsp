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
			field : "utdate",
			title : "登录日期",
			width : 120,
			align : 'center'
		}, {
			field : "utno",
			title : "资财编号",
			width : 150,
			align : 'center'
		}, {
			field : "utname",
			title : "名称",
			width : 160,
			align : 'center',
		}, {
			field : "utspec",
			title : "规格",
			width : 160,
			align : 'center'
		}, {
			field : "utuse",
			title : "用途",
			width : 120,
			align : 'center'
		}, {
			field : "utstatus",
			title : "状态",
			width : 120,
			align : 'center'
		}, {
			field : "utremark",
			title : "REMARK",
			width : 100,
			align : 'center'
		} ] ];
		datagrid("#tt", "个人UNIT管理", "per/listMyUnit.jspx", "#tb", "id", columns,
				fcolumn);
	});
</script>
<table id="tt">
</table>
<div id="tb" style="height: 28px; display: none;">
	<div style="margin: 2px;">
		<auth:a cssClass="easyui-linkbutton" allowAuth="false"
			funcSign="unit.add" plain="true" onclick="add()"
			iconCls="easyui-icon-edit" text="添加" />
		<auth:a cssClass="easyui-linkbutton" href="unit/dwUnitXls.jspx"
			iconCls="easyui-icon-downloads" plain="true" text="导出Excel" />
		<span style="float: right;">内容: <input id="scon" type="text"
			name="scon" class="x-form-text" /> <a class="easyui-linkbutton"
			onclick="doSearch()">搜索</a> </span> 
		<span style="float: right;">名称:
			<select id=utname name="utname" style="width: 100px;" >
				<option value="CPU">CPU</option>
				<option value="HDD">HDD</option>
				<option value="MEMORY">MEMORY</option>
				<option value="MB">MB</option>
				<option value="OTHER">OTHER</option>
		</select>
		</span> <span style="clear: both;"></span>
		<script>
			var utname = '${utname}';
			$('#utname').combobox({
				multiple : false,
				editable : true,
				panelHeight : 'auto',
				onLoadSuccess : function() {
				$("#utname").combobox("setValue", utname);
				},
				onSelect : function() {
					doSearch();
				}
			});
			function doSearch(value) {
				$("#tt").datagrid("load", {
					utname : $("input[name='utname']").val(),
					scon : $("input[name='scon']").val(),
				});
			}
			function add() {
				withoutButtonWin2("unit/add.jspx");
			}
			
		</script>
	</div>
</div>