<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>YumYum 공지내용</title>
		<style>
			#ex_date{
				display:none;
				width: 100%;
			}
			.HH_a{color:#ad968a; font-size:30px;}
			.HH_a:hover{text-decoration:none; color:#ad968a; opacity:0.7; transition: 0.3s;}
			.write_line{margin:0 auto; height:930px; width:600px; margin-top:20px; border:1px solid silver; border-radius:30px;}
			legend{font-size:17pt; font-weight:bold; text-align:center;}
			fieldset{margin:0 auto; border:1px solid silver; border-radius:30px; padding:30px;}
			form{height:890px; width:550px; background:white; margin:0 auto; margin-top:30px;}
			.HH_label{display:inline-block; font-weight:bold; margin:17px 0 5px 0; font-size:11pt; width:80px;}
			.HH_input[type=text]{text-indent:7px; border:none;
								 border-bottom:1px solid silver; width:200px; color:gray;}
			.HH_input{outline:none;}
			.HH_input[type=button]{
					display:inline-block;
					height:32px; width:19%; 
					background:#ad968a; color:white;
					border-style:none;
					line-height:32px;
					font-size:9pt;
					cursor:pointer;
			}
			.HH_input[type=button]:hover{opacity:0.7; border-radius:15px; transition: 0.3s;}
			.HH_input:focus{background:#ad968a; border-bottom:1px solid #ad968a;opacity:0.7; transition: 1s;}
			.HH_title{width: 100%!important}
			#ex_year,#ex_month,#ex_day{height:30px; width:20%}
			#ex_select{height:30px; width:20%; background:#dddddd; outline:none; font-size:11px; border:1px solid #eaeaea; margin-left:0.5px}
			#HH_content{width:100%; margin-bottom:5px; outline:none; color:gray; text-align:center;
						padding:7px; border: 1px solid silver;}
			#HH_content:focus{background:#ad968a; border:1px solid #ad968a;opacity:0.7; transition: 1s; color:black;}
			.HH_button{font-size:10.5pt;}
			.HH_button[type='submit']{background:#ad968a; color:white; margin-top:37px;}
			.HH_button[type='reset']{}
			.HH_button{height:35px; width:49.5%; border-style:none; cursor:pointer;}
			.HH_button:hover{opacity:0.7; border-radius:15px; transition: 0.3s;}
			.HH_back{width:100%; background:#ad968a; color:white; margin-top:37px;};
			
			.HH_span{display:none; position: absolute;}
			.HH_span{color:red; font-size:11px;}
			.HH_input::placeholder{font-size:14px; color:#a2a2a2}
			
			.tx_none{text-decoration:none;}
	</style>
	</head>
	<body>
		<header>
			<jsp:include page="../Main/Y_header.jsp"/>
		</header>
		<div class="out_container">
			<div class="write_line">
				<form action="#" method="post">
					<fieldset>
						<legend class="HH_a" onclick="location.href='BoardMain'">
							공지 내용
						</legend>
						<label for="writer" class="HH_label">작성자</label>
						<input type="text" id="writer" name="writer" class="HH_input"
							   value="${board_data.board_writer}" readonly>
						<br>
						<label class="HH_label" for="category">카테고리</label>
						<input type="text" id="category" name="category" class="HH_input"
							   value="${board_data.board_category}" readonly>
						<br>
						<label class="HH_label" for="regidate">등록일</label>
						<input type="text" id="regidate" name="regidate" class="HH_input"
							   value="${board_data.board_regidate}" readonly>
						<br>
						<label class="HH_label" for="expirate">기간</label>
						<c:if test = "${board_data.board_is_all_time == 0}">
                     	<input type="text" name="expirate" id="expirate" class="HH_input" value="${board_data.board_regidate} ~ ${board_data.board_expirate}" readonly>
                        </c:if>
						<c:if test = "${board_data.board_is_all_time == 1}">
                     	<input type="text" name="expirate" id="expirate" class="HH_input" value="상시" readonly>
                  		</c:if>
						<br>
						<label for="title" class="HH_label">제목</label>
						<br>
						<input type="text" name="title" id="title" class="HH_input HH_title"
							   value="${board_data.board_title}" readonly>
						<br>
						<label for="HH_content" class="HH_label">내용</label>
						<br>
						<textarea name="content" id="HH_content" rows="15" readonly>${board_data.board_content}</textarea>
						<%
						if(session.getAttribute("user_is_admin")!= null)
						{
							if(session.getAttribute("user_is_admin").equals(1))
							{
						%>
                     		<button type = "button" class = "HH_button" onclick="editBoard()">수정</button>
                     		<button type = "button" class = "HH_button" onclick="deleteBoard()">삭제</button>
                     	<%
							}
						}
                     	%>
                     		<br>
                     		<button type = "button" class = "HH_button HH_back" onclick="location.href='BoardMain'">목록으로 돌아가기</button>
					</fieldset>
				</form>
			</div>
		</div>
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		
		<script>
			function editBoard()
			{
				location.href = 'Y_Board_Edit?board_no=' + ${board_data.board_no};
			}
			
			function deleteBoard()
			{
				var ans = confirm('정말로 공지를 지우시겠습니까?');
				if(ans)
				{
					location.href = 'Y_Board_Delete?board_no='  + ${board_data.board_no};
				}
			}
		</script>
		<footer>
			<jsp:include page="../Main/Y_footer.jsp"/>
		</footer>
	</body>
</html>