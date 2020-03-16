<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>    
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(function(){
	var imgs;
	var img_count;
	var img_position = 1;
	
	imgs=$(".slide ul");
	imgs2=$(".slide ul a")
	img_count=4;

	//버튼을 클릭했을때 함수 실행
	$('#back').click(function(){
		back();
	});
	
	$('#next').click(function(){
		next();
	});
	
	function back(){
		if(1<img_position){
			imgs.animate({left:"+=1000px"});
			
			img_position--;
		}
		
	}
	
	function next(){
	if(img_count>img_position){
			imgs.animate({
				left:'-=1000px'
			});
			img_position++;
			
		
	}else{
		 
		imgs.animate({
		left:'+=3000px'
		});
		img_position=1;
		}
	console.log(img_position);
	}
	
	/* setInterval(function nextto(){
		var i=$('.de_width').first();
		$('li').last().after('<li>'+$('.de_width').first()+'</li>');
	},2000); */
	//이미지 슬라이드 자동
	var start=setInterval(next,4000);
	//이미지에 마우스 오버시 슬라이드 멈춤
	$(".de_a").hover(function(){
		clearInterval(start);
		
	},function(){
		start=setInterval(next,4000);
		
	});
	//화살표버튼에 마우스 오버시 슬라이드멈춤
	$("#next").hover(function(){
		clearInterval(start);
	},function(){
		start=setInterval(next,4000);
	});
	
	$("#back").hover(function(){
		clearInterval(start);
	},function(){
		start=setInterval(next,4000);
	});
	var title=$(".de_a").attr("href");
    $(".de_width").attr("title",title);
   
});
</script>
<style>
*{margin:0;}
@font-face { font-family: 'GoodFont'; src: url('../font/Typo_Buamdong2019M.ttf'); }

.font{font-family:GoodFont}
.de_width{width:600px;height:350px;z-index: -1; opacity: 0.8;}
#back{z-index:1;width:50px;height:50px;position:absolute;top:150px;left:0;}
#next{z-index:1;width:50px;height:50px;position:absolute;top:150px;right:0;}
.slide{
background:#494644;
width:1000px;
height:350px;
overflow: hidden;
position:relative;
left:0px;
/* left:140px;
top:50px; */

}
.slide ul{
width:4100px;
position:absolute;
top:0px;
left:0;
list-style: none;
}
.slide ul li{
	background:#494644;
	display:block;
	float:left;
}
#de_sub{text-align: center; width:1000px ; margin-top:10px;
		}
.slide a:hover  {opacity: 0.5}
#de_h3{color:white;font-family:GoodFont;
		z-index:2;text-align: left;position:relative;font-size:30pt;top:295px;} 
.de_a li:last-child {
	width:400px;
	height:400px;
	padding:70px;
	box-sizing: border-box;
	font-family: GoodFont;color:white;
}		
</style>
</head>
<body>

<c:if test="${best4[0].img!=null}">
<div class="slide">
<h3 id="de_h3"> # BEST 5 </h3>
	<img id="back"src="../img/back1.png">
	<ul style="padding:0;">
		
		<a class="de_a" href="" >
			<li><img class="de_width" src="../img/${best4[0].img}"></li>
			<li><h1>#${best4[0].name}</h1><h1>#${best4[0].adress}맛집</h1><br><p></p></li>
		</a>
		<c:if test="${best4[1].img!=null}">
		<a class="de_a" href="" >
			<li><img class="de_width" src="../img/${best4[1].img}"></li>
			<li><h1>#${best4[1].name}</h1><h1>#${best4[1].adress}맛집</h1><br><p></p></li>
		</a>
		</c:if>
		<c:if test="${best4[2].img!=null}">
		<a class="de_a" href="" >
			<li><img class="de_width" src="../img/${best4[2].img}"></li>
			<li><h1>#${best4[2].name}</h1><h1>#${best4[2].adress}맛집</h1><br><p></p></li>
		</a>
		</c:if>
		<c:if test="${best4[3].img!=null}">
		<a class="de_a" href="" >
			<li><a class="de_a" href="" ><img class="de_width" src="../img/${best4[3].img}"></li>
			<li><h1>#${best4[3].name}</h1><h1>#${best4[3].adress}맛집</h1><br><p></p></li>
		</a>
		</c:if>
		<h1 style="line-height:300px;font-family:GoodFont">맛집을 등록해주세요</h1>
	</ul>
	<img id="next"src="../img/next.png">
</div>
</c:if>
<c:if test="${best4[0].img==null}">
<div class="slide">
<h1 style="line-height:300px;font-family:GoodFont">맛집을 등록해주세요</h1>
</div>
</c:if>
