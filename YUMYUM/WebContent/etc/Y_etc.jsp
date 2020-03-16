<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Etc</title>
<style>
	.breadcrumb{
	margin-top: 20px;
	}
	.row{min-height:900px}
	#yy_h3 {
    text-align:center;
    color: grey;
    margin-top: 27px;
}
#yy_tbl{
	margin-left: 137px;
}
footer hr{margin-top: 7rem;}
</style>
</head>
<body>
<% 
String pagefile= request.getParameter("page");
if(pagefile==null){
	pagefile="Y_howtouse";
}
session.setAttribute("pagemove", request.getParameter("pagemove"));
%>
<header><jsp:include page="../Main/Y_header.jsp"/> </header>
  <!-- Page Content -->
  <div class="container">

    <ol class="breadcrumb">
      <li class="breadcrumb-item">
        <a href="../Main/index">Home</a>
      </li>
      <li class="breadcrumb-item active">더보기</li>
    </ol>

    <!-- Content Row -->
    <div class="row">
      <!-- Sidebar Column -->
      <div class="col-lg-3 mb-4">
        <div class="list-group">
          <a href="../Main/index" class="list-group-item">Home</a>
          <a href="index?page=Y_howtouse" class="list-group-item">이용방법</a>
          <a href="index?page=Y_inquery" class="list-group-item">문의사항</a>
          <a href="../board/BoardMain" class="list-group-item">공지사항</a>
          <a href="index?page=Y_newregister" class="list-group-item">새로운 맛집 등록 요청</a>
          <a href="index?page=Y_suggestion" class="list-group-item">기술제안</a>
        </div>
      </div>
      <!-- Content Column -->
      <div class="col-lg-9 mb-4">
       <jsp:include page='<%=pagefile+".jsp" %>'/>
      </div>
    </div>
    <!-- /.row -->

  </div>
  <!-- /.container -->
	<footer><jsp:include page="../Main/Y_footer.jsp"></jsp:include></footer>
</body>
</html>