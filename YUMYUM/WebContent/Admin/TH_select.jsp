<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %> 
<link rel = "stylesheet" type = "text/css" href = "../css/Y_admin.css">
<link rel = "stylesheet" type = "text/css" href = "../css/font.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="../js/de_them_select.js"></script>
<style>
.table-form td{border: 0px;}
.table-form{border-collapse: separate;}
.table-form img {position: relative; z-index: -1;display: inline ;width:410px}
td:hover{opacity: 0.7; cursor:pointer;}
.TH_p{display:block;z-index: 2;font-family:Y_font4;color:blue}
img{width:422px!important;height:300px!important;}
</style>
<!-- 메세지가 있을 경우만 -->
<h1 class = 'b_h1'>현재 등록된 테마</h1><p>■ 테마를 수정하시려면 이미지를 클릭해주센요.</p>
<table class='table-form'>
	<tr>
	<c:if test="${THlistfive[0]!=null}">
		<td class="td1">
			<img src="../img/${THlistfive[0].TH_IMG_URL}" ><p class="TH_p">#${THlistfive[0].TH_TITLE}</p>
		</td>
	</c:if>	
	<c:if test="${THlistfive[0]==null}">
		<td class="td1"><img src="../img/noimage.gif" width=100% ></td>
	</c:if>	
	<c:if test="${THlistfive[1]!=null}">
		<td class="td2">
		<img src="../img/${THlistfive[1].TH_IMG_URL}">
		<p class="TH_p">#${THlistfive[1].TH_TITLE}</p>
		<input type="hidden" value="${THlistfiive[1].HASH}" id="#TH2_HASH">
		</td>
	</c:if>	
	<c:if test="${THlistfive[1]==null}">
		<td class="td2"><img src="../img/noimage.gif" width=100% style=""></td>
	</c:if>	
	</tr>
	<tr>
		<c:if test="${THlistfive[2]!=null}">
		<td class="td3"><img src="../img/${THlistfive[2].TH_IMG_URL}"><p class="TH_p">#${THlistfive[2].TH_TITLE}</p></td>
	</c:if>	
	<c:if test="${THlistfive[2]==null}">
		<td class="td3"><img src="../img/noimage.gif" width=100% style=""></td>
	</c:if>
	<c:if test="${THlistfive[3]!=null}">
		<td class="td4"><img src="../img/${THlistfive[3].TH_IMG_URL}"><p class="TH_p">#${THlistfive[3].TH_TITLE}</p></td>
	</c:if>	
	<c:if test="${THlistfive[3]==null}">
		<td class="td4"><img src="../img/noimage.gif" width=100% style=""></td>
	</c:if>
	</tr>
	<tr>
		<c:if test="${THlistfive[4]!=null}">
		<td class="td5"><img src="../img/${THlistfive[4].TH_IMG_URL}"><p class="TH_p">#${THlistfive[4].TH_TITLE}</p></td>
	</c:if>	
	<c:if test="${THlistfive[4]==null}">
		<td class="td5"><img src="../img/noimage.gif" width=100% style=""></td>
	</c:if>
		<c:if test="${THlistfive[5]!=null}">
		<td class="td6"><img src="../img/${THlistfive[5].TH_IMG_URL}"><p class="TH_p">#${THlistfive[5].TH_TITLE}</p></td>
	</c:if>	
	<c:if test="${THlistfive[5]==null}">
		<td class="td6"><img src="../img/noimage.gif" width=100% style=""></td>
	</c:if>
	</tr>
</table>
<c:if test="${THlistfive[0]!=null}">
	<c:forEach var="i" begin="0" end="5">
	<input type="hidden" id="TH_${i}"value="${i}">
</c:forEach>
</c:if>
<c:if test="${THlistfive[0]==null}">
	<c:forEach var="i" begin="0" end="5">
	<input type="hidden" id="TH_${i}"value="${i}">
</c:forEach>

</c:if>

