<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<style>
.row{height:1200px;}
.HY_legend{margin-left:70px;}
</style>
<body>
	<form method="post" action="mp_modify" id="joinform"style="width:700px"
	enctype="multipart/form-data"  name="mp_regiform">
		<fieldset class="HY_legend">
			<legend><a href="../Main/index" class="HY_a">YUMYUM</a></legend>
			<input type="hidden" name="MP_NO"  value = "${m.MP_NO}">
				<label class="HY_label" for="MP_NAME">*식당이름</label><br>
				<input class="HY_input yy_m_name" type="text" placeholder="식당이름을 입력하세요" size="10" maxLength="30" name="MP_NAME" id="id" autocomplete="off" value ="${m.MP_NAME}">
				<!-- <input class="HY_input" type="button" value="ID중복검사" id="idcheck"> --><br>
				<span class="HY_span nameMessage"></span>
				<label class="HY_label" for="MP_KIND">*분류</label><br>
				<input class="HY_input yy_m_category" type="text" name="MP_KIND" id="domain" value = "${m.MP_KIND}">
				<select name="KIND" id="KIND">
					<option value="">분류</option>
					<option value="양식">양식</option>
					<option value="중식">중식</option>
					<option value="일식">일식</option>
					<option value="한식">한식</option>
					<option value="직접입력">직접입력</option>
				</select>
				<span class="HY_span kindMessage"></span>
				<br>
				<label class="HY_label" for="MP_WRITER">*등록인(수정불가)</label><br>
				<input class="HY_input" type="text" maxLength="20" name="MP_WRITER" id="pass" value="${m.MP_WRITER}" readonly="readonly" style="background: lightgray"><br>
				<span class="HY_span passMessage"></span>
				
				<label class="HY_label">*우편번호</label><br>
				<input class="HY_input yy_m_postcode" type="text" name="post1" id="post1" readonly>
				<input class="HY_input" id="postbtn" type="button" value="우편검색" onclick="Postcode()"><br>
				<span class="HY_span postMessage"></span>
				<!-- 우편번호 -->
				
				<label class="HY_label">*주소</label><br>
				<input class="HY_input yy_m_address" type="text" name="MP_ADDRESS" id="address" value = "${m.MP_ADDRESS}"readonly><br>
				<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
				<span class="HY_span addressMessage"></span>
				
				<label for="" class="HY_label">*식당 전화번호 ('-'없이 입력)</label><br>
				<input class="HY_input yy_m_telephone" type="text" size="4" maxLength="11" name="MP_PHONE" id="year" placeholder="식당전화번호를 입력해주세요" autocomplete="off"
				style="width:700px" value = "${m.MP_PHONE}">
				<!-- <input class="HY_input" type="text" size="2" maxLength="2" name="month" id="month" placeholder="Month"> -->
				<span class="HY_span telephoneMessage"></span>
				
				<br>
				
				<label class="HY_label">*상세정보</label><br>
				<textarea rows="10" cols="95" name="MP_INFO" class = "yy_m_detail">${m.MP_INFO}</textarea>
				<span class="HY_span detailMessage"></span><br>
				<!-- 성별 -->
				
				<label class="HY_label">*오픈/클로징 시간</label>
				<br>
				<input class="HY_input yy_m_time" type="text" placeholder="오픈/클로징 시간을 입력하세요" maxLength="11" name="MP_HOURS" id="name" autocomplete="off" value="${m.MP_HOURS}"><br>
				<span class="HY_span OpenCloseTimeMessage"></span>
				
				
				<label class="HY_label">*대표이미지</label><br>
				<input class="HY_input yy_m_file" type="file" name="MP_IMG_URL" value="${m.MP_IMG_URL}"><br>
				<span class="HY_span fileMessage"></span>
				<label class="HY_label">추가 첨부이미지</label><br>
				<input class="HY_input" type="file"multiple="multiple"name="MP_IMG_URL"><br>
				<span class="HY_span emailMessage"></span>	
				<label class="HY_label">해쉬태그('#'로 구분)</label>
				<br>
				<input class="HY_input" type="text" placeholder="#해쉬태그를 입력하세요(선택사항)" maxLength="30" name="MP_HASH" id="name" autocomplete="off" value="${m.MP_HASH}"><br>
				<span class="HY_span bnMessage"></span>
				
				<button class="HY_button" id="submit_btn" type="submit">수정</button>
				<button class="HY_button" type="button" value="취소" onClick="location.href='admin'">입력취소</button>	
		</fieldset>
	</form>
</body>
</html>