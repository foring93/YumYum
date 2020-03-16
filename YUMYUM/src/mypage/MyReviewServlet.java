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

@WebServlet("/mypage/myReview")
public class MyReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyReviewServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String nickname = (String)session.getAttribute("nickname");
		mypageinfoDAO dao = new mypageinfoDAO();
		Gson gson = new Gson();
		JsonArray arr = dao.myReviewList(nickname);
		String myreview = gson.toJson(arr);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().append(myreview);
	}
}
