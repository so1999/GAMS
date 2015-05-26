<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<div class="easyui-layout" data-options="fit:true">
	<div title="登录UNIT信息" style="width: 780px; height: 300px;"
		data-options="region:'center'">
		<form id="funcform" style="padding: 20px;">
			<input type="hidden" name="id" value="${ut.id}" />
			<table style="width: 900px;">
				<tr>
					<td>名称<span style="color: red;">*</span>
					</td>
					<td><select id="utname" class="x-form-text" name="utname"
						style="width: 179px;">
							<option value="CPU">CPU</option>
							<option value="HDD">HDD</option>
							<option value="MEMORY">MEMORY</option>
							<option value="MB">MB</option>
							<option value="OTHER">OTHER</option>
					</select>
					</td>
					<td>规格<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="utspec"
						name="utspec" value="${ut.utspec}" style="width: 177px;" />
					</td>
				</tr>
				<tr>
					<td>用途<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="utuse"
						name="utuse" value="${ut.utuse}" style="width: 177px;" />
					</td>
					<td>位置<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="utplace"
						name="utplace" value="${ut.utplace}" style="width: 177px;" />
					</td>
				</tr>
				<tr>
					<td>状态<span style="color: red;">*</span>
					</td>
					<td><select id="utstatus" class="x-form-text" name="utstatus"
						style="width: 179px;">
							<option value="在库">在库</option>
							<option value="内部借出">内部借出</option>
							<option value="外部借出">外部借出</option>
							<option value="外部借入">外部借入</option>
							<option value="报废">报废</option>
					</select>
					</td>
				<tr>
					<td>Remark</td>
					<td colspan="2"><textarea cols="40" rows="4"
							class="x-form-text" name="utremark">${ut.utremark}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center"><input type="button"
						class="d-button d-state-highlight" value="提交" onclick="saveUnit()"
						style="width: 80px;" /><input type="button"
						class="d-button d-state-highlight" value="返回"
						onclick="addTab('unit/list.jspx')" style="width: 80px;" /></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		var utname = '${utname}';
		$("#utname").combobox({
			panelHeight : 'auto',
			onLoadSuccess : function() {
				$("#utname").combobox("setValue", utname);
			}
		});
		var utstatus = '${utstatus}';
		$("#utstatus").combobox({
			panelHeight : 'auto',
			onLoadSuccess : function() {
				$("#utstatus").combobox("setValue", utstatus);
			}
		});
		function saveUnit() {
			if (checkNull("#utspec")) {
				save('#funcform', 'unit/save.jspx', 'unit/list.jspx');
			}
		}
	</script>
</div>
