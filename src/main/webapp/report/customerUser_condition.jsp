<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
var dataGrid;
$(function(){
	dataGrid=$('#dataGrid').datagrid({
		url : '${pageContext.request.contextPath}/reportCustomerUserDo/getConditionCustomer',
		fit : true,
		fitColumns : true,
		border : false,
		pagination : true,
		idField : 'id',
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		sortName : 'id',
		sortOrder : 'asc',
		checkOnSelect : false,
		selectOnCheck : false,
		nowrap : false,
		striped : true,
		rownumbers : true,
		singleSelect : true,
		
		frozenColumns : [[ {
			field : 'id',
			hidden : true
		}, {
			field : 'townId',
			hidden : true
		}, {
			field : 'communityId',
			hidden : true
		}, {
			field : 'villageId',
			hidden : true
		}, {
			field : 'customerTypeId',
			hidden : true
		},{
			field: 'userId',
			title: '用户编号',
			width : 100,
			sortable : true,
			formatter: function(value,row,index){
				return '<a href=&quot;###&quot;>'+value+'</a>';
			}
		},
		{
			field: 'customerName',
			title: '用户名称',
			width : 80,
			sortable : true
		},
		{
			field : 'town',
			title : '行政区',
			width : 80,
			sortable : true
		} , {
			field : 'community',
			title : '社区',
			width : 80,
			sortable : true
		} , {
			field : 'village',
			title : '小区',
			width : 120,
			sortable : true
		}, {
			field : 'customerType',
			title : '客户类型',
			width : 150,
			sortable : true
		} ,
		{
			field: 'fulladdress',
			title: '地址',
			width : 150,
			sortable : true
		}]],
		
		toolbar : '#toolbar',
		onLoadSuccess : function() {
			$('#searchForm table').show();
			parent.$.messager.progress('close');
			userIdBinds();
			/* $(this).datagrid('tooltip');
			$(this).datagrid('showHeadTip'); */
			
		},
		onLoadError : function() {
			parent.$.messager.progress('close');
			parent.$.messager.alert('出错了','获取表格信息失败!');
		},
		onClickCell : function(rowIndex, field, value) {
			if(field == 'userId'){
				$('#cellMenu').menu('show', {
					left : window.event.pageX,
					top : window.event.pageY
				});
				var thisRow = $('#dataGrid').datagrid('getRows')[rowIndex];
				$('#showInNewWindow').prop('target','_blank');
				$('#showInNewWindow').prop('href','${pageContext.request.contextPath}/reportvodDo/getUser/' + thisRow.userId);
				$('#showInNewWindowBOSS').prop('target','_blank');
				$('#showInNewWindowBOSS').prop('href','http://10.8.70.11/boss/User/DocDo?goto=view&userId='+thisRow.userId);
				$('#cellMenu .menu-line').hide(); 
			}
		}
			
	});
}
);
function searchFun() {
	if(! $('#searchForm').form('validate')){
		return false;
	}
	dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
}
function cleanFun() {
	$('#searchForm input').val('');
	dataGrid.datagrid('load', {});
}
function userIdBinds(){
	$(".datagrid-cell-c1-userId a").click(function(){
		if(window.parent != null){
			var url='${pageContext.request.contextPath}/reportvodDo/getUser/' + $(this).text();
			window.parent.addTab({url:url,title:'用户详情',newtab:true});
		}
		return false;
	});
} 
</script>
</head>
<body>
<div id="queryPanels" class="easyui-layout" data-options="fit:true,border:false,title:'查询条件'" style="min-width:1250px">
	<div style="overflow:hidden;padding-bottom:5px"
		data-options="region:'north',title:'查询条件',border:false,onCollapse:function(){$('#queryPanels div.panel-title').text('查询条件');}">
		
			<form id="searchForm" method="get">
				<table>
				<!-- <table class="table table-hover table-condensed"> -->
				 <%-- <jsp:include page="condition_area.jsp"></jsp:include> --%>
				 	<tr>
						<td><select name="conditionType1"  class="span2" >
								<option value="customername">姓名</option>	
								<option value="fulladdress">地址</option>	
							</select></td>
						<td><input name="conditionValue1" placeholder="支持模糊查询" class="span3" /></td>
					</tr>
					<tr>
						<td><select name="conditionType2"  class="span2" >
								<option value="userid">用户编号</option>	
								<option value="simserialno">sim卡号</option>	
								<option value="stbserialno">机顶盒号</option>	
								<option value="servicecardid">服务卡号</option>	
							</select></td>
						<td><input name="conditionValue2" placeholder="精确查询" class="span3" /></td>
					</tr>
				</table>
			</form>
	
	
		
	</div>
	<div id="dgTab" data-options="region:'center',border:false" style="margin-top:1px">
			<table id="dataGrid" style="min-width:1200px"></table>
	</div>
	<div id="cellMenu" class="easyui-menu" style="width: 120px; display: none;">
				<a id="showInNewWindow" href="###">打开详情</a>
				<br/>
				<a id="showInNewWindowBOSS" href="###">打开BOSS详情</a>
	</div>
</div>
</body>
<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="statVodArea();">分析图</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询数据</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_go',plain:true" onclick="downloadFun();">下载Excel</a>
</div>
</html>