<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 

<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/global.jsp"%>
<%@ include file="/common/include-dist.jsp"%>
<%@ include file="/common/include-easyui.jsp"%>
<script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx }/js/md5.js"></script>
<script type="text/javascript" charset="utf-8">
$(function() {
	var loging = false;
	
	  
	// bind submit handler to form
	$('#loginForm').on('submit', function(e) {
		saveUserInfo();
	    $("#loginForm:reset").prop("disabled", true);
	    $("#loginForm:submit").prop("disabled", true);
	    e.preventDefault(); // prevent native submit
	    if(loging){
	    	return false;
	    }
	    loging = true;
	    $(this).ajaxSubmit({
	    	dataType:'json',
	    	success:function(result){
	    		if (result.success) {
	    			if($("#refererURL").val() != ""){
	    				window.location= $("#refererURL").val();
	    			} else {
	    				window.location= "index.jsp";
	    			}
	    		} else {
	    			alert(result.msg);
		    	    $("#loginForm:reset").prop("disabled", false);
		    	    $("#loginForm:submit").prop("disabled", false);
		    		loging = false;
	    		}
	    	},
	    	error:function(){
	    	    $("#loginForm:reset").prop("disabled", false);
	    	    $("#loginForm:submit").prop("disabled", false);
	    	    loging = false;
	    	}
	    	
	    });
	});
});
$(document).ready(function() {   
    if ($.cookie("rmbuser") == "true") {   
        $("#rmbuser").attr("checked", true);   
        $("#loginName").val($.cookie("loginName"));   
        $("#pwd").val($.cookie("pwd"));   
    }   
});   
function saveUserInfo(){
	if (rmbuser.checked == true) {  
        var loginName = $("#loginName").val();   
        var pwd = $("#pwd").val();   
        $.cookie("rmbuser", "true", { expires: 7 }); // 存储一个带7天期限的 cookie   
        $.cookie("loginName", loginName, { expires: 7 }); // 存储一个带7天期限的 cookie 
        if(pwd.length>16) {
        	$.cookie("pwd", pwd, { expires: 7 }); // 存储一个带7天期限的 cookie   
        } else {
        	$.cookie("pwd", hex_md5(pwd), { expires: 7 });
        }
    } else {   
        $.cookie("rmbuser", "false", { expires: -1 });   
        $.cookie("loginName", '', { expires: -1 });   
        $.cookie("password", '', { expires: -1 });   
    }   
}
</script>
</head>
<body >
<div class="container" style="max-width:100%;height:600px;background:url('style/images/login.png');background-repeat:no-repeat;background-position:50% 50%;">
	<div class="row" style="height:100px;"></div>
	<div class="row">
	<div class="col-md-7 col-sm-7 col-xs-5"></div>
	<div class="col-md-3 col-sm-3 col-xs-5">
      <form id="loginForm" class="form-signin" role="form" method="post" action="userController/login">
        <h2 class="form-signin-heading">请登录</h2>
        <input id="loginName"name="loginName" type="text" class="form-control" placeholder="登录名" required autofocus ></input>
        
        <input id="pwd" name="pwd" type="password" class="form-control" placeholder="密码" required ></input>
        <label class="checkbox">
          <input id="rmbuser"name="rmbuser" type="checkbox" value="rmbuser"></input> 记住
        </label>
        <input id="refererURL" type="hidden" name="refererURL" value="${refererURL }" ></input>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
      </form>
    </div>
	</div>
</div>
</body>
</html>
