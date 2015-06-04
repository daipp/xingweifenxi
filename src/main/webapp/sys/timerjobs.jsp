<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var timerJobs = ${timerJobs}
	var dataGrid;
	$(function() {
		parent.$.messager.progress('close');
		$("#searchForm input[name='date2']").val(new Date().format("yyyy-MM-dd HH:mm:ss"))
		$("#searchForm input[name='date1']").val(new Date().format("yyyy-MM-dd") + " 00:00:00")
		initDataGrid();
	});
	
	function initDataGrid(){
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/timerJobLogController/dataGrid',
			fit : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 20,
			pageList : [ 20, 40, 60, 80, 100 ],
			sortName : 'id',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect : true,
			nowrap : false,
			frozenColumns : [ [ {
				field : 'id',
				hidden : true
			}, {
				field : 'jobId',
				title : '任务编号',
				width : 100,
				sortable : true
			}, {
				field : 'jobName',
				title : '任务名称',
				width : 200,
				sortable : false
			} ] ],
			columns : [ [ {
				field : 'crtimeDesc',
				title : '执行时间',
				width : 200,
				sortable : false
			}, {
				field : 'memo',
				title : '备注',
				width : 500,
				sortable : false
			}, {
				field : 'milliseconds',
				title : '执行时长(ms)',
				width : 100
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
				$(this).datagrid('tooltip');
			}
		});
	}

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div id="queryPanels" class="easyui-layout" data-options="fit:true,border:false,title:'查询条件'" style="min-width:1000px">
		<div style="overflow: hidden; height:60px" 
		data-options="region:'north',title:'查询条件',border:false,onCollapse:function(){$('#queryPanels div.panel-title').text('查询条件');}">
			<form id="searchForm" style="margin:0px">
				<table stlye="padding:0px">
					<tr>
						<th style="width:80px">任务名称</th>
						<td>
							<input name="jobId" class="easyui-combobox" data-options="
								valueField: 'id',
								textField: 'codeName',
								data: timerJobs" />
						</td>
						<th style="width:80px">开始时间</th>
						<td >
							<input name="date1" placeholder="点击选择时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
							至
							<input name="date2" placeholder="点击选择时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>

</body>
</html>