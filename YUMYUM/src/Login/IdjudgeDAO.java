package Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class IdjudgeDAO {
	private DataSource ds; 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	public IdjudgeDAO() {
		try {

			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/yumyum");

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	//id가 존재하는 아이디인지 확인 
	public boolean idsearch(String id, String password) throws SQLException {
		boolean result=false;//id가 존재하면 true 존재하지 않으면 false를 반환
		String sql = "select USER_ID from USER_TBL where USER_ID = ? and USER_PASS =?";
		try {
			conn= ds.getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result=true;
			}else {
				result=false;
			}
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
		System.out.println("DAO에서 찍은"+result);
		return result;
	}
	//user_no, user_nickname, USER_IS_ADMIN, USER_IS_ADMIN, USER_POINT, USER_IS_OWNER session에 넣기 위해 만든 메소드
	public List Userinfo(String id) {
		List list = new ArrayList();
		String sql = "SELECT USER_NO,USER_NICKNAME, USER_IS_ADMIN, USER_POINT, USER_IS_OWNER FROM USER_TBL WHERE USER_ID =?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list.add(rs.getInt("USER_NO"));
				list.add(rs.getString("USER_NICKNAME"));
				list.add(rs.getInt("USER_IS_ADMIN"));
				list.add(rs.getInt("USER_POINT"));
				list.add(rs.getInt("USER_IS_OWNER"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		return list;
	}
}
