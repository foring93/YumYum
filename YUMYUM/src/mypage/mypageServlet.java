package mypage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mypage/index")
public class mypageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public mypageServlet() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request,
    		HttpServletResponse response) throws IOException, ServletException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("../mypage/Y_mypage.jsp");
		dispatcher.forward(request, response);
		
	}

}
