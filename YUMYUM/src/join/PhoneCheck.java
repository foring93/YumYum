package join;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PhoneCheck")

public class PhoneCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public PhoneCheck() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("post");
		MemberDAO dao = new MemberDAO();
		
		
		int result = dao.isPhone(request.getParameter("phone"));
		response.getWriter().append(Integer.toString(result));
	}
}
