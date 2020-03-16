<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html lang="en">
<link href="../css/font.css" rel="stylesheet" type="text/css">
<link href="../css/main.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
p{font-size:30pt;font-family:Y_font3}
.carousel-item {
    height: 55vh!important;}
</style>
<script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="../js/Search_Place.js"></script>
<script src="../js/de_main.js"></script>
<script src = "../js/Y_All.js"></script>
<script>
$(function(){
	$('.active').click(function(){
		var s= $('.active input').val();
		console.log(s);
		location.href="../M_category/place.move?mp_no="+s;
		
	});
	
	$('.active').hover(function(){
		$(".active").css('opacity','0.5');
		$(this).css('cursor','pointer');
		
	},function(){
		$(".active").css('opacity','1');
		$(this).css('cursor','default');
	});
	
}) 
</script>
<head>

 <jsp:include page="../Main/Y_header.jsp"/>
</head>

<body>
  <header>
    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
      <ol class="carousel-indicators">
      	 <c:if test="${best4[0].name!=null}">
        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
        </c:if>
         <c:if test="${best4[1].name!=null}">
        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
        </c:if>
         <c:if test="${best4[2].name!=null}">
        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </c:if>
         <c:if test="${best4[3].name!=null}">
        <li data-target="#carouselExampleIndicators" data-slide-to="3"></li>
        </c:if>
         <c:if test="${best4[4].name!=null}">
        <li data-target="#carouselExampleIndicators" data-slide-to="4"></li>
        </c:if>
      </ol>
      <div class="carousel-inner" role="listbox">
    	
		<c:if test="${best4[0].name!=null}">  
		  <div class="hh" >
      <input type="text" name="search" id="search_place"class="main_search"placeholder="검색어를 입력하세요" style="z-index: 3;position: relative;">
      <button type = "button" name="search"id = "search_btn2"><img style="width:30px;" src="../img/search.png"></button>
      
      
		<h2 class="de_best de_best5 " style="z-index:2;display:inline-block;">BEST5 HOT PLACE</h2> 
		<hr class="de_hr">   
	  
	  </div>
        <!-- Slide One - Set the background image for this slide in the line below -->
        
        <div class="carousel-item active" style="background-image: url('../img/${best4[0].img}')">
        <input type="hidden" value="${best4[0].no}">
          <div class="carousel-caption d-none d-md-block">
          
            <h3 class="de_best">${best4[0].name}</h3>
            
            
            <p>#${best4[0].adress}맛집</p>
          </div>
        </div>
        <!-- Slide Two - Set the background image for this slide in the line below -->
        <c:if test="${best4[1].name!=null}">
        <div class="carousel-item" style="z-index:-1;background-image: url('../img/${best4[1].img}')">
          <input type="hidden" value="${best4[1].no}">
          <div class="carousel-caption d-none d-md-block">
            <h3 class="de_best">${best4[1].name}</h3>
            
             <p>#${best4[1].adress}맛집</p>
          </div>
        </div>
        </c:if>
         <c:if test="${best4[2].name!=null}">
        <!-- Slide Three - Set the background image for this slide in the line below -->
        <div class="carousel-item" style="z-index:-1;background-image: url('../img/${best4[2].img}')">
          <input type="hidden" value="${best4[2].no}">
          <div class="carousel-caption d-none d-md-block">
            <h3 class="de_best">${best4[2].name}</h3>
             <p>#${best4[2].adress}맛집</p>
          </div>
        </div>
        </c:if>
         <c:if test="${best4[3].name!=null}">
        <div class="carousel-item" style="z-index:-1;background-image: url('../img/${best4[3].img}')">
          <input type="hidden" value="${best4[3].no}">
          <div class="carousel-caption d-none d-md-block">
            <h3 class="de_best">${best4[3].name}</h3>
             <p>#${best4[3].adress}맛집</p>
          </div>
        </div>
        </c:if>
         <c:if test="${best4[4].name!=null}">
         <div class="carousel-item" style="z-index:-1;background-image: url('../img/${best4[4].img}')">
          <input type="hidden" value="${best4[4].no}">
          <div class="carousel-caption d-none d-md-block">
            <h3 class="de_best">${best4[4].name}</h3>
             <p>#${best4[4].adress}맛집</p>
          </div>
        </div>
        </c:if>
        </c:if>
        <c:if test="${best4[0].name==null}">
        <style>.carousel-inner{height:200px;}</style>
        <h3 style="position:relative;top:50px ">등록된 글이 없습니다.<br>맛집을 등록해주세요</h3>
        </c:if>
      </div>
      
      <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div>
  </header>
	
  <div class="container">

   <!--  <h1 class="my-4">식당 검색</h1> -->

    <!-- Marketing Icons Section -->
    <!-- 검색 -->  
    
    <hr>
  <!-- Page Content -->
    
   
    <!-- /.row -->

    <!-- Portfolio Section -->
    
    <h2 class="divd">THEME</h2>
   
    <div class="row">
	<c:if test="${TH_list[0].TH_TITLE!=null}">
    <!-- 첫번째 테마사진 -->
      <div class="t_div_l">
        <div class="jb-image">
          <img class="card-img-top img1" style="height:333px;width:540px;z-index:-1;display:inline-block"src="../img/${TH_list[0].TH_IMG_URL}" alt="">
          <input type="hidden" id="TH1_HASH" value="${TH_list[0].TH_HASH}">
                
        </div>
        <div class="jb-text">
        	<h1 class="t_h1">#${TH_list[0].TH_TITLE}</h1>
        </div>
      </div>
     </c:if>
      

     <c:if test="${TH_list[1].TH_TITLE!=null}">
      <!-- 두번째 사진 -->
     <div class="t_div_l">
        <div class="jb-image">
          <img class="card-img-top img2" style="height:333px;width:540px;z-index:-1;display:inline-block"src="../img/${TH_list[1].TH_IMG_URL}" alt="">
          <!-- 해쉬태그 히든 -->
                <input type="hidden" id="TH2_HASH" value="${TH_list[1].TH_HASH}">
        </div>
        <div class="jb-text">
        	<h1 class="t_h1">#${TH_list[1].TH_TITLE}</h1>
        </div>
      </div>
      </c:if>
      <c:if test="${TH_list[2].TH_TITLE!=null}">
      <!-- 세번째 사진 -->
      <div class="t_div_l">
        <div class="jb-image">
          <img class="card-img-top img3" style="height:333px;width:540px;z-index:-1;display:inline-block"src="../img/${TH_list[2].TH_IMG_URL}" alt="">
            <input type="hidden" id="TH3_HASH" value="${TH_list[2].TH_HASH}">    
        </div>
        <div class="jb-text">
        	<h1 class="t_h1">#${TH_list[2].TH_TITLE}</h1>
        </div>
      </div>
      </c:if>
      <c:if test="${TH_list[3].TH_TITLE!=null}">
      <!-- 네번째 -->
        <div class="t_div_l">
        <div class="jb-image">
          <img class="card-img-top img4" style="height:333px;width:540px;z-index:-1;display:inline-block"src="../img/${TH_list[3].TH_IMG_URL}" alt="">
          <input type="hidden" id="TH4_HASH" value="${TH_list[3].TH_HASH}">
                
        </div>
        <div class="jb-text">
        	<h1 class="t_h1">#${TH_list[3].TH_TITLE}</h1>
        </div>
      </div>
      </c:if>
      <c:if test="${TH_list[4].TH_TITLE!=null}">
      <!-- 다섯버째 -->
       <div class="t_div_l">
        <div class="jb-image">
          <img class="card-img-top img5" style="height:333px;width:540px;z-index:-1;display:inline-block"src="../img/${TH_list[4].TH_IMG_URL}" alt="">
          <input type="hidden" id="TH5_HASH" value="${TH_list[4].TH_HASH}">
                
        </div>
        <div class="jb-text">
        	<h1 class="t_h1">#${TH_list[4].TH_TITLE}</h1>
        </div>
      </div>
      </c:if>
      <c:if test="${TH_list[5].TH_TITLE!=null}">
      <!-- 여섯 -->
      <div class="t_div_l">
        <div class="jb-image">
          <img class="card-img-top img6" style="height:333px;width:540px;z-index:-1;display:inline-block"src="../img/${TH_list[5].TH_IMG_URL}" alt="">
          <input type="hidden" id="TH6_HASH" value="${TH_list[5].TH_HASH}">
                
        </div>
        <div class="jb-text">
        	<h1 class="t_h1">#${TH_list[5].TH_TITLE}</h1>
        </div>
        </div>
       </c:if> 
      </div>
      </div>
    <!-- /.row -->

   
  <!-- /.container -->


 
  <!-- Bootstrap core JavaScript -->
 

</body>
<jsp:include page="Y_footer.jsp"/>

</html>
