package detail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DetailDAO {
	private DataSource ds;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public DetailDAO() {
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

	// ## 맛집 카테고리에서 클릭하여 상세 페이지로 이동 시 가져갈 맛집 정보 ##
	public PlaceVO mp_info(String mp_no) {
		PlaceVO vo = null;
		try {
			conn = ds.getConnection();
			String select = "SELECT MP_NO,MP_NAME,MP_ADDRESS,MP_PHONE, "
							+ "		MP_KIND,MP_INFO,MP_HOURS,MP_RE_CNT,MP_VIEW_CNT, "
							+ "		MP_RE_GRADE,MP_REGIDATE,ROUND(MP_AVG_GRADE,1)MP_AVG_GRADE, "
							+ "		MP_HASH,MP_REGIDATE,MP_IMG_URL,"
							+ "		MP_ADD_IMG_URL "
							+ "FROM MP_TBL WHERE MP_NO = ?";
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, mp_no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new PlaceVO();
				vo.setMP_NO(rs.getInt("MP_NO"));
				vo.setMP_NAME(rs.getString("MP_NAME"));
				vo.setMP_ADDRESS(rs.getString("MP_ADDRESS"));
				vo.setMP_PHONE(rs.getString("MP_PHONE"));
				vo.setMP_KIND(rs.getString("MP_KIND"));
				vo.setMP_INFO(rs.getString("MP_INFO"));
				vo.setMP_HOURS(rs.getString("MP_HOURS"));
				vo.setMP_RE_CNT(rs.getInt("MP_RE_CNT"));
				vo.setMP_VIEW_CNT(rs.getInt("MP_VIEW_CNT"));
				vo.setMP_RE_GRADE(rs.getInt("MP_RE_GRADE"));
				vo.setMP_REGIDATE(rs.getDate("MP_REGIDATE"));
				vo.setMP_AVG_GRADE(rs.getString("MP_AVG_GRADE"));
				vo.setMP_HASH(rs.getString("MP_HASH"));
				vo.setMP_REGIDATE(rs.getDate("MP_REGIDATE"));
				vo.setMP_IMG_URL(rs.getString("MP_IMG_URL"));
				vo.setMP_ADD_IMG_URL(rs.getString("MP_ADD_IMG_URL"));
			}
			
			//내용을 확일할 글의 조회수를 증가시킵니다.
			String setViewCount = "UPDATE MP_TBL "
							+ "SET MP_VIEW_CNT = MP_VIEW_CNT +1 "
							+ "WHERE MP_NO = ?";
			pstmt = conn.prepareStatement(setViewCount);
			pstmt.setInt(1, Integer.parseInt(mp_no));
			int result = pstmt.executeUpdate();
			if(result == 1) {
				System.out.println("조회수 증가 + 1");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return vo;
	}
	/* mp_info */
	
	//찜하기 insert
	public void jjim_insert(String id, int mp_no) {
		try {
			conn = ds.getConnection();
			String select = "SELECT USER_NO FROM USER_TBL WHERE USER_ID = ?";
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			int user_no = 0;
			if(rs.next()) {
				user_no = Integer.parseInt(rs.getString("USER_NO"));
			}
			
			pstmt = conn.prepareStatement("INSERT INTO MY_TBL VALUES("+user_no+",?)");
			pstmt.setInt(1, mp_no);
			int result = pstmt.executeUpdate();
			if(result == 1) {
				System.out.println("찜하기 성공");
			}else {
				System.out.println("찜하기 실패");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
	/* jjim_insert */

	//찜 delete
	public void jjim_delete(String id, int mp_no) {
		try {
			conn = ds.getConnection();
			String select = "SELECT USER_NO FROM USER_TBL WHERE USER_ID = ?";
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			int user_no = 0;
			if(rs.next()) {
				user_no = Integer.parseInt(rs.getString("USER_NO"));
			}
			
			String delete = "delete my_tbl where MY_USER_NO="+user_no+" and MY_MP_NO = ?";
			pstmt = conn.prepareStatement(delete);
			pstmt.setInt(1, mp_no);			
			
			int result = pstmt.executeUpdate();
			if(result == 1) {
				System.out.println("찜취소 성공");
			}else {
				System.out.println("찜취소 실패");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}		
	}
	/* jjim_delete */
	
	// ## 내가 찜한맛집 찜버튼 유지 ##
	public int isJiim(String id, int mp_no) {
		int result = 0;
		try {
			conn = ds.getConnection();
			String select = "SELECT USER_NO FROM USER_TBL WHERE USER_ID = ?";
			pstmt = conn.prepareStatement(select);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			int user_no = 0;
			if(rs.next()) {
				user_no = Integer.parseInt(rs.getString("USER_NO"));
			}
			
			pstmt = conn.prepareStatement("SELECT count(*) FROM MY_TBL "
					+ "WHERE MY_USER_NO ="+user_no+" AND MY_MP_NO = ?");
			
			pstmt.setInt(1, mp_no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);	
			}
			if(result == 1) {
				System.out.println("찜목록에 있는 맛집");
			}else {
				System.out.println("찜목록에 없는 맛집 ");
			}
						
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return result;
	}
	/* isJiim */
	
	//마이페이지에서 내가 찜한 목록 가져오기 
	public JsonArray MyDibsList(String id) {
		JsonArray arr = new JsonArray();
		//내 번호를 골라서 찜목록에 저장한 맛집번호 불러오기
		ResultSet rs2 = null;
		String sql = " SELECT MY_MP_NO FROM MY_TBL "
								+ "WHERE MY_USER_NO =(SELECT USER_NO "
								+ "							FROM USER_TBL "
								+ "							WHERE USER_ID=?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {//맛집 번호가 존재하는 동안 아래 쿼리 실행 후 JsonArray에 add
				sql = "SELECT * FROM MP_TBL WHERE MP_NO=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, rs.getInt(1));
				rs2= pstmt.executeQuery();
				if(rs2.next()) {
					JsonObject object = new JsonObject();
					object.addProperty("MP_NO", rs2.getInt("MP_NO"));
					object.addProperty("MP_WRITER", rs2.getString("MP_WRITER"));
					object.addProperty("MP_NAME", rs2.getString("MP_NAME"));
					object.addProperty("MP_ADDRESS", rs2.getString("MP_ADDRESS"));
					object.addProperty("MP_PHONE", rs2.getString("MP_PHONE"));
					object.addProperty("MP_KIND", rs2.getString("MP_KIND"));
					object.addProperty("MP_INFO", rs2.getString("MP_INFO"));
					object.addProperty("MP_HOURS", rs2.getString("MP_HOURS"));
					object.addProperty("MP_RE_CNT", rs2.getInt("MP_RE_CNT"));
					object.addProperty("MP_VIEW_CNT", rs2.getInt("MP_VIEW_CNT"));
					object.addProperty("MP_RE_GRADE", rs2.getInt("MP_RE_GRADE"));
					object.addProperty("MP_AVG_GRADE", rs2.getInt("MP_AVG_GRADE"));
					object.addProperty("MP_HASH", rs2.getString("MP_HASH"));
					object.addProperty("MP_REGIDATE", rs2.getString("MP_REGIDATE"));
					object.addProperty("MP_IMG_URL", rs2.getString("MP_IMG_URL"));
					object.addProperty("MP_ADD_IMG_URL", rs2.getString("MP_ADD_IMG_URL"));
					//JsonArray에 object 추가
					arr.add(object);
					}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
			try {
				if (rs2 != null)
				    rs2.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return arr;
	}
	/* MyDibsList */
	
	// ## 추천 맛집 주소로 뽑아오기 ##
	public JsonArray recommend_mp(String mp_address, int mp_no) {
		
		JsonArray recomList = new JsonArray();
		
		try {
			conn = ds.getConnection();
			//랜덤으로 3개 근처 지역 뽑아옴
//			String sql = "select s.* from "
//						+ "		(select mp_name, mp_address, mp_img_url, mp_no "
//						+ "		from mp_tbl where mp_address "
//						+ "		like ? order by DBMS_RANDOM.VALUE)s "
//						+ "where rownum <4 and mp_no != ?"; //mp_no가 같은맛집은 걸러줌
			
			//탑3 근처 지역 맛집 뽑아옴
			String sql = "select s.* from "
						+ "		(select mp_name, mp_address, mp_img_url, mp_no, mp_avg_grade "
						+ "		from mp_tbl "
						+ "		where mp_address like ? "
						+ "		order by mp_avg_grade desc)s "
						+ "where rownum <4 and mp_no != ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+mp_address+"%");
			pstmt.setInt(2, mp_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				JsonObject Object = new JsonObject();
				
				Object.addProperty("mp_name", rs.getString(1));
				Object.addProperty("mp_address", rs.getString(2).split("\\s")[0]+" "+rs.getString(2).split("\\s")[1]);
				Object.addProperty("mp_img_url", rs.getString(3));
				Object.addProperty("mp_no", rs.getInt(4));
				
				//JsonArray에 addProperty로 값 넣어줌
				recomList.add(Object);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		return recomList;
	}
	/* recomend_mp */
}
