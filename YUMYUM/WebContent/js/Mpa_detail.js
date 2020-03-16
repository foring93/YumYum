$(function(){
	$("button[type=button]").click(function(){
		var reject = prompt("이유를 입력하세요:","형식이 올바르지않습니다.");
		$('input[name=reject]').val(reject);
		var rejectwhy=$('input[name=reject]').val();
		var num=$('input[name=MP_NO]').val();
		//이유를 입력해야 에이잭스 실행
		console.log("prompt반환:"+reject);
		if(reject==null){
			alert("취소됨");
			return;
		}
		else if(reject!=""&&reject!=null){	
			console.log("입력됨");
		$.ajax({
			url:"admin",
			data:{"page":"reject","MPA_NO":num,"reject":rejectwhy},
			type:"get",
			success:function(){
				alert("반려되었습니다.");
				location.href="../Main/admin?page=registerlist";
				
			},error:function(){
				alert("오류! 다시 실행하여주세요");
			}
		})//ajax end
		}else{
			alert("이유를 입력해주세요");
		}
	});//click end
	
})