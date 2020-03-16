<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>        
<!DOCTYPE html>
<html>
<head>
<title>내 정보 수정</title>
<link href="../css/modify.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="../js/modify.js" charset="utf-8"></script>
<!-- 유효성검사  -->
<script src="../js/address.js"></script>
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
$(function(){
	if("${user_is_owner}"==1){
		$("#business_no").css('display','block');
	}
})
</script>
<style>
footer hr{margin-top: 4rem;}
</style>
</head>
<body>
<%-- <header><jsp:include page="Y_header.jsp"/> </header> --%>

<div class="out_container">
	<div class="join_line">
	<form method="post" action="ModifyMyInfo" id="joinform">
		<fieldset class="HY_legend">
			<legend><a href="../Main/index" class="HY_a">YUMYUM</a></legend>
				<label class="HY_label" for="id">ID</label><br>
				<input class="HY_input" type="text" placeholder="ID" size="10" maxLength="10" name="id" id="id" autocomplete="off" value="${id}" readOnly>
				<!-- <input class="HY_input" type="button" value="ID중복검사" id="idcheck"> --><br>
				<span class="HY_span idMessage"></span>
				
				<label class="HY_label" for="pass">Password</label><br>
				<input class="HY_input" type="password" placeholder="Password" maxLength="20" name="pass" id="pass" value="${pass}"><br>
				<span class="HY_span passMessage"></span>
				
				<label class="HY_label" for="name">Name</label><br>
				<input class="HY_input" type="text" placeholder="Name" maxLength="10" name="name" id="name" autocomplete="off" value ="${name}" readOnly><br>
				<span class="HY_span nameMessage"></span>
				
				<label for="birthday" class="HY_label">생년월일</label><br>
				<input class="HY_input" type="text"  name="birthday" id="birthday" placeholder="Birthday" autocomplete="off" value="${birthday}"><br>
				<!-- <input class="HY_input" type="text" size="2" maxLength="2" name="month" id="month" placeholder="Month"> -->
				<span class="HY_span birthMessage"></span>
								
				<!-- <input type="text" size="6" maxLength="6" name="jumin1" id="jumin1"
						placeholder="주민번호 앞자리"> -
				<input type="text" size="7" maxLength="7" name="jumin2" id="jumin2"
						placeholder="주민번호 뒷자리"><br> -->
				<!-- 생년월일 -->
				
				<label class="HY_label">성별</label><br>
				<input type ="text" name = "gender" id ="gender" value="${gender}" readOnly>
				<span class="HY_span genderMessage"></span><br>
				<!-- 성별 -->
				
				<label class="HY_label">회원 선택</label>
				<br>
				<c:if test="${user_is_owner==0}">
					<input type="checkbox" id="box1" name="who" onClick="oneCheckbox(this)" value="0" checked><label class="who" for="box1"><span>일반회원</span></label>&nbsp;&nbsp;
					<input type="checkbox" id="box2" name="who" onClick="oneCheckbox(this)" value="1"><label class="who" for="box2"><span>사업자</span></label><br>
				</c:if>
				<c:if test="${user_is_owner==1}">
					<input type="checkbox" id="box1" name="who" onClick="oneCheckbox(this)" value="0"><label class="who" for="box1" ><span>일반회원</span></label>&nbsp;&nbsp;
					<input type="checkbox" id="box2" name="who" onClick="oneCheckbox(this)" value="1" checked><label class="who" for="box2"><span>사업자</span></label><br>
				</c:if>
				<input type="text" class="HY_input" id="business_no" name="business_no" placeholder="- 없이 숫자만 입력" maxlength="10" autocomplete="off" value="${business_no}">
				<span class="HY_span bnMessage"></span>
				<!-- 손님 or 사업자 -->
				
				<label class="HY_label">E-mail</label><br>
				<input class="HY_input" type="text" name="email" id="email" maxlength="20" value="${email}" ><br>
				<span class="HY_span emailMessage"></span>		
				<!-- E-mail -->
				
				<label class="HY_label">우편번호</label><br>
				<input class="HY_input" type="text" name="post1" id="post1" readonly value = "${postcode}">
				<input class="HY_input" id="postbtn" type="button" value="우편검색" onclick="Postcode()"><br>
				<span class="HY_span postMessage"></span>
				<!-- 우편번호 -->
				
				<label class="HY_label">주소</label><br>
				<input class="HY_input" type="text" name="address" id="address" readonly value ="${address}"><br>
				<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
				<span class="HY_span addressMessage"></span>
				<!-- 주소 -->
				
				<label class="HY_label">휴대폰 번호</label><br>
				<input class="HY_input" type="text" maxLength="11" name="phone" id="phone" placeholder="- 없이 숫자만 입력" autocomplete="off" value = "${phone}"><br>
				<span class="HY_span phoneMessage"></span>
				
				<label class="HY_label">닉네임</label><br>
				<input class="HY_input" type="text" maxLength="10" name="nickname" id="nickname" placeholder="NickName" autocomplete="off" value = "${nickname}"><br>
				<span class="HY_span nickMessage"></span>
				
				<button class="HY_button" id="submit_btn" type="submit" >수정</button><button class="HY_button yy_reset" type="reset" value="취소" onClick="location.href='Y_mypage.jsp'">취소</button>	
				
		</fieldset>
	</form>
	</div>
</div>
<footer style="text-align:center"><jsp:include page="../Main/Y_footer.jsp"/></footer>
</body>
</html>