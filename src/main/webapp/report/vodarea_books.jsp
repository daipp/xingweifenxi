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
	$(function(){
		dataGrid= $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/reportRefeeDo/getVodRefee/books',
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
				width : 120,
				sortable : true
			}, {
				field : 'customerType',
				title : '客户类型',
				width : 150,
				sortable : true
			} ]],
			
			columns : [ [   {
				field : 'vodExpiredBook',
				title : '点播离线订购',
				headTip:'在日期范围之前到期,且在日期范围内订购的互动用户',
				width : 120,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getVodOfflineBookUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'订购实时统计-互动用户(离线订购)'});\">"+value+"</a>";
				}
			}, {
				field : 'vodExpiringBook',
				title : '点播到期续费',
				headTip:'在日期范围之内到期,且在日期范围内订购的互动用户',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getVodExpiredBookUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'订购实时统计-互动用户(到期续订)'});\">"+value+"</a>";
				}
			} , {
				field : 'vodPreBook',
				title : '点播预订购',
				headTip:'在日期范围之后到期,且在日期范围内订购的互动用户',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getVodUnExpiredBookUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'订购实时统计-互动用户(未到期订购)'});\">"+value+"</a>";
				}
			} , {
				field : 'bbExpiredBook',
				title : '宽带离线订购',
				headTip:'在日期范围之前到期,且在日期范围内订购的宽带用户',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getBbOfflineBookUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'订购实时统计-宽带用户(离线订购)'});\">"+value+"</a>";
				}
			} , {
				field : 'bbExpiringBook',
				title : '宽带到期续费',
				headTip:'在日期范围之内到期,且在日期范围内订购的宽带用户',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getBbExpiredBookUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'订购实时统计-宽带用户(到期续订)'});\">"+value+"</a>";
				}
			}, {
				field : 'bbPreBook',
				title : '宽带预订购',
				headTip:'在日期范围之后到期,且在日期范围内订购的宽带用户',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					var url="${pageContext.request.contextPath}/report/vodareauserlist.jsp?getWhat=getBbUnExpiredBookUsers" + getParams(row);
					return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'订购实时统计-宽带用户(未到期续订)'});\">"+value+"</a>";
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
			onClickCell:function(rowIndex, field, value){
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
				
			},
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
		
		$("#searchForm :input[name='bookDate1']").each(function(){
			if($(this).val() != ''){
				param +="&bookDate1=" + $(this).val();
			}
		});
		$("#searchForm :input[name='bookDate2']").each(function(){
			if($(this).val() != ''){
				param +="&bookDate2=" + $(this).val();
			}
		});
		
		return param;
	}

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
	function addTab(params) {
		params.newtab = true;
		window.parent.addTab(params);
		
	}
	function downloadFun(){
		if(! $('#searchForm').form('validate')){
			return false;
		}
		$('#searchForm').attr("action","${pageContext.request.contextPath}/reportCustomerUserDo/downLoad/vodarea_books");
	///	$('#searchForm').attr("target","_blank");
		$('#searchForm').attr("method","post");
		$('#searchForm').submit();
	}
</script>
</head>
<body>
<div id="queryPanels" class="easyui-layout" data-options="fit:true,border:false,title:'查询条件'" style="min-width:1250px">
	<div style="overflow:hidden;padding-bottom:5px"
		data-options="region:'north',title:'查询条件',border:false,onCollapse:function(){$('#queryPanels div.panel-title').text('查询条件');}">
		<form id="searchForm" style="margin:0px">
			<table class="table table-hover table-condensed">
				<jsp:include page="condition_area.jsp"></jsp:include>
				<tr>
					<th style="width:70px">订购日期:</th>
					<td colspan="3">
						<input class="span2 easyui-validatebox" name="bookDate1" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						至
						<input class="span2 easyui-validatebox" name="bookDate2" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true" />
					
					</td>
				</tr>
			</table>
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