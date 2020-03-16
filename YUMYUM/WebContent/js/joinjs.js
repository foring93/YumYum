$(document).ready(function(){
	
	//회원가입 버튼 클릭시 공백유효성 검사
	$('#joinform').submit(function(){
		
		//ID공백 검사
		if($.trim($('#id').val()) == ''){
			$('.idMessage').html("필수 정보입니다.");
			$('#id').focus();
			return false;
		}
		
		//Password공백 검사
		var pw = $('#pass').val().trim();
		if(pw == ''){
			$('.passMessage').html("필수 정보입니다.");
			$('#pass').focus();
			return false;
		}
			
		//Password 자릿수 체크
		var pass = $('#pass').val().length;
		if(pass < 4){
			alert('비밀번호 4자리이상 입력하세요');
			$('#pass').focus();
			return false;
		}
		
		//Name공백 검사
		var name = $('#name').val().trim();
		if(name == ''){
			$('.nameMessage').html("필수 정보입니다.");
			$('#name').focus();
			return false;
		}
		//Name 자릿수 체크
		var name = $('#name').val().length;
		if(name < 2){
			alert('이름 2자리이상 입력하세요');
			$('#name').focus();
			return false;
		}
		
		//생년월일 공백검사
		var year = $('#year').val().trim();
		if(year == ''){
			$('.birthMessage').html("필수 정보입니다.");
			$('#year').focus();
			return false;
		}
		//생년월일 자릿수 체크
		var year1 = $('#year').val().length;
		if(year1 != 4){
			alert('년도 4자리를 입력하세요');
			$('#year').focus();
			return false;
		}
		
		var month = $('#month').val().trim();
		if(month == ''){
			$('.birthMessage').html("필수 정보입니다.");
			$('#month').focus();
			return false;
		}
		var day = $('#day').val().trim();
		if(day == ''){
			$('.birthMessage').html("필수 정보입니다.");
			$('#day').focus();
			return false;
		}
		
		//성별 선택부분 
		var gender = $('#gender').val().trim();
		if(gender == ''){
			$('.genderMessage').html("필수 정보입니다.");
			$('#gender').focus();
			return false;
		}
		
		//손님 점주 체크
		var box1 = $("#box1").prop("checked");
		var box2 = $("#box2").prop("checked");
		if(!box1 && !box2){
			alert("일반회원,사업자 중 선택하세요.");
			$('#box1').focus();
			return false;
		}
		if(box2){ //사업자 체크일때 유효성 확인
			if($('#business_no').val() == ''){
				$('.bnMessage').html("필수 정보입니다.");
				$('#business_no').focus();
				return false;
			}
			//사업자번호 자리수 체크
			var bn = $('#business_no').val().length;
			if(bn != 10){
				alert('사업자 번호 10자리를 입력하세요');
				$('#business_no').focus();
				return false;
			}
		}
		
		//email 공백 유효성 검사
		var email = $('#email').val().trim();
		if(email == ''){
			$('.emailMessage').html("필수 정보입니다.");
			$('#email').focus();
			return false;
		}
		//domain 공백 유효성 검사
		var domain = $('#domain').val();
		if(domain == ''){
			$('.emailMessage').html("필수 정보입니다.");
			$('#domain').focus();
			return false;
		}
		
		//우편번호 유효성 검사
		var post1 = $('#post1').val().trim();
		if(post1 == ''){
			$('.postMessage').html("필수 정보입니다.");
			$('#post1').focus();
			return false;
		}else{
			$('.postMessage').html("");
		}
		
		//핸드폰번호 공백 유효성 검사
		var phone = $('#phone').val().trim();
		if(phone == ''){
			$('.phoneMessage').html("필수 정보입니다.");
			$('#phone').focus();
			return false;
		}
		
		//핸드폰번호 자리수 체크
		var p = $('#phone').val().length;
		if(p <= 9){
			alert('휴대전화 번호 9~11자리를 입력하세요');
			$('#phone').focus();
			return false;
		}
		
		//닉네임 공백 유효성 검사
		var nickname = $('#nickname').val().trim();
		if(nickname == ''){
			$('.nickMessage').html("필수 정보입니다.");
			$('#nickname').focus();
			return false;
		}
		
	});//submit end
	/* 공백 유효성 검사 end */
	
	//ID 4자리 이상
	$("#id").keyup(function(){
		//$(".idMessage").empty(); // empty()비워주는 메소드
		var pattern = /^\w{5,12}$/;
		var id = $(this).val();
		if(!pattern.test(id)){
			$(".idMessage").css('color','red').html("영문 숫자_로 5~12자 가능합니다.");
			//submit버튼 막음
			$('#submit_btn').attr('disabled',true);
			//return;
		}else{
			$('#submit_btn').attr('disabled',false);
			$.ajax({
				type : "post",
				url : "idCheck",
				data : {"id" : id},
				success : function(resp){
					if(resp == -1){
						$('.idMessage').css('color','green').html("사용 가능한 아이디입니다.");
						$('#submit_btn').attr('disabled',false);
					}else{
						$('.idMessage').css('color','#ff6060').html("사용중인 아이디 입니다.");
						$('#submit_btn').attr('disabled',true);
					}
				}//if else end
			});//ajax end
		}
	});//idCheck end
	
	//Password 4자리 이상
	$('#pass').keyup(function(){
		if($(this).val().length < 4){
			$('.passMessage').html("비밀번호 4자리 이상 입력하세요.");
		}else{
			$('.passMessage').html("");
		}
	})
	
	//생년월일 자릿수 체크
	$('#year').keyup(function(){
		//생년월일 자릿수 체크
		if($(this).val().length != 4){
			$('.birthMessage').html("년도 4자리를 입력하세요.");
		}else{
			$('.birthMessage').html("");
		}
	})
	
	//사업자번호 10자리
	$('#business_no').keyup(function(){
		if($(this).val().length > 9){
			$('.bnMessage').html("");
		}else{
			$('.bnMessage').html("사업자번호 10자리를 입력하세요.")
		}
	});
	
	//Phone 중복검사 ajax
	$('#phone').keyup(function(){
		var phone = $(this).val();
		if($(this).val().length <= 9){
			$('.phoneMessage').css('color','red').html("휴대폰 번호 9~11자리를 입력하세요.")
		}else{
			$.ajax({
				type : "post",
				url : "PhoneCheck",
				data : {"phone" : phone},
				success : function(p){
					if(p == -1){
						$('.phoneMessage').css('color','green').html('사용 가능한 핸드폰번호입니다.');
						$('#submit_btn').attr('disabled',false);
					}else{
						$('.phoneMessage').css('color','#ff6060').html('사용중인 핸드폰번호입니다.');
						$('#submit_btn').attr('disabled',true);
					}
				}
			});
		}
	});
	
	//이름 형식 유효성검사
	$('#name').blur(function(){
		if($(this).val().length > 1){
			reg = /^[가-힣a-zA-Z]+$/;
			if(!reg.test($(this).val())){				
				//alert('한글이나 영문으로 입력해주세요')
				$('.nameMessage').html("한글이나 영문 대 소문자를 입력해주세요.");
				$(this).val('');
				$(this).focus();
			}else{
				$('.nameMessage').html("");
			}
		}
	});
	
	//생년월일 형식 유효성검사
	$('#year').keyup(function(){
		if($(this).val().length == 4){
			reg = /^[0-9][0-9]{3,}$/;
			if(!reg.test($(this).val())){				
				$('.birthMessage').html("년도(Year)를 숫자로만 입력해주세요");
				$(this).val('');
				$(this).focus();
			}else{
				$('.birthMessage').html("");
			}
		}
	});
	$('#month').blur(function(){
		if($(this).val() != ''){
			$('.birthMessage').html("");
		}
	});
	$('#day').keyup(function(){
		reg = /^[0-9]{1,}$/;
		if(!reg.test($(this).val())){
			$('.birthMessage').html("일(Day)을 숫자로 입력해주세요.")
			$(this).val('');
			$(this).focus();
		}else{
			$('.birthMessage').html("");
		}
	})
	
	$('#gender').blur(function(){
		if($(this).val() != ''){
			$('.genderMessage').html("");
		}
	});
	
	$('#email').blur(function(){
		if($(this).val() != ''){
			$('.emailMessage').html("");
		}
	});
	
	$('#domian').blur(function(){
		if($(this).val() != ''){
			$('.emailMessage').html("");
		}
	})
	
	$('#post1').blur(function(){
		if($(this).val() != ''){
			$('.postMessage').html("");
		}
	});
	
	$('#address').blur(function(){
		if($(this).val() != ''){
			$('.postMessage').html("");
		}
	});
	
	$('#postbtn').focus(function(){
		if($('#post1').val() != ''){
			$('.postMessage').html("");
		}		
	});
	
	$('#business_no').blur(function(){
		if($(this).val() != ''){
			$('.bnMessage').html("");
		}	
	})
	
	//사업자번호 형식 유효성검사
	$('#business_no').keyup(function(){
		//사업자번호 10자리인 경우
		if($(this).val().length == 10){
			pattern = /^[0-9][0-9]{9,}$/;
			if(!pattern.test($(this).val())){				
				alert('숫자로 입력해주세요')
				$(this).val('');
				$(this).focus();
			}
		}
	});
	
	//휴대전화번호 형식 유효성검사
	$('#phone').keyup(function(){
		//핸드폰번호 9~11자리인 경우
		if($(this).val().length >= 9){
			pattern = /^[0][0-9]{8,12}$/;
			if(!pattern.test($(this).val())){				
				alert('형식에 맞게 입력해주세요')
				$(this).val('');
				$(this).focus();
			}
		}
	});
	
	//NickName 2자리 이상, 중복검사 ajax
	$('#nickname').keyup(function(){
//		var pattern = /^\w{5,12}$/;
		var nickname = $(this).val();
		if($('#nickname').val().length < 2){
		//if(!pattern.test(nick)){
			$('.nickMessage').css('color','red').html("닉네임 2자리이상 입력하세요.");
			$('#submit_btn').attr('disabled',true);
		}else{
			$.ajax({
				type : "post",
				url : "NickCheck",
				data : {"nickname" : nickname},
				success : function(nick){
					if(nick == -1){
						$('.nickMessage').css('color','green').html('사용 가능한 닉네임입니다.');
						$('#submit_btn').attr('disabled',false);
					}else{
						$('.nickMessage').css('color','#ff6060').html('사용중인 닉네임입니다.');
						$('#submit_btn').attr('disabled',true);
					}
				}
			});
		}
	})//NickNameCheck
	
	//select 태그에서 선택한 도메인 설정하는 부분
	$('#sel').change(function(){
		var select = $(this).val();
		if(select == ''){//직접 선택한 경우
			$('#domain').prop('readOnly',false);
			$('#domain').val('');
			$('#domain').focus();
		}else{//option 중에서 선택한 경우
			$('#domain').prop('readonly',true);
			$('#domain').val(select);
			$('.emailMessage').html("");
		}
	});
	
	//domain 형식
	// var pattern /\w+[.]\w{3}/;
	$('#domain').keyup(function(){
		var pt  = /\w+[.]\w{3}/;
		var domain = $(this).val();
		if(!pt.test(domain)){
			$('.emailMessage').css('color','red').html("도메인 형식이 맞지 않습니다.");
		}else{
			$('.emailMessage').css('color','red').html("");
		}
	})
	
	//점주,손님 체크빡스
	$("input[type='checkbox']").change(function(){
		var box2 = $("#box2").prop("checked");
		if(box2){
			$("#business_no").css('display','block');
		}else{
			$("#business_no").css('display','none');
		}
	});
	
	$(".HY_input").focus(function(){
		$('.HY_label').css('color','#75655c')
					.css('transition','0.7s');
	})
	$(".HY_input").blur(function(){
		$('.HY_label').css('color','black');
	})
});