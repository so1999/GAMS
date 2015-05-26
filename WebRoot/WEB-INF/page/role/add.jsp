<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<script type="text/javascript">
	$(document).ready(function() {
		inputfocus();
	});
	function getChecked() {
		var nodes = $('#tt').tree('getChecked');
		if (null != nodes && nodes.length > 0) {
			var str = new Array();
			for ( var i = 0; i < nodes.length; i++) {
				if (nodes[i].attributes) {
					//	alert(nodes[i].attributes.attr);
					str.push(nodes[i].attributes.attr + "#" + nodes[i].id);
				}
			}
			//alert(str.toString());
			return str.toString();
		}
		return null;
	}
	function add() {
		if (checkNull("#roleName")) {
			var treeVal = getChecked();
			if (null != treeVal) {
				$("#treeVal").val(treeVal);
			} else {
				promotdialog("请选角色拥有的栏目及功能");
				return false;
			}
			save("#roleform", "role/save.jspx", "role/index.jspx");
		}
	}
</script>
<%--编辑角色信息的页面  class="easyui-layout" fit="true"    data-options="region:'center'"--%>
<form id="roleform" style="padding: 10px;">
	<div style="width: 900px; height: 400px;">
		<input type="hidden" name="treeVal" id="treeVal" />
		<%-- fit="true" data-options="region:'east',border:false"--%>
		<div style="width: 570px; height: 100%; float: left; overflow: auto;">
			<div class="easyui-panel" title="角色信息" fit="true">
				<table style="padding: 20px;">
				<input type="hidden" class="x-form-text" name="id"
							value="${role.id}"  />
					<%--<tr>
						<td>角色编号</td>
						<td><input type="text" class="x-form-text" name="id"
							value="${role.id}" /></td>
					</tr> --%>
					<tr>
						<td>角色名称<span style="color: red;">*</span></td>
						<td><input type="text" class="x-form-text" name="roleName" id="roleName"
							value="${role.roleName}" /></td>
					</tr>
					<tr>
						<td>角色描述</td>
						<td><textarea cols="50" rows="5" class="x-form-text"
								name="roleDesc">${role.roleDesc}</textarea></td>
					</tr>
					<tr>
						<td colspan="2">
							<fieldset>
								<legend>说明及注意事项</legend>
								<ul style="margin-left: 30px;">
									<li>在创建角色时，必须选择栏目信息</li>
									<li>请先填写角色信息</li>
									<li>再选择功能信息</li>
									<li>关于功能信息的介绍如下：</li>
									<li>第一层节点为顶级栏目信息</li>
									<li>第二层节点为二级栏目信息</li>
									<li>第三层节点为二级栏目下的功能节点</li>
								</ul>
							</fieldset>
						</td>
					</tr>
				</table>

			</div>
		</div>
		<div style="width: 300px; height: 100%; float: left; overflow: auto;">
			<div class="easyui-panel" title="栏目及功能信息" fit="true">
				<ul id="tt" style="padding: 20px;" class="easyui-tree"
					data-options="url:'module/funcModule/${role.id}.jspx',animate:true,checkbox:true,cascadeCheck:false,lines:true">
				</ul>
			</div>
		</div>
	</div>
	<div style="clear: both;"></div>
	<%-- data-options="region:'south',border:false" --%>
	<div style="width: 900px; height: 80px;">
		<div style="width: 200px; height: 30px; margin: 0 auto;">
			<auth:a onclick="add()" iconCls="easyui-icon-save"
				funcSign="role.save" cssClass="easyui-linkbutton" text="提交" />
			<auth:a onclick="addTab('role/index.jspx')" funcSign="role.back"
				iconCls="easyui-icon-back" cssClass="easyui-linkbutton" text="返回" />

		</div>
	</div>
</form>
