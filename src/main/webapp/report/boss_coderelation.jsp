<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript">
var relatedCode${param.typeId };
var unrelatedCode${param.typeId };
$(function() {

	//填充多选框
	$.getJSON("${pageContext.request.contextPath}/boss/syscodeDo/getCodeListBySysUserEx", 
			{ state: "1", typeId: "${param.typeId }",userId:"${param.userId }" }, function(json){
		unrelatedCode${param.typeId } = json;
		
		//填充下拉框
		$('#typeIdex${param.typeId }').combobox({
			data:unrelatedCode${param.typeId },
	        valueField:'codeId',
	        textField:'codeContent',
	        multiple:true
		});
		
	});
	
	
	
	//填充类名称
	$.getJSON("${pageContext.request.contextPath}/boss/syscodeDo/getBossSysCode", 
			{ codeName: "${param.typeId }", typeId: 4 }, function(json){
		$('#label${param.typeId }').text(json.codeContent);
	});
	
	//填充多选框
	$.getJSON("${pageContext.request.contextPath}/boss/syscodeDo/getCodeListBySysUser", 
			{ state: "-1", typeId: "${param.typeId }",userId:"${param.userId }" }, function(json){
		relatedCode${param.typeId } = json;
		$.each( relatedCode${param.typeId }, function(i, n){
			$('#typeId${param.typeId }').append("<option value='" + n.codeId + "'>" + n.codeContent + "</option>");
		});
		
	});
	
	//多选框双击事件
	$('#typeId${param.typeId }').dblclick( function () { 
		$("#typeId${param.typeId } option:selected").remove();
	});
	
	//按钮点击事件
	$('#addtypeId${param.typeId }').click( function () { 
		var arr = $('#typeIdex${param.typeId }').combobox('getValues');
		for (x in arr) {
			for (y in unrelatedCode${param.typeId }) {
				if(arr[x] == unrelatedCode${param.typeId }[y].codeId){
					if($("#typeId${param.typeId } option[value='"+ arr[x] +"']").length==0){
		$("#typeId${param.typeId }").append("<option value='" + arr[x] + "'>"+ unrelatedCode${param.typeId }[y].codeContent +"</option>");
		
					}
					
				}
			}
		}
		$('#typeIdex${param.typeId }').combobox('setValue','');
		
		
	});
	$('#deltypeId${param.typeId}').click(  function(){
		$("#typeId${param.typeId } option:selected").remove();

	});
});
</script>

<div>
	<label id="label${param.typeId }"></label>
	<input id="typeIdex${param.typeId }" />
	<input id="addtypeId${param.typeId }" type="button" value="↓">
</div>
<div>
	<input type="hidden" name="typeIds" value="${param.typeId }">
	<select id="typeId${param.typeId }" name="codeIds" multiple="multiple" size="10" style="width:180px; float:left" >
	
	</select>
	<input id="deltypeId${param.typeId}" type="button" value="↑">
</div>