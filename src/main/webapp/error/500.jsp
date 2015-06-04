<%@ page language="java" isErrorPage="true" pageEncoding="UTF-8" 
 	import="java.io.StringWriter" 
 	import="java.io.PrintWriter" 
 	import="java.lang.reflect.InvocationTargetException" 
%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

//	异常消息提示
	String message = "发生未知错误.";
//	返回地址
	String backURL = "javascript:history.back()";
	Throwable throwable = exception;
	
	if(exception != null) {
		exception.printStackTrace();
		
//		如果是JSP抛出的异常, 则取得RootCause
		while(throwable instanceof ServletException) {
			throwable = ((ServletException)throwable).getRootCause();
		}
		//如果异常是调用对象异常, 则获取对象异常
		while(throwable instanceof InvocationTargetException) {
			throwable = ((InvocationTargetException)throwable).getTargetException();
		}
		//获取原始异常
		while(throwable.getCause() != null && !(throwable instanceof RuntimeException)) {
			throwable = throwable.getCause();
		}
		
		message = throwable.getMessage();

	}
	
	if(message == null) {
		message = "发生未知错误.";
	} else {
		message = message.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
    StringWriter sw = new StringWriter();
    exception.printStackTrace(new PrintWriter(sw));
    String traceStack = sw.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	
	response.setStatus(HttpServletResponse.SC_OK);

%>
	
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/global.jsp"%>
	<script type="text/javascript" src="${ctx}/js/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" href="${ctx }/css/info_error.css" type="text/css"></link>
	<title>${applicationScope.systemTitle }</title>
	<script type="text/javascript">
	function slapErrorInfo(){
		if($("#trace").is(":hidden")){
			$("#trace").show();
		} else {
			$("#trace").hide();
		}
	}
	</script>
</head>
<body>

<div id="msgBox">
	<div id="title">
		<img src="${pageContext.request.contextPath}/style/images/blue_face/bluefaces_05.png" alt="资源未找到" ></img>
		<p>系统内部错误[500]: </p>
	</div>
	<div id="content">
		<%=message %>
	</div>
	<div>
		[<a href="###" onclick="slapErrorInfo();">详细错误信息</a>]
		<a href="###" onclick="window.parent.location.href='/oa/'" class="linkButton">返回首页</a>
		<a href="<%=backURL%>" class="linkButton">返回上页</a>
	</div>
</div>
	<div id="trace">
	<div id="traceClose"><a href="###" onclick="slapErrorInfo();">x</a></div>
	<pre><%=traceStack%></pre>
	</div>
</body>
</html>
