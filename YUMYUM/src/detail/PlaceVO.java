package detail;

import java.sql.Date;

public class PlaceVO {
	private int MP_NO; // 맛집 번호
	private String MP_NAME; // 맛집 이름
	private String MP_ADDRESS; // 맛집 주소
	private String MP_KIND; // 음식 종류
	private int MP_RE_CNT; // 맛집에 대한 리뷰수
	private int MP_VIEW_CNT; // 맛집에 대한 조회수
	private int MP_RE_GRADE; // 맛집에 대한 평점
	private Date MP_REGIDATE; // 맛집 등록일
	private String MP_IMG_URL; // 이미지 경로
	private String MP_PHONE;
	private String MP_AVG_GRADE; //맛집에 대한 평점
	private String MP_HOURS; //맛집 운영시간
	private String MP_INFO; //맛집 정보
	private String MP_ADD_IMG; //추가 이미지 경로
	private String MP_ADD_IMG_URL; //추가 이미지
	
	public String getMP_ADD_IMG_URL() {
		return MP_ADD_IMG_URL;
	}

	public void setMP_ADD_IMG_URL(String MP_aDD_IMG_URL) {
		MP_ADD_IMG_URL = MP_aDD_IMG_URL;
	}

	public String getMP_ADD_IMG() {
		return MP_ADD_IMG;
	}

	public void setMP_ADD_IMG(String mP_ADD_IMG) {
		MP_ADD_IMG = mP_ADD_IMG;
	}

	public String getMP_INFO() {
		return MP_INFO;
	}

	public void setMP_INFO(String mP_INFO) {
		MP_INFO = mP_INFO;
	}

	public String getMP_HOURS() {
		return MP_HOURS;
	}

	public void setMP_HOURS(String mP_HOURS) {
		MP_HOURS = mP_HOURS;
	}

	public String getMP_HASH() {
		return MP_HASH;
	}

	public void setMP_HASH(String mP_HASH) {
		MP_HASH = mP_HASH;
	}

	private String MP_HASH; //맛집 해쉬태그

	public String getMP_AVG_GRADE() {
		return MP_AVG_GRADE;
	}

	public void setMP_AVG_GRADE(String mP_AVG_GRADE) {
		MP_AVG_GRADE = mP_AVG_GRADE;
	}

	public String getMP_PHONE() {
		return MP_PHONE;
	}

	public void setMP_PHONE(String mP_PHONE) {
		MP_PHONE = mP_PHONE;
	}

	public int getMP_NO() {
		return MP_NO;
	}

	public void setMP_NO(int mP_NO) {
		MP_NO = mP_NO;
	}

	public String getMP_NAME() {
		return MP_NAME;
	}

	public void setMP_NAME(String mP_NAME) {
		MP_NAME = mP_NAME;
	}

	public String getMP_ADDRESS() {
		return MP_ADDRESS;
	}

	public void setMP_ADDRESS(String mP_ADDRESS) {
		MP_ADDRESS = mP_ADDRESS;
	}

	public String getMP_KIND() {
		return MP_KIND;
	}

	public void setMP_KIND(String mP_KIND) {
		MP_KIND = mP_KIND;
	}

	public int getMP_RE_CNT() {
		return MP_RE_CNT;
	}

	public void setMP_RE_CNT(int mP_RE_CNT) {
		MP_RE_CNT = mP_RE_CNT;
	}

	public int getMP_VIEW_CNT() {
		return MP_VIEW_CNT;
	}

	public void setMP_VIEW_CNT(int mP_VIEW_CNT) {
		MP_VIEW_CNT = mP_VIEW_CNT;
	}

	public int getMP_RE_GRADE() {
		return MP_RE_GRADE;
	}

	public void setMP_RE_GRADE(int mP_RE_GRADE) {
		MP_RE_GRADE = mP_RE_GRADE;
	}

	public Date getMP_REGIDATE() {
		return MP_REGIDATE;
	}

	public void setMP_REGIDATE(Date mP_REGIDATE) {
		MP_REGIDATE = mP_REGIDATE;
	}

	public String getMP_IMG_URL() {
		return MP_IMG_URL;
	}

	public void setMP_IMG_URL(String mP_IMG_URL) {
		MP_IMG_URL = mP_IMG_URL;
	}

}
