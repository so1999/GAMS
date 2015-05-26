<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
String base =request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<!-- 布局 -->
<link rel="stylesheet" type="text/css" href="<%=base %>/res/ui/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=base %>/res/ui/icon.css"/>
<script type="text/javascript" src="<%=base %>/res/jquery-1.8.0.min.js"> </script>
<script type="text/javascript" src="<%=base %>/res/ui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=base %>/res/share/common.js"></script>
<link rel="stylesheet" type="text/css" href="<%=base %>/res/nav.css"/>
<!-- 提示消息 -->
<link rel="stylesheet" type="text/css" href="<%=base %>/res/tip/jquery.pnotify.default.css"/>
<link rel="stylesheet" type="text/css" href="<%=base %>/res/tip/jquery.pnotify.default.icons.css"/>
<link rel="stylesheet" type="text/css" href="<%=base %>/res/tip/style/css/bootstrap.css"   />
<script type="text/javascript" src="<%=base %>/res/tip/jquery.pnotify.js"></script>
<link rel="stylesheet" type="text/css" href="<%=base %>/res/tip/mytip.css"/>
<!-- 对话框 -->
<script type="text/javascript" src="<%=base %>/res/art/jquery.art.dialog.min.js"></script>
<script type="text/javascript" src="<%=base %>/res/art/art.dialog.plugins.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=base %>/res/art/skins/default.css"/>

 <%--<script type="text/javascript" src="<%=base %>/res/ui/jquery-easyui-extends.js"></script>
<script type="text/javascript" src="<%=base %>/res/art/artDialog.js"></script>
<script type="text/javascript" src="<%=base %>/res/art/dialog.min.js"></script> --%>