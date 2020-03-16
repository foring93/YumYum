package admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

@WebServlet("/Main/WithdrawUser")
public class admin_WithdrawUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public admin_WithdrawUser() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		adminUserDAO dao = new adminUserDAO();
		JsonArray arr = dao.deleted_user();
		Gson gson = new Gson();
		String result = gson.toJson(arr);
		System.out.println(result);
		response.getWriter().append(result);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String page = request.getParameter("page");
		request.setAttribute("page", page);
		RequestDispatcher dispatcher = request.getRequestDispatcher("../Admin/Y_admin.jsp");
		dispatcher.forward(request, response);
	}

}
