$(function(){
	
	// ## seesion id 찜 유,무 체크 ##
	var id = $('input[name=id]').val();
	var mp_no = $('input[name=mp_no]').val();
	$.ajax({
		type : "get",
		url : "user_jjim",
		dataType: "json",
		data : {"id" : id, "mp_no" : mp_no},
		success : function(data){
			if(data == 1){
				//찜목록에 있으면 1
				$('#jjim_img').attr('src','../img/icon2.png');
			}else{
				//찜목록에 없으면 0, 빈하트
				$('#jjim_img').attr('src','../img/icon1.png');
			}
		}
	})
	
	// ## li이미지 클릭시 img 바꾸기 ##
	$(".photo_list_li").click(function(){
		var photo = $(this).attr('src');
		$("#b_img").attr('src',photo);
	});//photo click
	
	// ## 맛집에 대한 별점 갯수 지정 ##
	//0.0 ~ 5.0 , star5 = 80%
	var star = $('#star_rating_img').val()*10;
		$('.star_img').css('width',star+'%');
	
	//하트button클릭시 찜목록 insert
	$('#jjim_btn').click(function(){		
		
		var id = $('input[name=id]').val();
		var mp_no = $('input[name=mp_no]').val();
		var jjim_img = $('#jjim_img').attr('src'); //찜하기, 찜취소
		
		console.log("jjim_img" + jjim_img);
		if(jjim_img == "../img/icon2.png"){
			//jjim_btn 클릭시 빈하트로 바뀌고
			$('#jjim_img').attr('src','../img/icon1.png');
			$.ajax({
				type : "get",
				url : "jjimCheck",
				dataType:"json",
				//찜상태는 ../img/icon2 상태로 넘어감
				data : {"jjim_img" : jjim_img, "id" : id, "mp_no" : mp_no},
				success : function(data){
					
				}
			})//ajax
		}else{
			$('#jjim_img').attr('src','../img/icon2.png');
			$.ajax({
				type : "get",
				url : "jjimCheck",
				dataType:"json",
				data : {"jjim_img" : jjim_img, "id" : id, "mp_no" : mp_no},
				success : function(data){
					
				}
			})//ajax
		}
	});//#jjim_btn click end
	
	var mp_address = $('.mp_address').text();
	//맛집주소와 맛집번호를 가져옴
	var senddata = {"mp_address" : mp_address, "mp_no" : $("input[name='mp_no']").val()};
	
	// ## 주변 추천식당 보여주는 용 ##
	$.ajax({
		type : "post",
		url : "Recommend_mp",
		dataType : "json",
		data : senddata, //파라미터값 보냄 mp_address=1?mp_no=1
		cache : false,
		success : function(data){
			var output = "";
				output += "<b>주변 인기 식당</b><br>"
			if(data.length != 0){
				$(data).each(function(index,item){
					//지금 접속해있는 상세페이지의 맛집은 추천목록에 안뜸
					output += "<a class='ya' href='place.move?mp_no="+ item.mp_no +"'>";
					output += "<div class='rc_tb'>";
					output += "  <img src='../img/" + item.mp_img_url + "'>"; 
					output += "  <div class='rc_bg'>";
					output += "		<span class='rc_name'>"+ item.mp_name +"</span><br>";
					output += "		<span class='rc_ar'>"+ item.mp_address +"</span>";
					output += "  </div>"
					output += "</div>";
					output += "</a>";
				})
			}else{ //db에 근처 맛집이 없으면
				output += "<h3 class='rcMessage'>주변 맛집이 없습니다..</h3>";
			}
			$('.mp_info_test').prepend(output); //선택한요소 첫번째
			
			//rc_tb hover하면 rc_bg = height:80px(기존60px), rc_name = margin-top:17px(기존7px) 
			$(".rc_tb").hover(function(){
				$(this).children(".rc_bg").css('height','80px').css('transition','0.3s');
				$(this).children(".rc_bg").children(".rc_name").css('margin-top','17px').css('transition','0.3s');
			});
			$(".rc_tb").mouseleave(function(){
				$(this).children(".rc_bg").css('height','60px').css('transition','0.3s');
				$(this).children(".rc_bg").children(".rc_name").css('margin-top','7px').css('transition','0.3s');
			})//.rc_tb hover
			
		}//success end
	})//ajax Recommend_mp	
	
	// ## 로고 이미지 관련 ##
	$(".search_img").hover(function(){
		$(this).css('background','#f1f1f1').css('transition','0.3s');
	})
	$(".search_img").mouseleave(function(){
		$(this).css('background','white').css('transition','0.3s');
	})
})