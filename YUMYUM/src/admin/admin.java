package admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import join.Member;
import owner.ownerDAO;
import review.Y_Review_DAO;
import review.Y_Review_VO;

@WebServlet("/Main/admin")
public class admin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doprocess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		ownerDAO dao= new ownerDAO();
				mpVO m= new mpVO();
				String realFolder="";
				int result=0;
				//WebContent아래에꼭 폴더 생성하세요
				String saveFolder="img";
				int fileSize=10*1024*1024;//업로드할 파일의 최대 사이즈 입니다.
				
				//실제 저장 경로를 지정합니다.
				ServletContext sc= request.getServletContext();
				realFolder= sc.getRealPath(saveFolder);
				
				System.out.println("realFolder="+ realFolder);
				
				
				try {
					MultipartRequest multi= null;
					multi = new MultipartRequest(request,
								realFolder,fileSize,"utf-8", new DefaultFileRenamePolicy());
				//boardbean객체에 글등록 폼에서 입력받은 정보들을 저장합니다.
					String s= multi.getParameter("MP_NAME");
					//입력폼 차있을때 등록하라고
					if(s!=null) {
					m.setMP_NO(Integer.parseInt(
								multi.getParameter("MP_NO")));	
					
					m.setMP_WRITER(
							multi.getParameter("MP_WRITER"));
					
					m.setMP_NAME(multi.getParameter("MP_NAME"));
					
					m.setMP_ADDRESS(multi.getParameter("MP_ADDRESS"));
					
					m.setMP_PHONE(
							multi.getParameter("MP_PHONE"));
					m.setMP_KIND(
							multi.getParameter("MP_KIND"));
					m.setMP_INFO(
							multi.getParameter("MP_INFO"));
					m.setMP_HOURS(
							multi.getParameter("MP_HOURS"));
					m.setMP_HASH(
							multi.getParameter("MP_HASH"));
					
					
					//업로드한 파일명은 업로드한 파일의 전체경로에서 파일이름만 저장합니다.
					m.setMP_IMG_URL(multi.getParameter("MP_IMG_URL"));
					m.setMP_ADD_IMG_URL(multi.getParameter("MP_ADD_IMG_URL"));
					System.out.println("admin.java==="+multi.getFilesystemName("MP_IMG_URL"));
					
					//글등록 처리를 위해DAO의 boardInsert()메서드 호출합니다.
					//글등록 폼에서 입력한 정보가 저장되어있는 boarddata객체를 전달합니다.
					result = dao.ownerInsert(m);
					
					
					
					//글등록에 실패한 경우null을 반환 합니다.
					PrintWriter out= response.getWriter();
					
					if(result==0) {
						System.out.println("게시판 등록 실패");
					out.println("<script>alert('게시판 등록 실패');history.back();</script>");	
						
					}else {
						//승인 요청테이블에서 승인됐는지 안됐는지 
					adminDAO admindao = new adminDAO();
					admindao.mpa_update(m.getMP_NO());
					System.out.println("게시판 등록 완료");
					out.println("<script>alert('승인완료');location.href='admin?page=registerlist';</script>");
					}
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			
		
	
	}
	protected void doprocess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8;");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String check="" + session.getAttribute("user_is_admin");
		if(check!="") {
			boolean is_admin = check.equals("1");
			if(!is_admin)
			{
			   out.write("<script>alert('접근 권한이 없습니다.'); "
			         + "history.back()</script>");
			   return;
			}
		}else {
			 out.write("<script>alert('관리자로 로그인해주세요.'); "
			         + "location.href='../Main/index'</script>");
			   return;
		}
		String page=request.getParameter("page");
		adminDAO dao= new adminDAO();
		int MPA_count  = dao.MPA_count();
		request.setAttribute("MPA_count",MPA_count);
		
		if(page!=null) {
			if(page.equals("boardlist")) {
				String state= request.getParameter("state");
				System.out.println("boardlist="+state);
				if(state==null||state.equals("")) {
			//페이지 네이션
				int pagenum=1;
				int limit=10;
				int listcount;
				//1페이지가아닌것이 넘어오면 페이지가 바뀜
					if(request.getParameter("pagenum")!=null) {
						pagenum=Integer.parseInt(request.getParameter("pagenum"));}
					
				List<mpVO>arr= new ArrayList<mpVO>();
				arr=dao.mp_list(pagenum,limit);
				listcount=dao.getMPlistcount();
			
				if(arr==null) {
				
				}
			
				int maxpage=(listcount+limit-1)/limit;
			
				int startpage=((pagenum-1)/10)*10+1;
			
				int endpage = startpage+10-1;
			
				if(endpage>maxpage) endpage=maxpage;
				request.setAttribute("pagenum", pagenum);//현재 페이지 수
			
				request.setAttribute("maxpage", maxpage);//최대 페이지수
			
				request.setAttribute("startpage",startpage);
				/*현재 페이지에 표시할 끝 페이지의 수*/
				request.setAttribute("endpage", endpage);
				request.setAttribute("listcount",listcount);//총 글의 수
			
				//페이지 네이션 끝
				request.setAttribute("mplist",arr);
				request.setAttribute("page",page);
				RequestDispatcher dis= request.getRequestDispatcher("/Admin/Y_admin.jsp");
				dis.forward(request, response);
				}//에이잭스아닐때 if끝
				else {
					int pagenum=1;
					int limit=10;
					int listcount;
					//1페이지가아닌것이 넘어오면 페이지가 바뀜
						if(request.getParameter("pagenum")!=null) {
							pagenum=Integer.parseInt(request.getParameter("pagenum"));}
						
						
					String searchword=request.getParameter("searchword");
					int value=Integer.parseInt(request.getParameter("value"));
					System.out.println("검색어:"+searchword);
					//검색어가 포함된 총 리스트 카운트
					listcount=dao.getMPlistcount(value, searchword);
					//검색어가 포함된 맛집리스트를 제이슨 어레이로 변환
					JsonArray arr = dao.Search(value,searchword, pagenum, limit);
					
					int maxpage=(listcount+limit-1)/limit;
					
					int startpage=((pagenum-1)/10)*10+1;
				
					int endpage = startpage+10-1;
				
					if(endpage>maxpage) endpage=maxpage;
					JsonObject object = new JsonObject();
					object.addProperty("pagenum", pagenum);
					object.addProperty("maxpage", maxpage);
					object.addProperty("startpage",startpage);
					object.addProperty("endpage", endpage);
					
					arr.add(object);
					Gson gson = new Gson();
					String json = gson.toJson(arr);
					System.out.println(json);
					response.getWriter().append(json);
					
				}
			
			}else if(page.equals("registerlist")) {
				List<mpVO>mparr= new ArrayList<mpVO>();
				mparr=dao.mpa_list();
				request.setAttribute("mpalist", mparr);
				request.setAttribute("page",page);
				RequestDispatcher dis= request.getRequestDispatcher("/Admin/Y_admin.jsp");
				dis.forward(request, response);
			
				//만약페이지가 memberlist일때
				
			}else if(page.equals("memberlist")) {
				List<Member>arr= new ArrayList<Member>();
				String state= request.getParameter("state");
				//ajax로 들어왔을때
				if(state!=null) {
					 String value=request.getParameter("value");
					 //점주를 선택했을때
					 if(value.equals("1")) {
					  arr=dao.jumju_list(); 
					  JsonObject object= new JsonObject(); JsonArray je = new
					  Gson().toJsonTree(arr).getAsJsonArray(); Gson gson= new Gson();
					  object.add("memberlist",je); String json=gson.toJson(object);
					  response.getWriter().append(json);
					  //일반을 선택했을때
					 }else if(value.equals("0")) {
						  arr=dao.ilban_list(); 
						  JsonObject object= new JsonObject(); JsonArray je = new
						  Gson().toJsonTree(arr).getAsJsonArray(); Gson gson= new Gson();
						  object.add("memberlist",je); String json=gson.toJson(object);
						  response.getWriter().append(json);
					//아무것도 안선택할떄
					}else {
						arr=dao.member_list(); 
						  JsonObject object= new JsonObject(); JsonArray je = new
						  Gson().toJsonTree(arr).getAsJsonArray(); Gson gson= new Gson();
						  object.add("memberlist",je); String json=gson.toJson(object);
						  response.getWriter().append(json);
					}
					 
				}
				//page 속성이 아무것도아닐때	
				else{
					arr=dao.member_list();
					request.setAttribute("memberlist",arr);
					request.setAttribute("page",page);
					RequestDispatcher dis= request.getRequestDispatcher("/Admin/Y_admin.jsp");
					dis.forward(request, response);
				}
				
				//승인요청관리 상세페이지
			}else if(page.equals("Mpa_detail")) {
				mpVO mp=new mpVO();
				int num= Integer.parseInt(request.getParameter("MP_NO"));
				mp=dao.mpa_detail(num);
				request.setAttribute("mpa_detail",mp);
				request.setAttribute("page",page);
				RequestDispatcher dis= request.getRequestDispatcher("/Admin/Y_admin.jsp");
				dis.forward(request, response);
			
				//승인요청 거절될때 value=2 로업데이트
			}else if(page.equals("reject")) {
				int num= Integer.parseInt(request.getParameter("MPA_NO"));
				String reject = request.getParameter("reject");
				dao.mpa_reject(num);
				dao.rejectwhy(reject,num);
				request.setAttribute("page","registerlist");
			
			}else if(page.equals("Member_detail")) {// 페이지가 Member_detail 일 때 - 유저 상세 페이지 
				String id = request.getParameter("USER_ID");
				adminDAO admindao = new adminDAO();
				Member m = admindao.Member_detail(id);
				request.setAttribute("m", m);
				request.setAttribute("page",page);
				RequestDispatcher dis= request.getRequestDispatcher("/Admin/Y_admin.jsp");
				dis.forward(request, response);
			}else if(page.equals("Member_delete")) {// 페이지가 Member_delete 일 때 - 유저 삭제
				String id = request.getParameter("USER_ID");
				adminDAO admindao = new adminDAO();
			    admindao.Member_delete(id);
				response.sendRedirect("admin?page=memberlist");
			}else if (page.equals("board_delete")) {//맛집 게시물 삭제 
				int MP_NO = Integer.parseInt(request.getParameter("MP_NO"));//맛집 번호
				boardDAO boarddao = new boardDAO();
				boarddao.board_delete(MP_NO);
				ownerDAO o_dao = new ownerDAO();
				o_dao.mpa_delete(MP_NO+"");
				response.sendRedirect("admin");
			}else if(page.equals("Y_board_m_info")) {//맛집 게시물 수정 눌렀을 때 정보를 띄워줌 
				int MP_NO = Integer.parseInt(request.getParameter("MP_NO"));//맛집 번호
				boardDAO boarddao = new boardDAO();
				mpVO mpvo = boarddao.m_info(MP_NO);
				request.setAttribute("m", mpvo);//다음 페이지로 자바빈을 넘겨줌 
				request.setAttribute("page",page);
				RequestDispatcher dis= request.getRequestDispatcher("/Admin/Y_admin.jsp");
				dis.forward(request, response);
				
				//테마목록 띄워주는 기능
			}else if(page.equals("theme")) {
				request.setAttribute("page",page);
				List<themeVO> arr = dao.ThemeselectAll();
				request.setAttribute("length",arr.size());
				request.setAttribute("THlist",arr);
				RequestDispatcher dis= request.getRequestDispatcher("/Admin/Y_admin.jsp");
				dis.forward(request, response);
				//테마삭제
			}else if(page.equals("TH_delete")) {
				dao.TH_delte(Integer.parseInt(request.getParameter("TH_NO")));
				dao.TH_delete_update(Integer.parseInt(request.getParameter("TH_NO")));
				out.write("<script>location.href='../Main/admin?page=theme'</script>");
				out.close();
				
			}else if(page.equals("TH_select")) {
				request.setAttribute("page",page);
				List<themeVO> arr = dao.Themeselectfive();
				
				request.setAttribute("THlistfive",arr);
				RequestDispatcher dis= request.getRequestDispatcher("/Admin/Y_admin.jsp");
				dis.forward(request, response);
				//리뷰 관리
			}else if(page.equals("reviewlist")) 
			{
				
				System.out.println("reviewList");
				request.setAttribute("page",page);
				Y_Review_DAO rdao = new Y_Review_DAO();
				List<Y_Review_VO> reviewList = rdao.getWholeReviews();
				
				request.setAttribute("reviewlist", reviewList);
				
				RequestDispatcher dis= request.getRequestDispatcher("/Admin/Y_admin.jsp");
				dis.forward(request, response);
			}
			
			
		}else {
		//null일땐디폴트로 띄워주는것
			int pagenum=1;
			int limit=10;
			int listcount;
			//1페이지가아닌것이 넘어오면 페이지가 바뀜
				if(request.getParameter("pagenum")!=null) 
						pagenum=Integer.parseInt(request.getParameter("pagenum"));
				
			//페이지에 찾을 문자열이 null이 아닐경우
				List<mpVO>arr= new ArrayList<mpVO>();
				arr=dao.mp_list(pagenum,limit);
				listcount=dao.getMPlistcount();
			
			if(arr==null) {
				
			}
			
			int maxpage=(listcount+limit-1)/limit;
			
			int startpage=((pagenum-1)/10)*10+1;
			
			int endpage = startpage+10-1;
			
			if(endpage>maxpage) endpage=maxpage;
			request.setAttribute("pagenum", pagenum);//현재 페이지 수
			
			request.setAttribute("maxpage", maxpage);//최대 페이지수
			
			request.setAttribute("startpage",startpage);
			/*현재 페이지에 표시할 끝 페이지의 수*/
			 request.setAttribute("endpage", endpage);
			 request.setAttribute("listcount",listcount);//총 글의 수
			//arr=dao.getList();
			
			//
			request.setAttribute("mplist",arr);
			request.setAttribute("page",page);
			RequestDispatcher dis= request.getRequestDispatcher("/Admin/Y_admin.jsp");
			dis.forward(request, response);
		}
	}
}
