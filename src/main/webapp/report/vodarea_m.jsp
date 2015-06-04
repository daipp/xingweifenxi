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
			url : '${pageContext.request.contextPath}/reportAreaDo/get/m',
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
			frozenColumns : [[{
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
			} ]],
			
			columns : [ [  {
				field : 'repMonthDesc',
				title : '月份',
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
			}, 
			//以下是活跃度方面的内容:
			{
				field : 'activeUsers',
				title : '活跃用户',
				headTip:'点播次数>0',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getActiveUsers"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-活跃用户'});\">"+value+"</a>";
				}
			},  {
				field : 'onlineBooked',
				title : '登录用户(在线)',
				headTip:'有订购的,登录次数>0',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getOnlineBooked"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-登录用户(在线)'});\">"+value+"</a>";
				}
			}, {
				field : 'onlineUnbook',
				title : '登录用户(离线)',
				headTip:'无订购,登录次数>0',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getOnlineUnbook"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-登录用户(离线)'});\">"+value+"</a>";
				}
			}, {
				field : 'offlineBooked',
				title : '未登用户(在线)',
				headTip:'有订购,登录次数=0',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getOfflineBooked"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-未登用户(在线)'});\">"+value+"</a>";
				}
			}, {
				field : 'offlineUnbook',
				title : '未登用户(离线)',
				headTip:'未订购,登录次数=0',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getOfflineUnbook"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-未登用户(离线)'});\">"+value+"</a>";
				}
			}, {
				field : 'inactiveOnline',
				title : '不活跃用户(登录)',
				headTip:'有订购的,点播次数=0,登录次数>0',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getInactiveOnline"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-不活跃用户(登录)'});\">"+value+"</a>";
				}
			}, {
				field : 'inactiveOffline',
				title : '不活跃用户(未登)',
				headTip:'有订购的,点播次数=0,登录次数=0',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getInactiveOffline"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-不活跃用户(未登录)'});\">"+value+"</a>";
				}
			}, {
				field : 'vodExpiring',
				title : '点播到期',
				headTip:'点播本月到期的',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getVodExpiring"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-点播当月到期的'});\">"+value+"</a>";
				}
			}, {
				field : 'vodExpiringBook',
				title : '点播到期续费',
				headTip:'点播本月到期的,且有续费的',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getVodExpiringBook"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-点播到期续费'});\">"+value+"</a>";
				}
			}, {
				field : 'vodExpiredBook',
				title : '点播离线订购',
				headTip:'点播未订购过或之前就到期的,在本月有续费的',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getVodExpiredBook"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-点播离线订购'});\">"+value+"</a>";
				}
			}, {
				field : 'vodPreBook',
				title : '点播预订购',
				headTip:'点播在本月之后到期,但在本月有续费的',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getVodPreBook"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-点播预订购'});\">"+value+"</a>";
				}
			}, {
				field : 'vodBookRate',
				title : '点播续费率',
				headTip:'点播本月到期续费的 / 点播本月到期的',
				width : 100,
				sortable : false
			},
			//以下是宽带续费率方面的内容:
			{
				field : 'bbExpiring',
				title : '宽带到期',
				headTip:'宽带本月到期的',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getBbExpiring"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-宽带当月到期的'});\">"+value+"</a>";
				}
			}, {
				field : 'bbExpiringBook',
				title : '宽带到期续费',
				headTip:'宽带本月到期的,且有续费的',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getBbExpiringBook"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-宽带到期续费'});\">"+value+"</a>";
				}
			}, {
				field : 'bbExpiredBook',
				title : '宽带离线订购',
				headTip:'宽带未订购过或之前就到期的,在本月有续费的',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getBbExpiredBook"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-宽带离线订购'});\">"+value+"</a>";
				}
			}, {
				field : 'bbPreBook',
				title : '宽带预订购',
				headTip:'宽带在本月之后到期,但在本月有续费的',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getBbPreBook"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-宽带预订购'});\">"+value+"</a>";
				}
			}, {
				field : 'bbBookRate',
				title : '宽带续费率',
				headTip:'宽带本月到期续费的 / 点播本月到期的',
				width : 100,
				sortable : false
			},
			//以下都是针对客户-用户情况汇总报表
			{
				field : 'customers',
				title : '客户数',
				headTip:'客户数',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getCustomers"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-客户数'});\">"+value+"</a>";
				}
			}, {
				field : 'dvbUsers',
				title : '基本',
				headTip:'基本型用户数',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getDvbUsers"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-基本型用户数'});\">"+value+"</a>";
				}
			}, {
				field : 'vodUsers',
				title : '交互',
				headTip:'交互型用户数',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getVodUsers"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-交互型用户数'});\">"+value+"</a>";
				}
			}, {
				field : 'vodBooked',
				title : '交互在线',
				headTip:'在线(有订购)的交互型用户数',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getVodBooked"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-交互在线用户数'});\">"+value+"</a>";
				}
			}, {
				field : 'vodUnbook',
				title : '交互离线',
				headTip:'离线(未订购)的交互型用户数',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getVodUnbook"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-交互在线用户数'});\">"+value+"</a>";
				}
			}, {
				field : 'bbUsers',
				title : '宽带',
				headTip:'宽带用户数',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getBbUsers"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-宽带用户数'});\">"+value+"</a>";
				}
			}, {
				field : 'bbBooked',
				title : '宽带在线',
				headTip:'在线(有订购)的宽带用户数',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getBbBooked"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-宽带用户数'});\">"+value+"</a>";
				}
			}, {
				field : 'bbUnbook',
				title : '宽带离线',
				headTip:'离线(未订购)的宽带用户数',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getBbUnbook"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-宽带用户数'});\">"+value+"</a>";
				}
			}, {
				field : 'newDvb',
				title : '净增基本',
				headTip:'净增基本型用户数(与上月比)',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getNewDvb"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-净增基本'});\">"+value+"</a>";
				}
			}, {
				field : 'newVod',
				title : '净增交互',
				headTip:'净增交互型用户数(与上月比)',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getNewVod"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-净增基本'});\">"+value+"</a>";
				}
			}, {
				field : 'newBb',
				title : '净增宽带',
				headTip:'净增宽带用户数(与上月比)',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getNewBb"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-净增宽带'});\">"+value+"</a>";
				}
			}, {
				field : 'newVodBooked',
				title : '净增交互在线',
				headTip:'净增交互在线用户数(与上月比)',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getNewVodBooked"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-净增交互在线'});\">"+value+"</a>";
				}
			}, {
				field : 'newBbBooked',
				title : '净增宽带在线',
				headTip:'净增宽带在线用户数(与上月比)',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getNewBbBooked"+ getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域活跃度明细-净增宽带在线'});\">"+value+"</a>";
				}
			}
			
			] ],
			
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
				if(field != 'repMonthDesc' && field != 'town' && field != 'community'  && field != 'village' && field != 'customerType'){
					$('#cellMenu').menu('show', {
						left : window.event.pageX,
						top : window.event.pageY
					});
					var thisRow = dataGrid.datagrid('getRows')[rowIndex];
					//alert(getParams(thisRow));
					$('#showInNewWindow').click(function(){
						requestDetailOpen('get'+field.substring(0,1).toUpperCase()+field.substring(1),thisRow);
					})
					$('#downLoadExcel').click(function(){
						requestDetailDownload('get'+field.substring(0,1).toUpperCase()+field.substring(1),thisRow);
					})
					$('#cellMenu .menu-line').hide(); 
				}
				
				/* if(field != 'repDate' && field != 'town' && field != 'community'  && field != 'village' && field != 'customerType'){
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
				} */
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
		if(thisRow.repDateMonth != null){
			param +="&queryMonth=" + thisRow.repDateMonth;
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
	


	function requestDetailFormInit(getWhat,thisRow){
		$("#requestDetailForm input[name='getWhat']").val(getWhat);
		$("#requestDetailForm input[name='customerTypeIds']").remove();
		$("#requestDetailForm input[name='townIds']").remove();
		$("#requestDetailForm input[name='communityIds']").remove();
		$("#requestDetailForm input[name='villageIds']").remove();
		$("#requestDetailForm input[name='repMonth']").remove();
		
		if(thisRow.repDate !=  null){
			$("#requestDetailForm").append("<input name='repMonth' value='" + thisRow.repMonthDesc + "' />");
		}
		if(thisRow.customerTypeId != null){
			$("#requestDetailForm").append("<input name='customerTypeIds' value='" + thisRow.customerTypeId + "' />");
		} else {
			//$("#requestDetailForm").append($("#searchForm :input[name='customerTypeIds']"));
			$("#searchForm :input[name='customerTypeIds']").each(function(){
				if($(this).val() != ''){
					$("#requestDetailForm").append("<input name='customerTypeIds' value='" + $(this).val() + "' />");
				}
			});
		}
		if(thisRow.townId != null){
			$("#requestDetailForm").append("<input name='townIds' value='" + thisRow.townId + "' />");
		} else {
			//$("#requestDetailForm").append($("#searchForm :input[name='townIds']"));
			$("#searchForm :input[name='townIds']").each(function(){
				if($(this).val() != ''){
					$("#requestDetailForm").append("<input name='townIds' value='" + $(this).val() + "' />");
				}
			});
		}
		if(thisRow.communityId != null){
			$("#requestDetailForm").append("<input name='communityIds' value='" + thisRow.communityId + "' />");
		} else {
			//$("#requestDetailForm").append($("#searchForm :input[name='communityIds']"));
			$("#searchForm :input[name='communityIds']").each(function(){
				if($(this).val() != ''){
					$("#requestDetailForm").append("<input name='communityIds' value='" + $(this).val() + "' />");
				}
			});
		}
		if(thisRow.villageId != null){
			$("#requestDetailForm").append("<input name='villageIds' value='" + thisRow.villageId + "' />");
		} else {
			//$("#requestDetailForm").append($("#searchForm :input[name='villageIds']"));
			$("#searchForm :input[name='villageIds']").each(function(){
				if($(this).val() != ''){
					$("#requestDetailForm").append("<input name='villageIds' value='" + $(this).val() + "' />");
				}
			});
		}
	}

	
	function requestDetailOpen(getWhat,thisRow){
		requestDetailFormInit(getWhat,thisRow);
		$('#requestDetailForm').attr("action","${pageContext.request.contextPath}/report/areauserlist.jsp");
		$('#requestDetailForm').attr("target","_blank");
		$('#requestDetailForm').submit();
	}
	function requestDetailDownload(getWhat,thisRow){
		requestDetailFormInit(getWhat,thisRow);
		$('#requestDetailForm').attr("action","${pageContext.request.contextPath}/reportAreaDo/downloadUserList");
		$('#requestDetailForm').attr("target","_blank");
		$('#requestDetailForm').submit();
	}
	
	function statVodArea() {
		if(! $('#searchForm').form('validate')){
			return false;
		}
		$('#searchForm').attr("action","${pageContext.request.contextPath}/reportAreaDo/getChart/m");
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
		$('#searchForm').attr("action","${pageContext.request.contextPath}/reportAreaDo/downLoad/m");
	///	$('#searchForm').attr("target","_blank");
		$('#searchForm').attr("method","post");
		$('#searchForm').submit();
	}
</script>
</head>
<body>

	<div id="queryPanels" class="easyui-layout" data-options="fit:true,border:false,title:'查询条件'">
		<div style="overflow:hidden;padding-bottom:5px"
			data-options="region:'north',title:'查询条件',onCollapse:function(){$('#queryPanels div.panel-title').text('查询条件');}">
			<%-- <form id="searchForm" action="${pageContext.request.contextPath}/reportvodDo/getVodArea/m" target="_blank" method="get"> --%>
			<form id="searchForm" style="margin:0px">	
				<table class="table table-hover table-condensed">
					<jsp:include page="condition_area.jsp"></jsp:include>
					<tr>
						<th>月份:</th>
						<td colspan="3">
							<input name="repMonth1" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM'})" class="span2 easyui-validatebox" data-options="required:true"/>
							至
							<input name="repMonth2" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM'})" class="span2" />
							<input type="checkbox" name="showRepDate" checked="checked" value="true" onclick="return false;"> 分行显示
						</td>
					</tr>
				</table>
			</form>
			<form id="requestDetailForm" style="display:none" method="post" >
				<input type="hidden" name="report" value="AreaMonthReport">
				<input type="hidden" name="getWhat" value="">
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