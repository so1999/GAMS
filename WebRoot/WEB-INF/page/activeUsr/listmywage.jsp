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
			field : "tId",
			title : "工号",
			width : 120,
			align : 'center'
		}, {
			field : "tName",
			title : "姓名",
			width : 120,
			align : 'center'
		}, {
			field : "wDate",
			title : "月份",
			width : 120,
			align : 'center'
		}, {
			field : "wBasic",
			title : "基本工资",
			width : 120,
			align : 'center'
		}, {
			field : "wBonus",
			title : "奖金",
			width : 120,
			align : 'center'
		}, {
			field : "wTotal",
			title : "合计",
			width : 120,
			align : 'center'
		}, {
			field : "remark",
			title : "备注",
			width : 200,
			align : 'center'
		} ] ];
		datagrid("#tt", "工资管理", "acusr/mywage.jspx", "#tb", "id", columns,
				fcolumn);
	});
</script>

<table id="tt">
</table>
<div id="tb" style="height: 28px; display: none;">
	<div style="margin: 2px;">
		<span style="float: right;"> <select id="year" name="year">
				<option value="">--请选择年--</option>
							<option value="2013">2013</option>
							<option value="2014">2014</option>
							<option value="2015">2015</option>
							<option value="2016">2016</option>
							<option value="2017">2017</option>
							<option value="2018">2018</option>
							<option value="2019">2019</option>
							<option value="2020">2020</option>
							<option value="2021">2021</option>
							<option value="2022">2022</option>
		</select>年 <select id="month" name="month">
				<option value="">--请选择月--</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
		</select>月&nbsp;&nbsp;<a href="javascript:void(0)" onclick="doSearch()"
			class="easyui-linkbutton">查询</a>
		</span>
		<script>
			function doSearch(value) {
				$("#tt").datagrid("load", {
					year : $("input[name='year']").val(),
					month : $("input[name='month']").val()
				});
			}
		</script>
	</div>
	<script type="text/javascript">
		$("#year").combobox({
			panelHeight : 'auto'
		});
		$("#month").combobox({
			panelHeight : 'auto'
		});
	</script>
</div>