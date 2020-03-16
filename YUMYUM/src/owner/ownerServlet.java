package owner;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
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
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import admin.mpVO;
@WebServlet("/owner/owner")
public class ownerServlet extends HttpServlet {
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
				int fileSize=1000*1024*1024;//업로드할 파일의 최대 사이즈 입니다.
				
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
					
					m.setMP_IMG_URL(multi.getFilesystemName("rep_img_file"));
					System.out.println(multi.getFilesystemName("rep_img_file"));
					String etc_imgs_names = multi.getParameter("etc_imgs_names");
					if(etc_imgs_names != null)
					{
						etc_imgs_names = etc_imgs_names.replaceAll(", ", "*");
						m.setMP_ADD_IMG_URL(etc_imgs_names);
					}
					System.out.println(etc_imgs_names);
					
					
					//글등록 처리를 위해DAO의 boardInsert()메서드 호출합니다.
					//글등록 폼에서 입력한 정보가 저장되어있는 boarddata객체를 전달합니다.
					result = dao.beforeinsert(m);
					
					//글등록에 실패한 경우null을 반환 합니다.
					PrintWriter out= response.getWriter();
					
					if(result==0) {
					out.println("<script>alert('요청실패! 다시입력해주세요');history.back();</script>");	
						
					}else {
					out.println("<script>alert('요청완료');location.href='owner?page=registrationlist';</script>");
					}
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			
		
	}
	
	protected void doprocess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=utf-8");
	String page=request.getParameter("page");
	ownerDAO dao = new ownerDAO();
	if(page!=null) {
		List<mpVO> arr= new ArrayList<mpVO>();
		request.setAttribute("page",page);
		if(page.equals("registrationlist")) {
			HttpSession session= request.getSession();
			String USER_NICKNAME=(String)session.getAttribute("nickname");
			arr=dao.mpa_list(USER_NICKNAME);
			//페이지가 승인요청으로 들어오면 지가 승인 요청한거보여주고 현재상태 나타내줌 반려인지 승인인지 대기중인지
			request.setAttribute("mpa_my_list",arr);
			RequestDispatcher dis= request.getRequestDispatcher("ownerpage.jsp");
			dis.forward(request, response);
		}
		if(page.equals("registrationForm")) {
			RequestDispatcher dis= request.getRequestDispatcher("ownerpage.jsp");
			dis.forward(request, response);
		}
		if(page.equals("Mpa_modify")) {
			request.setAttribute("page",page);
			mpVO m=dao.mpa_modify_show(Integer.parseInt(request.getParameter("MP_NO")));
			request.setAttribute("mpa_detail",m);
			RequestDispatcher dis= request.getRequestDispatcher("ownerpage.jsp");
			dis.forward(request, response);
			
			
			//요청철회 
		}if(page.equals("Mpa_cancel")) {
			//12/03추가할것 철회 요청 들어온 MP_NO삭제하고 제이슨 어레이 타입으로 select 해주는것
			request.setAttribute("page",page);
			Gson gson = new Gson();
			String num=request.getParameter("MP_NO");
			dao.mpa_delete(num);
			JsonArray jsonarr= dao.mpa_selectall();
			String mpa_after_delete= gson.toJson(jsonarr);
			response.getWriter().append(mpa_after_delete);
		}
		
	}else {
		RequestDispatcher dis= request.getRequestDispatcher("ownerpage.jsp");
		dis.forward(request, response);
	}
	
	
	
	
	}

}
