<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>用户清单明细</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">

$(function() {
	var sss="";
	var sss2="";
	var sss3="";
	var sss4="";
	<%
	String s1[]=request.getParameterValues("customerTypeIds");
	String s2[]=request.getParameterValues("townIds");
	String s3[]=request.getParameterValues("communityIds");
	String s4[]=request.getParameterValues("villageIds");
		if(s1!=null&&s1.length>0)
		{
			for(int i=0;i<s1.length;i++)
			{
			%>	
			$.getJSON("${pageContext.request.contextPath}/boss/syscodeDo/getBossSysCode", 
				{ codeId: <%=Long.valueOf(s1[i])%> }, 
				function(json){
					sss=sss+json.codeContent+",";
					$("#customerType").val(sss);
				});
		<%	
		}
		%>
	<%}%>
	<%
		if(s2!=null&&s2.length>0)
		{
			
			for(int i=0;i<s2.length;i++)
			{
			%>	
			$.getJSON("${pageContext.request.contextPath}/boss/syscodeDo/getBossSysCode", 
				{ codeId: <%=Long.valueOf(s2[i])%> }, 
				function(json){
					sss2=sss2+json.codeContent+",";
					$("#townName").val(sss2);
				});
		<%	
		}
		%>
	<%}%>
	<%
		if(s3!=null&&s3.length>0)
		{
			for(int i=0;i<s3.length;i++)
			{
			%>	
			$.getJSON("${pageContext.request.contextPath}/boss/syscodeDo/getBossSysCode", 
				{ codeId: <%=Long.valueOf(s3[i])%> }, 
				function(json){
					sss3=sss3+json.codeContent+",";
					$("#communityName").val(sss3);
			});
		<%	
		}
		%>
	<%}%>
	<%
		if(s4!=null&&s4.length>0)
		{
			for(int i=0;i<s4.length;i++)
			{
			%>	
			$.getJSON("${pageContext.request.contextPath}/boss/syscodeDo/getBossSysCode", 
				{ codeId: <%=Long.valueOf(s4[i])%> }, 
				function(json){
					sss4=sss4+json.codeContent+",";
					$("#villageName").val(sss4);
			});
		<%	
		}
		%>
	<%}%>
	//$("td [field='userId'] a").click(function(){
	

	
});
function userIdBinds(){
	$(".datagrid-cell-c1-userId a").click(function(){
		if(window.parent != null){
			var url='${pageContext.request.contextPath}/reportvodDo/getUser/' + $(this).text();
			window.parent.addTab({url:url,title:'用户详情',newtab:true});
		}
	});
}
	
/*
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/reportvodDo/getVodAreaDetail',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'userId',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'createdatetime',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			
			frozenColumns : [ [ {
				field : 'userId',
				title : '用户编号',
				width : 50
			} ] ],
			
			columns : [ [  {
				field : 'customerId',
				title : '客户编号',
				width : 50,
				sortable : true
			}, {
				field : 'customerName',
				title : '客户名称',
				width : 100,
				sortable : true
			}, {
				field : 'customerType',
				title : '客户类型',
				width : 100,
				sortable : true
			}, {
				field : 'stb',
				title : '机顶盒',
				width : 150,
				sortable : true
			}, {
				field : 'crtime',
				title : '开户日期',
				width : 100,
				sortable : true
			} , {
				field : 'town',
				title : '街道',
				width : 100,
				sortable : true
			} , {
				field : 'community',
				title : '社区',
				width : 100,
				sortable : true
			} , {
				field : 'village',
				title : '小区',
				width : 100,
				sortable : true
			} , {
				field : 'fulladdress',
				title : '具体地址',
				width : 100,
				sortable : true
			} ] ],
			
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			},
			onLoadError : function() {
				parent.$.messager.progress('close');
				parent.$.messager.alert('出错了','获取表格信息失败!');
			},
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
		
	});

	function viewFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '查看卡信息',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/cardData/view?id=' + id
		});
	}

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
*/	
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
							<input id="repDate" readonly="readonly" value=
						<c:if test="${param.repDate != null}">
							'${param.repDate }'
						</c:if>
						<c:if test="${param.queryMonth != null}">
							'${param.queryMonth }'
						</c:if>
            			<c:if test="${param.repDate1 != null && param.repDate2 != null}"  >
            				'${param.repDate1 }——${param.repDate2 }'
            			</c:if>
            			<c:if test="${param.repDate1 != null && param.repDate2 == null}">
            				'${param.repDate1 }'
            			</c:if>
            			<c:if test="${param.repDate2 == null && param.repDate2 != null}">
            				'${param.repDate2 }'
            			</c:if>
            			<c:if test="${param.activeDate1 != null && param.activeDate2 != null}">
            				'${param.activeDate1 }——${param.activeDate2 }'
            			</c:if>
            			<c:if test="${param.activeDate1 != null && param.activeDate2 == null}">
            				'${param.activeDate1 }'
            			</c:if>
            			<c:if test="${param.activeDate1 == null && param.activeDate2 != null}">
            				'${param.activeDate2 }'
            			</c:if>
            			<c:if test="${param.bookDate1 != null && param.bookDate2 != null}">
           					 '${param.bookDate1 }——${param.bookDate2 }'
            			</c:if>
            			<c:if test="${param.bookDate1 != null && param.bookDate2 == null}">
           					 '${param.bookDate1 }'
            			</c:if>
            			<c:if test="${param.bookDate1 == null && param.bookDate2 != null}">
           					 '${param.bookDate2 }'
            			</c:if>
            			<c:if test="${param.expiredDate1 != null && param.expiredDate2 != null}">
           					'${param.expiredDate1 }——${param.expiredDate2 }'
            			</c:if>
            				<c:if test="${param.expiredDate1 != null && param.expiredDate2 == null}">
           					'${param.expiredDate1 }' 
            			</c:if>
            				<c:if test="${param.expiredDate1 == null && param.expiredDate2 != null}">
           					'${param.expiredDate2 }'
            			</c:if>
            			></input>
						</td>
						<th>客户类型</th>
						<td>
							<input readonly="readonly" id="customerType"></input>
							<input type="hidden" id="customerTypeId" name="customerTypeId" value="${param.customerTypeIds }"></input>
						</td>
						<th>行政区</th>
						<td>
							<input readonly="readonly" id="townName"></input>
							<input type="hidden" id="townId" name="townId" value="${param.townIds }"></input>
						</td>
						<th>社区</th>
						<td>
							<input readonly="readonly" id="communityName"></input>
							<input type="hidden" id="communityId" name="communityId" value="${param.communityIds }"></input>
						</td>
						<th>小区</th>
						<td>
							<input readonly="readonly" id="villageName"></input>
							<input type="hidden" id="villageId" name="villageId" value="${param.villageIds }"></input>
						</td>
						<td>
							<input type="hidden" name="getWhat" value="${param.getWhat }">
							<input type="submit" value="下载Excel"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
		
<!-- 			<div id="dgTab" data-options="region:'center',border:false" style="overflow: auto;"> -->
			<table id="dataGrid" class="easyui-datagrid" title="报表明细" style="width:700px"
            data-options="url : '${pageContext.request.contextPath}/reportvodDo/getVodAreaDetail',
            		queryParams: {
            		<c:if test="${param.repDate != null}">
            			repDate:'${param.repDate }',
            		</c:if>
            		<c:if test="${param.repDate1 != null}">
            			repDate1:'${param.repDate1 }',
            		</c:if>
            		<c:if test="${param.repDate2 != null}">
            			repDate2:'${param.repDate2 }',
            		</c:if>
            		<c:if test="${param.activeDate1 != null}">
            			activeDate1:'${param.activeDate1 }',
            		</c:if>
            		<c:if test="${param.activeDate2 != null}">
            			activeDate2:'${param.activeDate2 }',
            		</c:if>
            		<c:if test="${param.onlineTimes1 != null}">
            			onlineTimes1:'${param.onlineTimes1 }',
            		</c:if>
            		<c:if test="${param.onlineTimes2 != null}">
            			onlineTimes2:'${param.onlineTimes2 }',
            		</c:if>
            		<c:if test="${param.activeTimes1 != null}">
            			activeTimes1:'${param.activeTimes1 }',
            		</c:if>
            		<c:if test="${param.activeTimes2 != null}">
            			activeTimes2:'${param.activeTimes2 }',
            		</c:if>
            		<c:if test="${param.queryMonth != null}">
            			queryMonth:'${param.queryMonth }',
            		</c:if>
            		<c:if test="${param.reportRange != null}">
            			reportRange:'${param.reportRange }',
            		</c:if>
            		<c:if test="${param.bookDate1 != null}">
           				 bookDate1:'${param.bookDate1 }',
            		</c:if>
            		<c:if test="${param.bookDate2 != null}">
           				 bookDate2:'${param.bookDate2 }',
            		</c:if>
            		<c:if test="${param.expiredDate1 != null}">
           				 expiredDate1:'${param.expiredDate1 }',
            		</c:if>
            		<c:if test="${param.expiredDate2 != null}">
           				 expiredDate2:'${param.expiredDate2 }',
            		</c:if>
            		<c:if test="${param.userTypeId != null}">
           				 userTypeId:'${param.userTypeId }',
            		</c:if>
<%--             	
					<c:if test="${param.customerTypeId != null}">
           				customerTypeId:'${param.customerTypeId }',
            		</c:if>
            		<c:if test="${param.townId != null}">
            			townId:'${param.townId }',
            		</c:if>
            		<c:if test="${param.communityId != null}">
            			communityId:'${param.communityId }',
            		</c:if>
            		<c:if test="${param.villageId != null}">
            			villageId:'${param.villageId }',
            		</c:if>
  --%>           		
            		<c:if test="${paramValues.customerTypeIds != null and fn:length(paramValues.customerTypeIds) > 0}">
						customerTypeIds:'<%
           					String[] a = request.getParameterValues("customerTypeIds");
            				for(int i= 0;i<a.length;i++){
            					if(i +1 == a.length){
            						%><%=a[i] %><%
            					} else {
            						%><%=a[i]+"," %><%
            					}
            				}
           				%>',
            		</c:if>
            		<c:if test="${paramValues.townIds != null and fn:length(paramValues.townIds) > 0}">
            			townIds:'<%
           					String[] a = request.getParameterValues("townIds");
            				for(int i= 0;i<a.length;i++){
            					if(i +1 == a.length){
            						%><%=a[i] %><%
            					} else {
            						%><%=a[i]+"," %><%
            					}
            				}
           				%>',
            		</c:if>
            		<c:if test="${paramValues.communityIds != null and fn:length(paramValues.communityIds) > 0}">
            			communityIds:'<%
           					String[] a = request.getParameterValues("communityIds");
            				for(int i= 0;i<a.length;i++){
            					if(i +1 == a.length){
            						%><%=a[i] %><%
            					} else {
            						%><%=a[i]+"," %><%
            					}
            				}
           				%>',
            		</c:if>
            		<c:if test="${paramValues.villageIds != null and fn:length(paramValues.villageIds) > 0}">
            			villageIds:'<%
           					String[] a = request.getParameterValues("villageIds");
            				for(int i= 0;i<a.length;i++){
            					if(i +1 == a.length){
            						%><%=a[i] %><%
            					} else {
            						%><%=a[i]+"," %><%
            					}
            				}
           				%>',
            		</c:if>
            			getWhat:'${param.getWhat }'
            		},
					fit : true,
					fitColumns : true,
					border : false,
					pagination : true,
					idField : 'userId',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50 ],
					sortName : 'userId',
					sortOrder : 'desc',
					checkOnSelect : false,
					selectOnCheck : false,
					nowrap : false,
					striped : true,
					rownumbers : true,
					singleSelect : true,
					onLoadSuccess: function(){
	                    var rownumlength = $('.datagrid-cell-rownumber:last').text().length * 10;
	                    $('.datagrid-cell-rownumber').css('width',rownumlength+'px');
	                    $('.datagrid-td-rownumber').css('width',rownumlength+'px');
	                    $('.datagrid-header-rownumber').css('width',rownumlength+'px');
	                    userIdBinds();
	                }
 					,onClickCell : function(rowIndex, field, value) {
						if(field == 'userId'){
							$('#cellMenu').menu('show', {
								left : window.event.pageX,
								top : window.event.pageY
							});
							var thisRow = $('#dataGrid').datagrid('getRows')[rowIndex];
							$('#showInNewWindow').prop('target','_blank');
							$('#showInNewWindow').prop('href','${pageContext.request.contextPath}/reportvodDo/getUser/' + thisRow.userId);
							$('#showInNewWindowBOSS').prop('target','_blank');
							$('#showInNewWindowBOSS').prop('href','http://10.8.70.11/boss/User/DocDo?goto=view&userId='+thisRow.userId);
							$('#cellMenu .menu-line').hide(); 
						}
					} 
				">
<c:choose>
	<c:when test="${param.getWhat == 'getCustomers'}">
	
				<thead data-options="frozen:true">
		            <tr>
						<th data-options="field:'customerId',width:100,halign:'center',sortable:true">客户编号</th>
		            </tr>
		        </thead>
		        <thead>
		            <tr>
						<th data-options="field:'customerId',width:100,halign:'center',sortable:true">支付卡号</th>
				        <th data-options="field:'customerName',width:150,halign:'center'">客户名称</th>
				        <th data-options="field:'customerType',width:100,halign:'center',sortable:true">客户类型</th>
				        <th data-options="field:'town',width:100,halign:'center',sortable:true">街道</th>
				        <th data-options="field:'community',width:100,halign:'center',sortable:true">社区</th>
				        <th data-options="field:'village',width:100,halign:'center',sortable:true">小区</th>
				        <th data-options="field:'fulladdress',width:300,halign:'center'">具体地址</th>
		            </tr>
		        </thead>
	</c:when>
	
	<c:otherwise>
				<thead data-options="frozen:true">
		            <tr>
		                <th data-options="field:'userId',width:100
		                ,formatter: function(value,row,index){
							return '<a href=&quot;###&quot;>'+value+'</a>';
						}
		                ">用户编号</th>
		            </tr>
		        </thead>
		        <thead>
		            <tr>
						<th data-options="field:'customerId',width:100,halign:'center',sortable:true">客户编号</th>
				        <th data-options="field:'customerName',width:150,halign:'center'">客户名称</th>
				        <th data-options="field:'customerType',width:120,halign:'center',sortable:true">客户类型</th>
				        <th data-options="field:'stb',width:300,halign:'center',sortable:true">机顶盒</th>
				        <th data-options="field:'crTime',width:150,halign:'center',sortable:true">开户日期</th>
				        <th data-options="field:'town',width:100,halign:'center',sortable:true">街道</th>
				        <th data-options="field:'community',width:100,halign:'center',sortable:true">社区</th>
				        <th data-options="field:'village',width:100,halign:'center',sortable:true">小区</th>
				        <th data-options="field:'fulladdress',width:300,halign:'center'">具体地址</th>
				        <th data-options="field:'phone',width:150,halign:'center'">电话</th>
				        <th data-options="field:'mobile',width:160,halign:'center'">手机</th>
	<c:if test="${param.getWhat == 'findVodAreaDetail' || param.getWhat == 'getActives' || param.getWhat == 'getVodOpenNowUsers'  || param.getWhat == 'getVodOpenDelayUsers' }">
				       <th data-options="field:'maxEndTime',width:150,halign:'center'">最大截止日期</th>
	</c:if>
	<c:if test="${'getVodOfflineBookUsers' == param.getWhat || 'getVodExpiredBookUsers' == param.getWhat || 'getVodUnExpiredBookUsers' == param.getWhat || param.getWhat == 'getRefeeExpiredUsers' }">
						<th data-options="field:'maxEndTime',width:150,halign:'center'">截止日期</th>
	</c:if>
	<c:if test="${'getVodOfflineBookUsers' == param.getWhat || 'getVodExpiredBookUsers' == param.getWhat || 'getVodUnExpiredBookUsers' == param.getWhat || param.getWhat == 'getRefeeBookUsers' }">
				        <th data-options="field:'maxBookTime',width:150,halign:'center'">订购日期</th>
	</c:if>
	<c:if test="${param.getWhat == 'getActives' || param.getWhat == 'getVodOpenNowUsers' || param.getWhat == 'getVodOpenDelayUsers'}">
				        <th data-options="field:'activeTimes',width:150,halign:'center'">点播次数</th>
				        <th data-options="field:'onlineTimes',width:150,halign:'center'">登录次数</th>
	</c:if>			        
		            </tr>
		        </thead>
	</c:otherwise>
</c:choose>		            
		    </table>
			<div id="cellMenu" class="easyui-menu" style="width: 120px; display: none;">
				<a id="showInNewWindow" href="###">打开详情</a>
				<br/>
				<a id="showInNewWindowBOSS" href="###">打开BOSS详情</a>
			</div>
		
		</div>
	</div>
 
</body>
</html>