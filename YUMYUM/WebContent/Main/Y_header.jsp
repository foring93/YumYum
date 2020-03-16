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

  <title>YUMYUM!</title>

  <!-- Bootstrap core CSS -->
  <link href="../css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="../css/modern-business.css" rel="stylesheet">
 <!-- font css -->
 <link href="../css/font.css" rel="stylesheet" type="text/css">

</head>
<style>
.de_best{font-size:50pt}
.bg-header{background: #ad968a !important;}
#yum {font-weight:1000;font-size:20pt}
.de_best5{color:white;line-height: 140px;font-size:20pt;font-family:J_font}
.carousel-inner{text-align: center;}
.nav-link{margin-right: 15px;} 
</style>
<body>

  <!-- Navigation -->
  <nav class="navbar fixed-top navbar-expand-lg navbar-dark fixed-top bg-header" style="background:#ad968a">
    <div class="container">
      <a id="yum"class="navbar-brand" href="../Main/index">YUMYUM</a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a class="nav-link" href="../M_category/new">신규식당</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="../M_category/best">베스트맛집</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="../M_category/all">전체식당</a>
          </li>
          
          <li class="nav-item">
            <a class="nav-link" href="../review/Y_Review_Board">리뷰모아보기</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="../board/BoardMain">공지</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="../etc/index">더보기</a>
          </li>
          <c:if test="${id==null}">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownBlog" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              	함께하기
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownBlog">
              <a class="dropdown-item" href="../Join">회원가입</a>
              <a class="dropdown-item" href="../Login">로그인</a>
            </div>
          </li>
          </c:if>
          <c:if test="${id!=null}">
          	<c:if test="${user_is_admin ==0&&user_is_owner ==0}">
           <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownBlog" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              	<span style="color:white">${id}님</span>
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownBlog">
              <a class="dropdown-item" href="../mypage/index">마이페이지</a>
              <a class="dropdown-item" href="../Main/logout">로그아웃</a>
            </div>
          </li>
          </c:if>
          <c:if test="${user_is_owner ==1&&user_is_admin ==0}">
           <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownBlog" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              	<span style="color:white">${id}</span> 점주님
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownBlog">
              <a class="dropdown-item" href="../mypage/index">마이페이지</a>
              <a class="dropdown-item" href="../owner/owner">나의식당등록</a>
              <a class="dropdown-item" href="../Main/logout">로그아웃</a>
            </div>
          </li>
          </c:if>
          <c:if test="${user_is_admin ==1&&user_is_owner==0}">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownBlog" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <span style="color:white">${id}</span> 관리자님
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownBlog">
              <a class="dropdown-item" href="../Main/admin">관리자페이지</a>
              <a class="dropdown-item" href="../mypage/index">마이페이지</a>
              <a class="dropdown-item" href="../Main/logout">로그아웃</a>
            </div>
          </li>
          </c:if>
          </c:if>
        </ul>
      </div>
    </div>
  </nav>
