<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<div class="easyui-panel" title="PC统计" width="100%" height="100%" id="didv" align="center">
	<table id="tbd" border="0" width="100%" height="100%">
		<tr>
			<td>部门</td>
			<td>PC 在库</td>
			<td>内部借出</td>
			<td>外部借出</td>
			<td>外部借入</td>
			<td>保管总数</td>
		</tr>
		<tr>
			<td>H/W</td>
			<td><div id="hwtotalin" style=" color:blue;font-size:20px"></div></td>
			<td><div id="hwtotalbiout" style=" color:blue;font-size:20px"></div></td>
			<td><div id="hwtotalbout" style=" color:blue;font-size:20px"></div></td>
			<td><div id="hwtotalbin" style=" color:blue;font-size:20px"></div></td>
			<td><div id="hwtotalis" style=" color:blue;font-size:20px"></div></td>
		</tr>
		<tr>
			<td>S/W</td>
			<td><div id="swtotalin" style="color:blue;font-size:20px"></div></td>
			<td><div id="swtotalbiout" style=" color:blue;font-size:20px"></div></td>
			<td><div id="swtotalbout" style="color:blue;font-size:20px"></div></td>
			<td><div id="swtotalbin" style=" color:blue;font-size:20px"></div></td>
			<td><div id="swtotalis" style="color:blue;font-size:20px"></div></td>
		</tr>
		<tr>
			<td>QRE</td>
			<td><div id="qretotalin" style="color:blue;font-size:20px"></div></td>
			<td><div id="qretotalbiout" style=" color:blue;font-size:20px"></div></td>
			<td><div id="qretotalbout" style="color:blue;font-size:20px"></div></td>
			<td><div id="qretotalbin" style="color:blue;font-size:20px"></div></td>
			<td><div id="qretotalis" style="color:blue;font-size:20px"></div></td>
		</tr>
		<tr>
			<td>CFM</td>
			<td><div id="cfmtotalin" style="color:blue;font-size:20px"></div></td>
			<td><div id="cfmtotalbiout" style=" color:blue;font-size:20px"></div></td>
			<td><div id="cfmtotalbout" style="color:blue;font-size:20px"></div></td>
			<td><div id="cfmtotalbin" style="color:blue;font-size:20px"></div></td>
			<td><div id="cfmtotalis" style="color:blue;font-size:20px"></div></td>
		</tr>
		<tr>
			<td>ME</td>
			<td><div id="metotalin" style="color:blue;font-size:20px"></div></td>
			<td><div id="metotalbiout" style=" color:blue;font-size:20px"></div></td>
			<td><div id="metotalbout" style="color:blue;font-size:20px"></div></td>
			<td><div id="metotalbin" style="color:blue;font-size:20px"></div></td>
			<td><div id="metotalis" style="color:blue;font-size:20px"></div></td>
		</tr>
	</table>
</div>

<div id="tb" style="height: 28px; display: none; overflow:hidden">
	<div style="margin: 2px;">
		<script>
			$("#didv").ready(function() {
				$.post("borr/countpc.jspx", function(data) {
					$("#hwtotalin").text(data.hwtotalin);
					$("#hwtotalis").text(data.hwtotalis);
					$("#hwtotalbin").text(data.hwtotalbin);
					$("#hwtotalbout").text(data.hwtotalbout);
					$("#hwtotalbiout").text(data.hwtotalbiout);
					$("#swtotalin").text(data.swtotalin);
					$("#swtotalis").text(data.swtotalis);
					$("#swtotalbin").text(data.swtotalbin);
					$("#swtotalbout").text(data.swtotalbout);
					$("#swtotalbiout").text(data.swtotalbiout);
					$("#cfmtotalin").text(data.cfmtotalin);
					$("#cfmtotalis").text(data.cfmtotalis);
					$("#cfmtotalbin").text(data.cfmtotalbin);
					$("#cfmtotalbout").text(data.cfmtotalbout);
					$("#cfmtotalbiout").text(data.cfmtotalbiout);
					$("#qretotalin").text(data.qretotalin);
					$("#qretotalis").text(data.qretotalis);
					$("#qretotalbin").text(data.qretotalbin);
					$("#qretotalbout").text(data.qretotalbout);
					$("#qretotalbiout").text(data.qretotalbiout);
					$("#metotalin").text(data.metotalin);
					$("#metotalis").text(data.metotalis);
					$("#metotalbin").text(data.metotalbin);
					$("#metotalbout").text(data.metotalbout);
					$("#metotalbiout").text(data.metotalbiout);
				}, "json");
			});
		</script>
	</div>
</div>