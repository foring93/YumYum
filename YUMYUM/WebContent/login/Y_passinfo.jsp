<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>패스워드 information</title>
<style>
	.yy_passinfodiv{    height: 500px;
    text-align: center;
    padding-top: 200px;}
    #yy_btn{margin-top: 150px;
    height: 60px;
    background: #ad968a;
    border: 0;
    margin-left: 5px;
    margin-right: 5px;
    color: white;}
    footer hr{margin-top: 37rem;}
</style>
</head>
<body>
	<header><jsp:include page="../Main/Y_header.jsp"/> </header>
	<div class="yy_passinfodiv">
		<c:if test="${pass != null}">
			해당 아이디의 비밀번호는<br> 
			${pass}<br>
			입니다.
		</c:if>
		<c:if test="${pass == null}">
			입력하신 정보로 조회한 결과 아이디가 존재하지 않습니다.
		</c:if>
		<br>
		<button id="yy_btn" onClick="location.href='../Login'">로그인 페이지로 돌아가기</button><button id="yy_btn" onClick="location.href='../findinfo'">아이디/패스워드 찾기로 돌아가기</button>
	</div>
	<footer><jsp:include page="../Main/Y_footer.jsp"></jsp:include></footer>
</body>
</html>