<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="well well-large" style="margin: 10px;">
	</div> -->
	
<table id="dg"></table> 

<script type="text/javascript">
var datagrid;
$(function(){
$('#dg').datagrid({   
    url:'${pageContext.request.contextPath}/noticeController/treeGrid',  
    //title: '公告信息',
    iconCls: 'icon-save',
    pagination :true,
    pageSize:10,
    pageList:[10,20,30,40],
    fit:true,
    fitColumns : true,
    idField:'id',
   	border:false,
    columns:[[ 
		{title:'编号',field:'id',width:100,hidden:true}, 
        {title:'标题',field:'title',width:300,formatter:function(value,row,index){
        	var url="${pageContext.request.contextPath}/noticeController/editPage?id="+ row.id ;
        	return "<a href='###' onclick=\"addTab({url:'"+url+"',title:'公告详情'});\">"+value+"</a>";
		}},   
        {title:'上传人',field:'loginName'},   
        {title:'上传时间',field:'time',width:140}   
    ]]   
});  
});
function addTab(params) {
	params.newtab = true;
	window.parent.addTab(params);
	
}
</script>
