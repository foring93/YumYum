$(function(){
		$('.yy_m_delete').click(function(){
			var input = confirm('게시물을 삭제하시겠습니까?');
			if(input == true){
				alert('게시물 삭제 성공');
			}else{
				$('.delete_m_a').prop('href','admin?page=boardlist');
				alert('게시물 삭제 취소');
			}
		});//click end
		
		$("button[name=searchbutton]").click(function(){
			var searchword=$('input[name=search]').val();
			var value=$('#value').val();
			console.log(value);
			$.ajax({
				url:"../Main/admin",
				data:{'page':'boardlist','state':'ajax','value':value,'searchword':searchword},
				type:"get",
				dataType:"json",
				success:function(data){
					$("tbody").empty();
					$(".center-block").remove();
					var out="";
					var out2="";
					var length= data.length;
					$(data).each(function(index, item) {
						if(index<length-1){
						var str=item.MP_REGIDATE;
						var date = str.split(" ");
						out+="<tr>";
						out+="	<td>"+item.MP_NO+"</td>";
						out+="	<td>"+item.MP_NAME+"</td>";
						out+="	<td>"+item.MP_AVG_GRADE+"</td>";
						out+="	<td>"+item.MP_RE_CNT+"</td>";
						out+="	<td>"+item.MP_VIEW_CNT+"</td>";
						out+="	<td>"+item.MP_WRITER+"</td>";
						out+="	<td>"+date[0]+"</td>";
						out+="<td><a href = 'admin?page=Y_board_m_info&MP_NO="+item.MP_NO+"'><button class='btn btn-primary'type='button'>수정</button></a></td>";
						out+="<td><a href ='admin?page=board_delete&MP_NO="+item.MP_NO+"' class = 'delete_m_a'><button class='btn btn-danger yy_m_delete'type='button'>삭제</button></a></td>";
						out+="</tr>";
						}
						if(index==length-1){
							out2="";
							out2+="<div class='center-block'>";
							out2+="	<div class='row'>";
							out2+="  	<div class='col'>";
							out2+="			<ul class='pagination'>";
							if(item.pagenum>item.startpage){
								out2+=" <li class='page-item'>";
								out2+=" <a class='page-link' href='admin?page=boardlist&pagenum="+item.pagenum;
								out2+="&state=ajax&value="+value+"&searchword="+searchword+"'>&lt;&nbsp;</a>";
								out2+="</li>";
							}
							if(item.pagenum==item.startpage){
								out2+="<li class='page-item'>";
								out2+="	<a class='page-link' href=''>&lt;&nbsp;</a>";
								out2+="</li>";
							}
							for(var i=item.startpage; i<=item.maxpage;i++){
								if(i==item.pagenum){
									out2+="<li class='page-item'>";
									out2+="<a class='page-link current' style='color:white'href=''>"+i+"&nbsp;</a>";
									out2+="</li>";
								}else if(i!=item.pagenum){
									out2+="<li class='page-item'>";
									out2+="<a class='page-link' href='admin?page=boardlist&pagenum="+i;
									out2+="&state=ajax&value="+value+"&searchword="+searchword+"'>"+i+"</a>";
									out2+="</li>"
								}
							}//포문
							
							if(item.pagenum<item.maxpage){
								out2+="<li class='page-item'>";
								out2+=" <a class='page-link' href='admin?page=boardlist&pagenum="+(item.pagenum+1);
								out2+="&state=ajax&value="+value+"&searchword="+searchword+"'>&gt;&nbsp;</a>";
								out2+="</li>";
							}else if(item.pagenum==item.maxpage){
								out2+="<li class='page-item'>";
								out2+=" <a class='page-link' href=''>&gt;&nbsp;</a>";
								out2+="</li>"
									
							}
							out2+="	</ul>";
							out2+="</div>";
							out2+="</div>";
							out2+="</div>";
							console.log(out2);
						}//else 문 끝
						
					});
					

					$('tbody').append(out);

					$('table').after(out2);
					
				}//success end
				,error:function(){
					alert("오류");
				}//error end
				
			})//ajax end
			
		});//button end
	});//ready end