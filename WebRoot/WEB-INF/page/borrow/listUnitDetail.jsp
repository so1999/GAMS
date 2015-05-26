<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<div style="width: 1000px; height: 460px;">
	<script type="text/javascript">
		$(function() {
			var fcolumn = [ [ {
				field : "id",
			} ] ];
			var columns = [ [ {
				field : "ioutno",
				title : "资财编号",
				width : 150,
				align : 'center'
			}, {
				field : "iou",
				title : "借出/归还",
				width : 100,
				align : 'center',
			}, {
				field : "ioutname",
				title : "名称",
				width : 160,
				align : 'center',
			}, {
				field : "ioutspec",
				title : "规格",
				width : 150,
				align : 'center'
			}, {
				field : "ioutcharger",
				title : "CHARGER",
				width : 120,
				align : 'center'
			}, {
				field : "ioperson",
				title : "借用人",
				width : 120,
				align : 'center'
			}, {
				field : "ioindate",
				title : "借出/归还日期",
				width : 80,
				align : 'center'
			}, {
				field : "ioremark",
				title : "REMARK",
				width : 100,
				align : 'center'
			}, {
				field : "ioopperson",
				title : "操作人",
				width : 100,
				align : 'center'
			}, {
				field : "ioopdate",
				title : "操作时间",
				width : 100,
				align : 'center'
			} ] ];
			datagrid("#tt", "", "borr/listUnitBorr.jspx", "#tc", "id", columns,
					fcolumn);
		});
	</script>
	<table id="tt">
	</table>
	<script>
		$('#tt').datagrid({
			rowStyler : function(index, row) {
				if (row.iou == "借出") {
					return 'background-color:#cccccc'
				}
			}
		})
	</script>
</div>