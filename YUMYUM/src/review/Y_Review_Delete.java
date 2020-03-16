package review;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/review/Y_Review_Delete")
public class Y_Review_Delete extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public Y_Review_Delete()
    {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		Y_Review_DAO dao = new Y_Review_DAO();
		int re_no = Integer.parseInt(request.getParameter("re_no"));
		if(session.getAttribute("id") == null)
		{
			out.write("<script>alert('먼저 로그인 해주세요.'); "
					+ "location.href='../Login'</script>");
			return;
		}
		else
		{
			String re_writer = "";
			try
			{
				re_writer = dao.getRe_Writer(re_no);
				
			}
			catch(SQLException se)
			{
				se.printStackTrace();
			}
			boolean is_user = !session.getAttribute("nickname").equals(re_writer);
			boolean is_admin = !session.getAttribute("user_is_admin").equals(1);
			if(is_user && is_admin)
			{
				out.write("<script>alert('삭제 권한이 없습니다.'); "
						+ "history.back()</script>");
				return;
			}
		}
		int result = 0;
		try
		{
			result = dao.deleteReview(re_no);			
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		finally
		{
			String output = "";
			if(result == 0)
			{
				output += "<script>alert('삭제 실패');";
			}
			else
			{
				output += "<script>alert('삭제 성공');";
				int mp_no = Integer.parseInt(request.getParameter("mp_no"));
				try
				{
					dao.updateMP(mp_no);
				}
				catch(SQLException se)
				{
					se.printStackTrace();
				}
			}
			// 뒤로 가기하면서 새로고침
			output += "window.location = document.referrer;</script>";
			//output += "history.back();</script>";
			out.write(output);
		}
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	
}
