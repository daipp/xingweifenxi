<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	var editor;
	$(function() {

		window.setTimeout(function() {
			parent.$.messager.progress('close');
		}, 1);

		$('#form').form({
			url : '${pageContext.request.contextPath}/syscodeController/add',
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
				<tr>
					<th style="width:120px">类别编号:</th>
					<td><input id="codeTypeId" name="typeId" type="text" class="span2" value="${codeType.id }" readonly="readonly"></td>
					<th>类别名称:</th>
					<td>${codeType.codeContent }</td>
					
				</tr>
				<tr>
					<th style="width:120px">编号:</th>
					<td><input name="id" type="text" class="span2" value="" readonly="readonly"></td>
					<th>状态:</th>
					<td>
						<select name="status" style="width:200px">
							<option value="1">有效</option>
							<option value="0">无效</option>
						</select>
					</td>
				</tr>
				<tr>
					<th style="width:120px">代码编码</th>
					<td colspan="3"><input name="codeName" type="text" placeholder="请输入系统代码名称" style="width:400px" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<th style="width:120px">代码内容</th>
					<td colspan="3"><input name="codeContent" type="text" placeholder="请输入系统代码内容" style="width:400px" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<th style="width:120px">系统代码描述</th>
					<td colspan="3"><textarea name="memo" id="memo"  placeholder="备注信息" cols="30" rows="4" style="width:400px"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</div>