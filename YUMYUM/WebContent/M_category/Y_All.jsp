<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<style>
	.AllImgTd{width: 300px;}
</style>
	<head>
		<meta charset="UTF-8">
		<title>전체 맛집</title>
		<script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src = "../js/Y_All.js"></script>
		<link rel = "stylesheet" type = "text/css" href = "../css/Y_Place.css">
		<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	</head>
	<body>
		<header>
			<jsp:include page="/Main/Y_header.jsp"/>
		</header>
		<div class = "container">
			<!-- ### 검색창 추가 ### -->
			<div class = "out">
				<div class = "for_nav">
					<div class = "page_title"><span><a href = "../M_category/all">All Place</a></span></div>
					<nav class = "yh_nav">
						<!-- 지역별 -->
						<select name = "city" id = "sel_city" class = "all_sel">
							<option value = "">전체</option>
							<option value = "서울">서울</option>
							<option value = "경기">경기</option>
							<option value = "인천">인천</option>
							<option value = "대전">대전</option>
							<option value = "대구">대구</option>
							<option value = "부산">부산</option>
							<option value = "울산">울산</option>
							<option value = "세종">세종</option>
							<option value = "강원">강원</option>
							<option value = "충북">충북</option>
							<option value = "충남">충남</option>
							<option value = "전북">전북</option>
							<option value = "전남">전남</option>
							<option value = "경북">경북</option>
							<option value = "경남">경남</option>
							<option value = "광주">광주</option>
							<option value = "제주">제주</option>	
						</select>
						<!-- 상세 지역 -->
						<select name = "sub_city" id = "sel_subcity" class = "all_sub_sel">
							<option value = "">전체</option>
						</select>
						<!-- 음식별 -->
						<select name = "food" id = "sel_food" class = "all_sel_food">
							<option value = "">종류</option>
							<!-- 추가 -->
							<option value = "한식">한식</option>
							<option value = "양식">양식</option>
							<option value = "중식">중식</option>
							<option value = "일식">일식</option>
							<option value = "분식">분식</option>
							<option value = "치킨">치킨</option>
							<option value = "피자">피자</option>
							<option value = "고기">고기</option>
							<option value = "국수">국수</option>
							<option value = "디저트">디저트</option>
							<option value = "멕시코">멕시코음식</option>
							<option value = "태국">태국음식</option>
							<option value = "베트남">베트남음식</option>
						</select>
					</nav>
				</div>
				<br>
				<div id = "ss">
					<div id = "search_section">
						<br>
						<input type = "text" id = "search_place" name = "search" value = "${searchWord}" autocomplete = "off" placeholder = "검색할 단어를 입력해주세요.">
						<button type = "button" id = "search_btn"><i class = "fa fa-search"></i></button>
					</div>
				</div>
			</div>
			<table class = "yh_Y_All">
			</table>
			<div class = "pagination">
				<!-- 페이지 추가 -->
			</div>
		</div>
		<footer>
			<jsp:include page="/Main/Y_footer.jsp"/>
		</footer>
	</body>
</html>
