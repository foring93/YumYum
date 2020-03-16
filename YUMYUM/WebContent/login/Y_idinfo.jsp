<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<style>
.yy_idinfo {
	width: 1000px;
    height: 600px;
    padding: 200px;
    margin: 0 auto;
}
  #yy_btn{margin-top: 150px;
    height: 60px;
    background: #ad968a;
    border: 0;
    margin-left: 5px;
    margin-right: 5px;
    color: white;}
#yy_idresult{text-align:center;}
footer hr{margin-top: 30rem;}
</style>
</head>
<body>
	<header><jsp:include page="../Main/Y_header.jsp" />
	</header>
	<div class="out container">
		<div class="yy_idinfo">
			<div id="yy_idresult"><%=request.getAttribute("idsearchresult")%>
			<br>
		<button id="yy_btn" onClick="location.href='../Login'">로그인 페이지로 돌아가기</button><button id="yy_btn" onClick="location.href='../findinfo'">아이디/패스워드 찾기로 돌아가기</button>
			</div>
			
			<!-- 확인 후 메인 페이지로 돌아갑니다. -->
		</div>
	</div>
	<footer><jsp:include page="../Main/Y_footer.jsp"></jsp:include></footer>
</body>
</html>