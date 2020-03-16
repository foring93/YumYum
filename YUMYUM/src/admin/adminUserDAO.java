package admin;

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

public class adminUserDAO {
	private DataSource ds; 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	public adminUserDAO() {
		try {

			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/yumyum");

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public JsonArray deleted_user() {
		JsonArray arr = new JsonArray();
		String sql = "SELECT * FROM USER_WITHDRAWAL_TBL";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				JsonObject object = new JsonObject();
				object.addProperty("id", rs.getString("USER_ID"));
				object.addProperty("reason", rs.getString("REASON"));
				object.addProperty("date", rs.getString("WITHDRAW_DATE"));
				arr.add(object);
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
		
		return arr;
	}
}
