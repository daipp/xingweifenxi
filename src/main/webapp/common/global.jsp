<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="systemTitle" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	var ctx = '<%=request.getContextPath() %>';
</script>

<script type="text/javascript" src="${ctx }/js/extBrowser.js" charset="utf-8"></script>


<link rel="shortcut icon" href="${pageContext.request.contextPath}/style/images/favico.ico" >
<link rel="icon" href="${pageContext.request.contextPath}/style/images/logo_wasu.png" type="image/png" >