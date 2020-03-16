
delete MP_TBL;
INSERT INTO USER_TBL VALUES (user_seq.nextval,'ccorle', 'dragonball','홍길동','F',
'광팬','19950505','서울시 중구','000-00','01011112222','dragon@gmail.com',0,0,'124587-15-467',sysdate,0);

INSERT INTO USER_TBL VALUES (user_seq.nextval,'admin2', '1234','시리','M',
'애플','19891211','서울시 마포구','150-12','01023452882','siri475@gmail.com',1, 0, '124545-22-747',sysdate,100);

INSERT INTO USER_TBL VALUES (user_seq.nextval,'forkkk', '1111','호빵맨','M',
'세균맨','19791105','서울시 서초구','450-25','01025807955','gohobbang@gmail.com',0,0,'112587-15-467',sysdate,0);
INSERT INTO USER_TBL VALUES (user_seq.nextval,'jumju1', '1234','이아은','F',
'점주','19950530','서울시 중구','001-00','01099548518','fdddf@gmail.com',0,1,'124587-15-467',sysdate,0);
select * from USER_TBL;

INSERT INTO MP_TBL  VALUES (1,'광팬', '은하수 식당', '서울시 마포구 성산동 595','010-9954-8516', '일식','인포메이션입니다.','21시','0','0','10','0','가정식',sysdate,'1','2');
INSERT INTO MP_TBL  VALUES ((select max(MP_NO)+1 from MP_TBL),'광팬', '성심당', '서울시 마포구 성산동 592','010-9954-8516', '양식','인포메이션입니다.','21시','0','0','14','0','가정식',sysdate,'2','2');
INSERT INTO MP_TBL  VALUES ((select max(MP_NO)+1 from MP_TBL),'광팬', '귤농장', '서울시 마포구 성산동 59','010-9954-8516', '디저트','인포메이션입니다.','21시','0','55','16','0','가정식',sysdate, '3','3');
INSERT INTO MP_TBL  VALUES ((select max(MP_NO)+1 from MP_TBL),'광팬', '신당동 떡볶이', '서울시 중구 퇴계로76길 50','010-9954-8516', '분식','인포메이션입니다.','21시','0','0','12','0','가정식',sysdate, '4','4');
INSERT INTO MP_TBL  VALUES ((select max(MP_NO)+1 from MP_TBL),'광팬', '닭갈비', '서울시 중랑구 웅앵동 59','010-9954-8516','한식','인포메이션입니다.','21시','0','0','33','0','가정식',sysdate,'5','5');
INSERT INTO MP_TBL  VALUES ((select max(MP_NO)+1 from MP_TBL),'광팬', '겐로쿠우동', '서울시 마포구 어울마당로 39','010-9954-8516', '일식','인포메이션입니다.','21시','0','0','3','0','가정식',sysdate, '6','6');
INSERT INTO MP_TBL  VALUES ((select max(MP_NO)+1 from MP_TBL),'광팬', '감성타코', '서울시 마포구 와우산로21길 20-11','010-9954-8516', '양식','인포메이션입니다.','21시','0','0','2','0','가정식',sysdate, '7','7');
INSERT INTO MP_TBL  VALUES ((select max(MP_NO)+1 from MP_TBL),'광팬', '더 팬케이크 에피데믹 서울', '서울시 강남구 신사동 645-24 2층','010-9954-8516', '디저트','인포메이션입니다.','21시','0','0','11','0','가정식',sysdate, '8','8');
INSERT INTO MP_TBL  VALUES ((select max(MP_NO)+1 from MP_TBL),'광팬', '시즈닝', '경북시 경주시 첨성로99번길 25-2(황남동)','010-9954-8516', '양식','인포메이션입니다.','21시','0','0','1','0','가정식',sysdate, '9','9');
INSERT INTO MP_TBL  VALUES ((select max(MP_NO)+1 from MP_TBL),'광팬', '가보정', '경기도 수원시 팔달구 장다리로 282','010-9954-8516', '육류','인포메이션입니다.','21시','0','0','18','0','가정식',sysdate, '10','10');
select * from MP_TBL;
select * from MP_APPROVAL_NEED_TBL;
select MP_IMG_URL from (select rownum r , b.* from (select * from MP_TBL order by MP_RE_GRADE desc)b) where r>=1 and r<=4;