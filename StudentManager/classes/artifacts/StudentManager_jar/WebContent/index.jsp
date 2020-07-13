<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>宿舍管理登录界面</title>

<link rel="stylesheet" type="text/css" href="css/style.css">

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/vector.js"></script>

</head>
<body>

<div id="container">
	<div id="output">
		<div class="containerT">
			<h1>宿舍管理系统</h1>
			<form name="myForm" class="form" id="entry_form" action="login" method="post" onsubmit="return checkForm()">
				<input id="stuCode" name="stuCode" type="text" placeholder="学号" id="entry_name">
				<input id="password" name="password" type="password" placeholder="密码" id="entry_password">
				<font id="error" color="red">${error}</font>
				<div>				
        <label class="checkbox">
          <input id="remember" name="remember" type="checkbox" value="remember-me" ${remember==1?'checked':''}>记住我 &nbsp;&nbsp;&nbsp;&nbsp;   
        </label> 
</div>
				<button type="submit" id="entry_btn">登录</button>
				<div id="prompt" class="prompt"></div>

			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
    $(function(){
        Victor("container", "output");   //登录背景函数
        $("#entry_name").focus();
        $(document).keydown(function(event){
            if(event.keyCode==13){
                $("#entry_btn").click();
            }
        });
    });
    function checkForm(){
    	
    	var stuCode = document.getElementById("stuCode").value;
    	var password = document.getElementById("password").value;    	
    	if(stuCode == null || stuCode == ""){
    		document.getElementById("error").innerHTML="学号不能为空";
    		return false;
    	}
    	if(password == null || password == ""){
    		document.getElementById("error").innerHTML="密码不能为空";
    		return false;
    	}
    	return true;
    }
    $(document).ready(function(){
    	$("#login").addClass("active");
    })
</script>

</body>
</html>