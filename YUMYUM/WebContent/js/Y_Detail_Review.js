$(document).ready(function(){
	// 해당 맛집 게시물의 모든 리뷰
	var mp_no = $('input[name=mp_no]').val();
	var order = 0;
	var grade = 0;
	var senddata = {'mp_no' : mp_no, 'order' : order, 'grade' : grade};
	var reviews = new Array();
	var reviewCount = 0;
	getReviewData(senddata);
	
	// database 에서 모든 리뷰글을 가져오는 fucntion
	var start = 0;
	var end = 4;
	
	function getReviewData(sd)
	{
		$.ajax({
			type : 'post',	// post 방식
			url : '../review/Y_Detail_Review',	// 요청 전송 url
			data : sd,
			dataType : 'json', 	// return data의 type
			cache : false,	// cache 사용하지 않겠다.
			success : function(review_data){
				reviews = review_data;
				reviewCount = reviews.length;
				start = 0;
				end = 4;
				showReviews();
			},	// HTTP 요청이 성공한 경우 실행
			error : function(request, status, error){
				alert('error 발생!');
			},
			complete : function(){
				
			}	// 요청의 성공유무와 상관없이 완료 될 경우
		});	// ajax end
	}
	
	// 유저의 모든 리뷰 추천 리스트를 가져오는 메서드
	var recomList = null;
	function getRecomList(userNum)
	{
		$.ajax({
			type : 'post',	// post 방식
			url : '../review/Y_Review_Recommend',	// 요청 전송 url
			data : {'user_no' : userNum},
			dataType : 'json', 	// return data의 type
			cache : false,	// cache 사용하지 않겠다.
			success : function(recomLists){
				recomList = recomLists;
				changeRecomImg();
			},	// HTTP 요청이 성공한 경우 실행
			error : function(request, status, error){
				alert('error 발생!');
			},
			complete : function(){
				
			}	// 요청의 성공유무와 상관없이 완료 될 경우
		});	// ajax end
	}
	function changeRecomImg()
	{
		$.each(recomList, function(){
			console.log(this.re_no);
			$('#recom' + this.re_no).children('img').attr('src', '../review/img/recom_on.png');
		});
	}
	
	// 맛집 게시물에 달린 모든 리뷰를 보여주는 function
	function showReviews()
	{
		if(reviews == null || reviewCount == 0)
		{
			$('.reviews').empty();
			$('.reviews').append('<span class="no_review">아직 등록된 리뷰가 없습니다.</span>');
			return;
		}
		$('.review_cnt').text('리뷰(' + reviewCount + ')');
		var output = '';
		var length = reviews.length;
		var user_nick = '';
		var user_is_admin = 0;
		var usernum = 0;
		var userChecked = 0;
		$.each(reviews, function(index){
			if(index == 0 && index == start)
			{
				$('.reviews').empty();
			}
			if(index < start)
			{
				return true;
			}
			if(index > end && index != length - 1)
			{
				return true;
			}
			if(index == length - 1)
			{
				if(userChecked == 1)
					return;
				if(this.user_nick != null)
				{
					user_nick = this.user_nick;
					user_is_admin = this.user_is_admin;
					usernum = this.usernum;
					reviewCount = reviewCount - 1;
					$('.review_cnt').text('리뷰(' + reviewCount + ')');
					userChecked = 1;
					return;
				}
				if(length-1 > end)
				{
					return;
				}
			}
			output += '<div class="review">';
			output += 	'<div class="user_info">';
			output += 		'<span class="user_nickname" id="rwriter' + this.re_no + '">' + this.re_writer + '</span>';
			output +=		'<hr>';
			output += 		'추천 : <span class="recommend_cnt" id="rcnt'+ this.re_no +'">' + this.re_recommend_cnt + '</span>';
			output +=		'<input type="hidden" value="' + this.re_no + '">';
			output +=	'</div>';
			output +=	'<div class="re_control"> ';
			output +=		'<span class="editR">수정</span>';
			output +=		'<span class="deleteR">삭제</span>';
			output +=	'</div>';
			output +=	'<div class="rev_content">';
			output += 		'<span class="re_regidate">' + this.re_regidate + '</span>';
			output += 		'<span class="review_grade">';
			var grade = this.re_grade;
			for(var cnt = 1; cnt <= 5; cnt++)
			{
				if(cnt <= grade)
					output +=		'<img src="../review/img/orange_star.png">';
				else
					output += 		'<img src="../review/img/grey_star.png">';
			}
			output += 		'</span>';
			output +=		'<span class="re_recom" id="recom'+ this.re_no +'"><img src="../review/img/recom_off.png"></span>';
			output +=	'</div>';
			output += 	'<div class="re_content">' + this.re_content + '</div>';
			output += 	'<div class="re_img">';
			var imgs_url = this.re_img_url;
			if(imgs_url == null || imgs_url == '')
			{
				
			}
			else
			{
				var img = imgs_url.split('*');
				$.each(img,function(index){
					output += '<img src="../img/' + this + '" width="auto" height="90px">';
				});
			}
			output += 	'</div>';
			output += '</div>';
			if(index == end && reviews.length - 1 > end)
			{
				output += '<button class="viewMore" id="viewMore">더보기</button>';
			}
		});
		output += '<input type="hidden" id="user_nick" value="'+user_nick+'">';
		output += '<input type="hidden" id="user_is_admin" value="'+user_is_admin+'">';
		output += '<input type="hidden" id="usernum" value="'+usernum+'">';
		$('.reviews').append(output);
		if(reviewCount == 0)
		{
			$('.reviews').append('<span class="no_review">아직 등록된 리뷰가 없습니다.</span>');
			return;
		}
		deleteControl();
		if($('#usernum').val() != null)
		{
			getRecomList($('#usernum').val());
		}
		$('.deleteR').on('click', deleteReview);
		$('.editR').on('click', editReview);
		$('.re_recom').on('click', recomReview);
		$('.viewMore').click(viewMore);
		$('.re_img>img').click(modalImgOn);
	}

	// 더보기 버튼 구현
	function viewMore()
	{
		start += 5;
		end += 5;
		this.remove();
		showReviews();
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
	
	
	
	// 정렬순 변경할 시 게시물을 정렬하는 함수
	$('#order_by').on('change', function(){
		order = $('#order_by').val();
		senddata = {'mp_no' : mp_no, 'order' : order, 'grade' : grade};
		getReviewData(senddata);
	});
	
	// 평점 변경할 시 게시물을 정렬하는 함수
	$('#grade_by').on('change', function(){
		grade = $('#grade_by').val();
		senddata = {'mp_no' : mp_no, 'order' : order, 'grade' : grade};
		getReviewData(senddata);
	});
	
	// 수정 기능 구현
	function editReview()
	{
		var ans = confirm('리뷰를 수정하시겠습니까?');
		if(ans)
		{
			var re_no = $(this).parent().parent().children('.user_info').children('input[type="hidden"]').val();
			location.href='../review/Y_Review_Update?re_no=' + re_no + '&mp_no=' + mp_no;
		}
	}
	
	// 삭제 기능 구현
	function deleteReview()
	{
		var ans = confirm('정말로 리뷰를 지우시겠습니까?');
		if(ans)
		{
			var re_no = $(this).parent().parent().children('.user_info').children('input[type="hidden"]').val();
			location.href='../review/Y_Review_Delete?re_no=' + re_no + '&mp_no=' + mp_no;
		}
	}
	
	// 운영자 혹은 본인이 아닐경우 수정 삭제 불가
	function deleteControl()
	{
		$.each($('.review'), function(){
			if($('#user_is_admin').val() == 0)
			{
				if($('#user_nick').val() != $(this).children('.user_info').children('.user_nickname').text())
				{
					$(this).children('.re_control').remove();
				}
			}
		});
	}
	
	// 리뷰 추천 기능 구현
	function recomReview()
	{
		var re_no = $(this).parent().parent().children('.user_info').children('input[type="hidden"]').val();
		var user_no = $('#usernum').val();
		var user_nick = $('#user_nick').val();
		if(user_no == 0)
		{
			alert('로그인이 필요합니다.');
			location.href = '../Login';
			return;
		}
		if(user_nick == $('#rwriter' + re_no).text())
		{
			alert('본인의 리뷰를 추천할 수 없습니다.');
			return;
		}
		var ddabong = {'user_no' : user_no, 're_no' : re_no};
		recommendReview(ddabong, re_no);
	}
	
	// database 에서 추천 리뷰 유무를 가져오는 function
	function recommendReview(ddb, rn)
	{
		$.ajax({
			type : 'post',	// post 방식
			url : '../review/Y_Review_Recommend',	// 요청 전송 url
			data : ddb,
			dataType : 'json', 	// return data의 type
			cache : false,	// cache 사용하지 않겠다.
			success : function(dbd){
				var msg = dbd.msg;
				alert(msg);
				if(msg == '오류 발생')
				{
					
				}
				else if(msg == '리뷰 추천 취소')
				{
					$('#recom' + rn).children('img').attr('src', '../review/img/recom_off.png');
					var rcnt = Number($('#rcnt' + rn).text());
					$('#rcnt' + rn).text(rcnt - 1);
				}
				else if(msg == '리뷰 추천 성공')
				{
					$('#recom' + rn).children('img').attr('src', '../review/img/recom_on.png');
					var rcnt = Number($('#rcnt' + rn).text());
					$('#rcnt' + rn).text(rcnt + 1);
				}
			},	// HTTP 요청이 성공한 경우 실행
			error : function(request, status, error){
				alert('error 발생!');
			},
			complete : function(){
				
			}	// 요청의 성공유무와 상관없이 완료 될 경우
		});	// ajax end
	}
	
	// 이미지 클릭시 modal 불러오기 기능
	var imgStart = 0;
	var imgEnd = 3;
	var imgLength = 0;
	function modalImgOn()
	{
		imgStart = 0;
		imgEnd = 3;
		var sameRImgs = $(this).parent().children('img');
		var imgNum = $(this).parent().children('img').index(this);
		
		imgLength = sameRImgs.length;
		var backSrc = '../img/back1.png';
		var nextSrc = '../img/next.png';
		var imgsDiv = '<div class="cImgsArea">';
		var currentShow = '';
		$.each(sameRImgs, function(index){
			if(index == imgNum)
			{
				currentShow = '<div class="currentShowImg"><img src="'+ this.src +'"></div>';
			}
			if(index > imgEnd)
			{
				imgsDiv += '<div class="clickableImgs hiddenImg"><img src="'+ this.src +'"></div>';
			}
			else
			{
				imgsDiv += '<div class="clickableImgs"><img src="'+ this.src +'"></div>';
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
}); // document end