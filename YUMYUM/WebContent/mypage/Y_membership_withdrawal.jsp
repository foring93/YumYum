<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
	function membership_withdraw(){
		if($("#yy_textarea").val()=="" || $("#yy_textarea").val()==null || $("option:eq(0)").prop("selected")==true){
			alert("회원 탈퇴 사유를 선택해주세요");
		}else{
			location.href="User_withdrawal?reason="+a;			
		}
	}
	$(document).ready(function(){
		$(".yy_withdrawreason").on("change",function(){
			a = $(this).val();
			if($(".yy_withdrawreason option:eq(5)").prop("selected")==false){
				$("#yy_textarea").empty();
				$("#yy_textarea").text(a);
				$("#yy_textarea").prop("readOnly",true);
			}else if($(".yy_withdrawreason option:eq(5)").prop("selected")==true){
				$("#yy_textarea").prop("readOnly",false)
								 .text('');
			}
		});
		
	})
	
	
</script>
</head>
<body>
	<div id = "yy_withdrawdiv">
		<div>회원 탈퇴를 하게 되면 내가 작성한 리뷰, 게시글, 찜한 목록 및<br>
		내 모든 정보가 삭제됩니다.
		</div><br>
		<div>회원 탈퇴 사유를 선택해주세요</div>
		
		<select class = "yy_withdrawreason">
			<option selected disabled>&nbsp;-- 회원 탈퇴 이유를 선택해 주세요 --</option>
			<option value="맛집 정보가 충분하지 않음">맛집 정보가 충분하지 않음</option>
			<option value="웹 페이지의 디자인이 마음에 들지 않음">웹 페이지의 디자인이 마음에 들지 않음</option>
			<option value="맛집 위치가 정확하지 않음">맛집 위치가 정확하지 않음</option>
			<option value="더 이상 이 사이트를 이용하지 않음">더 이상 이 사이트를 이용하지 않음</option>
			<option value="">직접입력</option>
		</select><br><br>
		<textarea rows="10" cols="60" id="yy_textarea"></textarea><br>
		<button onClick="membership_withdraw();" id="yy_withdrawbtn">회원 탈퇴</button>
	</div>
</body>
</html>