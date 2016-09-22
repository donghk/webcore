<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-hans">
<head>
<!-- 头部文件，CSS样式 -->
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<script src="${contextPath}/statics/js/system/user.js?version=${version}"></script>
</head>
<body>
	<div class="content">
		<!-- 顶部菜单导航栏 -->
		<%@ include file="/WEB-INF/jsp/common/navbar.jsp"%>
		<div class="main-container" id="main-container">
			<div class="main-container-inner">
				<!-- 左边菜单导航栏 -->
				<%@ include file="/WEB-INF/jsp/common/sidebar.jsp"%>
				<!-- 正文 -->
				<div class="main-content">
					<!-- 正文顶部导航栏 -->
					<div class="breadcrumbs" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i> 
								<a href="${contextPath}/welcome">首页</a>
							</li>
							<li>系统管理</li>
							<li>用户管理</li>
						</ul>
					</div>

					<!-- 正文内容 -->
					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
							
								<!-- 标题 -->
								<div class="page-header">
									<div class="row">
										<div class="col-xs-12">
											<div class="col-xs-12 col-sm-8">
												<h1 id="title">新增用户</h1>
											</div>
									
											<div class="col-xs-9 col-sm-2 col-md-3">
												<button class="btn btn-sm btn-primary float-right" id="saveBtn" type="button">
													<i class="icon-save"></i>
													<span id="saveText">保存</span>
												</button>
											</div>
											<div class="col-xs-3 col-sm-2 col-md-1">
												<button class="btn btn-sm btn-success float-right" id="backBtn" type="button">
													<i class="icon-reply"></i>返回
												</button>
											</div>
										</div>
									</div>
								</div>

								<!-- 表单信息 -->
								<form class="form-horizontal" id="userForm" role="form">

									<input id="id" name="id" type="hidden" value="${user.id }">

									<div class="form-group"> 
										<label class="col-sm-3 control-label no-padding-right" for="userName">用户名:</label>
										<div class="col-sm-9">
											<input class="col-xs-12 col-sm-6" id="userName" name="userName" placeholder="请输入用户名" maxlength="10" type="text" value="${user.userName}">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="realName">真实姓名:</label>
										<div class="col-sm-9">
											<input class="col-xs-12 col-sm-6" id="realName" name="realName" placeholder="请输入真实姓名" maxlength="10" type="text" value="${user.realName}">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="passwd">密码:</label>
										<div class="col-sm-9">
											<input class="col-xs-12 col-sm-6" id="passwd" name="passwd" placeholder="请输入密码" maxlength="16" type="password" value="${user.passwd}">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="passwdConfirm">确认密码:</label>
										<div class="col-sm-9">
											<input class="col-xs-12 col-sm-6" id="passwdConfirm" name="passwdConfirm" placeholder="请再一次输入密码" maxlength="16" type="password" value="${user.passwd}">
										</div>
									</div>

									<c:if test="${!empty user.id}">
										<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right" for="isValid">是否有效:</label>
											<div class="col-sm-9">
												<datatag:enum classStyle="col-xs-12 col-sm-6 no-padding" id="isValid" name="isValid"
													path="BaseEntity$ISVALID" resultType="select" value="${user.isValid}">
												</datatag:enum>
											</div>
										</div>
									</c:if>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="remark">备注:</label>
										<div class="col-sm-9">
											<textarea class="col-xs-12 col-sm-6 textarea-high" id="remark" name="remark" placeholder="请输入备注" maxlength="100" type="text" value="${user.remark}"></textarea>
										</div>
									</div>

								</form>

							</div>
							<!-- /.col-xs-12 -->
						</div>
						<!-- /.row -->
					</div>
					<!-- /.page-content -->
				</div>
				<!-- /.main-content -->
			</div>
			<!-- /.main-container-inner -->
		</div>
		<!-- /.main-container -->
	</div>
	<!-- /.content -->

	<script type="text/javascript">
		$(function($) {
			SysUser.edit();
		});
	</script>
</body>
</html>