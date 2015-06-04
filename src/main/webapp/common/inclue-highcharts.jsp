<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 引入jQuery 
<script src="${pageContext.request.contextPath}/js/Highcharts-3.0.10/jquery.min.js" type="text/javascript" charset="utf-8"></script>
-->
<!-- 引入Highcharts -->
<script src="${pageContext.request.contextPath}/js/Highcharts-4.0.1/js/highcharts.js" type="text/javascript" charset="utf-8"></script>

<script src="${pageContext.request.contextPath}/js/Highcharts-4.0.1/js/modules/exporting.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
<!--
Highcharts.setOptions({
	lang:{
		months: [ "一月" , "二月" , "三月" , "四月" , "五月" , "六月" , "七月" , "八月" , "九月" , "十月" , "十一月" , "十二月"],
		shortMonths :[ "1月" , "2月" , "3月" , "4月" , "5月" , "6月" , "7月" , "8月" , "9月" , "10月" , "11月" , "12月"],
		weekdays: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"]
	}
})
var chartConfig_credit = {
	// enabled:true,               	// 默认值，如果想去掉版权信息，设置为false即可
	text:'© 2014 NDTV ', // 显示的文字
	href:'http://www.ndtv.com.cn',	// 链接地址
	position: {
	    align: 'right',
	    x: -10,
	    verticalAlign: 'bottom',
	    y: -5
	},
	style: {
	    cursor: 'pointer',
	    color: '#909090',
	    fontSize: '10px'
	}
}

//-->
</script>


