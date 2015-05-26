<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<script type="text/javascript">
	$(document).ready(function() {
		inputfocus();
		$("#moduleSign").focus(function() {
			$("#sign").html("");
		});
		$("#moduleSign").blur(function() {
			workload.remote(this, "#sign", "module/check.jspx");
			//remoteCheck("#sign", "module/check.jspx");
		});
	});
	function saveModule() {
		if (checkNull("#moduleName") && checkNull("#moduleHref")
				&& checkNull("#moduleSign")) {
			save('#moduleform', 'module/save.jspx', 'module/index.jspx');
		}
	}
</script>
<%--编辑栏目信息的页面 --%>
<div class="easyui-layout" data-options="fit:true">
	<div title="编辑栏目信息" style="width: 660px; height: 400px;"
		data-options="region:'center'">
		<fieldset style="width: 550px; margin: 20px;">
			<legend>提示</legend>
			<ul style="margin-left: 30px;">
				<li>目前本系统最大只支持二级栏目</li>
				<li>带有<span style="color: red; font-weight: bolder;">*</span>的为必填项
				</li>
			</ul>
		</fieldset>
		<form id="moduleform" style="margin: 20px;">
			<table style="width: 600px;">
				<input type="hidden" class="x-form-text" name="id"
					value="${module.id}" />
				<%--<tr>
					<td>栏目编号</td>
					<td><input type="hidden" class="x-form-text" name="id"
						value="${module.id}" /></td>
				</tr> --%>
				<tr>
					<td>栏目名称<span style="color: red;">*</span></td>
					<td><input type="text" class="x-form-text" name="moduleName"
						id="moduleName" value="${module.moduleName}" /></td>
					<td style="width: 30px;">&nbsp;</td>
					<td>栏目Href<span style="color: red;">*</span></td>
					<td><input type="text" class="x-form-text" name="moduleHref"
						id="moduleHref" value="${module.moduleHref}" /></td>
					<td style="width: 30px;">&nbsp;</td>
				</tr>
				<tr>
					<td>栏目标识<span style="color: red;">*</span></td>
					<td><input type="text" class="x-form-text" name="moduleSign"
						id="moduleSign" value="${module.moduleSign}" /></td>
					<td style="width: 60px;"><span id="sign"></span></td>
					<td>栏目排序</td>
					<td><input type="text" class=" easyui-numberbox x-form-text" required="true"
						name="moduleOrder" value="${module.moduleOrder}" /></td>
					<td style="width: 30px;">&nbsp;</td>
				</tr>
				<%-- <input name="parentId" class="easyui-combobox"
						data-options="url:'module/getParent.jspx',valueField:'id',textField:'moduleName'"
						tyle=" width: 200px;" /> --%>
				<tr>
					<td>上级栏目</td>
					<td><input name="parentId" value="${module.parentId}"
						class="easyui-combotree"
						data-options="url:'module/getParent.jspx',valueField:'id',textField:'moduleName'"
						tyle=" width: 200px;" /></td>
					<td colspan="5" style="color: green;">提示：默认父级栏目的编号为0</td>
				</tr>
				<tr>
					<td>栏目描述</td>
					<td colspan="5"><textarea cols="60" rows="4"
							class="x-form-text" name="moduleDesc">${module.moduleDesc}</textarea></td>
				</tr>
				<tr>
					<td colspan="2"><auth:a onclick="saveModule()"
							iconCls="easyui-icon-add" cssClass="easyui-linkbutton" text="提交" />
						<auth:a onclick="addTab('module/index.jspx')"
							iconCls="easyui-icon-back" cssClass="easyui-linkbutton" text="返回" />
				</tr>
			</table>
		</form>
	</div>
	<div style="width: 260px; height: 400px;"
		data-options="region:'east',border:false">
		<div class="easyui-panel" title="栏目预览" fit="true">
			<ul class="easyui-tree"
				data-options="url:'module/tree.jspx',animate:true,lines:true">
			</ul>
		</div>
	</div>
</div>