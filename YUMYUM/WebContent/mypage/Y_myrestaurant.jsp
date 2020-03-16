<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 등록한 맛집</title>
<script>
	$(document).ready(function(){
		function myRestaurantList(){
			$.ajax({//MP_TBL에 있는것만 보여줌 pagination 있음
				type:"POST",
				dataType:"json",
				url:"myrestaurantlist",
				success:function(data){
					$("#yy_myrestaurantselect").prepend("<div id='yy_title'>찜목록</div>");
					out = "";
					if(data.length == 0){
						out2="내가 등록한 맛집이 없습니다.";
						$("#message").text(out2);
						$("#yy_myrestaurant").css("color","grey")
											 .css("font-weight","bold")
											 .css("text-align","center");
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
						$(data).each(function(index, item){
							 if(index >=startnum-1 && index <= endnum-1){
								out += "<div id = 'yy_dibsdivdiv'><a href='../M_category/place.move?mp_no="+item.MP_NO
								    +"'><div><img src = '../img/"+item.MP_IMG_URL +"' id='yy_dibsimg'></div>";//img폴더에 있는 이미지 가져오기 
							    out += "<div id='yy_dibstext'><div><span id='yy_MP_Name'>"+item.MP_NAME + "</span><span id='yy_starspan'><img src='../img/yellowstar.png' id='yy_star'>"+item.MP_AVG_GRADE+"</span></div>";//맛집 이름
							    out += "<div>"+item.MP_ADDRESS + "</div><br>";
							    out += "<div>"+item.MP_PHONE + "</div></div><a></div>";
							 }
						})//each end
						if((data.length%5) != 0){
							 btn_cnt ++;
						 }
						 out += "<div id='yy_pagenumdiv'>"
						 for(var i=1; i<=btn_cnt;i++){
							 out+="<a href='index?page=Y_myrestaurant&pagenum="+i+"&option="+op_value+"' id ='yy_dibspagenum'>"+i+"</a>";
						 }
						 out+="</div>";
						 $("#message").text("");
					}
					$("#yy_myrestaurant").append(out);
				}
			});//ajax end
		}
		op_value="MP_REGIDATE DESC";//처음에는 최신순으로 보여줌
		myRestaurantList();
		
		$("#yy_myrestaurantselect").on("change",function(){
			$('#yy_myrestaurant').find('div').remove();
			op_value = $(this).val();
			console.log(op_value);
			myRestaurantList();
		})
	})
</script>
</head>
<body>
	<div id ="yy_myrestaurant">
		<span id = "yy_myselectdiv" style="
    text-align: right;
    display: block;
">		
		<span id="yy_titlespan">내가 등록한 맛집</span>
		<select id="yy_myrestaurantselect">
			<option value="MP_REGIDATE DESC">최신순</option>
			<option value="MP_VIEW_CNT DESC">조회수 높은순 </option>
			<option value="MP_NAME ASC">이름 오름차순</option> 
			<option value="MP_NAME DESC">이름 내림차순</option>
			<option value="MP_AVG_GRADE DESC">평점 높은 순</option>
			<option value="MP_RE_CNT DESC">리뷰 많은 순</option>
		</select>
		</span>
		<span id="message"></span>
	</div>
</body>
</html>