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
							<li><i class="icon-home home-icon"></i> <a href="${contextPath}/welcome">首页</a></li>
							<li>系统管理</li>
							<li>用户管理</li>
						</ul>
					</div>

					<!-- 正文内容 -->
					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">

								<!-- 查询表单 -->
								<div class="row">
									<div class="col-xs-12">
										<form class="form-horizontal" id="searchForm" role="form">
											<!-- 查询条件 -->
											<div class="col-sm-11 no-padding-left">
												<div class="form-group">
													<label class="col-sm-1 control-label" for="userName">用户名称:</label>
													<div class="col-sm-3">
														<input class="col-xs-12" id="userName" name="userName" placeholder="请输入用户名称" maxlength="10" type="text">
													</div>
													<label class="col-sm-1 control-label" for="realName">真实姓名:</label>
													<div class="col-sm-3">
														<input class="col-xs-12" id="realName" name="realName" placeholder="请输入真实姓名" maxlength="10" type="text">
													</div>
													<label class="col-sm-1 control-label" for="isValid">是否有效:</label>
													<div class="col-sm-3">
														<datatag:enum classStyle="col-xs-10 padding-4" id="isValid" name="isValid" resultType="select" path="BaseEntity$ISVALID">
														</datatag:enum>
														·
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-1 control-label" for="remark">备注:</label>
													<div class="col-sm-3">
														<input class="col-xs-12" id="remark" name="remark" placeholder="请输入备注" maxlength="30" type="text">
													</div>
													<label class="col-sm-1 control-label" for="createUserName">创建人:</label>
													<div class="col-sm-3">
														<input class="col-xs-12" id="createUserName" name="createUserName" placeholder="请输入创建人" maxlength="10" type="text">
													</div>
													<label class="col-sm-1 control-label" for="createDate">创建日期:</label>
													<div class="col-sm-3">
														<input class="col-xs-10 datepicker" id="createDate" name="createDate" placeholder="请输入创建日期" maxlength="10" type="text">
													</div>
												</div>
											</div>
											<!-- 查询btn -->
											<div class="col-sm-1 no-padding-right">
												<button class="btn btn-sm btn-primary float-right" type="submit">
													<i class="icon-search  bigger-110"></i>查询
												</button>
											</div>
										</form>
									</div>
								</div>

								<hr>

								<!-- 操作btn -->
								<div class="row">
									<div class="col-xs-12">
										<button class="btn btn-primary btn-sm" id="addBtn" type="button">
											<i class="icon-plus bigger-110"></i>新增
										</button>
										<button class="btn btn-danger btn-sm" id="delBtn" type="button">
											<i class="icon-minus bigger-110"></i>删除
										</button>
									</div>
								</div>

								<div class="space-4"></div>

								<!-- 列表 -->
								<div class="row">
									<div class="col-xs-12">
										<div id="grid-body">
											<table id="grid-table"></table>
											<div id="grid-pager"></div>
										</div>
									</div>
								</div>

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

	<!-- 提交表单信息 -->
	<form id="postForm" method="post">
		<input id="id" name="id" type="hidden">
	</form>

	<!-- 提示框弹出层 -->
	<div id="alertModal" class="modal"></div>

	<script type="text/javascript">
		$(function($) {
			SysUser.index();
		});
	</script>

</body>
</html>