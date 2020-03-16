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

@WebServlet("/review/Y_Review_Recommend")
public class Y_Review_Recommend extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public Y_Review_Recommend()
    {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setHeader("cache-control", "no-cache,no-store");
		
		PrintWriter out = response.getWriter();

		int user_no = Integer.parseInt(request.getParameter("user_no"));
		if(request.getParameter("re_no") != null)
		{
			doRecommend(request, response);
			return;
		}
		JsonArray recomList = new JsonArray();
		Y_Review_DAO dao = new Y_Review_DAO();
		try
		{
			recomList = dao.reviewRecommended(user_no);
			Gson gson = new Gson();
			String json = gson.toJson(recomList);
			System.out.println(json);
			out.write(json);
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	protected void doRecommend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();

		int user_no = Integer.parseInt(request.getParameter("user_no"));
		int re_no = Integer.parseInt(request.getParameter("re_no"));
		int result = 0;
		Y_Review_DAO dao = new Y_Review_DAO();
		try
		{
			HttpSession session = request.getSession();
			if(session.getAttribute("id") == null)
			{
				return;
			}
			result = dao.reviewRecommend(re_no, user_no);
			
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		finally
		{
			if(result == 0)
			{
				JsonObject obj = new JsonObject();
				String msg = "오류 발생";
				obj.addProperty("msg", msg);
				Gson gson = new Gson();
				String json = gson.toJson(obj);
				out.write(json);
			}
			else if(result == 2)
			{
				JsonObject obj = new JsonObject();
				String msg = "리뷰 추천 취소";
				obj.addProperty("msg", msg);
				Gson gson = new Gson();
				String json = gson.toJson(obj);
				try
				{
					dao.updateReviewCnt(re_no, 1);
				}
				catch(SQLException se)
				{
					se.printStackTrace();
				}
				out.write(json);
			}
			else if(result == 3)
			{
				JsonObject obj = new JsonObject();
				String msg = "리뷰 추천 성공";
				obj.addProperty("msg", msg);
				Gson gson = new Gson();
				String json = gson.toJson(obj);
				try
				{
					dao.updateReviewCnt(re_no, 0);
				}
				catch(SQLException se)
				{
					se.printStackTrace();
				}
				out.write(json);
			}
		}
	}
}
