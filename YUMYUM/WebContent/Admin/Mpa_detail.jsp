<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<link rel = "stylesheet" type = "text/css" href = "../css/Y_admin.css">
<script src="../js/Mpa_detail.js"></script>

<style>

.font{font-size:30pt;display:inline;}
button:hover{opacity:0.8}

</style>
<script>
	$(function(){
		var output ="";
		var addImg ="${mpa_detail.MP_ADD_IMG_URL}"
		var addImgSplit = addImg.split("*");
		//<img src="../img/${mpa_detail.MP_ADD_IMG_URL}"style="width:300px">
		if(addImg != ""){//추가파일이 없을땐 쳐냄
			for(var i in addImgSplit){
				output += '<img src="../img/'+ addImgSplit[i] +' "style="width:150px; height:100px; margin:15px 5px;">'
			}
		}else{
			output+="<img src='../img/noimage.gif'>";
			console.log("추가이미지없음");
		}
		$('.MPA_bottom_td').append(output);
	})
</script>
<div class="b_div">
	<div>
		<h1 class="b_h1" style="text-align: center;">맛집 [<span style="color:gray">${mpa_detail.MP_NAME}</span>]의 상세페이지 </h1>
		
		<form method="post" action="admin" 
			enctype="multipart/form-data"  name="mp_approve" style="margin: 0px; ">
		<input type="hidden"name="MP_WRITER"value="${mpa_detail.MP_WRITER}">
			<table class="table-form MPA-table" style="border:0.3px solid lightgray" >
					<tr>
						<th>번호</th>
						<td width="30%" colspan="2"><input type="hidden"name="MP_NO"value="${mpa_detail.MP_NO}">${mpa_detail.MP_NO}</td>
						<th width="30%">신청일</th>
						<td><input type="hidden"name="MP_REGIDATE"value="${mpa_detail.MP_REGIDATE}">${mpa_detail.MP_REGIDATE}</td>
					</tr>
					<tr>
						<th>이름</th>
						<td colspan="2"><input type="hidden"name="MP_NAME"value="${mpa_detail.MP_NAME}">${mpa_detail.MP_NAME}</td>
						<th>전화번호</th>
						<td><input type="hidden"name="MP_PHONE"value="${mpa_detail.MP_PHONE}">${mpa_detail.MP_PHONE}</td>
						
					</tr>
					<tr> 
						<th>주소</th>
						<td colspan='4'><input type="hidden"name="MP_ADDRESS"value="${mpa_detail.MP_ADDRESS}">${mpa_detail.MP_ADDRESS}</td>
					</tr>
					
					<tr>
						<th>음식종류</th>
						<td colspan="2"><input type="hidden"name="MP_KIND"value="${mpa_detail.MP_KIND}">${mpa_detail.MP_KIND}</td>
						<th>오픈/클로징시간</th>
						<td><input type="hidden"name="MP_HOURS"value="${mpa_detail.MP_HOURS}">${mpa_detail.MP_HOURS}</td>
					</tr>
					<tr>
						<th rowspan="4">내용</th>
						<th width="18%"class="MPA_in">맛집 상세정보</th>
						<td colspan='3' ><input type="hidden"name="MP_INFO"value="${mpa_detail.MP_INFO}">${mpa_detail.MP_INFO}</td>
					</tr>
					
					<tr>
						<th class="MPA_center">해쉬태그</th>
						<td colspan='3'><input type="hidden"name="MP_HASH"value="${mpa_detail.MP_HASH}">${mpa_detail.MP_HASH}</td>
					</tr>
					
					<tr>
						<th class="MPA_center">썸네일</th>
						<td colspan='3'><input type="hidden"name="MP_IMG_URL"value="${mpa_detail.MP_IMG_URL}"><img src="../img/${mpa_detail.MP_IMG_URL}" style="width:300px; margin:10px;"></td>
					</tr>
					<tr>
						<th class="MPA_bottom">부가이미지</th>
						<td colspan='3' style="border-bottom: 0px" class="MPA_bottom_td">
							<input type="hidden"name="MP_ADD_IMG_URL"value="${mpa_detail.MP_ADD_IMG_URL}" >
							<%-- <img src="../img/${mpa_detail.MP_ADD_IMG_URL}"style="width:300px"> --%>
						</td>
					</tr> 
					<tr>
					<td colspan='5' style="height:0px!important"></td>
					</tr>
				</table>
				<div class="MPA_div">
					<button class="btn btn-primary"type="submit">승인하기</button>
					<%-- <a href="admin?page=reject&MPA_NO=${mpa_detail.MP_NO}"> --%>
						<button class="btn btn-danger"type="button">
						반려하기
						</button>
					<!-- </a> -->
					<input type="hidden" name="reject">
				</div>
		</form>
</div>
</div>