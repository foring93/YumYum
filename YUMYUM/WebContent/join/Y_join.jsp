<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>회원가입</title>
<link href="css/join.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="js/joinjs.js" charset="utf-8"></script>
<!-- 유효성검사  -->
<script src="js/address.js"></script>
<!-- 우편번호 검색 -->
<script>
//체크빡스 체크용
function oneCheckbox(a){
    var obj = document.getElementsByName("who");
    for(var i=0; i<obj.length; i++){
        if(obj[i] != a){
            obj[i].checked = false;
        }
    }
}
</script>
</head>
<body>
<%-- <header><jsp:include page="/Main/Y_header.jsp"/> </header> --%>

<div class="out_container">
	<div class="join_line">
	<form method="post" action="joinAction" id="joinform">
		<fieldset class="HY_legend">
			<legend><a href="Main/index" class="HY_a">YUMYUM</a></legend>
				<label class="HY_label" for="id">ID</label><br>
				<input class="HY_input" type="text" placeholder="ID" size="10" maxLength="10" name="id" id="id" autocomplete="off">
				<!-- <input class="HY_input" type="button" value="ID중복검사" id="idcheck"> --><br>
				<span class="HY_span idMessage"></span>
				
				<label class="HY_label" for="pass">Password</label><br>
				<input class="HY_input" type="password" placeholder="Password" maxLength="20" name="pass" id="pass"><br>
				<span class="HY_span passMessage"></span>
				
				<label class="HY_label" for="name">Name</label><br>
				<input class="HY_input" type="text" placeholder="Name" maxLength="10" name="name" id="name" autocomplete="off"><br>
				<span class="HY_span nameMessage"></span>
				
				<label for="" class="HY_label">생년월일</label><br>
				<input class="HY_input" type="text" size="4" maxLength="4" name="year" id="year" placeholder="Year" autocomplete="off">
				<!-- <input class="HY_input" type="text" size="2" maxLength="2" name="month" id="month" placeholder="Month"> -->
				<select name="month" id="month">
					<option value="">Month</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
				</select>
				<input class="HY_input" type="text" size="2" maxLength="2" name="day" id="day" placeholder="Day"><br>
				<span class="HY_span birthMessage"></span>
								
				<!-- <input type="text" size="6" maxLength="6" name="jumin1" id="jumin1"
						placeholder="주민번호 앞자리"> -
				<input type="text" size="7" maxLength="7" name="jumin2" id="jumin2"
						placeholder="주민번호 뒷자리"><br> -->
				<!-- 생년월일 -->
				
				<label class="HY_label">성별</label><br>
				<select name="gender" id="gender">
					<option value="">성별을 선택하세요</optaion>
					<option value="M">남자</option>
					<option value="F">여자</option>
				</select>
				<span class="HY_span genderMessage"></span><br>
				<!-- 성별 -->
				
				<label class="HY_label">회원 선택</label>
				<br>
				<input type="checkbox" id="box1" name="who" onClick="oneCheckbox(this)" value="0"><label class="who" for="box1"><span>일반회원</span></label>&nbsp;&nbsp;
				<input type="checkbox" id="box2" name="who" onClick="oneCheckbox(this)" value="1"><label class="who" for="box2"><span>사업자</span></label><br>
				<input type="text" class="HY_input" id="business_no" name="business_no" placeholder="- 없이 숫자만 입력" maxlength="10" autocomplete="off">
				<span class="HY_span bnMessage"></span>
				<!-- 손님 or 사업자 -->
				
				<label class="HY_label">E-mail</label><br>
				<input class="HY_input" type="text" name="email" id="email" maxlength="20"> @
				<input class="HY_input" type="text" name="domain" id="domain">
				<select name=sel id="sel">
					<option value="">직접입력</option>
					<option value="naver.com">naver.com</option>
					<option value="daum.net">daum.net</option>
					<option value="gmail.com">gmail.com</option>
				</select>
				<span class="HY_span emailMessage"></span>		
				<!-- E-mail -->
				
				<label class="HY_label">우편번호</label><br>
				<input class="HY_input" type="text" name="post1" id="post1" readonly>
				<input class="HY_input" id="postbtn" type="button" value="우편검색" onclick="Postcode()"><br>
				<span class="HY_span postMessage"></span>
				<!-- 우편번호 -->
				
				<label class="HY_label">주소</label><br>
				<input class="HY_input" type="text" name="address" id="address" readonly><br>
				<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
				<span class="HY_span addressMessage"></span>
				<!-- 주소 -->
				
				<label class="HY_label">휴대폰 번호</label><br>
				<input class="HY_input" type="text" maxLength="11" name="phone" id="phone" placeholder="- 없이 숫자만 입력" autocomplete="off"><br>
				<span class="HY_span phoneMessage"></span>
				
				<label class="HY_label">닉네임</label><br>
				<input class="HY_input" type="text" maxLength="10" name="nickname" id="nickname" placeholder="NickName" autocomplete="off"><br>
				<span class="HY_span nickMessage"></span>
				
				<button class="HY_button" id="submit_btn" type="submit" value="회원가입">회원가입</button>
				<button class="HY_button" type="button" value="취소" onClick="location.href='Main/index'">취소</button>	
		</fieldset>
	</form>
	</div>
</div>

<footer><jsp:include page="/Main/Y_footer.jsp"/></footer>
</body>
</html>