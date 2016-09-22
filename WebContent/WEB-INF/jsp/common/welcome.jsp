<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-hans">
<head>
<!-- 头部文件，CSS样式 -->
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
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
						</ul>
					</div>

					<!-- 正文内容 -->
					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<div class="alert alert-block alert-success">
									<button type="button" class="close" data-dismiss="alert">
										<i class="icon-remove"></i>
									</button>
									<i class="icon-ok green"></i> 
									Welcome to 
									<strong class="green"> 
										webcore
									</strong>
								</div>
								<datatag:enum classStyle="col-xs-12 col-sm-5 no-padding" id="isValid" name="isValid" 
									resultType="select" path="BaseEntity$ISVALID">
								</datatag:enum>·
		
								<datatag:dataDict classStyle="col-xs-12 col-sm-5 no-padding" 
									resultType="select" code="yn,y">
								</datatag:dataDict>
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
</body>
</html>
