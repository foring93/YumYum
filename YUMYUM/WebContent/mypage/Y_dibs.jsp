<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>찜목록</title>
<script type="text/javascript">
	$(document).ready(function(){
		 $.ajax({
			 data: {"id":"${id}"},
			 url:"../mypage/dibslist",
			 dataType:"json",
			 type:"POST",
			 success:function(data){
				 if(data.length==0){
					out="찜한 맛집이 없습니다.";
					$('#yy_dibsdiv').append(out);
					$("#yy_dibsdiv").css("color","grey")
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
					 $(data).each(function(index, item){//index는 0부터 시작 하므로 startnum 과 endnum에  -1을 해준다.
						 if(index >=startnum-1 && index <= endnum-1){
							 out += "<div id = 'yy_dibsdivdiv'><a href='../M_category/place.move?mp_no="+item.MP_NO
								 +"'><div><img src = '../img/"+item.MP_IMG_URL +"' id='yy_dibsimg'></div>";//img폴더에 있는 이미지 가져오기 
							 out += "<div id='yy_dibstext'><div><span id='yy_MP_Name'>"+item.MP_NAME + "</span><span id='yy_starspan'><img src='../img/yellowstar.png' id='yy_star'>"+item.MP_AVG_GRADE+"</span></div>";//맛집 이름
							 out += "<div>"+item.MP_ADDRESS + "</div><br>";
							 out += "<div>"+item.MP_PHONE + "</div></div><a></div>";
						 }
					 })//each
					 if((data.length%5) != 0){
						 btn_cnt ++;
					 }
					 out += "<div id='yy_pagenumdiv'>"
					 for(var i=1; i<=btn_cnt;i++){
						 out+="<a href='index?page=Y_dibs&pagenum="+i+"' id ='yy_dibspagenum'>"+i+"</a>";
					 }
					 out+="</div>";
					 $('#yy_dibsdiv').append(out);
				 }
			 },
			 error:function(){
				alert('찜목록을 불러오는 중 오류가 났습니다.')				 
			 }
		 });//ajax end
	})//ready end
</script>
</head>
<body>
	<div id = "yy_dibsdiv">
	<div id="yy_title">찜목록</div>
		
	</div>
</body>
</html>