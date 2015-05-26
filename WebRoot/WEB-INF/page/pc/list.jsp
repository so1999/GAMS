<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<script type="text/javascript">
	$(function() {
		var fcolumn = [ [ {
			field : "id",
			checkbox : true
		} ] ];
		var columns = [ [
				{
					field : "pcdate",
					title : "登录日期",
					width : 120,
					align : 'center'
				},
				{
					field : "pcno",
					title : "资财编号",
					width : 150,
					align : 'center',
					formatter : function(val, rows) {
						var valx = "<a  href ='javascript:void(0)' onclick='withoutButton3(\"borr/detailHdm/"
								+ rows.pcno + ".jspx\")'>" + val + "</a>";
						return valx;
					}
				}, {
					field : "pcmodel",
					title : "MODEL",
					width : 160,
					align : 'center',
				}, {
					field : "pcsn",
					title : "S/N",
					width : 200,
					align : 'center'
				}, {
					field : "pccharger",
					title : "CHARGER",
					width : 160,
					align : 'center'
				}, {
					field : "pcstatus",
					title : "状态",
					width : 120,
					align : 'center'
				}, {
					field : "pcremark",
					title : "REMARK",
					width : 100,
					align : 'center'
				} ] ];
		datagrid("#tt", "PC管理", "pc/listPc.jspx", "#tb", "id", columns, fcolumn);
	});
</script>
<table id="tt">
</table>
<div id="tb" style="height: 28px; display: none; overflow:hidden"  >
	<div style="margin: 2px;">
		<auth:a cssClass="easyui-linkbutton" plain="true" onclick="add()"
			iconCls="easyui-icon-edit" text="添加" />
		<auth:a cssClass="easyui-linkbutton" plain="true"
			onclick="deletebyIds('#tt','pc/delete.jspx')"
			iconCls="easyui-icon-cut" text="删除" />
		<auth:a cssClass="easyui-linkbutton" plain="true"
			onclick="edit('#tt','pc/edit')" iconCls="easyui-icon-edit" text="编辑" />
		<auth:a cssClass="easyui-linkbutton" plain="true"
			onclick="edit('#tt','pc/addout')" iconCls="easyui-icon-redo"
			text="借出" />
		<auth:a cssClass="easyui-linkbutton" plain="true"
			onclick="addTab('borr/addin.jspx')" iconCls="easyui-icon-undo"
			text="归还" />
		<auth:a cssClass="easyui-linkbutton" plain="true"
			href="pc/dwPcXls.jspx" iconCls="easyui-icon-downloads" text="导出Excel" />
		<span style="float: right;"><input class="easyui-searchbox"
			data-options="prompt:'请输入查找内容',searcher:doSearch"
			style="width: 300px; height: 24px;" /> </span> <span style="clear: both;"></span>
		<script>
			function doSearch(value) {
				$("#tt").datagrid("load", {
					queryString : value
				});
			}
			function add() {
				withoutButtonWin2("pc/add.jspx");
			}
			function withoutButton3(viewUrl) {
				windialog = $.dialog({
					id : 'window',
					title : '借用记录',
					padding : 0,
					cache : false,
					lock : true,
					fix : true
				});
				var content = null;
				$.ajax({
					url : viewUrl,
					success : function(data) {
						windialog.content(data);
					}
				});
			}
			
/* 			$('#tt').datagrid({
				rowStyler : function(index,row){
					if(row.pcstatus !=="在库"){
						return 'background-color:#CDBA96' ;
						};
				}
			}); */
		</script>
	</div>
</div>