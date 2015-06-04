<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<jsp:include page="../inc.jsp"></jsp:include>
	<link rel="stylesheet" href="${ctx }/css/info_error.css" type="text/css"></link>
    <title>${applicationScope.systemTitle }</title>
<%response.setStatus(HttpServletResponse.SC_OK);%>
<script type="text/javascript">
	$(function() {

		parent.$.messager.progress('close');
	});
</script>
</head>
<body>
	<div id="msgBox">
	<div id="title">抱歉! </div>
	<div id="content">
		您访问的资源不存在, 可能正在建设中. 
	</div>
	<div>
		<a href="javascript:history.go(-1)">返回</a>
	</div>
</div>
</body>
</html>
