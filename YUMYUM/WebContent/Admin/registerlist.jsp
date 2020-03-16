<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<link rel = "stylesheet" type = "text/css" href = "../css/Y_admin.css">
<style>
</style>
<div class="b_div" style="text-align: center;">
	<div>
		<h1 class="b_h1">승인 요청 관리 </h1>
		<hr>
			<table class="table-form">
				<thead>
					<tr>
						<th>이름</th>
						<th>점주</th>
						<th>요청일</th>
						<th>상세보기</th>
					</tr>
				</thead>
			<tbody>
		<c:forEach var="m" items="${mpalist}">
		<tr>
			<td>${m.MP_NAME}</td>
			<td>${m.MP_WRITER}</td>
			<td>${m.MP_REGIDATE}</td>
			<td><a href="admin?page=Mpa_detail&MP_NO=${m.MP_NO}"><button class="btn" style="background:#ad968a;font-weight: bolder;font-size: 10pt"type="button">상세보기</button></a></td>
			
		</tr>
		</c:forEach>
	</tbody>
	
</table>
</div>
</div>