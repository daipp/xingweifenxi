<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/global.jsp"%>
<%@ include file="/common/include-kindeditor.jsp"%>
<%@ include file="/common/inclue-highcharts.jsp"%>
<%@ include file="/common/include-dist.jsp"%>
<%@ include file="/common/include-easyui.jsp"%>
<%@ include file="/common/include-style.jsp"%>

 --%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/extBrowser.js" charset="utf-8"></script>

<!-- 引入my97日期时间控件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js" charset="utf-8"></script>

<!-- 引入kindEditor插件 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/kindeditor-4.1.10/themes/default/default.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/kindeditor-4.1.10/kindeditor-all-min.js" charset="utf-8"></script>

<!-- 引入jQuery -->
<script src="${pageContext.request.contextPath}/js/jquery-1.8.3.js" type="text/javascript" charset="utf-8"></script>

<!-- 引入Highcharts -->
<script src="${pageContext.request.contextPath}/js/Highcharts-3.0.10/js/highcharts.js" type="text/javascript" charset="utf-8"></script>

<!-- 引入bootstrap样式 -->
<link href="${pageContext.request.contextPath}/js/bootstrap-2.3.1/css/bootstrap.min.css" rel="stylesheet" media="screen">
<!-- <script charset="utf-8" src="${pageContext.request.contextPath}/js/bootstrap-2.3.1/js/bootstrap.min.js" charset="utf-8"></script> -->

<!-- 引入EasyUI -->
<link id="easyuiTheme" rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.6/themes/<c:out value="${cookie.easyuiThemeName.value}" default="bootstrap"/>/easyui.css" type="text/css">
<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.6/themes/icon.css" type="text/css"> -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.6/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<!-- 修复EasyUI1.3.3中layout组件的BUG -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.6/plugins/jquery.layout.js" charset="utf-8"></script>

<!-- 引入EasyUI Portal插件 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-easyui-portal/portal.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>

<!-- 扩展EasyUI -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/extEasyUI.js?v=201305241044" charset="utf-8"></script>

<!-- 扩展EasyUI Icon -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/extEasyUIIcon.css?v=201305301906" type="text/css">

<!-- 扩展jQuery -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/extJquery.js?v=201305301341" charset="utf-8"></script>