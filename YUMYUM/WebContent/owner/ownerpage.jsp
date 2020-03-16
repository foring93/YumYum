<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<!DOCTYPE html>
<html>
<head>
	<title>점주 페이지</title>
	<link href="../css/join.css" rel="stylesheet" type="text/css">
	<link href="../css/owner.css" rel="stylesheet" type="text/css">
	<link href="../css/owner_imgs.css" rel="stylesheet" type="text/css">
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="../js/address.js"></script>
	<script src="../js/m_join.js"></script>
	<script>
	$(function(){
		if('${page}'=='registrationlist'){
			$('.ad_content').css("background",'#494644');
		}
		
		$('.page_title.1').click(function(){
			$(this).addClass("page_title_sel");
			$('.page_title.2').removeClass("page_title_sel");
		})
		
		$('.page_title.2').click(function(){
			$(this).addClass("page_title_sel");
			$('.page_title.1').removeClass("page_title_sel");
		})
	})
	</script>
</head>
<body>
	<header><jsp:include page="/Main/Y_header.jsp"/></header>
	<div class = "container">
		<div class = "owner_menu">
			<div class = "menu_section">
				<ul class = "list-unstyled">
					<li>
						<div class = "owner_page">
							<a href = "owner?page=registrationForm">
								<div class = "page_title">맛집등록</div>
							</a>
						</div>
					</li>
					<li>
						<div class = "owner_page">
							<a href="owner?page=registrationlist">
								<div class = "page_title">요청현황</div>
							</a>
						</div>
					</li>
				</ul>
			</div>
			<div class = "owner_content">
			<c:if test="${page!=null}">
				<jsp:include page="${page}.jsp"></jsp:include>
			</c:if>
			<c:if test="${page==null}">
				<jsp:include page="registrationForm.jsp"></jsp:include>
			</c:if>
			</div>
		</div>
	</div><!-- container 끝 -->
	<footer><jsp:include page="../Main/Y_footer.jsp"/></footer>
</body>
</html>