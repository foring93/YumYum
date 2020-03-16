<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel = "stylesheet" type = "text/css" href = "../css/Y_admin.css">

<style>
	.yy_detail_btn{float:right; margin-right:0px; 
					margin-top:20px;color:gray; 
					background: white;width:100px; height:30px}
	.table-form th,td{border:1px solid lightgray;height:30px!important;}
	.table-form th{background: #e9ecef;}
</style>
</head>
<body>

	
		<h1 class="b_h1">회원 상세페이지</h1>
		<P>■ ${m.USER_ID}의 회원정보입니다.</P>
			<table class="table-form">
				
				<tr>
					<th>아이디</th>
					<td colspan="3">${m.USER_ID}</td>
					
				</tr>
				
				<tr>
					<th>이름</th>
					<td>${m.USER_NAME}</td>
					<th>닉네임</th>
					<td>${m.USER_NICKNAME}</td>
				</tr>
				
				<tr>
					<th>생년월일</th>
					<td>${m.USER_BIRTHDAY}</td>
					<th>성별</th>
					<td>${m.USER_GENDER}</td>
				</tr>
				<tr>
					<th>회원유형</th>
					<c:if test="${m.USER_IS_OWNER == 0}">
						<td colspan="3">일반회원</td>	
					</c:if>
					<c:if test="${m.USER_IS_OWNER == 1}">
						<td colspan="3">점주</td>	
					</c:if>
				</tr>
				<tr>
					<th>이메일</th>
					<td colspan="3">${m.USER_EMAIL}</td>
				</tr>
				<tr>
					<th>우편번호</th>
					<td colspan="1">${m.USER_POSTCODE}</td>
					<th>주소</th>
					<td colspan="3">${m.USER_ADDRESS}</td>
				</tr>
			
				<tr>
					<th>전화번호</th>
					<td colspan="3">${m.USER_PHONE}</td>
				</tr>
				
			</table>
			<button onClick="history.back()" class="yy_detail_btn">목록</button>
		
	
	
</body>
