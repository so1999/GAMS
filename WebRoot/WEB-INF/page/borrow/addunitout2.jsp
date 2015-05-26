<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<div class="easyui-layout" data-options="fit:true">
	<div title="借出Unit信息" style="width: 900px; height: 400px;"
		data-options="region:'center'">
		<form id="funcform" style="padding: 20px;">
			<input type="hidden" name="bid" /> <input type="hidden" name="cc"
				id="cc" />
			<table style="width: 900px;">
				<tr>
					<td><input type="hidden" name="id" id="id" value="${ut.id}" /></td>
				</tr>
				<tr>
					<td style="color:blue;font-size:20px">资财编号<span style="color: red;">*</span></td>
					<td><input type="text" class="x-form-text" id="ioutno"
						name="ioutno" style="width: 177px;" /></td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td>名称<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="ioutname"
						name="ioutname" style="width: 177px;" readonly="readonly"/>
					</td>
					<td>规格<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="ioutspec"
						name="ioutspec" style="width: 177px;" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>Charger<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="ioutcharger"
						name="ioutcharger"  style="width: 177px;" readonly="readonly"/>
					</td>
					<td>Place<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="ioutplace"
						name="ioutplace" style="width: 177px;" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>状态<span style="color: red;">*</span>
					</td>
					<td><select id="ioutstatus" class="x-form-text" name="ioutstatus"
						style="width: 179px;">
							<option value="内部借出">内部借出</option>
							<option value="外部借出">外部借出</option>
					</select>
					</td>
					<td>借用人<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="ioperson"
						name="ioperson"  style="width: 177px;"/>
					</td>
				</tr>
				<tr>
					<td>归还<span style="color: red;">*</span>
					</td>
					<td><select id="ioyon" class="x-form-text" name="ioyon"
						style="width: 179px;">
							<option value="是">是</option>
							<option value="否">否</option>
					</select>
					</td>
					<td>借出日期</td>
					<td><input id="dd" type="text" class="easyui-datebox"
						data-options="formatter:myformatter,parser:myparser"
						name="ioindate" style="width: 179px;"></td>
				</tr>
				<tr>
					<td>Remark</td>
					<td colspan="2"><textarea cols="40" rows="4"
							class="x-form-text" name="ioremark"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center"><input type="button"
						class="d-button d-state-highlight" value="提交" onclick="saveAddout()"
						style="width: 80px;" id="button1" /><input type="button"
						class="d-button d-state-highlight" value="返回"
						onclick="addTab('borr/listunit')" style="width: 80px;" /></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$("#ioutno")
				.blur(
						function() {
							var str = document.getElementById('ioutno').value;
							$("#cc").combobox(
											{
												url : "borr/getUnit.jspx?utno="+ str,
												onLoadSuccess : function() {
													document.getElementById("id").value = null;
													document.getElementById("ioutname").value = null;
													document.getElementById("ioutcharger").value = null;
													document.getElementById("ioutplace").value = null;
													document.getElementById("ioutspec").value = null;
													document.getElementById("button1").disabled = false;
													var data = $("#cc").combobox("getData");
													if (data.length > 0) {
														document.getElementById("id").value = data[1].id;
														document.getElementById("ioutname").value = data[1].utname;
														document.getElementById("ioutcharger").value = data[1].utcharger;
														document.getElementById("ioutplace").value = data[1].utplace;
														document.getElementById("ioutspec").value = data[1].utspec;
														$("#ioutstatus").combobox("setValue",data[1].utstatus);
														var str = $("#ioutstatus").combobox("getValue");
														if (str.indexOf("在库") < 0) {
															alert("请注意！已借出！");
															document
																	.getElementById("button1").disabled = 'disabled';
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

		$("#ioutstatus").combobox({

		});
		$("#ioyon").combobox({
			panelHeight : 'auto',
		});

		function saveAddout() {
			if (checkNull("#ioutno")&&checkNull("#ioperson")&&checkStatus("#ioutstatus")) {
				save('#funcform', 'borr/saveborrunit.jspx', 'borr/listunit.jspx');
			}
		}
	</script>
</div>
