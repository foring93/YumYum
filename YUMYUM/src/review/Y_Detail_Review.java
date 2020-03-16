package review;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet("/review/Y_Detail_Review")
public class Y_Detail_Review extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public Y_Detail_Review()
    {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setHeader("cache-control", "no-cache,no-store");

		int mp_no = Integer.parseInt(request.getParameter("mp_no"));
		int order = Integer.parseInt(request.getParameter("order"));
		int grade = Integer.parseInt(request.getParameter("grade"));
		try
		{
			Y_Review_DAO dao = new Y_Review_DAO();
			JsonArray reviewList = dao.getWholeReview(mp_no, order, grade, "");
			HttpSession session = request.getSession();
			if(session.getAttribute("nickname") != null)
			{
				String user_nick = (String)session.getAttribute("nickname");
				int user_is_admin = (int)session.getAttribute("user_is_admin");
				int usernum = (int)session.getAttribute("usernum");
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("user_nick", user_nick);
				jsonObject.addProperty("user_is_admin", user_is_admin);
				jsonObject.addProperty("usernum", usernum);
				reviewList.add(jsonObject);
			}
			
			Gson gson = new Gson();
			String json = gson.toJson(reviewList);
			request.setAttribute("reviewList", json);
			System.out.println(json);
			PrintWriter out = response.getWriter();
			out.write(json);
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		finally
		{
			
		}
		
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
