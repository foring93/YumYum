package Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MainDAO {
	DataSource ds;
	Connection con;
	PreparedStatement pstmt; //쿼리 실행해주는 객체
	ResultSet rs;
	int result;
	
	public MainDAO(){
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/yumyum");
		}catch(Exception ex){
			System.out.println("DB 연결 실패 : " + ex);
		}
	}
	public List<BestVO> best5() {
		List<BestVO> arr= new ArrayList<BestVO>();
		
		BestVO b=null;
		StringBuffer sql = new StringBuffer();
		sql.append("select MP_NO, MP_IMG_URL, MP_NAME, SUBSTR(MP_ADDRESS,INSTR(MP_ADDRESS,' ',1,1),INSTR(MP_ADDRESS,' ',1,1)-0) "
				+ "from (select rownum r , b.* from (select * from MP_TBL order by MP_RE_GRADE desc)b) where r>=1 and r<=5");
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			while(rs.next()) {
				b= new BestVO();
				b.setNo(rs.getInt(1)+"");
				b.setImg(rs.getString(2));
				b.setName(rs.getString(3));
				b.setAdress(rs.getString(4));
				
				arr.add(b);
				
			}
			System.out.println("DAO");
			
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
}
