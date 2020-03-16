<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>아이디 찾기</title>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<style>
		.yy_idsearchform{width:1000px;margin: 0 auto;padding-top: -100px}
		.yy_idsearchfieldset{width:1000px;}
		.yy_idcontents{width: 600px;text-align: center;
    		margin:0 auto;margin-top: 50px;
  			margin-bottom: 600px;}
		fieldset{width:400px; margin:0 auto;padding:20px;border: 0;}
		.yy_1000 {
			width: 1000px
		}
		input[type=text]{padding-left:20px;width:500px; height:60px; margin : 0 auto;margin-bottom: 10px}
		#yy_phone + hr{color:lightgrey;width: 500px;} 
		#yy_logo{font-weight: 800;color:#ad968a; text-decoration: none; font-size: 80px;}
		#idsearch{width:523px;height: 90px; background:#ad968a; color:white;border: none; font-size:20pt }
		.de_in{margin:0 auto;}
		.container{text-align:center;}
		header div{text-align: left;}
	</style>
</head>
<body>
<script type="text/javascript" src="../js/idsearch.js"></script>
	<div class = "out container">
		<form action="../info/idinfo" method="post" class="yy_idsearchform">
			<header><div><span id="yy_logjoin"><a  href = "../Login">로그인</a><a href = "../Join">회원가입</a></span></div></header>
			<fieldset class="yy_idsearchfieldset">
				<div class="yy_idcontents">
					<a href="../Main/index"  id="yy_logo">YUMYUM</a>
					<h1 id="yy_idsearchh">아이디 찾기</h1>
					<input type ="text" id = "yy_name" name ="name" placeholder="이름"><br>
					<input type ="text" id = "yy_phone" name ="phone" placeholder="휴대폰 번호 ( '-'를 제외한 숫자만 입력 )" pattern="[0-9]{11}"><hr>
					<input type="submit" name="idsearch" id="idsearch" value ="찾기">
				</div>
			</fieldset>
		</form>
	</div>
	<footer><jsp:include page="../Main/Y_footer.jsp"></jsp:include></footer>
</body>
</html>