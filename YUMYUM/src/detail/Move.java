package detail;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/M_category/place.move")
public class Move extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("euc-kr");
		
		// 맛집 페이지에서 클릭된 맛집의 번호를 가져온다.
		String test = request.getParameter("mp_no");
		
		DetailDAO dao = new DetailDAO();
		PlaceVO vo = new PlaceVO();
		
		vo = dao.mp_info(test);
		
		// 데이터베이스에서 가져온 해당 맛집 정보를 request 객체에 저장한다. 
		request.setAttribute("mp_info", vo);
																	// 여기에는 맛집 상세페이지 주소를 적으세요.
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Detail/Y_Detail.jsp");
		dispatcher.forward(request, response);
	}
}
