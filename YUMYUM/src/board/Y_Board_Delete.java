package board;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/board/Y_Board_Delete")
public class Y_Board_Delete extends HttpServlet
{
	private static final long serialVersionUID = 2L;
    public Y_Board_Delete()
    {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
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
			out.write("<script>alert('삭제 권한이 없습니다.'); "
					+ "history.back()</script>");
			return;
		}
		
		int board_no = 0;
		if(request.getParameter("board_no") != null)
		{
			board_no = Integer.parseInt(request.getParameter("board_no"));
		}
		Y_Board_DAO dao = new Y_Board_DAO();
		int result = 0;
		try
		{
			if(board_no != 0)
				result = dao.deletePost(board_no);
			else
				out.write("<script>alert('글번호 불러오기 실패');</script>");
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		finally
		{
			if(result == 0)
			{
				out.write("<script>alert('삭제 실패'); ");
				out.write("location.href='BoardMain';</script>");
			}
			else
			{
				out.write("<script>alert('삭제 완료'); "); 
				out.write("location.href='BoardMain';</script>");
			}
		}	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}