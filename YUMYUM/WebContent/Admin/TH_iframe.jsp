<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>   
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" type = "text/css" href = "../css/Y_admin.css">
<link rel = "stylesheet" type = "text/css" href = "../css/font.css">
<style>
 span{font-weight:bold;font-family: Y_font3}
 p{margin:0px;display:inline-block;font-size:8pt;float:right}
 .THlist td{text-align:left;height:25px;border-right:1px solid lightgray}
 .THlist tr td:nth-child(2){width:60%}
 .THlist th{border-right:1px solid lightgray}
 .THlist{width:280px;}
 
</style>
</head>
<body>
<span style="margin:0px">테마목록</span>
<p>총 갯수:${THlist.size()}</p>
<input type="text" id='search_iframe'>
<img src="../img/search.png"width=15px>
<br>
<table class="table-form THlist" style="display:block;width:100%"  >
<thead>
	
	<tr>
		<th style="font-size: 8pt ;height:10px">테마이름</th>
		<th style="font-size: 8pt;height:10px">해쉬태그</th>
	</tr>
</thead>
	<tbody>
		<c:forEach var="t" items="${THlist}">
		<tr >
			<td width=90px;>${t.TH_TITLE}</td>
			<td>${t.TH_HASH}</td>
		</tr>
		</c:forEach>
	</tbody>

</table>

</body>
</html>