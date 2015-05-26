<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<%--编辑用户信息的页面 --%>
<div class="easyui-layout" data-options="fit:true">
	<div title="编辑用户信息" style="width: 660px; height: 400px;"
		data-options="region:'center'">
		<form id="funcform" style="padding: 20px;">
			<input type="hidden" class="x-form-text" name="id" value="${usr.id}" />
			<table style="width: 600px;">
				<%--<tr>
					<td>用户帐号<span style="color: red;">*</span></td>
					<td colspan="3"><input type="text" class="x-form-text" id="id"
						name="id" value="${usr.id}" /><span id="usrId"></span></td>
				</tr>--%>
				<tr>
					<td>用户名<span style="color: red;">*</span>
					</td>
					<td><input type="text" class="x-form-text" id="usrName"
						name="usrName" value="${usr.usrName}" /><span id="usr_name"></span>
					</td>
					<td>账户状态</td>
					<td><select id="combostatus" class="x-form-text" name="status"
						style="width: 90px;">
							<option value="1">启用</option>
							<option value="0">禁用</option>
					</select>
					</td>
					<%--<input type="text" class="easyui-combobox x-form-text"
						name="status" value="${usr.status}" />  --%>
				</tr>
				<tr>
					<td>部&nbsp;&nbsp;&nbsp;门<span style="color: red;">*</span>
					</td>
					<td><input id="part" class="easyui-combobox" name="part"
						style="width: 144px;" value="${usr.part}"/>
					<td>部门ID<span style="color: red;">*</span>
					</td>
					<td><input id="partid" class="easyui-combobox" name="partid"
						style="width: 90px;" disabled="disabled" value="${usr.partid}" />
				</tr>
				<tr>
					<td>分配角色</td>
					<td><input id="roleIds" class="x-form-text" name="roleIds" />
					</td>
				</tr>
				<tr>
					<td>备注</td>
					<td colspan="3"><textarea cols="60" rows="4"
							class="x-form-text" name="remark">${usr.remark}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2"><auth:a onclick="saveUsr()"
							iconCls="easyui-icon-add" cssClass="easyui-linkbutton" text="提交" />
						<auth:a onclick="addTab('usr/index.jspx')"
							iconCls="easyui-icon-back" cssClass="easyui-linkbutton" text="返回" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			inputfocus();
			$("#usrName").focus(function() {
				$("#usr_name").html("");
			});
			var usr = null;
			$("#usrName").blur(function() {
				if ($(this).val().length <= 0) {
					return false;
				} else if ($(this).val() == usr) {
					return false;
				}
				$.post("usr/check.jspx", {
					usrId : $(this).val()
				}, function(data) {
					usr = data.usr;
					if (data.success) {
						$("#usr_name").html(data.msg).css({
							"color" : "green",
							"padding-left" : "4px"
						});
					} else {
						$("#usr_name").html(data.msg).css({
							"color" : "red",
							"padding-left" : "4px"
						});
					}
				}, "json");
			});
			var roleIds = '${roleIds}';
			var status = '${usr.status}';
			$("#combostatus").combobox({
				panelHeight : 'auto',
				onLoadSuccess : function() {
					if ("" != status) {
						$('#combostatus').combobox('setValues', status);
					}
				}
			});
			$('#roleIds').combobox({
				url : 'usr/getRoles.jspx',
				valueField : 'id',
				textField : 'roleName',
				multiple : true,
				panelHeight : 'auto',
				onLoadSuccess : function() {
					if ("" != roleIds) {
						var x = roleIds.split(",");
						$('#roleIds').combobox('setValues', x);
					}
				}
			});
		});
		$("#part").combobox({
			url : "part/getpart.jspx",
			valueField : 'part',
			textField : 'part',
			multiple : false,
			editable : false,
			panelHeight : '130',
			onSelect : function(rec) {
				var url = "part/getpartid.jspx?part=" + rec.part;
				$('#partid').combobox('clear', url);
				$('#partid').combobox('reload', url);

			}
		});
		$("#partid").combobox({
			valueField : 'partid',
			textField : 'partid',
			multiple : false,
			editable : false,
			panelHeight : '130',
			onLoadSuccess : function(date) {
				var data = $('#partid').combobox('getData');
				if (data.length > 0) {
					$('#partid').combobox('select', data[1].partid);
				}
			},
		});
		function saveUsr() {
			if (checkNull("#usrName")) {
				save('#funcform', 'usr/update.jspx', 'usr/index.jspx');
			}
		}
	</script>
</div>