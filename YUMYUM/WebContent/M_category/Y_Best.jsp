<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<meta charset = "UTF-8">
		<script src = "../js/Y_Best.js"></script>
		<link rel = "stylesheet" type = "text/css" href = "../css/Y_Place.css">
		<title>Best Place</title>
	</head>
	<body>
		<header>
			<jsp:include page="../Main/Y_header.jsp"/>
		</header>
		<div class= container>
			<div class = "for_nav">
				<div class = "page_title"><span><a href = "../M_category/best">Best Place</a></span></div>
				<nav class = "yh_nav">
					<!-- 지역별 -->
					<select name = "city" id = "sel_city">
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
					<select name = "sub_city" id = "sel_subcity">
						<option value = "">전체</option>
					</select>
					<!-- 음식별 -->
					<select name = "food" id = "sel_food">
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
			<table class = "yh_Y_Best">
			</table> 
		</div>
		<footer>
			<jsp:include page="../Main/Y_footer.jsp"/>
		</footer>
	</body>
</html>