<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("id") == null)
	{
		out.write("<script>alert('먼저 로그인해주세요.'); top.location.href='../Login';</script>");
	}
	String mp_no = request.getParameter("mp_no");
	String mp_name = request.getParameter("mp_name");
	
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>YUMYUM ~ 리뷰 작성</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<script src="http://code.jquery.com/jquery-latest.js"></script>
<style>
			*::placeholder{font-size:14px; color: #a2a2a2}
			.de_in{width:100%!important}
			.reviews{padding-top:10px;}
			.mpArea span{font-size:10.5pt;}
			#ReviewWriteForm{position:relative; margin-top:67px;}
			footer{left:0; bottom:0; right:0; margin-bottom:32px;}
			fieldset{border:1px solid #eaeaea;}
			*{outline:none!important;}
			.hh_con{min-height:500px;}
			.topArea{
				width: 600px;
				height: 70px;
				display: block;
				margin:0 auto;
				margin-top:50px;
			}
			.centerArea{
				width: 600px;
				height: 200px;
				display: block;
				margin: 0 auto;
				margin-top: 20px;
			}
			.bottomArea{
				width: 600px;
				height: 150px;
				display: block;
				margin: 0 auto;
				margin-top:10px;
			}
			.mpArea{
				display: inline-block;
				height:50px;
				vertical-align:middle;
				float: left;
			}
			#mp_name{
				border:0;
				font-size:17pt;
				font-weight:bolder;
				color:#636363;
			}
			
			.ratingArea{
				display: inline-block;
				height: 50px;
				line-height: 170px;
				float: right;
				color: #5d5d5d;
				vertical-align:middle;
				font-weight:bold;
			}
			.stars{
				display:inline-block;
				margin:0 auto;
				height: 23px;
			}
			.ratingArea img{
				vertical-align: text-top;
				width:19px;
				height:19px;
				cursor:pointer;
				margin:0 -1px;
			}
			.ratingArea span{
				display:inline-block;
			}
			.userArea{
				display: inline-block;
				width: 100px;
				vertical-align: middle;
				float: left;
			}
			#writer{
				border: 0;
				width: 200px;
				font-size:13pt;
				font-weight:bold;
				color:#636363;
				vertical-align:middle;
				position:absolute;
				top:143px;
			}
			
			.contentArea{
				display: inline-block;
				width: 600px;
				height: 200px;
				margin-top:20px;
			}
			
			.reviewContent {
			    display: inline-block;
			    width: 595px;
			    height: 150px;
			    padding: 12px 15px 30px 15px;
			    border: 1px solid #DBDBDB;
			    border-radius: 3px;
			    box-sizing: border-box;
			    font-size: 16px;
			    color: #000000;
			    resize: none;
			}
			.reviewContent:focus{
				background:#f1e2da;
				border:1px solid gray;
				opacity:0.7;
				transition: 1s;
				font-size:11pt;
			}
			.textLength{
				height:50px;
				font-size:10.5pt;
				color:gray;
			}
			#imgNum{
				font-size:11pt;
				color:gray;
			}
			
			.HH_button{
					display:inline-block;
					color:white;
					border-style:none;
					line-height:32px;
					font-size:10.5pt;
					cursor:pointer;
					height:35px; width:49.5%;
			}
			.HH_button[type=submit]{
				background:#ad968a; color:white; margin-top:37px;
			}
			.HH_button:hover{
				opacity:0.7; border-radius:15px; transition: 0.3s;
			}
			
			.imgArea{
				display: block;
			}
			
			.imgbox{
				display: inline-block;
			}

			.imgbox label{
				display: inline-block; padding: .5em .75em; color: #999;
				font-size: inherit; line-height: normal; vertical-align: middle;
				background-color: #fdfdfd; cursor: pointer; border: 1px solid #ebebeb;
				border-bottom-color: #e2e2e2; border-radius: .25em;
			}
			
			.imgbox input[type="file"]{
			 	/* 파일 필드 숨기기 */
			 	position: absolute; width: 1px; height: 1px; padding: 0;
			 	margin: -1px; overflow: hidden; clip:rect(0,0,0,0); border: 0;
			}
		
			/* imaged preview */
			.imgbox .upload-display{
			/* 이미지가 표시될 지역 */
				margin-bottom: 5px;
			}
			@media(min-width: 768px){
				.imgbox .upload-display{
					display: inline-block; margin-right: 4px; margin-bottom: 0;
				}
			}
			
		
			.imgbox .upload-thumb-wrap{
			/* 추가될 이미지를 감싸는 요소 */
				display: inline-block; width: 52px;
				padding: 2px; vertical-align: middle;
				border: 1px solid #ddd; border-radius: 5px;
				background-color: #fff;
			}
			
			.imgbox .upload-display img {
			/* 추가될 이미지 */
				display: block; max-width: 100%; width: 100% \9;
				height: auto;
			}
			
			.imgbox .upload-display:hover{
				opacity: 0.5;
				cursor:pointer;
			}
		</style>
	</head>
	<body>
		<div class="container hh_con">
			<fieldset>
				<form	action="Y_Review_Write" method="post" id="YWriteFrm"
						enctype="multipart/form-data">
					<!-- 맛집 번호 들어갈 자리 -->
					<input type="hidden" name="mp_no" value="<%=mp_no%>">
					<div class="topArea">
						<div class="mpArea">
							<!-- 맛집 이름 들어갈 자리 -->
							<input type="text" name="mp_name" id="mp_name" value="<%=mp_name%>" readonly>
							<br>
							<span>에 대한 솔직한 리뷰를 써주세요.</span>
						</div>
						<div class="ratingArea">
							<span>평점 주기</span>
							<div class="stars">
								<img src="img/orange_star.png" id="v1">
								<img src="img/orange_star.png" id="v2">
								<img src="img/orange_star.png" id="v3">
								<img src="img/orange_star.png" id="v4">
								<img src="img/orange_star.png" id="v5">
							</div>
							<input type="hidden" name="grade" id="grade" value="5">
						</div>
					</div>
					<div class="centerArea">
						<div class="userArea">
							<!-- 작성자 닉네임이 들어갈 자리 -->
							<input type="text" name="writer" readonly id="writer" value="<%=session.getAttribute("nickname")%>">
						</div>
						<div class="contentArea">
							<textarea name="content" class="reviewContent" rows="15" maxlength="500" required
									  placeholder="<%=session.getAttribute("nickname") %>님 , 주문하신 메뉴는 어떠셨나요? 식당의 분위기와 서비스도 궁금해요!"
									  style="overflow: hidden; overflow-wrap: break-word; height: 150px;"></textarea>
							<div class="textLength">
								<span class="currentTextLength">0</span>
								<span>/ 500</span>
							</div>
						</div>
					</div>
					<div class="bottomArea">
						<div class="imgArea">
							<span id="imgNum">0/9</span>
							<br>
							<div class="imgbox preview-image">
								<label for="img1" id="label1">+</label>
								<input type="file" name="img1" id="img1"
									   class="upload-hidden" accept=".gif, .jpg, .png, .jpeg">
							</div>
						</div>
						<button type="submit" class="HH_button" id="submitBtn">작성완료</button>
						<button type="button" class="HH_button" id="cancelBtn">취소</button>
					</div>
				</form>
			</fieldset>
		</div>
		<script>
			// rating 관련
			$('#v1').click(function(){
				$.each($('.ratingArea img'), function(index){
					if(index > 0)
						$(this).attr('src', 'img/grey_star.png');
					else
						$(this).attr('src', 'img/orange_star.png');
				});
				$('#grade').val('1');
			});
			$('#v2').click(function(){
				$.each($('.ratingArea img'), function(index){
					if(index > 1)
						$(this).attr('src', 'img/grey_star.png');
					else
						$(this).attr('src', 'img/orange_star.png');
				});
				$('#grade').val('2');
			});
			$('#v3').click(function(){
				$.each($('.ratingArea img'), function(index){
					if(index > 2)
						$(this).attr('src', 'img/grey_star.png');
					else
						$(this).attr('src', 'img/orange_star.png');
				});
				$('#grade').val('3');
			});
			$('#v4').click(function(){
				$.each($('.ratingArea img'), function(index){
					if(index > 3)
						$(this).attr('src', 'img/grey_star.png');
					else
						$(this).attr('src', 'img/orange_star.png');
				});
				$('#grade').val('4');
			});
			$('#v5').click(function(){
				$('.ratingArea img').attr('src', 'img/orange_star.png');
				$('#grade').val('5');
			});
			
			// content 관련
			$('.reviewContent').on('keyup', function(){
				var remain = $('.reviewContent').val().length;
				$('.currentTextLength').text(remain);
			});
			
			
			// img 업로드
			var imgbox = $('.imgbox');
			var cnt = 1;
			var imgNum = 0;
			var fileTarget = $('.imgbox .upload-hidden');
			var act = 0;
			
			// 새로운 div 생성
			function newDivision(){
				$('#label' + cnt).css('display', 'none');
				cnt++;
				if(imgNum < 9)
				{
					var newDiv = '<div class="imgbox preview-image"> '
						   + 	'<label for="img' + cnt + '" id="label' + cnt + '">+</label> '
						   + 	'<input type="file" name="img' + cnt + '" id="img' + cnt + '" '
					   	   + 		   'class="upload-hidden" accept=".gif, .jpg, .png, .jpeg"> '
						   + '</div>'
					$('.imgArea').append(newDiv);
					imgTarget.off('change', previewImg);
					imgTarget = $('.preview-image .upload-hidden');
					imgTarget.on('change', previewImg);
				}
			}
			
			// img 수정
			function editImg(){
				var parent = $(this).parent();
				act = 1;
				parent.children('label').trigger('click');
			}
			
			//preview image
			var imgTarget = $('.preview-image .upload-hidden');
			imgTarget.on('change', previewImg);
			var uploadImg = $('.upload-display');
			uploadImg.click(editImg);
			
			
			// 이미지를 보여주는 function
			function previewImg(){
				var parent = $(this).parent();
				parent.children('.upload-display').remove();
				if(window.FileReader){
					//image 파일만
					if (typeof ($(this)[0].files[0]) == 'undefined')
					{
						alert('이미지 업로드를 취소합니다.');
						parent.remove();
						if(imgNum != 0)
							imgNum--;
						$('#imgNum').text(imgNum + '/9');
						act = 0;
						newDivision();
						return;
					}
					if (!$(this)[0].files[0].type.match(/image\//))
					{
						alert('이미지만 업로드 가능합니다.');
						parent.remove();
						if(imgNum != 0)
							imgNum--;
						$('#imgNum').text(imgNum + '/9');
						act = 0;
						newDivision();
						return;
					}
					var reader = new FileReader();
					reader.onload = function(e){
						var src = e.target.result;
						parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img src="'+src+'" class="upload-thumb"></div></div>');
						if(act == 1)
						{
							act = 0;
						}
						else
						{
							imgNum++;
							$('#imgNum').text(imgNum + '/9');
							newDivision();
						}
						uploadImg.off('click', editImg);
						uploadImg = $('.upload-display');
						uploadImg.click(editImg);
					}
					reader.readAsDataURL($(this)[0].files[0]);
				}
				else
				{
					$(this)[0].select();
					$(this)[0].blur();
					var imgSrc = document.selection.createRange().text;
					parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb"></div></div>');
					var img = $(this).siblings('.upload-display').find('img');
					img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")";
				}
			}
		
			$('#cancelBtn').on('click', closingFrame);
			function closingFrame()
			{
				top.$('#review_write_cancel_btn').trigger('click');
			}
		</script>
	</body>
</html>