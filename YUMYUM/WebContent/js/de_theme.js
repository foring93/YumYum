$(function(){
	// 해쉬태그의 #을 만들어 주는 함수
	$('#TH_HASH').on('keyup', makeSh);
	function makeSh()
	{
		var mp_hash = $('#TH_HASH').val();
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
					$('#TH_HASH').val(mp_hash.trim());
					return;
				}
				$('#TH_HASH').val(mp_hash.trim() + '#');
			}
			else
				$('#TH_HASH').val(mp_hash.trim());
		}
		if(mp_hash.trim().replace('#','').length == 0)
		{
			$('#TH_HASH').val('');
		}
	}
	
	
	$('.th_button').click(function(){
		if($('.toggle-table').css('display')=='none'){
		$('.toggle-table').css('display','inline-block');
		}else{
			$('.toggle-table').hide();
		}	
	});
	
	$('.TH_delete').click( function(){
	var recheck=confirm('삭제하시겠습니까?');
	var j = $(".TH_delete").index(this);  // 존재하는 모든 버튼을 기준으로 index

    console.log("버튼 위치"+j);


	var TH_NO=$('.TH_NO');
	console.log(TH_NO[j].value);
	if(recheck){
		location.href="../Main/admin?page=TH_delete&&TH_NO="+TH_NO[j].value;
	}else{
		
	}
	});
	
	$('.th_select').click(function(){
		
		location.href="../Main/admin?page=TH_select";
	})
	
})