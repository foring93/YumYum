package board;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/board/Y_Board_Write")
public class Y_Board_Write extends HttpServlet
{
	private static final long serialVersionUID = 2L;
    public Y_Board_Write()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int result = 0;
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		if(session.getAttribute("id") == null)
		{
			out.write("<script>alert('먼저 로그인 해주세요.'); "
					+ "location.href='../Login'</script>");
			return;
		}
		else if(!session.getAttribute("user_is_admin").equals(1))
		{
			out.write("<script>alert('작성 권한이 없습니다.'); "
					+ "location.href='BoardMain'</script>");
			return;
		}
		if(request.getParameter("writeDone") == null)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Y_Board_Write.jsp");
			dispatcher.forward(request, response);
			return;
		}
		try
		{
			int category = Integer.parseInt(request.getParameter("category"));
			String writer = request.getParameter("writer");
			int all_time = 0;
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String ex_date = "";
			
			if(request.getParameter("all_time") == null)
			{
				String ex_year = request.getParameter("ex_year");
				String ex_month = request.getParameter("ex_month");
				String ex_day = request.getParameter("ex_day");
				ex_date = ex_year + ex_month + ex_day + " 23:59:59";
			}
			else
			{
				all_time = 1;
			}
			
			Y_Board_DAO dao = new Y_Board_DAO();
			result = dao.writeNewPost(category, writer, all_time, title, content, ex_date);
			response.setHeader("cache-control", "no-cache,no-store");
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		finally
		{
			if(result == 0)
			{
				out.write("<script>alert('작성 실패'); ");
				out.write("location.href='BoardMain';</script>");
			}
			else
			{
				out.write("<script>alert('작성 완료'); "); 
				out.write("location.href='BoardMain';</script>");
			}
		}
	}
}