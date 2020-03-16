$(document).ready(function(){
	// 검색 시 서블릿으로 place 파라미터 값 갖고 이동한다.
	$('#search_btn2').click(function(){
		var search = $('#search_place').val();
		if (search == '#') {
			alert('검색어를 제대로 입력해주세요.');
			return;
		} else {
			var temp = search.replace(/#/g, "%23");
			location.href = "../M_category/search.place?place=" + temp;
		}
	});
});