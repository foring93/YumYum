<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<link rel = "stylesheet" type = "text/css" href = "../css/Y_admin.css">
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
$(function(){
	if($("#m_select")!=""){
	$("#m_select").change(function(){
		var jumju=$("#m_select").val();
		console.log(jumju);
		var data='page=memberlist&state=ajax&value='+jumju;
		$.ajax({
			url:"../Main/admin",
			type:"get",
			data:data,
			dataType :"json",
			success:function(data){
				 $("tbody").empty();
				var out="";
				$(data.memberlist).each(function(index, item) {
				out+="<tr>";
				out+="<td>";
				if(item.USER_IS_OWNER==0){
				out+="<a href='admin?page=Member_detail&USER_ID="+item.USER_ID+ "' title='상세보기'>"+item.USER_ID+"</a></td>";
				}else if(item.USER_IS_OWNER==1){
				out+="<a href='admin?page=Member_detail&USER_ID="+item.USER_ID+ "' title='상세보기'>"+item.USER_ID+"</a>";
				out+="<div style='background:#e9ecef;'><span style='font-size:8pt'>점주</span></div></td>";	
				}
				out+="<td>"+item.USER_NAME+"</td>";
				out+="<td>"+item.USER_NICKNAME+"</td>";
				out+="<td>"+item.USER_REGIDATE+"</td>";
				out+="<td><a href='admin?page=Member_delete&USER_ID="+item.USER_ID+"'><button class='btn btn-danger user_delete' type='button'>삭제</button></a></td>";
				out+="</tr>";
				});
				$("tbody").append(out);  
				console.log("성공");
				},
			error : function(request,status,error){
	            console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

	         }
		});//ajax끝
	
	})
	}//if 끝
	
	$(document).on('click','.user_delete',function(){
		var input = confirm('회원정보를 삭제하시겠습니까?');
		if(input==false){
			alert("삭제 취소");
			$(".delete_a").prop('href', 'admin?page=memberlist');
		}else{
			//삭제 성공 시 본래 a태그의 href 로 이동 
			alert("삭제 성공");
		}
	})
	
})//ready end
</script>
<style>
</style>
<div class="b_div">
	<div>
		<h1 class="b_h1" style="display:inline">회원관리 </h1>
		<div style="background:blue; display:inline;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
		<span style="font-weight: bolder;color:gray">상세보기</span>
		<select id="m_select" name="memberkind" style="float:right;margin: 10px;">
			 <option value="">선택</option>
   			 <option value="1">점주</option>
   			 <option value="0">일반</option>
		</select>
		<hr>
			<table class="table-form">
				<thead>
					<tr>
						<th>아이디</th>
						<th>이름</th>
						<th>닉네임</th>
						<th>회원가입일</th>
						<th>계정삭제</th>
					</tr>
				</thead>
			<tbody>
		<c:forEach var="m" items="${memberlist}">
		<tr>
		<c:if test="${m.USER_IS_OWNER==1}">
			<td id="jumju">
				<img src="../img/jumju.png"width=20px>
				<a href="admin?page=Member_detail&USER_ID=${m.USER_ID}" title="상세보기">
					${m.USER_ID}
				</a>
				
			</c:if>
			<c:if test="${m.USER_IS_OWNER==0}">
			<td><a href="admin?page=Member_detail&USER_ID=${m.USER_ID}" title="상세보기">${m.USER_ID}</a></td>
			</c:if>
			<!--점주인지 아닌지 판단 -->
			<td>${m.USER_NAME}</td>
			<td>${m.USER_NICKNAME}</td>
			<td>${m.USER_REGIDATE}</td>
			<c:if test="${user_is_admin==1}">
			<td><a class = "delete_a" href ="admin?page=Member_delete&USER_ID=${m.USER_ID}" >
					<button class="btn btn-danger user_delete"type="button">삭제</button>
				</a>
			</td>
			</c:if>
			<c:if test="${user_is_admin==0}">
			<td></td>
			</c:if>
		</tr>
		</c:forEach>
	</tbody>
	
</table>
</div>
</div>