package review;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

import javax.naming.*;
import javax.sql.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class Y_Review_DAO
{
	private DataSource ds; 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	public Y_Review_DAO()
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
			System.out.println("close() 메서드 오류 발생");
		}
	}
	
	// 리뷰 번호를 이용해 작성자를 불러오는 메서드
	// select re_writer from review_tbl
	public String getRe_Writer(int re_no) throws SQLException
	{
		String re_writer = "";
		try
		{
			conn = ds.getConnection();
			String sql = "SELECT RE_WRITER FROM REVIEW_TBL WHERE RE_NO = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, re_no);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				re_writer = rs.getString(1);
			}
		}
		finally
		{
			System.out.println(re_writer);
			this.close();
		}
		return re_writer;
	}
	
	// 작성자의 리뷰 추천 유무를 가져오는 메서드
	public JsonArray reviewRecommended(int user_no) throws SQLException
	{
		JsonArray recomList = new JsonArray();
		try
		{
			conn = ds.getConnection();
			String sql = "SELECT RECOMMENDED_NO FROM RE_RECOMMEND_TBL "
					   + "WHERE RECOMMENDER_NO = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_no);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				int re_no = rs.getInt(1);
				JsonObject obj = new JsonObject();
				obj.addProperty("re_no", re_no);
				recomList.add(obj);
			}
		}
		finally
		{
			this.close();
		}
		return recomList;
	}
	
	
	// 추천 버튼 클릭시 추천 혹은 취소하는 메서드
	public int reviewRecommend(int re_no, int user_no) throws SQLException
	{
		int result = 0;
		try
		{
			conn = ds.getConnection();
			String sql = "SELECT RECOMMENDER_NO FROM RE_RECOMMEND_TBL "
					   + "WHERE RECOMMENDED_NO = ? "
					   + "AND RECOMMENDER_NO = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, re_no);
			pstmt.setInt(2, user_no);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				System.out.println("추천 목록에 존재.. 삭제");
				String sql2 = "DELETE RE_RECOMMEND_TBL "
							+ "WHERE RECOMMENDED_NO = ? "
							+ "AND RECOMMENDER_NO = ? ";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, re_no);
				pstmt.setInt(2, user_no);
				result = pstmt.executeUpdate();
				if(result != 0)
					result = 2;
			}
			else
			{
				System.out.println("추천 목록에 없음.. 삽입");
				String sql2 = "INSERT INTO RE_RECOMMEND_TBL "
							+ "VALUES(?, ?) ";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, user_no);
				pstmt.setInt(2, re_no);
				result = pstmt.executeUpdate();
				if(result != 0)
					result = 3;
			}
		}
		finally
		{
			System.out.println(result);
			if(result == 0)
			{
				System.out.println("따봉 오류");
			}
			else
			{
				System.out.println("따봉 성공");
			}
			this.close();
		}
		return result;
	}
	
	// 추천 수 업데이트 하는 메서드
	public int updateReviewCnt(int re_no, int recom) throws SQLException
	{
		int result = 0;
		String plus_minus = "+";
		if(recom == 1)
		{
			plus_minus = "-";
		}
		try
		{
			conn = ds.getConnection();
			String sql = "UPDATE REVIEW_TBL "
					   + "SET RE_RECOMMEND_CNT = RE_RECOMMEND_CNT " + plus_minus + " 1 "
					   + "WHERE RE_NO = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, re_no);
			result = pstmt.executeUpdate();
		}
		finally
		{
			if(result == 0)
			{
				System.out.println("리뷰 추천수 업데이트 실패");
			}
			else
			{
				System.out.println("리뷰 추천수 업데이트 성공");
			}
			this.close();
		}
		return result;
	}
	
	// 작성자 닉네임으로 아이디 가져오는 메서드
	public String getIdByNick(String nick)
	{
		String id = "";
		try
		{
			conn = ds.getConnection();
			String sql = "SELECT USER_ID FROM USER_TBL WHERE USER_NICKNAME = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nick);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				id = rs.getString(1);
			}
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		finally
		{
			this.close();
		}
		return id;
	}
	
	// 모든 리뷰를 리스트형 자바빈으로 반환하는 메서드
	// select * from review_tbl
	public List<Y_Review_VO> getWholeReviews()
	{
		List<Y_Review_VO> reviewList = new ArrayList<Y_Review_VO>();
		Statement stmt = null;
		ResultSet rsId = null;
		try
		{
			conn = ds.getConnection();
			String sql = "SELECT * FROM REVIEW_TBL ORDER BY RE_NO DESC";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				Y_Review_VO vo = new Y_Review_VO();
				int re_no = rs.getInt(1);
				String re_writer = rs.getString(2);
				int re_ref_no = rs.getInt(3);
				String re_ref_name = rs.getString(4);
				String re_content = rs.getString(5);
				int re_grade = rs.getInt(6);
				int re_recommend_cnt = rs.getInt(7);
				Date re_regidate = rs.getDate(8);
				String re_img_url = rs.getString(9);
				
				stmt = conn.createStatement();
				String sql2 = "SELECT USER_ID FROM USER_TBL WHERE USER_NICKNAME = '" + re_writer + "' ";
				rsId = stmt.executeQuery(sql2);
				rsId.next();
				String re_writer_id = rsId.getString(1);
				
				vo.setRe_no(re_no);
				vo.setRe_writer(re_writer);
				vo.setRe_ref_no(re_ref_no);
				vo.setRe_ref_name(re_ref_name);
				vo.setRe_content(re_content);
				vo.setRe_grade(re_grade);
				vo.setRe_recommend_cnt(re_recommend_cnt);
				vo.setRe_regidate(re_regidate);
				vo.setRe_img_url(re_img_url);
				vo.setRe_writer_id(re_writer_id);
				
				reviewList.add(vo);

				try
				{
					if(stmt != null){stmt.close(); stmt=null;}
					if(rsId != null){rsId.close(); rsId=null;}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			System.out.println("리뷰 정보 가져오는 중 에러 발생");
		}
		finally
		{
			this.close();
		}
		return reviewList;
	}
	
	
	// 조건에 해당하는 모든 리뷰를 가져오는 메서드
	// select * from review_tbl where ? order by ?
	public JsonArray getWholeReview(int mp_no, int order, int grade, String category) throws SQLException
	{
		JsonArray reviewList = new JsonArray();
		Statement stmt = null;
		ResultSet rsTime = null;
		String where = "1 = 1 ";
		String orderBy = "RE_NO DESC ";
		
		if(mp_no != 0)
		{
			where = "RE_REF_NO = " + mp_no;
		}
		if(order == 1)
		{
			orderBy = "RE_RECOMMEND_CNT DESC ";
		}
		else if(order == 2)
		{
			orderBy = "RE_GRADE DESC ";
		}
		else if(order == 3)
		{
			orderBy = "RE_GRADE ASC ";
		}
		if(grade != 0)
		{
			where += " AND RE_GRADE >= 4 ";
		}
		if(category != "")
		{
			where += "AND RE_REF_NO IN "
				   + "( SELECT MP_NO FROM MP_TBL "
				   + "WHERE MP_KIND IN('" + category + "') ) ";
		}
		try
		{
			conn = ds.getConnection();
			String sql = "SELECT * FROM REVIEW_TBL WHERE " + where + " ORDER BY " + orderBy;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				JsonObject jsonObject = new JsonObject();
				int re_no = rs.getInt(1);
				jsonObject.addProperty("re_no", re_no);
				jsonObject.addProperty("re_writer", rs.getString(2));
				jsonObject.addProperty("re_ref_no", rs.getInt(3));
				jsonObject.addProperty("re_ref_name", rs.getString(4));
				jsonObject.addProperty("re_content", rs.getString(5));
				jsonObject.addProperty("re_grade", rs.getInt(6));
				jsonObject.addProperty("re_recommend_cnt", rs.getInt(7));
				jsonObject.addProperty("re_regidate", rs.getString(8));
				jsonObject.addProperty("re_img_url", rs.getString(9));
				
				stmt = conn.createStatement();
				String timeSQL = "SELECT TO_CHAR( ( SELECT RE_REGIDATE FROM REVIEW_TBL " +
								"WHERE RE_NO =  " + re_no + 
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
				long diffSec = (now - timeRegidate) / 1000;
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
				jsonObject.addProperty("re_regidate", strDate);
				
				reviewList.add(jsonObject);
				try
				{
					if(stmt != null){stmt.close(); stmt=null;}
					if(rsTime != null){rsTime.close(); rsTime=null;}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("시간 닫는 중 오류 발생");
				}
			}
		}
		catch(java.text.ParseException pe)
		{
			pe.printStackTrace();
		}
		finally
		{
			this.close();
		}
		return reviewList;
	}
	
	// 새로운 리뷰를 작성하는 메서드
	// insert into review_tbl
	public int writeReview(Y_Review_VO vo) throws SQLException
	{
		int result = 0;
		int mp_no = 0;
		try
		{
			conn = ds.getConnection();
			String sql = "INSERT INTO REVIEW_TBL"
					   + "( RE_NO, RE_WRITER, RE_REF_NO, RE_REF_NAME, "
					   + "RE_CONTENT, RE_GRADE, RE_IMG_URL ) "
					   + "VALUES( REVIEW_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ? ) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getRe_writer());
			mp_no = vo.getRe_ref_no();
			pstmt.setInt(2, mp_no);
			pstmt.setString(3, vo.getRe_ref_name());
			String content = vo.getRe_content();
			content = replaceParameter(content);
			pstmt.setString(4, content);
			pstmt.setInt(5, vo.getRe_grade());
			pstmt.setString(6, vo.getRe_img_url());
			result = pstmt.executeUpdate();
		}
		finally
		{
			this.close();
		}
		return result;
	}
	
	// 리뷰를 수정하는 메서드
	// update review_tbl
	public int updateReview(String re_content, int re_grade, String re_img_url, int re_no) throws SQLException
	{
		int result = 0;
		try
		{
			conn = ds.getConnection();
			String sql = "UPDATE REVIEW_TBL "
					   + "SET RE_CONTENT = ?, "
					   + "RE_GRADE = ?, "
					   + "RE_IMG_URL = ? "
					   + "WHERE RE_NO = ? ";
			pstmt = conn.prepareStatement(sql);
			re_content = replaceParameter(re_content);
			pstmt.setString(1, re_content);
			pstmt.setInt(2, re_grade);
			pstmt.setString(3, re_img_url);
			pstmt.setInt(4, re_no);
			result = pstmt.executeUpdate();
		}
		finally
		{
			this.close();
		}
		return result;
	}
	
	// 리뷰 번호를 이용해 리뷰의 정보를 불러오는 메서드
	// select * from review_tbl where re_no = ?
	public Y_Review_VO getReviewInfo(int re_no) throws SQLException
	{
		Y_Review_VO vo = new Y_Review_VO();
		try
		{
			conn = ds.getConnection();
			String sql = "SELECT * FROM REVIEW_TBL WHERE RE_NO = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, re_no);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				// re_no
				vo.setRe_no(rs.getInt(1));
				// re_writer
				vo.setRe_writer(rs.getString(2));
				// re_ref_no
				vo.setRe_ref_no(rs.getInt(3));
				// re_ref_name
				vo.setRe_ref_name(rs.getString(4));
				// re_content
				vo.setRe_content(rs.getString(5));
				// re_grade
				vo.setRe_grade(rs.getInt(6));
				// re_recommend_cnt
				vo.setRe_recommend_cnt(rs.getInt(7));
				// re_regidate
				vo.setRe_regidate(rs.getDate(8));
				// re_img_url
				vo.setRe_img_url(rs.getString(9));
			}
		}
		finally
		{
			this.close();
		}
		return vo;
	}
	
	// 리뷰를 삭제하는 메서드
	// delete review
	public int deleteReview(int re_no) throws SQLException
	{
		int result = 0;
		try
		{
			conn = ds.getConnection();
			String sql = "DELETE REVIEW_TBL "
					   + "WHERE RE_NO = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, re_no);
			result = pstmt.executeUpdate();
		}
		finally
		{
			this.close();
		}
		return result;
	}
	
	// 맛집의 평균 평점 및 점수를 변경하는 메서드
	// update MP_TBL
	public int updateMP(int mp_no) throws SQLException
	{
		int result = 0;
		// MP_RE_CNT : 리뷰수
		// MP_AVG_GRADE : 평균 평점
		// MP_RE_GRADE : 
		// 리뷰수당 1점, 3 : + 100, 4 : + 200, 5 : + 300
		try
		{
			conn = ds.getConnection();
			String sql = "UPDATE MP_TBL "
					   + "SET MP_RE_CNT = (SELECT COUNT(RE_NO) FROM REVIEW_TBL WHERE RE_REF_NO = ?), "
					   + "MP_AVG_GRADE = (SELECT AVG(RE_GRADE) FROM REVIEW_TBL WHERE RE_REF_NO = ?), "
					   + "MP_RE_GRADE = (SELECT SUM(RE_GRADE - 2) FROM REVIEW_TBL WHERE RE_REF_NO = ?) "
					   + "WHERE MP_NO = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mp_no);
			pstmt.setInt(2, mp_no);
			pstmt.setInt(3, mp_no);
			pstmt.setInt(4, mp_no);
			result = pstmt.executeUpdate();
		}
		finally
		{
			this.close();
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
