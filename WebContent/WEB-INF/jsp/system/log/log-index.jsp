<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-hans">
<head>
<!-- 头部文件，CSS样式 -->
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<script src="${contextPath}/statics/js/system/log.js?version=${version}"></script>
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
							<li>日志管理</li>
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
													<label class="col-sm-1 control-label" for="logType">日志类型:</label>
													<div class="col-sm-2">
														<input class="col-xs-12" id="logType" name="logType" placeholder="请输入日志类型" maxlength="10" type="text">
													</div>
													<label class="col-sm-1 control-label" for="logDetail">日志内容:</label>
													<div class="col-sm-2">
														<input class="col-xs-12" id="logDetail" name="logDetail" placeholder="请输入日志内容" maxlength="10" type="text">
													</div>
													<label class="col-sm-1 control-label" for="createUserName">操作人:</label>
													<div class="col-sm-2">
														<input class="col-xs-12" id="createUserName" name="createUserName" placeholder="请输入操作人" maxlength="10" type="text">
													</div>
													<label class="col-sm-1 control-label" for="createDate">操作日期:</label>
													<div class="col-sm-2">
														<input class="datepicker col-xs-12" id="createDate" name="createDate" placeholder="请选择操作日期" maxlength="10" type="text">
													</div>
												</div>
											</div>
											<!-- 查询btn -->
											<div class="col-sm-1 no-padding-right">
												<button class="btn btn-sm btn-primary float-right" type="submit">
													<i class="icon-search bigger-110" ></i>查询
												</button>
											</div>
										</form>	
									</div>
								</div>

								<hr>
																
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

	<script type="text/javascript">
		$(function($) {
			SysLog.index();
		});
	</script>

</body>
</html>