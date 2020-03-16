package idsearch;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/info/idinfo")
public class idinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public idinfo() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("여기는 idinfo 서블릿");
		idsearchDAO dao = new idsearchDAO();
		String name = request.getParameter("name");
		String phone =request.getParameter("phone");
		System.out.println("idinfo에서 찍은 name"+name);
		System.out.println("idinfo에서 찍은 phone"+phone);
		String result = dao.idsearch(name, phone);
		if(result.equals("")) {
			result="해당 정보로 등록된 아이디가 없습니다.";
			request.setAttribute("idsearchresult", result);
		}else {
			result="해당 정보로 조회된 아이디는 "+result + "입니다.";
			request.setAttribute("idsearchresult", result);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("../login/Y_idinfo.jsp");
		dispatcher.forward(request, response);
	}

}
