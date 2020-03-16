package mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import join.Member;

public class mypageinfoDAO {
	private DataSource ds; 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	public mypageinfoDAO() {
		try {

			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/yumyum");

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public Member user_info(String id) {// 유저 정보를 보여줌
		String sql = "SELECT * FROM USER_TBL WHERE USER_ID=?";
		Member member= new Member();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member.setUSER_ID(rs.getString("USER_ID"));
				member.setUSER_PASS(rs.getString("USER_PASS"));
				member.setUSER_NAME(rs.getString("USER_NAME"));
				member.setUSER_GENDER(rs.getString("USER_GENDER"));
				member.setUSER_NICKNAME(rs.getString("USER_NICKNAME"));
				member.setUSER_BIRTHDAY(rs.getString("USER_BIRTHDAY"));
				member.setUSER_ADDRESS(rs.getString("USER_ADDRESS"));
				member.setUSER_POSTCODE(rs.getString("USER_POSTCODE"));
				member.setUSER_PHONE(rs.getString("USER_PHONE"));
				member.setUSER_EMAIL(rs.getString("USER_EMAIL"));
				member.setUSER_IS_OWNER(rs.getInt("USER_IS_OWNER"));
				member.setUSER_BUSINESS_NO(rs.getString("USER_BUSINESS_NO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("mypageinfoDAO 의 user_info에서 에러남");
		}finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return member;
	}
	public int user_modify(Member m, String nickname) {//유저 정보 수정
		System.out.println("user_modify임");
		String sql1 = "UPDATE USER_TBL SET USER_PASS=?,USER_NICKNAME=?, "
										+" USER_ADDRESS=?, USER_POSTCODE=?, "
										+" USER_PHONE=?, USER_EMAIL=?, USER_IS_OWNER=?, "
										+" USER_BUSINESS_NO =? "
										+" WHERE USER_ID=?";
		
		String sql2 = "UPDATE MP_TBL SET MP_WRITER = ? where MP_WRITER=?";
		String sql3 = "UPDATE REVIEW_TBL SET RE_WRITER=? where RE_WRITER=?";
		String sql4 = "UPDATE REPLY_TBL SET REPLY_WRITER=? where REPLY_WRITER=?";
		String sql5 = "UPDATE MP_APPROVAL_NEED_TBL SET MPA_WRITER=? where MPA_WRITER=?";
		String sql6 = "UPDATE BOARD_TBL SET BOARD_WRITER=? where BOARD_WRITER=?"; 
		int result =0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, m.getUSER_PASS());
			pstmt.setString(2, m.getUSER_NICKNAME());
			pstmt.setString(3, m.getUSER_ADDRESS());
			pstmt.setString(4, m.getUSER_POSTCODE());
			pstmt.setString(5, m.getUSER_PHONE());
			pstmt.setString(6, m.getUSER_EMAIL());
			pstmt.setInt(7, m.getUSER_IS_OWNER());
			pstmt.setString(8, m.getUSER_BUSINESS_NO());
			pstmt.setString(9, m.getUSER_ID());
			result += pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, m.getUSER_NICKNAME());
			pstmt.setString(2, nickname);
			
			result += pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = conn.prepareStatement(sql3);
			pstmt.setString(1, m.getUSER_NICKNAME());
			pstmt.setString(2, nickname);
			
			result += pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = conn.prepareStatement(sql4);
			pstmt.setString(1, m.getUSER_NICKNAME());
			pstmt.setString(2, nickname);
			result += pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = conn.prepareStatement(sql5);
			pstmt.setString(1, m.getUSER_NICKNAME());
			pstmt.setString(2, nickname);
			result += pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = conn.prepareStatement(sql6);
			pstmt.setString(1, m.getUSER_NICKNAME());
			pstmt.setString(2, nickname);
			result += pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("mypageinfoDAO 의 user_modify에서 에러남");
		}finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public JsonArray myReviewList(String user_nickname) {// 내가 등록한 리뷰를 뽑아 오는 메서드
		JsonArray arr = new JsonArray();
		String sql = "SELECT * FROM REVIEW_TBL WHERE RE_WRITER = ? ORDER BY RE_REGIDATE DESC";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_nickname);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				JsonObject object = new JsonObject();
				object.addProperty("RE_REF_NAME", rs.getString("RE_REF_NAME"));//맛집 이름
				object.addProperty("RE_CONTENT", rs.getString("RE_CONTENT"));//리뷰 내용
				object.addProperty("RE_GRADE", rs.getInt("RE_GRADE"));//내가 남긴 맛집에 대한 평점
				object.addProperty("RE_RECOMMEND_CNT", rs.getInt("RE_RECOMMEND_CNT"));//리뷰에 대한 추천 수
				object.addProperty("RE_REGIDATE", rs.getDate("RE_REGIDATE").toString());//리뷰 등록일
				object.addProperty("RE_IMG_URL", rs.getString("RE_IMG_URL"));//리뷰 이미지 경로
				object.addProperty("RE_REF_NO", rs.getInt("RE_REF_NO"));//맛집 참조 번호
				object.addProperty("RE_NO", rs.getInt("RE_NO"));
				arr.add(object);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("mypageinfoDAO 의 myReviewList에서 에러남");
		}finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arr;
	}
	public int user_delete(String id, String reason, String nickname, int usernum) {// 유저 삭제
		int result=0;
		String sql1 = "DELETE FROM USER_TBL WHERE USER_ID =?";
		String sql2 = "INSERT INTO USER_WITHDRAWAL_TBL VALUES(?,?,sysdate)";
		String sql3 = "DELETE FROM REVIEW_TBL WHERE  RE_WRITER=?";
		String sql4 = "DELETE FROM MP_TBL WHERE MP_WRITER=?";
		String sql5 = "DELETE FROM MY_TBL WHERE MY_USER_NO=?";
		String sql6	= "DELETE FROM REPLY_TBL WHERE REPLY_WRITER=?";
		String sql7 = "DELETE FROM MP_APPROVAL_NEED_TBL WHERE MPA_WRITER=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, id);
			result += pstmt.executeUpdate();
			System.out.println("result1 = "+result);
			pstmt.close();
			
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, id);
			pstmt.setString(2, reason);
			result += pstmt.executeUpdate();
			System.out.println("result2 = "+result);
			pstmt.close();
			
			pstmt = conn.prepareStatement(sql3);
			pstmt.setString(1, nickname);
			result += pstmt.executeUpdate();
			System.out.println("result3 = "+result);
			pstmt.close();
			
			pstmt = conn.prepareStatement(sql4);
			pstmt.setString(1, nickname);
			result += pstmt.executeUpdate();
			System.out.println("result4 = "+result);
			pstmt.close();
			
			pstmt = conn.prepareStatement(sql5);
			pstmt.setInt(1, usernum);
			result += pstmt.executeUpdate();
			System.out.println("result5 = "+result);
			pstmt.close();
			
			pstmt = conn.prepareStatement(sql6);
			pstmt.setString(1, nickname);
			result += pstmt.executeUpdate();
			System.out.println("result6 = "+result);
			pstmt.close();
			
			pstmt = conn.prepareStatement(sql7);
			pstmt.setString(1, nickname);
			result += pstmt.executeUpdate();
			System.out.println("result7 = "+result);
			
			if(result>0)
				System.out.println("유저 삭제 성공");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("mypageinfoDAO 의 user_delete에서 에러남");
		}finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public JsonArray myRestaurantList(String data, String nickname) {//내가 등록한 맛집 정보를 뽑아오는 메서드
		JsonArray arr = new JsonArray();
		String sql = "SELECT * FROM MP_TBL WHERE MP_WRITER=? ORDER BY "+ data;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				JsonObject object = new JsonObject();
				object.addProperty("MP_NO", rs.getInt("MP_NO"));
				object.addProperty("MP_NAME", rs.getString("MP_NAME"));
				object.addProperty("MP_ADDRESS", rs.getString("MP_ADDRESS"));
				object.addProperty("MP_PHONE", rs.getString("MP_PHONE"));
				object.addProperty("MP_KIND", rs.getString("MP_KIND"));
				object.addProperty("MP_AVG_GRADE", rs.getInt("MP_AVG_GRADE"));
				object.addProperty("MP_VIEW_CNT", rs.getInt("MP_VIEW_CNT"));
				object.addProperty("MP_IMG_URL", rs.getString("MP_IMG_URL"));
				object.addProperty("MP_RE_CNT", rs.getInt("MP_RE_CNT"));
				arr.add(object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("mypageinfoDAO 의 myRestaurantList에서 에러남");
		}finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return arr;
	}
	
}
