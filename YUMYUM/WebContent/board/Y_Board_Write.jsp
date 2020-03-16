<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("id") == null)
	{
		String msg = "<script>";
		msg += "alert('먼저 로그인 해주세요'); ";
		msg += "location.href='../Login'";
		msg += "</script>";
		out.write(msg);
	}
	else if(!session.getAttribute("user_is_admin").equals(1))
	{
		String msg = "<script>";
		msg += "alert('권한이 없습니다'); ";
		msg += "history.back();";
		msg += "</script>";
		out.write(msg);
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>YumYum 새 공지글 작성</title>
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
			.HH_label{display:inline-block; font-weight:bold; margin:17px 0 5px 0; font-size:11pt;}
			.HH_input[type=text]{text-indent:7px; border:none; border-bottom:1px solid silver;}
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
			.HH_input:focus{background:#ad968a; border-bottom:1px solid gray;opacity:0.7; transition: 1s;}
			.HH_title{width: 100%}
			#ex_year,#ex_month,#ex_day{height:30px; width:20%}
			#ex_select{height:30px; width:20%; background:#dddddd; outline:none; font-size:11px; border:1px solid #eaeaea; margin-left:0.5px}
			#HH_content{width:100%; margin-bottom:5px; outline:none;
						text-indent:7px; border: 1px solid silver;}
			#HH_content:focus{background:#ad968a; border:1px solid gray;opacity:0.7; transition: 1s;}
			.HH_button{font-size:10.5pt;}
			.HH_button[type='submit']{background:#ad968a; color:white; margin-top:37px;}
			.HH_button[type='reset']{}
			.HH_button{height:35px; width:49.5%; border-style:none; cursor:pointer;}
			.HH_button:hover{opacity:0.7; border-radius:15px; transition: 0.3s;}
			.HH_span{display:none; position: absolute;}
			.HH_span{color:red; font-size:11px;}
			.HH_input::placeholder{font-size:14px; color:#a2a2a2}
		</style>
	</head>
	<body>
		<header>
			<jsp:include page="../Main/Y_header.jsp"/>
		</header>
		<div class="out_container">
			<div class="write_line">
				<form action="Y_Board_Write" method="post" id="writeform">
					<fieldset>
						<legend>
							<a href="#" class="HH_a">공지등록</a>
						</legend>
						<label for="writer" class="HH_label">작성자</label>
						<input type="text" id="writer" name="writer" readonly value="<%=session.getAttribute("nickname") %>" class="HH_input">
						<br>
						<label class="HH_label">카테고리</label>
						<label for="announcements">공지</label>
						<input type="radio" name="category" id="announcements" value="0" checked>
						<label for="events">이벤트</label>
						<input type="radio" name="category" id="events" value="1">
						<br>
						<label for="all_time" class="HH_label">상시노출</label>
						<input type="checkbox" name="all_time" id="all_time" value="1" checked>
						<br>
						<div id="ex_date">
							<label class="HH_label">만료일</label>
							<br>
							<input type="text" id="ex_year" name="ex_year" size="4" maxlength="4"
								   placeholder="Year" class="HH_input">년
							<input type="text" id="ex_month" name="ex_month" size="2" maxlength="2" 
								   placeholder="Month" class="HH_input">월
							<input type="text" id="ex_day" name="ex_day" size="2" maxlength="2" 
								   placeholder="Day" class="HH_input">일
							<select id="ex_select">
								<option selected value="direct">직접 입력</option>
								<option value="day">1 일 뒤</option>
								<option value="week">1 주 뒤</option>
								<option value="month">1 달 뒤</option>
								<option value="year">1 년 뒤</option>
							</select>
						</div>
						<label for="title" class="HH_label">제목</label>
						<br>
						<input type="text" name="title" id="title" class="HH_input HH_title"
							   placeholder="Title" maxlength="100" required>
						<br>
						<label for="HH_content" class="HH_label">내용</label>
						<br>
						<textarea name="content" id="HH_content" rows="15" placeholder="Content" required></textarea>
						<button type="submit" class="HH_button">작성완료</button>
						<button type="button" class="HH_button" onclick="back()">취소</button>
						<input type="hidden" name="writeDone" value="done">
					</fieldset>
				</form>
			</div>
		</div>
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script>
			function back()
			{
				location.href="BoardMain";
			}
			$('#all_time').change(function(){
				if($('#all_time').prop("checked") == true)
				{
					$('#ex_date').css('display', 'none');
				}
				else
				{
					$('#ex_date').css('display', 'inline-block');
				}
			});
			$('#ex_select').change(function(){
				var val = $('#ex_select').val();
				if(val == 'direct')
				{
					$('#ex_year').prop('readonly', false).val('');
					$('#ex_month').prop('readonly', false).val('');
					$('#ex_day').prop('readonly', false).val('');
				}
				else
				{
					var today = new Date();
					
					if(val == 'day')
					{
						today.setDate(today.getDate() + 1); 
					}
					else if(val == 'week')
					{
						today.setDate(today.getDate() + 7);
					}
					else if(val == 'month')
					{
						today.setDate(today.getDate() + 30);
					}
					else if(val == 'year')
					{
						today.setDate(today.getDate() + 365);
					}
					$('#ex_year').prop('readonly', true).val(today.getFullYear());
					$('#ex_month').prop('readonly', true).val(today.getMonth() + 1);
					$('#ex_day').prop('readonly', true).val(today.getDate());
				}
			});
		</script>
		<footer>
			<jsp:include page="../Main/Y_footer.jsp"/>
		</footer>
	</body>
</html>