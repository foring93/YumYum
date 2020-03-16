<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>YUMYUM 리뷰 모아보기</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<!-- 모달 css 구현 -->
		<link href="../css/Y_Review_Modal.css" rel="stylesheet" type="text/css">
		<style>
			.Rview_B_con{min-height:762px;}
			.review{width:830px; margin:15px 0; border:1px solid #dabcac; float:left; border-radius:25px 0; position:relative; left:142px;}
			.re_info{width:465px; display:inline-block; margin:20px 10px 10px 15px;}
			.re_regidate{display:inline-block;}
			.re_img{display: inline-block; width: 200px; height:160px; float:left; margin:20px 10px 20px 20px;}
			.selects{margin:35px; text-align:center;}
			.selects span{font-size:10.5pt; color:gray; margin-left:11px;}
			.selects select{font-size:11pt; color:#5d5d5d;}
			.mp_name{display:inline-block; font-size:13.5pt; color:#77675f; margin-bottom:3px;}
			.mp_name:hover{cursor:pointer; opacity:0.7; transition: 0.3s;}
			.re_regidate,.user_nickname{display:block; font-size:11.5pt;}
			.re_control{display:inline-block; width:80px; float:right; margin:17px 15px 0 0; font-size:11pt;}
			.re_content{font-size:9.5pt;word-wrap: break-word;}
			.review_grade img{width:15px;}
			.re_regidate{color:gray; margin-top:30px;}
			.editR{margin:0 5px;}
			.editR,.deleteR{color:gray;}
			.editR:hover,.deleteR:hover{color:black; transition: 0.3s; cursor:pointer;}
			.recommend_cnt{margin-left:7px;}
			.user_nickname{margin:0 0 7px 3px; font-weight:bold;}
			.re_regidate{font-size:10pt}
			.color_orange{text-align:center; margin-top:90px; font-size:1.8rem; color:darkgrey;}
			#move_top_btn{
				position: fixed;
				right: 2%;
				bottom: 50px;
				display: none;
				z-index:999;
				border:0;
				background:white;
				outline:none!important;
			}
			.viewMore{
				z-index:999;
				display: block;
				margin: 0 auto;
				margin-top: 20px;
				width: 100%;
				text-align:center;
				border:0;
				background:0;
				outline:none!important;
			}
			.viewMore:hover{
				opacity:0.7;
			}
			.repImgs:hover{
				opacity:0.7;
				border:0.5px solid black;
				cursor:pointer;
			}
			
		</style>
	</head>
	<body>
		<header>
			<jsp:include page="../Main/Y_header.jsp"/>
		</header>
		<div class="container Rview_B_con">
			<div class="selects">
				<span>카테고리</span>
				<select id="category_by">
					<option value="" selected>종류</option>
					<!-- 추가 -->
					<option value="한식">한식</option>
					<option value="양식">양식</option>
					<option value="중식">중식</option>
					<option value="일식">일식</option>
					<option value="분식">분식</option>
					<option value="치킨">치킨</option>
					<option value="피자">피자</option>
					<option value="고기">고기</option>
					<option value="국수">국수</option>
					<option value="디저트">디저트</option>
					<option value="멕시코음식">멕시코음식</option>
					<option value="태국음식">태국음식</option>
					<option value="베트남음식">베트남음식</option>
				</select>
				<span>정렬 순</span>
				<select id="order_by">
					<option value="0" selected>최신 순</option>
					<option value="1">추천 순</option>
					<option value="2">높은 평점 순</option>
					<option value="3">낮은 평점 순</option>
				</select>
				<span>평점</span>
				<select id="grade_by">
					<option value="0" selected>전체 보기</option>
					<option value="1">4 이상만</option>
				</select>
			</div>
			<div class="reviews" id="reviews">
				<h2 class="color_orange">아직 등록된 리뷰가 없습니다.</h2>
			</div>
		</div>
		<button id="move_top_btn" type="button">TOP</button>
		   <button type="button" class="btn btn-lg hiddenBtn" data-toggle="modal" data-target="#imgDetails" id="imgsBtn"></button>
	   <!-- modalArea -->
		<div class="modal fade" id="imgDetails" tabindex="-1" role="dialog" aria-labelledby="imgsDetail" aria-hidden="true">
			<div class="modal-dialog modal-lg modal-center">
				<div class="modal-content">
					<button type="button" class="close xBtn" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">X</span></button>
				    <div class="modal-body">
				       
						<div class="sameRImgsArea modal-center">
								
						</div>
					</div>
					<div class="modal-footer">
						
					</div>
				</div>
			</div>
		</div>
		<!-- modalArea Ends -->
		
		
		<footer>
			<jsp:include page="../Main/Y_footer.jsp"/>
		</footer>
		<script>
			var wholeReviews = <%=request.getAttribute("reviewList")%>;
			var category = "";
			var order = 0;
			var grade = 0;
			var senddata = {'category' : category, 'order' : order, 'grade' : grade};
			
			var start = 0;
			var end = 4;
			
			
			function showWholeReviews()
			{
				if(wholeReviews == null || wholeReviews.length == 0)
				{
					$('#reviews').empty();
					$('#reviews').html('<h2 class="color_orange">아직 등록된 리뷰가 없습니다.</h2>');
					return;
				}
				var output = '';
				$.each(wholeReviews, function(index){
					if(index == 0 && index == start)
					{
						$('#reviews').empty();
					}
					if(index < start)
					{
						return true;
					}
					if(index > end)
					{
						return true;
					}
					output += '<div class="review">';
					output += 	'<div class="re_img">';
					var imgs_url = this.re_img_url;
					if(imgs_url == null || imgs_url == '')
					{
						output += 		'<img src="img/noImg.gif" width="100%" height="100%">';
					}
					else
					{
						var img = imgs_url.split('*');
						output += 		'<img class="repImgs" src="../img/' + img[0] + '" width="100%" height="100%">';
						output +=		'<input type="hidden" value="'+imgs_url+'">';
					}
					output += 	'</div>';
					output += 	'<div class="re_info">';
					output +=		'<input type="hidden" value="' + this.re_ref_no + '">';
					output += 		'<span class="mp_name">' + this.re_ref_name + '</span>';
					output += 		'<span class="user_nickname">' + this.re_writer + '</span>';
					output += 		'<span class="re_content">' + this.re_content + '</span>'
					output += 		'<span class="re_regidate">' + this.re_regidate + '</span>'
					output +=	'</div>';
					output +=	'<div class="re_control">';
					output +=		'<input type="hidden" value="' + this.re_no + '">';
				<%
					if(session.getAttribute("user_is_admin") != null)
					{
						int admin = (int)session.getAttribute("user_is_admin");
						String user = (String)session.getAttribute("nickname");
						if(admin == 1)
						{
				%>
					output +=		'<span class="editR">수정</span>';
					output +=		'<span class="deleteR">삭제</span>';
				<%
						}
						else
						{
				%>
					if(this.re_writer == '<%=user%>')
					{
						output +=	'<span class="editR">수정</span>';
						output +=	'<span class="deleteR">삭제</span>';
					}
				<%
						}
					}
				%>
					output += 		'<div class="review_grade">';
					var grade = this.re_grade;
					for(var cnt = 1; cnt <= 5; cnt++)
					{
						if(cnt <= grade)
							output +=		'<img src="img/orange_star.png">';
						else
							output += 		'<img src="img/grey_star.png">';
					}
					output += 		'</div>';
					output += 		'<br>';
					output += 		'<span class="recommend_cnt">추천수 : ' + this.re_recommend_cnt + '</span>';
					output +=	'</div>';
					output += '</div>';
					if(index == end && wholeReviews.length - 1 > end)
					{
						output += '<button class="viewMore" id="viewMore">더보기</button>';
					}
				});
				$('#reviews').append(output);
				$('.editR').click(editReview);
				$('.deleteR').click(deleteReview);
				$('.mp_name').click(redirectin);
				$('.viewMore').click(viewMore);
			}
			showWholeReviews();
			
			// select 변경시 호출하는 ajax
			function getReviews(sd)
			{
				$.ajax({
					type : 'post',	// post 방식
					url : '/YUMYUM/review/Y_Review_Board',	// 요청 전송 url
					data : sd,
					dataType : 'json', 	// return data의 type
					cache : false,	// cache 사용하지 않겠다.
					success : function(review_data){
						wholeReviews = review_data;
						reviewCount = reviews.length;
						start = 0;
						end = 4;
						showWholeReviews();
					},	// HTTP 요청이 성공한 경우 실행
					error : function(request, status, error){
						alert('error 발생!');
					},
					complete : function(){
						
					}	// 요청의 성공유무와 상관없이 완료 될 경우
				});	// ajax end
			}
			
			// 더보기 버튼 구현
			function viewMore()
			{
				start += 5;
				end += 5;
				this.remove();
				showWholeReviews();
			}
			
			
			// Scroll Top
			$(window).scroll(function(){
				if($(this).scrollTop() > 500)
				{
					$('#move_top_btn').fadeIn();
				}
				else
				{
					$('#move_top_btn').fadeOut();
				}
			});
			$('#move_top_btn').click(moveTop);
			function moveTop()
			{
				$('html, body').animate({scrollTop : 0}, 100);
				return false;
			}
			
			
			// 음식 종류 변경 시
			$('#category_by').on('change', function(){
				category = $('#category_by').val();
				senddata = {'category' : category, 'order' : order, 'grade' : grade, 'ajax' : 'ajax'};
				getReviews(senddata);
			});
			

			// 정렬순 변경 시
			$('#order_by').on('change', function(){
				order = $('#order_by').val();
				senddata = {'category' : category, 'order' : order, 'grade' : grade, 'ajax' : 'ajax'};
				getReviews(senddata);
			});
			

			// 평점 변경할 시 게시물을 정렬하는 함수
			$('#grade_by').on('change', function(){
				grade = $('#grade_by').val();
				senddata = {'category' : category, 'order' : order, 'grade' : grade, 'ajax' : 'ajax'};
				getReviews(senddata);
			});
			
			// 수정 기능 구현
			function editReview()
			{
				var ans = confirm('리뷰를 수정하시겠습니까?');
				if(ans)
				{
					var re_no = $(this).parent().children('input[type="hidden"]').val();
					var mp_no = $(this).parent().parent().children('.re_info').children('input[type="hidden"]').val();
					location.href='Y_Review_Update?re_no=' + re_no + '&mp_no=' + mp_no;
				}
			}
			
			// 삭제 기능 구현
			function deleteReview()
			{
				var ans = confirm('정말로 리뷰를 지우시겠습니까?');
				if(ans)
				{
					var re_no = $(this).parent().children('input[type="hidden"]').val();
					var mp_no = $(this).parent().parent().children('.re_info').children('input[type="hidden"]').val();
					location.href='Y_Review_Delete?re_no=' + re_no + '&mp_no=' + mp_no;
				}
			}
			
			// 맛집 이름 클릭 시 상세페이지로
			function redirectin()
			{
				var mp_no = $(this).parent().children('input[type="hidden"]').val();
				location.href='../M_category/place.move?mp_no=' + mp_no;
			}
			
			// 모달 기능 구현
			$('.repImgs').click(modalImgOn);
			// 이미지 클릭시 modal 불러오기 기능
			var imgStart = 0;
			var imgEnd = 3;
			var imgLength = 0;
			function modalImgOn()
			{
				imgStart = 0;
				imgEnd = 3;
				var sameRImgs = $(this).parent().children('input').val().split('*');
				imgLength = sameRImgs.length;
				var backSrc = '../img/back1.png';
				var nextSrc = '../img/next.png';
				var imgsDiv = '<div class="cImgsArea">';
				var currentShow = '';
				$.each(sameRImgs, function(index){
					if(index == 0)
					{
						currentShow = '<div class="currentShowImg"><img src="../img/'+ this +'"></div>';
					}
					if(index > imgEnd)
					{
						imgsDiv += '<div class="clickableImgs hiddenImg"><img src="../img/'+ this +'"></div>';
					}
					else
					{
						imgsDiv += '<div class="clickableImgs"><img src="../img/'+ this +'"></div>';
					}
				});
				imgsDiv += '</div>';
				imgsDiv = currentShow + imgsDiv;
				if(imgLength > 4)
				{
					imgsDiv = '<div class="backImg hiddenImg"><img src="'+ backSrc +'"></div>' + imgsDiv + '<div class="nextImg"><img src="'+ nextSrc +'"></div>'
				}
				$('.sameRImgsArea').empty();
				$('.sameRImgsArea').append(imgsDiv);
				$('#imgsBtn').trigger('click');
				$('.clickableImgs>img').click(changeCurrentImg);
				$('.nextImg').click(showNextImg);
				$('.backImg').click(showBackImg);
			}
			
			// modal 에서 이미지 클릭시 크게 보여주는 이미지 변경
			function changeCurrentImg(){
				$('.currentShowImg>img').attr('src', this.src);
			}
			

			// next 클릭시 다음 index 사진들 보여줌
			var preventN = 0;
			function showNextImg(){
				// 연속 클릭 방지
				if(preventN != 0){return}
				preventN = 1;
				preventB = 1;
				if($('.backImg').hasClass('hiddenImg'))
					$('.backImg').removeClass('hiddenImg').css('opacity','0').animate({opacity:"1"},400);
				//$(this).parent().children('.cImgsArea').css({'position':'absolute'});
				var targetImg = $(this).parent().children('.cImgsArea').children('.clickableImgs').eq(imgStart);
				targetImg.animate({opacity:"0"},400,function(){
					$(this).parent().animate({left:"-=72"},700,function(){
						targetImg.addClass('hiddenImg');
						$(this).css('left','-177px');
						preventN = 0;
						preventB = 0;
						imgStart++;
						imgEnd++;
					});
					var targetImg2 = $(this).parent().children('.clickableImgs').eq(imgEnd+1);
					targetImg2.removeClass('hiddenImg').css('opacity','0');
					targetImg2.animate({opacity:"1"},700);
				});
				if(imgLength - 2 == imgEnd)
				{
					$(this).animate({opacity:"0"},400,function(){
						$(this).addClass('hiddenImg');
					});
				}
			}
			
			// back 클릭시 이전 index 사진들 보여줌
			var preventB = 0;
			function showBackImg(){
				// 연속클릭방지
				if(preventB != 0){return};
				preventB = 1;
				preventN = 1;
				if($('.nextImg').hasClass('hiddenImg'))
					$('.nextImg').removeClass('hiddenImg').css('opacity','0').animate({opacity:"1"},400);
				var targetImg = $(this).parent().children('.cImgsArea').children('.clickableImgs').eq(imgEnd);
				targetImg.animate({opacity:"0"},400,function(){
					$(this).parent().animate({left:"+=72"},700,function(){
						targetImg.addClass('hiddenImg');
						$(this).css('left','-177px');
						preventB = 0;
						preventN = 0;
						imgStart--;
						imgEnd--;
					});
					var targetImg2 = $(this).parent().children('.clickableImgs').eq(imgStart-1);
					targetImg2.removeClass('hiddenImg').css('opacity','0');
					targetImg2.animate({opacity:"1"},700);
				});
				if(imgStart == 1)
				{
					$(this).animate({opacity:"0"},400,function(){
						$(this).addClass('hiddenImg');
					});
				}
			}
			
		</script>
	</body>
</html>