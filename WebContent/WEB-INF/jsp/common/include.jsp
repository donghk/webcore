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

<!-- ace css -->

<link rel="stylesheet" href="${contextPath}/ace/css/jquery-ui-1.10.3.full.min.css">
<link rel="stylesheet" href="${contextPath}/ace/css/ui.jqgrid.css">
<link rel="stylesheet" href="${contextPath}/ace/css/jquery-ui-1.10.3.custom.min.css">
<link rel="stylesheet" href="${contextPath}/ace/css/chosen.css">
<link rel="stylesheet" href="${contextPath}/ace/css/datepicker.css">
<link rel="stylesheet" href="${contextPath}/ace/css/bootstrap-timepicker.css">
<link rel="stylesheet" href="${contextPath}/ace/css/daterangepicker.css">
<link rel="stylesheet" href="${contextPath}/ace/css/jquery.gritter.css">
<link rel="stylesheet" href="${contextPath}/ace/css/select2.css">

<link rel="stylesheet" href="${contextPath}/ace/css/bootstrap.min.css">
<link rel="stylesheet" href="${contextPath}/ace/css/font-awesome.min.css">

<link rel="stylesheet" href="${contextPath}/ace/css/ace.min.css">
<link rel="stylesheet" href="${contextPath}/ace/css/ace-rtl.min.css">
<link rel="stylesheet" href="${contextPath}/ace/css/ace-skins.min.css">

<!--[if lte IE 8]>
  <link rel="stylesheet" href="ace/css/ace-ie.min.css">
<![endif]-->
<!--[if IE 7]>
  <link rel="stylesheet" href="${contextPath}/ace/css/font-awesome-ie7.min.css">
<![endif]-->

<link rel="stylesheet" href="${contextPath}/ace/css/ace-fonts.css">

<!-- plugins css -->

<link rel="stylesheet" href="${contextPath}/plugins/css/jquery.loadmask.css">

<!-- common css -->

<link rel="stylesheet" href="${contextPath}/statics/css/common/common.css">

<!-- ace js -->

<!--[if !IE]> -->
<script type="text/javascript">
	window.jQuery || document.write("<script src='${contextPath}/ace/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
</script>
<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
 	window.jQuery || document.write("<script src='${contextPath}/ace/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

<script type="text/javascript">
	if ("ontouchend" in document) document.write("<script src='${contextPath}/ace/js/jquery.mobile.custom.min.js'>" + "<"+"/script>");
</script>

<script src="${contextPath}/ace/js/ace-extra.min.js"></script>
<script src="${contextPath}/ace/js/ace-elements.min.js"></script>
<script src="${contextPath}/ace/js/ace.min.js"></script>

<!--[if lt IE 9]>
<script src="${contextPath}/ace/js/html5shiv.js"></script>
<script src="${contextPath}/ace/js/respond.min.js"></script>
<![endif]-->

<script src="${contextPath}/ace/js/bootstrap.min.js"></script>
<script src="${contextPath}/ace/js/bootbox.min.js"></script>
<script src="${contextPath}/ace/js/typeahead-bs2.min.js"></script>

<script src="${contextPath}/ace/js/jqGrid/jquery.jqGrid.min.js"></script>
<script src="${contextPath}/ace/js/jqGrid/i18n/grid.locale-cn.js"></script>

<script src="${contextPath}/ace/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="${contextPath}/ace/js/jquery.ui.touch-punch.min.js"></script>
<script src="${contextPath}/ace/js/chosen.jquery.min.js"></script>
<script src="${contextPath}/ace/js/fuelux/fuelux.spinner.min.js"></script>
<script src="${contextPath}/ace/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="${contextPath}/ace/js/date-time/bootstrap-timepicker.min.js"></script>
<script src="${contextPath}/ace/js/date-time/moment.min.js"></script>
<script src="${contextPath}/ace/js/date-time/daterangepicker.min.js"></script>
<script src="${contextPath}/ace/js/jquery.knob.min.js"></script>
<script src="${contextPath}/ace/js/jquery.autosize.min.js"></script>
<script src="${contextPath}/ace/js/jquery.inputlimiter.1.3.1.min.js"></script>
<script src="${contextPath}/ace/js/jquery.maskedinput.min.js"></script>
<script src="${contextPath}/ace/js/bootstrap-tag.min.js"></script>
<script src="${contextPath}/ace/js/jquery.validate.min.js"></script>
<script src="${contextPath}/ace/js/select2.min.js"></script>
<script src="${contextPath}/ace/js/select2_locale_cn.js"></script>
<script src="${contextPath}/ace/js/jquery.gritter.min.js"></script>

<!-- plugins js -->
	
<script src="${contextPath}/plugins/js/jquery.form.js"></script>
<script src="${contextPath}/plugins/js/jquery.loadmask.js"></script>
<script src="${contextPath}/plugins/js/bootstrap-maxlength.js"></script>
<script src="${contextPath}/plugins/js/addclear.js"></script>

<!-- common js -->

<script src="${contextPath}/statics/js/common/common.js?version=${version}"></script>
