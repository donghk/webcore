<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- 自定义标签 -->
<%@ taglib prefix="datatag" uri="/datatag" %>
<!-- 系统路径 -->
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!-- JS版本号 -->
<c:set var="jsVersion" value="1"/>