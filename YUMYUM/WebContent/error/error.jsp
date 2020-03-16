<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>YUMYUM 에러 페이지</title>
<style>
	#errorimg{width: 200px;height: 200px;margin-left: 100px;margin-top: 81px;float: left;}
	#errormsg{float: left;font-size: 79px;margin-top: 131px;margin-left: 79px;}
	#yy_div{margin-left: 200px}
	#yy_errbtn{width: 354px;height: 64px;color: white;background: #19c719;font-size: 18px;border: 0;margin-top: 123px;margin-left: 100px;}
	#yy_errspan{font-size:54px; color: gray;}
</style>
</head>
<body>
	<div id="yy_div">
		<img src="../img/error.png" alt="에러 이미지" id="errorimg">
		<div id="errormsg">에러 발생!!<br>
			<span id="yy_errspan">${errormsg}</span><br>
			<input type="button" onClick="location.href='Main/index'" value = "메인 페이지로 이동" id="yy_errbtn">
		</div>
	</div>
	
</body>
</html>