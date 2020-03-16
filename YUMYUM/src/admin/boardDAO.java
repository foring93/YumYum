package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class boardDAO {
	DataSource ds;
	Connection con;
	PreparedStatement pstmt; //쿼리 실행해주는 객체
	ResultSet rs;
	int result;
	
	public boardDAO(){
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/yumyum");
		}catch(Exception ex){
			System.out.println("DB 연결 실패 : " + ex);
		}
	}
	
	public int board_delete(int board_no) {
		int result = 0;
		String sql = "DELETE FROM MP_TBL WHERE MP_NO =?";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_no);
			result = pstmt.executeUpdate();
			if(result ==1) {
				System.out.println("맛집 게시물 삭제 성공");
			}
		}catch (SQLException e) {
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
	            if (con != null) {
	               con.close();
	               con = null;
	            }

	         } catch (SQLException e) {
	            e.printStackTrace();
	         }

	      }//finally end
		return result;
	}
	public int board_modify(mpVO board) {
		
		String sql = "UPDATE MP_TBL SET MP_NAME=?, MP_ADDRESS=?, MP_PHONE=?, "
						+"MP_KIND=?, MP_INFO=?, MP_HOURS=?, MP_HASH=?,MP_IMG_URL=? WHERE MP_NO=?";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getMP_NAME());
			pstmt.setString(2, board.getMP_ADDRESS());
			pstmt.setString(3, board.getMP_PHONE());
			pstmt.setString(4, board.getMP_KIND());
			pstmt.setString(5, board.getMP_INFO());
			pstmt.setString(6, board.getMP_HOURS());
			pstmt.setString(7, board.getMP_HASH());
			pstmt.setString(8, board.getMP_IMG_URL());
			pstmt.setInt(9, board.getMP_NO());
			result = pstmt.executeUpdate();
			if(result==1)
				System.out.println("맛집 게시물 수정 성공");
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
	            if (con != null) {
	               con.close();
	               con = null;
	            }

	         } catch (SQLException e) {
	            e.printStackTrace();
	         }

	      }//finally end
		return result;
	}
	public mpVO m_info(int board_no) {
		mpVO board = new mpVO();
		String sql = "SELECT * FROM MP_TBL WHERE MP_NO=?";
		try {
			con= ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board.setMP_NO(rs.getInt("MP_NO"));
				board.setMP_WRITER(rs.getString("MP_WRITER"));
				board.setMP_NAME(rs.getString("MP_NAME"));
				board.setMP_ADDRESS(rs.getString("MP_ADDRESS"));
				board.setMP_PHONE(rs.getString("MP_PHONE"));
				board.setMP_KIND(rs.getString("MP_KIND"));
				board.setMP_INFO(rs.getString("MP_INFO"));
				board.setMP_HOURS(rs.getString("MP_HOURS"));
				board.setMP_RE_CNT(rs.getInt("MP_RE_CNT"));
				board.setMP_VIEW_CNT(rs.getInt("MP_VIEW_CNT"));
				board.setMP_RE_GRADE(rs.getInt("MP_RE_GRADE"));
				board.setMP_AVG_GRADE(rs.getInt("MP_AVG_GRADE"));
				board.setMP_HASH(rs.getString("MP_HASH"));
				board.setMP_REGIDATE(rs.getDate("MP_REGIDATE"));
				board.setMP_IMG_URL(rs.getString("MP_IMG_URL"));
				board.setMP_ADD_IMG_URL(rs.getString("MP_ADD_IMG_URL"));
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
	            if (con != null) {
	               con.close();
	               con = null;
	            }

	         } catch (SQLException e) {
	            e.printStackTrace();
	         }

	      }//finally end
		return board;
	}
	
}
