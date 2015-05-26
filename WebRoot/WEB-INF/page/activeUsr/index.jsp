<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="authorize" prefix="auth"%>
<%--编辑个人信息的页面 --%>
<div class="easyui-layout" data-options="fit:true">
	<div title="编辑个人信息" style="width: 660px; height: 400px;"
		data-options="region:'center'">
		<form id="funcform" style="padding: 20px;">
		<input type="hidden"  name="id" value="${tec.id }"/>
			<table style="width: 900px;">
				<tr>
					<td>工号<span style="color: red;">*</span></td>
					<td colspan="3"><input type="text" class="x-form-text"
						name="tId" value="${tec.tId}"  readonly="readonly" style="border:0px;"/>
					<td>
				</tr>
				<tr>
					<td>姓名<span style="color: red;">*</span></td>
					<td><input type="text" class="x-form-text" name="tName"
						value="${tec.tName}"  readonly="readonly" style="border:0px;"/></td>
					<td>性别</td>
					<td><select id="xb" class="x-form-text" name="tSex"
						style="width: 90px;">
							<option value="1">男</option>
							<option value="0">女</option>
					</select></td>
				</tr>
				<tr>
					<td>学历</td>
					<td><select id="xl" class="x-form-text" name="tEdu" 
						style="width: 90px;">
							<option value="0">本科</option>
							<option value="1">硕士</option>
							<option value="2">博士</option>
							<option value="3">高中</option>
							<option value="4">其他</option>
					</select></td>
					<td>出生日期<span style="color: red;">*</span></td>
					<td><input type="text" class="x-form-text" name="tBirth"
						value="${tec.tBirth}" /></td>
				</tr>
				<tr>
					<td>电话<span style="color: red;">*</span></td>
					<td><input type="text" class="x-form-text" name="tPhone"
						value="${tec.tPhone}" /></td>
					<td>民族<span style="color: red;">*</span></td>
					<td><input type="text" class="x-form-text" name="tNation"
						value="${tec.tNation}" /></td>

				</tr>
				<tr>
					<td>政治面貌</td>
					<td><select id="zzmm" class="x-form-text" name="tPlic"
						style="width: 90px;">
							<option value="0">群众</option>
							<option value="1">共青团员</option>
							<option value="2">预备党员</option>
							<option value="3">党员</option>
							<option value="4">其他</option>
					</select></td>
<!-- 
					<td>职务</td>
					<td><select id="zw" class="x-form-text" name="tTitle" 
						style="width: 90px;">
							<option value="0">教师</option>
							<option value="1">后勤</option>
							<option value="2">园长</option>
							<option value="3">副园长</option>
					</select></td>
 -->
				</tr>
				<tr>
					<td>籍贯<span style="color: red;">*</span></td>
					<td><input type="text" class="x-form-text" name="tNative"
						value="${tec.tNative}" /></td>
<!-- 
					<td>班级</td>
					<td><input type="text" name="clazzId" id="clazzId" readonly="readonly" 
						value="${tec.clazz.id }" /></td>
				</tr>
 -->
				<tr>
					<td colspan="2"><auth:a onclick="saveUsr()"
							iconCls="easyui-icon-add" cssClass="easyui-linkbutton" text="更 新" />
						</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$("#clazzId").combobox({
			url : "cla/getAllClazz.jspx",
			valueField : 'id',
			textField : 'cName',
			multiple : false,
			editable : false,
			panelHeight : '130'
		});
		$("#xb").combobox({
				panelHeight : 'auto',
			});
		$("#xl").combobox({
				panelHeight : 'auto',
			});
		$("#zzmm").combobox({
				panelHeight : 'auto',
			});
		$("#zw").combobox({
				panelHeight : 'auto',
			});
		function saveUsr() {
			save('#funcform', 'acusr/save.jspx', 'acusr/index.jspx');
		}
	</script>
</div>