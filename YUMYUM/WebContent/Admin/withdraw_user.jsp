<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>탈퇴 회원</title>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<link rel = "stylesheet" type = "text/css" href = "../css/Y_admin.css">
<script>
	$(document).ready(function(){
		$.ajax({
			type:"POST",
			dataType:"json",
			url : "../Main/WithdrawUser",
			success:function(data){
				var out ="";
				if(data == null){
					out += "<tr><td>탈퇴한 유저가 존재하지 않습니다.</td></tr>";
				}else{
					console.log(data);
					out += "<thead><tr><th>아이디</th><th>탈퇴 이유</th><th>탈퇴 날짜</th></tr></thead>";
					$(data).each(function(index, item){
						out += "<tr>";
						out += "	<td>"+ item.id + "</td>";
						out += "	<td>"+ item.reason + "</td>";
						out += "	<td>"+ item.date + "</td>";
						out += "</tr>";
					})
				} 
				$("#yy_usertbl").append(out);
			},//success end
			error : function(request, status, error) {
		         console.log("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
		     }//error end
		});//ajax end
	})
	
</script>
<style>
	
</style>
</head>
<body>
	<div class="b_div" style="text-align: center;">
		<div>
			<h1 class="b_h1">탈퇴 회원 목록</h1>
			<hr>
			<table class="table-form" id ="yy_usertbl">
					
			</table>
		</div>
	</div>
</body>
</html>