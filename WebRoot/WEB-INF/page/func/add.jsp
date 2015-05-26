<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize"  prefix="auth"%>
<%--编辑功能信息的页面 --%>
<div class="easyui-layout" data-options="fit:true">
	<div title="编辑栏目信息" style="width: 660px; height: 400px;"
		data-options="region:'center'">
		<form id="funcform" style="padding: 20px;">
			<table style="width: 600px;">
				<input type="hidden" class="x-form-text" name="id"
					value="${func.id}" />
				<%--<tr>
					<td>功能编号</td>
					<td><input type="text" class="x-form-text" name="id"
						value="${func.id}" /></td>
				</tr> --%>
				<tr>
					<td>功能名称<span style="color: red;">*</span></td>
					<td><input type="text" class="x-form-text" name="funcName"
						id="funcName" value="${func.funcName}" /></td>
					<td>功能标识<span style="color: red;">*</span></td>
					<td><input type="text" class="x-form-text" name="funcSign"
						id="funcSign" value="${func.funcSign}" /><span id="sign"></span></td>
				</tr>
				<tr>
					<td>所属栏目<span style="color: red;">*</span></td>
					<td><input id="moduleTree" type="text" class="x-form-text"
						name="moduleId" value="${func.module.id}" /></td>
				</tr>
				<tr>
					<td>功能描述</td>
					<td colspan="3"><textarea cols="60" rows="4"
							class="x-form-text" name="funcDesc">${func.funcDesc}</textarea></td>
				</tr>
				<tr>
					<td colspan="2"><auth:a id="funcSave" funcSign="func.save"
							iconCls="easyui-icon-save" cssClass="easyui-linkbutton" text="提交" />
						<auth:a onclick="addTab('func/index.jspx')" funcSign="func.back"
							iconCls="easyui-icon-back" cssClass="easyui-linkbutton" text="返回" />
				</tr>
			</table>
		</form>
	</div>
	<div class="easyui-panel" title="栏目"
		style="width: 260px; height: 400px;" data-options="region:'east'">
		<ul class="easyui-tree"
			data-options="url:'module/funcModule/.jspx',animate:true,checkbox:true">
		</ul>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			inputfocus();
			var signVal = null;
			$("#funcSign").focus(function() {
				$("#funcSign").html("");
			});
			$("#funcSign").blur(function() {
				if ($(this).val() == signVal) {
					return false;
				} else if ($(this).val().length <= 0) {
					return false;
				}
				$.post("func/checkSign.jspx", {
					sign : $("#funcSign").val()
				}, function(data) {
					signVal = data.sign;
					if (data.success) {
						$("#sign").html(data.msg).css({
							"padding-left" : "4px",
							"color" : "green"
						});
					} else {
						$("#sign").html(data.msg).css({
							"padding-left" : "4px",
							"color" : "red"
						});
					}
				}, "json");
			});
			$("#funcSave").click(function() {
				if (checkNull("#funcName") && checkNull("#funcSign")) {
					save('#funcform', 'func/save.jspx', 'func/index.jspx');
				}
			});
			$('#moduleTree').combotree({
				//获取数据URL  
				url : 'module/tree.jspx',
				//选择树节点触发事件  
				onSelect : function(node) {
					//返回树对象  
					var tree = $(this).tree;
					//选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
					var isLeaf = tree('isLeaf', node.target);
					var size = getNodeLevel(node);//获取系统中的节点的层次
					if (size == 1 || !isLeaf) {
						promotdialog("本系统设定只能在子栏目下才可设置具体功能");
						//清除选中  
						$('#moduleTree').combotree('clear');
					}
				}
			});
		});
	</script>
</div>