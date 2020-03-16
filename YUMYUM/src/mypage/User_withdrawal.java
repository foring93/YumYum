package mypage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class User_withdrawal
 */
@WebServlet("/mypage/User_withdrawal")
public class User_withdrawal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public User_withdrawal() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String reason = request.getParameter("reason");
		String nickname = (String)session.getAttribute("nickname");
		int usernum= (int)session.getAttribute("usernum");
		mypageinfoDAO dao = new mypageinfoDAO();
		int result=dao.user_delete(id, reason, nickname, usernum);
		PrintWriter out = response.getWriter();
		System.out.println("회원 탈퇴 dao 갔다옴 "+result);
		if(result > 0) {
			session.invalidate();
			out.write("<script>alert('회원 탈퇴가 완료되었습니다.');location.href='../Main/index';</script>");
		}
		
	}

}
