$(document).ready(function(){
	$('#KIND').change(function(){
		var select = $(this).val();
		if(select == '직접선택'){//직접 선택한 경우
			$('#domain').prop('readOnly',false);
			$('#domain').val('');
			$('#domain').focus();
		}else{//option 중에서 선택한 경우
			$('#domain').prop('readonly',true);
			$('#domain').val(select);
		}
	});
})