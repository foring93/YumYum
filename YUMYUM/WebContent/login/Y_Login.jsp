<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>로그인</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<style>
.yy_logina {
	text-decoration: none;
	font-size: 10pt;
	margin-top:10px
}

#logindiv {
	width: 600px;
	padding: 20px;
	margin: 0 auto;
	text-align: center;
}

.yy_1000 {
	margin: 0 auto;
	width: 1000px;
	height: 1000px;
	
}
#id, #pwd{width:500px; height:60px; margin-top:10px;margin-bottom:10px; padding-left:20px}
#yy_loginh1{text-align: center;font-weight: 800;font-size:80px;margin-top:50px; margin-bottom:50px; color:#ad968a;}
#login{width: 523px; height:90px; margin-top:30px; background: #ad968a; color:white;border:none;font-size:20pt; margin-bottom:20px;}
.yy_foot{text-align: center;}
#logindiv hr {
    margin-top: 1rem;
    margin-bottom: 1rem;
    border: 0;
    border-top: 1px solid rgba(0,0,0,.1);
    width: 519px;
}
.yy_foot hr {
	margin:0 auto;
    margin-top: 8rem;
    margin-bottom: 1rem;
    border: 0.1;
    border-top: 1px solid rgba(0,0,0,.1);
    width:1140px;
}
</style>
<%
	if(session.getAttribute("id")!=null){
		response.sendRedirect("Main/index");
	}
%>
<script>
		function LoginFunction(event){
			event.preventDefault();
			var id =$("#id").val();
			var password = $("#pwd").val();
			if (id==""||id==null){//id를 입력하지 않았을 경우
				alert("아이디를 입력해주세요");
				return false;
			}else if(password==""||password==null){//password를 입력하지 않았을 경우
				alert("비밀번호를 입력해주세요");
				return false;
			}else{//id와 password 둘다 입력
				$.ajax({
					type : "post",
					url : "Login",
					data : {"id":id, "password":password},
					success : function(resp){
						if(resp=="true"){
							location.href="Main/index";
						}else{
								alert("아이디나 비밀번호를 확인하세요");
								if(resp>=3){
									location.href="Login?page=autologinbanned";
								}
								else{
									location.href="Login";
								}
						}
					},
					error : function(){alert("로그인 실패");
					}
				});//ajax end
			}//if end
		}
		function enterkey() {
	        if (window.event.keyCode == 13) {
	             // 엔터키가 눌렸을 때 실행할 내용
	             LoginFunction(event);
	        }
	}
	
</script>
</head>
<body>
	<div class="container">
		<div class="yy_1000 conatiner">
			<form id ="logindiv">
				<h1 id="yy_loginh1" onClick="location.href='Main/index'" style="cursor:pointer">YUMYUM</h1>
				<input type="text" name ="id" id ="id" placeholder="아이디를 입력하세요"><br>
				<input type="password" name ="pwd" id ="pwd" placeholder="비밀번호를 입력하세요" onKeyup="enterkey();"><br>
				
				<button type ="button" id="login" onClick="LoginFunction(event)">로그인</button>
				<hr>
				<a href="findinfo" class ="yy_logina">아이디/비밀번호가 기억나지 않으세요?</a><!-- 아이디/비밀번호 찾기로 넘어감 -->&nbsp;&nbsp;&nbsp;<a href="Join" class ="yy_logina">회원가입</a>
			</form>
		</div>
	</div>
	<footer class="yy_foot">
		<hr>
		<span>Copyright 80808Corp. All Rights Reserved.</span>
	</footer>
</body>
</html>