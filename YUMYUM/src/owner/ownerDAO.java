package owner;

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

import admin.mpVO;

public class ownerDAO {
	
	DataSource ds;
	Connection con;
	PreparedStatement pstmt; //쿼리 실행해주는 객체
	ResultSet rs;
	int result;
	
	public ownerDAO(){
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/yumyum");
		}catch(Exception ex){
			System.out.println("DB 연결 실패 : " + ex);
		}
	}
	
	public int ownerInsert(mpVO m){
		StringBuffer sql= new StringBuffer();
		sql.append("insert into MP_TBL values((select nvl(max(MP_NO),0)+1 from MP_TBL),?,?,?,?,?,?,?,0,0,0,0,?,sysdate,?,?)");
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1,m.getMP_WRITER());
			pstmt.setString(2,m.getMP_NAME());
			pstmt.setString(3,m.getMP_ADDRESS());
			pstmt.setString(4,m.getMP_PHONE());
			pstmt.setString(5,m.getMP_KIND());
			String MP_INFO = replaceParameter(m.getMP_INFO());
			pstmt.setString(6,MP_INFO);
			pstmt.setString(7,m.getMP_HOURS());
			String MP_HASH = replaceParameter(m.getMP_HASH());
			pstmt.setString(8,MP_HASH);
			pstmt.setString(9,m.getMP_IMG_URL());
			System.out.println(m.getMP_IMG_URL());
			pstmt.setString(10,m.getMP_ADD_IMG_URL());
			System.out.println(m.getMP_ADD_IMG_URL());
			result=pstmt.executeUpdate();
		}catch (Exception e) {
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
	public int beforeinsert(mpVO m){
		StringBuffer sql= new StringBuffer();
		sql.append("insert into MP_APPROVAL_NEED_TBL values((select nvl(max(MPA_NO),0)+1 from MP_APPROVAL_NEED_TBL),?,?,?,?,?,?,?,?,sysdate,?,?,0,'-')");
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1,m.getMP_WRITER());
			pstmt.setString(2,m.getMP_NAME());
			pstmt.setString(3,m.getMP_ADDRESS());
			pstmt.setString(4,m.getMP_PHONE());
			pstmt.setString(5,m.getMP_KIND());
			String MP_INFO = replaceParameter(m.getMP_INFO());
			pstmt.setString(6,MP_INFO);
			pstmt.setString(7,m.getMP_HOURS());
			String MP_HASH = replaceParameter(m.getMP_HASH());
			pstmt.setString(8,MP_HASH);
			pstmt.setString(9,m.getMP_IMG_URL());
			pstmt.setString(10,m.getMP_ADD_IMG_URL());
			result=pstmt.executeUpdate();
		}catch (Exception e) {
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
	public List<mpVO>mpa_list(String USER_NICKNAME){
		List<mpVO>arr = new ArrayList<mpVO>();
		StringBuffer sql= new StringBuffer();
		mpVO m=null;
		sql.append("select * from MP_APPROVAL_NEED_TBL where MPA_WRITER = ? order by MPA_NO desc");
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, USER_NICKNAME);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				m=new mpVO();
				m.setMP_NO(rs.getInt("MPA_NO"));
				m.setMP_NAME(rs.getString("MPA_NAME"));
				m.setMP_WRITER(rs.getString("MPA_WRITER"));
				m.setMPA_IS_APPROVED(rs.getInt("MPA_IS_APPROVED"));
				m.setMP_REGIDATE(rs.getDate("MPA_REGIDATE"));
				m.setMPA_REJECT_WHY(rs.getString("MPA_REJECT_WHY"));
				arr.add(m);
				
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
		return arr;
		
	}
	public mpVO mpa_modify_show(int MP_NO){
		StringBuffer sql= new StringBuffer();
		mpVO m=null;
		sql.append("select * from MP_APPROVAL_NEED_TBL where MPA_NO = ? ");
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1, MP_NO);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				m=new mpVO();
				m.setMP_NO(rs.getInt("MPA_NO"));
				m.setMP_ADDRESS(rs.getString("MPA_ADDRESS"));
				m.setMP_KIND(rs.getString("MPA_KIND"));
				m.setMP_INFO(rs.getString("MPA_INFO"));
				m.setMP_PHONE(rs.getString("MPA_PHONE"));
				m.setMP_NAME(rs.getString("MPA_NAME"));
				m.setMP_HASH(rs.getString("MPA_HASH"));
				m.setMP_HOURS(rs.getString("MPA_HOURS"));
				m.setMP_WRITER(rs.getString("MPA_WRITER"));
				m.setMPA_IS_APPROVED(rs.getInt("MPA_IS_APPROVED"));
				m.setMP_REGIDATE(rs.getDate("MPA_REGIDATE"));
				m.setMP_IMG_URL(rs.getString("MPA_IMG_URL"));
				String etc_imgs_names = rs.getString("MPA_ADD_IMG_URL");
				if(etc_imgs_names != null)
				{
					etc_imgs_names = etc_imgs_names.replaceAll("\\*", ", ");
					m.setMP_ADD_IMG_URL(etc_imgs_names);
				}
				m.setMP_ADD_IMG_URL(etc_imgs_names);
				
				
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
		return m;
		
	}
	public int mpa_modify(mpVO m){
		StringBuffer sql= new StringBuffer();
		int i=0;
		sql.append("update MP_APPROVAL_NEED_TBL set MPA_ADDRESS = ? ,"
				+ " MPA_KIND =?,MPA_NAME = ?, MPA_PHONE = ?, MPA_INFO = ? ,MPA_HOURS = ? ,"
				+ "MPA_HASH = ? , MPA_IMG_URL = ? , MPA_ADD_IMG_URL = ? , MPA_IS_APPROVED = 0 where MPA_NO = ?");
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1,m.getMP_ADDRESS());
			pstmt.setString(2,m.getMP_KIND());
			pstmt.setString(3,m.getMP_NAME());
			pstmt.setString(4,m.getMP_PHONE());
			String MP_INFO = replaceParameter(m.getMP_INFO());
			pstmt.setString(5,MP_INFO);
			pstmt.setString(6,m.getMP_HOURS());
			String MP_HASH = replaceParameter(m.getMP_HASH());
			pstmt.setString(7,MP_HASH);
			pstmt.setString(8,m.getMP_IMG_URL());
			pstmt.setString(9, m.getMP_ADD_IMG_URL());
			pstmt.setInt(10,m.getMP_NO());
			
			i=pstmt.executeUpdate();
			
			
			
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
		return i;
		
	}
	
	public JsonArray mpa_selectall(){
		JsonArray arr = new JsonArray();
		JsonObject object =null;
		StringBuffer sql= new StringBuffer();
		sql.append("select * from MP_APPROVAL_NEED_TBL order by MPA_REGIDATE desc");
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			while(rs.next()) {
				object = new JsonObject();
				object.addProperty("MPA_NO",(rs.getInt("MPA_NO")));
				object.addProperty("MPA_ADDRESS",(rs.getString("MPA_ADDRESS")));
				object.addProperty("MPA_KIND",(rs.getString("MPA_KIND")));
				object.addProperty("MPA_INFO",(rs.getString("MPA_INFO")));
				object.addProperty("MPA_PHONE",(rs.getString("MPA_PHONE")));
				object.addProperty("MPA_NAME",(rs.getString("MPA_NAME")));
				object.addProperty("MPA_HASH",(rs.getString("MPA_HASH")));
				object.addProperty("MPA_HOURS",(rs.getString("MPA_HOURS")));
				object.addProperty("MPA_WRITER",(rs.getString("MPA_WRITER")));
				object.addProperty("MPA_IS_APPROVED",(rs.getInt("MPA_IS_APPROVED")));
				object.addProperty("MPA_REGIDATE",(rs.getString("MPA_REGIDATE")));
				object.addProperty("MP_IMG_URL",(rs.getString("MPA_IMG_URL")));
				object.addProperty("MPA_ADD_IMG_URL",(rs.getString("MPA_ADD_IMG_URL")));
				object.addProperty("MPA_REJECT_WHY", (rs.getString("MPA_REJECT_WHY")));
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
	            if (con != null) {
	               con.close();
	               con = null;
	            }

	         } catch (SQLException e) {
	            e.printStackTrace();
	         }

	      }//finally end
		return arr;
		
	}
	public int mpa_delete(String MP_NO) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from MP_APPROVAL_NEED_TBL where MPA_NO in("+MP_NO+")");
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			result=pstmt.executeUpdate();
		}catch (Exception e) {
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
