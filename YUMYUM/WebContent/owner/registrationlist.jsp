<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<link rel = "stylesheet" type = "text/css" href = "../css/reg_list.css">
<script src = "../js/registrationlist.js"></script>
<div class = "b_div">
	<div>
		<div class = "approve_state_list">승인요청 현황</div>
		<button type = "button" class = "btn-cancel">요청 철회</button>
		<table class = "approve_state_tbl">
			<thead>
				<tr>
					<th>선택</th>
					<th>고유번호</th>
					<th>신청이름</th>
					<th>신청일</th>
					<th>상태</th>
					<th>수정</th>
				</tr>
		</thead>
		<tbody>
		<c:forEach var = "m" items = "${mpa_my_list}">
			<!-- 반려되지 않은 경우 (승인 대기 중/승인 완료) -->
			<c:if test = "${m.MPA_IS_APPROVED != 2}">
				<tr>
					<!-- 승인 대기 중일 때 -->
					<c:if test = "${m.MPA_IS_APPROVED != 1}">
						<!-- 요청 철회할 수 있는 체크박스 생성 -->
						<td><input class = "mpa_cancel" type = "checkbox" name = "mpa_cancel" value = "${m.MP_NO}"></td>
					</c:if>
					<!-- 승인 완료 됐을 때 -->
					<c:if test = "${m.MPA_IS_APPROVED == 1}">
						<!-- 요청 철회 불가능 -->
						<td><input type = "checkbox" disabled = disabled></td>
					</c:if>
					<td class = "MP_NO">${m.MP_NO}</td>
					<td>${m.MP_NAME}</td>
					<td>${m.MP_REGIDATE}</td>
					<!-- 등록 요청이 승인됐을 때 -->
					<c:if test = "${m.MPA_IS_APPROVED == 1}">
						<td><span style = "color : #90A8B8">승인 완료</span></td>
						<td>
							<a href = "../etc/index?page=Y_inquery" class = "ask_modify">
								<button type = "button" class = "ask_btn">문의</button>
							</a>
						</td>
					</c:if>
					<!-- 등록 요청이 대기중일 때 -->
					<c:if test = "${m.MPA_IS_APPROVED == 0}">
						<td><span style = "color : #C2AC91">승인 대기 중</span></td>
						<td>
							<a href="owner?page=Mpa_modify&MP_NO=${m.MP_NO}">
								<button type = "button" class = "reg_modify">수정</button>
							</a>
						</td>
					</c:if>
				</tr>
		</c:if>
		<!-- 반려된 경우 -->
		<c:if test = "${m.MPA_IS_APPROVED == 2}">
			<tr>
				<td><input class = "mpa_cancel" type = "checkbox" name = "mpa_cancel" value = "${m.MP_NO}"></td>
				<td>${m.MP_NO}</td>
				<td>${m.MP_NAME}</td>
				<td>${m.MP_REGIDATE}</td>
				<td><span style = "color : #CC4F43;">반려</span></td>
				<td><a href="owner?page=Mpa_modify&MP_NO=${m.MP_NO}"><button type = "button" class = "reg_modify">수정</button></a></td>
			</tr>
			<tr id = "reject_why">
				<td colspan = "7" class = "reject_reason"> 반려 사유 : ${m.MPA_REJECT_WHY} </td>
			</tr>
		</c:if>
	</c:forEach>
	</tbody>
</table>
<%-- <a href="admin?page=Mpa_cancel&MP_NO=${m.MP_NO}" class="displaynone"> --%>
<!-- </a> -->
</div>
</div>