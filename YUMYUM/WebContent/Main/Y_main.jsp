<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>모든 맛집을모아 YUMYUM!</title>

<style>

.de_new{width:150px}/* 새로운 맛집 이미지크기 */
.de_newtable{border-collapse: collapse;border-spacing:30px;width:1000px;}
.de_newtable tr {
	border-bottom: 1px solid black;
	height: 30px
}
.de_newtable td:nth-child(2n){width:700px}

 /* td{width:200px;border: 2px solid blue; } */
</style>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<style>
@font-face { font-family: 'GoodFont'; src: url('../font/Typo_Buamdong2019M.ttf'); }

.font{font-family:GoodFont;color:#F67A06;margin-top:20px; }
.container{width:1000px!important}
.de_table img{width:500px;height:250px;z-index:-1;position: relative;}
.de_table td{padding:0px;opacity:0.99;}
.de_table td:hover{opacity:0.5;}
.de_wrap{position: relative;}
.de_text{position: absolute;
	top: 50%;
	left: 50%;
	color:white;
	transform: translate( -50%, -50% );
	font-family: GoodFont;
	 }
.de_imgl{opacity:0.5}
#button{border-width:4px;border-color: gray; border-radius: 10px; width:120px;
		height:50px;color:black;font-size: 20pt;font-family: Font3}
</style>
</head>
<body>
<header>
<jsp:include page="../Main/Y_header.jsp"/>
</header>


<div class="container" style="width:1000px">

	<!-- <div class="out" style="text-align: right;">
		<span>검색</span>
		<input type="text" name="search" style="border:5px solid #F27004">
	</div> -->
	<div class="out"style="padding:0px">
		<jsp:include page="Y_slide.jsp"/>
		
	</div>
	<div class="out"style="height:120px;background: #494644">
		<div>
		<br>
		<h4 class="font"style="margin-top:0px">#우리지역 맛집 검색하기</h4>
		<input type="text" name="search" style="border:5px solid #F27004; width:700px;border-radius:5px">
		<input type="button" id="button"name="search" value="찾아보기" style="background:#F27004 ">
		</div>
	</div>
	<div class="out"style="height:850px;background: #494644;">
	<br>
	<h2 class='font'style="margin-top: 0px">테마별 맛집</h2>
		<table class="de_table" >
		<tr>
			<td><div class="de_wrap"><img class="de_imgl" src="../img/1.jpg" ><div class="de_text"><h1>#테마1</h1></div></div></td>
			<td><div class="de_wrap"><img class="de_imgl" src="../img/2.jpg" ><div class="de_text"><h1>#테마2</h1></div></div></td>
			
		</tr>
		<tr>
			<td><div class="de_wrap"><img class="de_imgl" src="../img/3.jpg" ><div class="de_text"><h1>#테마3</h1></div></div></td>
			<td><div class="de_wrap"><img class="de_imgl" src="../img/4.jpg" ><div class="de_text"><h1>#테마4</h1></div></div></td>
			
		</tr>
		<tr>
			<td><div class="de_wrap"><img class="de_imgl" src="../img/5.jpg" ><div class="de_text"><h1>#테마5</h1></div></div></td>
			<td><div class="de_wrap"><img class="de_imgl" src="../img/6.jpg" ><div class="de_text"><h1>#테마6</h1></div></div></td>
			
		</tr>
		
		</table>
		<a href="" style="float:right;line-height: 30px">더보러가기</a>
	</div>
	
	<div class="out"style="padding:0">
	할인 및 이벤트
	</div>

</div>

<footer>
	<jsp:include page="Y_footer.jsp"/>
</footer>

</body>
</html>