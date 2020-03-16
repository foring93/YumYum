<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<style>
#yy_idpass {
	width: 1000px;
	height:1000px;
	line-height:100px;
	padding: 20px;
	margin:0 auto;
}

.yy_inputformat {
	width: 400px;
	height: 400px;
	margin:0 20px;
	cursor: pointer;
	background:#0000000d;
	border:0;
	border-radius: 20px;
}

.yy_input {
	text-align: center;
	margin-top:100px;
}

.yy_idimg{width:150px;height:150px}
.yy_pwdimg{width:144px;height:144px}
#yy_loginh1{text-align: center;font-weight: 800;font-size:80px;margin-top:50px; margin-bottom:50px; color:#ad968a;}
#btnspan{font-size: 20pt}
footer .de_in{margin:0 auto;}
footer .container{text-align:center;}
footer hr{margin-top: 8em;}
</style>
</head>
<body>
	<div class="container">
		<div id="yy_idpass">
		<h1 id="yy_loginh1" onClick="location.href='Main/index'" style="cursor:pointer">YUMYUM</h1>
			<br>
			<div class="yy_input">
				<button  class="yy_inputformat" onClick="location.href='info/idsearch'"><img alt="아이디" src="img/id.png" class="yy_idimg"><br><span id="btnspan">아이디 찾기</span></button>
				<button class="yy_inputformat" onclick="location.href='info/pwdsearch'" ><img alt="아이디" src="img/password.png" class="yy_pwdimg"><br><span id="btnspan">비밀번호 찾기</span></button>
			</div>
		</div>
	</div>
	<footer><jsp:include page="../Main/Y_footer.jsp"></jsp:include></footer>
</body>
</html>