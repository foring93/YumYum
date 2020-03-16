<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<link rel = "stylesheet" type = "text/css" href = "../css/Y_admin.css">
<style>hr{margin-bottom: 0px}</style>
<div class="b_div">
	<div>
		<h1 class="b_h1" style="display:inline">전체 식당 관리 </h1>
		
		<div style="background:blue; display:inline;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
		<span style="font-weight: bolder;color:gray">상세보기</span>
		<hr>
		<span id="listcount"style="font-weight: bolder;line-height:60px;color:gray">전체 수:${listcount}</span>
		<button type="button" name="searchbutton"class="search">검색</button>
		<input type="text" name="search" class="search">
		<select name="value" id="value"class="search" style="height:30px">
			<option value='0' selected="selected">상호명</option>
				<option value='1' >평점</option>
				<option value='2' >리뷰수</option>
				<option value='3' >조회수</option>
				<option value='4' >점주</option>
				<option value='5' >등록일</option>
		
		</select>
			<table class="table-form">
				<thead>
					<tr>
						<th>번호</th>
						<th>이름</th>
						<th>평점</th>
						<th>리뷰수</th>
						<th>조회수</th>
						<th>점주</th>
						<th>등록일</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
			<tbody>
		<c:forEach var="m" items="${mplist}">
		<tr>
			<td>${m.MP_NO}</td>
			<td><a href="../M_category/place.move?mp_no=${m.MP_NO}">${m.MP_NAME}</a></td>
			<td>${m.MP_AVG_GRADE}</td>
			<td>${m.MP_RE_CNT}</td>
			<td>${m.MP_VIEW_CNT}</td>
			<td>${m.MP_WRITER}</td>
			<td>${m.MP_REGIDATE}</td>
			<td><a href = "admin?page=Y_board_m_info&MP_NO=${m.MP_NO}"><button class="btn btn-primary board_btn"type="button">수정</button></a></td>
			<td><a href ="admin?page=board_delete&MP_NO=${m.MP_NO}" class = "delete_m_a "><button class="btn btn-danger yy_m_delete board_btn"type="button">삭제</button></a></td>
		</tr>
		</c:forEach>
	</tbody>
	
</table>

<!-- 페이지 네이션********** -->
<c:if test="${listcount!=0}">
<div class="center-block">
         	<div class="row">
				<div class="col">
	        		 <ul class="pagination">
	        			 <!-- 현재 페이지가 시작 페이지보다 크다면 -->
	        			 <c:if test="${pagenum>startpage}">
	         					 <li class="page-item">
	        						 <a class="page-link" href="admin?page=boardlist&pagenum=${pagenum-1}">&lt;&nbsp;</a>
	        					 </li>
	       				  </c:if>
	       				  <!-- 현재페이지가 시작 페이지와 같다면 -->
	        			 <c:if test="${pagenum==startpage}">
	      					   <li class="page-item">
	        						 <a class="page-link" href="">&lt;&nbsp;</a>
	        				 </li>
	        			 </c:if>
	        			<!-- 페이지 123- - -이 startpage 부터 maxpage까지 나옴 -->
        				 <c:forEach var="c" begin="${startpage}" end="${maxpage}">
        				 	<!-- c값이 현재 페이지와 같으면 앵커태그 변화 없음 -->
         					<c:if test="${c==pagenum}">
         						<li class="page-item">
         							<a class="page-link current" href="">${c}&nbsp;</a>
         						</li>
         					</c:if>
         					<!-- c의 값이 현재페이지와 다르면 c값을 넘겨줌 -->
         					<c:if test="${c!=pagenum}">
	        				    <li class="page-item" style="background:white;">
	        						 <a class="page-link" href="admin?page=boardlist&pagenum=${c}"style="background-color:white!important;">${c}</a>
	        					</li>
         					</c:if>
   						 </c:forEach>
    					<!-- 현재페이지보다 맥스 페이지가 작다면 그값을 넘겨주는 앵커태그 -->
    					 <c:if test="${pagenum<maxpage}">
	     				    <li class="page-item">
	     				   		 <a class="page-link" href="admin?page=boardlist&pagenum=${pagenum+1}">&gt;&nbsp;</a>
	    				     </li>
	       				 </c:if>
	       				 <!-- 현재페이지와  맥스 페이지가 같다면 주소변화 없음-->
	  					 <c:if test="${pagenum==maxpage}">
	       					  <li class="page-item">
	        					 <a class="page-link" href="">&gt;&nbsp;</a>
	        				 </li>
	        			 </c:if>       
	         
   					 </ul>
    			</div>
		</div>
</div>
</c:if>
</div>
</div>