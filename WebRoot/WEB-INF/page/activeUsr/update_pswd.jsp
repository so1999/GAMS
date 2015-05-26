<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--编辑功能信息的页面 --%>
<style type="text/css">
.text-right {
	text-align: right;
	padding-right: 4px;
	width: 60px;
}
</style>
<div title="修改密码" style="width: 480px; padding: 20px;" fit="true">
	<form id="pswdform" style="padding: 20px;">
		<table style="width: 100%;">
			<tr>
				<td class="text-right">旧密码</td>
				<td><input type="password" class="x-form-text" id="password" name="password" maxlength="15" /></td>
				<td colspan="2" style="color: green;"></td>
			</tr>
			<tr>
				<td class="text-right">新密码</td>
				<td><input type="password" class="x-form-text" name="newpswd" id="newpswd" maxlength="15" /></td>
			</tr>
			<tr>
				<td class="text-right">确认密码</td>
				<td><input type="password" class="x-form-text" name="oldpswd" id="oldpswd" maxlength="15" /></td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td colspan="4" align="center"><input type="button" class="d-button d-state-highlight" value="修改密码"
					onclick="updatepswdAction()" style="width: 80px;" /></td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		function updatepswdAction() {
			var x = checkNull("#password");
			var newpswd = checkNull("#newpswd");
			var oldpswd = checkNull("#oldpswd");
			if (x && newpswd && oldpswd) {
				winSave('#pswdform', 'acusr/updatePswd.jspx');
			};
		}
	</script>
</div>