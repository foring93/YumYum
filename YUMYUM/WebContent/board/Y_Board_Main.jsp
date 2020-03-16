<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>YUMYUM 공지게시판</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<style>
			body{
				padding-top:70px!important;
			}
			.hh_row{margin: 15px auto;}
			.hh_page{
				justify-content:center;
			}
			.hh_cursorP{cursor:pointer;}
			.hh_caption{
				caption-side:top;
				text-align:center;
				font-size:20pt;
				font-weight:bold;
				padding-left:74.36px;
			}
			.hh_caption>span{
				font-size:10pt;
				font-weight:normal;
				float:right;
				padding-top:20px;
			}
			.hh_tr{
				height:50px;
			}
			.hh_tr>th{
				vertical-align:middle;
			}
			#hh_contentSection{
				text-align:left;
			}
			#hh_menuBar{
				width: 1000px;
				left: 10%; right: 10%;
				text-align:center!important;
			}
			#hh_backB{
				display:none;
				vertical-align:0;
			}
			#hh_counter>span, #hh_counter>#hh_count{
				float:right;
				margin: 0 5px;
			}
			#hh_order>span, #hh_order>#hh_orderby{
				float:right;
				margin: 0 5px;
			}
			#hh_category>span, #hh_category>#hh_categoryby{
				float:right;
				margin: 0 5px;
			}
			#hh_searchBar{
				height: 80px; line-height: 80px;
				width:800px;
				display:inline-block;
				text-align:center!important;
			}
			#hh_searchSel{
				width: 150px;
				vertical-align:0;
			}
			#hh_search{
				float:none;
				width: 400px!important;
				display: inline-block!important;
			}
			#hh_searchButton{
				vertical-align:0;
			}
			#hh_write{
				vertical-align:0;
				margin-left: 20px;
			}
			.hh_table thead>tr{
				background:#DEDFDF;
				color: grey;
			}
			.hh_table thead>tr>th:first-child{width:100px;}
			.hh_table thead>tr>th:nth-child(2){width:100px;}
			.hh_table thead>tr>th:nth-child(3){}
			.hh_table thead>tr>th:nth-child(4){width:90px;}
			.hh_table thead>tr>th:nth-child(5){width:120px;}
			.hh_table thead>tr>th:last-child{width:80px;}
			.hh_table *{
				text-align:center;
			}
			.HH_tr:hover{background:#d2d2d2;opacity:0.6; transition: 1s; cursor:pointer;}
			.hh_btn{
				background:#ad968a;
				color:white;
				border-style:none;
				font-size:10.5pt;
				height: 37px;
				width: 86px;
				line-height:initial;
				border-radius: 0.25rem;
			}
			.hh_btn:hover{
				opacity:0.7; border-radius:15px; transition: 0.3s;
			}
			.hh_se_btn{
				background:#ad968a;
			}
			.hh_con{
				min-height:797px;
				padding-top: 13px;
			}
			.tableArea{
				width:100%;
				min-height:652px;
			}
			.no_boards{
				color:darkgrey;
				height:100px;
				font-size: 20pt;
				font-weight: bold;
				vertical-align:middle;
			}
		</style>
	</head>
	<body>
		<header>
			<jsp:include page="../Main/Y_header.jsp"/>
		</header>
		<div class="container hh_con">
			<div id="hh_counter">
				<select id="hh_count">
					<option value="10" selected>10 개 보기</option>
					<option value="50">50 개 보기</option>
					<option value="100">100 개 보기</option>
				</select>
				<span>보여줄 항목 수</span>
			</div>
			<div id="hh_category">
	    		<select id="hh_categoryby"> 
					<option value="0" selected>전체 보기</option>
					<option value="1">유효한 이벤트만 보기</option>
					<option value="2">전체 이벤트 보기</option>
					<option value="3">공지만 보기</option>
				</select>
				<span>카테고리</span>
			</div>
			<div id="hh_order">
	    		<select id="hh_orderby"> 
					<option selected value="0">최신 순</option>
					<option value="1">조회 순</option>
					<option value="2">만료일</option>
				</select>
				<span>정렬 순</span>
			</div>
			<div class="tableArea">
				<table class="table hh_table">
						<caption class="hh_caption">공지 게시판</caption>
						<thead>
							<tr class="hh_tr">
								<th>글번호</th>
								<th>카테고리</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>조회수</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="no_boards" colspan="6">아직 등록된 게시물이 없습니다</td>
							</tr>
						</tbody>
				</table>
			</div>
			<div id="hh_menuBar">
				<!-- 페이지네이션 구현 -->
				<div class="row hh_row">
					<div class="col hh_col">
						<ul class="pagination hh_page">
						</ul>
					</div>
				</div>
				<!-- 검색 기능 구현 -->
				<div id="hh_searchBar">
					<select id="hh_searchSel" class="custom-select mb-3">
						<option value="all" selected>통합검색</option>
						<option value="titlecontent">제목 + 내용</option>
					</select>&nbsp;
					<input type="search" placeholder="검색어를 입력하세요" id="hh_search" class="form-control">
					<button id = "hh_searchButton" type="button" class="hh_btn hh_se_btn">검색</button>
					<%
						if(session.getAttribute("user_is_admin")!= null)
						{
							if(session.getAttribute("user_is_admin").equals(1))
							{
					%>
					<button class='hh_btn' id="hh_write" type="button"> 공지 등록 </button>
					<%
							}
						}
					%>
					
				</div>
			</div>
		</div>
		<footer>
			<jsp:include page="../Main/Y_footer.jsp"/>
		</footer>
		<script>
			var boards = new Array();
			
			// database 에서 모든 게시글을 가져오는 fucntion
			function getBoardData(sd)
			{
				$.ajax({
					type : 'post',	// post 방식
					url : 'Y_Board_Main',	// 요청 전송 url
					data : sd,
					dataType : 'json', 	// return data의 type
					cache : false,	// cache 사용하지 않겠다.
					success : function(board_data){
						boards = board_data;
						boardCount = boards.length;
						currentPage = 1;
						showBoard();
					},	// HTTP 요청이 성공한 경우 실행
					error : function(request, status, error){
						alert('error 발생!');
					},
					complete : function(){
						
					}	// 요청의 성공유무와 상관없이 완료 될 경우
				});	// ajax end
			}
			
			var pageCount = 1;
			var currentPage = 1;
			var maxPage = 1;
			var pageBoard = new Array();
			var boardCount = 0;
			var category = "0";
			var order = "0";
			var senddata = {"category" : category, "order" : order};

			getBoardData(senddata);
			
			// tbody 를 리셋 해주는 함수
			function showBoard()
			{
				// 게시글 수 띄우기
				$('.hh_table>caption').html(function(index, html){
					return '공지 게시판' + '<span>게시글 수 : ' + boardCount + '</span>';	
				});
				$('.hh_table>tbody').empty();
				var output = '';
				if(boardCount == 0)
				{
					if(category == 0)
					{
						output += '<tr><td colspan="6">아직 등록된 게시물이 없습니다</td></tr>';
					}
					else if(category == 1)
					{
						output += '<tr><td colspan="6">현재 진행중인 이벤트가 없습니다</td></tr>';
					}
					else if(category == 2)
					{
						output += '<tr><td colspan="6">아직 등록된 이벤트가 없습니다</td></tr>';
					}
					else if(category == 3)
					{
						output += '<tr><td colspan="6">아직 등록된 공지가 없습니다</td></tr>';
					}
					$('.hh_table>tbody').html(output);
					return;
				}
				var count = Number( $('#hh_count').val() );
				var whole = boards.length;
				boardCount = whole;
				
				var currentBoard = new Array();
				
				if(whole % count != 0)
				{
					maxPage = Math.floor(whole/count) + 1;
				}
				else
				{
					maxPage = Math.floor(whole/count);
				}
				
				var k = 0;
				var j = 0;
				$.each(boards, function(i, item){
					currentBoard.push( item );
					k++;
					if(k % count == 0)
					{
						pageBoard[j] = new Array();
						var pBoard = pageBoard[j];
						$.each(currentBoard, function(index){
							pBoard[index] = this;
						});
						currentBoard.length = 0;
						j++;
					}
					else if( i == whole - 1 )
					{
						pageBoard[j] = new Array();
						var pBoard = pageBoard[j];
						$.each(currentBoard, function(index){
							pBoard[index] = this;
						});
					}
				});
				
				var uboard = pageBoard[currentPage - 1];
				for(var u = 0; u < uboard.length; u++)
				{
					output += '<tr>';
					output += '	<td>' + uboard[u].board_no + '</td>';
					output += '	<td>' + uboard[u].board_category + '</td>';
					output += '	<td>' + uboard[u].board_title + '</td>';
					output += '	<td>' + uboard[u].board_writer + '</td>';
					output += '	<td>' + uboard[u].board_regidate + '</td>';
					output += '	<td>' + uboard[u].board_view_cnt + '</td>';
					output += '</tr>';
				}
				$('.hh_table>tbody').html(output);
				$('.hh_table>tbody>tr').addClass('hh_cursorP').addClass('HH_tr').click(showContent);
				createPage();
			}
			
			// 공지 상세 페이지로 주소를 바꿔주는 함수
			function showContent()
			{
				var b_no = $(this).find('td:first').text();
				location.href= "Y_Board_Content?board_no=" + b_no;
			}
			
			
			// 카테고리 변경할시 필요한 게시물의 데이터만 가져오는 함수
			$('#hh_categoryby').on('change', categoryChange);
			function categoryChange()
			{
				category = $('#hh_categoryby').val();
				senddata = {'category' : category, 'order' : order};
				getBoardData(senddata);
			}
			// 정렬순 변경할 시 게시물을 정렬하는 함수
			$('#hh_orderby').on('change', function(){
				order = $('#hh_orderby').val();
				senddata = {'category' : category, 'order' : order};
				getBoardData(senddata);
			});
			
			
			// 페이지를 만들어주는 기능 구현
			function createPage()
			{
				var paging = "";
				for(var i = 0; i < maxPage; i++)
				{
					paging += "<li class='page-item'>";
					paging += "<a class='page-link' id='page" + (i+1) + "'>" + (i+1) + "</a></li>";
				}
				paging = "<li class='page-item'><a class='page-link' href='#'>&lt;&lt;</a></li>" + paging + "<li class='page-item'><a class='page-link' href='#'>&gt;&gt;</a></li>";
				$('.hh_page').html(paging);
				
				$.each($('.page-link'), function(index){
					$(this).click(function(){
						if(index == 0)
							currentPage = 1;
						else if( index == $('.page-link').length -1 )
							currentPage = $('.page-link').length - 2;
						else
							currentPage = index;
						showBoard();
					});
				});
			}
			
			// 검색 기능 구현
			$('#hh_search').click(function(){$('#hh_search').val('');});
			var maxSearchPage = 1;
			var currentSearchPage = 1;
			var searchBoard = new Array();
			// 검색 버튼 클릭시 발생하는 이벤트
			$('#hh_searchButton').on('click', searching);
			function searching()
			{
				var input = $.trim($('#hh_search').val());
				// 검색어를 입력 하지 않고 검색 버튼을 누를 경우 '검색어를 입력해주세요.' 출력
				if(input == "")
				{
					alert("검색어를 입력해주세요.");
					$('#hh_search').focus();
					return;
				}
				// 검색결과가 없을 경우 '검색결과가 없습니다' 출력
				searchBoard.length = 0;
				currentSearchPage = 1;
				var searchSel = $('#hh_searchSel').val();
				var searchResult = 0;
				var scount = 0;
				searchBoard[scount] = new Array();
				// '통합검색'
				if(searchSel == "all")
				{
					$.each(boards, function()
					{
						if(this.board_title.includes(input) || this.board_content.includes(input))
						{
							if(searchResult % 10 == 0 && searchResult != 0)
							{
								scount++;
								searchBoard[scount] = new Array();
							}
							searchBoard[scount].push(this);
							searchResult++;
						}
					});
				}
				// '제목 + 내용' 으로 검색
				else if(searchSel == "titlecontent")
				{
					$.each(boards, function(){
						if(this.board_title.includes(input) || this.board_content.includes(input))
						{
							if(searchResult % 10 == 0 && searchResult != 0)
							{
								scount++;
								searchBoard[scount] = new Array();
							}
							searchBoard[scount].push(this);
							searchResult++;
						}
					});
				}
				var resultMsg = $('#hh_searchSel>option:selected').text();
				resultMsg += ' 검색 결과 ... ' + searchResult + '개';
				$('.hh_table>caption').text(resultMsg);
				
				maxSearchPage = searchBoard.length;
				createSearchPage();
				showSearchBoard();
			}

			// 검색 후 10개의 게시물을 보여주는 함수
			function showSearchBoard()
			{
				var output = '';
				$('.hh_table>tbody').html('<tr><td colspan = "5">검색결과가 없습니다.</td></tr>');
				if(true)
				{
					var searchBoardLatest = new Array();
					var sbi = 0;
					searchBoardLatest[sbi] = new Array();
					for(var s = searchBoard.length - 1; s >= 0; s--)
					{
						for(var s2 = searchBoard[s].length - 1; s2 >= 0; s2--)
						{
							searchBoardLatest[sbi].push(searchBoard[s][s2]);
							if(searchBoardLatest[sbi].length == 10)
							{
								sbi++;
								searchBoardLatest[sbi] = new Array();
							}
						}
					}
					$.each(searchBoardLatest[currentSearchPage-1], function(index){
						output += '<tr>';
						output += ' <td>' + this.board_no + '</td>';
						output += ' <td>' + this.board_category + '</td>';
						output += '	<td>' + this.board_title + '</td>';
						output += '	<td>' + this.board_writer + '</td>';
						output += '	<td>' + this.board_regidate + '</td>';
						output += '	<td>' + this.board_view_cnt + '</td>';
						output += '</tr>';
						$('.hh_table>tbody').html(function(index, html){
							return output;
						});
					});
				}

				$('.hh_table>tbody>tr>td:nth-child(3)').addClass('hh_cursorP').addClass('HH_td').click(showContent);
			}
			
			// 이름 클릭시 이름으로 검색해주는 기능 구현
			function searchByName()
			{
				var clickedName = $.trim($(this).text());
				$('#hh_search').val(clickedName);
				$('#hh_searchSel').val('name').attr('selected', 'selected');
				$('#hh_searchButton').trigger('click');
			}
			
			// 검색 후 페이지가 여러개 일 때 사용 하는 페이지네이션 구현
			function createSearchPage()
			{
				var paging = "";
				for(var i = 0; i < maxSearchPage; i++)
				{
					paging += "<li class='page-item'>";
					paging += "<a class='page-link' id='page" + (i+1) + "'>" + (i+1) + "</a></li>";
				}
				paging = "<li class='page-item'><a class='page-link' href='#'>&lt;&lt;</a></li>" + paging + "<li class='page-item'><a class='page-link' href='#'>&gt;&gt;</a></li>";
				$('.hh_page').html(paging);
				
				$.each($('.page-link'), function(index){
					$(this).click(function(){
						if(index == 0)
							currentSearchPage = 1;
						else if( index == $('.page-link').length -1 )
							currentSearchPage = $('.page-link').length - 2;
						else
							currentSearchPage = index;
						showSearchBoard();
					});
				});
			}
						
			// 보여줄 항목수 변경 시 발생하는 이벤트
			$('#hh_count').on('change', show);
			function show(){
				currentPage = 1;
				showBoard();
			}
			
			// 공지등록 버튼 클릭시
			$('#hh_write').click(function(){
				location.href='Y_Board_Write';
			});
		</script>
	</body>
</html>