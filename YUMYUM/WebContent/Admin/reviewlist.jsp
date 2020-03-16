<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    

<link rel = "stylesheet" type = "text/css" href = "../css/Y_admin.css">
<style>
	.imgs_url:hover, .re_writers:hover, .mp_names:hover, .re_contents:hover{
		cursor:pointer;
		opacity:0.5;
	}
	.imgs_url{
		max-width:210px;
		color:dimgrey;
		font-weight:bold;
	}
	.re_writers, .mp_names, .re_contents{color:dimgrey; font-weight:bold;}
	.detailArea{
		display:block;
		margin-top:20px;
		text-align:center;
		min-height: 120px;
		/*width:-webkit-fill-available;*/
		max-width: 850px;
		border: 0;
	}
	.reviewImg{
		display: inline-block;
		margin: 5px;
	}
	.reviewImg img{
		height:100px;
		width:auto;
		margin-top:inherit;
	}
	.detailMsg{
		text-align:center;
		font-size:15pt;
		color:darkgrey;
		width:100%;
		height:100%;
		line-height:6;
	}
	.fullContent{
		display:inline-block;
		max-width:850px;
		padding:40px;
		text-align:center;
		font-size:14pt;
		color:grey;
		vertical-align:middle;
		word-wrap:break-word;
	}
	.hh_btn:hover{
		opacity:0.8;
		border-radius:8px;
		transition: 0.4s;
	}
	.no_review{
		text-align:center;
		vertical-align:middle;
		color:darkgrey;
		font-weight:bold;
		height:60pt;
	}
	.hiddenBtn{
		display:none;
	}
	.modal-header{
		display:contents;
	}
	.modal-title{
		font-size:15pt;
	}
</style>
<div class="b_div" style="text-align: center;">
	<div>
		<h1 class="b_h1">리뷰 관리 </h1>
		<hr>
			<table class="table-form">
				<thead>
					<tr>
						<th>리뷰번호</th>
						<th>작성자</th>
						<th>맛집이름</th>
						<th>내용</th>
						<th>평점</th>
						<th>추천수</th>
						<th>작성일</th>
						<th>이미지</th>
						<th>관리</th>
					</tr>
				</thead>
			<tbody>
			<c:if test="${reviewlist[0] == null }">
				<tr><td class="no_review" colspan="9">등록된 리뷰가 없습니다.</td></tr>
			</c:if>
			<c:forEach var="r" items="${reviewlist}">
				<tr>
					<td>${r.re_no}</td>
					<td class="re_writers">${r.re_writer}<input type="hidden" value="${r.re_writer_id}"></td>
					<td class="mp_names">${r.re_ref_name}<input type="hidden" value="${r.re_ref_no}"></td>
					<td class="re_contents"><span>${r.re_content}</span><input type="hidden" value="${r.re_content}"></td>
					<td>${r.re_grade}</td>
					<td>${r.re_recommend_cnt}</td>
					<td>${r.re_regidate}</td>
					<td class="imgs_url"><span>${r.re_img_url}</span><input type="hidden" value="${r.re_img_url}"></td>
					<td>
						<button class="btn hh_btn" style="background:lightgrey;font-weight: bolder;font-size: 10pt" type="button">삭제</button>
						<input type="hidden" value="../review/Y_Review_Delete?re_no=${r.re_no}&mp_no=${r.re_ref_no}">
					</td>
				</tr>
			</c:forEach>
			</tbody>
	
		</table>
	</div>
	<button type="button" class="btn btn-lg hiddenBtn" data-toggle="modal" data-target="#review_detail" id="reviewDB">
 		 리뷰 상세보기
	</button>
	<div class="modal fade" id="review_detail" tabindex="-1" role="dialog" aria-labelledby="reviewDetail" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		        <h4 class="modal-title" id="reviewDetail">리뷰 상세정보</h4>
		      </div>
		      <div class="modal-body">
		       <!-- <img src="" data-toggle="modal" data-target="#img_detail"> -->
				<div class="detailArea">
					<span class="detailMsg">내용, 이미지 클릭시 상세정보 출력</span>
				</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
		      </div>
		    </div>
		</div>
	</div>
	
</div>
<script>

	// 작성자 클릭시 작성자의 상세 정보페이지로 이동
	$('.re_writers').click(function(){
		location.href='admin?page=Member_detail&USER_ID=' + $(this).children('input').val();
	});
	
	// 맛집이름 클릭시 맛집 상세 페이지로 이동
	$('.mp_names').click(function(){
		location.href='../M_category/place.move?mp_no=' + $(this).children('input').val();
	});
	
	// 글 내용이 너무 길 경우 뒷 부분 .. 으로 변경
	$('.re_contents>span').each(function(){
		var reCon = $(this).text();
		if(reCon.length > 10)
		{
			$(this).text(reCon.substr(0, 10) + ' ...');
		}
	});
	
	// 글 내용 클릭 시 상세내용 하단부에 노출
	$('.re_contents').click(function(){
		$('.detailArea').empty();
		if($(this).text() == '')
		{
			var msg = '<span class="detailMsg">글 내용이 없습니다</span>';
			$('.detailArea').append(msg);
			return;
		}
		$('.detailArea').css('display', 'block');
		var reCon = $(this).children('input').val();
		var full_content = '<div class="fullContent">' + reCon + '</div>';
		$('.detailArea').append(full_content);
		$('#reviewDB').trigger('click');
	});
	
	// 이미지가 너무 길 경우 뒷 부분 .. 으로 변경
	$('.imgs_url>span').each(function(){
		var imgURL = $(this).text();
		if(imgURL.length >= 30)
		{
			$(this).text(imgURL.substr(0, 27) + ' ...');
		}
	});
	
	// 이미지 클릭시 하단부에 이미지 노출
	$('.imgs_url').click(function(){
		$('.detailArea').empty();
		$('.detailArea').css('display', 'block');
		if($(this).text() == '')
		{
			var msg = '<span class="detailMsg">이미지가 없습니다</span>';
			$('.detailArea').append(msg);
			return;
		}
		var imgs = $(this).children('input').val().split('*');
		var img = '';
		$.each(imgs, function(){
			img += '<div class="reviewImg"><img src="../img/' + this +'"></div>';
		});
		$('.detailArea').append(img);
		$('#reviewDB').trigger('click');
	});
	
	// 삭제 클릭시 정말로 삭제할지 확인
	$('.hh_btn').click(function(){
		var ans = confirm('정말로 삭제하시겠습니까?');
		if(ans)
		{
			location.href = $(this).parent().children('input').val();
		}
	});
</script>