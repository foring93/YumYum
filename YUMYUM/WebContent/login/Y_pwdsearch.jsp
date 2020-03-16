<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>비밀번호 찾기</title>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<style>
		.yy_passsearchform{width:1000px;margin: 0 auto;padding-top: -100px}
		.yy_passsearchfieldset{width:1000px;}
		.yy_passcontents{width: 600px;text-align: center;
    		margin:0 auto;margin-top: 50px;
  			margin-bottom: 600px;}
		fieldset{width:400px; margin:0 auto;padding:20px;border: 0;}
		.yy_1000 {
			width: 1000px
		}
		input[type=text]{padding-left:20px;width:500px; height:60px; margin : 0 auto;margin-bottom: 10px}
		#phone + hr{color:lightgrey;width: 500px;} 
		#yy_pwdp{color:grey; font-size: 50px;font-weight: bold;}
		#yy_logo{font-weight: 800;color:#ad968a; text-decoration: none; font-size: 80px;}
		#yy_search{width:523px;height: 90px; background:#ad968a; color:white;border: none; font-size:20pt }
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".yy_passsearchform").submit(function(){
				var id = $("#id").val();
				var name = $("#name").val();
				var phone = $("#phone").val();
				$.ajax({
					type :"post",
					url :"pwdinfo",	
					data:{"id": id, "name" : name, "phone" : phone},
					success : function(resp){
						location.href = 'pwdinfo';
					}//success end
					,error:function(){
						console.log("오류")
					}
				})
			})
		})
	</script>
</head>
<body>
	<div class = "out container">
		<form method="post" class="yy_passsearchform">
			<header><div><span id="yy_logjoin"><a  href = "../Login">로그인</a><a href = "../Join">회원가입</a></span></div></header>
			<fieldset class="yy_passsearchform">
				<div class="yy_passcontents">
					<a href="../Main/index" id="yy_logo">YUMYUM</a>
					<p id="yy_pwdp">비밀번호 찾기</p>
					<input type="text" id ="id" name ="id" placeholder="아이디"><br>
					<input type ="text" id = "name" name ="name" placeholder="이름"><br>
					<input type ="text" id = "phone" name ="phone" placeholder="휴대폰 번호" pattern="[0-9]{11}"><hr>
					<input type="submit" name="pwdsearch" id="yy_search" value ="찾기">
				</div>
			</fieldset>
		<footer><jsp:include page="../Main/Y_footer.jsp"></jsp:include></footer>
		</form>
	</div>
</body>
</html>