<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>회원가입</title>
<link href="../css/userinfomodify.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<body>
<div class="out_container">
	<div class="yy_form">
				<h3 id="yy_h3">내 정보</h3>
				<label class="HY_label" for="id">ID</label><br>
				<input class="HY_input" type="text" placeholder="ID" size="10" maxLength="10" name="id" id="id" readOnly><br>
				 
				<label class="HY_label" for="pass">Password</label><br>
				<input class="HY_input" type="password" placeholder="Password" maxLength="10" name="pass" id="pass"><br>
				<span class="HY_span passCheck">비밀번호 4자리 이상 입력하세요.</span>
				
				<label class="HY_label" for="name">Name</label><br>
				<input class="HY_input" type="text" placeholder="Name" maxLength="10" name="name" id="name" readOnly><br>
				
				<label for="" class="HY_label">생년월일</label><br>
				<input class="HY_input" type="text" id="birthday" readOnly><br>
								
				<!-- <input type="text" size="6" maxLength="6" name="jumin1" id="jumin1"
						placeholder="주민번호 앞자리"> -
				<input type="text" size="7" maxLength="7" name="jumin2" id="jumin2"
						placeholder="주민번호 뒷자리"><br> -->
				<!-- 생년월일 -->
				
				<label class="HY_label">성별</label><br>
				<input class="HY_input" type ="text" name="gender" id="gender" readOnly><br>
				<!-- 성별 -->
				
				<label class="HY_label" >E-mail</label><br>
				<input class="HY_input" type="text" name="email" id="email" readOnly><br>
				
				<!-- E-mail -->
				
				<label class="HY_label">우편번호</label><br>
				<input class="HY_input" type="text" size="6" name="post1" id="post1" readOnly><br>

				<!-- 우편번호 -->
				
				<label class="HY_label">주소</label><br>
				<input class="HY_input" type="text" size="50" name="address" id="address" readOnly><br>
				<!-- 주소 -->
				
				<label class="HY_label">휴대폰 번호</label><br>
				<input class="HY_input" type="text" maxLength="11" name="phone" id="phone" placeholder="- 없이 숫자만 입력" readOnly><br>
				
				<label class="HY_label">닉네임</label><br>
				<input class="HY_input" type="text" maxLength="10" name="nickname" id="nickname" placeholder="NickName" readOnly>
				
				<button class="HY_button yy_btn" type="submit" value="수정">수정</button>
	</div>
	</div>
</body>
</html>