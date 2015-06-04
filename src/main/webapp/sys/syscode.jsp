<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>

<title>系统代码:${codeType.codeContent }</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/syscodeController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/syscodeController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/syscodeController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/syscodeController/dataGrid',
			queryParams: {
				typeId: $("#codeTypeId").val()
			},
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : ($("#codeTypeId").val() == '0' ? 'id':'codeName'),
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 50,
				sortable : true
			}, {
				field : 'codeName',
				title : ($("#codeTypeId").val() == '0' ? '类别编码':'代码编码'),
				width : 200,
				sortable : true,
				formatter: function(value,row,index){
					if($("#codeTypeId").val() == '0'){
						return "<a href='${pageContext.request.contextPath}/syscodeController/manager?typeId="+ row.id +"'>"+value+"</a>";
					} else {
						return value;
					}
				}
			}, {
				field : 'codeContent',
				title : ($("#codeTypeId").val() == '0' ? '类别名称':'代码内容'),
				width : 150,
				sortable : true
			} ] ],
			columns : [ [ {
				field : 'memo',
				title : '备注',
				width : 250,
				sortable : false
			}, {
				field : 'statusDesc',
				title : '状态',
				width : 20,
				sortable : true
			},{
				field : 'updateTimeDesc',
				title : '最后修改时间',
				width : 80,
				sortable : true
			}, {
				field : 'updateLoginName',
				title : '最后修改人员',
				width : 50,
				sortable : true
			}, {
				field : 'action',
				title : '操作',
				width : 50,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit) {
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					}
					str += '&nbsp;';
					if ($.canDelete) {
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
					str += '&nbsp;';
					if ($.canView) {
						str += $.formatString('<img onclick="viewFun(\'{0}\');" src="{1}" title="查看"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/magnifier.png');
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
				
				if($("#codeTypeId").val() != '0'){
					//如果是非代码类别列表,则红色显示
					//$("#conditionDiv .panel-title").css("color","red");
					
					$("#conditionDiv .panel-title").empty();
					$("#conditionDiv .panel-title").html(
						"<a href='${pageContext.request.contextPath}/syscodeController/manager?typeId=0'>系统代码类别</a>：" + 
						"<font style='color:red'>"+ $("#codeTypeName").val() +"</font>"
					);
				}
				
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

	function deleteFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前系统代码？', function(b) {
			if (b) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/syscodeController/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					}
					parent.$.messager.progress('close');
				}, 'JSON');
			}
		});
	}

	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '编辑系统代码',
			width : 600,
			height : 400,
			href : '${pageContext.request.contextPath}/syscodeController/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : ($("#codeTypeId").val() == '0' ? '添加代码类别':($("#codeTypeName").val()+':添加系统代码')),
			width : 600,
			height : 400,
			href : '${pageContext.request.contextPath}/syscodeController/addPage?typeId='+$("#codeTypeId").val(),
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function viewFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '查看系统代码',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/syscodeController/view?id=' + id
		});
	}

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$("#searchForm input[name!='typeId']").val('');
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
</script>
<style type="text/css">
.remind {
	color:red;
}
</style>
</head>
<body>
	<input type="hidden" id="codeTypeName" value="${codeType.codeContent }">
	<div id="conditionDiv" class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'${codeType.codeContent }',border:false, headerCls:'remind'" style="height: 65px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<th style="width:100px">代码名称/内容</th>
						<td>
							<input name="codeName" placeholder="可以模糊查询" class="span2" style="width:200px"/>
							<input type="hidden" name="typeId" id="codeTypeId" value="${codeType.id }"/>
						</td>
						
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/syscodeController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'cog_add'">添加</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>

	<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/syscodeController/addPage')}">
			<div onclick="addFun();" data-options="iconCls:'pencil_add'">增加</div>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/syscodeController/delete')}">
			<div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/syscodeController/editPage')}">
			<div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div>
		</c:if>
	</div>
</body>
</html>