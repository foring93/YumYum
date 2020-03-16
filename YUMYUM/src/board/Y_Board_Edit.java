package board;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/board/Y_Board_Edit")
public class Y_Board_Edit extends HttpServlet
{
	private static final long serialVersionUID = 2L;
    public Y_Board_Edit()
    {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		int board_no = 0;

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
			out.write("<script>alert('수정 권한이 없습니다.'); "
					+ "history.back();'</script>");
			return;
		}
		
		if(request.getParameter("board_no") != null)
		{
			board_no = Integer.parseInt(request.getParameter("board_no"));
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("Y_Board_Edit.jsp");
		Y_Board_DAO dao = new Y_Board_DAO();
		try
		{
			Y_Board_VO boarddata = dao.getDetails(board_no);
			request.setAttribute("board_data", boarddata);
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		dispatcher.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}