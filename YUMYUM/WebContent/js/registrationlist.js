$(function(){
	// 체크박스 선택시 요청철회 실행되는 이벤트
	// '요청철회' 버튼 클릭 시 
	$(".btn-cancel").click(function(){
		// data 에 채크박스 name이 mpa_cancel인 것 중 checked된 것만 저장합니다.
		var data = $("input:checkbox[name='mpa_cancel']:checked");
		alert(data.length);
		var datas = "";
		
		//체크된것의 value를 datas에 String 형으로 저장합니다.
		//delete from table where -- in(---)에 사용하려고 ,를 붙여서 저장했습니다.
		for(var i = 0; i < data.length; i++){
			if(i < data.length-1)
				datas += data[i].value+",";
			else
				datas += data[i].value;	
		}
		if(data.length!=0){
		//에이젝스 실행
		 $.ajax({
			url : "../owner/owner",
			data : "page=Mpa_cancel&MP_NO="+datas,
			type : "get",
			dataType : "json",
			success : function(data) {
				$("tbody").empty();
				var out = ""
				$(data).each(function(index, item) {
					var str = item.MPA_REGIDATE.split(' ');
						out = ""
						out += "<tr>";
					//승인현황에 따라 체크박스/철회불가 생성
					if (item.MPA_IS_APPROVED == 2 || item.MPA_IS_APPROVED == 0){	// 승인 대기 중이거나 반려됐을 경우 
						out += "	<td><input class = 'mpa_cancel' type = 'checkbox' name = 'mpa_cancel' value = '${m.MP_NO}'></td>";
					} else {	// 승인 완료 됐을 시 
						out += "	<td><input type = 'checkbox' disabled = disabled></td>";
					}
						out += "	<td>"+item.MPA_NO+"</td>";
						out += "	<td>"+item.MPA_NAME+"</td>";
						out += "	<td>"+str[0]+"</td>";
					
					//승인현황에 따라 승인상태 표시 
					if (item.MPA_IS_APPROVED == 1) {	// 요청이 승인 됐을 때
						out += "	<td><span style = 'color : #90A8B8'>승인 완료</span></td>";
						out += "	<td><a href = '' class = 'ask_modify'>";
						out += "<button type = 'button' class = 'ask_btn'>문의</button></a></td>";
						out += "</tr>";
					} else if(item.MPA_IS_APPROVED == 0) {	// 요청에 대한 승인이 대기 중일 때 
						out+="	<td><span style = 'color : #C2AC91'>승인 대기 중</span></td>";
						out+="	<td><a href='owner?page=Mpa_modify&MP_NO=${m.MP_NO}'>";
						out+="		<button type = 'button' class = 'reg_modify'>수정</button></a></td>";
						out+="</tr>";
					} else {	// 요청이 반려됐을 때
						out+="	<td><span style='color:#CC4F43;'>반려</span></td>";
						out+="	<td><a href='owner?page=Mpa_modify&MP_NO=${m.MP_NO}'>";
						out+="		<button type = 'button' class = 'reg_modify>수정</button></a></td>";
						out+="</tr>";
						//반려되었을 경우 반려사유 표시
						out += "<tr id = 'reject_why'>";
						out += "	<td colspan = '7' class = 'reject_reason'> 반려 사유 : '${m.MPA_REJECT_WHY}'</td>";
						our += "</tr>";
					}
					$("tbody").append(out);
				});//each end 
			}//success end 
		})//ajax end 
		}
	});
})