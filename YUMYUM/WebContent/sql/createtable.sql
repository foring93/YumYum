-- ȸ�����̺� ���� --
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

-- Ż����� �� ��� ���� ���� --
INSERT INTO USER_TBL
 (USER_NO, USER_ID, USER_PASS, USER_NAME, USER_GENDER, USER_NICKNAME, 
  USER_BIRTHDAY, USER_ADDRESS, USER_POSTCODE, USER_EMAIL)
 VALUES(0, 'DELETED', 'DELETED', 'Ż��������', 'M', 'Ż��������', '-', '-', '-', '-' );
INSERT INTO USER_TBL
 (USER_NO, USER_ID, USER_PASS, USER_NAME, USER_GENDER, USER_NICKNAME, 
  USER_BIRTHDAY, USER_ADDRESS, USER_POSTCODE, USER_EMAIL, USER_IS_ADMIN)
 VALUES(USER_SEQ.NEXTVAL, 'ADMIN', 'ADMIN1234', '���', 'M', '���', '-', '-', '-', '-', 1 );

-- ���� �Խù� ������ ���� ���̺� ���� --

CREATE TABLE MP_TBL
( MP_NO NUMBER PRIMARY KEY,
MP_WRITER VARCHAR2(40) REFERENCES USER_TBL(USER_NICKNAME),
MP_NAME VARCHAR2(40) UNIQUE,
MP_ADDRESS VARCHAR2(100) NOT NULL,
MP_PHONE VARCHAR2(40) NOT NULL,
MP_KIND VARCHAR2(200) NOT NULL,
MP_INFO LONG NOT NULL,
MP_HOURS VARCHAR2(200) NOT NULL,
MP_RE_CNT NUMBER DEFAULT 0,
MP_VIEW_CNT NUMBER DEFAULT 0,
MP_RE_GRADE NUMBER,
MP_AVG_GRADE NUMBER DEFAULT 5,
MP_HASH VARCHAR2(1000),
MP_REGIDATE DATE DEFAULT SYSDATE NOT NULL,
MP_IMG_URL VARCHAR2(1000) NOT NULL,
MP_ADD_IMG_URL VARCHAR2(2000)
);



-- �������̺� ���� --
CREATE TABLE REVIEW_TBL
( RE_NO NUMBER PRIMARY KEY,
RE_WRITER VARCHAR2(40) REFERENCES USER_TBL(USER_NICKNAME),
RE_REF_NO NUMBER REFERENCES MP_TBL(MP_NO),
RE_REF_NAME VARCHAR2(40) REFERENCES MP_TBL(MP_NAME),
RE_TITLE VARCHAR2(100) NOT NULL,
RE_CONTENT LONG NOT NULL,
RE_GRADE NUMBER CHECK ( RE_GRADE IN ( 1, 2, 3, 4, 5 ) ) NOT NULL,
RE_RECOMMEND_CNT NUMBER DEFAULT 0,
RE_REGIDATE DATE DEFAULT SYSDATE NOT NULL,
RE_IMG_URL VARCHAR2(1000)
);
CREATE SEQUENCE REVIEW_SEQ;

-- ������ ���� ���̺� ���� --
CREATE TABLE MY_TBL
( MY_USER_NO NUMBER REFERENCES USER_TBL(USER_NO),
MY_MP_NO NUMBER REFERENCES MP_TBL(MP_NO)
);

-- ��� ���̺� ���� --
CREATE TABLE REPLY_TBL
( REPLY_NO NUMBER PRIMARY KEY,
REPLY_WRITER VARCHAR2(40) REFERENCES USER_TBL(USER_NICKNAME),
REPLY_CONTENT VARCHAR2(200) NOT NULL,
REPLY_REF NUMBER REFERENCES REVIEW_TBL(RE_NO),
REPLY_LEVEL NUMBER DEFAULT 0,
REPLY_SEQ NUMBER NOT NULL,
REPLY_REGIDATE DATE DEFAULT SYSDATE
);
CREATE SEQUENCE REPLY_SEQ;

-- �˻� �� ī��Ʈ�� ���� ���̺� ���� --
CREATE TABLE SEARCH_TBL
( SEARCH_KEYWORD VARCHAR2(20) PRIMARY KEY,
SEARCH_CNT NUMBER DEFAULT 1
);

-- ������ ���� �� ���� �Խù� ������ ���� ���̺� ���� --
drop table MP_APPROVAL_NEED_TBL;
CREATE TABLE MP_APPROVAL_NEED_TBL
(
MPA_NO NUMBER PRIMARY KEY,
MPA_WRITER VARCHAR2(40) REFERENCES USER_TBL(USER_NICKNAME),
MPA_NAME VARCHAR2(40) NOT NULL,
MPA_ADDRESS VARCHAR2(100) NOT NULL,
MPA_PHONE VARCHAR2(11) NOT NULL,
MPA_KIND VARCHAR2(40) NOT NULL,
MPA_INFO LONG NOT NULL,
MPA_HOURS VARCHAR2(200) NOT NULL,
MPA_HASH VARCHAR2(1000),
MPA_REGIDATE DATE DEFAULT SYSDATE NOT NULL,
MPA_IMG_URL VARCHAR2(1000) NOT NULL,
MPA_ADD_IMG_URL VARCHAR2(2000),
MPA_IS_APPROVED NUMBER(1) DEFAULT 0 CHECK ( MPA_IS_APPROVED IN ( 0, 1, 2 ) ) 
);


-- ���� / �̺�Ʈ �Խù��� ���� ���̺� ���� --
CREATE TABLE BOARD_TBL
( BOARD_NO NUMBER PRIMARY KEY,
BOARD_CATEGORY NUMBER(1) CHECK ( BOARD_CATEGORY IN(0, 1) ) NOT NULL,
BOARD_WRITER VARCHAR2(40) REFERENCES USER_TBL(USER_NICKNAME),
BOARD_TITLE VARCHAR2(100) NOT NULL,
BOARD_CONTENT LONG NOT NULL,
BOARD_REPLY_CNT NUMBER DEFAULT 0 NOT NULL,
BOARD_VIEW_CNT NUMBER DEFAULT 0 NOT NULL,
BOARD_REGIDATE DATE DEFAULT SYSDATE NOT NULL,
BOARD_EXPIRATE DATE,
BOARD_IS_ALL_TIME NUMBER(1) DEFAULT 0 NOT NULL
);

CREATE SEQUENCE BOARD_SEQ;
