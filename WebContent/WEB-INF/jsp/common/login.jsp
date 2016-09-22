<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- UTF-8编码-->
<meta charset="utf-8">
<!-- 默认IE使用最新版本 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- 手机浏览器默认像素，静止缩放 -->
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<!-- 搜索引擎，关键字 -->
<meta name="keywords" content="webcore" />
<!-- 搜索引擎，描述文字 -->
<meta name="description" content="webcore" />
<!-- 标题 -->
<title>webcore</title>

<!-- 标签 -->
<%@ include file="taglibs.jsp"%>

<!-- 图标 -->
<link rel="shortcut icon" href="${contextPath}/statics/img/favicon.ico"/>

<!-- bootstrap框架基css及字体 -->
<link rel="stylesheet" href="${contextPath}/ace/css/bootstrap.min.css" />
<link rel="stylesheet" href="${contextPath}/ace/css/font-awesome.min.css" />

<!--[if IE 7]>
		  <link rel="stylesheet" href="${contextPath}/ace/css/font-awesome-ie7.min.css" />
		<![endif]-->

<!-- page specific plugin styles -->

<!-- fonts -->

<link rel="stylesheet" href="${contextPath}/ace/css/ace-fonts.css" />

<!-- ace styles -->

<link rel="stylesheet" href="${contextPath}/ace/css/ace.min.css" />
<link rel="stylesheet" href="${contextPath}/ace/css/ace-rtl.min.css" />

<!--[if lte IE 8]>
		  <link rel="stylesheet" href="${contextPath}/ace/css/ace-ie.min.css" />
		<![endif]-->

<!-- inline styles related to this page -->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<!--[if lt IE 9]>
			<script src="${contextPath}/ace/js/html5shiv.js"></script>
			<script src="${contextPath}/ace/js/respond.min.js"></script>
		<![endif]-->

<style type="text/css">
.btn-login {
	width: 100%;
	background: #018fe1;
	border: 1px #018fe1 solid;
	border-radius: 3px;
	color: #fff;
	height: 40px;
	font-size: 18px;
	letter-spacing: 10px;
}

.login-user-label {
	width: 100%;
	height: 40px;
	margin-top: 50px;
	margin-bottom: 20px;
	display: block;
	float: left;
	padding-left: 40px;
}

.login-pass-label {
	width: 100%;
	height: 40px;
	margin-bottom: 20px;
	display: block;
	float: left;
	padding-left: 40px;
}

.login-user-icon {
	width: 40px;
	height: 40px;
	background-color: #ffffff;
	background-image: url(${contextPath}/statics/img/login-user.png);
	background-repeat: no-repeat;
	float: left;
	margin-left: -40px;
}

.login-pass-icon {
	width: 40px;
	height: 40px;
	background-color: #ffffff;
	background-image: url(${contextPath}/statics/img/login-pass.png);
	background-repeat: no-repeat;
	float: left;
	margin-left: -40px;
}

#loginForm input.input-text {
	border: 1px #cecece solid;
	border-radius: 5px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	background: #fff;
	height: 40px;
	width: 100%;
	float: left;
	padding: 4px;
}

#errorMsg {
	display: block;
	padding: 10px 0px 0px;
	font-size: 13px;
	color: red;
}
</style>
</head>
<body class="login-layout">
	<div class="main-container">
		<div class="main-content">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container">
						<div class="center">
							<h1>
								<i class="icon-leaf green"></i> <span class="white">WebCore</span>
							</h1>
							<h4 class="blue">&copy; donghk</h4>
						</div>

						<div class="space-6"></div>

						<div class="position-relative">
							<div id="login-box" class="login-box visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">

										<form name="loginForm" action="${pageContext.request.contextPath}/j_spring_security_check" method="POST" id="loginForm">
											<label id="errorMsg" class="${param.error == true ? '' : 'hidden' }">用户名或密码不正确</label>
											<input id="errorInput" type="hidden" value="${param.error }" />
											<div align="center">
												<div class="login-user-label" style="margin-top: 10px;">
													<span class="login-user-icon"></span>
													<input class="input-text" placeholder="请输入您的用户名" type='text' name='j_username' value="" id="username" />
												</div>
												<div class="login-pass-label">
													<span class="login-pass-icon"></span>
													<input class="input-text" type='password' name='j_password' placeholder="请输入您的密码" id="password">
												</div>
												<div align="center">
													<input id="loginBtn" name="loginBtn" type="button" value="登录" class="btn-login">
												</div>
										</form>

									</div>
									<!-- /widget-main -->

								</div>
								<!-- /widget-body -->
							</div>
							<!-- /login-box -->

						</div>
						<!-- /position-relative -->
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</div>
	<!-- /.main-container -->

	<!--[if !IE]> -->
	<script type="text/javascript">
		window.jQuery || document.write("<script src='${contextPath}/ace/js/jquery-2.0.3.min.js'>" + "<"+"/script>");
	</script>
	<!-- <![endif]-->

	<!--[if IE]>
		<script type="text/javascript">
		 	window.jQuery || document.write("<script src='${contextPath}/ace/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
		</script>
		<![endif]-->

	<script type="text/javascript">
		if ("ontouchend" in document)
			document.write("<script src='${contextPath}/ace/js/jquery.mobile.custom.min.js'>" + "<"+"/script>");
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#username").val("");
			$("#password").val("");
			$("#username").focus();

		});

		//点击提交
		$("#loginBtn").click(function() {
			submit();
		});

		//回车提交
		document.onkeydown = function(event) {
			var e = event || window.event || arguments.callee.caller.arguments[0];
			if (e && e.keyCode == 13) { // enter 键
				submit();
			}
		};

		function submit() {
			if ($("#username").val() == "") {
				$("#errorMsg").removeClass("hidden");
				$("#errorMsg").html("请输入用户名");
				return;
			}
			if ($("#password").val() == "") {
				$("#errorMsg").removeClass("hidden");
				$("#errorMsg").html("请输入密码");
				return;
			}

			$("#loginForm").submit();
		}
	</script>
</body>
</html>
