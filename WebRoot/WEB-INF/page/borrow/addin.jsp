<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<div class="easyui-layout" data-options="fit:true">
	<div title="归还PC信息" style="width: 900px; height: 400px;"
		data-options="region:'center'">
		<fieldset style="width: 550px; margin: 20px;">
			<legend>提示</legend>
			<ul style="margin-left: 30px;">
				<li>归还PC请先输入资材编号</li>
				<li>带有<span style="color: red; font-weight: bolder;">*</span>的为必填项
				</li>
			</ul>
		</fieldset>
		<form id="funcform" style="padding: 20px;">
			<input type="hidden" name="bid" /> <input type="hidden" name="cc"
				id="cc" />
			<table style="width: 900px;">
				<tr>
					<td><input type="hidden" name="id" id="id" value="${pc.id}" /></td>
				</tr>
				<tr>
					<td style="color:blue;font-size:20px">资财编号<span style="color: red;">*</span></td>
					<td><input type="text" class="x-form-text" id="iopcno"
						name="iopcno" style="width: 177px;" /></td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td>Model</td>
					<td><input type="text" class="x-form-text" id="iopcmodel"
						name="iopcmodel" style="width: 177px;" readonly="readonly" /></td>
					<td>S/N</td>
					<td><input type="text" class="x-form-text" id="iopcsn"
						name="iopcsn" style="width: 177px;" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>Charger</td>
					<td><input type="text" class="x-form-text" id="iopccharger"
						name="iopccharger" style="width: 177px;" readonly="readonly" /></td>
					<td>Place<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="iopcplace"
						name="iopcplace" style="width: 177px;" /></td>
				</tr>
				<tr>
					<td>状态</td>
					<td><input id="iopcstatus" class="easyui-validatebox"
						name="iopcstatus" disabled="disabled" style="width:175px" /></td>
					<td>归还人<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="ioperson"
						name="ioperson" style="width: 177px;" /></td>
				</tr>
				<tr>
					<td>归还<span style="color: red;">*</span>
					</td>
					<td><select id="ioyon" class="x-form-text" name="ioyon"
						style="width: 179px;">
							<option value="是">是</option>
							<option value="否">否</option>
					</select></td>
					<td>归还日期<span style="color: red;">*</span></td>
					<td><input id="dd" type="text" class="easyui-datebox"
						data-options="formatter:myformatter,parser:myparser"
						name="ioindate" style="width: 179px;"></td>
				</tr>
				<tr>
					<td>Remark</td>
					<td colspan="2"><textarea cols="40" rows="4"
							class="x-form-text" name="ioremark"></textarea></td>
				</tr>
				<tr>
					<td colspan="3" align="center"><input type="button"
						class="d-button d-state-highlight" value="提交"
						onclick="saveAddin()" style="width: 80px;" id="button1" /><input
						type="button" class="d-button d-state-highlight" value="返回"
						onclick="addTab('borr/list.jspx')" style="width: 80px;" /></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$("#iopcno")
				.blur(
						function() {
							var str = document.getElementById('iopcno').value;
							$("#cc")
									.combobox(
											{
												url : "borr/getModel.jspx?pcno="
														+ str,
												onLoadSuccess : function() {
													document
															.getElementById("id").value = null;
													document
															.getElementById("iopcmodel").value = null;
													document
															.getElementById("iopcsn").value = null;
													document
															.getElementById("iopccharger").value = null;
													document
															.getElementById("iopcplace").value = null;
													document
															.getElementById("iopcstatus").value = null;
													document
															.getElementById("button1").disabled = false;
													var data = $("#cc")
															.combobox("getData");
													if (data.length > 0) {
														document
																.getElementById("id").value = data[1].id;
														document
																.getElementById("iopcmodel").value = data[1].pcmodel;
														document
																.getElementById("iopcsn").value = data[1].pcsn;
														document
																.getElementById("iopccharger").value = data[1].pccharger;
														document
																.getElementById("iopcstatus").value = data[1].pcstatus;
														var str = document
																.getElementById('iopcstatus').value;
														if (str.indexOf("在库") >= 0) {
															alert("请注意！机台在库不能归还操作！")
															document
																	.getElementById("button1").disabled = 'disabled'
														}
													}
												}
											});
						});
		function myformatter(date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
					+ (d < 10 ? ('0' + d) : d);
		}
		function myparser(s) {
			if (!s)
				return new Date();
			var ss = (s.split('-'));
			var y = parseInt(ss[0], 10);
			var m = parseInt(ss[1], 10);
			var d = parseInt(ss[2], 10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
				return new Date(y, m - 1, d);
			} else {
				return new Date();
			}
		}

		$("#ioyon").combobox({});

		function saveAddin() {
			if (checkNull("#iopcno") && checkNull("#iopcplace")
					&& checkNull("#ioperson")) {
				save('#funcform', 'borr/savein.jspx', 'borr/list.jspx');
			}
		}
	</script>
</div>
