<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>이용방법</title>

  <!-- Custom styles for this template -->
  <link href="../css/modern-business.css" rel="stylesheet">
	<style>
		strong{
			margin-right:3px;
		}
		strong:hover{
			color:darkgrey;
			cursor:pointer;
		}
	</style>
</head>

<body>



    <div class="mb-4" id="accordion" role="tablist" aria-multiselectable="true">
      <div class="card">
        <div class="card-header" role="tab" id="headingOne">
          <h5 class="mb-0">
            <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">YUMYUM은 어떤 서비스 인가요?</a>
          </h5>
        </div>

        <div id="collapseOne" class="collapse show" role="tabpanel" aria-labelledby="headingOne">
          <div class="card-body">
            <strong>YumYum</strong>은 맛집 정보제공 서비스 웹페이지입니다.<br>
			누구나 자유롭게 맛집에 대해 <strong>평점</strong>과 <strong>리뷰</strong>를 달 수 있고,<br>
			다른 사용자들과 맛집 정보와 후기를 공유할 수 있습니다.<br>
			새롭게 등록된 맛집을 보시려면 <strong>신규맛집</strong>을,<br>
			많은 사람들에게 검증된 진짜 맛집을 찾으신다면 <strong>베스트맛집</strong>을,<br>
			원하시는 나만의 맛집을 찾고 계신다면 <strong>전체맛집</strong>으로!<br>
			<strong>검색</strong>과 <strong>찜하기</strong> 기능도 잊지마세요! 
          </div>
        </div>
      </div>
      <div class="card">
        <div class="card-header" role="tab" id="headingTwo">
          <h5 class="mb-0">
            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">Mypage는 무엇인가요?
            </a>
          </h5>
        </div>
        <div id="collapseTwo" class="collapse" role="tabpanel" aria-labelledby="headingTwo">
          <div class="card-body">
            <strong>MyPage</strong>에서 나의 정보수정, 내가 작성한 리뷰, 찜한 맛집 등을 관리할 수 있는 메뉴입니다.
          </div>
        </div>
      </div>
      <div class="card">
        <div class="card-header" role="tab" id="headingThree">
          <h5 class="mb-0">
            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">새로운 맛집을 등록하고 싶어요</a>
          </h5>
        </div>
        <div id="collapseThree" class="collapse" role="tabpanel" aria-labelledby="headingThree">
          <div class="card-body">
            	<b>일반회원</b><br>
            	안타깝게도 일반회원의 경우 맛집 등록을 직접할 수 없습니다.<br>
            	아래의 이메일을 통해 요청을 해주시면 검토 후 해당 맛집을 등록하도록 하겠습니다.<br><br>
            	<b>점주</b><br>
            	로그인후 아이디 옆쪽 화살표를 클릭하면<br>
            	드롭 다운 메뉴가 나옵니다. 거기서 맛집 등록을 클릭 후 맛집 등록을 하시면 됩니다.<br>
            	승인이 완료 되어야  맛집 등록이 완료 되며 승인 전에는 요청 철회가 가능합니다.<br>
            	승인 완료 후에는 맛집 정보 수정이 되지 않으니 아래 이메일로 수정 요청하여 주시기 바랍니다.<br><br>
            	YUMYUM80808@gmail.com<br>
          </div>
        </div>
      </div>
    </div>
    
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
	$('strong').on('click',function(){
		var loc = '';
		var num = $(this).index('strong');
		// YUMYUM
		if(num == 0){loc = '../Main/index';}
		// 리뷰
		else if(num == 1 || num == 2){loc = '../review/Y_Review_Board'}
		// 신규맛집
		else if(num == 3){loc = '../M_category/new'}
		// 베스트맛집
		else if(num == 4){loc = '../M_category/best'}
		// 전체맛집
		else if(num == 5){loc = '../M_category/all'}
		// 검색
		else if(num == 6){loc = '../M_category/search.place?place=%23YUMYUM'}
		// 찜하기
		else if(num == 7){loc = '../mypage/index?page=Y_dibs'}
		// MyPage
		else if(num == 8){loc = '../mypage/index'}
		else{loc = '#'}
		location.href = loc;
	});

</script>



</body>

</html>
