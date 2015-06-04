<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>区域活跃度比例</title>
<jsp:include page="../inc.jsp"></jsp:include>
<%@ include file="/common/inclue-highcharts.jsp" %>
<script type="text/javascript">
var spotDataMap,seriesDataMap;
<c:if test="${spotDataMap!=null}">
spotDataMap = ${spotDataMap};
</c:if>
<c:if test="${seriesDataMap!=null}">
seriesDataMap = ${seriesDataMap};
</c:if>
$(function() {
	if($("#customerTypeId").val()!=''){
		$.getJSON("${pageContext.request.contextPath}/boss/syscodeDo/getBossSysCode", 
					{ codeId: $("#customerTypeId").val() }, 
					function(json){
						$("#customerType").val(json.codeContent);
					});
	}
	if($("#townId").val()!=''){
		$.getJSON("${pageContext.request.contextPath}/boss/syscodeDo/getBossSysCode", 
					{ codeId: $("#townId").val() }, 
					function(json){
						$("#townName").val(json.codeContent);
					});
	}
	if($("#communityId").val()!=''){
		$.getJSON("${pageContext.request.contextPath}/boss/syscodeDo/getBossSysCode", 
				{ codeId: $("#communityId").val() }, 
				function(json){
					$("#communityName").val(json.codeContent);
				});
	}
	if($("#villageId").val()!=''){
		$.getJSON("${pageContext.request.contextPath}/boss/syscodeDo/getBossSysCode", 
				{ codeId: $("#villageId").val() }, 
				function(json){
					$("#villageName").val(json.codeContent);
				});
	}
	
});

function showStat(title,piedata,chartType){
	
	if(chartType == 'line'){
		$("#linecharts div").css("border-width","1px");
		$("#linecharts div").css("border-style","solid");
		showLine("客户数:"+title,piedata.customers,seriesDataMap.fromDate,"customersLine");
		showLine("宽带用户:"+title,piedata.bbUsers,seriesDataMap.fromDate,"bbUsersLine");
		showLine("基本型用户:"+title,piedata.dvbUsers,seriesDataMap.fromDate,"dvbUsersLine");
		showLine("交互型用户:"+title,piedata.vodUsers,seriesDataMap.fromDate,"vodUsersLine");
		showLine("高清用户:"+title,piedata.hdstbs,seriesDataMap.fromDate,"hdstbsLine");
		showLine("订购用户:"+title,piedata.bookUsers,seriesDataMap.fromDate,"bookUsersLine");
		showLine("活跃用户:"+title,piedata.activeUsers,seriesDataMap.fromDate,"activeUsersLine");
		showLine("登录用户(订购):"+title,piedata.onlineBookedUsers,seriesDataMap.fromDate,"onlineBookedUsersLine");
		showLine("登录用户(未订):"+title,piedata.onlineUnbookUsers,seriesDataMap.fromDate,"onlineUnbookUsersLine");
		showLine("未登用户(订购):"+title,piedata.offlineBookedUsers,seriesDataMap.fromDate,"offlineBookedUsersLine");
		showLine("未登用户(未订):"+title,piedata.offlineUnbookUsers,seriesDataMap.fromDate,"offlineUnbookUsersLine");
		showLine("不活跃(登录)"+title,piedata.inactiveOnlineUsers,seriesDataMap.fromDate,"inactiveOnlineUsersLine");
		showLine("不活跃(未登):"+title,piedata.inactiveOfflineUsers,seriesDataMap.fromDate,"inactiveOfflineUsersLine");
		
		showLine("点播立即开通用户:"+title,piedata.vodOpenNowUsers,seriesDataMap.fromDate,"vodOpenNowUsersLine");
		showLine("点播延迟开通用户:"+title,piedata.vodOpenDelayUsers,seriesDataMap.fromDate,"vodOpenDelayUsersLine");
		showLine("点播未开通用户:"+title,piedata.vodNotOpenUsers,seriesDataMap.fromDate,"vodNotOpenUsersLine");
		showLine("宽带立即开通用户:"+title,piedata.bbOpenNowUsers,seriesDataMap.fromDate,"bbOpenNowUsersLine");
		showLine("宽带延迟开通"+title,piedata.bbOpenDelayUsers,seriesDataMap.fromDate,"bbOpenDelayUsersLine");
		showLine("宽带未开通用户:"+title,piedata.bbNotOpenUsers,seriesDataMap.fromDate,"bbNotOpenUsersLine");
		
		
		showLine("基本型用户数净增:"+title,piedata.newDvbUsers,seriesDataMap.fromDate,"newDvbUsersLine");
		showLine("交互在线用户数:"+title,piedata.onlineVodUsers,seriesDataMap.fromDate,"onlineVodUsersLine");
		showLine("宽带在线用户数:"+title,piedata.onlineBbUsers,seriesDataMap.fromDate,"onlineBbUsersLine");
		showLine("交互型用户数净增:"+title,piedata.newVodUsers,seriesDataMap.fromDate,"newVodUsersLine");
		showLine("交互在线用户数净增"+title,piedata.newOnlineVodUsers,seriesDataMap.fromDate,"newOnlineVodUsersLine");
		showLine("交户离线用户数:"+title,piedata.offlineVodUsers,seriesDataMap.fromDate,"offlineVodUsersLine");
		showLine("宽带用户数净增:"+title,piedata.newBbUsers,seriesDataMap.fromDate,"newBbUsersLine");
		showLine("宽带在线用户数净增"+title,piedata.newOnlineBbUsers,seriesDataMap.fromDate,"newOnlineBbUsersLine");
		showLine("宽带离线用户数:"+title,piedata.offlineBbUsers,seriesDataMap.fromDate,"offlineBbUsersLine");
		return;
	}
	if(chartType == 'pie'){
			showPie("客户数:"+title,piedata.customers,"customersPie");
			showPie("宽带户:"+title,piedata.bbUsers,"bbUsersPie");
			showPie("基本型用户:"+title,piedata.dvbUsers,"dvbUsersPie");
			showPie("交互型用户:"+title,piedata.vodUsers,"vodUsersPie");
			showPie("高清用户:"+title,piedata.hdstbs,"hdstbsPie");
			showPie("订购用户:"+title,piedata.bookUsers,"bookUsersPie");
		
			showPie("活跃用户:"+title,piedata.activeUsers,"activeUsersPie");
			showPie("登录用户(订购):"+title,piedata.onlineBookedUsers,"onlineBookedUsersPie");
			showPie("登录用户(未订):"+title,piedata.onlineUnbookUsers,"onlineUnbookUsersPie");
			showPie("未登用户(订购):"+title,piedata.offlineBookedUsers,"offlineBookedUsersPie");
			showPie("未登用户(未订):"+title,piedata.offlineUnbookUsers,"offlineUnbookUsersPie");
			showPie("不活跃(登录)"+title,piedata.inactiveOnlineUsers,"inactiveOnlineUsersPie");
			showPie("不活跃(未登):"+title,piedata.inactiveOfflineUsers,"inactiveOfflineUsersPie");
			
			showPie("点播立即开通用户:"+title,piedata.vodOpenNowUsers,"vodOpenNowUsersPie");
			showPie("点播延迟开通用户:"+title,piedata.vodOpenDelayUsers,"vodOpenDelayUsersPie");
			showPie("点播未开通用户:"+title,piedata.vodNotOpenUsers,"vodNotOpenUsersPie");
			showPie("宽带立即开通用户:"+title,piedata.bbOpenNowUsers,"bbOpenNowUsersPie");
			showPie("宽带延迟开通"+title,piedata.bbOpenDelayUsers,"bbOpenDelayUsersPie");
			showPie("宽带未开通用户:"+title,piedata.bbNotOpenUsers,"bbNotOpenUsersPie");
			
			showPie("基本型用户数净增:"+title,piedata.newDvbUsers,"newDvbUsersPie");
			showPie("交互在线用户数:"+title,piedata.onlineVodUsers,"onlineVodUsersPie");
			showPie("宽带在线用户数:"+title,piedata.onlineBbUsers,"onlineBbUsersPie");
			showPie("交互型用户数净增:"+title,piedata.newVodUsers,"newVodUsersPie");
			showPie("交互在线用户数净增"+title,piedata.newOnlineVodUsers,"newOnlineVodUsersPie");
			showPie("交户离线用户数:"+title,piedata.offlineVodUsers,"offlineVodUsersPie");
			showPie("宽带用户数净增:"+title,piedata.newBbUsers,"newBbUsersPie");
			showPie("宽带在线用户数净增"+title,piedata.newOnlineBbUsers,"newOnlineBbUsersPie");
			showPie("宽带离线用户数:"+title,piedata.offlineBbUsers,"offlineBbUsersPie");

		$("#chartTable tr td>div").not(":has('.highcharts-container')").hide();
		$("#chartTable div:has('.highcharts-container')").css("border-width","1px");
		$("#chartTable div:has('.highcharts-container')").css("border-style","solid");
	}
	if(chartType == 'column'){
			showColumn("客户数:"+title,piedata.customers,"customersPie");
			showColumn("宽带户:"+title,piedata.bbUsers,"bbUsersPie");
			showColumn("基本型用户:"+title,piedata.dvbUsers,"dvbUsersPie");
			showColumn("交互型用户:"+title,piedata.vodUsers,"vodUsersPie");
			showColumn("高清用户:"+title,piedata.hdstbs,"hdstbsPie");
			showColumn("订购用户:"+title,piedata.bookUsers,"bookUsersPie");
			showColumn("活跃用户:"+title,piedata.activeUsers,"activeUsersPie");
			showColumn("登录用户(订购):"+title,piedata.onlineBookedUsers,"onlineBookedUsersPie");
			showColumn("登录用户(未订):"+title,piedata.onlineUnbookUsers,"onlineUnbookUsersPie");
			showColumn("未登用户(订购):"+title,piedata.offlineBookedUsers,"offlineBookedUsersPie");
			showColumn("未登用户(未订):"+title,piedata.offlineUnbookUsers,"offlineUnbookUsersPie");
			showColumn("不活跃(登录)"+title,piedata.inactiveOnlineUsers,"inactiveOnlineUsersPie");
			showColumn("不活跃(未登):"+title,piedata.inactiveOfflineUsers,"inactiveOfflineUsersPie");
			
			showColumn("点播立即开通用户:"+title,piedata.vodOpenNowUsers,"vodOpenNowUsersPie");
			showColumn("点播延迟开通用户:"+title,piedata.vodOpenDelayUsers,"vodOpenDelayUsersPie");
			showColumn("点播未开通用户:"+title,piedata.vodNotOpenUsers,"vodNotOpenUsersPie");
			showColumn("宽带立即开通用户:"+title,piedata.bbOpenNowUsers,"bbOpenNowUsersPie");
			showColumn("宽带延迟开通"+title,piedata.bbOpenDelayUsers,"bbOpenDelayUsersPie");
			showColumn("宽带未开通用户:"+title,piedata.bbNotOpenUsers,"bbNotOpenUsersPie");
			
			showColumn("基本型用户数净增:"+title,piedata.newDvbUsers,"newDvbUsersPie");
			showColumn("交互在线用户数:"+title,piedata.onlineVodUsers,"onlineVodUsersPie");
			showColumn("宽带在线用户数:"+title,piedata.onlineBbUsers,"onlineBbUsersPie");
			showColumn("交互型用户数净增:"+title,piedata.newVodUsers,"newVodUsersPie");
			showColumn("交互在线用户数净增"+title,piedata.newOnlineVodUsers,"newOnlineVodUsersPie");
			showColumn("交户离线用户数:"+title,piedata.offlineVodUsers,"offlineVodUsersPie");
			showColumn("宽带用户数净增:"+title,piedata.newBbUsers,"newBbUsersPie");
			showColumn("宽带在线用户数净增"+title,piedata.newOnlineBbUsers,"newOnlineBbUsersPie");
			showColumn("宽带离线用户数:"+title,piedata.offlineBbUsers,"offlineBbUsersPie");

			
		$("#chartTable tr td>div").not(":has('.highcharts-container')").hide();
		$("#chartTable div:has('.highcharts-container')").css("border-width","1px");
		$("#chartTable div:has('.highcharts-container')").css("border-style","solid");
	}
}

function testcharts(){
	 $('#testcharts').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: 'Browser market shares at a specific website, 2010'
	        },
	        tooltip: {
	    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
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
	            name: 'Browser share',
	            data: spotDataMap.customerType.activeUsers
	        }]
	    });
}

function showPie(chartTitle,piedata,domId){
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
    	    pointFormat: '占比: <b>{point.percentage:.1f}%</b><br/>数量:<b>{point.y}</b>'
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

function showColumn(chartTitle,piedata,domId){
	if(piedata == null){
		$('#'+domId).hide();
		return;
	}

	var categoryArray = new Array(piedata.length);
	for(var i=0;i<piedata.length;i++){
		categoryArray[i] = piedata[i][0];
	}
	
	$('#'+domId).highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 60, 60]
        },
        credits:chartConfig_credit,
        title: {
            text: chartTitle
        },
        xAxis: {
            categories: categoryArray,
            labels: {
                rotation: -45,
                align: 'right',
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '数量'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '数量: <b>{point.y} </b>',
        },
        series: [{
            name: '数量',
            data: piedata,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                x: 4,
                y: 10,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });
}

function showLine(chartTitle,lineSeries,fromDate,domId){
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
        //subtitle: {
        //    text: 'Source: WorldClimate.com',
        //    x: -20
        //},
	    xAxis: {
	        type: 'datetime',
	        dateTimeLabelFormats: {
	              day: '%m.%d'
	        }
	    },
	    yAxis: {
	        title: {
	            text: '数量'
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
		    valueSuffix: '个',
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
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 70px; overflow: auto;">
			<form action="${pageContext.request.contextPath}/reportvodDo/downLoadVodAreaDetail" method="get">
				<table>
					<tr>
						<th>日期</th>
						<td>
							<input class="span2" name="repDate1" value="${param.repDate1 }" readonly="readonly" />
							至
							<input class="span2" name="repDate2" value="${param.repDate2 }" readonly="readonly" />
						</td>
						<th>客户类型</th>
						<td>
							<input readonly="readonly" id="customerType"></input>
							<input type="hidden" id="customerTypeId" name="customerTypeId" value="${param.customerTypeId }"></input>
						</td>
						<th>行政区</th>
						<td>
							<input readonly="readonly" id="townName"></input>
							<input type="hidden" id="townId" name="townId" value="${param.townId }"></input>
						</td>
						<th>社区</th>
						<td>
							<input readonly="readonly" id="communityName"></input>
							<input type="hidden" id="communityId" name="communityId" value="${param.communityId }"></input>
						</td>
						<th>小区</th>
						<td>
							<input readonly="readonly" id="villageName"></input>
							<input type="hidden" id="villageId" name="villageId" value="${param.villageId }"></input>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center',border:false">
			<c:if test="${seriesDataMap!=null}">
				<c:if test="${param.showRepDate}">
					<div>
						<input type="button" onclick="showStat('按统计日期(不限)',seriesDataMap.repDate,'line')" value="按统计日期(不限)·折线图">
					</div>
				</c:if>
				<c:if test="${param.showCustomerType}">
					<div>
						<input type="button" onclick="showStat('按客户类型',seriesDataMap.customerType,'line')" value="按客户类型·折线图">
					</div>
				</c:if>
				<c:if test="${param.showTown }">
					<div>
						<input type="button" onclick="showStat('按行政区',seriesDataMap.town,'line')" value="按行政区·折线图">
					</div>
				</c:if>
				<c:if test="${param.showCommunity }">
					<div>
						<input type="button" onclick="showStat('按社区',seriesDataMap.community,'line')" value="按社区·折线图">
					</div>
				</c:if>
				<c:if test="${param.showVillage }">
					<div>
						<input type="button" onclick="showStat('按小区',seriesDataMap.village,'line')" value="按小区·折线图">
					</div>
				</c:if>
				
			</c:if>
			
			<c:if test="${spotDataMap!=null}">
			<c:if test="${param.showCustomerType}">
				<div>
					<input type="button" onclick="showStat('按客户类型',spotDataMap.customerType,'pie')" value="按客户类型·饼图">
					<input type="button" onclick="showStat('按客户类型',spotDataMap.customerType,'column')" value="按客户类型·柱图">
				</div>
			</c:if>
			<c:if test="${param.showTown }">
				<div>
					<input type="button" onclick="showStat('按行政区',spotDataMap.town,'pie')" value="按行政区·饼图">
					<input type="button" onclick="showStat('按行政区',spotDataMap.town,'column')" value="按行政区·柱图">
				</div>
			</c:if>
			<c:if test="${param.showCommunity }">
				<div>
					<input type="button" onclick="showStat('按社区',spotDataMap.community,'pie')" value="按社区·饼图">
					<input type="button" onclick="showStat('按社区',spotDataMap.community,'column')" value="按社区·柱图">
				</div>
			</c:if>
			<c:if test="${param.showVillage }">
				<div>
					<input type="button" onclick="showStat('按小区',spotDataMap.village,'pie')" value="按小区·饼图">
					<input type="button" onclick="showStat('按小区',spotDataMap.village,'column')" value="按小区·柱图">
				</div>
			</c:if>
			</c:if>
			
			<div id="testcharts">
			</div>
			
			<div id="linecharts">
				<div style="margin-bottom:3px;max-width:1200px" id="customersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="dvbUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="vodUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="bbUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="hdstbsLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="bookUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="activeUsersLine"></div>
				
				<div style="margin-bottom:3px;max-width:1200px" id="onlineBookedUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="onlineUnbookUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="offlineBookedUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="offlineUnbookUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="inactiveOnlineUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="inactiveOfflineUsersLine"></div>
				
				
				
				<div style="margin-bottom:3px;max-width:1200px" id="vodOpenNowUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="vodOpenDelayUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="vodNotOpenUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="bbOpenNowUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="bbOpenDelayUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="bbNotOpenUsersLine"></div>
				
				
				<div style="margin-bottom:3px;max-width:1200px" id="newDvbUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="onlineVodUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="onlineBbUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="newVodUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="newOnlineVodUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="offlineVodUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="newBbUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="newOnlineBbUsersLine"></div>
				<div style="margin-bottom:3px;max-width:1200px" id="offlineBbUsersLine"></div>
				
			</div>
			
			<table id="chartTable">
				<tr>
					<td><div id="customersPie" style="width:600px;min-height:400px" ></div></td>
					<td><div id="dvbUsersPie" style="width:600px;min-height:400px" ></div></td>
				</tr>
				<tr>
					<td><div id="vodUsersPie" style="width:600px;min-height:400px" ></div></td>
					<td><div id="bbUsersPie" style="width:600px;min-height:400px" ></div></td>
				</tr>
				<tr>
					<td><div id="hdstbsPie" style="width:600px;min-height:400px" ></div></td>
					<td><div id="bookUsersPie" style="width:600px;min-height:400px" ></div></td>
				</tr>
				<tr>
					<td><div id="activeUsersPie" style="width:600px;min-height:400px" ></div></td>
					<td></td>
				</tr>
				<tr>
					<td><div id="onlineBookedUsersPie" style="width:600px;min-height:400px" ></div></td>
					<td><div id="onlineUnbookUsersPie" style="width:600px;min-height:400px" ></div></td>
				</tr>
				<tr>
					<td><div id="offlineBookedUsersPie" style="width:600px;min-height:400px" ></div></td>
					<td><div id="offlineUnbookUsersPie" style="width:600px;min-height:400px" ></div></td>
				</tr>
				<tr>
					<td><div id="inactiveOnlineUsersPie" style="width:600px;min-height:400px" ></div></td>
					<td><div id="inactiveOfflineUsersPie" style="width:600px;min-height:400px" ></div></td>
				</tr>
				
				
				
				<tr>
					<td><div id="vodOpenNowUsersPie" style="width:600px;min-height:400px" ></div></td>
					<td><div id="vodOpenDelayUsersPie" style="width:600px;min-height:400px" ></div></td>
				</tr>
				<tr>
					<td><div id="vodNotOpenUsersPie" style="width:600px;min-height:400px" ></div></td>
					<td><div id="bbOpenNowUsersPie" style="width:600px;min-height:400px" ></div></td>
				</tr>
				<tr>
					<td><div id="bbOpenDelayUsersPie" style="width:600px;min-height:400px" ></div></td>
					<td><div id="bbNotOpenUsersPie" style="width:600px;min-height:400px" ></div></td>
				</tr>
				
				
				<tr>
					<td><div id="newDvbUsersPie" style="width:600px;min-height:400px" ></div></td>
					<td><div id="onPieVodUsersPie" style="width:600px;min-height:400px" ></div></td>
				</tr>
				<tr>
					<td><div id="onPieBbUsersPie" style="width:600px;min-height:400px" ></div></td>
					<td><div id="newVodUsersPie" style="width:600px;min-height:400px" ></div></td>
				</tr>
				<tr>
					<td><div id="newOnPieVodUsersPie" style="width:600px;min-height:400px" ></div></td>
					<td><div id="offPieVodUsersPie" style="width:600px;min-height:400px" ></div></td>
				</tr>
				<tr>
					<td><div id="newBbUsersPie" style="width:600px;min-height:400px" ></div></td>
					<td><div id="newOnPieBbUsersPie" style="width:600px;min-height:400px" ></div></td>
				</tr>
				<tr>
					<td><div id="offPieBbUsersPie" style="width:600px;min-height:400px" ></div></td>
					<td></td>
				</tr>
				
				
				
			</table>
			
			
			
		</div>
	</div>
 
</body>
</html>