<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>区域点播统计</title>
<jsp:include page="../inc.jsp"></jsp:include>
<%@ include file="/common/inclue-highcharts.jsp" %>
<script type="text/javascript">
	var dataGrid;
	$(function(){
		parent.$.messager.progress('close');
	});
	function timeSearch(){//时长
		if(! $('#searchForm').form('validate')){
			return false;
		}		
		setTableName();
		 $('#query_tabs').tabs('unselect','次数TopN');
		 $('#query_tabs').tabs('select','时长TopN');
		 $.getJSON('${pageContext.request.contextPath}/reportHotDo/getReportHotPrograms/time',$.serializeObject($('#searchForm')),function(json){
			 showShape("热门影片(秒)",new Array({name:'播放时长(秒)',data:json.filmTimes}),json.filmNames,'秒长',"filmShape2");
			 showShape("热门分类(秒)",new Array({name:'播放时长(秒)',data:json.catergoryTimes}),json.catergoryNames,'秒长',"typeShape2");
		 });
	         
		 
	}
	function timesSearch(){ //次数
		if(! $('#searchForm').form('validate')){
			return false;
		}
		setTableName();
		 $('#query_tabs').tabs('select','次数TopN');
		 $('#query_tabs').tabs('unselect','时长TopN');
		 $.getJSON('${pageContext.request.contextPath}/reportHotDo/getReportHotPrograms/times', 
					$.serializeObject($('#searchForm')), function(json){
				
			 showShape("热门影片(次)",new Array({name:'播放频次(次)',data:json.filmTimes}),json.filmNames,'次数',"filmShape1");
			 showShape("热门分类(次)",new Array({name:'播放频次(次)',data:json.catergoryTimes}),json.catergoryNames,'次数',"typeShape1"); 
			});
		 
	}
	function showShape(chartTitle,data1,data2,y_text,divId){
		if(data1==null){
			$('#'+divId).hide();
			return;
		}
		$('#'+divId).highcharts({                                           
	        chart: {                                                           
	            type: 'bar'                                                    
	        },                                                                 
	        title: {                                                           
	            text: chartTitle                    
	        },                                                                 
	        subtitle: {                                                        
	            text: '来源: 华数广电'                                  
	        },                                                                 
	        xAxis: {                                                           
	            categories: data2,
	            title: {                                                       
	                text: null                                                 
	            }                                                              
	        },                                                                 
	        yAxis: {                                                           
	            min: 0,                                                        
	            title: {                                                       
	                text: y_text,                             
	                align: 'high'                                              
	            },                                                             
	            labels: {                                                      
	                overflow: 'justify'                                        
	            }                                                              
	        },                                                                 
	        tooltip: {                                                         
	            valueSuffix: y_text                                       
	        },                                                                 
	        plotOptions: {                                                     
	            bar: {                                                         
	                dataLabels: {                                              
	                    enabled: true                                          
	                }                                                          
	            }                                                              
	        },                                                                 
	        legend: {                                                          
	            layout: 'vertical',                                            
	            align: 'right',                                                
	            verticalAlign: 'top',                                          
	            x: -40,                                                        
	            y: 100,                                                        
	            floating: true,                                                
	            borderWidth: 1,                                                
	            backgroundColor: '#FFFFFF',                                    
	            shadow: true                                                   
	        },                                                                 
	        credits: {                                                         
	            enabled: false                                                 
	        },                                                                 
	        series: data1                                                               
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
					 <div id="filmShape1" style="margin-bottom:0px;max-width:600px;float:left;" ></div>
					   <div id="typeShape1" style="margin-bottom:0px;max-width:600px;float:left;"></div> 
					</div>
					<div id="tab2" title="时长TopN" style="overflow:auto;">
						<div id="filmShape2" style="margin-bottom:0px;max-width:600px;float:left;"></div>
					   <div id="typeShape2" style="margin-bottom:0px;max-width:600px;float:left;" ></div>
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