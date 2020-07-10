# YumYum
맛집에 대한 정보를 제공하는 웹 페이지로
회원들이 직접 작성한 리뷰와 평점을 통해 신뢰도 높은 베스트 맛집을 선정한다.  

## 기여한 역할
- 신규순 맛집 정보 제공 
- 베스트순 맛집 정보 제공
- 전체 맛집 정보 제공
- 테마별 맛집 정보 제공
- 검색을 통한 맛집 정보 제공
- 점주의 식당 등록 및 등록 요청 현황 페이지 UI 제작

### #신규순 맛집 정보 제공

![1](https://user-images.githubusercontent.com/44693915/87138457-68ca4680-c2d9-11ea-8e94-ca3f201e586d.png)

상단의 '신규 식당' 메뉴 클릭 시 ajax를 통해
관리자에 의해 등록된 순서대로 한 페이지당 최대 여섯 개의 맛집 정보 데이터를 로드한다.


### #베스트순 맛집 정보 제공

![screencapture-localhost-8088-YUMYUM-M-category-best-2020-07-10-19_02_59](https://user-images.githubusercontent.com/44693915/87142908-3708ae00-c2e0-11ea-9140-cd445fd1552c.png)

상단의 '베스트 맛집' 메뉴 클릭 시 ajax를 통해
유저들에 매겨진 평점 순으로 여섯 개의 맛집 정보 데이터만 로드한다.


### #전체 맛집 정보 제공

![screencapture-localhost-8088-YUMYUM-M-category-all-2020-07-10-19_06_41](https://user-images.githubusercontent.com/44693915/87143180-a383ad00-c2e0-11ea-9423-3eed30eab643.png)

상단의 '전체 식당' 메뉴 클릭 시 ajax를 통해 가나다 순으로 모든 식당 정보 데이터를 로드한다.


### #테마별 맛집 정보 제공



### #검색을 통한 맛집 정보 제공

**1. 전체 식당 페이지에서의 검색**

![image](https://user-images.githubusercontent.com/44693915/87144411-ae3f4180-c2e2-11ea-916f-993bd80123db.png)

![image](https://user-images.githubusercontent.com/44693915/87144523-d4fd7800-c2e2-11ea-82ac-7f2a6c595209.png)

![image](https://user-images.githubusercontent.com/44693915/87144710-29a0f300-c2e3-11ea-9021-97d955ac12f9.png)

![image](https://user-images.githubusercontent.com/44693915/87144941-800e3180-c2e3-11ea-97af-28ee29927a8f.png)

검색어 입력 시 ajax를 통해 음식점의 이름, 정보, 해시태그, 음식 종류, 주소에서 일치하는 정보들을 페이지 이동없이 로드한다.


**2. 메인 화면에서의 검색**

![Inkedscreencapture-localhost-8088-YUMYUM-Main-index-2020-07-10-19_29_48_LI](https://user-images.githubusercontent.com/44693915/87145271-0591e180-c2e4-11ea-8ccd-6cdf207ee58d.jpg)

![image](https://user-images.githubusercontent.com/44693915/87145362-2c501800-c2e4-11ea-90a7-a89e355acd63.png)

검색어 입력 시 전체 페이지로 이동해 음식점의 이름, 정보, 해시태그, 음식 종류, 주소에서 일치하는 정보들을 로드한다.


### #점주의 식당 등록 및 등록 요청 현황 페이지 UI 제작

![image](https://user-images.githubusercontent.com/44693915/87137925-9ebafb00-c2d8-11ea-925c-c50820f078da.png)
<승인 요청 현황 이미지>
