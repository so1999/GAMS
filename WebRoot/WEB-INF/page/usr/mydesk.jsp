<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="auth" uri="authorize"%>
<style type="text/css">
#usrinfo {
	
}

#usrinfo li {
	padding-left: 20px;
	list-style: none;
	border-bottom: 1px solid green;
	height: 30px;
	line-height: 30px;
}
</style>
<div class="easyui-layout" fit="true">
	<div data-options="region:'center',border:false" fit="true">
		<div class="easyui-layout" fit="true" style="height: 240px;">
			<div data-options="region:'west',border:false" style="width: 410px;">
				<div class="easyui-panel" title="个人信息"
					style="width: 400px; height: 209px; padding: 16px;">
					<div
						style="font-size: 16px; margin: 6px; color: #19446A; font-weight: bold;">欢迎使用本系统</div>
					<div
						style="margin: 10px; font-size: 16px; font-weight: 600; font-family: '楷体';">
						<div>
							姓&nbsp;&nbsp;&nbsp;&nbsp;名：<%=request.getSession().getAttribute("uname")%></div>
						<div>
							部&nbsp;&nbsp;&nbsp;&nbsp;门：<%=request.getSession().getAttribute("part")%></div>
					</div>
				</div>
				<div class="easyui-panel" title="PC统计"
					style="width: 400px; height: 209px;" id="didv">
					<table id="tbd" border="0"  width="100%"  height="100%">
						<tr style="background-color:  	#BC8F8F"><td>部门</td><td>PC 在库</td><td>内部借出</td><td>外部借出</td><td>外部借入</td><td>保管总数</td></tr>
						<tr style="background-color: #cccccc">
							<td>H/W</td>
							<td><div id = "hwtotalin" style=" color:blue;font-size:16px"></div></td>
							<td><div id = "hwtotalbiout" style=" color:blue;font-size:16px"></div></td>
							<td><div id = "hwtotalbout" style=" color:blue;font-size:16px"></div></td>
							<td><div id = "hwtotalbin" style=" color:blue;font-size:16px"></div></td>
							<td><div id = "hwtotalis" style=" color:blue;font-size:16px"></div></td>
						</tr>
						<tr>
							<td>S/W</td>
							<td><div id = "swtotalin" style="color:blue;font-size:16px"></div></td>
							<td><div id = "swtotalbiout" style=" color:blue;font-size:16px"></div></td>
							<td><div id = "swtotalbout" style="color:blue;font-size:16px"></div></td>
							<td><div id = "swtotalbin" style=" color:blue;font-size:16px"></div></td>
							<td><div id = "swtotalis" style="color:blue;font-size:16px"></div></td>
						</tr>
						<tr style="background-color: #cccccc">
							<td>QRE</td>
							<td><div id = "qretotalin" style="color:blue;font-size:16px"></div></td>
							<td><div id = "qretotalbiout" style=" color:blue;font-size:16px"></div></td>
							<td><div id = "qretotalbout" style="color:blue;font-size:16px"></div></td>
							<td><div id = "qretotalbin" style="color:blue;font-size:16px"></div></td>
							<td><div id = "qretotalis" style="color:blue;font-size:16px"></div></td>
						</tr>
						<tr>
							<td>CFM</td>
							<td><div id = "cfmtotalin" style="color:blue;font-size:16px"></div></td>
							<td><div id = "cfmtotalbiout" style=" color:blue;font-size:16px"></div></td>
							<td><div id = "cfmtotalbout" style="color:blue;font-size:16px"></div></td>
							<td><div id = "cfmtotalbin" style="color:blue;font-size:16px"></div></td>
							<td><div id = "cfmtotalis" style="color:blue;font-size:16px"></div></td>
						</tr>
						<tr style="background-color: #cccccc">
							<td>ME</td>
							<td><div id = "metotalin" style="color:blue;font-size:16px"></div></td>
							<td><div id = "metotalbiout" style=" color:blue;font-size:16px"></div></td>
							<td><div id = "metotalbout" style="color:blue;font-size:16px"></div></td>
							<td><div id = "metotalbin" style="color:blue;font-size:16px"></div></td>
							<td><div id = "metotalis" style="color:blue;font-size:16px"></div></td>
						</tr>							
					</table>
				</div>
			</div>
			<div data-options="region:'center',border:false">
				<div class="easyui-panel" title="说明"
					style="width: 520px; height: 209px;">
					<span>资财编号说明：</span><br> <span>示例：HW01A000001</span><br>
					<span>1.前四位HW01为部门代码</span><br> <span>2.第五位字母代表资财类型</span><br>
					<span>&nbsp;&nbsp;&nbsp;&nbsp;A：PC, B:HDD, C:MEMORY, D:MB,
						E:CPU, X:OTHER</span><br> <span>3.后六位为递增的随机数</span><br> <br>
					<span>注意：</span><br> <span>使用本系统需要IE8或以上版本浏览器</span><br>
				</div>
			</div>
		</div>
	</div>
	<div data-options="region:'south',border:false">
		<div class="easyui-panel" title=" " style="height: auto;" fit="true">
			<div style="width: 840px; margin: 0 auto;"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
	//$("#didv").load('borr/countpc.jspx'); 
	$("#didv").ready(function() {
		$.post("borr/countpc.jspx", 
		function(data){
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
			},
		"json");
	});
</script>