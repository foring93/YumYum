package join;
import java.sql.Date;

public class Member {
	private String USER_ID; 
	private String USER_PASS; 
	private String USER_NAME; 
	private String USER_GENDER;
	private String USER_NICKNAME;
	private String USER_BIRTHDAY;
	private String USER_ADDRESS;
	private String USER_POSTCODE;
	private String USER_PHONE;
	private String USER_EMAIL;
	private int USER_IS_OWNER;
	private String USER_BUSINESS_NO;
	private Date USER_REGIDATE;
	
	public int getUSER_IS_OWNER() {
		return USER_IS_OWNER;
	}
	public void setUSER_IS_OWNER(int uSER_IS_OWNER) {
		USER_IS_OWNER = uSER_IS_OWNER;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getUSER_PASS() {
		return USER_PASS;
	}
	public void setUSER_PASS(String uSER_PASS) {
		USER_PASS = uSER_PASS;
	}
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	public String getUSER_GENDER() {
		return USER_GENDER;
	}
	public void setUSER_GENDER(String uSER_GENDER) {
		USER_GENDER = uSER_GENDER;
	}
	public String getUSER_NICKNAME() {
		return USER_NICKNAME;
	}
	public void setUSER_NICKNAME(String uSER_NICKNAME) {
		USER_NICKNAME = uSER_NICKNAME;
	}
	public String getUSER_BIRTHDAY() {
		return USER_BIRTHDAY;
	}
	public void setUSER_BIRTHDAY(String uSER_BIRTHDAY) {
		USER_BIRTHDAY = uSER_BIRTHDAY;
	}
	public String getUSER_ADDRESS() {
		return USER_ADDRESS;
	}
	public void setUSER_ADDRESS(String uSER_ADDRESS) {
		USER_ADDRESS = uSER_ADDRESS;
	}
	public String getUSER_POSTCODE() {
		return USER_POSTCODE;
	}
	public void setUSER_POSTCODE(String uSER_POSTCODE) {
		USER_POSTCODE = uSER_POSTCODE;
	}
	public String getUSER_PHONE() {
		return USER_PHONE;
	}
	public void setUSER_PHONE(String uSER_PHONE) {
		USER_PHONE = uSER_PHONE;
	}
	public String getUSER_EMAIL() {
		return USER_EMAIL;
	}
	public void setUSER_EMAIL(String uSER_EMAIL) {
		USER_EMAIL = uSER_EMAIL;
	}
	public String getUSER_BUSINESS_NO() {
		return USER_BUSINESS_NO;
	}
	public void setUSER_BUSINESS_NO(String uSER_BUSINESS_NO) {
		USER_BUSINESS_NO = uSER_BUSINESS_NO;
	}
	public Date getUSER_REGIDATE() {
		return USER_REGIDATE;
	}
	public void setUSER_REGIDATE(Date uSER_REGIDATE) {
		USER_REGIDATE = uSER_REGIDATE;
	}
}
