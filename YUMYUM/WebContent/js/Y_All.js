/* 페이지 네이션 */
// 1페이지, 2페이지 등 클릭했을 때 이동 
function go(page) {
	show($('#sel_city').val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val(), page);
}

// ## 페이지 목록 
function setPaging(href, digit) {
	output += '';
	gray = '';
		
	// 현재 페이지가 #? ''이면 회색처리를 한다. a href = '#' 일 때 
	if (href == '') {
		gray = "gray";
	}
	anchor = "<a class = 'page-link " + gray + "'" + href + ">" + digit + "</a></li>";
	output += anchor;
}

//############ 맛집 찾아오기 ############
function show (city, subcity, kind, search, page) {
	$.ajax({
		type : "get",
		url : "place.all",
		data : {'place' : city, 'subplace' : subcity, 'kind' : kind, 'search' : search, 'page' : page},
		dataType : 'json',
		cache : false,
		success : function(data) {
			output = '';
			// 총 맛집 리스트 개수가 1개 이상인 경우
			if (data.listcount > 0) { 
				$('.yh_Y_All').html("");
				var num = data.listcount - (data.page-1) * data.limit; // 총 존재하는 글의 개수 - (기본 페이지(1) - 1) * 리스트 제한 개수(6)
				console.log(num);
				console.log(data.placelist);
				$(data.placelist).each(function(index, item) {
						// 한 줄당 세 개의 맛집을 출력한다.
						if ((index + 1) % 3 == 1) {
							output += '<tr>';
						}
						output += '	<td>';
						output += '		<a href = "../M_category/place.move?mp_no=' + item.MP_NO + '" class = "yh_move_place">';
						output += '			<img src = "../img/' + item.MP_IMG_URL + '" class = "yh_AllImg"><br>';
						output += '			<span class = "yh_img_title">' + item.MP_NAME;
						output += '			<span class = "detail_info">';
						output += '			<img src = "../img/view_cnt_icon.png" class = "mpViewCntImg">' + item.MP_VIEW_CNT;
						output += '			<img src="../img/d_edit.png" class="mpRecntImg">' + item.MP_RE_CNT;
						output += '			<img src="../img/d_star.png" class="mpGradeImg">' + item.MP_AVG_GRADE
						output += '			</span></span>';
						output += '		</a>';
						output += '	</td>';
						if ((index + 1) % 3 == 0) {
							output += '</tr>';
						}
				});
				
				console.log(output);
				$('.yh_Y_All').append(output);
				$('.pagination').empty();	// 페이징 처리 
				output = ""; // 변수 초기화
				
				digit = "«&nbsp;";
				href = "";
				
				// 2페이지 이상일 때 
				if (data.page > 1) {
					href = 'href=javascript:go(' + (data.page - 1) + ')';
				}
				
				setPaging(href, digit);
				
				for (var i = data.startpage; i <= data.endpage; i++) {
					digit = i;
					href = "";
					if (i != data.page) {
						href = 'href=javascript:go(' + i + ')';
					}
					setPaging(href, digit);
				}
				
				digit = '»&nbsp;';
				href = "";
				if (data.page < data.maxpage) {
					href = 'href=javascript:go(' + (data.page + 1) + ')';
				}
				
				setPaging(href, digit);
				
				$('.pagination').append(output);
			} // if (data.placelist > 0) end 
			else {
				console.log(data.placelist);
				$('.yh_Y_All').html("");
				$('.pagination').html("");
				output = '';
				output += '<tr>';
				output += '	<td style = "color : #CC4F43; width : 1140px">'
				output += '		<div class = "datanone">존재하는 맛집이 없습니다.</div>';
				output += '	</td>';
				output += '</tr>';
				$('.yh_Y_All').append(output);
			}
		}, // success end
		error : function(request, status, error) {
			console.log("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
		}
	}); // ajax end
}; // show() end

$(document).ready(function(){
	// # 처음 접속할 때 가나다 순으로 리스트 출력 
	if ($('#search_place').val() != '#') {
		show($('#sel_city').val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val(), 1);
	}
	
	// 검색 버튼 클릭 시 
	$('#search_btn').click(function(event){
		// 아무 검색어도 입력하지 않았다면
		if ($('#search_place').val() == '') {
			$('#search_place').focus();
			alert("검색할 단어를 입력해주세요.");
		} else if ($('#search_place').val() == '#') {
			$('#search_place').focus();
			event.preventDefault();
			alert("검색할 단어를 제대로 입력해주세요.");
			return;
		}
		// 검색어가 있다면 
		$('.yh_Y_All').html('');
		show($('#sel_city').val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val(), 1);
	});
	
	//지역별 선택 시
	$('#sel_city').change(function() {
		// 다른 지역을 선택했다가 '전체'를 선택할 시 세부 지역 초기화
		if ($('#sel_city').val() == '') {
			$('#sel_subcity').html('<option value = "">전체</option>');
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val(), 1);	
			return;
		}
		
		// 서울 선택 시 서울의 세부 지역 추가
		if ($('#sel_city').val() == '서울') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(seoul());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val(), 1);	
		}
		
		// 경기 선택 시 경기도의 세부 지역 추가
		if ($('#sel_city').val() == '경기') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(gyeonggi());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());	
		}
		
		// 인천 선택 시 인천의 세부 지역 추가
		if ($('#sel_city').val() == '인천') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(incheon());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());		
		}
		
		// 대전 선택 시 대전의 세부 지역 추가
		if ($('#sel_city').val() == '대전') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(daejeon());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());	
		}
		
		// 대구 선택 시 대구의 세부 지역 추가
		if ($('#sel_city').val() == '대구') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(daegu());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());		
		}
		
		// 부산 선택 시 부산의 세부 지역 추가
		if ($('#sel_city').val() == '부산') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(busan());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());	
		}
		
		// 광주 선택 시 광주의 세부 지역 추가
		if ($('#sel_city').val() == '광주') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(gwangju());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());		
		}
		
		// 제주 선택 시 제주의 세부 지역 추가
		if ($('#sel_city').val() == '제주') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(jeju());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());		
		}
		
		// 울산 선택 시 울산의 세부 지역 추가
		if ($('#sel_city').val() == '울산') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(ulsan());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());		
		}
		
		// 세종 선택 시 세종의 세부 지역 추가
		if ($('#sel_city').val() == '세종') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(sejong());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());		
		}
		
		// 강원 선택 시 강원의 세부 지역 추가
		if ($('#sel_city').val() == '강원') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(kangwon());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());		
		}
		
		// 충청북도 선택 시 충청북도의 세부 지역 추가
		if ($('#sel_city').val() == '충북') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(chungcheongN());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());		
		}
		
		// 충청남도 선택 시 충청남도의 세부 지역 추가
		if ($('#sel_city').val() == '충남') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(chungcheongS());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());		
		}
		
		// 전라북도 선택 시 전라북도의 세부 지역 추가
		if ($('#sel_city').val() == '전북') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(jeonbook());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());		
		}
		
		// 전라남도 선택 시 전라남도의 세부 지역 추가
		if ($('#sel_city').val() == '전남') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(jeonnam());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());		
		}
		
		// 경상북도 선택 시 경상북도의 세부 지역 추가
		if ($('#sel_city').val() == '경북') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(kyeongbook());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());		
		}
		
		// 경상남도 선택 시 경상남도의 세부 지역 추가
		if ($('#sel_city').val() == '경남') {
			$('#sel_subcity').html('');
			$('#sel_subcity').append(kyeongnam());
			$('.yh_Y_All').html('');
			show($(this).val(), $('#sel_subcity').val(), $('#sel_food').val(), $('#search_place').val());		
		}
	});
	
	// 세부지역별 선택 시
	$('#sel_subcity').change(function() {
		$('.yh_Y_All').html('');		// 기존의 데이터를 지우고
		show($('#sel_city').val(), $(this).val(), $('#sel_food').val(), $('#search_place').val(), 1);
	});
	
	// 음식 종류 선택 시 
	$('#sel_food').change(function(){
		$('.yh_Y_All').html('');
		show($('#sel_city').val(), $('#sel_subcity').val(), $(this).val(), $('#search_place').val(), 1);
	});
	
	function seoul() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value = "강남구">강남구</option>';
		input += '<option value = "강동구">강동구</option>';
		input += '<option value = "강서구">강서구</option>';
		input += '<option value = "강북구">강북구</option>';
		input += '<option value = "관악구">관악구</option>';
		input += '<option value = "광진구">광진구</option>';
		input += '<option value = "구로구">구로구</option>';
		input += '<option value = "금천구">금천구</option>';
		input += '<option value = "노원구">노원구</option>';
		input += '<option value = "동대문구">동대문구</option>';
		input += '<option value = "도봉구">도봉구</option>';
		input += '<option value = "동작구">동작구</option>';
		input += '<option value = "마포구">마포구</option>';
		input += '<option value = "서대문구">서대문구</option>';
		input += '<option value = "성동구">성동구</option>';
		input += '<option value = "성북구">성북구</option>';
		input += '<option value = "서초구">서초구</option>';
		input += '<option value = "송파구">송파구</option>';
		input += '<option value = "영등포구">영등포구</option>';
		input += '<option value = "용산구">용산구</option>';
		input += '<option value = "양천구">양천구</option>';
		input += '<option value = "은평구">은평구</option>';
		input += '<option value = "종로구">종로구</option>';
		input += '<option value = "중구">중구</option>';
		input += '<option value = "중랑구">중랑구</option>';
		return input;
	}
	
	function gyeonggi() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value = "가평군">가평군</option>';
		input += '<option value = "고양시">고양시</option>';
		input += '<option value = "고양시 덕양구">고양시 덕양구</option>';
		input += '<option value = "고양시 일산동구">고양시 일산동구</option>';
		input += '<option value = "고양시 일산서구">고양시 일산서구</option>';
		input += '<option value = "과천시">과천시</option>';
		input += '<option value = "광명시">광명시</option>';
		input += '<option value = "광주시">광주시</option>';
		input += '<option value = "구리시">구리시</option>';
		input += '<option value = "군포시">군포시</option>';
		input += '<option value = "김포시">김포시</option>';
		input += '<option value = "남양주시">남양주시</option>';
		input += '<option value = "동두천시">동두천시</option>';
		input += '<option value = "부천시">부천시</option>';
		input += '<option value = "성남시">성남시</option>';
		input += '<option value = "성남시 수정구">성남시 수정구</option>';
		input += '<option value = "성남시 중원구">성남시 중원구</option>';
		input += '<option value = "성남시 분당구">성남시 분당구</option>';
		input += '<option value = "수원시">수원시</option>';
		input += '<option value = "수원시 장안구">수원시 장안구</option>';
		input += '<option value = "수원시 권선구">수원시 권선구</option>';
		input += '<option value = "수원시 팔달구">수원시 팔달구</option>';
		input += '<option value = "수원시 영통구">수원시 영통구</option>';
		input += '<option value = "시흥시">시흥시</option>';
		input += '<option value = "안산시">안산시</option>';
		input += '<option value = "안산시 상록구">안산시 상록구</option>';
		input += '<option value = "안산시 단원구">안산시 단원구</option>';
		input += '<option value = "안성시">안성시</option>';
		input += '<option value = "안양시">안양시</option>';
		input += '<option value = "안양시 만안구">안양시 만안구</option>';
		input += '<option value = "안양시 동안구">안양시 동안구</option>';
		input += '<option value = "양주시">양주시</option>';
		input += '<option value = "양평군">양평군</option>';
		input += '<option value = "여주시">여주시</option>';
		input += '<option value = "연천군">연천군</option>';
		input += '<option value = "오산시">오산시</option>';
		input += '<option value = "용인시">용인시</option>';
		input += '<option value = "용인시 처인구">처인구</option>';
		input += '<option value = "용인시 기흥구">기흥구</option>';
		input += '<option value = "용인시 수지구">용인시 수지구</option>';
		input += '<option value = "의왕시">의왕시</option>';
		input += '<option value = "의정부시">의정부시</option>';
		input += '<option value = "이천시">이천시</option>';
		input += '<option value = "파주시">파주시</option>';
		input += '<option value = "평택시">평택시</option>';
		input += '<option value = "포천시">포천시</option>';
		input += '<option value = "하남시">하남시</option>';
		input += '<option value = "화성시">화성시</option>';
		return input;
	}
	
	function incheon() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value = "강화군">강화군</option>';
		input += '<option value = "계양구">계양구</option>';
		input += '<option value = "남동구">남동구</option>';
		input += '<option value = "동구">동구</option>';
		input += '<option value = "미추홀구">미추홀구</option>';
		input += '<option value = "부평구">부평구</option>';
		input += '<option value = "서구">서구</option>';
		input += '<option value = "연수구">연수구</option>';
		input += '<option value = "용진군">옹진군</option>';
		input += '<option value = "중구">중구</option>';
		return input;
	}
	
	function daejeon() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value="대덕구">대덕구</option>';
		input += '<option value="동구">동구</option>';
		input += '<option value="서구">서구</option>';
		input += '<option value="유성구">유성구</option>';
		input += '<option value="중구">중구</option>';
		return input;
	}
	
	function daegu() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value="남구">남구</option>';
		input += '<option value="달서구">달서구</option>';
		input += '<option value="달성군">달성군</option>';
		input += '<option value="동구">동구</option>';
		input += '<option value="북구">북구</option>';
		input += '<option value="서구">서구</option>';
		input += '<option value="수성구">수성구</option>';
		input += '<option value="중구">중구</option>';
		return input;
	}
	
	function busan() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value="강서구">강서구</option>';
		input += '<option value="금정구">금정구</option>';
		input += '<option value="기장군">기장군</option>';
		input += '<option value="남구">남구</option>';
		input += '<option value="동구">동구</option>';
		input += '<option value="동래구">동래구</option>';
		input += '<option value="부산진구">부산진구</option>';
		input += '<option value="북구">북구</option>';
		input += '<option value="사상구">사상구</option>';
		input += '<option value="사하구">사하구</option>';
		input += '<option value="서구">서구</option>';
		input += '<option value="수영구">수영구</option>';
		input += '<option value="연제구">연제구</option>';
		input += '<option value="영도구">영도구</option>';
		input += '<option value="중구">중구</option>';
		input += '<option value="해운대구" >해운대구</option>';
		return input;
	}
	
	function gwangju() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value="광산구">광산구</option>';
		input += '<option value="남구">남구</option>';
		input += '<option value="동구">동구</option>';
		input += '<option value="북구">북구</option>';
		input += '<option value="서구">서구</option>';
		return input;
	}
	
	function jeju() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value="서귀포시">서귀포시</option>';
		input += '<option value="제주시">제주시</option>';
		return input;
	}
	
	function ulsan() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value="중구">중구</option>';
		input += '<option value="남구">남구</option>';
		input += '<option value="동구">동구</option>';
		input += '<option value="북구">북구</option>';
		input += '<option value="울주구">울주구</option>';
		return input;
	}
	
	function sejong() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value="소정면">소정면</option>';
		input += '<option value="전의면">전의면</option>';
		input += '<option value="전동면">전동면</option>';
		input += '<option value="연서면">연서면</option>';
		input += '<option value="조치원읍">조치원읍</option>';
		input += '<option value="장군면">장군면</option>';
		input += '<option value="연기면">연기면</option>';
		input += '<option value="연동면">연동면</option>';
		input += '<option value="금남면">금남면</option>';
		input += '<option value="부강면">부강면</option>';
		input += '<option value="고운동">고운동</option>';
		input += '<option value="아름동">아름동</option>';
		input += '<option value="종촌동">종촌동</option>';
		input += '<option value="도담동">도담동</option>';
		input += '<option value="새롬동">새롬동</option>';
		input += '<option value="한솔동">한솔동</option>';
		input += '<option value="보람동">보람동</option>';
		return input;
	}
	
	function kangwon() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value="철원군">철원군</option>';
		input += '<option value="화천군">화천군</option>';
		input += '<option value="양구군">양구군</option>';
		input += '<option value="인제군">인제군</option>';
		input += '<option value="고성군">고성군</option>';
		input += '<option value="속초시">속초시</option>';
		input += '<option value="춘천시">춘천시</option>';
		input += '<option value="홍천군">홍천군</option>';
		input += '<option value="양양군">양양군</option>';
		input += '<option value="원주시">원주시</option>';
		input += '<option value="횡성군">횡성군</option>';
		input += '<option value="평창군">평창군</option>';
		input += '<option value="강릉시">강릉시</option>';
		input += '<option value="동해시">동해시</option>';
		input += '<option value="정선군">정선군</option>';
		input += '<option value="철원군">철원군</option>';
		input += '<option value="영월군">영월군</option>';
		input += '<option value="태백시">태백시</option>';
		input += '<option value="삼척시">삼척시</option>';
		return input;
	}
	
	function chungcheongN() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value = "청주시">청주시</option>';
		input += '<option value = "청주시 상당구">청주시 상당구</option>';
		input += '<option value = "청주시 서원구">청주시 서원구</option>';
		input += '<option value = "청주시 흥덕구">청주시 흥덕구</option>';
		input += '<option value = "청주시 청원구">청주시 청원구</option>';
		input += '<option value = "충주시">충주시</option>';
		input += '<option value = "제천시">제천시</option>';
		input += '<option value = "보은군">보은군</option>';
		input += '<option value = "옥천군">옥천군</option>';
		input += '<option value = "영동군">영동군</option>';
		input += '<option value = "진천군">진천군</option>';
		input += '<option value = "괴산군">괴산군</option>';
		input += '<option value = "음성군">음성군</option>';
		input += '<option value = "단양군">단양군</option>';
		input += '<option value = "증평군">증평군</option>';
		return input;
	}
	
	function chungcheongS() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value = "천안시">천안시</option>';
		input += '<option value = "천안시 동남구">천안시 동남구</option>';
		input += '<option value = "천안시 서북구">천안시 서북구</option>';
		input += '<option value = "공주시">공주시</option>';
		input += '<option value = "보령시">보령시</option>';
		input += '<option value = "아산시">아산시</option>';
		input += '<option value = "서산시">서산시</option>';
		input += '<option value = "논산시">논산시</option>';
		input += '<option value = "계룡시">계룡시</option>';
		input += '<option value = "당진시">당진시</option>';
		input += '<option value = "금산군">금산군</option>';
		input += '<option value = "부여군">부여군</option>';
		input += '<option value = "서천군">서천군</option>';
		input += '<option value = "청양군">청양군</option>';
		input += '<option value = "홍성군">홍성군</option>';
		input += '<option value = "예산군">예산군</option>';
		input += '<option value = "태안군">태안군</option>';
		return input;
	}
	
	function jeonbook() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value = "전주시">전주시</option>';
		input += '<option value = "전주시 완산구">전주시 완산구</option>';
		input += '<option value = "전주시 덕진구">전주시 덕진구</option>';
		input += '<option value = "군산시">군산시</option>';
		input += '<option value = "익산시">익산시</option>';
		input += '<option value = "정읍시">정읍시</option>';
		input += '<option value = "남원시">남원시</option>';
		input += '<option value = "김제시">김제시</option>';
		input += '<option value = "완주군">완주군</option>';
		input += '<option value = "진안군">진안군</option>';
		input += '<option value = "무주군">무주군</option>';
		input += '<option value = "장수군">장수군</option>';
		input += '<option value = "임실군">임실군</option>';
		input += '<option value = "순창군">순창군</option>';
		input += '<option value = "고창군">고창군</option>';
		input += '<option value = "부안군">부안군</option>';
		return input;
	}
	
	function jeonnam() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value = "목포시">목포시</option>';
		input += '<option value = "여수시">여수시</option>';
		input += '<option value = "순천시">순천시</option>';
		input += '<option value = "나주시">나주시</option>';
		input += '<option value = "광양시">광양시</option>';
		input += '<option value = "담양군">담양군</option>';
		input += '<option value = "곡성군">곡성군</option>';
		input += '<option value = "구례군">구례군</option>';
		input += '<option value = "고흥군">고흥군</option>';
		input += '<option value = "보성군">보성군</option>';
		input += '<option value = "화순군">화순군</option>';
		input += '<option value = "장흥군">장흥군</option>';
		input += '<option value = "강진군">강진군</option>';
		input += '<option value = "해남군">해남군</option>';
		input += '<option value = "영암군">영암군</option>';
		input += '<option value = "무안군">무안군</option>';
		input += '<option value = "함평군">함평군</option>';
		input += '<option value = "영광군">영광군</option>';
		input += '<option value = "장성군">장성군</option>';
		input += '<option value = "완도군">완도군</option>';
		input += '<option value = "진도군">진도군</option>';
		input += '<option value = "신안군">신안군</option>';
		return input;
	}
	
	function kyeongbook() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value = "포항시">포항시</option>';
		input += '<option value = "포항시 남구">포항시 남구</option>';
		input += '<option value = "포항시 북구">포항시 북구</option>';
		input += '<option value = "경주시">경주시</option>';
		input += '<option value = "김천시">김천시</option>';
		input += '<option value = "안동시">안동시</option>';
		input += '<option value = "구미시">구미시</option>';
		input += '<option value = "영주시">영주시</option>';
		input += '<option value = "영천시">영천시</option>';
		input += '<option value = "상주시">상주시</option>';
		input += '<option value = "문경시">문경시</option>';
		input += '<option value = "경산시">경산시</option>';
		input += '<option value = "군위군">군위군</option>';
		input += '<option value = "의성군">의성군</option>';
		input += '<option value = "청송군">청송군</option>';
		input += '<option value = "영양군">영양군</option>';
		input += '<option value = "영덕군">영덕군</option>';
		input += '<option value = "청도군">청도군</option>';
		input += '<option value = "고령군">고령군</option>';
		input += '<option value = "성주군">성주군</option>';
		input += '<option value = "칠곡군">칠곡군</option>';
		input += '<option value = "예천군">예천군</option>';
		input += '<option value = "봉화군">봉화군</option>';
		input += '<option value = "울진군">울진군</option>';
		input += '<option value = "울릉군">울릉군</option>';
		return input;
	}
	
	function kyeongnam() {
		var input = '';
		input += '<option value = "">전체</option>';
		input += '<option value = "창원시">창원시</option>';
		input += '<option value = "창원시 의창구">창원시 의창구</option>';
		input += '<option value = "창원시 성산구">창원시 성산구</option>';
		input += '<option value = "창원시 마산합포구">창원시 마산합포구</option>';
		input += '<option value = "창원시 마산회원구">창원시 마산회원구</option>';
		input += '<option value = "창원시 진해구">창원시 진해구</option>';
		input += '<option value = "진주시">진주시</option>';
		input += '<option value = "통영시">통영시</option>';
		input += '<option value = "사천시">사천시</option>';
		input += '<option value = "김해시">김해시</option>';
		input += '<option value = "밀양시">밀양시</option>';
		input += '<option value = "거제시">거제시</option>';
		input += '<option value = "양산시">양산시</option>';
		input += '<option value = "의령군">의령군</option>';
		input += '<option value = "함안군">함안군</option>';
		input += '<option value = "창녕군">창녕군</option>';
		input += '<option value = "고성군">고성군</option>';
		input += '<option value = "남해군">남해군</option>';
		input += '<option value = "하동군">하동군</option>';
		input += '<option value = "산청군">산청군</option>';
		input += '<option value = "함양군">함양군</option>';
		input += '<option value = "거창군">거창군</option>';
		input += '<option value = "합천군">합천군</option>';
		return input;
	}
});	