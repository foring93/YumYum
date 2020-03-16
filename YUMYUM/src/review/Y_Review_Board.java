package review;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

@WebServlet("/review/Y_Review_Board")
public class Y_Review_Board extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public Y_Review_Board()
    {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setHeader("cache-control", "no-cache,no-store");
		if(request.getParameter("ajax") != null)
		{
			doExecute(request, response);
			return;
		}
		try
		{
			Y_Review_DAO dao = new Y_Review_DAO();
			JsonArray reviewList = dao.getWholeReview(0 ,0 ,0, "");
			Gson gson = new Gson();
			String json = gson.toJson(reviewList);
			request.setAttribute("reviewList", json);
			System.out.println(json);
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		finally
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Y_Review_Board.jsp");
			dispatcher.forward(request, response);
		}
		
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	protected void doExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String category = request.getParameter("category");
		int order = Integer.parseInt(request.getParameter("order"));
		int grade = Integer.parseInt(request.getParameter("grade"));
		try
		{
			Y_Review_DAO dao = new Y_Review_DAO();
			JsonArray reviewList = dao.getWholeReview(0 ,order ,grade, category);
			Gson gson = new Gson();
			String json = gson.toJson(reviewList);
			PrintWriter out = response.getWriter();
			System.out.println(json);
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

}
