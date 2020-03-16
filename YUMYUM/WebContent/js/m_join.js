//맛집 분류를 선택하면 직접 입력을 눌렀을 때 빼고 
//input에 맛집 분류가 들어감
$(document).ready(function() {
	$('#KIND').change(function() {
		var select = $(this).val();
		if (select == '') {// 직접 선택한 경우
			$('#MP_KIND').prop('readOnly', false);
			$('#MP_KIND').val('');
			$('#MP_KIND').focus();
		} else {// option 중에서 선택한 경우
			$('#MP_KIND').prop('readonly', true);
			$('#MP_KIND').val(select);
		}
	});

	// 해쉬태그의 #을 만들어 주는 함수
	$('#MP_HASH').on('keyup', makeSh);
	function makeSh()
	{
		var mp_hash = $('#MP_HASH').val();
		var leng = mp_hash.length;
		if(leng != mp_hash.trim().length)
		{
			if(mp_hash.substr(0,1) != '#')
			{
				mp_hash = '#' + mp_hash;
			}
			if(mp_hash.substr(mp_hash.length-2,1) != '#')
			{
				if((mp_hash.match(/#/g) || []).length >= 5)
				{
					alert('해쉬태그는 5개까지만 입력 가능합니다.');
					$('#MP_HASH').val(mp_hash.trim());
					return;
				}
				$('#MP_HASH').val(mp_hash.trim() + '#');
			}
			else
				$('#MP_HASH').val(mp_hash.trim());
		}
		if(mp_hash.trim().replace('#','').length == 0)
		{
			$('#MP_HASH').val('');
		}
	}
	
	// img 의 src 를 바꿔주는 함수
	var imgTarget = $('#rep_img_file');
	imgTarget.on('change', previewImg);
	function previewImg()
	{
		if(window.FileReader)
		{
			if (typeof ($(this)[0].files[0]) == 'undefined')
			{
				$('.rep_img>img').attr('src', '../img/noimage.gif');
				$('#rep_img_name').val('');
				return;
			}
			if (!$(this)[0].files[0].type.match(/image\//))
			{
				alert('이미지만 업로드 가능합니다.');
				$('.rep_img>img').attr('src', '../img/noimage.gif');
				$('#rep_img_name').val('');
				return;
			}
			var reader = new FileReader();
			reader.onload = function(e){
				var src = e.target.result;
				$('.rep_img>img').attr('src', src);
				$('#rep_img_name').val(src);
			}
			reader.readAsDataURL($(this)[0].files[0]);
		}
	}
	// 추가 첨부 이미지의 파일이름을 띄워주는 function
	$('#etc_imgs_names').click(function(){
		$('#etc_imgs_label').trigger('click');
	});
	$('#etc_imgs').on('change', setEtcImgsName);
	function setEtcImgsName()
	{
		$.each(this.files, function(){
			if(typeof (this) == 'undefined')
			{
				alert('이미지 업로드를 취소합니다.');
				$('#etc_imgs_names').val('');
				$('.addfileMessage').text('추가 이미지 파일이 없습니다.');
				return;
			}
			if(!this.type.match(/image\//))
			{
				alert('이미지만 업로드 가능합니다.');
				$('#etc_imgs_names').val('');
				$('.addfileMessage').text('추가 이미지 파일이 없습니다.');
				return
			}
		});
		var filesLength = this.files.length;
		if(filesLength == 0)
		{
			alert('이미지 업로드를 취소합니다.');
			$('#etc_imgs_names').val('');
			$('.addfileMessage').text('추가 이미지 파일이 없습니다.');
			return;
		}
		if(filesLength > 8)
		{
			alert('추가 이미지는 8개까지만 가능합니다.');
			$('#etc_imgs_names').val('');
			$('.addfileMessage').text('추가 이미지 파일이 없습니다.');
			return;
		}
		var fileNames = '';
		$.each(this.files, function(index){
			if(index == 0)
				fileNames += this.name;
			else
				fileNames += ', ' + this.name;
		});
		$('#etc_imgs_names').val(fileNames);
		$('.addfileMessage').text('');
	}
	
	
	// 맛집 등록 폼에서 등록 버튼 누를 때 입력 폼을 다 채우지 않으면 안넘어가도록

	$('#joinform').submit(function() {
		// cnt 가 1이상이면 return false;
		// 식당이름을 입력하지 않으면 cnt++;
		var cnt = 0;
		var m_name = $(".yy_m_name").val();
		if (m_name == null || m_name == "") {
			$(".nameMessage").text('식당이름을 입력하세요').css('color', 'red');
			cnt++;
		} else {
			$(".nameMessage").text('');
		}
		// 식당 분류를 입력하지 않거나 선택하지 않으면 cnt++;
		var m_category = $('.yy_m_category').val().trim();
		if (m_category == null || m_category == "") {
			$(".kindMessage").text('식당분류를 선택하세요').css('color', 'red');
			cnt++;
		} else {
			$(".kindMessage").text('');
		}

		// 맛집 우편번호를 입력하지 않으면 cnt++;
//		var m_postcode = $('.yy_m_postcode').val();
//		if (m_postcode == null || m_postcode == "") {
//			$(".postMessage").text('주소 검색을 통해 우편번호를 입력해주세요').css('color', 'red');
//			cnt++;
//		} else {
//			$(".postMessage").text('');
//		}
		
		// 맛집 주소를 입력하지 않으면 cnt++;
		var m_address = $('.yy_m_address').val();
		if (m_address == null || m_address == "") {
			$(".addressMessage").text('우편 검색을 통해 주소를 입력해주세요').css('color', 'red');
			cnt++;
		} else {
			$(".addressMessage").text('');
		}
		
		// 식당 전화번호를 입력하지 않으면 cnt++;
		var m_telephone = $('.yy_m_telephone').val();
		if (m_telephone == null || m_telephone == "") {
			$(".telephoneMessage").text('식당 전화번호를 입력해주세요').css('color', 'red');
			cnt++;
		} else {
			$(".telephoneMessage").text('');
		}
		
		// 상세정보를 입력하지 않으면 cnt++;
		var m_detail = $('.yy_m_detail').val();
		if (m_detail == null || m_detail == "") {
			$(".detailMessage").text('상세정보를 입력해주세요').css('color', 'red');
			cnt++;
		} else {
			$(".detailMessage").text('');
		}
		
		// 오픈/클로징 시간을 입력하지 않으면 cnt++;
		var m_time = $('.yy_m_time').val();
		if (m_time == null || m_time == "") {
			$(".OpenCloseTimeMessage").text('오픈/클로징 시간을 입력해주세요').css('color', 'red');
			cnt++;
		} else {
			$(".OpenCloseTimeMessage").text('');
		}
		console.log("대표이미지전cnt:"+cnt);
		//대표 이미지를 등록하지 않으면 cnt++;
		var m_file = $('#rep_img_name').val();
		if (m_file == null || m_file == "") {
			$(".fileMessage").text('대표이미지를 등록해주세요').css('color', 'red');
			cnt++;
		} else {
			$(".fileMessage").text('');
		}
		
		console.log("마지막cnt:"+cnt);
		//cnt가 1이상이면 return false;
	      if(cnt>0){
	         return false;
	      }else{
	         return true;
	      }
	});
	
})// ready end

