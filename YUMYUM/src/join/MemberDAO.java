package join;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	DataSource ds;
	Connection con;
	PreparedStatement pstmt; //쿼리 실행해주는 객체
	ResultSet rs;
	int result;
	
	public MemberDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/yumyum");
		}catch(Exception ex){
			System.out.println("DB 연결 실패 : " + ex);
		}
	}
	
	public int insert(Member m)throws SQLException{
		try {
			con = ds.getConnection();
			
			String sql = "INSERT INTO user_tbl "
						+ "(USER_NO, USER_ID, USER_PASS, USER_NAME, USER_GENDER, "
						+ "USER_NICKNAME, USER_BIRTHDAY, USER_ADDRESS, "
						+ "USER_POSTCODE, USER_PHONE, USER_EMAIL, USER_IS_OWNER, "
						+ "USER_BUSINESS_NO, USER_REGIDATE) "
						+ "VALUES(USER_SEQ.NEXTVAL,?,?,?,?,"
						+ "		?,?,?,"
						+ "		?,?,?,?,"
						+ "		?,sysdate)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getUSER_ID());
			pstmt.setString(2, m.getUSER_PASS());
			pstmt.setString(3, m.getUSER_NAME());
			pstmt.setString(4, m.getUSER_GENDER());
			pstmt.setString(5, m.getUSER_NICKNAME());
			pstmt.setString(6, m.getUSER_BIRTHDAY());
			pstmt.setString(7, m.getUSER_ADDRESS());
			pstmt.setString(8, m.getUSER_POSTCODE());			
			pstmt.setString(9, m.getUSER_PHONE());			
			pstmt.setString(10, m.getUSER_EMAIL());			
			pstmt.setInt(11, m.getUSER_IS_OWNER());		
			pstmt.setString(12, m.getUSER_BUSINESS_NO());	
						
			result=pstmt.executeUpdate();
		}finally {
			close();
		}//finally end
		return result;
	}//insert end
	
	private void close() {

		try {

			if (rs != null) {
				rs.close();
				rs = null;
			}

			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}

			if (con != null) {
				con.close();
				con = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* close() */
	
	public int isId(String id){
		try {
			con = ds.getConnection();
			System.out.println("getConnection");
			
			String sql = "select user_id from user_tbl where user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = 0;
			}else {
				result = -1;
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return result;
	}//isId
	
	public int isNick(String nick) {
		int result2 = 0;
		try {
			con = ds.getConnection();
			System.out.println("getConnection");
			
			String sql = "select user_nickname from user_tbl where user_nickname = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nick);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result2 = 0;
			}else {
				result2 = -1;
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return result2;
	}//isNick
	
	public int isPhone(String nick) {
		int result3 = 0;
		try {
			con = ds.getConnection();
			System.out.println("getConnection");
			
			String sql = "select user_phone from user_tbl where user_phone = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nick);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result3 = 0;
			}else {
				result3 = -1;
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return result3;
	}//isPhone
}
