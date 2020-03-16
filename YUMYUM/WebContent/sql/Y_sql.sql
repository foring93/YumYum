-- 유저 생성 및 권한 부여 --
CONN SYS/1234 AS SYSDBA
DROP USER YUMYUM CASCADE;
CREATE USER YUMYUM IDENTIFIED BY YUM1234;
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE, CREATE TRIGGER,
CREATE SYNONYM
TO YUMYUM;

ALTER USER YUMYUM
QUOTA UNLIMITED ON SYSTEM;
CONN YUMYUM/YUM1234

-- 회원테이블 생성 --
CREATE TABLE USER_TBL
( USER_NO NUMBER PRIMARY KEY,
USER_ID VARCHAR2(40) UNIQUE,
USER_PASS VARCHAR2(40) NOT NULL,
USER_NAME VARCHAR2(40) NOT NULL,
USER_GENDER VARCHAR2(2) CHECK ( USER_GENDER IN('M', 'F') ) NOT NULL,
USER_NICKNAME VARCHAR2(40) UNIQUE,
USER_BIRTHDAY VARCHAR2(100) NOT NULL,
USER_ADDRESS VARCHAR2(200) NOT NULL,
USER_POSTCODE VARCHAR2(100) NOT NULL,
USER_PHONE VARCHAR2(40) UNIQUE,
USER_EMAIL VARCHAR2(50) NOT NULL,
USER_IS_ADMIN NUMBER(1) DEFAULT 0 NOT NULL,
USER_IS_OWNER NUMBER(1) DEFAULT 0 NOT NULL,
USER_BUSINESS_NO VARCHAR2(100),
USER_REGIDATE DATE DEFAULT SYSDATE NOT NULL,
USER_POINT NUMBER DEFAULT 0
);
CREATE SEQUENCE USER_SEQ;

-- 탈퇴계정 및 운영자 계정 생성 --
INSERT INTO USER_TBL
 (USER_NO, USER_ID, USER_PASS, USER_NAME, USER_GENDER, USER_NICKNAME, 
  USER_BIRTHDAY, USER_ADDRESS, USER_POSTCODE, USER_EMAIL)
 VALUES(0, 'DELETED', 'DELETED', '탈퇴한유저', 'M', '탈퇴한유저', '-', '-', '-', '-' );
INSERT INTO USER_TBL
 (USER_NO, USER_ID, USER_PASS, USER_NAME, USER_GENDER, USER_NICKNAME, 
  USER_BIRTHDAY, USER_ADDRESS, USER_POSTCODE, USER_EMAIL, USER_IS_ADMIN)
 VALUES(USER_SEQ.NEXTVAL, 'ADMIN', 'ADMIN1234', '운영자', 'M', '운영자', '-', '-', '-', '-', 1 );
INSERT INTO USER_TBL
 (USER_NO, USER_ID, USER_PASS, USER_NAME, USER_GENDER, USER_NICKNAME, 
  USER_BIRTHDAY, USER_ADDRESS, USER_POSTCODE, USER_EMAIL, USER_IS_ADMIN)
 VALUES(USER_SEQ.NEXTVAL, 'admin', 'admin1234', '관리자', 'M', '관리자', '-', '-', '-', '-', 1 );

 
-- 맛집 게시물 정보를 담을 테이블 생성 --
CREATE TABLE MP_TBL
( MP_NO NUMBER PRIMARY KEY,
MP_WRITER VARCHAR2(40) NOT NULL,
MP_NAME VARCHAR2(40) UNIQUE,
MP_ADDRESS VARCHAR2(100) NOT NULL,
MP_PHONE VARCHAR2(40) NOT NULL,
MP_KIND VARCHAR2(200) NOT NULL,
MP_INFO VARCHAR2(1000) NOT NULL,
MP_HOURS VARCHAR2(200) NOT NULL,
MP_RE_CNT NUMBER DEFAULT 0,
MP_VIEW_CNT NUMBER DEFAULT 0,
MP_RE_GRADE NUMBER,
MP_AVG_GRADE NUMBER(4,2) DEFAULT 5,
MP_HASH VARCHAR2(3000),
MP_REGIDATE DATE DEFAULT SYSDATE NOT NULL,
MP_IMG_URL VARCHAR2(1000) NOT NULL,
MP_ADD_IMG_URL VARCHAR2(2000)
);

-- 관리자 인증 전 맛집 게시물 정보를 담을 테이블 생성 --
CREATE TABLE MP_APPROVAL_NEED_TBL
( MPA_NO NUMBER PRIMARY KEY,
MPA_WRITER VARCHAR2(40) NOT NULL,
MPA_NAME VARCHAR2(40) NOT NULL,
MPA_ADDRESS VARCHAR2(100) NOT NULL,
MPA_PHONE VARCHAR2(1000) NOT NULL,
MPA_KIND VARCHAR2(40) NOT NULL,
MPA_INFO VARCHAR2(1000) NOT NULL,
MPA_HOURS VARCHAR2(200) NOT NULL,
MPA_HASH VARCHAR2(1000),
MPA_REGIDATE DATE DEFAULT SYSDATE NOT NULL,
MPA_IMG_URL VARCHAR2(3000) NOT NULL,
MPA_ADD_IMG_URL VARCHAR2(2000),
MPA_IS_APPROVED NUMBER(1) DEFAULT 0 CHECK ( MPA_IS_APPROVED IN ( 0, 1, 2 ) ), 
MPA_REJECT_WHY VARCHAR2(2000)
);

-- 찜목록을 담을 테이블 생성 --
CREATE TABLE MY_TBL
( MY_USER_NO NUMBER REFERENCES USER_TBL(USER_NO) ON DELETE CASCADE,
MY_MP_NO NUMBER REFERENCES MP_TBL(MP_NO) ON DELETE CASCADE
);



-- 리뷰테이블 생성 --
CREATE TABLE REVIEW_TBL
( RE_NO NUMBER PRIMARY KEY,
RE_WRITER VARCHAR2(40) NOT NULL,
RE_REF_NO NUMBER REFERENCES MP_TBL(MP_NO) ON DELETE CASCADE,
RE_REF_NAME VARCHAR2(40),
RE_CONTENT VARCHAR2(4000) NOT NULL,
RE_GRADE NUMBER CHECK ( RE_GRADE IN ( 1, 2, 3, 4, 5 ) ) NOT NULL,
RE_RECOMMEND_CNT NUMBER DEFAULT 0,
RE_REGIDATE DATE DEFAULT SYSDATE NOT NULL,
RE_IMG_URL VARCHAR2(1000)
);
CREATE SEQUENCE REVIEW_SEQ;

-- 리뷰 추천테이블 생성 --
CREATE TABLE RE_RECOMMEND_TBL
( RECOMMENDER_NO NUMBER REFERENCES USER_TBL(USER_NO) ON DELETE CASCADE,
RECOMMENDED_NO NUMBER REFERENCES REVIEW_TBL(RE_NO) ON DELETE CASCADE
);

-- 검색 수 카운트를 위한 테이블 생성 --
CREATE TABLE SEARCH_TBL
( SEARCH_KEYWORD VARCHAR2(20) PRIMARY KEY,
SEARCH_CNT NUMBER DEFAULT 1
);

-- 공지 / 이벤트 게시물을 담을 테이블 생성 --
CREATE TABLE BOARD_TBL
( BOARD_NO NUMBER PRIMARY KEY,
BOARD_CATEGORY NUMBER(1) CHECK ( BOARD_CATEGORY IN(0, 1) ) NOT NULL,
BOARD_WRITER VARCHAR2(40) NOT NULL,
BOARD_TITLE VARCHAR2(100) NOT NULL,
BOARD_CONTENT VARCHAR2(4000) NOT NULL,
BOARD_REPLY_CNT NUMBER DEFAULT 0 NOT NULL,
BOARD_VIEW_CNT NUMBER DEFAULT 0 NOT NULL,
BOARD_REGIDATE DATE DEFAULT SYSDATE NOT NULL,
BOARD_EXPIRATE DATE,
BOARD_IS_ALL_TIME NUMBER(1) DEFAULT 0 NOT NULL
);

CREATE SEQUENCE BOARD_SEQ;


-- 댓글 테이블 생성 --
CREATE TABLE REPLY_TBL
( REPLY_NO NUMBER PRIMARY KEY,
REPLY_WRITER VARCHAR2(40) NOT NULL,
REPLY_CONTENT VARCHAR2(1000) NOT NULL,
REPLY_REF NUMBER REFERENCES BOARD_TBL(BOARD_NO) ON DELETE CASCADE,
REPLY_LEVEL NUMBER DEFAULT 0,
REPLY_SEQ NUMBER NOT NULL,
REPLY_REGIDATE DATE DEFAULT SYSDATE
);
CREATE SEQUENCE REPLY_SEQ;


-- 테마 테이블 생성 --
CREATE TABLE THEME_TBL
( TH_NO NUMBER PRIMARY KEY,
TH_IMG_URL VARCHAR2(1000) NOT NULL,
TH_TITLE VARCHAR2(1000) NOT NULL,
TH_HASH VARCHAR2(2000) NOT NULL
);
CREATE SEQUENCE THEME_SEQ;


-- 탈퇴 회원 목록 테이블 생성 --
CREATE TABLE USER_WITHDRAWAL_TBL
(
	USER_ID VARCHAR2(40) NOT NULL,
	REASON VARCHAR2(300) NOT NULL,
	WITHDRAW_DATE DATE  DEFAULT SYSDATE NOT NULL
);