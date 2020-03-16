package join;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import join.Member;

@WebServlet("/joinAction")
public class JoinAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public JoinAction()
	{
        super();
    }
	protected void doGet(HttpServletRequest request,
						 HttpServletResponse response) throws ServletException, IOException
	{
		
		/*
		 * RequestDispatcher dispatcher = request.getRequestDispatcher
		 * ("join/Y_join.jsp"); dispatcher.forward(request, response);
		 */
	}
	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		//out에 alert창 한글깨짐 해결
		response.setContentType("text/html;charset=utf-8");
		
		Member vo = new Member();
		vo.setUSER_ID(request.getParameter("id"));
		vo.setUSER_PASS(request.getParameter("pass"));
		vo.setUSER_NAME(request.getParameter("name"));
		vo.setUSER_GENDER(request.getParameter("gender"));
		vo.setUSER_NICKNAME(request.getParameter("nickname"));
		//생년월일
		String y = request.getParameter("year");
		String m = request.getParameter("month");
		String d = request.getParameter("day");
		String y_m_d = y+"-"+m+"-"+d;
		vo.setUSER_BIRTHDAY(y_m_d);
		vo.setUSER_ADDRESS(request.getParameter("address"));
		vo.setUSER_POSTCODE(request.getParameter("post1"));
		vo.setUSER_PHONE(request.getParameter("phone"));
		//이메일= 아이디+도메인
		String email_id = request.getParameter("email");
		String domain = request.getParameter("domain");
		String email = email_id +"@"+domain;
		vo.setUSER_EMAIL(email);
		vo.setUSER_IS_OWNER(
				Integer.parseInt(request.getParameter("who"))); //손님,점주 
		vo.setUSER_BUSINESS_NO(request.getParameter("business_no"));
		
		MemberDAO dao = new MemberDAO();
		PrintWriter out = response.getWriter();		
			
		try {
			int result = dao.insert(vo);			
			if(result == 1) {
				out.println("<script>");
				out.println("alert('회원가입 완료')");
				out.println("location.href='Main/index'");
				out.println("</script>");
				//response.sendRedirect("login");
			}else {
				out.println("<script>");
				out.println("alert('회원가입 실패')");
				out.println("location.href='history.back();'");
				out.println("</script>");
			}
		}catch(Exception e) {
			RequestDispatcher dis = request.getRequestDispatcher("jsp/error.jsp");
			System.out.println("SQL문 장애발생으로 데이터 입력 실패");
			e.printStackTrace();
			request.setAttribute("errorMsg", "SQL문 장애발생으로 데이터 입력 실패함!!");
			dis.forward(request, response);
		}
	}
}

