<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>     
<link rel = "stylesheet" type = "text/css" href = "../css/Y_admin.css">

<style>
.table-form th{ width:200px;background: white;border-right:1px solid lightgray;color:gray}
.table-form input{width:600px;margin-left:30px;margin-right:30px;}
.table-form tr:last-child>th {
	border:0px;
}

.table-form tr{border:1px solid lightgray}
.table-form{float:left;width:850px;margin-bottom:20px;}
.th_button{float:right; font-size:8pt}
.b_h1{display:inline-block;}
.th_div{width:850px;}
form{margin:0px;height:0px;}
.listcount{float:right;display:inline-block;}
.toggle-table{border:2px solid #A3D0D2}
.th_select{float:right; font-size:8pt;margin-right:5px;}
</style>

<div class='th_div'>
 	<h1 class="b_h1">테마지정페이지</h1>
 	<button type="button" class="th_button btn btn-info">테마등록</button>
 	<button type="button" class="th_select btn btn-primary">테마지정</button>
	
	<form method="post" action="theme" enctype="multipart/form-data">

		<table class="table-form toggle-table" style="display:none;">
			<tr>
				<th>테마이름</th>
				<td><input type="text" name="TH_TITLE"></td>
			</tr>
			<tr>
				<th>검색될 문자열 <br>('#'로구분)</th>
				<td><input type="text" name="TH_HASH"></td>
			</tr>
			<tr>
				<th>테마배경사진</th>
				<td><input type="file" name="TH_IMG_URL"></td>
			</tr>
			<tr>
				<th><input type="hidden" name="page" value="theme"></th>
				<td>
					<button type="reset" class="btn btn-outline-danger waves-effect"style="float:right;font-size: 8pt">취소</button>
					<button class="btn btn-outline-primary waves-effect"style="float:right;font-size: 8pt;margin-right:10px">제출</button>
				</td>
			</tr>
	</table>
</form>

<!--현재 있는 테마 모두 보여주는 테이블 -->
<p style="display:inline-block;font-weight:bold;"> ■ 테마목록 </p>
<P class= "listcount" style="font-weight:bold;"> 개수:${length}</P>
<table class="table-form THlist" style="display:block" >
	
	<thead>
		<tr>
			<th>지정</th>
		
			<th>이름</th>
		
			<th style="width:30%">저장된 해쉬코드</th>
		
			<th>테마 배경이미지</th>
			
			<th>테마 삭제</th>
			
		</tr>
		
	</thead>
	<tbody>
		<c:forEach var="t" items="${THlist}">
		<tr >
		<!-- 테마지정이되었으면체크 이미지 노출 -->
			<c:if test="${t.TH_NO<=5}">
			<td>
				<img src="../img/success3.png" width=15px style="margin-right:5px;">
				<input type="hidden" class="TH_NO" value="${t.TH_NO}">
			</td>
			</c:if>
		<!-- 테마지정안된경우-->	
			<c:if test="${t.TH_NO>5}">
			<td>
				<input type="hidden" class="TH_NO" value="${t.TH_NO}">
			</td>	
			</c:if>
			<td>${t.TH_TITLE}</td>
			<td>${t.TH_HASH}</td>
			<td>${t.TH_IMG_URL}</td> 
			<td>
				<button type="button" class="btn btn-outline-danger waves-effect TH_delete" style="font-size:8pt;">삭제</button>
			</td> 
		</tr>
		</c:forEach>
	</tbody>

</table>
</div>