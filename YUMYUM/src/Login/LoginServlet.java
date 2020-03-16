package Login;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int cnt =0;
	public LoginServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("여기는 서블릿");
		HttpSession session = request.getSession();
		String result = "";
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		IdjudgeDAO dao = new IdjudgeDAO();
		
		try {
			if(session.getAttribute("id")!=null) {
				response.getWriter().append("true");
			}else {
				//id가 존재하면 true 존재하지 않으면 false를 반환
				boolean resultdao = dao.idsearch(id, password);
				System.out.println("여기는 서블릿 DAO 갔다옴 : " + resultdao);
				result = String.valueOf(resultdao);
				if (result.equals("true")) {
					List list = dao.Userinfo(id);
					session.setAttribute("id", id);
					session.setAttribute("usernum", list.get(0));
					session.setAttribute("nickname", list.get(1));
					session.setAttribute("user_is_admin", list.get(2));
					session.setAttribute("point", list.get(3));
					session.setAttribute("user_is_owner", list.get(4));
					System.out.println("login서블릿 결과는 = "+result);
					response.getWriter().append(String.valueOf(result));
					
				} else if (result.equals("false")) {
					cnt++;
					result=String.valueOf(cnt);
					System.out.println(cnt);
					response.getWriter().append(String.valueOf(result));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		if(page!=null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("login/Y_autologinbanned.jsp");
			dispatcher.forward(request, response);
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("login/Y_Login.jsp");
			dispatcher.forward(request, response);
		}
		
	}

}
