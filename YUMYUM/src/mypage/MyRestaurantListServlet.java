package mypage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * Servlet implementation class MyRestaurantListServlet
 */
@WebServlet("/mypage/myrestaurantlist")
public class MyRestaurantListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyRestaurantListServlet() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		String data = request.getParameter("option");
		String nickname = (String)session.getAttribute("nickname"); 
		mypageinfoDAO dao = new mypageinfoDAO();
		JsonArray arr= dao.myRestaurantList(data, nickname);
		Gson gson = new Gson();
		String result = gson.toJson(arr);
		response.getWriter().append(result);
	}

}
