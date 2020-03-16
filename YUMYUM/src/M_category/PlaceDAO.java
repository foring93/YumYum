package M_category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
// ################# 페이지네이션 테스트 클래스 
public class PlaceDAO {
	private DataSource ds;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public PlaceDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:/comp/env/jdbc/yumyum");
		} catch (Exception ex) {
			System.out.println("DB 연결 실패 : " + ex);
			return;
		}
	}

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

			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 선생님이 주신 귀한 보물,, 선생님,, the love,,☆★
	   public List<PlaceVO> SearchHash (int page, int limit, String[] search) {
	      List<PlaceVO> list = new ArrayList<PlaceVO>();
	      int startrow = (page - 1) * limit + 1;
	      int endrow = startrow + limit - 1;
	      try {
	         conn = ds.getConnection();
	         
	         String str = "'%" + search[1] + "%'";
	         for (int i = 2; i < search.length; i++) {
	            str += " OR UPPER(MP_HASH) LIKE '%" + search[i] + "%' ";
	         }
	         System.out.println(str);
	         String select = "SELECT * FROM MP_TBL WHERE UPPER(MP_HASH) LIKE " + str;
	         System.out.println(select);
	         
	         String place_list = "SELECT * FROM "
	                       + "(SELECT ROWNUM R, M.* "
	                       + "FROM (" + select 
	                       + "ORDER BY MP_NAME ASC) M) "
	                       + "WHERE R >= ? AND R <= ?";
	         
	         pstmt = conn.prepareStatement(place_list);
	         pstmt.setInt(1, startrow);
	         pstmt.setInt(2, endrow);
	         rs = pstmt.executeQuery();
	         
	         while (rs.next()) {
	            PlaceVO vo = new PlaceVO();
	            vo.setMP_IMG_URL(rs.getString("MP_IMG_URL"));
	            vo.setMP_NO(rs.getInt("MP_NO"));
	            vo.setMP_NAME(rs.getString("MP_NAME"));
	            list.add(vo);
	         }
	         System.out.println("dao에서 찍는 list 사이즈는 " + list.size());
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         close();
	      }
	      return list;
	   }
	/*/////////////////////// */
	
	
	// ########## Best ##########
	// ## Best 맛집 가져오기 ##
	public JsonArray getBest() {
		JsonArray arr = new JsonArray();
		try {
			conn = ds.getConnection();

			// 평점순으로 여섯 맛집 데이터를 가져온다.
			String select = "SELECT * FROM (SELECT * FROM MP_TBL WHERE MP_AVG_GRADE > 0 ORDER BY MP_AVG_GRADE DESC) WHERE ROWNUM <= 6";
			pstmt = conn.prepareStatement(select);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("mp_img_url", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("mp_no", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("mp_name", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("mp_re_cnt", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("mp_view_cnt", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("mp_avg_grade", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	// ## 도시에 해당하는 베스트맛집 찾기 ##
	public JsonArray getBestCity(String city) {
		JsonArray arr = new JsonArray();
		try {
			conn = ds.getConnection();
			String select = "SELECT * FROM (SELECT * FROM MP_TBL  WHERE MP_AVG_GRADE > 0 AND MP_ADDRESS LIKE ?||'%' "
						  + "ORDER BY MP_AVG_GRADE DESC) WHERE ROWNUM <= 6";
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, city);
			rs = pstmt.executeQuery();
		
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("mp_img_url", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("mp_no", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("mp_name", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("mp_re_cnt", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("mp_view_cnt", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("mp_avg_grade", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	// ## 도시, 세부지역에 해당하는 베스트맛집 찾기 ##
	public JsonArray getBestSubCity (String city, String subcity) {
		JsonArray arr = new JsonArray();
		try {
			conn = ds.getConnection();
			String select = "SELECT * FROM (SELECT * FROM MP_TBL  WHERE MP_AVG_GRADE > 0 AND MP_ADDRESS LIKE ?||'%'||?||'%' "
						  + "ORDER BY MP_AVG_GRADE DESC) WHERE ROWNUM <= 6";
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, city);
			pstmt.setString(2, subcity);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("mp_img_url", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("mp_no", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("mp_name", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("mp_re_cnt", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("mp_view_cnt", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("mp_avg_grade", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	// ## 음식종류에 해당하는 베스트맛집 찾기 ##
	public JsonArray getBestKind (String kind) {
		JsonArray arr = new JsonArray();
		try {
			conn = ds.getConnection();
			String select = "SELECT * FROM (SELECT * FROM MP_TBL WHERE MP_AVG_GRADE > 0 AND MP_KIND LIKE '%'||?||'%' "
						  + "ORDER BY MP_AVG_GRADE DESC) WHERE ROWNUM <= 6";
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, kind);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("mp_img_url", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("mp_no", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("mp_name", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("mp_re_cnt", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("mp_view_cnt", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("mp_avg_grade", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	
	// ## 도시와 음식종류에 해당하는 베스트맛집 찾기 ##
	public JsonArray getBestCityKind (String city, String kind) {
		JsonArray arr = new JsonArray();
		try {
			conn = ds.getConnection();
			String select = "SELECT * FROM (SELECT * FROM MP_TBL WHERE MP_AVG_GRADE > 0 AND MP_ADDRESS LIKE ?||'%' AND "
						  + "MP_KIND LIKE '%'||?||'%' ORDER BY MP_AVG_GRADE DESC) WHERE ROWNUM <= 6";
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, city);
			pstmt.setString(2, kind);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("mp_img_url", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("mp_no", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("mp_name", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("mp_re_cnt", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("mp_view_cnt", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("mp_avg_grade", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	// ## 도시, 세부지역, 음식종류에 해당하는 베스트맛집 찾기 ##
	public JsonArray getBestAll (String city, String subcity, String kind) {
		JsonArray arr = new JsonArray();
		try {
			conn = ds.getConnection();
			String select = "SELECT * FROM (SELECT * FROM MP_TBL WHERE MP_AVG_GRADE > 0 AND MP_ADDRESS LIKE ?||'%'||?||'%' AND "
						  + "MP_KIND LIKE '%'||?||'%' ORDER BY MP_AVG_GRADE DESC) WHERE ROWNUM <= 6";
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, city);
			pstmt.setString(2, subcity);
			pstmt.setString(3, kind);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("mp_img_url", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("mp_no", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("mp_name", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("mp_re_cnt", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("mp_view_cnt", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("mp_avg_grade", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	
	// ########## New ##########
	// ## 페이지네이션 ##
	public int getNewCount() { // 정해진 기간 내에 등록된 신규 맛집의 개수를 가져온다. 
		int result = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL ORDER BY MP_REGIDATE DESC";	
			pstmt = conn.prepareStatement(select);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	public JsonArray getNew(int page, int limit) {
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
				
			// 최신순으로 모든 맛집 데이터를 가져온다.
			String place_list = "SELECT * FROM " 
							  + "(SELECT ROWNUM R, M.* "
							  + "FROM (SELECT * FROM MP_TBL "
							  + "ORDER BY MP_REGIDATE DESC) M) "
							  + "WHERE R >= ? AND R <= ?"; 
			pstmt = conn.prepareStatement(place_list);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("mp_img_url", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("mp_no", rs.getString("MP_NO")); // 맛집 이미지
				js.addProperty("mp_name", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("mp_re_cnt", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("mp_view_cnt", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("mp_avg_grade", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	// ## 페이지네이션 ##
	public int getNewCityCount(String city) { // 정해진 기간 내에 등록된 도시별 신규 맛집 개수를 가져온다. 
		int result = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL "
						  + "WHERE MP_ADDRESS LIKE ? "
						  + "ORDER BY MP_REGIDATE DESC";	
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, city + "%");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	public JsonArray getNewCity(int page, int limit, String city) { // page, limit에 맞춰 도시별 신규 맛집을 가져온다.
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			String place_list = "SELECT * FROM " 
							  + "(SELECT ROWNUM R, M.* "
							  + "FROM (SELECT * FROM MP_TBL "
							  + "WHERE MP_ADDRESS LIKE ? "
							  + "ORDER BY MP_REGIDATE DESC) M) "
							  + "WHERE R >= ? AND R <= ?"; 
			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, city + "%");
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, endrow);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("mp_img_url", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("mp_no", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("mp_name", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("mp_re_cnt", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("mp_view_cnt", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("mp_avg_grade", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}

	// ## 페이지네이션 ##
	public int getNewSubCityCount (String city, String subcity) { // 정해진 기간 내에 등록된 도시, 세부지역별 신규 맛집 개수를 가져온다. 
		int result = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL "
						  + "WHERE MP_ADDRESS LIKE ?||'%'||?||'%' "
						  + "ORDER BY MP_REGIDATE DESC";	
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, city);
			pstmt.setString(2, subcity);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	// ## 도시에 속한 지역별 New 맛집 가져오기 ##
	public JsonArray getNewSubCity(int page, int limit, String city, String subcity) {
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			String place_list = "SELECT * FROM " 
					  + "(SELECT ROWNUM R, M.* "
					  + "FROM (SELECT * FROM MP_TBL "
					  + "WHERE MP_ADDRESS LIKE ?||'%'||?||'%' "
					  + "ORDER BY MP_REGIDATE DESC) M) "
					  + "WHERE R >= ? AND R <= ?"; 
			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, city);
			pstmt.setString(2, subcity);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, endrow);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("mp_img_url", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("mp_no", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("mp_name", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("mp_re_cnt", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("mp_view_cnt", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("mp_avg_grade", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}

	// ## 페이지네이션 ##
	public int getNewKindCount (String kind) { // 정해진 기간 내에 등록된 종류별 신규 맛집 개수를 가져온다. 
		int result = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL "
						  + "WHERE MP_KIND LIKE ? "
						  + "ORDER BY MP_REGIDATE DESC";	
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, "%" + kind + "%");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	public JsonArray getNewKind (int page, int limit, String kind) { 
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			
			String place_list = "SELECT * FROM " 
					  	  	  + "(SELECT ROWNUM R, M.* "
					  	  	  + "FROM (SELECT * FROM MP_TBL "
					  	  	  + "WHERE MP_KIND LIKE ? "
					  	  	  + "ORDER BY MP_REGIDATE DESC) M) "
					  	  	  + "WHERE R >= ? AND R <= ?"; 
			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, "%" + kind + "%");
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, endrow);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("mp_img_url", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("mp_no", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("mp_name", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("mp_re_cnt", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("mp_view_cnt", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("mp_avg_grade", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}

	// ## 페이지네이션 ##
	public int getNewCityKindCount (String city, String kind) { // 정해진 기간 내에 등록된 도시, 종류별 신규 맛집 개수를 가져온다. 
		int result = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL "
						  + "WHERE MP_ADDRESS LIKE ? "
						  + "AND MP_KIND LIKE ? "
						  + "ORDER BY MP_REGIDATE DESC";	
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, city + "%");
			pstmt.setString(2, "%" + kind + "%");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	// ## 도시, 종류별로 New 맛집 가져오기 ##
	public JsonArray getNewCityKind (int page, int limit, String city, String kind) {
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			String place_list = "SELECT * FROM " 
					+ "(SELECT ROWNUM R, M.* "
					+ "FROM (SELECT * FROM MP_TBL "
					+ "WHERE MP_ADDRESS LIKE ? "
					+ "AND MP_KIND LIKE ? "
					+ "ORDER BY MP_REGIDATE DESC) M) "
					+ "WHERE R >= ? AND R <= ?"; 

			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, city + "%");
			pstmt.setString(2, "%" + kind + "%");
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, endrow);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("mp_img_url", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("mp_no", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("mp_name", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("mp_re_cnt", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("mp_view_cnt", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("mp_avg_grade", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}

	// ## 페이지네이션 ##
	public int getNewAllCount (String city, String subcity, String kind) { // 정해진 기간 내에 등록된 도시, 세부지역, 종류별 신규 맛집 개수를 가져온다. 
		int result = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL "
						  + "WHERE MP_ADDRESS LIKE ?||'%'||?||'%' "
						  + "AND MP_KIND LIKE ? "
						  + "ORDER BY MP_REGIDATE DESC";	
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, city);
			pstmt.setString(2, subcity);
			pstmt.setString(3, "%" + kind + "%");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	// ## 도시, 지역, 종류별로 New 맛집 가져오기 ##
	public JsonArray getNewAll (int page, int limit, String city, String subcity, String kind) {
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			String place_list = "SELECT * FROM " 
							  + "(SELECT ROWNUM R, M.* "
							  + "FROM (SELECT * FROM MP_TBL "
							  + "WHERE MP_ADDRESS LIKE ?||'%'||?||'%' "
							  + "AND MP_KIND LIKE ? "
							  + "ORDER BY MP_REGIDATE DESC) M) "
							  + "WHERE R >= ? AND R <= ?"; 
			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, city);
			pstmt.setString(2, subcity);
			pstmt.setString(3, "%" + kind + "%");
			pstmt.setInt(4, startrow);
			pstmt.setInt(5, endrow);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("mp_img_url", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("mp_no", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("mp_name", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("mp_re_cnt", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("mp_view_cnt", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("mp_avg_grade", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	
	// ########## All ##########
	// ## 페이지네이션 ##
	public int getAllListCount() { // 조건 없는 전체 맛집의 개수를 가져온다.
		int count = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL";
				
			pstmt = conn.prepareStatement(select);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}	
	
	public JsonArray getAll (int page, int limit) { // 조건 없는 전체 맛집의 데이터를 page, limit에 맞춰 가져온다. 
		String place_list = "SELECT * FROM " 
							+ "(SELECT ROWNUM R, M.* "
							+ "FROM (SELECT * FROM MP_TBL "
							+ "ORDER BY MP_NAME ASC) M) "
							+ "WHERE R >= ? AND R <= ?"; 
		JsonArray arr = new JsonArray();
		
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(place_list);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("MP_IMG_URL", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("MP_NO", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("MP_NAME", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("MP_RE_CNT", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("MP_VIEW_CNT", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("MP_AVG_GRADE", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
			return arr;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}
	
	// ## 페이지네이션 ##
	public int getAllCityCount(String city) { // 도시별 전체 맛집의 개수를 가져온다.
		int count = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL WHERE MP_ADDRESS LIKE ?||'%'";
			
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, city);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}
	
	public JsonArray getAllCity(int page, int limit, String city) { // 도시별 전체 맛집의 데이터를 page, limit에 맞춰 가져온다. 
		String place_list = "SELECT * FROM "
				+ "				(SELECT ROWNUM R, M.* "
				+ "				FROM (SELECT * FROM MP_TBL"
				+ "					  WHERE MP_ADDRESS LIKE ?||'%'"
				+ "					  ORDER BY MP_NAME ASC) M) "
				+ "				WHERE R >= ? AND R <= ?";
		JsonArray arr = new JsonArray();
		
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		
		try {
			conn = ds.getConnection();

			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, city);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, endrow);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("MP_IMG_URL", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("MP_NO", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("MP_NAME", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("MP_RE_CNT", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("MP_VIEW_CNT", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("MP_AVG_GRADE", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	// ## 페이지네이션 ##
	public int getAllKindCount(String kind) { // 종류별 전체 맛집의 개수를 가져온다.
		int count = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL WHERE MP_KIND LIKE '%'||?||'%'";
			
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, kind);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}
	
	public JsonArray getAllKind(int page, int limit, String kind) { // 종류별 전체 맛집의 데이터를 page, limit에 맞춰 가져온다. 
		JsonArray arr = new JsonArray();
		
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		
		try {
			conn = ds.getConnection();
				
			String place_list = "SELECT * FROM "
								+ "(SELECT ROWNUM R, M.* "
								+ "FROM (SELECT * FROM MP_TBL "
								+ "WHERE MP_KIND LIKE '%'||?||'%' "
								+ "ORDER BY MP_NAME ASC) M) "
								+ "WHERE R >= ? AND R <= ?";
			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, endrow);
			rs = pstmt.executeQuery();
				
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("MP_IMG_URL", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("MP_NO", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("MP_NAME", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("MP_RE_CNT", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("MP_VIEW_CNT", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("MP_AVG_GRADE", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}	
	
	// ## 페이지네이션 ##
	public int getAllWordCount(String word) { // 검색어별 전체 맛집의 개수를 가져온다.
		int count = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL WHERE UPPER(REPLACE(MP_NAME, ' ', '')) LIKE ? "
				  	  		+ "OR REPLACE(MP_ADDRESS, ' ', '') LIKE ? "
				  	  		+ "OR REPLACE(MP_KIND, ' ', '') LIKE ? "
				  	  		+ "OR UPPER(REPLACE(MP_INFO, ' ', '')) LIKE ? "
				  	  		+ "OR UPPER(REPLACE(MP_HASH, ' ', '')) LIKE ? ";
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, "%" + word + "%");
			pstmt.setString(2, "%" + word + "%");
			pstmt.setString(3, "%" + word + "%");
			pstmt.setString(4, "%" + word + "%");
			pstmt.setString(5, "%" + word + "%");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}
	
	public JsonArray getWord (int page, int limit, String keyword) { // 검색어별 전체 맛집의 데이터를 page, limit에 맞춰 가져온다. 
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			String place_list = "SELECT * FROM "
						  + "(SELECT ROWNUM R, M.* "
						  + "FROM (SELECT * FROM MP_TBL "
						  + "WHERE UPPER(REPLACE(MP_NAME, ' ', '')) LIKE ? "
					  	  + "OR REPLACE(MP_ADDRESS, ' ', '') LIKE ? "
					  	  + "OR REPLACE(MP_KIND, ' ', '') LIKE ? "
					  	  + "OR UPPER(REPLACE(MP_INFO, ' ', '')) LIKE ? " 	  
					  	  + "OR UPPER(REPLACE(MP_HASH, ' ', '')) LIKE ? "
					  	  + "ORDER BY MP_NAME ASC) M) "
					  	  + "WHERE R >= ? AND R <= ?";
			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setString(3, "%" + keyword + "%");
			pstmt.setString(4, "%" + keyword + "%");
			pstmt.setString(5, "%" + keyword + "%");
			pstmt.setInt(6, startrow);
			pstmt.setInt(7, endrow);
				
			rs = pstmt.executeQuery();
				
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("MP_IMG_URL", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("MP_NO", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("MP_NAME", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("MP_RE_CNT", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("MP_VIEW_CNT", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("MP_AVG_GRADE", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
			return arr;
		}
	
	// ## 페이지네이션 ##
	public int getAll2CityCount(String place, String subplace) { // 도시, 세부지역별 전체 맛집의 개수를 가져온다.
		int count = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL "
							+ "WHERE MP_ADDRESS LIKE ?||'%'||?||'%'";
			
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, place);
			pstmt.setString(2, subplace);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}
	
	public JsonArray getAll2City(int page, int limit, String city, String subcity) { // 도시, 세부지역별 전체 맛집의 데이터를 page, limit에 맞춰 가져온다. 
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			String place_list = "SELECT * FROM "
							+ "(SELECT ROWNUM R, M.* "
							+ "FROM (SELECT * FROM MP_TBL "
							+ "WHERE MP_ADDRESS LIKE ?||'%'||?||'%' "
							+ "ORDER BY MP_NAME ASC) M) "
							+ "WHERE R >= ? AND R <= ?";
			
			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, city);
			pstmt.setString(2, subcity);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, endrow);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("MP_IMG_URL", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("MP_NO", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("MP_NAME", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("MP_RE_CNT", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("MP_VIEW_CNT", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("MP_AVG_GRADE", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	// ## 페이지네이션 ##
	public int getAllCityKindCount(String place, String kind) { // 도시, 음식종류별 전체 맛집의 개수를 가져온다.
		int count = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL "
							+ "WHERE MP_ADDRESS LIKE ?||'%' AND "
					  		+ "MP_KIND LIKE ? ORDER BY MP_NAME";
				
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, place);
			pstmt.setString(2, "%" + kind + "%");
			rs = pstmt.executeQuery();
				
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}
	
	public JsonArray getAllCityKind(int page, int limit, String city, String kind) { // 도시, 음식종류별 전체 맛집의 데이터를 page, limit에 맞춰 가져온다.
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			String place_list = "SELECT * FROM "
							  + "(SELECT ROWNUM R, M.* "
							  + "FROM (SELECT * FROM MP_TBL "
							  + "WHERE MP_ADDRESS LIKE ? AND "
							  + "MP_KIND LIKE ? "
							  + "ORDER BY MP_NAME ASC) M) "
							  + "WHERE R >= ? AND R <= ?";
			
			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, city + "%");
			pstmt.setString(2, "%" + kind + "%");
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, endrow);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("MP_IMG_URL", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("MP_NO", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("MP_NAME", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("MP_RE_CNT", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("MP_VIEW_CNT", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("MP_AVG_GRADE", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	// ## 페이지네이션 ##
	public int cityWordCount(String place, String keyword) { // 도시, 검색어별 전체 맛집의 개수를 가져온다.
		int count = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL "
							+ "WHERE MP_ADDRESS LIKE ? AND "
							+ "(REPLACE(MP_ADDRESS, ' ', '') LIKE ? OR "
							+ "UPPER(REPLACE(MP_NAME, ' ', '')) LIKE ? OR "
							+ "REPLACE(MP_KIND, ' ', '') LIKE ? OR "
							+ "UPPER(REPLACE(MP_INFO, ' ', '')) LIKE ? OR "
							+ "UPPER(REPLACE(MP_HASH, ' ', '')) LIKE ?)";
					
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, place + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setString(3, "%" + keyword + "%");
			pstmt.setString(4, "%" + keyword + "%");
			pstmt.setString(5, "%" + keyword + "%");
			pstmt.setString(6, "%" + keyword + "%");
			
			rs = pstmt.executeQuery();
					
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}
	
	public JsonArray cityWord (int page, int limit, String city, String keyword) { // 도시, 검색어별 전체 맛집의 데이터를 page, limit에 맞춰 가져온다.
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			
			String place_list = "SELECT * FROM "
						  + "(SELECT ROWNUM R, M.* "
						  + "FROM (SELECT * FROM MP_TBL "
						  + "WHERE MP_ADDRESS LIKE ? AND "
						  + "(REPLACE(MP_ADDRESS, ' ', '') LIKE ? OR "
						  + "UPPER(REPLACE(MP_NAME, ' ', '')) LIKE ? OR "
						  + "REPLACE(MP_KIND, ' ', '') LIKE ? OR "
						  + "UPPER(REPLACE(MP_INFO, ' ', '')) LIKE ? OR " 
						  + "UPPER(REPLACE(MP_HASH, ' ', '')) LIKE ?) "
						  + "ORDER BY MP_NAME ASC) M) "
						  + "WHERE R >= ? AND R <= ?";
			
			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, city + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setString(3, "%" + keyword + "%");
			pstmt.setString(4, "%" + keyword + "%");
			pstmt.setString(5, "%" + keyword + "%");
			pstmt.setString(6, "%" + keyword + "%");
			pstmt.setInt(7, startrow);
			pstmt.setInt(8, endrow);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("MP_IMG_URL", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("MP_NO", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("MP_NAME", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("MP_RE_CNT", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("MP_VIEW_CNT", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("MP_AVG_GRADE", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	// 해시태그 검색인 경우 
	public List<PlaceVO> SearchCityHash (int page, int limit, String city, String[] search) { // 도시, 검색어별
		List<PlaceVO> list = new ArrayList<PlaceVO>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			// #만 입력하고 검색어는 따로 입력하지 않은 경우 
			if (search.length == 0) {
				String place_list = "SELECT * FROM "
						+ "(SELECT ROWNUM R, M.* "
						+ "FROM ( SELECT * FROM MP_TBL WHERE "
						+ "MP_ADDRESS LIKE ? "
						+ "ORDER BY MP_NAME ASC) M) "
						+ "WHERE R >= ? AND R <= ?";
				
				pstmt = conn.prepareStatement(place_list);
				pstmt.setString(1, city + "%");
				pstmt.setInt(2, startrow);
				pstmt.setInt(3, endrow);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					PlaceVO vo = new PlaceVO();
					vo.setMP_IMG_URL(rs.getString("MP_IMG_URL"));
					vo.setMP_NO(rs.getInt("MP_NO"));
					vo.setMP_NAME(rs.getString("MP_NAME"));
					vo.setMP_RE_CNT(rs.getInt("MP_RE_CNT"));
					vo.setMP_VIEW_CNT(rs.getInt("MP_VIEW_CNT"));
					vo.setMP_AVG_GRADE(rs.getInt("MP_AVG_GRADE"));
					
					list.add(vo);
				}
			} else {
				String str = "'%" + search[1] + "%'";
				for (int i = 2; i < search.length; i++) {
					str += " OR UPPER(MP_HASH) LIKE '%" + search[i] + "%' ";
				}
				System.out.println(str);
				String select = "SELECT * FROM MP_TBL WHERE (UPPER(MP_HASH) LIKE " + str;
				System.out.println(select);
				
				String place_list = "SELECT * FROM "
								  + "(SELECT ROWNUM R, M.* "
								  + "FROM (" + select 
								  + ") AND MP_ADDRESS LIKE ? "
								  + "ORDER BY MP_NAME ASC) M) "
								  + "WHERE R >= ? AND R <= ?";
				
				System.out.println(place_list);
				pstmt = conn.prepareStatement(place_list);
				pstmt.setString(1, city + "%");
				pstmt.setInt(2, startrow);
				pstmt.setInt(3, endrow);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					PlaceVO vo = new PlaceVO();
					vo.setMP_IMG_URL(rs.getString("MP_IMG_URL"));
					vo.setMP_NO(rs.getInt("MP_NO"));
					vo.setMP_NAME(rs.getString("MP_NAME"));
					vo.setMP_RE_CNT(rs.getInt("MP_RE_CNT"));
					vo.setMP_VIEW_CNT(rs.getInt("MP_VIEW_CNT"));
					vo.setMP_AVG_GRADE(rs.getInt("MP_AVG_GRADE"));
					
					list.add(vo);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	
	
	// ## 페이지네이션 ##
	public int getKindWordCount (String kind, String keyword) { // 음식종류, 검색어별 전체 맛집의 개수를 가져온다.
		int count = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL "
							+ "WHERE MP_KIND LIKE ? AND "
							+ "(REPLACE(MP_ADDRESS, ' ', '') LIKE ? OR "
							+ "UPPER(REPLACE(MP_NAME, ' ', '')) LIKE ? OR "
							+ "REPLACE(MP_KIND, ' ', '') LIKE ? OR "
							+ "UPPER(REPLACE(MP_INFO, ' ', '')) LIKE ? OR "
							+ "UPPER(REPLACE(MP_HASH, ' ', '')) LIKE ?) ";
						
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, "%" + kind + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setString(3, "%" + keyword + "%");
			pstmt.setString(4, "%" + keyword + "%");
			pstmt.setString(5, "%" + keyword + "%");
			pstmt.setString(6, "%" + keyword + "%");
				
			rs = pstmt.executeQuery();
					
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}
		
	public JsonArray getKindWord (int page, int limit, String kind, String keyword) { // 음식종류, 검색어별 전체 맛집의 데이터를 page, limit에 맞춰 가져온다.
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			
			String place_list = "SELECT * FROM "
						  + "(SELECT ROWNUM R, M.* "
						  + "FROM (SELECT * FROM MP_TBL "
						  + "WHERE MP_KIND LIKE ? AND "
						  + "(REPLACE(MP_ADDRESS, ' ', '') LIKE ? OR "
						  + "UPPER(REPLACE(MP_NAME, ' ', '')) LIKE ? OR "
						  + "REPLACE(MP_KIND, ' ', '') LIKE ? OR "
						  + "UPPER(REPLACE(MP_INFO, ' ', '')) LIKE ? OR "
						  + "UPPER(REPLACE(MP_HASH, ' ', '')) LIKE ?) "
						  + "ORDER BY MP_NAME ASC) M) "
						  + "WHERE R >= ? AND R <= ?";
			
			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, "%" + kind + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setString(3, "%" + keyword + "%");
			pstmt.setString(4, "%" + keyword + "%");
			pstmt.setString(5, "%" + keyword + "%");
			pstmt.setString(6, "%" + keyword + "%");
			pstmt.setInt(7, startrow);
			pstmt.setInt(8, endrow);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("MP_IMG_URL", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("MP_NO", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("MP_NAME", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("MP_RE_CNT", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("MP_VIEW_CNT", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("MP_AVG_GRADE", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}

	// 해시태그 검색인 경우 
	public List<PlaceVO> SearchKindHash (int page, int limit, String kind, String[] search) { // 음식종류, 검색어
		List<PlaceVO> list = new ArrayList<PlaceVO>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			if (search.length == 0) {
				String place_list = "SELECT * FROM "
								  + "(SELECT ROWNUM R, M.* "
								  + "FROM ( SELECT * FROM MP_TBL WHERE "  
								  + "MP_KIND LIKE ? "
								  + "ORDER BY MP_NAME ASC) M) "
								  + "WHERE R >= ? AND R <= ?";
				
				pstmt = conn.prepareStatement(place_list);
				pstmt.setString(1, "%" + kind + "%");
				pstmt.setInt(2, startrow);
				pstmt.setInt(3, endrow);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					PlaceVO vo = new PlaceVO();
					vo.setMP_IMG_URL(rs.getString("MP_IMG_URL"));
					vo.setMP_NO(rs.getInt("MP_NO"));
					vo.setMP_NAME(rs.getString("MP_NAME"));
					list.add(vo);
				}
			} else {
				String str = "'%" + search[1] + "%'";
				for (int i = 2; i < search.length; i++) {
					str += " OR UPPER(MP_HASH) LIKE '%" + search[i] + "%' ";
				}
				System.out.println(str);
				String select = "SELECT * FROM MP_TBL WHERE (UPPER(MP_HASH) LIKE " + str;
				System.out.println(select);
				String place_list = "SELECT * FROM "
						  + "(SELECT ROWNUM R, M.* "
						  + "FROM ( " + select   
						  + ") AND MP_KIND LIKE ? "
						  + "ORDER BY MP_NAME ASC) M) "
						  + "WHERE R >= ? AND R <= ?";
				
				pstmt = conn.prepareStatement(place_list);
				pstmt.setString(1, "%" + kind + "%");
				pstmt.setInt(2, startrow);
				pstmt.setInt(3, endrow);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					PlaceVO vo = new PlaceVO();
					vo.setMP_IMG_URL(rs.getString("MP_IMG_URL"));
					vo.setMP_NO(rs.getInt("MP_NO"));
					vo.setMP_NAME(rs.getString("MP_NAME"));
					list.add(vo);
				}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					close();
				}
				return list;
	}
	
	
	// ## 페이지네이션 ##
	public int getNotWordCount (String city, String subcity, String kind) { // 도시, 세부지역, 음식종류별 전체 맛집의 개수를 가져온다.
		int count = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL "
					  	  + "WHERE MP_ADDRESS LIKE ?||'%'||?||'%' AND "
					  	  + "MP_KIND LIKE '%'||?||'%' ";
						
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, city);
			pstmt.setString(2, subcity);
			pstmt.setString(3, kind);
				
			rs = pstmt.executeQuery();
					
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}
	
	public JsonArray getNotWord (int page, int limit, String city, String subcity, String kind) { // 도시, 세부지역, 음식종류별 전체 맛집의 데이터를 page, limit에 맞춰 가져온다.
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			
			String place_list = "SELECT * FROM "
						  + "(SELECT ROWNUM R, M.* "
						  + "FROM (SELECT * FROM MP_TBL "
						  + "WHERE MP_ADDRESS LIKE ?||'%'||?||'%' AND "
						  + "MP_KIND LIKE '%'||?||'%' "
						  + "ORDER BY MP_NAME ASC) M) "
						  + "WHERE R >= ? AND R <= ?";
			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, city);
			pstmt.setString(2, subcity);
			pstmt.setString(3, kind);
			pstmt.setInt(4, startrow);
			pstmt.setInt(5, endrow);
			rs = pstmt.executeQuery();
				
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("MP_IMG_URL", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("MP_NO", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("MP_NAME", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("MP_RE_CNT", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("MP_VIEW_CNT", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("MP_AVG_GRADE", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	// ## 페이지네이션 ##
	public int getNotKindCount (String city, String subcity, String keyword) { // 도시, 세부지역, 검색어별 전체 맛집의 개수를 가져온다.
		int count = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL "
					  	  + "WHERE MP_ADDRESS LIKE ?||'%'||?||'%' AND "
						  + "(REPLACE(MP_ADDRESS, ' ', '') LIKE ? OR "
						  + "UPPER(REPLACE(MP_NAME, ' ', '')) LIKE ? OR "
						  + "REPLACE(MP_KIND, ' ', '') LIKE ? OR "
						  + "UPPER(REPLACE(MP_INFO, ' ', '')) LIKE ? OR "
						  + "UPPER(REPLACE(MP_HASH, ' ', '')) LIKE ?) ";
						
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, city);
			pstmt.setString(2, subcity);
			pstmt.setString(3, "%" + keyword + "%");
			pstmt.setString(4, "%" + keyword + "%");
			pstmt.setString(5, "%" + keyword + "%");
			pstmt.setString(6, "%" + keyword + "%");
			pstmt.setString(7, "%" + keyword + "%");
				
			rs = pstmt.executeQuery();
					
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}
	
	public JsonArray getNotKind (int page, int limit, String city, String subcity, String keyword) { // 도시, 세부지역, 검색어별 전체 맛집의 데이터를 page, limit에 맞춰 가져온다.
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			String place_list = "SELECT * FROM "
					  		  + "(SELECT ROWNUM R, M.* "
					  		  + "FROM (SELECT * FROM MP_TBL "
					  		  + "WHERE MP_ADDRESS LIKE ?||'%'||?||'%' AND "
							  + "(REPLACE(MP_ADDRESS, ' ', '') LIKE ? OR "
							  + "UPPER(REPLACE(MP_NAME, ' ', '')) LIKE ? OR "
							  + "REPLACE(MP_KIND, ' ', '') LIKE ? OR "
							  + "UPPER(REPLACE(MP_INFO, ' ', '')) LIKE ? OR "
							  + "UPPER(REPLACE(MP_HASH, ' ', '')) LIKE ?) "
					  		  + "ORDER BY MP_NAME ASC) M) "
					  		  + "WHERE R >= ? AND R <= ?";
			
			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, city);
			pstmt.setString(2, subcity);
			pstmt.setString(3, "%" + keyword + "%");
			pstmt.setString(4, "%" + keyword + "%");
			pstmt.setString(5, "%" + keyword + "%");
			pstmt.setString(6, "%" + keyword + "%");
			pstmt.setString(7, "%" + keyword + "%");
			pstmt.setInt(8, startrow);
			pstmt.setInt(9, endrow);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("MP_IMG_URL", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("MP_NO", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("MP_NAME", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("MP_RE_CNT", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("MP_VIEW_CNT", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("MP_AVG_GRADE", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}

	// 해시태그 검색인 경우 
	public List<PlaceVO> SearchNotKindHash (int page, int limit, String city, String subcity, String[] search) {
		List<PlaceVO> list = new ArrayList<PlaceVO>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			// #만 입력하고 검색어는 입력하지 않았을 때
			if (search.length == 0) {
				String place_list = "SELECT * FROM "
						+ "(SELECT ROWNUM R, M.* "
						+ "FROM ( SELECT * FROM MP_TBL WHERE " 
						+ "MP_ADDRESS LIKE ?||'%'||?||'%' "
						+ "ORDER BY MP_NAME ASC) M) "
						+ "WHERE R >= ? AND R <= ?";
				
				pstmt = conn.prepareStatement(place_list);
				pstmt.setString(1, city);
				pstmt.setString(2, subcity);
				pstmt.setInt(3, startrow);
				pstmt.setInt(4, endrow);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					PlaceVO vo = new PlaceVO();
					vo.setMP_IMG_URL(rs.getString("MP_IMG_URL"));
					vo.setMP_NO(rs.getInt("MP_NO"));
					vo.setMP_NAME(rs.getString("MP_NAME"));
					vo.setMP_RE_CNT(rs.getInt("MP_RE_CNT"));
					vo.setMP_VIEW_CNT(rs.getInt("MP_VIEW_CNT"));
					vo.setMP_AVG_GRADE(rs.getInt("MP_AVG_GRADE"));
					list.add(vo);
				}
			} else {
				String str = "'%" + search[1] + "%'";
				for (int i = 2; i < search.length; i++) {
					str += " OR UPPER(MP_HASH) LIKE '%" + search[i] + "%' ";
				}
				System.out.println(str);
				String select = "SELECT * FROM MP_TBL WHERE (UPPER(MP_HASH) LIKE " + str;
				System.out.println(select);
				
				String place_list = "SELECT * FROM "
								  + "(SELECT ROWNUM R, M.* "
								  + "FROM (" + select 
								  + ") AND MP_ADDRESS LIKE ?||'%'||?||'%' "
								  + "ORDER BY MP_NAME ASC) M) "
								  + "WHERE R >= ? AND R <= ?";
				
				pstmt = conn.prepareStatement(place_list);
				pstmt.setString(1, city);
				pstmt.setString(2, subcity);
				pstmt.setInt(3, startrow);
				pstmt.setInt(4, endrow);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					PlaceVO vo = new PlaceVO();
					vo.setMP_IMG_URL(rs.getString("MP_IMG_URL"));
					vo.setMP_NO(rs.getInt("MP_NO"));
					vo.setMP_NAME(rs.getString("MP_NAME"));
					vo.setMP_RE_CNT(rs.getInt("MP_RE_CNT"));
					vo.setMP_VIEW_CNT(rs.getInt("MP_VIEW_CNT"));
					vo.setMP_AVG_GRADE(rs.getInt("MP_AVG_GRADE"));
					list.add(vo);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	
	
	// ## 페이지네이션 ##
	public int getNotsCityCount (String city, String kind, String keyword) { // 도시, 음식종류, 검색어별 전체 맛집의 개수를 가져온다.
		int count = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL "
					  	  + "WHERE MP_ADDRESS LIKE ? AND "
					  	  + "MP_KIND LIKE ? AND"
						  + "(REPLACE(MP_ADDRESS, ' ', '') LIKE ? OR "
						  + "UPPER(REPLACE(MP_NAME, ' ', '')) LIKE ? OR "
						  + "REPLACE(MP_KIND, ' ', '') LIKE ? OR "
						  + "UPPER(REPLACE(MP_INFO, ' ', '')) LIKE ? OR "
						  + "UPPER(REPLACE(MP_HASH, ' ', '')) LIKE ?) ";
						
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, city + "%");
			pstmt.setString(2, "%" + kind + "%");
			pstmt.setString(3, "%" + keyword + "%");
			pstmt.setString(4, "%" + keyword + "%");
			pstmt.setString(5, "%" + keyword + "%");
			pstmt.setString(6, "%" + keyword + "%");
			pstmt.setString(7, "%" + keyword + "%");
				
			rs = pstmt.executeQuery();
					
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}
	
	public JsonArray getNotsCity (int page, int limit, String city, String kind, String keyword) { // 도시, 음식종류, 검색어별 전체 맛집의 데이터를 page, limit에 맞춰 가져온다.
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			String place_list = "SELECT * FROM "
			  		  		  + "(SELECT ROWNUM R, M.* "
			  		  		  + "FROM (SELECT * FROM MP_TBL "
			  		  		  + "WHERE MP_ADDRESS LIKE ? AND "
			  		  		  + "MP_KIND LIKE ? AND "
			  		  		  + "(REPLACE(MP_ADDRESS, ' ', '') LIKE ? OR "
			  		  		  + "UPPER(REPLACE(MP_NAME, ' ', '')) LIKE ? OR "
			  		  		  + "REPLACE(MP_KIND, ' ', '') LIKE ? OR "
			  		  		  + "UPPER(REPLACE(MP_INFO, ' ', '')) LIKE ? OR "
			  		  		  + "UPPER(REPLACE(MP_HASH, ' ', '')) LIKE ?) "
			  		  		  + "ORDER BY MP_NAME ASC) M) "
			  		  		  + "WHERE R >= ? AND R <= ?";	
				
			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, city + "%");
			pstmt.setString(2, "%" + kind + "%");
			pstmt.setString(3, "%" + keyword + "%");
			pstmt.setString(4, "%" + keyword + "%");
			pstmt.setString(5, "%" + keyword + "%");
			pstmt.setString(6, "%" + keyword + "%");
			pstmt.setString(7, "%" + keyword + "%");
			pstmt.setInt(8, startrow);
			pstmt.setInt(9, endrow);
			
			rs = pstmt.executeQuery();
				
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("MP_IMG_URL", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("MP_NO", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("MP_NAME", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("MP_RE_CNT", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("MP_VIEW_CNT", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("MP_AVG_GRADE", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	// 해시태그 검색인 경우 
	public List<PlaceVO> SearchNotsCityHash (int page, int limit, String city, String kind, String[] search) {
		List<PlaceVO> list = new ArrayList<PlaceVO>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
				
			if (search.length == 0) {
				String place_list = "SELECT * FROM "
						  + "(SELECT ROWNUM R, M.* "
						  + "FROM (SELECT * FROM MP_TBL WHERE "
						  + "MP_ADDRESS LIKE ? "
						  + "AND (MP_KIND LIKE ?) "
						  + "ORDER BY MP_NAME ASC) M) "
						  + "WHERE R >= ? AND R <= ?";
				
				pstmt = conn.prepareStatement(place_list);
				pstmt.setString(1, city + "%");
				pstmt.setString(2, "%" + kind + "%");
				pstmt.setInt(3, startrow);
				pstmt.setInt(4, endrow);
				rs = pstmt.executeQuery();
					
				while (rs.next()) {
					PlaceVO vo = new PlaceVO();
					vo.setMP_IMG_URL(rs.getString("MP_IMG_URL"));
					vo.setMP_NO(rs.getInt("MP_NO"));
					vo.setMP_NAME(rs.getString("MP_NAME"));
					vo.setMP_RE_CNT(rs.getInt("MP_RE_CNT"));
					vo.setMP_VIEW_CNT(rs.getInt("MP_VIEW_CNT"));
					vo.setMP_AVG_GRADE(rs.getInt("MP_AVG_GRADE"));
					list.add(vo);
				}
			} else {
				String str = "'%" + search[1] + "%'";
				for (int i = 2; i < search.length; i++) {
					str += " OR UPPER(MP_HASH) LIKE '%" + search[i] + "%' ";
				}
				System.out.println(str);
				String select = "SELECT * FROM MP_TBL WHERE (UPPER(MP_HASH) LIKE " + str;
				System.out.println(select);
					
				String place_list = "SELECT * FROM "
								  + "(SELECT ROWNUM R, M.* "
								  + "FROM (" + select 
								  + ") AND MP_ADDRESS LIKE ? "
								  + "AND MP_KIND LIKE ? "
								  + "ORDER BY MP_NAME ASC) M) "
								  + "WHERE R >= ? AND R <= ?";
					
				pstmt = conn.prepareStatement(place_list);
				pstmt.setString(1, city + "%");
				pstmt.setString(2, "%" + kind + "%");
				pstmt.setInt(3, startrow);
				pstmt.setInt(4, endrow);
				rs = pstmt.executeQuery();
					
				while (rs.next()) {
					PlaceVO vo = new PlaceVO();
					vo.setMP_IMG_URL(rs.getString("MP_IMG_URL"));
					vo.setMP_NO(rs.getInt("MP_NO"));
					vo.setMP_NAME(rs.getString("MP_NAME"));
					vo.setMP_RE_CNT(rs.getInt("MP_RE_CNT"));
					vo.setMP_VIEW_CNT(rs.getInt("MP_VIEW_CNT"));
					vo.setMP_AVG_GRADE(rs.getInt("MP_AVG_GRADE"));
					list.add(vo);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	
	
	// ## 페이지네이션 ##
	public int getAllSelCount (String city, String subcity, String kind, String keyword) { // 모든 조건에 해당하는 전체 맛집의 개수를 가져온다.
		int count = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT COUNT(*) FROM MP_TBL "
					  	  + "WHERE MP_ADDRESS LIKE ?||'%'||?||'%' AND "
					  	  + "MP_KIND LIKE ? AND "
					  	  + "(REPLACE(MP_ADDRESS, ' ', '') LIKE ? OR "
					  	  + "UPPER(REPLACE(MP_NAME, ' ', '')) LIKE ? OR "
					  	  + "REPLACE(MP_KIND, ' ', '') LIKE ? OR "
					  	  + "UPPER(REPLACE(MP_INFO, ' ', '')) LIKE ? OR "
					  	  + "UPPER(REPLACE(MP_HASH, ' ', '')) LIKE ?)";
							
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, city);
			pstmt.setString(2, subcity);
			pstmt.setString(3, "%" + kind + "%");
			pstmt.setString(4, "%" + keyword + "%");
			pstmt.setString(5, "%" + keyword + "%");
			pstmt.setString(6, "%" + keyword + "%");
			pstmt.setString(7, "%" + keyword + "%");
			pstmt.setString(8, "%" + keyword + "%");
					
			rs = pstmt.executeQuery();
						
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}
	
	// ## 도시, 지역, 음식종류, 검색어별 전체 맛집 가져오기 ##
	public JsonArray getAllSel (int page, int limit, String city, String subcity, String kind, String keyword) { // 모든 조건을 만족하는 전체 맛집의 데이터를 page, limit에 맞춰 가져온다.
		JsonArray arr = new JsonArray();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			
			String place_list = "SELECT * FROM "
	  		  		  + "(SELECT ROWNUM R, M.* "
	  		  		  + "FROM (SELECT * FROM MP_TBL "
	  		  		  + "WHERE MP_ADDRESS LIKE ?||'%'||?||'%' AND "
					  + "MP_KIND LIKE ? AND "
					  + "(REPLACE(MP_ADDRESS, ' ', '') LIKE ? OR "
					  + "UPPER(REPLACE(MP_NAME, ' ', '')) LIKE ? OR "
					  + "REPLACE(MP_KIND, ' ', '') LIKE ? OR "
					  + "UPPER(REPLACE(MP_INFO, ' ', '')) LIKE ? OR "
					  + "UPPER(REPLACE(MP_HASH, ' ', '')) LIKE ?) "
	  		  		  + "ORDER BY MP_NAME ASC) M) "
	  		  		  + "WHERE R >= ? AND R <= ?";	
				
			pstmt = conn.prepareStatement(place_list);
			pstmt.setString(1, city);
			pstmt.setString(2, subcity);
			pstmt.setString(3, "%" + kind + "%");
			pstmt.setString(4, "%" + keyword + "%");
			pstmt.setString(5, "%" + keyword + "%");
			pstmt.setString(6, "%" + keyword + "%");
			pstmt.setString(7, "%" + keyword + "%");
			pstmt.setString(8, "%" + keyword + "%");
			pstmt.setInt(9, startrow);
			pstmt.setInt(10, endrow);
			rs = pstmt.executeQuery();
				
			while (rs.next()) {
				JsonObject js = new JsonObject();
				js.addProperty("MP_IMG_URL", rs.getString("MP_IMG_URL")); // 맛집 이미지
				js.addProperty("MP_NO", rs.getString("MP_NO")); // 맛집 번호
				js.addProperty("MP_NAME", rs.getString("MP_NAME")); // 맛집 이름
				js.addProperty("MP_RE_CNT", rs.getString("MP_RE_CNT")); // 리뷰수
				js.addProperty("MP_VIEW_CNT", rs.getString("MP_VIEW_CNT")); // 조회수
				js.addProperty("MP_AVG_GRADE", rs.getString("MP_AVG_GRADE")); // 평점
				arr.add(js);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	

	// 해시태그 검색인 경우 
	public List<PlaceVO> SearchAllHash (int page, int limit, String city, String subcity, String kind, String[] search) {
		List<PlaceVO> list = new ArrayList<PlaceVO>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			
			if (search.length == 0) {
				String place_list = "SELECT * FROM "
						  + "(SELECT ROWNUM R, M.* "
						  + "FROM ( SELECT * FROM MP_TBL WHERE " 
						  + "MP_ADDRESS LIKE ?||'%'||?||'%' "
						  + "AND MP_KIND LIKE ? "
						  + "ORDER BY MP_NAME ASC) M) "
						  + "WHERE R >= ? AND R <= ?";
			
				pstmt = conn.prepareStatement(place_list);
				pstmt.setString(1, city);
				pstmt.setString(2, subcity);
				pstmt.setString(3, "%" + kind + "%");
				pstmt.setInt(4, startrow);
				pstmt.setInt(5, endrow);
				rs = pstmt.executeQuery();
					
				while (rs.next()) {
					PlaceVO vo = new PlaceVO();
					vo.setMP_IMG_URL(rs.getString("MP_IMG_URL"));
					vo.setMP_NO(rs.getInt("MP_NO"));
					vo.setMP_NAME(rs.getString("MP_NAME"));
					vo.setMP_RE_CNT(rs.getInt("MP_RE_CNT"));
					vo.setMP_VIEW_CNT(rs.getInt("MP_VIEW_CNT"));
					vo.setMP_AVG_GRADE(rs.getInt("MP_AVG_GRADE"));
					list.add(vo);
				}	
			} else {
				String str = "'%" + search[1] + "%'";
				for (int i = 2; i < search.length; i++) {
					str += " OR UPPER(MP_HASH) LIKE '%" + search[i] + "%' ";
				}
				System.out.println(str);
				String select = "SELECT * FROM MP_TBL WHERE (UPPER(MP_HASH) LIKE " + str;
				System.out.println(select);
					
				String place_list = "SELECT * FROM "
								  + "(SELECT ROWNUM R, M.* "
								  + "FROM (" + select 
								  + ") AND MP_ADDRESS LIKE ?||'%'||?||'%' "
								  + "AND MP_KIND LIKE ? "
								  + "ORDER BY MP_NAME ASC) M) "
								  + "WHERE R >= ? AND R <= ?";
					
				pstmt = conn.prepareStatement(place_list);
				pstmt.setString(1, city);
				pstmt.setString(2, subcity);
				pstmt.setString(3, "%" + kind + "%");
				pstmt.setInt(4, startrow);
				pstmt.setInt(5, endrow);
				rs = pstmt.executeQuery();
					
				while (rs.next()) {
					PlaceVO vo = new PlaceVO();
					vo.setMP_IMG_URL(rs.getString("MP_IMG_URL"));
					vo.setMP_NO(rs.getInt("MP_NO"));
					vo.setMP_NAME(rs.getString("MP_NAME"));
					vo.setMP_RE_CNT(rs.getInt("MP_RE_CNT"));
					vo.setMP_VIEW_CNT(rs.getInt("MP_VIEW_CNT"));
					vo.setMP_AVG_GRADE(rs.getInt("MP_AVG_GRADE"));
					list.add(vo);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	
	// ## 검색을 통해 맛집 찾기 ##
	public List<PlaceVO> searchPlace (String place) {
		List<PlaceVO> list = new ArrayList<PlaceVO>(); 
		try {
			conn = ds.getConnection();
			// MP_NAME 맛집 이름
			// MP_ADDRESS 맛집 주소
			// MP_KIND 음식 종류 
			// MP_INFO 맛집 정보	--> LONG 타입은 LIKE 연산자를 사용할 수가 없어서 일단 포기 
			// MP_HASH 해시태그
			String search = "SELECT * FROM MP_TBL WHERE UPPER(REPLACE(MP_NAME, ' ', '')) LIKE ? "
							  + "OR REPLACE(MP_ADDRESS, ' ', '') LIKE ? "
							  + "OR REPLACE(MP_KIND, ' ', '') LIKE ? "
							  + "OR UPPER(REPLACE(MP_HASH, ' ', '')) LIKE ? "
							  + "ORDER BY MP_NAME";
			// java.sql.SQLSyntaxErrorException: ORA-00932: inconsistent datatypes: expected NUMBER got LONG
			//+ "OR MP_INFO LIKE ? "
			pstmt = conn.prepareStatement(search);
			pstmt.setString(1, "%" + place + "%");
			pstmt.setString(2, "%" + place + "%");
			pstmt.setString(3, "%" + place + "%");
			pstmt.setString(4, "%" + place + "%");
			//pstmt.setString(5, "%" + place + "%");
			rs = pstmt.executeQuery();
			int count = 0;	
			while (rs.next()) {
				count++;
				PlaceVO vo = new PlaceVO();
				vo.setMP_NO(rs.getInt("MP_NO"));					// 번호
				vo.setMP_NAME(rs.getString("MP_NAME"));				// 이름
				vo.setMP_ADDRESS(rs.getString("MP_ADDRESS"));		// 주소
				vo.setMP_PHONE( rs.getString("MP_PHONE"));			// 전화번호
				vo.setMP_KIND(rs.getString("MP_KIND"));				// 종류
				vo.setMP_INFO(rs.getString("MP_INFO"));				// 정보
				vo.setMP_HOURS(rs.getString("MP_HOURS"));			// 운영시간	
				vo.setMP_RE_CNT(rs.getInt("MP_RE_CNT"));			// 리뷰수
				vo.setMP_VIEW_CNT(rs.getInt("MP_VIEW_CNT"));		// 조회수
				vo.setMP_AVG_GRADE(rs.getInt("MP_AVG_GRADE"));	// 평점
				vo.setMP_HASH(rs.getString("MP_HASH"));				// 해시태그
				vo.setMP_REGIDATE(rs.getDate("MP_REGIDATE"));		// 등록일
				vo.setMP_IMG_URL(rs.getString("MP_IMG_URL"));		// 이미지 경로 
				list.add(vo);
			}
			System.out.println("메인에서 검색한 결과는 " + count + "건");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
}
