<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<form method="post" action="modify" id="joinform"style="width:700px"
	enctype="multipart/form-data"  name="mp_regiform">
		<fieldset class="HY_legend">
			<legend><a href="../Main/index" class="HY_a">YUMYUM</a></legend>
			<!-- MPA 고유 번호 -->
			<input type="hidden" name="MP_NO" value="${mpa_detail.MP_NO}">
				<!-- 식당이름 -->
				<label class="HY_label" for="MP_NAME">*식당이름</label><br>
				<input class="HY_input yy_m_name" type="text" placeholder="식당이름을 입력하세요"
				 size="30" maxLength="30" name="MP_NAME" id="MP_NAME" autocomplete="off" value="${mpa_detail.MP_NAME}">
				<br>
				<span class="HY_span nameMessage"></span>
				
				<!-- 음식 분류 -->
				<label class="HY_label" for="MP_KIND">*분류</label><br>
				<input class="HY_input yy_m_category" type="text" name="MP_KIND" id="MP_KIND" value="${mpa_detail.MP_KIND}">
				<select name="KIND" id="KIND">
					<option value="">직접입력</option>
					<option value="한식">한식</option>
					<option value="양식">양식</option>
					<option value="중식">중식</option>
					<option value="일식">일식</option>
					<option value="분식">분식</option>
				</select>
				<span class="HY_span kindMessage"></span>
				<br>
				
				<!-- 등록인 -->
				<label class="HY_label" for="MP_WRITER">*등록인(수정불가)</label><br>
				<input class="HY_input" type="text" maxLength="20" name="MP_WRITER" id="MP_WRITER" value="${mpa_detail.MP_WRITER}" readonly="readonly" style="background: lightgray"><br>
				<span class="HY_span passMessage"></span>
				
				<!-- 우편번호 -->
				<label class="HY_label" for="post1">*우편번호</label><br>
				<input class="HY_input yy_m_postcode" type="text" name="post1" id="post1" readonly>
				<input class="HY_input" id="postbtn" type="button" value="우편검색" onclick="Postcode()"><br>
				<span class="HY_span postMessage"></span>
				
				<!-- 주소 -->
				<label class="HY_label" for="address">*주소</label><br>
				<input class="HY_input yy_m_address" type="text" name="MP_ADDRESS" id="address" value="${mpa_detail.MP_ADDRESS}"readonly><br>
				<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
				<span class="HY_span addressMessage"></span>
				
				<!-- 식당 전화번호 -->
				<label for="" class="HY_label">*식당 전화번호 ('-'없이 입력)</label><br>
				<input class="HY_input yy_m_telephone" type="text" size="4" 
					maxLength="11" name="MP_PHONE" id="year" placeholder="식당전화번호를 입력해주세요" 
					autocomplete="off"style="width:700px"value="${mpa_detail.MP_PHONE}">
				<span class="HY_span telephoneMessage"></span>
				<br>
				
				<!-- 식당 상세정보 -->
				<label class="HY_label" for="MP_INFO">*상세정보</label><br>
				<textarea rows="10" cols="85" name="MP_INFO" id="MP_INFO" class = "yy_m_detail">${mpa_detail.MP_INFO}</textarea>
				<span class="HY_span detailMessage"></span><br>
				
				<!-- 운영시간 -->
				<label class="HY_label" for="MP_HOURS">*오픈/클로징 시간</label>
				<br>
				<input class="HY_input yy_m_time" type="text" placeholder="오픈/클로징 시간을 입력하세요" maxLength="11" 
					name="MP_HOURS" id="MP_HOURS" autocomplete="off" value="${mpa_detail.MP_HOURS}"><br>
				<span class="HY_span OpenCloseTimeMessage"></span>
				
				<!-- 대표 이미지 -->
				<label class="HY_label">*대표이미지</label>
				<label class="HY_label margin_left" for="etc_imgs" id="etc_imgs_label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;추가 첨부 이미지
				<c:if test="${mpa_detail.MP_ADD_IMG_URL == null}">
					<span class="HY_span addfileMessage">추가 이미지 파일이 없습니다.</span>
				</c:if>
				<c:if test="${mpa_detail.MP_ADD_IMG_URL != null}">
					<span class="HY_span addfileMessage"></span>
				</c:if>
				</label><br>
				<label class="rep_img" for="rep_img_file"><img src="../img/${mpa_detail.MP_IMG_URL}"></label>
				<input class="upload-hidden" type="file" name="rep_img_file" id="rep_img_file" accept=".gif, .jpg, .png, .jpeg">
				<input type="hidden" id="rep_img_name" name="rep_img_name" value="${mpa_detail.MP_IMG_URL}">
								
				<!-- 추가 첨부 이미지 -->
				<input type="text" class="HY_input margin_left" name="etc_imgs_names" id="etc_imgs_names" size="60" readonly value="${mpa_detail.MP_ADD_IMG_URL}">
				<span class="HY_span fileMessage"></span>
				<input class="upload-hidden" type="file" multiple="multiple" name="etc_imgs" id="etc_imgs" accept=".gif, .jpg, .png, .jpeg"><br>
				
				
				<!-- 해쉬태그 -->
				<label class="HY_label">해쉬태그(5개 까지 가능)</label>
				<br>
				<input class="HY_input" type="text" placeholder="#해쉬태그를 입력하세요(선택사항)" 
					size="100" maxLength="100" name="MP_HASH" id="MP_HASH" autocomplete="off" value="${mpa_detail.MP_HASH}"><br>
				
				<!-- submint & cancel 버튼 -->
				<button class="HY_button" id="submit_btn" type="submit">제출</button>
				<button class="HY_button" type="button" value="취소" onClick="location.href='../Main/index'">입력취소</button>	
		</fieldset>
	</form>