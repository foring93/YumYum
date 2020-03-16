package admin;

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

import join.Member;

public class adminDAO {
	DataSource ds;
	Connection con;
	PreparedStatement pstmt; //쿼리 실행해주는 객체
	ResultSet rs;
	int result;
	
	public adminDAO(){
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/yumyum");
		}catch(Exception ex){
			System.out.println("DB 연결 실패 : " + ex);
		}
	}
	
	public List<mpVO>mp_list(int page , int limit){
		StringBuffer sql= new StringBuffer();
		sql.append("select * from (select rownum r , b.* from "
				+ "(select * from MP_TBL order by MP_REGIDATE desc)b) "
				+ "where r >=? and r<=?");
		
		int startrow = (page-1)*limit+1;
		int endrow=startrow+limit-1;
		List<mpVO>arr = new ArrayList<mpVO>();
		mpVO m=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1, startrow);
			pstmt.setInt(2,endrow);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				m=new mpVO();
				m.setMP_NO(rs.getInt("MP_NO"));
				m.setMP_NAME(rs.getString("MP_NAME"));
				m.setMP_AVG_GRADE(rs.getFloat("MP_AVG_GRADE"));
				m.setMP_VIEW_CNT(rs.getInt("MP_VIEW_CNT"));
				m.setMP_REGIDATE(rs.getDate("MP_REGIDATE"));
				m.setMP_RE_CNT(rs.getInt("MP_RE_CNT"));
				m.setMP_WRITER(rs.getString("MP_WRITER"));
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
	public List<Member>member_list(){
		
		List<Member> arr= new ArrayList<Member>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from USER_TBL where USER_IS_ADMIN !=1 order by USER_REGIDATE desc");
		Member m= null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			while(rs.next()) {
				m= new Member();
				m.setUSER_ID(rs.getString("USER_ID"));
				m.setUSER_NAME(rs.getString("USER_NAME"));
				m.setUSER_NICKNAME(rs.getString("USER_NICKNAME"));
				m.setUSER_REGIDATE(rs.getDate("USER_REGIDATE"));
				m.setUSER_IS_OWNER(rs.getInt("USER_IS_OWNER"));
				arr.add(m);
			}
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
		return arr;
	}
	public List<Member>jumju_list(){
		List<Member> arr= new ArrayList<Member>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from USER_TBL where USER_IS_OWNER=1 order by USER_REGIDATE ");
		Member m= null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			while(rs.next()) {
				m= new Member();
				m.setUSER_ID(rs.getString("USER_ID"));
				m.setUSER_NAME(rs.getString("USER_NAME"));
				m.setUSER_NICKNAME(rs.getString("USER_NICKNAME"));
				m.setUSER_REGIDATE(rs.getDate("USER_REGIDATE"));
				m.setUSER_IS_OWNER(rs.getInt("USER_IS_OWNER"));
				arr.add(m);
			}
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
		return arr;
	}	
	public List<Member>ilban_list(){
		List<Member> arr= new ArrayList<Member>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from USER_TBL where USER_IS_OWNER=0  and USER_IS_ADMIN!=1 order by USER_REGIDATE ");
		Member m= null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			while(rs.next()) {
				m= new Member();
				m.setUSER_ID(rs.getString("USER_ID"));
				m.setUSER_NAME(rs.getString("USER_NAME"));
				m.setUSER_NICKNAME(rs.getString("USER_NICKNAME"));
				m.setUSER_REGIDATE(rs.getDate("USER_REGIDATE"));
				m.setUSER_IS_OWNER(rs.getInt("USER_IS_OWNER"));
				arr.add(m);
			}
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
		return arr;
	}	
	public List<mpVO>mpa_list(){
		List<mpVO>arr = new ArrayList<mpVO>();
		StringBuffer sql= new StringBuffer();
		mpVO m=null;
		sql.append("select * from MP_APPROVAL_NEED_TBL where MPA_IS_APPROVED = 0 order by MPA_REGIDATE desc");
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			while(rs.next()) {
				m=new mpVO();
				m.setMP_NO(rs.getInt("MPA_NO"));
				m.setMP_NAME(rs.getString("MPA_NAME"));
				m.setMP_WRITER(rs.getString("MPA_WRITER"));
				m.setMP_REGIDATE(rs.getDate("MPA_REGIDATE"));
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
	
	public mpVO mpa_detail(int MPA_NO){
		mpVO m= new mpVO();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from MP_APPROVAL_NEED_TBL where MPA_NO = ? ");
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1,MPA_NO);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				m.setMP_NO(rs.getInt("MPA_NO"));
				m.setMP_WRITER(rs.getString("MPA_WRITER"));
				m.setMP_NAME(rs.getString("MPA_NAME"));
				m.setMP_ADDRESS(rs.getString("MPA_ADDRESS"));
				m.setMP_PHONE(rs.getString("MPA_PHONE"));
				m.setMP_KIND(rs.getString("MPA_KIND"));
				m.setMP_INFO(rs.getString("MPA_INFO"));
				m.setMP_HOURS(rs.getString("MPA_HOURS"));
				m.setMP_HASH(rs.getString("MPA_HASH"));
				m.setMP_REGIDATE(rs.getDate("MPA_REGIDATE"));
				m.setMP_IMG_URL(rs.getString("MPA_IMG_URL"));
				m.setMP_ADD_IMG_URL(rs.getString("MPA_ADD_IMG_URL"));
				m.setMPA_IS_APPROVED(rs.getInt("MPA_IS_APPROVED"));
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
	
	public int  mpa_update(int mp_no) {
		StringBuffer sql= new StringBuffer();
		sql.append("update MP_APPROVAL_NEED_TBL set MPA_IS_APPROVED = 1 where MPA_NO = ?");
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1,mp_no);
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
	public int  mpa_reject(int mp_no) {
		StringBuffer sql= new StringBuffer();
		sql.append("update MP_APPROVAL_NEED_TBL set MPA_IS_APPROVED = 2 where MPA_NO = ?");
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1,mp_no);
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
	public Member Member_detail(String id) {
		Member m = new Member();
		String sql = "SELECT * FROM USER_TBL WHERE USER_ID=?";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs= pstmt.executeQuery();
			if(rs.next()) {
				m.setUSER_ID(rs.getString("USER_ID"));
				m.setUSER_PASS(rs.getString("USER_PASS"));
				m.setUSER_NAME(rs.getString("USER_NAME"));
				m.setUSER_BIRTHDAY(rs.getString("USER_BIRTHDAY"));
				m.setUSER_GENDER(rs.getString("USER_GENDER"));
				m.setUSER_IS_OWNER(rs.getInt("USER_IS_OWNER"));
				m.setUSER_BUSINESS_NO(rs.getString("USER_BUSINESS_NO"));
				m.setUSER_EMAIL(rs.getString("USER_EMAIL"));
				m.setUSER_POSTCODE(rs.getString("USER_POSTCODE"));
				m.setUSER_ADDRESS(rs.getString("USER_ADDRESS"));
				m.setUSER_PHONE(rs.getString("USER_PHONE"));
				m.setUSER_NICKNAME(rs.getString("USER_NICKNAME"));
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
	public int Member_delete(String id) {
		String sql = "DELETE FROM USER_TBL WHERE USER_ID=?";
		int result = 0;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
			if(result ==1) {
				System.out.println("user 삭제 성공");
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
		return result;
		
	}
	public int rejectwhy(String rejectwhy,int MPA_NO) {
		String sql = "update MP_APPROVAL_NEED_TBL set MPA_REJECT_WHY= ? where MPA_NO = ? ";
		int result = 0;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, rejectwhy);
			pstmt.setInt(2,MPA_NO);
			result = pstmt.executeUpdate();
			if(result ==1) {
				System.out.println("거절된 이유 입력 성공");
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
		return result;
		
	}
	public int getMPlistcount() {
		int listcount=0;
		StringBuffer sql= new StringBuffer();
		sql.append("select count(*) from MP_TBL");
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			if(rs.next()) {
				listcount=rs.getInt(1);
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

	     }
		
		return listcount;
	}
	public JsonArray Search(int search,String s,int page,int limit){
		JsonArray arr= new JsonArray();
		JsonObject object=null;
		//선택한 selector
		String col[]= {"MP_NAME","MP_RE_GRADE","MP_RE_GRADE","MP_VIEW_CNT","MP_WRITER","MP_REGIDATE"};
		String sql="select * from(select rownum r , b.* from(select * from MP_TBL where "+col[search]+" like  ? )b)where r >=? and r<=?";
		int startrow = (page-1)*limit+1;
		int endrow=startrow+limit-1;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1,"%"+s+"%");
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, endrow);
			//System.out.println(sql.toString());
			rs=pstmt.executeQuery();
			while(rs.next()) {
				object= new JsonObject();
				object.addProperty("MP_NO",(rs.getInt("MP_NO")));
				object.addProperty("MP_NAME",(rs.getString("MP_NAME")));
				object.addProperty("MP_AVG_GRADE",(rs.getString("MP_AVG_GRADE")));
				object.addProperty("MP_RE_CNT",(rs.getString("MP_RE_CNT")));
				object.addProperty("MP_VIEW_CNT",(rs.getString("MP_VIEW_CNT")));
				object.addProperty("MP_WRITER",(rs.getString("MP_WRITER")));
				object.addProperty("MP_REGIDATE",(rs.getString("MP_REGIDATE")));
				arr.add(object);
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
		return arr;
	}//search end
	public int getMPlistcount(int value, String searchword) {
		int listcount=0;
		String col[]= {"MP_NAME","MP_RE_GRADE","MP_RE_GRADE","MP_VIEW_CNT","MP_WRITER","MP_REGIDATE"};
		String sql="select count(*) from(select rownum r , b.* from(select * from MP_TBL where "+col[value]+" like  ? )b)";
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1,"%"+searchword+"%");
			rs=pstmt.executeQuery();
			if(rs.next()) {
				listcount=rs.getInt(1);
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

	     }
		
		return listcount;
	}
	//테마 insert
	public int insert_TH(String TH_TITLE,String TH_IMG_URL,String TH_HASH) {
		
		String sql="insert into THEME_TBL values((select NVL(max(TH_NO)+1,6) from THEME_TBL where TH_NO >5 ),?,?,?)";
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1,TH_IMG_URL);
			pstmt.setString(2,TH_TITLE);
			pstmt.setString(3,TH_HASH);
			result = pstmt.executeUpdate();
			
			
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

	     }
		
		return result;
	}

	//승인요청 수 세어주는 메서드
	public int MPA_count() {
		String sql="select count(*) from MP_APPROVAL_NEED_TBL where MPA_IS_APPROVED = 0";
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			
			rs= pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
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

	     }
		
		return result;
		
	}
	//전체 테마 목록 불러오는 메서드
	public List<themeVO> ThemeselectAll() {
		String sql="select * from THEME_TBL order by TH_NO";
		List<themeVO> arr= new ArrayList<themeVO>();
		themeVO t = null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			
			rs= pstmt.executeQuery();
			while(rs.next()) {
				t= new themeVO();
				t.setTH_NO(rs.getInt("TH_NO"));
				t.setTH_HASH(rs.getString("TH_HASH"));
				t.setTH_TITLE(rs.getString("TH_TITLE"));
				t.setTH_IMG_URL(rs.getString("TH_IMG_URL"));
				arr.add(t);
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

	     }
		
		return arr;
		
	}
	//테마 삭제
	public int TH_delte(int TH_NO) {
		String sql="delete from THEME_TBL where TH_NO = "+TH_NO;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			
			result= pstmt.executeUpdate();
			
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

	     }
		
		return result;
		
	}
	
	//테마번호 1-6까지 뽑아오는 것 
	public List<themeVO> Themeselectfive() {
		String sql="select * from THEME_TBL where TH_NO between 1 and 6 order by TH_NO";
		List<themeVO> arr= new ArrayList<themeVO>();
		themeVO t = null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			
			rs= pstmt.executeQuery();
			while(rs.next()) {
				t= new themeVO();
				t.setTH_NO(rs.getInt("TH_NO"));
				t.setTH_HASH(rs.getString("TH_HASH"));
				t.setTH_TITLE(rs.getString("TH_TITLE"));
				t.setTH_IMG_URL(rs.getString("TH_IMG_URL"));
				arr.add(t);
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

	     }
		
		return arr;
		
	}
	
	//선택 테마 업데이트
	public int TH_update(int TH_NO,String change) {
		int change_TH_NO=0;
		String check="select TH_NO from THEME_TBL where TH_NO = "+TH_NO;
		//원래있던 테마를 뒤의 번호로 바꿔줌다.
		String sql="update THEME_TBL set TH_NO=(select max(TH_NO)+1 from THEME_TBL) where TH_NO = "+TH_NO;
		//선택한 테마이름이 속한 TH_NO를 구함
		String sql2="select TH_NO from THEME_TBL where TH_TITLE = ?";
//		String sql2="select TH_NO from THEME_TBL where TH_TITLE like '%"+change+"%'";
		//노출되고 싶은 TH_NO 에 테마이름의 TH_NO를 넣어준다.
		String sql3="update THEME_TBL set TH_NO = "+TH_NO+" where TH_NO= ?";
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(check.toString());
			rs= pstmt.executeQuery();
			
			if(rs.next()){
			pstmt.close();
			pstmt=con.prepareStatement(sql.toString());
			result = pstmt.executeUpdate();
			
			}
			pstmt = null;
			
			pstmt=con.prepareStatement(sql2.toString());
			pstmt.setString(1,change);
			rs= pstmt.executeQuery();
			if(rs.next()) {
				change_TH_NO = rs.getInt("TH_NO");
				
			}
			if(change_TH_NO==0) {
				return 0;
			}
			pstmt.close();
			System.out.println("바뀌어질 번호:"+change_TH_NO);
			pstmt=con.prepareStatement(sql3.toString());
			pstmt.setInt(1,change_TH_NO);
			result =pstmt.executeUpdate();
			
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

	     }
		
		return result;
		
	}
	
	public int TH_delete_update(int TH_NO) {
		String sql="update THEME_TBL set TH_NO=TH_NO-1 where TH_NO > "+TH_NO+"and TH_NO <6";
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.executeUpdate();
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

	     }
		
		return result;
		
	}
	public int TH_find(String TH_TITLE) {
		String sql="select count(*) from THEME_TBL where TH_TITLE = ?";
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, TH_TITLE);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
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

	     }
		
		return result;
		
	}
	
	
}
