<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>        
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>YUMYUM::관리자 페이지</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="../js/boardlist.js"></script>
<link href="../css/join.css" rel="stylesheet" type="text/css">
	<link href="../css/owner.css" rel="stylesheet" type="text/css">
	<link href="../css/owner_imgs.css" rel="stylesheet" type="text/css">
	<script src="../js/address.js"></script>
	<script src="../js/m_join.js"></script>
	<script src="../js/admin.js"></script>
	<script src= "../js/de_theme.js"></script>
 <jsp:include page="../Main/Y_header.jsp"/>

</head>
<style>
.mpacount{background: red;width:10px;display: inline-block;color:white;text-align: center;font-size:6pt;position:relative;top:-10px;left:-3px;}
.new{position: relative; top:-3px;}
.active{background:#e9ecef;
		}
</style>
<body>
<input type="hidden" class="nowpage" value="${page}">
 

  <!-- Page Content -->
  <div class="container">

    <!-- Page Heading/Breadcrumbs -->
   <br>
<div class="de_row">
    <ol class="breadcrumb">
      <li class="breadcrumb-item">
        관리자페이지
      </li>
      <c:if test="${page==null}">
      <li class="breadcrumb-item active">전체식당관리</li>
      </c:if>
      <c:if test="${page!=null}">
      	<c:if test="${page=='registerlist'}">
     		 <li class="breadcrumb-item active">승인요청관리</li>
     	 </c:if>
     	 <c:if test="${page=='boardlist'}">
     		 <li class="breadcrumb-item active">전체식당관리</li>
     	 </c:if>
     	  <c:if test="${page=='Mpa_detail'}">
     		 <li class="breadcrumb-item active">승인요청관리> 상세정보</li>
     	 </c:if>
     	  <c:if test="${page=='memberlist'}">
     		 <li class="breadcrumb-item active">전체회원관리</li>
     	 </c:if>
     	 <c:if test="${page=='theme'}">
     		 <li class="breadcrumb-item active">테마관리</li>
     	 </c:if>
     	<c:if test="${page=='Y_board_m_info'}"> 
     	<li class="breadcrumb-item active">전체식당관리 > 수정</li>
     	</c:if>
     	<c:if test="${page=='reviewlist'}"> 
     	<li class="breadcrumb-item active">리뷰관리</li>
     	</c:if>
     	 
      </c:if>
    </ol>
	
    <!-- Content Row -->
    <div class="row">
      <!-- Sidebar Column -->
      <div class="col-lg-3 mb-4">
        <div class="list-group">
          <a href="admin?page=boardlist" class="list-group-item" style="color:gray">전체식당관리</a>
          <a href="admin?page=memberlist" class="list-group-item"style="color:gray">전체회원관리</a>
          <a href="admin?page=registerlist" class="list-group-item"style="color:gray">식당승인요청관리
         	 <c:if test="${MPA_count!=0}">
         	 	<!-- <img src= "../img/new.png" class="new"style="width:22px;"> -->
          		<div class= "mpacount">${MPA_count}</div>
         	 </c:if>	
          </a>
          <a href="admin?page=reviewlist" class="list-group-item"style="color:gray">리뷰관리</a>
          <a href="admin?page=theme" class="list-group-item"style="color:gray">테마관리</a>
          <a href="../Main/WithdrawUser?page=withdraw_user" class="list-group-item"style="color:gray">탈퇴회원</a>
          <c:if test="${page=='TH_select'}">
          <div id="TH_iframe">	
          	<iframe src="../Main/TH_iframe" width="260" height="300"  frameborder="1" scrolling="yes"></iframe>
          </div>	
          </c:if>
         
        </div>
      </div>
      <!-- Content Column -->
      <div >
        <c:if test="${page!=null}">
	<jsp:include page="${page}.jsp"></jsp:include>
	</c:if>
	<c:if test="${page==null}">
	<jsp:include page="boardlist.jsp"></jsp:include>
	</c:if>
      </div>
    </div>
    <!-- /.row -->
  </div>
  </div>
  <!-- /.container -->

  <!-- Footer -->
  <footer>
 <jsp:include page="../Main/Y_footer.jsp"/>
</footer>
</body>

</html>
