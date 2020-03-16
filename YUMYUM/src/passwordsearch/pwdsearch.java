package passwordsearch;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/info/pwdsearch")
public class pwdsearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public pwdsearch() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("pwdsearch 서블릿 패스워드 찾기 페이지로 이동");
		RequestDispatcher dispatcher = request.getRequestDispatcher("../login/Y_pwdsearch.jsp");
		dispatcher.forward(request, response);
	}
}
