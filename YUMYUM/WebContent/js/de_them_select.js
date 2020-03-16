$(function(){
	var text = $('.table-form  p').text();
	console.log(text)
	$('.td1').click(function(){
		var num1= $('#TH_1').val();
		
		var p1=prompt("수정할인덱스:"+num1+" \n수정할 테마이름을 입력하세요");
		if(text.match(p1)){
			alert("이미등록된 테마입니다. 삭제후 다시 시도해주십시오");
			return;
		}
		if(p1){
			location.href="../Main/theme?TH_NO="+num1+"&change="+p1;
		}
		
	});
	$('.td2').click(function(){
		var num2= $('#TH_2').val();
		var check1=$('.td1 img:not(img[src|=gif])').attr('src');
		console.log(check1);
		if(check1=="../img/noimage.gif"){
			alert("인덱스1번을 먼저 추가해주세요.");
			return;
		}
			
			
			var p2=prompt("수정할인덱스:"+num2+" \n수정할 테마이름을 입력하세요");
			if(text.match(p2)){
				alert("이미등록된 테마입니다. 삭제후 다시 시도해주십시오");
				return;
			}
			if(p2){
				location.href="../Main/theme?TH_NO="+num2+"&change="+p2;
			}
			
		
		
	});
	$('.td3').click(function(){
		var num3= $('#TH_3').val();
		var check1=$('.td2 img:not(img[src|=gif])').attr('src');
		if(check1=="../img/noimage.gif"){
			alert("인덱스2번을 먼저 추가해주세요.");
			return;
		}
		var p3=prompt("수정할인덱스:"+num3+" \n수정할 테마이름을 입력하세요");
		if(text.match(p3)){
			alert("이미등록된 테마입니다. 삭제후 다시 시도해주십시오");
			return;
		}
		if(p3){
			location.href="../Main/theme?TH_NO="+num3+"&change="+p3;
		}
		
	});
	$('.td4').click(function(){
		var num4= $('#TH_4').val();
		var check1=$('.td3 img').attr('src');
		console.log(check1);
		if(check1=="../img/noimage.gif"){
			alert("인덱스3번을 먼저 추가해주세요.");
			return;
		}
		var p4=prompt("수정할인덱스:"+num4+" \n수정할 테마이름을 입력하세요");
		if(text.match(p4)){
			alert("이미등록된 테마입니다. 삭제후 다시 시도해주십시오");
			return;
		}
		if(p4){
			location.href="../Main/theme?TH_NO="+num4+"&change="+p4;
		}
		
	});
	$('.td5').click(function(){
		var num5= $('#TH_5').val();
		var check1=$('.td4 img').attr('src');
		console.log(check1);
		if(check1=="../img/noimage.gif"){
			alert("인덱스4번을 먼저 추가해주세요.");
			return;
		}
		var p5=prompt("수정할인덱스:"+num5+" \n수정할 테마이름을 입력하세요");
		if(text.match(p5)){
			alert("이미등록된 테마입니다. 삭제후 다시 시도해주십시오");
			return;
		}
		if(p5){
			location.href="../Main/theme?TH_NO="+num5+"&change="+p5;
		}
		
	});
	$('.td6').click(function(){
		var num6= $('#TH_1').val();
		var check1=$('.td5 img').attr('src');
		console.log(check1);
		if(check1=="../img/noimage.gif"){
			alert("인덱스3번을 먼저 추가해주세요.");
			return;
		}
		var p6=prompt("수정할인덱스:"+num6+" \n수정할 테마이름을 입력하세요");
		if(text.match(p6)){
			alert("이미등록된 테마입니다. 삭제후 다시 시도해주십시오");
			return;
		}
		if(p6){
			location.href="../Main/theme?TH_NO="+num6+"&change="+p6;
		}
		
	});
	
	
})