<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>用户详细信息</title>
<jsp:include page="../inc.jsp"></jsp:include>
<%@ include file="/common/inclue-highcharts.jsp" %>
<script type="text/javascript">
$(function(){
	var userId = "8800103387";
	$('#p').panel( {
		href : '${pageContext.request.contextPath}/reportvodDo/getUser?userId='+ userId,
		collapsible : true	
	});
}); 
function loginSearch() {
	 $('#query_tabs').tabs('select','登录情况');
	 $('#query_tabs').tabs('unselect','点播情况');
	 $('#query_tabs').tabs('unselect','点播分类');
	 $('#query_tabs').tabs('unselect','点播活跃度');
	 $('#dg1').datagrid({   
	 	    url:'${pageContext.request.contextPath}/reportvodDo/getSelectEpglog', 
	 	   	queryParams:$.serializeObject($('#searchForm')),
	 	    
	 	   	//fit : true,
			//fitColumns : false,
			//border : false,
			
	 	   	pagination :true,
		    pageSize:20,
		    pageList:[10,20,30,40],
		    iconCls: 'icon-save',
		    idField:'id',
		    sortName : 'logTime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
		   	border:false,
			rownumbers : true,
			singleSelect : true,
		   	//fit:true,
		    columns:[[ 
				{title:'登录时间',field:'logTime',sortable : true},
				{title:'用户Id',field:'userId'}, 
		        {title:'resno',field:'resno'}
		    ]]  ,
		    onLoadSuccess : function() {
				$('#searchForm table').show();
				$(this).datagrid('tooltip');
			}
	}); 
	//$('#dg1').datagrid('load', $.serializeObject($('#searchForm')));

}
function wasuSearch() {
	 $('#query_tabs').tabs('unselect','登录情况');
	 $('#query_tabs').tabs('select','点播情况');
	 $('#query_tabs').tabs('unselect','点播分类');
	$('#query_tabs').tabs('unselect','点播活跃度');
	 $('#dg2').datagrid({
	 	    url:'${pageContext.request.contextPath}/reportvodDo/getSelectWasunlog',  
	 	   	queryParams:$.serializeObject($('#searchForm')),
		    pagination :true,
		    pageSize:20,
		    pageList:[10,20,30,40],
		    iconCls: 'icon-save',
		    idField:'id',
		    sortName : 'beginTime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
		   	border:false,
			rownumbers : true,
			singleSelect : true,
		   	//fit:true,
		    columns:[[ 
				{title:'开始时间',field:'beginTime'}, 
				{title:'结束时间',field:'endTime'}, 
				{title:'影片名称',field:'filmName',sortable : true}, 
				{title:'费用',field:'fee'}, 
				{title:'分类',field:'catergory'}, 
				{title:'dispalyPath',field:'displayPath'}, 
				{title:'用户Id',field:'userId'}, 
				{title:'subscriberCode',field:'subscriberCode'}, 
				{title:'ppvId',field:'ppvId'}, 
		        {title:'billingNo',field:'billingNo'} 
		    ]]  ,
		    onLoadSuccess : function() {
				$('#searchForm table').show();
				$(this).datagrid('tooltip');
			}
	}); 
	//$('#dg2').datagrid('load', $.serializeObject($('#searchForm')));
}
function catergoryStatic() {
	$('#query_tabs').tabs('unselect','登录情况');
	$('#query_tabs').tabs('unselect','点播情况');
	$('#query_tabs').tabs('select','点播分类');
	$('#query_tabs').tabs('unselect','点播活跃度');
	
	$.getJSON('${pageContext.request.contextPath}/reportvodDo/getUserCatergoryChart', 
			$.serializeObject($('#searchForm')), function(json){
		
		showPie("播放频次(次)",json.frequency,'次数',"frequencyPie");
		showPie("播放时长(秒)",json.duration,'时长(秒)',"durationPie");
		
		//$("#chartTable tr td>div").not(":has('.highcharts-container')").hide();
		//$("#chartTable div:has('.highcharts-container')").css("border-width","1px");
		//$("#chartTable div:has('.highcharts-container')").css("border-style","solid");
	});
	//$('#dg2').datagrid('load', $.serializeObject($('#searchForm')));
}
function activeStatic() {
	$('#query_tabs').tabs('unselect','登录情况');
	$('#query_tabs').tabs('unselect','点播情况');
	$('#query_tabs').tabs('unselect','点播分类');
	$('#query_tabs').tabs('select','点播活跃度');
	
	$.getJSON('${pageContext.request.contextPath}/reportvodDo/getUserEverydayChart', 
			$.serializeObject($('#searchForm')), function(json){

		$("#tab4 div").css("border-width","1px");
		$("#tab4 div").css("border-style","solid");
		
		showLine("播放频次(次)",new Array({name:'播放频次(次)',data:json.frequency}),json.fromDate,'次','次数',"frequencyLine");
		showLine("播放时长(秒)",new Array({name:'播放时长(秒)',data:json.duration}),json.fromDate,'秒','时长(秒)',"durationLine");
	});
	//$('#dg2').datagrid('load', $.serializeObject($('#searchForm')));
}

function showPie(chartTitle,piedata,unit,domId){
	if(piedata == null){
		$('#'+domId).hide();
		return;
	}
	$('#'+domId).highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        credits:chartConfig_credit,
        title: {
            text: chartTitle
        },
        tooltip: {
    	    pointFormat: '占比: <b>{point.percentage:.1f}%</b><br/>'+ unit +':<b>{point.y}</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                }
            }
        },
        series: [{
            type: 'pie',
            name: '占比',
            data: piedata
        }]
    });
}


function showLine(chartTitle,lineSeries,fromDate,unit,y_text,domId){
	if(lineSeries == null){
		$('#'+domId).hide();
		return;
	}
	
	var dateBegin = new Date(fromDate);
	$('#'+domId).highcharts({
		chart: {
			type: 'spline'
		},
	    title: {
            text: chartTitle,
            x: -20 //center
        },
        credits:chartConfig_credit,
	    xAxis: {
	        type: 'datetime',
	        dateTimeLabelFormats: {
	              day: '%m.%d'
	        }
	    },
	    yAxis: {
	        title: {
	            text: y_text
	            }
	    },
	    plotOptions: {
          spline: {
              lineWidth: 2,
              states: {
                  hover: {
                      lineWidth: 3
                  }
              },
              marker: {
                  enabled: true
              },
              pointInterval: 24 * 3600 * 1000,
              pointStart: Date.UTC(dateBegin.getFullYear(),dateBegin.getMonth(),dateBegin.getDate())
          }
		},
		tooltip: {
		    valueSuffix: unit,
		    dateTimeLabelFormats: {
		          day: '%Y-%m-%d, %A'
		    }
		},
		series: lineSeries,
		navigation: {
		    menuItemStyle: {
		        fontSize: '10px'
		    }
		}
	});
}

</script>
</head>
<body>	
		
<div id="userPanel" class="easyui-layout"  data-options="fit:true,border:false" >
	<div data-options="region:'north',title:'用户基本信息',border:false,onCollapse:function(){$('#userPanel div.panel-title').text('用户基本信息');}"" style="height: 180px; overflow: auto;">
		<table class="table table-hover table-condensed" style="margin:0px;max-width:1000px">
			<tr>
				<th>客户名</th>
				<td>${user.customerName}</td>
				<th>用户编号</th>
				<td>${user.userId}</td>
				<th>客户类型</th>
				<td>${user.customerType}</td>
				<th>重要级别</th>
				<td>${user.importanceLevel}</td>
			</tr>
			<tr>
				<th>用户群</th>
				<td>${user.userGroup}</td>
				<th>营业厅</th>
				<td>${user.shop}</td>
				<th>操作员</th>
				<td>${user.operator}</td>
				<th>创建时间</th>
				<td>${user.crTimeDesc}</td>
			</tr>
			<tr>
				<th>用户地址</th>
				<td colspan="7">${user.town} ${user.community} ${user.village} ${user.fulladdress}</td>
			</tr>
			<tr>
				<th>机顶盒类型</th>
				<td>${user.stbType}</td>
				<th>机顶盒型号</th>
				<td>${user.stb}</td>
				<th>获取途径</th>
				<td>${user.obtainWay}</td>
			</tr>
			<tr>
				<th>机顶盒号</th>
				<td colspan="3">${user.stbserialNo}</td>
				<th>SIM卡号</th>
				<td colspan="3">${user.simserialNo}</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true" style="overflow:hidden;">
		<div class="easyui-layout" data-options="fit:true">
			
			<div data-options="region:'north'" style="height:32px;overflow:hidden;">
				<form id="searchForm" style="margin:1px">
					<input name="userId" value="${user.userId}" hidden="hidden">
					<table>	
						<tr>
							<th>时间范围</th>
							<td><input class="span2" name="repDate1" placeholder="点击选择时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />至
							<input class="span2" name="repDate2" placeholder="点击选择时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /></td>
							<td>
								<a href="###" class="easyui-linkbutton" data-options="iconCls:'brick_add'" onclick="loginSearch();">登录查询</a>
								<a href="###" class="easyui-linkbutton" data-options="iconCls:'brick_delete'" onclick="wasuSearch();">点播查询</a>
								<a href="###" class="easyui-linkbutton" data-options="iconCls:'bricks'" onclick="catergoryStatic();">点播分类</a>
								<a href="###" class="easyui-linkbutton" data-options="iconCls:'bricks'" onclick="activeStatic();">点播活跃度</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			
<!-- 	<div id="dgTab" data-options="region:'center',border:false" style="margin-top:1px">
			<table id="dataGrid"></table>
		</div> -->
			
			 <div data-options="region:'center'"> 
			<!-- <div id="dgTab" data-options="region:'center',border:false" style="margin-top:1px"> -->
				<div id="query_tabs" class="easyui-tabs">
					<div id="tab1" title="登录情况" style="overflow:auto;height:500px;">
						<table id="dg1" style="height:500px;"></table>
					</div>
					<div id="tab2" title="点播情况" style="overflow:auto;">
						<table id="dg2" style="height:500px;"></table>
					</div>
					<div id="tab3" title="点播分类" style="overflow:auto;">
					<div id="frequencyPie" style="margin-bottom:0px;width:800px;min-height:400px" ></div>
					<div id="durationPie" style="margin-bottom:0px;width:800px;min-height:400px" ></div>
<%--					
						<table id="chartTable" style="height:500px;border-collapse:collapse;">
							<tr style="vertical-align:top;">
								<td style="padding:0px">
									<div id="frequencyPie" style="width:600px;min-height:400px" ></div>
								</td>
								<td style="padding:0px">
									<div id="durationPie" style="width:600px;min-height:400px" ></div>
								</td>
							</tr>
						</table>
 --%>							
					</div>
					<div id="tab4" title="点播活跃度" style="overflow:auto;">
						<div id="frequencyLine" style="margin-bottom:0px;max-width:1200px;"></div>
						<div id="durationLine" style="margin-bottom:0px;max-width:1200px;"></div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
</div>

</body>
</html>