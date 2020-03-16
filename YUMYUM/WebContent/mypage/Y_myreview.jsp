<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>내가 쓴 글 보기</title>
<script>
	$(document).ready(function(){
		$.ajax({
			type:"POST",
			url:"myReview",
			dataType:"json",
			success:function(data){
				console.log(data);
				if(data.length==0){
					out= "<div id='yy_reviewdivdiv'>내가 작성한 리뷰가 없습니다.</div>";
					$("#yy_reviewdiv").append(out);
					$("#yy_reviewdiv").css("color","grey")
					   				  .css("font-weight","bold");
					
				}else{
					 btn_cnt = data.length/5;
					 var pagenum = <%=request.getParameter("pagenum")%>;
					 console.log("현재 페이지는 "+pagenum);
					 if(pagenum == 1 || pagenum == null ||pagenum == ""){
					 	startnum = 1;
						endnum = 5; 
					 }else{
						startnum = (5*(pagenum-1))+1;
						endnum = 5*pagenum;
					 }
					 out="";
					 $(data).each(function(index, item){
						if(index >=startnum-1 && index <= endnum-1){
							out+= "<div id=yy_myreivewdiv>";
							if(item.RE_IMG_URL!=null){
								out+= "<div id='yy_reviewimgdiv'><img id='yy_myreviewimg' src='../img/"+item.RE_IMG_URL +"'></div>";
							}else{
								out+= "<div id='yy_reviewimgdiv'><img id='yy_myreviewimg' src='../img/noimage.gif'></div>";
							}
							out+="<span id='yy_reviewspan'><br><a href ='../M_category/place.move?mp_no="+item.RE_REF_NO+"'><span id='yy_ref_name'>"+item.RE_REF_NAME+"</span></a>";
							out+="<span id='yy_reviewstar'>"
							cnt=5-item.RE_GRADE;
							for(var i=0; i<item.RE_GRADE;i++){
								out+="<img src='../img/orange_star.png'>";
							}
							for(var i=0; i<cnt;i++){
								out+="<img src='../img/grey_star.png'>";
							}
							out+="</span>";
							out+="<br><br>"+item.RE_CONTENT+"</span>";
							
							a="../review/Y_Review_Delete?re_no="; 
							a += item.RE_NO; 
							a += "&mp_no="; 
							a += item.RE_REF_NO;
							
							out+="<button id='yy_reviewdeletebtn' onClick=Delete('"+a+"')>삭제</button>";//a 안의 값은 변수가 아닌 문자열 이므로 ''싱글코트 넣어준다.
							
							b = "../review/Y_Review_Update?re_no=";
							b += item.RE_NO;
							b += "&mp_no="; 
							b += item.RE_REF_NO;
							
							out+="<button id='yy_reviewmodifybtn' onClick=Update('"+b+"')>수정</button>";
							out+="</div>";
						}
					 })//each
					 if((data.length%5) != 0){
						 btn_cnt ++;
					 }
					 console.log(btn_cnt);
					 out += "<div id='yy_pagenumdiv'>"
					 for(var i=1; i<=btn_cnt;i++){
						 out+="<a href='index?page=Y_myreview&pagenum="+i+"' id ='yy_myreivewpagenum'>"+i+"</a>";
					 }
					 out+="</div>";
					 $('#yy_reviewdiv').append(out);
				}
				
			},
			error:function(){
				alert('내 리뷰를 불러오는데 에러가 발생하였습니다.');
			}
		})//ajax end
		
	})
	function Delete(a){
		var ans = confirm('정말로 지우시겠습니까?');
		if(ans){
			location.href = a;
		}
	}
	function Update(b){
		var ans = confirm('해당 리뷰를 수정하시겠습니까?');
		if(ans){
			location.href=b;
		}
	}
</script>
</head>
<body>
	<div id = "yy_reviewdiv">
	<div id="yy_title">내가 쓴 리뷰</div>	
	</div>
</body>
</html>