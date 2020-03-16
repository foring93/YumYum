package board;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.*;
import javax.sql.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class Y_Board_DAO
{
	private DataSource ds; 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	public Y_Board_DAO()
	{
		try
		{
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/yumyum");
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
	}
	private void close()
	{
		try
		{
			if(rs != null) {rs.close(); rs=null;}
			if(pstmt != null) {pstmt.close(); pstmt=null;}
			if(conn != null) {conn.close(); conn=null;}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	// 새로운 공지글을 등록하는 메서드
	// insert into board_tbl
	public int writeNewPost(int category, String writer, int all_time, String title, String content, String ex_date) throws SQLException
	{
		int result = 0;
		try
		{
			conn = ds.getConnection();
			String sql = "INSERT INTO BOARD_TBL "
					   + "( BOARD_NO, BOARD_CATEGORY, BOARD_WRITER, BOARD_TITLE, "
					   + "BOARD_CONTENT, BOARD_EXPIRATE, BOARD_IS_ALL_TIME ) "
					   + "VALUES(BOARD_SEQ.NEXTVAL, ?, ?, ?, ?, TO_DATE(?, 'YYYYMMDD HH24:MI:SS'), ?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, category);
			pstmt.setString(2, writer);
			title = replaceParameter(title);
			pstmt.setString(3, title);
			content = replaceParameter(content);
			pstmt.setString(4, content);
			pstmt.setString(5, ex_date);
			pstmt.setInt(6, all_time);
			result = pstmt.executeUpdate();
		}
		finally
		{
			close();
		}
		return result;
	}
	
	// 공지글을 수정하는 메서드
	// UPDATE BOARD_TBL SET
	public int editPost(int category, String writer, int all_time, String title, String content, String ex_date, int board_no) throws SQLException
	{
		int result = 0;
		try
		{
			conn = ds.getConnection();
			String sql = "UPDATE BOARD_TBL "
					   + "SET BOARD_CATEGORY = ?, "
					   + "BOARD_WRITER = ?, "
					   + "BOARD_TITLE = ?, "
					   + "BOARD_CONTENT = ?, "
					   + "BOARD_EXPIRATE = TO_DATE(?, 'YYYYMMDD HH24:MI:SS'), "
					   + "BOARD_IS_ALL_TIME = ? "
					   + "WHERE BOARD_NO = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, category);
			pstmt.setString(2, writer);
			title = replaceParameter(title);
			pstmt.setString(3, title);
			content = replaceParameter(content);
			pstmt.setString(4, content);
			pstmt.setString(5, ex_date);
			pstmt.setInt(6, all_time);
			pstmt.setInt(7, board_no);
			result = pstmt.executeUpdate();
		}
		finally
		{
			close();
		}
		return result;
	}
	
	// 공지글을 지워주는 메서드
	// DELETE BOARD_TBL WHERE BOARD_NO = ?
	public int deletePost(int board_no) throws SQLException
	{
		int result = 0;
		try
		{
			conn = ds.getConnection();
			String sql = "DELETE BOARD_TBL CASCADE "
					   + "WHERE BOARD_NO = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_no);
			result = pstmt.executeUpdate();
		}
		finally
		{
			close();
		}
		return result;
	}
	
	// 전체 공지글을 가져오는 메서드
	// select * from board_tbl
	public JsonArray getWholeBoard(int category, int order) throws SQLException
	{
		JsonArray boardList = new JsonArray();
		Statement stmt = null;
		ResultSet rsTime = null;
		try
		{
			// WHERE 절의 조건을 추가해주는 문자열
			String where = "";
			conn = ds.getConnection();
			// 전체 보기
			if(category == 0)
			{
				where = "1 = 1 ";
			}
			// 유효한 이벤트만 보기
			else if(category == 1)
			{
				where = "BOARD_CATEGORY = 1 AND "
					  + "( NVL( BOARD_EXPIRATE, SYSDATE ) - SYSDATE >= 0) ";
			}
			// 전체 이벤트 보기
			else if(category == 2)
			{
				where = "BOARD_CATEGORY = 1 ";
			}
			// 공지만 보기
			else if(category == 3)
			{
				where = "BOARD_CATEGORY = 0 ";
			}
			// ORDER BY 의 정렬 순을 추가해주는 문자열
			String orderBy = "BOARD_NO DESC ";
			// 최신 순
			if(order == 0)
			{
				orderBy = "BOARD_NO DESC ";
			}
			// 조회 순
			else if(order == 1)
			{
				orderBy = "BOARD_VIEW_CNT DESC ";
			}
			// 만료일
			else if(order == 2)
			{
				orderBy = "BOARD_EXPIRATE ASC ";
			}
			String sql = "SELECT * FROM BOARD_TBL WHERE " + where
						+ "ORDER BY " + orderBy;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				JsonObject jsonObject = new JsonObject();
				int board_no = rs.getInt(1);
				jsonObject.addProperty("board_no", board_no);
				int b_category = rs.getInt(2);
				String board_category = "";
				if(b_category == 0)
				{
					board_category = "공지";
				}
				else if(b_category == 1)
				{
					board_category = "이벤트";
				}
				jsonObject.addProperty("board_category", board_category);
				jsonObject.addProperty("board_writer", rs.getString(3));
				String title = rs.getString(4);
				jsonObject.addProperty("board_content", rs.getString(5));
				int board_reply_cnt = rs.getInt(6);
				jsonObject.addProperty("board_view_cnt", rs.getInt(7));
			//	jsonObject.addProperty("board_regidate", rs.getDate(8).toString());
				// 댓글이 달려있을 경우
				if(board_reply_cnt > 0)
				{
					title = title + " (" + board_reply_cnt + ")";
				}
				Date expirate = rs.getDate(9);
				if(expirate != null)
				{
					long now = Calendar.getInstance().getTimeInMillis();
					long diffDate = (expirate.getTime() - now) / 1000 / 60 / 60 / 24;
					if(diffDate < 0)
					{
						title = title + " &lt;기간만료&gt;";
					}
					else if(diffDate == 0)
					{
						title = title + " &lt;오늘까지!&gt;";
					}
					else if(diffDate < 3)
					{
						title = title + " &lt;마감임박&gt;";
					}
					jsonObject.addProperty("board_expirate", expirate.toString());
				}
				jsonObject.addProperty("board_is_all_time", rs.getInt(10));

				jsonObject.addProperty("board_title", title);
				jsonObject.addProperty("board_reply_cnt", board_reply_cnt);
				
				
				// 시간에 따라 등록일 변경
				// 방금전, 몇 분전, 몇시간전, 등록일
				stmt = conn.createStatement();
				String timeSQL = "SELECT TO_CHAR( ( SELECT BOARD_REGIDATE FROM BOARD_TBL " +
								"WHERE BOARD_NO =  " + board_no + 
								"), 'yyyy-mm-dd hh24:mi:ss') from dual ";
				rsTime = stmt.executeQuery(timeSQL);
				rsTime.next();
				String regidate = rsTime.getString(1);
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				java.util.Date parsedRegi = fm.parse(regidate);
				
				long now = Calendar.getInstance().getTimeInMillis();
				long timeRegidate = parsedRegi.getTime();
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String strDate = dateFormat.format(timeRegidate);
				long diffSec = (now - timeRegidate) / 1000; // 등록시각과 현재 시각과 비교한 초
				// 60초 이하
				if(diffSec < 60)
				{
					strDate = "방금 전";
				}
				// 60분 이하
				else if(diffSec < 3600)
				{
					strDate = (diffSec / 60) + "분 전";
				}
				// 하루 이하
				else if(diffSec < 86400)
				{
					strDate = (diffSec / 60 / 60) + "시간 전";
				}
				// 하루 이상일 경우 날짜
				jsonObject.addProperty("board_regidate", strDate);
				
				boardList.add(jsonObject);
			}
		}
		catch(java.text.ParseException pe)
		{
			pe.printStackTrace();
		}
		finally
		{
			try
			{
				if(stmt != null){stmt.close();}
				if(rsTime != null){rsTime.close();}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			this.close();
		}
		return boardList;
	}
	
	// 게시판의 상세 내용을 보는 메서드
	public Y_Board_VO getDetails(int board_no) throws SQLException
	{
		Y_Board_VO details = new Y_Board_VO();
		int viewCnt = upViewCnt(board_no);
		Statement stmt = null;
		ResultSet rsTime = null;
		try
		{
			conn = ds.getConnection();
			String sql = "SELECT * FROM BOARD_TBL "
					   + "WHERE BOARD_NO = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_no);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				details.setBoard_no(board_no);
				int category = rs.getInt(2);
				String board_category = "";
				if(category == 0)
				{
					board_category = "공지";
				}
				else if(category == 1)
				{
					board_category = "이벤트";
				}
				details.setBoard_category(board_category);
				details.setBoard_writer(rs.getString(3));
				String title = rs.getString(4);
				details.setBoard_content(rs.getString(5));
				int board_reply_cnt = rs.getInt(6);
				details.setBoard_view_cnt(rs.getInt(7));
				details.setBoard_regidate(rs.getDate(8));
				Date expirate = rs.getDate(9);
				if(expirate != null)
					details.setBoard_expirate(expirate);
				details.setBoard_is_all_time(rs.getInt(10));
				details.setBoard_title(title);
				details.setBoard_reply_cnt(board_reply_cnt);
			}
			else
			{
				System.out.println("상세보기 실패");	
			}
		}
		finally
		{
			close();
			if(viewCnt == 0)
				System.out.println("조회 수 최신화 실패");
			if(stmt != null){stmt.close();}
			if(rsTime != null){rsTime.close();}
		}
		return details;
	}
	
	// 게시글의 조회수를 올려주는 메서드
	public int upViewCnt(int board_no)
	{
		int result = 0;
		try
		{
			conn = ds.getConnection();
			String sql = "UPDATE BOARD_TBL "
						+ "SET BOARD_VIEW_CNT "
						+ "= BOARD_VIEW_CNT + 1 "
						+ "WHERE BOARD_NO = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_no);
			result = pstmt.executeUpdate();
		}
		catch(SQLException se)
		{
			System.out.println("SQL 오류발생");
			se.printStackTrace();
		}
		catch(Exception e)
		{
			System.out.println("기타 오류발생");
			e.printStackTrace();
		}
		finally
		{
			close();
		}
		return result;
	}
	
	// 파라미터의 <, >, \ 를 변경해주는 메서드
	private String replaceParameter(String param)
	{
		String result = param;
		if(param != null)
		{
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}
		return result;
	}
	
	
}
