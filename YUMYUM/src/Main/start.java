package Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.adminDAO;
import admin.themeVO;

@WebServlet("/Main/index")
public class start extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 protected void doProcess(HttpServletRequest request,
	    		HttpServletResponse response) throws IOException, ServletException {
		 request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			MainDAO dao= new MainDAO();
			List<BestVO> arr = new ArrayList<BestVO>();
			arr=dao.best5();
			adminDAO adao = new adminDAO();
			List<themeVO>tarr=adao.Themeselectfive();
			request.setAttribute("best4",arr);
			request.setAttribute("TH_list", tarr);
			RequestDispatcher dis= request.getRequestDispatcher("/Main/Y_index.jsp");
			dis.forward(request,response);
	 }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doProcess(request, response);
	}

}
