<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<title>Mypage</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link href="../css/modern-business.css" rel="stylesheet">
<style>
  	.yy_profileimg{width:60px;height:60px;}
  	.list-group a{color:grey}
  	#yy_star {
	width: 20px;
	height: 20px
}
#yy_dibsdiv {
	width: 99%;
	float: left;
    text-align: center;
}

#yy_dibsdivdiv {
	width: 100%;
	height: 175px;
	margin-top: 0;
	margin-bottom: 0;
	padding-top: 4px;
	border: 1px solid #dcdcdc;
}

#yy_dibsdivdiv:hover {
	background: #f87a041f
}

#yy_dibstext {
	text-align: left;
	width: 80%;
	margin: 10px 30px;
	color:grey;
	text-decoration: none;
}
.yy_dibsspan {
	color: #3F3D3D;
	font-weight: bold;
	margin-left: 39px;
	font-size: 20px
}
.mypageinclude {
	width: 847px;
	min-height: 850px;
	max-height: 1000px;
	float: left;
	margin: 0;
	box-sizing: border-box;
}
.breadcrumb{margin-top: 20px;}
#yy_dibsimg{    
	width: 200px;
    height: 150px;
    float: left;
    margin-left: 20px;
    margin-right: 100px;}
#yy_starspan{float: right;}
#yy_reviewdiv{width:99%;}
#yy_myreivewdiv{border: 1px solid #dcdcdc;height: 175px;}
#yy_myreviewimg{width: 200px;
    height: 150px;margin-left: 20px;
    margin-right: 100px;margin-top:10px}
#yy_reviewimgdiv{float:left;}   
#yy_reviewmodifybtn{float:right;margin-right: 10px;margin-top: 60px;    background: #77675f;
    border: 0;
    color: white;
    width: 60px;
    height: 30px;}
#yy_reviewdeletebtn{margin-top: 60px;float:right;margin-right: 10px;    background: #77675f;
    border: 0;
    color: white;
    width: 60px;
    height: 30px;}
#yy_ref_name{color:#77675f;    font-weight: 600;
    font-size: 23px;} 
#yy_reviewspan{padding-top: 20px}    
#yy_reviewstar{float:right;    margin-right: 20px;}
#yy_dibspagenum{margin-left: 5px; margin-right: 5px;}
#yy_myreivewpagenum{margin-left: 5px; margin-right: 5px;}
#yy_pagenumdiv{text-align: center;margin-top: 10px}
#yy_MP_Name{
	color: #77675f;
    font-weight: 600;
    font-size: 23px;
}
#yy_reviewdivdiv{text-align: center;}
#yy_withdrawdiv{padding-left: 100px}
#yy_withdrawbtn{width: 492px;height: 55px;margin-top: 10px;background: #ad968a;color: white;border: 0;}
#yy_myrestaurant{width: 99%;}
#yy_myselectdiv{margin-bottom: 30px}
.list-group a:hover{text-decoration:none!important;}
#yy_reviewcontent{width: 500px; word-break: break-word;}
#yy_title{font-size: 28px;color: #cecece;text-align: left;padding-left: 21px;font-weight: 600;margin-bottom: 10px}
#yy_titlespan{font-size: 28px;color: #cecece;padding-left: 21px;font-weight: 600;float:left}
#yy_h3{font-size: 28px;color: #cecece;font-weight: 600;}
footer hr{    margin-top: 10rem;}
  </style>
</head>
<body>
<header><jsp:include page="../Main/Y_header.jsp"/></header>
<script>
	
	$(document).ready(function(){
		$.ajax({
			type:"POST",
			url:"Y_myinfo.co",
			dataType:'json',
			success:function(data){
				$("#id").val(data.id);
				$("#pass").val(data.pass);
				$("#name").val(data.name);
				$("#gender").val(data.gender);
				$("#nickname").val(data.nickname);
				$("#birthday").val(data.birthday);
				$("#address").val(data.address);
				$("#post1").val(data.postcode);
				$("#phone").val(data.phone);
				$("#email").val(data.email);
			},
			error : function(request, status, error) {
	            console.log("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
	         }
		});
		$(".yy_btn").click(function(){
			location.href = "ModifyMyInfo";//내 정보 보기에서 수정을 누르면 수정 페이지로 넘어감 
		})
		
		$('.list-group a').hover(function(){
			$(this).css('background','#a98f816b').css('color','white').css('padding-left','30px').css('transition','0.3s');
		})
		$('.list-group a').mouseleave(function(){
			$(this).css('background','white').css('color','gray').css('padding-left','22px').css('transition','0.3s');
		})
	})
</script>
<%
	if(session.getAttribute("id")==null || session.getAttribute("id").equals("")){
		response.sendRedirect("../Main/index");
	}
	String p = request.getParameter("page");
	request.setAttribute("page", p);
%>

	<div class="out container">
		<div class="yy_1000">
			<div class="yy_350">
	<ol class="breadcrumb">
      <li class="breadcrumb-item">
        <a href="../Main/index">Home</a>
      </li>
      <li class="breadcrumb-item active">mypage</li>
    </ol>

    <!-- Content Row -->
    <div class="row">
      <!-- Sidebar Column -->
      <div class="col-lg-3 mb-4">
        <div class="list-group">
          <a class="list-group-item"><span class="yy_idspan"><img alt="이미지" src="../img/blank_profile.jpg" class="yy_profileimg">${id} 님</span></a>
          <a href="index?page=Y_myinfo" class="list-group-item">내 정보 수정</a>
          <a class="list-group-item">내 포인트 : ${point} P</a>
          <c:if test="${user_is_owner ==1}">
	          <a href = "index?page=Y_myrestaurant"class="list-group-item">내가 등록한 맛집</a>
          </c:if>
          <a href="index?page=Y_dibs" class="list-group-item">찜목록 보기</a>
          <a href="index?page=Y_myreview" class="list-group-item">내 리뷰 보기</a>
          <a href="index?page=Y_membership_withdrawal" class="list-group-item">회원탈퇴</a>
        </div>
      </div>
			<div class="mypageinclude">
				<c:choose>
					<c:when test="${page==null }">
						<jsp:include page="Y_myinfo.jsp"/>
					</c:when>
					<c:otherwise>
						<jsp:include page="${page}.jsp"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<footer><jsp:include page="../Main/Y_footer.jsp"/></footer>
	
</body>
</html>