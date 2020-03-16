<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<!-- 상세페이지 테스트 5 -->
<title>YUMYUM~ 맛집 상세정보</title>

<link href="../css/Y_Detail.css" rel="stylesheet" type="text/css">
<link href="../css/Y_Detail_Review.css" rel="stylesheet" type="text/css">
<link href="../css/Y_Review_Modal.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

<!-- 지도 api -->
<script src="../js/Y_Map.js" charset=utf-8></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=5c358fda55f9d5604f1d9b9f92f7c537&libraries=services"></script>

<script src="../js/Y_Detail.js" charset="utf-8"></script>
<script src="../js/Y_Detail_Review.js" charset="utf-8"></script>


<style>
.detail_con{max-width:1100px!important; padding-left:59px!important; border:1px solid #eae2dd; padding-bottom:63px;}
.user_info{width:20%;}
.rev_content, .re_content{width:75%;}
.re_recom{left:99px;}

</style>

<script>
$(function(){
	//로그인 없이 찜하기 클릭시 로그인페이지로 이동
	$('#jjim_btn_login').on('click',function(){
		alert("로그인이 필요합니다.");
		location.href="../Login";
		//location.href="Login";
	})
	
	//첫번째 img_li에는 b_img와 동일한 img
	var output = '';
		
	var addImg = "${mp_info.MP_ADD_IMG_URL}";
	//var addImg = "1.jpg*2.jpg"
	console.log(addImg);
	var addImgSplit = addImg.split('*');
	console.log("addImgSplit.length = " + addImgSplit.length);
		
	if(addImg != ""){ //추가파일이 없을땐 쳐냄 li생성안함
		//추가이미지 첫 이미지는 대표이미지
		output += '<li class="HY_li">';
		output += '	<img src = "../img/${mp_info.MP_IMG_URL}" class = "photo_list_li">';
		output += '</li>';
		
		for(var i in addImgSplit){
			console.log(i);
			output += '<li class="HY_li">';
			output += '	<img src = "../img/' + addImgSplit[i] + '" class = "photo_list_li">';
			output += '</li>';
		}
	}
	
	$('#photo_list').append(output);
	
	//li이미지 클릭시 img 바꾸기
	$(".photo_list_li").hover(function(){
		var photo = $(this).attr('src');
		$("#b_img").attr('src',photo);
		$(this).css('border','1px solid #7b6a61');
	});//photo hover
	$(".photo_list_li").mouseleave(function(){
		$(this).css('border','none');
	})
	
})
</script>
</head>
<body>

<header>
<jsp:include page="../Main/Y_header.jsp"/>
</header>

<!-- 맛집번호 저장용 input -->
<input type="hidden" name="mp_no" value="${mp_info.MP_NO}">
<input type="hidden" id="star_rating_img" value="${mp_info.MP_AVG_GRADE}">
<!-- <input type="hidden" name="id" value="admin"> -->

<%-- <c:set var="mp" value="mp_info"/> --%>
<div class="container detail_con">
	<div class="main_area">
		<div class="img_area">
			<img id="b_img" src="../img/${mp_info.MP_IMG_URL}"> <!-- Detail Main img -->
			<div class="photo">
				<ul id="photo_list"></ul>
			</div>
		</div>
		<div class="title_wrap">
			<!-- 업체명 -->
			<h3 class="mp_name">${mp_info.MP_NAME}</h3>
			
			<!-- 찜버튼 -->
			<div class="jjim_wrap">
			<%
				String id  = (String) session.getAttribute("id");
				if(id != null && !id.equals("")){//로그인 안되있으면 login.jsp로 쳐냄
			%>
				<button type="button" id="jjim_btn"><img src="../img/icon1.png" style="width:27px;" id="jjim_img"></button>
			<% }else{%>
				<button type="button" id="jjim_btn_login"><img src="../img/icon1.png" style="width:29px;" id="jjim_img"></button>
			<%} %>
				<p class="jjim_p">찜하기</p>
				
			</div>
			<span class="detail_info">
				<img src="../img/view_cnt_icon.png"> ${mp_info.MP_VIEW_CNT} 
			 	<img src="../img/d_edit.png"> ${mp_info.MP_RE_CNT}
			 	<img src="../img/d_star.png"> ${mp_info.MP_AVG_GRADE}
			</span>			
			<span id="rating"></span>
			
			<!-- 맛집 평점에 따른 별점부분 -->
			<span class='star-rating'>
			<span style ="width:50%" class="star_img"></span>
			</span>
			
			<hr>
			<table class="info">
				<tr>
					<td class="HY_td">업체명</td>
					<td class="HY_td">${mp_info.MP_NAME}</td>
				</tr>
				<tr>
					<td class="HY_td">업종</td>
					<td class="HY_td">${mp_info.MP_KIND}</td>
				</tr>
				<tr>
					<td class="HY_td">전화번호</td>
					<td class="HY_td">${mp_info.MP_PHONE}</td>
				</tr>
				<tr>
					<td class="HY_td">주소</td>
					<td class="HY_td mp_address">${mp_info.MP_ADDRESS}</td>
				</tr>
				<tr>
					<td class="HY_td">영업시간</td>
					<td class="HY_td">${mp_info.MP_HOURS}</td>
				</tr>
				<tr>
					<td class="HY_td">맛집소개</td>
					<td class="HY_td">${mp_info.MP_INFO}</td>
				</tr>				
				<tr>
					<td class="HY_td">해쉬태그</td>
					<td class="HY_td">${mp_info.MP_HASH}</td>
				</tr>
				<tr class="mp_regidate_tr">
					<td class="mp_regidate">등록일 </td>
					<td class="mp_regidate">${mp_info.MP_REGIDATE}</td>
				</tr>				
			</table>
		</div>
		<%-- <p class="detail_date">등록일 : ${mp_info.MP_REGIDATE}</p> --%>
	</div>
	<!-- main_area -->
	
	<!-- 지도랑, 이맛집에대한 검색, 해쉬태그? -->
	<div class="sub_area">
		<hr>
		<h4 class="hy_h4">찾아오시는 길</h4>
		<div id="map"></div>
		<div class="mp_info_test">
			<!-- Recommend_mp ajax 부분 들어감 -->
			
			<h4 class="hy_h4 search_h4">포털검색으로 ${mp_info.MP_NAME}의 정보 알아보기</h4>
			<a href="https://search.naver.com/search.naver?where=m&ie=utf8&query=${mp_info.MP_NAME}" class="search_img">
				<img src="../img/naver_logo.png" style="width:125px;" class="logoImg"></a>
			<a href="http://search.daum.net/search?w=tot&q=${mp_info.MP_NAME}" class="search_img">
				<img src="../img/google_logo.png" style="width:110px;" class="logoImg"></a>
			<a href="https://www.google.com/search?q=${mp_info.MP_NAME}" class="search_img">
				<img src="../img/daum_logo.png" style="width:100px;" class="logoImg"></a>
		</div>
	</div>
	<!-- sub_area -->
	
	<div class="reviewArea">
      <header class="reviewHeader">
         <span class="review_cnt">리뷰(0)</span>
         <span id="review_write">
            <button type="button" id="review_write_btn"
               onclick = 'createWriteForm()'>리뷰작성<br><img src="../img/d_edit.png"></button>
               <button type="button" id="review_write_cancel_btn" style="display:none;"
               onclick = 'closeWriteForm()'>작성취소<br><img src="../img/d_edit.png"></button>
         </span>
         <hr>
         <div class="selects">
            <span>정렬 순</span>
            <select id="order_by">
               <option value="0" selected>최신 순</option>
               <option value="1">추천 순</option>
            </select>
            <span>평점</span>
            <select id="grade_by">
               <option value="0" selected>전체 보기</option>
               <option value="1">평점 4 이상만</option>
            </select>
         </div>
      </header>
      <!-- reviewHeader -->
      
      <div class="reviews">
         <span>아직 리뷰가 없습니다.</span>
      </div>
      <!-- reviews -->
   </div>
   <!-- reviewArea -->
   
   
   
   <button type="button" class="btn btn-lg hiddenBtn" data-toggle="modal" data-target="#imgDetails" id="imgsBtn"></button>
   <!-- modalArea -->
	<div class="modal fade" id="imgDetails" tabindex="-1" role="dialog" aria-labelledby="imgsDetail" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-center">
			<div class="modal-content">
				<button type="button" class="close xBtn" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">X</span></button>
			    <div class="modal-body">
			       
					<div class="sameRImgsArea modal-center">
							
					</div>
				</div>
				<div class="modal-footer">
					
				</div>
			</div>
		</div>
	</div>
	<!-- modalArea Ends -->
	
</div>
<!-- out_container -->
<button id="move_top_btn" type="button">TOP</button>
<footer>
	<jsp:include page="../Main/Y_footer.jsp"/>
	
</footer>
<script>
	function createWriteForm()
	{
		$('#review_write_btn').css('display', 'none');
		$('#review_write_cancel_btn').css('display', 'inline');
		var mp_name = '${mp_info.MP_NAME}';
		mp_name = mp_name.replace(' ', '+');
		var frameSrc = "../review/Y_Review_Write?mp_name="+mp_name+"&mp_no=${mp_info.MP_NO}";
		var frame = '<iframe id="writeForm" allowTransparency=＂true＂ name="writeForm" frameborder="0" src=' + frameSrc + ' scrolling="no" seamless></iframe>';
		$('.reviewHeader').append(frame);
		
	}
	function closeWriteForm()
	{
		$('iframe').remove();
		$('#review_write_cancel_btn').css('display', 'none');
		$('#review_write_btn').css('display','inline');
	}

</script>
</body>

</html>