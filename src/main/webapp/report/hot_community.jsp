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
		parent.$.messager.progress('close');
	});
	function timeSearch(){//时长
	
		 $('#query_tabs').tabs('unselect','次数TopN');
		 $('#query_tabs').tabs('select','时长TopN');
		 setTableName();
		  $('#dg2').datagrid({   
		 	    url:'${pageContext.request.contextPath}/reportHotDo/getReportHotCommunity/time', 
		 	   	queryParams:$.serializeObject($('#searchForm')),		 	    			
		 	   	pagination :true,
			    pageSize:20,
			    pageList:[10,20,30,40],
			    iconCls: 'icon-save',
			    idField:'id',
			    sortName : 'viewTime',
				sortOrder : 'asc', 
				checkOnSelect : false,
				selectOnCheck : false,
			   	border:false,
				rownumbers : true,
				singleSelect : true,
			   	//fit:true,
			    columns:[[ 
					{title:'热度小区',field:'village'},
					{title:'观看时长(秒)',field:'viewTime',sortable : true}, 
			    ]]  ,
			    onLoadSuccess : function() {
					$('#searchForm table').show();
					$(this).datagrid('tooltip');
				}
		});  	 
	}
	function timesSearch(){ //次数
		
		 $('#query_tabs').tabs('select','次数TopN');
		 $('#query_tabs').tabs('unselect','时长TopN');
		 setTableName();
		  $('#dg1').datagrid({   
		 	    url:'${pageContext.request.contextPath}/reportHotDo/getReportHotCommunity/times', 
		 	   	queryParams:$.serializeObject($('#searchForm')),		 	    			
		 	   	pagination :true,
			    pageSize:20,
			    pageList:[10,20,30,40],
			    iconCls: 'icon-save',
			    idField:'id',
			    sortName : 'clickTimes',
				sortOrder : 'asc',
				checkOnSelect : false,
				selectOnCheck : false,
			   	border:false,
				rownumbers : true,
				singleSelect : true,
				frozenColumns : [[ 			
				{title:'热度小区',field:'village'},
				{title:'活跃次数',field:'clickTimes',sortable : true}, 
				{
					field : 'id',
					hidden : true
				}]],
			    onLoadSuccess : function() {
					$('#searchForm table').show();
					$(this).datagrid('tooltip');
				}
		});  
	}
	function setTableName(){
		 var temp=$('input[name=bookDate1]').val().split('-');
		 var temp2=$('input[name=bookDate2]').val().split('-');
		 if(temp[0]!=temp2[0]||temp[1]!=temp2[1]){
			 alert("数据量太大!不支持跨月或跨年查询");
			 return ;
		 }
		 var tableName=temp[0]+temp[1];
	     $('#id_tableName').val(tableName);
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
					<th >时间范围:</th>
					<td >
						<input class="span2 easyui-validatebox" name="bookDate1" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						至
						<input class="span2 easyui-validatebox" name="bookDate2" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true" />					
					
								<a href="###" class="easyui-linkbutton" data-options="iconCls:'brick_add'" onclick="timesSearch();">按次数</a>
								<a href="###" class="easyui-linkbutton" data-options="iconCls:'brick_delete'" onclick="timeSearch();">按时长</a>	
								<input id="id_tableName"type="hidden" name="tableName"/>							
					</td>
				</tr>
			</table>
		</form>		
	</div>
	 <div data-options="region:'center'"> 
			<!-- <div id="dgTab" data-options="region:'center',border:false" style="margin-top:1px"> -->
				<div id="query_tabs" class="easyui-tabs">
					<div id="tab1" title="次数TopN" style="overflow:auto;height:500px;">
						<table id="dg1" style="height:500px;"></table>
					</div>
					<div id="tab2" title="时长TopN" style="overflow:auto;">
						<table id="dg2" style="height:500px;"></table> 
					</div>
				</div>
			</div>
</div>
<div id="cellMenu" class="easyui-menu" style="width: 130px; display: none;">
		<a id="showInNewWindow" href="###">新窗口打开明细</a>
		<br/>
		<a id="downLoadExcel" href="###">Excel下载明细</a>
	</div>-->
</body>
</html>