package passwordsearch;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/info/pwdinfo")
public class pwdinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public pwdinfo() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ajax post 방식
		System.out.println("pwdinfo여기는 서블릿");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String phone = request.getParameter("phone");
		passsearchDAO dao = new passsearchDAO();
		String pass = dao.passwordsearch(name, id, phone);
		System.out.println("DAO갔다가 비밀번호 찾아옴"+pass);
		HttpSession session = request.getSession();
		session.setAttribute("pass", pass);
		response.getWriter().append(pass);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//location.href 
		RequestDispatcher dispatcher = request.getRequestDispatcher("../login/Y_passinfo.jsp");
		dispatcher.forward(request, response);
	}

}
