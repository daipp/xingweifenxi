<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/noticeController/edit',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
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
			
				<tr >
					<th >公告标题</th>
					<td ><input name="title" type="text" placeholder="请输入标题" class="easyui-validatebox span2" value="${ notice.title}"style="width:300; height:40;border: 0" readonly="readonly"></td>
					<th ></th>
					<td><input name="id" type="hidden" class="span2" value="${notice.id}"></td>
					
				</tr>
				<%-- <tr>
					<th>公告正文</th>
					<td ><input name="content" type="text" placeholder="请输入正文" class="easyui-validatebox span2"style="width:700; height:300" value="${ notice.content}"></td>
					
				</tr> --%>
				<tr> 
					<th>公告正文</th>
					<td colspan="s">
					<textarea name="content" rows="" cols="" class="span5"style="width:700; height:300;">"${ notice.content}"</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>