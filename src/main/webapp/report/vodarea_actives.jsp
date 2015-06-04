<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>区域点播统计</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/reportvodDo/getVodArea/actives',
			fit : true,
			fitColumns : false,
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
			
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 50,
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
			}, {
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
				width : 150,
				sortable : true
			}, {
				field : 'customerType',
				title : '客户类型',
				width : 150,
				sortable : true
			} ] ],
			
			columns : [ [  {
				field : 'activeUsers',
				title : '用户数',
				headTip:'满足查询条件的用户数',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getActives"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-活跃用户'});\">"+value+"</a>";
				}
			} ] ],
			
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
				$(this).datagrid('tooltip');
				$(this).datagrid('showHeadTip');
			},
			onLoadError : function() {
				parent.$.messager.progress('close');
				parent.$.messager.alert('出错了','获取表格信息失败!');
			},
			onClickCell : function(rowIndex, field, value) {
				if(field != 'repDate' && field != 'town' && field != 'community'  && field != 'village' && field != 'customerType'){
					$('#cellMenu').menu('show', {
						left : window.event.pageX,
						top : window.event.pageY
					});
					var thisRow = dataGrid.datagrid('getRows')[rowIndex];
					$('#showInNewWindow').prop('target','_blank');
					$('#showInNewWindow').prop('href',"${pageContext.request.contextPath}/report/vodareauserlist.jsp"
							+"?getWhat=getActives" + getParams(thisRow));

					//$('#downLoadExcel').prop('target','_blank');
					$('#downLoadExcel').prop('href',"${pageContext.request.contextPath}/reportvodDo/downLoadVodAreaDetail"
							+"?getWhat=getActives" + getParams(thisRow));
					
					$('#cellMenu .menu-line').hide(); 
				}
			}
		});
			
	});
	
	function getParams(thisRow){
		var param = "";
		if(thisRow.customerTypeId != null){
			param +="&customerTypeIds=" + thisRow.customerTypeId;
		} else {
			$("#searchForm :input[name='customerTypeIds']").each(function(){
				if($(this).val() != ''){
					param +="&customerTypeIds=" + $(this).val();
				}
			});
		}
		if(thisRow.townId != null){
			param +="&townIds=" + thisRow.townId;
		} else {
			$("#searchForm :input[name='townIds']").each(function(){
				if($(this).val() != ''){
					param +="&townIds=" + $(this).val();
				}
			});
		}
		if(thisRow.communityId != null){
			param +="&communityIds=" + thisRow.communityId;
		} else {
			$("#searchForm :input[name='communityIds']").each(function(){
				if($(this).val() != ''){
					param +="&communityIds=" + $(this).val();
				}
			});
		}
		if(thisRow.villageId != null){
			param +="&villageIds=" + thisRow.villageId;
		} else {
			$("#searchForm :input[name='villageIds']").each(function(){
				if($(this).val() != ''){
					param +="&villageIds=" + $(this).val();
				}
			});
		}
		param +="&activeDate1=" + thisRow.condition.activeDate1;
		param +="&activeDate2=" + thisRow.condition.activeDate2;
		if(thisRow.condition.activeTimes1 > 0){
			param +="&activeTimes1=" + thisRow.condition.activeTimes1;
		}
		if(thisRow.condition.activeTimes2 > 0){
			param +="&activeTimes2=" + thisRow.condition.activeTimes2;
		}
		if(thisRow.condition.onlineTimes1 > 0){
			param +="&onlineTimes1=" + thisRow.condition.onlineTimes1;
		}
		if(thisRow.condition.onlineTimes2 > 0){
			param +="&onlineTimes2=" + thisRow.condition.onlineTimes2;
		}
		return param;
	}

	function searchFun() {
		if(! $('#searchForm').form('validate')){
			return false;
		}
		$('#searchForm').removeAttr("action");
		$('#searchForm').removeAttr("target");
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function statVodArea() {
		if(! $('#searchForm').form('validate')){
			return false;
		}
		$('#searchForm').attr("action","${pageContext.request.contextPath}/reportvodDo/getVodAreaChart/m");
		$('#searchForm').attr("target","_blank");
		$('#searchForm').attr("method","post");
		$('#searchForm').submit();
	}
	function cleanFun() {
		$('#searchForm').removeAttr("action");
		$('#searchForm').removeAttr("target");
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
	
	function addTab(params) {
		params.newtab = true;
		window.parent.addTab(params);
		
	}
	
	function downloadFun(){
		if(! $('#searchForm').form('validate')){
			return false;
		}
		$('#searchForm').attr("action","${pageContext.request.contextPath}/reportCustomerUserDo/downLoad/vodarea_active");
		$('#searchForm').attr("method","post");
		$('#searchForm').submit();
	}
</script>
</head>
<body>

<div id="queryPanels" class="easyui-layout" data-options="fit:true,border:false,title:'查询条件'">
	<div style="overflow:hidden;padding-bottom:5px"
		data-options="region:'north',title:'查询条件',onCollapse:function(){$('#queryPanels div.panel-title').text('查询条件');}">
			<%-- <form id="searchForm" action="${pageContext.request.contextPath}/reportvodDo/getVodArea/actives" target="_blank" method="get"> --%>
			<form id="searchForm" style="margin:0px">
				<table class="table table-hover table-condensed" style="margin:0px">
					<jsp:include page="condition_area.jsp"></jsp:include>
					<tr>
						<th>活跃日期:</th>
						<td>
							<input name="activeDate1" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="span2 easyui-validatebox"
							data-options="required:true"/>
							至
							<input name="activeDate2" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="span2 easyui-validatebox"
							data-options="required:true"/>
						</td>
						<td colspan="2">
							<table>
							<tr>
								<th style="width:65px;border:0px;padding:0px;margin:0px">登录次数:</th>
								<td style="border:0px;padding:0px;margin:0px">
									<input name="onlineTimes1" class="easyui-numberspinner" data-options="editable:true" style="width:80px">至
									<input name="onlineTimes2" class="easyui-numberspinner" data-options="editable:true" style="width:80px">
									<!-- <input class="span1" name="onlineTimes1"/>至<input class="span1" name="onlineTimes2"/> -->
								</td>
								<th style="width:65px;border:0px;padding-left:20px;padding-right:0px;">点播次数:</th>
								<td style="border:0px;padding:0px;margin:0px">
									<input name="activeTimes1" class="easyui-numberspinner" data-options="editable:true" style="width:80px">至
									<input name="activeTimes2" class="easyui-numberspinner" data-options="editable:true" style="width:80px">
									<!-- <input class="span1" name="vodTimes1"/>至<input class="span1" name="vodTimes2"/> -->
								</td>
							</tr>
							</table>
						</td>
					</tr>
					<tr>
						
					</tr>
				</table>
			</form> 
		</div>
		
		<div id="dgTab" data-options="region:'center',border:false" style="margin-top:1px">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="statVodArea();">分析图</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询数据</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_go',plain:true" onclick="downloadFun();">下载Excel</a>	
	</div>
 
	<div id="cellMenu" class="easyui-menu" style="width: 120px; display: none;">
		<a id="showInNewWindow" href="###">新窗口打开明细</a>
		<br/>
		<a id="downLoadExcel" href="###">Excel下载明细</a>
	</div>
</body>
</html>