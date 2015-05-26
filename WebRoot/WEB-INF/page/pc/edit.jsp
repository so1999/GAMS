<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<div class="easyui-layout" data-options="fit:true">
	<div title="编辑PC信息" style="width: 900px; height: 400px;"
		data-options="region:'center'">
		<form id="funcform" style="padding: 20px;">
			<input type="hidden" name="id" value="${pc.id}" />
			<table style="width: 900px;">
				<tr>
					<td>Model<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" 
						name="pcmodel" value="${pc.pcmodel}" style="width: 177px;" disabled="disabled"/>
					</td>
					<td>S/N<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="pcsn"
						name="pcsn" value="${pc.pcsn}" style="width: 177px;" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td>CPU<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="pccpu"
						name="pccpu" value="${pc.pccpu}" style="width: 177px;" disabled="disabled"/>
					</td>
					<td>Memory<span style="color: red;">*</span>
					</td>
					<td><select id="pcmemory" class="x-form-text" name="pcmemory"
						style="width: 179px;" disabled="disabled">
							<option value="2G">2G</option>
							<option value="4G">4G</option>
							<option value="8G">8G</option>
							<option value="16G">16G</option>
					</select>
					</td>
					<td>HDD<span style="color: red;">*</span>
					</td>
					<td><select id="pchdd" class="x-form-text" name="pchdd"
						style="width: 183px;" disabled="disabled">
							<option value="128G">128G</option>
							<option value="256G">256G</option>
							<option value="320G">320G</option>
							<option value="500G">500G</option>
							<option value="750G">750G</option>
							<option value="1T">1T</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>用途<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="pcuse"
						name="pcuse" value="${pc.pcuse}" style="width: 177px;" />
					</td>
					<td>位置<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="pcplace"
						name="pcplace" value="${pc.pcplace}" style="width: 177px;" />
					</td>
					<td>状态<span style="color: red;">*</span>
					</td>
					<td><select id="pcstatus" class="x-form-text" name="pcstatus"
						style="width: 183px;">
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
							class="x-form-text" name="pcremark">${pc.pcremark}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center"><input type="button"
						class="d-button d-state-highlight" value="提交" onclick="updatePc()"
						style="width: 80px;" /><input type="button"
						class="d-button d-state-highlight" value="返回"
						onclick="addTab('pc/list.jspx')" style="width: 80px;" /></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		var pcmemory = '${pcmemory}';
		$("#pcmemory").combobox({
			panelHeight : 'auto',
			onLoadSuccess : function() {
				$("#pcmemory").combobox("setValue", '${pc.pcmemory}');
			}
		});
		var pchdd = '${pchdd}';
		$("#pchdd").combobox({
			panelHeight : 'auto',
			onLoadSuccess : function() {
				$("#pchdd").combobox("setValue", '${pc.pchdd}');
			}
		});
		var pcstatus = '${pcstatus}';
		$("#pcstatus").combobox({
			panelHeight : 'auto',
			onLoadSuccess : function() {
				$("#pcstatus").combobox("setValue", '${pc.pcstatus}');
			}
		});
		function updatePc(){
			save('#funcform', 'pc/update.jspx', 'pc/list.jspx');
		}
	</script>
</div>
