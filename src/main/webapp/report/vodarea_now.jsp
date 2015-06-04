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
			url : '${pageContext.request.contextPath}/reportvodDo/getVodArea/now',
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
				field : 'repDate',
				title : '日期',
				width : 80,
				sortable : true
			} ,{
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
				title : '活跃用户',
				headTip:'有完整的点播记录',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getActiveUsers"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-活跃用户'});\">"+value+"</a>";
				}
			}, {
				field : 'onlineBookedUsers',
				title : '登录用户(在线)',
				headTip:'有首页访问记录,且有正常未到期的点播产品',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getOnlineBookedUsers"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-登录用户(在线)'});\">"+value+"</a>";
				}
			}, {
				field : 'onlineUnbookUsers',
				title : '登录用户(离线)',
				headTip:'有首页访问记录,但无有效点播产品',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getOnlineUnbookUsers"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-登录用户(离线)'});\">"+value+"</a>";
				}
			}, {
				field : 'offlineBookedUsers',
				title : '未登用户(在线)',
				headTip:'无首页访问记录,但有正常未到期的点播产品',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getOfflineBookedUsers"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-未登用户(在线)'});\">"+value+"</a>";
				}
			}, {
				field : 'offlineUnbookUsers',
				title : '未登用户(离线)',
				headTip:'无首页访问记录,且无有效点播产品',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getOfflineUnbookUsers"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-未登用户(离线)'});\">"+value+"</a>";
				}
			}, {
				field : 'inactiveOnlineUsers',
				title : '不活跃用户(登录)',
				headTip:'无任何点播记录,但有首页访问记录',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getInactiveOnlineUsers"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-不活跃用户(登录)'});\">"+value+"</a>";
				}
			}, {
				field : 'inactiveOfflineUsers',
				title : '不活跃用户(未登)',
				headTip:'无任何点播记录,且无首页访问记录',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getInactiveOfflineUsers"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-不活跃用户(未登录)'});\">"+value+"</a>";
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
							+"?getWhat="+('get'+field.substring(0,1).toUpperCase()+field.substring(1))
							+ getParams(thisRow));

					//$('#downLoadExcel').prop('target','_blank');
					$('#downLoadExcel').prop('href',"${pageContext.request.contextPath}/reportvodDo/downLoadVodAreaDetail"
							+"?getWhat="+('get'+field.substring(0,1).toUpperCase()+field.substring(1))
							+ getParams(thisRow));
					
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
		if(thisRow.condition.repDate1 != null){
			param +="&repDate1=" + thisRow.condition.repDate1;
		}
		if(thisRow.condition.repDate2 != null){
			param +="&repDate2=" + thisRow.condition.repDate2;
		}
		return param;
	}

	function searchFun() {
		$('#searchForm').removeAttr("action");
		$('#searchForm').removeAttr("target");
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function statVodArea() {
		$('#searchForm').attr("action","${pageContext.request.contextPath}/reportvodDo/getVodAreaChart/now");
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
		$('#searchForm').attr("action","${pageContext.request.contextPath}/reportCustomerUserDo/downLoad/vodarea_now");
		$('#searchForm').attr("method","post");
		$('#searchForm').submit();
	}
</script>
</head>
<body>

	<div id="queryPanels" class="easyui-layout" data-options="fit:true,border:false,title:'查询条件'">
		<div style="overflow:hidden;padding-bottom:5px"
			data-options="region:'north',title:'查询条件',onCollapse:function(){$('#queryPanels div.panel-title').text('查询条件');}">
			<%-- <form id="searchForm" action="${pageContext.request.contextPath}/reportvodDo/getVodArea/now" target="_blank" method="get"> --%>
			<form id="searchForm" style="margin:0px">	
				<table class="table table-hover table-condensed">
					<jsp:include page="condition_area.jsp"></jsp:include>
					<tr>
						<th>日期:</th>
						<td colspan="5">
							<input name="repDate1" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="span2 easyui-validatebox" data-options="required:true"/>
							至
							<input name="repDate2" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="span2"/>
						</td>
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