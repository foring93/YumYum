package mypage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import detail.DetailDAO;

// 마이페이지에서 찜목록을 보여주는 서블릿
@WebServlet("/mypage/dibslist")
public class DibsListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public DibsListServlet() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		String id = request.getParameter("id");
		DetailDAO detaildao = new DetailDAO();
		JsonArray arr=detaildao.MyDibsList(id);
		String dibslist =  gson.toJson(arr);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().append(dibslist);
	}
}
