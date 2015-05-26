<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<script type="text/javascript">
	$(function() {
		var fcolumn = [ [ {
			field : "id",
		} ] ];
		var columns = [ [
				{
					field : "iopcno",
					title : "资财编号",
					width : 150,
					align : 'center',
				},{
					field : "iou",
					title : "借出/归还",
					width : 150,
					align : 'center',
				}, {
					field : "iopcmodel",
					title : "MODEL",
					width : 160,
					align : 'center',
				}, {
					field : "iopcsn",
					title : "S/N",
					width : 200,
					align : 'center'
				}, {
					field : "iopccharger",
					title : "CHARGER",
					width : 160,
					align : 'center'
				}, {
					field : "iopcstatus",
					title : "状态",
					width : 120,
					align : 'center'
				}, {
					field : "ioperson",
					title : "借用人",
					width : 120,
					align : 'center'
				}, {
					field : "ioyon",
					title : "是否归还",
					width : 120,
					align : 'center'
				}, {
					field : "ioindate",
					title : "借出/归还日期",
					width : 120,
					align : 'center'
				}, {
					field : "ioremark",
					title : "REMARK",
					width : 100,
					align : 'center'
				} ] ];
		datagrid("#tt", "借用记录", "borr/listBorr.jspx", "#tb", "id", columns,
				fcolumn );
	});
</script>
<table id="tt">
</table>
<div id="tb" style="height: 28px; display: none;">
	<div style="margin: 2px;">
		<span style="float: left;"><input class="easyui-searchbox"
			data-options="prompt:'请输入查找内容',searcher:doSearch"
			style="width: 300px; height: 24px;" /> </span> <span style="clear: both;"></span>
		<script>
			function doSearch(value) {
				$("#tt").datagrid("load", {
					queryString : value
				});
			}
			$('#tt').datagrid({
				rowStyler : function(index,row){
					if(row.iou =="借出"){
						return 'background-color:#cccccc' 
						}
				}
			})
		</script>
	</div>
</div>