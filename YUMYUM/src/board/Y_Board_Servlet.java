package board;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

@WebServlet("/board/Y_Board_Main")
public class Y_Board_Servlet extends HttpServlet
{
	private static final long serialVersionUID = 2L;
    public Y_Board_Servlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			int category = 0;
			int order = 0;
			if(request.getParameter("category") != null)
			{
				category = Integer.parseInt(request.getParameter("category"));
			}
			if(request.getParameter("order") != null)
			{
				order = Integer.parseInt(request.getParameter("order"));
			}
			Y_Board_DAO dao = new Y_Board_DAO();
			
			JsonArray boardList = null;
			boardList = dao.getWholeBoard(category, order);
			Gson gson = new Gson();
			String json = gson.toJson(boardList);
			
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("cache-control", "no-cache,no-store");
			
			PrintWriter out = response.getWriter();
			out.print(json);
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