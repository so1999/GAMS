<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资材管理系统GAMS V1.0</title>
<link href="./res/ui/login/css/main.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div class="login">
    <div class="box png">
		<div class="logo png"></div>
		<div class="input">
			<div class="log">
			<form id="login" action="login.jhtml" method="post">
				<div class="name">
					<label>用户名</label><input type="text" class="text" id="value_1" name="usrName" tabindex="1" />
				</div>
				<div class="pwd">
					<label>密　码</label><input type="password" class="text" id="value_2" name="password" tabindex="2" />
					<input type="submit" class="submit" tabindex="3" value="登录" />
					<div class="check"></div>
				</div>
				<div class="tip" style="color: red; ">${error }</div>
			</form>
			</div>
		</div>
	</div>
    <div class="air-balloon ab-1 png"></div>
	<div class="air-balloon ab-2 png"></div>
    <div class="footer"></div>
</div>

<script type="text/javascript" src="./res/ui/login/js/jQuery.js"></script>
<script type="text/javascript" src="./res/ui/login/js/fun.base.js"></script>
<script type="text/javascript" src="./res/ui/login/js/script.js"></script>

<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
</div>
</body>
</html>