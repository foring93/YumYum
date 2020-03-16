package detail;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/M_category/user_jjim")
public class User_jjim extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		
		
		//접속되어있는 세션가져옴
		HttpSession session = request.getSession();
		
		String id = null;
		//로그인이 안되있으면 찜목록 불러올수없어서 catch걸림
		try {
		id = session.getAttribute("id").toString();
		}catch(Exception e) {
			System.out.println("seesion id = " + id);
		}
				
		int mp_no = Integer.parseInt(request.getParameter("mp_no"));
		
		System.out.println("session아이디 : "+id+", 맛집번호 : "+mp_no);
		
		DetailDAO dao = new DetailDAO();
		
		int result = dao.isJiim(id,mp_no);
		
		response.getWriter().append(Integer.toString(result));
	}
}
