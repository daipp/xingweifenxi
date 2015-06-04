<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/js/kindeditor-4.1.10/themes/default/default.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/kindeditor-4.1.10/kindeditor-all-min.js" charset="utf-8"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>

<script type="text/javascript" >
              
 KindEditor.ready(function (K) {
	 console.info("s");
    var editor1 = K.create('#content', {  //xtContentText是html编辑器的id
        cssPath: '${pageContext.request.contextPath}/js/kindeditor-4.1.10/plugins/code/prettify.css',   //注意资源相对路径
        uploadJson: 'upload_json.ashx',  //用到了上传ashx
        fileManagerJson: 'file_manager_json.ashx',  //用到了管理ashx                
        allowFileManager: true,
        afterCreate: function () {
            var self = this;
            K.ctrl(document, 13, function () {
                self.sync();
                K('form[name=form]')[0].submit();
            });
            K.ctrl(self.edit.doc, 13, function () {
                self.sync();
                K('form[name=form]')[0].submit();
            });
        }
    });
   prettyPrint();
}); 

 </script>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
	
		$('#form').form({
			url : '${pageContext.request.contextPath}/noticeController/add',
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
					parent.$.messager.alert('提示', result.msg, 'info');
					
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
					<td ><input name="title" type="text" placeholder="请输入标题" style="width:500; height:40" ></td>
					<th ></th>
					<td><input name="id" type="hidden" class="span2" value="${notice.id}" readonly="readonly"></td>
					
				</tr>
				 <tr>
					<th>公告正文</th>
				 	<td ><input type="text" name="content"placeholder="请输入正文" style="width:700; height:300" ></td> 
					<td ><textarea id="txtContentText"name="content"  style="width:700; height:400;visibility:hidden;" ></textarea></td> 
					
				<tr> 
					<th>公告正文</th>
					<td><textarea  class="kindeditor"name="content"  id ="content" rows="" cols="" class="span5"style="width:700; height:300;visibility:hidden"></textarea></td>
				</tr>
			</table>
		</form>
		
	</div>
</div>
