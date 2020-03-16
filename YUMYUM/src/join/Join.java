package join;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Join")
public class Join extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Join()
	{
        super();
    }
	protected void doGet(HttpServletRequest request,
						 HttpServletResponse response) throws ServletException, IOException
	{
		 doProcess(request,response);
	}
	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) throws ServletException, IOException
	{
		 doProcess(request,response);
	}
	protected void doProcess (HttpServletRequest request,
			  HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("join/Y_join.jsp");
		
		dispatcher.forward(request, response);
	}
}

