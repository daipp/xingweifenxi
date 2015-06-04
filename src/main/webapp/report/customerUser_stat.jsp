<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>客户-用户情况月度统计</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/reportCustomerUserDo/getCustomerUser/'+'${mord}',
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
				field : 'repDate',
				title : '日期',
				width : 80,
				sortable : true
			},{
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
				width : 80,
				sortable : true
			}, {
				field : 'customerType',
				title : '客户类型',
				width : 120,
				sortable : true
			}]], 
			columns :[[{
				field : 'customers',
				title : '客户数',
				headTip:'周期末，含有非销户用户的客户',
				width : 70,
				sortable : true
			}, {
				field : 'dvbUsers',
				title : '基本型用户数',
				headTip:'周期末，非销户的基本型用户数',
				width : 100,
				sortable : true
			}, {
				field : 'vodUsers',
				title : '交互型用户数',
				headTip:'周期末，非销户的交互型用户数',
				width : 100,
				sortable : true
			}, {
				field : 'bbUsers',
				title : '宽带用户数',
				headTip:'周期末，非销户的宽带用户数',
				width : 100,
				sortable : true
			}, {
				field : 'newDvbUsers',
				title : '基本型用户数净增',
				headTip:'本周期末的统计-上个周期末的统计',
				width : 120,
				sortable : true
			}, {
				field : 'onlineVodUsers',
				title : '交互在线用户数',
				headTip:'在这个周期内任意一天，曾订购过点播产品的',
				width : 120,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getOnlineVodUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'交互在线用户数'});\">"+value+"</a>";
				}
			} , {
				field : 'onlineBbUsers',
				title : '宽带在线用户数',
				headTip:'在这个周期内任意一天，曾订购过宽带产品的',
				width : 120,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getOnlineBbUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'宽带在线用户数'});\">"+value+"</a>";
				}
			}, {
				field : 'newVodUsers',
				title : '交互型用户数净增',
				headTip:'本周期末的统计-上个周期末的统计',
				width : 120
			} , {
				field : 'newOnlineVodUsers',
				title : '交互在线用户数净增',
				headTip:'本周期末的统计-上个周期末的统计',
				width : 120,
				sortable : true
			}, {
				field : 'offlineVodUsers',
				title : '交户离线用户数',
				headTip:'在月末这一天统计总量',
				width : 120,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getOfflineVodUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'交户离线用户数'});\">"+value+"</a>";
				}
			}, {
				field : 'newBbUsers',
				title : '宽带用户数净增',
				headTip:'本周期末的统计-上个周期末的统计',
				width : 120,
				sortable : true
			}, {
				field : 'newOnlineBbUsers',
				title : '宽带在线用户数净增',
				headTip:'本周期末的统计-上个周期末的统计',
				width : 120,
				sortable : true
			}, {
				field : 'offlineBbUsers',
				title : '宽带离线用户数',
				headTip:'在月末这一天统计总量',
				width : 120,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getOfflineBbUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'宽带离线用户数'});\">"+value+"</a>";
				}
			}] ],
			
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
		
		if(thisRow.repDate != null){
			param +="&repDate=" + thisRow.repDate;
		}
		
		$("#searchForm :input[name='repDate1']").each(function(){
			if($(this).val() != ''){
				param +="&repDate1=" + $(this).val();
			}
		});
		$("#searchForm :input[name='repDate2']").each(function(){
			if($(this).val() != ''){
				param +="&repDate2=" + $(this).val();
			}
		});
		$("#searchForm :input[name='reportRange']").each(function(){
			if($(this).val() != ''){
				param +="&reportRange=" + $(this).val();
			}
		});
		return param;
	}

	function searchFun() {
		$('#searchForm').removeAttr("action");
		$('#searchForm').removeAttr("target");
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function statVodArea() {
		$('#searchForm').attr("action","${pageContext.request.contextPath}/reportCustomerUserDo/getCustomerUserChart/"+"${mord}");
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
		$('#searchForm').attr("action","${pageContext.request.contextPath}/reportCustomerUserDo/downLoad/"+"${mord}");
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
						<td>
							<select id="repDate1" name="repDate1"  style="width:165px" class="easyui-combobox"  >
									<c:forEach items="${mss }" var="t">
										<option value="${t[0]}">${t[1]}</option>
									</c:forEach>
							</select>
						至
							<select id="repDate2" name="repDate2" style="width:165px" class="easyui-combobox">
									<c:forEach items="${mes }" var="t">
										<option value="${t[0]}">${t[1]}</option>
									</c:forEach>
							</select>
							<input type="checkbox" name="showRepDate" checked="checked" value="true" onclick="return false;"> 分行显示
						</td>
					</tr>
				</table>
				<input type="hidden" id="reportRange" name = "reportRange" value="${mord}"/>
			</form>
		</div>
		<div id="dgTab" data-options="region:'center',border:false" style="margin-top:1px">
			<table id="dataGrid" style="min-width:1200px"></table>
		</div>
	</div>
	
	
	
	
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="statVodArea();">分析图</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询数据</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_go',plain:true" onclick="downloadFun();">下载Excel</a>	
	</div>
 
	<div id="cellMenu" class="easyui-menu" style="width: 130px; display: none;">
		<a id="showInNewWindow" href="###">新窗口打开明细</a>
		<br/>
		<a id="downLoadExcel" href="###">Excel下载明细</a>
	</div>
</body>
</html>