package passwordsearch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class passsearchDAO {
	private DataSource ds; 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	public passsearchDAO() {
		try {

			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/yumyum");

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public String passwordsearch(String name,String id, String phone) {
		System.out.println("passwordsearch 메서드에서 찍은 name = "+name);
		System.out.println(name);
		String pass="";
		String sql = "select USER_PASS from USER_TBL where USER_ID=? and USER_NAME=? and USER_PHONE=?";
		try {
			conn= ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, phone);
			rs= pstmt.executeQuery();
			if(rs.next()) {
				pass=rs.getString("USER_PASS");
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
		return pass;
	}

}
