package detail;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/M_category/jjimCheck")
public class Jjim extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		
		
		//접속되어있는 세션가져옴
		HttpSession session = request.getSession();		
		String id = session.getAttribute("id").toString();
		//String id = request.getParameter("id");
		int mp_no = Integer.parseInt(request.getParameter("mp_no"));
		String jjim_img = request.getParameter("jjim_img");
		
		System.out.println("session아이디 : "+id+", 맛집번호 : "+mp_no + 
							", 찜상태 : "+ jjim_img);
		
		DetailDAO dao = new DetailDAO();
		
		//찜상태가 icon2면 delete(찜삭제), icon1이면 insert(찜하기)
		if(jjim_img.equals("../img/icon1.png")) {
			//찜하기 클릭시
			dao.jjim_insert(id,mp_no);
		}else {
			//찜취소 클릭시
			dao.jjim_delete(id,mp_no);
		}
	}
}
