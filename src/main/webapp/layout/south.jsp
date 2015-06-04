<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="panel-header panel-title" style="text-align: center; height:30px">
	<p>	版权所有@<a href="http://www.ndtv.com.cn">ndtv</a>	</p>
	
	<p id="sessionInfoDiv" style="position: absolute; right: 0px; bottom: 0px; padding:0px;border:0px" class="alert alert-info">
		<c:if test="${sessionInfo.id != null}">[<strong>${sessionInfo.realName}</strong>]，欢迎您！您使用[<strong>${sessionInfo.ip}</strong>]IP登录！</c:if>
	</p>
</div>
