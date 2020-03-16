$(function() {
	$(".yy_idsearchform").submit(function() {
		var name = $("#yy_name").val();
		var phone = $("#yy_phone").val();
		var cnt = 0;
		if (name == "" || name == null) {
			cnt++;
		}
		if (phone == null || phone == "") {
			cnt++
		}
		if (cnt > 0) {
			alert("입력칸을 입력하세요");
			return false;
		}

	})
})