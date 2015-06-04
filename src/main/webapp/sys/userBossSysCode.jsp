<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <jsp:include page="../inc.jsp"></jsp:include> --%>

<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/boss/syscodeDo/mergeSysUserBossSysCode',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				$("#form select option").each(function() { 
					$(this).attr("selected", "selected"); 
				}); 
				
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr>
					<td>用户编号：<input name="userId" value="${user.id}"></td>
					<td>姓名：${user.realName}</td>
				</tr>
				<tr>
					<td>
						<jsp:include page="../report/boss_coderelation.jsp">
							<jsp:param name="userId" value="${user.id}"/>
							<jsp:param name="typeId" value="23"/>
						</jsp:include>
					</td>
					<td>
						<jsp:include page="../report/boss_coderelation.jsp">
							<jsp:param name="userId" value="${user.id}"/>
							<jsp:param name="typeId" value="32"/>
						</jsp:include>
					</td>
				</tr>
				<tr>
					<td>
						<jsp:include page="../report/boss_coderelation.jsp">
							<jsp:param name="userId" value="${user.id}"/>
							<jsp:param name="typeId" value="22"/>
						</jsp:include>
					</td>
					<td>
						<jsp:include page="../report/boss_coderelation.jsp">
							<jsp:param name="userId" value="${user.id}"/>
							<jsp:param name="typeId" value="71"/>
						</jsp:include>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>