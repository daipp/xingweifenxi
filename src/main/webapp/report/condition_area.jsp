<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function(){

	$('#townIds').combobox({
		multiple:true,
		value:'',
		prompt:'街道',
		//multiline:true,
		onSelect: function(record){
	        $('#villageIds').combobox({
				multiple:true,
				multiline:true,
	        	url: '${pageContext.request.contextPath}/boss/syscodeDo/getCodeRelation?typeId=22&state=1&codeId='+record.value,
	            valueField:'codeId',
	            textField:'codeContent'	            	
	        });
	        $('#communityIds').combobox({
				multiple:true,
				//multiline:true,
	        	url: '${pageContext.request.contextPath}/boss/syscodeDo/getCodeRelation?typeId=71&state=1&codeId='+record.value,
	            valueField:'codeId',
	            textField:'codeContent'	            	
	        });
	    }
	});
	
});

</script>
<tr>
	<th style="width:65px;padding:0px">街道:</th>
	<td style="padding:0px">
		<select id="townIds" name="townIds" style="width:350px;">
			<c:forEach items="${towns }" var="t">
			<option value="${t.codeId }">${t.codeContent }</option>
			</c:forEach>
		</select>
		<input type="checkbox" name="showTown" value="true" style="vertical-align:middle" /> <span>分行显示</span>
	</td>
	<th style="width:65px;padding:0px">社区:</th>
	<td style="padding:0px">
		<input id="communityIds" name="communityIds" type="text" placeholder="社区" style="width:350px;margin:0px;padding:0px" />
		<input type="checkbox" name="showCommunity" value="true" style="vertical-align:middle" /> <span>分行显示</span>
	</td>
</tr>
<tr>
	<th style="width:65px;padding:0px">客户类型:</th>
	<td style="padding:0px">
		<select id="customerTypeIds" name="customerTypeIds" class="easyui-combobox" style="width:350px;height:50px"
		 data-options="multiple:true,multiline:true,value:'',prompt:'客户类型'">
			<c:forEach items="${customerTypes }" var="t">
			<option value="${t.codeId }">${t.codeContent }</option>
			</c:forEach>
		</select>
		<input type="checkbox" name="showCustomerType" value="true" style="vertical-align:middle" /> <span>分行显示</span>
	</td>
	<th style="width:65px;padding:0px">小区:</th>
	<td style="padding:0px">
		<input id="villageIds" name="villageIds" type="text" placeholder="小区" style="width:350px;height:50px;margin:0px;padding:0px" />
		<input type="checkbox" name="showVillage" value="true" style="vertical-align:middle" /> <span>分行显示</span>
	</td>
</tr>
