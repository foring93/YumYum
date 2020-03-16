$(function(){
	/*첫번째 이미지 클릭시*/
	$('.img1').click(function(){
		var search=$('#TH1_HASH').val();
		var temp = search.replace(/#/g, "%23");
		location.href="../M_category/search.place?place="+temp;
	})
	/*두번째이미지 클릭시*/
	$('.img2').click(function(){
		var search=$('#TH2_HASH').val();
		var temp = search.replace(/#/g, "%23");
		location.href="../M_category/search.place?place="+temp;
	})
	/*세번째이미지 클릭시*/
	$('.img3').click(function(){
		var search=$('#TH3_HASH').val();
		var temp = search.replace(/#/g, "%23");
		location.href="../M_category/search.place?place="+temp;
	})
	/*네번째이미지 클릭시*/
	$('.img4').click(function(){
		var search=$('#TH4_HASH').val();
		var temp = search.replace(/#/g, "%23");
		location.href="../M_category/search.place?place="+temp;
	})
	/*다섯번째이미지 클릭시*/
	$('.img5').click(function(){
		var search=$('#TH5_HASH').val();
		var temp = search.replace(/#/g, "%23");
		location.href="../M_category/search.place?place="+temp;
	})
	/*여섯번째이미지 클릭시*/
	$('.img6').click(function(){
		var search=$('#TH6_HASH').val();
		var temp = search.replace(/#/g, "%23");
		location.href="../M_category/search.place?place="+temp;
	})
	
})