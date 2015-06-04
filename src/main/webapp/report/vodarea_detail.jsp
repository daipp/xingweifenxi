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
			url : '${pageContext.request.contextPath}/reportvodDo/getVodArea/detail',
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
				width : 100,
				sortable : true
			},{
				field : 'userId',
				title : '用户编号',
				width : 80,
				formatter: function(value,row,index){
					return '<a href=&quot;###&quot;>'+value+'</a>';
				}
			}, {
				field : 'customerName',
				title : '姓名',
				width : 80
			},  ] ],
							
			columns : [ [  {
				field : 'fulladdress',
				title : '具体地址',
				width : 100
			}, {
				field : 'phone',
				title : '电话',
				width : 50
			}, {
				field : 'mobile',
				title : '手机',
				width : 80
			}, {
				field : 'stb',
				title : '机顶盒',
				width : 100
			}, {
				field : 'userGroup',
				title : '用户群',
				width : 100
			}, {
				field : 'crTime',
				title : '开户日期',
				width : 100
			}, {
				field : 'maxEndTime',
				title : '产品截止日期',
				width : 100
			}, {
				field : 'maxBookTime',
				title : '最后订购日期',
				width : 100
			}, {
				field : 'userState',
				title : '用户状态',
				width : 100
			}, {
				field : 'onlineTimes',
				title : '登录次数',
				width : 50
			}, {
				field : 'activeTimes',
				title : '点播次数',
				width : 50
			}, {
				field : 'bbUnExpiredUsers',
				title : '宽带未到期',
				headTip:'该客户下，所有未到期的宽带用户数',
				width : 50
			}, {
				field : 'vodUnExpiredUsers',
				title : '点播未到期',
				headTip:'该客户下，所有未到期的点播用户数',
				width : 50
			}, {
				field : 'bbExpiredUsers',
				title : '宽带已到期',
				headTip:'该客户下，所有已到期的宽带用户数',
				width : 50
			}, {
				field : 'vodExpiredUsers',
				title : '点播已到期',
				headTip:'该客户下，所有已到期的点播用户数',
				width : 50
			}, {
				field : 'bbExpiringUsers',
				title : '宽带将到期',
				headTip:'该客户下，所有将到期的宽带用户数',
				width : 50
			}, {
				field : 'vodExpiringUsers',
				title : '点播将到期',
				headTip:'该客户下，所有将到期的点播用户数',
				width : 50
			} ] ],
			
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
				$(this).datagrid('tooltip');
				$(this).datagrid('showHeadTip');
				userIdBinds();
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
					var thisRow = dataGrid.datagrid('getRows')[rowIndex];
					$('#showInNewWindow').prop('target','_blank');
					$('#showInNewWindow').prop('href',"${pageContext.request.contextPath}/reportvodDo/getUser/"+thisRow.userId);

					$('#showInNewWindow2').prop('target','_blank');
					$('#showInNewWindow2').prop('href',"http://10.8.70.11/boss/User/DocDo?goto=view&userId="+thisRow.userId);
					
					$('#cellMenu .menu-line').hide(); 
				}
			}
		});

		$("span:contains('分行显示')").hide();
		$(":checkbox[name*='show']").hide();
			
	});

	function userIdBinds(){
		$(".datagrid-cell-c1-userId a").click(function(){
			if(window.parent != null){
				var url='${pageContext.request.contextPath}/reportvodDo/getUser/' + $(this).text();
				window.parent.addTab({url:url,title:'用户详情',newtab:true});
			}
			return false;
		});
	}

	function searchFun() {
		$('#searchForm').removeAttr("action");
		$('#searchForm').removeAttr("target");
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function downloadFun() {
		
		var o = "";
		$.each($('#searchForm').serializeArray(), function(index) {
			if(this['value']==''){
				return;
			}
			o += "&"+this['name']+"="+this['value'];
		});
		
		o = o.substr(1);
		
		window.location = "${pageContext.request.contextPath}/reportvodDo/downLoadVodArea/detail?" + o
		//$('#searchForm').prop("action","${pageContext.request.contextPath}/reportvodDo/downLoadVodArea/detail");
		//$('#searchForm').submit();
		//$('#searchForm').prop("target",);
		//dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function statVodArea() {
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
</script>
</head>
<body>



	<div id="queryPanels" class="easyui-layout" data-options="fit:true,border:false,title:'查询条件'">
		<div style="overflow:hidden;padding-bottom:5px"
			data-options="region:'north',title:'查询条件',onCollapse:function(){$('#queryPanels div.panel-title').text('查询条件');}">
			<%-- <form id="searchForm" action="${pageContext.request.contextPath}/reportvodDo/getVodArea/m" target="_blank" method="get"> --%>
			<form id="searchForm" style="margin:0px" method="post">	
				<table class="table table-hover table-condensed" style="margin:0px">
					<jsp:include page="condition_area.jsp"></jsp:include>
					<tr>
						<th>活跃日期:</th>
						<td>
							<input name="activeDate1" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="span2 easyui-validatebox" required="required"/>
							至
							<input name="activeDate2" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="span2"/>
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
						<th>到期日期:</th>
						<td colspan="3">
							<input name="expiredDate1" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="span2 easyui-validatebox" required="required"/>
							至
							<input name="expiredDate2" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="span2"/>
						</td>
					</tr>
					
				</table>
			</form>
		</div>
		<div id="dgTab" data-options="region:'center',border:false" style="margin-top:1px">
			<table id="dataGrid" style="overflow: auto;"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询数据</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<!-- 
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_go',plain:true" onclick="downloadFun();">下载数据</a> 
		-->
	</div>
 
	<div id="cellMenu" class="easyui-menu" style="width: 120px; display: none;">
		<a id="showInNewWindow" href="###">新窗口打开本地明细</a>
		<br/>
		<a id="showInNewWindow2" href="###">新窗口打开BOSS明细</a>
	</div>
</body>
</html>