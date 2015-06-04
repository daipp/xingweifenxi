<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ include file="/common/global.jsp"%>
<c:if test="${msg != null }">
	${msg}
	<script type="text/javascript" charset="utf-8">
		try{parent.$.messager.progress('close');}catch(e){}
	</script>
</c:if>


<c:if test="${msg == null }">
	<p>对不起，您的权限不足！</p>	
</c:if>