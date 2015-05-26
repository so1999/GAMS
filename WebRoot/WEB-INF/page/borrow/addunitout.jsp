<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<div class="easyui-layout" data-options="fit:true">
	<div title="登记Unit借出信息" style="width: 900px; height: 400px;"
		data-options="region:'center'">
		<form id="funcform" style="padding: 20px;">
			<input type="hidden" name="bid"  />
			<input type="hidden" name="id"  value="${ut.id}" />
			<table style="width: 900px;">
				<tr>
					<td>资财编号<span style="color: red;">*</span></td>
					<td><input type="text" class="x-form-text" id="ioutno"
						name="ioutno" value="${ut.utno}" style="width: 177px;" readonly="readonly"/>
					</td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td>名称<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="ioutname"
						name="ioutname" value="${ut.utname}" style="width: 177px;" readonly="readonly"/>
					</td>
					<td>规格<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="ioutspec"
						name="ioutspec" value="${ut.utspec}" style="width: 177px;" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>Charger<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="ioutcharger"
						name="ioutcharger" value="${ut.utcharger}" style="width: 177px;" readonly="readonly"/>
					</td>
					<td>Place<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="ioutplace"
						name="ioutplace" value="${ut.utplace}" style="width: 177px;" readonly="readonly"/>
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
						style="width: 80px;" /><input type="button"
						class="d-button d-state-highlight" value="返回"
						onclick="addTab('borr/listunit')" style="width: 80px;" /></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
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
			if (checkNull("#ioperson")) {
				save('#funcform', 'borr/saveborrunit.jspx','borr/listunit');
			}
		}
	</script>
</div>
