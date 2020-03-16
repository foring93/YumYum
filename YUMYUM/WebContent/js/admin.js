$(function(){
	var page = $('.nowpage').val();
	if(page){
		$(".row a[href*="+page+"]").css('background','#E9F4FD');
	}else{
		$(".row a:eq(0)").css('background','#E9F4FD');
	}
})