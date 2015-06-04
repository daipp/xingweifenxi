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
			url : '${pageContext.request.contextPath}/reportAreaDo/get/d',
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
			} ]],
			
			columns : [ [{
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
				width : 50,
				sortable : true
			} , {
				field : 'village',
				title : '小区',
				width : 100,
				sortable : true
			}, {
				field : 'customerType',
				title : '客户类型',
				width : 120,
				sortable : true
			}, {
				field : 'customers',
				title : '客户数',
				headTip:'含有状态是非销户、非罚停用户的客户',
				width : 60,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getCustomers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-客户数'});\">"+value+"</a>";
					
				}
			}, {
				field : 'hdstbs',
				title : '高清用户',
				headTip:'机顶盒类型是高清的,用户状态是非销户、非罚停的',
				width : 60,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getHdstbs" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-高清用户'});\">"+value+"</a>";
				}
			}, 
			
						
			{
				field : 'dvbUsers',
				title : '基本',
				headTip:'用户状态是非销户、非罚停的',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getDvbUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-交互型用户'});\">"+value+"</a>";
				}
			}, {
				field : 'vodUsers',
				title : '交互',
				headTip:'用户状态是非销户、非罚停的',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getVodUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-交互型用户'});\">"+value+"</a>";
				}
			},  {
				field : 'bbUsers',
				title : '宽带',
				headTip:'用户状态是非销户、非罚停的',
				width : 60,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getBbUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-宽带用户'});\">"+value+"</a>";
					
				}
			}, {
				field : 'analogUsers',
				title : '模拟',
				headTip:'用户状态是非销户、非罚停的',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getAnalogUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-模拟用户'});\">"+value+"</a>";
				}
			}, {
				field : 'dvbUsers0',
				title : '正常基本',
				headTip:'用户状态是非销户、非罚停的',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getDvbUsers0" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-交互型用户'});\">"+value+"</a>";
				}
			}, {
				field : 'vodUsers0',
				title : '正常交互',
				headTip:'用户状态是非销户、非罚停的',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getVodUsers0" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-交互型用户'});\">"+value+"</a>";
				}
			},  {
				field : 'bbUsers0',
				title : '正常宽带',
				headTip:'用户状态是非销户、非罚停的',
				width : 60,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getBbUsers0" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-宽带用户'});\">"+value+"</a>";
					
				}
			}, {
				field : 'analogUsers0',
				title : '正常模拟',
				headTip:'用户状态是非销户、非罚停的',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getAnalogUsers0" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-模拟用户'});\">"+value+"</a>";
				}
			}, 
			
			
			{
				field : 'dvbBooks',
				title : '付费在线',
				headTip:'付费频道节目在线的用户总数',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getDvbBooks" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-付费在线'});\">"+value+"</a>";
				}
			},  {
				field : 'vodBooks',
				title : '点播在线',
				headTip:'点播节目在线的用户总数',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getVodBooks" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-点播在线'});\">"+value+"</a>";
				}
			},  {
				field : 'bbBooks',
				title : '宽带在线',
				headTip:'宽带产品在线的宽带用户总数',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getBbBooks" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-宽带在线'});\">"+value+"</a>";
				}
			},  {
				field : 'dvbBooksNew',
				title : '付费在线_新',
				headTip:'付费频道节目在线的新开用户数',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getDvbBooksNew" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-付费在线_新'});\">"+value+"</a>";
				}
			},  {
				field : 'vodBooksNew',
				title : '点播在线_新',
				headTip:'点播节目在线的新开用户数',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getVodBooksNew" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-点播在线_新'});\">"+value+"</a>";
				}
			},  {
				field : 'bbBooksNew',
				title : '宽带在线_新',
				headTip:'宽带产品在线的宽带新开用户数',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getBbBooksNew" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-宽带在线_新'});\">"+value+"</a>";
				}
			},  
			
			{
				field : 'hostStarts',
				title : '主开',
				headTip:'有开机受理的主终端',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getHostStarts" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-主开'});\">"+value+"</a>";
				}
			}, {
				field : 'hostStops',
				title : '主停',
				headTip:'有停机受理的主终端',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getHostStops" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-主停'});\">"+value+"</a>";
				}
			},{
				field : 'hostQuits',
				title : '主销',
				headTip:'有销户受理的主终端',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getHostQuits" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-主销'});\">"+value+"</a>";
				}
			},{
				field : 'hostNormal',
				title : '正常主',
				headTip:'正常主终端总计',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getHostNormal" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-正常主'});\">"+value+"</a>";
				}
			}, {
				field : 'hostStoped',
				title : '已停主',
				headTip:'停机主终端总计',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getHostStoped" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-已停主'});\">"+value+"</a>";
				}
			}, {
				field : 'hostUnpay1',
				title : '欠1年主',
				headTip:'收视费1年(含当年)未交的主终端',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getHostUnpay1" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-欠1年主'});\">"+value+"</a>";
				}
			}, {
				field : 'hostUnpay2',
				title : '欠2年主',
				headTip:'收视费2年(含当年)未交的主终端',
				width : 70,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getHostUnpay2" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-欠1年主'});\">"+value+"</a>";
				}
			}, 
			{
				field : 'activeUsers',
				title : '活跃用户',
				headTip:'有完整的点播记录',
				width : 60,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getActiveUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-活跃用户'});\">"+value+"</a>";
				}
			}, {
				field : 'onlineBookedUsers',
				title : '登录用户(在线)',
				headTip:'有首页访问记录,且有正常未到期的点播产品',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getOnlineBookedUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-登录用户(在线)'});\">"+value+"</a>";
				}
			}, {
				field : 'onlineUnbookUsers',
				title : '登录用户(离线)',
				headTip:'有首页访问记录,但无有效点播产品',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/areauserlist.jsp?getWhat=getOnlineUnbookUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'区域日报明细-登录用户(离线)'});\">"+value+"</a>";
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
					//alert(getParams(thisRow));
					$('#showInNewWindow').click(function(){
						requestDetailOpen('get'+field.substring(0,1).toUpperCase()+field.substring(1),thisRow);
					})
					$('#downLoadExcel').click(function(){
						requestDetailDownload('get'+field.substring(0,1).toUpperCase()+field.substring(1),thisRow);
					})
/*					
					$('#showInNewWindow').prop('target','_blank');
					$('#showInNewWindow').prop('href',"${pageContext.request.contextPath}/report/areauserlist.jsp"
							+"?getWhat="+('get'+field.substring(0,1).toUpperCase()+field.substring(1))
							+ getParams(thisRow));

					//$('#downLoadExcel').prop('target','_blank');
					$('#downLoadExcel').prop('href',"${pageContext.request.contextPath}/reportvodDo/downLoadVodAreaDetail"
							+"?getWhat="+('get'+field.substring(0,1).toUpperCase()+field.substring(1))
							+ getParams(thisRow));
*/					
					$('#cellMenu .menu-line').hide(); 
				}
			}
		});

	/*	$("dgTab").delegate("a[newtab='true']","click",function(){
			var t = $(this).prop("title");
			var u = $(this).prop("href");
			addTab({url:u,title:t});
			stopDefault(e); 
			return false;
		});
		
		$("#dgTab a[newtab='true']").live('click',function(){
			var t = $(this).prop("title");
			var u = $(this).prop("href");
			addTab({url:u,title:t});
			stopDefault(e); 
			return false;
		});
	*/

	});
	
	function getParams(thisRow){
		var param = "";
		if(thisRow.repDate !=  null){
			param +="&repDate=" + thisRow.repDate;
		}
		
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
		
		if(thisRow.repDate != null){
			param +="&repDate=" + thisRow.repDate;
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
		$("#requestDetailForm input[name='repDate']").remove();
		
		if(thisRow.repDate !=  null){
			$("#requestDetailForm").append("<input name='repDate' value='" + thisRow.repDate + "' />");
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
		$("#searchForm input[name='getWhat']").val(getWhat);
		$('#searchForm').attr("action","${pageContext.request.contextPath}/reportAreaDo/geChart/d");
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
		$('#searchForm').attr("action","${pageContext.request.contextPath}/reportCustomerUserDo/downLoad/vodarea_d");
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
			<form id="searchForm" style="margin:0px">
				<table class="table table-hover table-condensed">
					<jsp:include page="condition_area.jsp"></jsp:include>
					<tr>
						<th>日期:</th>
						<td colspan="3">
							<input name="repDate1" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="span2 easyui-validatebox" data-options="required:true"/>
							至
							<input name="repDate2" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="span2"/>
							<input type="checkbox" name="showRepDate" checked="checked" value="true" onclick="return false;"> 分行显示
						</td>
					</tr>
				</table>
			</form>
			<form id="requestDetailForm" style="display:none" method="post" >
				<input type="hidden" name="report" value="AreaDateReport">
				<input type="hidden" name="getWhat" value="">
			</form>
		</div>
		<div id="dgTab" data-options="region:'center',border:false" style="margin-top:1px;">
			<table id="dataGrid" style="min-width:1200px"></table>
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