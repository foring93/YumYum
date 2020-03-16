package review;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.util.*;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/review/Y_Review_Update")
public class Y_Review_Update extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public Y_Review_Update()
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
		String conType = request.getContentType();
		if(conType != null && conType.contains("multi"))
		{
			doUpdate(request, response);
		}
		else
		{
			try
			{
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
					re_writer = dao.getRe_Writer(re_no);
					boolean is_user = !session.getAttribute("nickname").equals(re_writer);
					boolean is_admin = !session.getAttribute("user_is_admin").equals(1);
					if(is_user && is_admin)
					{
						out.write("<script>alert('수정 권한이 없습니다.'); "
								+ "history.back()</script>");
						return;
					}
				}
				Y_Review_VO review_data = dao.getReviewInfo(re_no);
				request.setAttribute("review_data", review_data);
			}
			catch(SQLException se)
			{
				se.printStackTrace();
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("Y_Review_Update.jsp");
			dispatcher.forward(request, response);
		}
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	protected void doUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int result = 0;

		String file[] = new String[10];
		String filename[] = new String[10];
		String origfilename[] = new String[10];
		
		ServletContext application = request.getServletContext();
		String uploadPath = application.getRealPath("img");
		int size = 1000*1024*1024;
		MultipartRequest multi =
				new MultipartRequest(request,
									 uploadPath,
									 size,
									 "UTF-8",
									 new DefaultFileRenamePolicy() );
		
		// 맛집 번호
		int mp_no = Integer.parseInt(multi.getParameter("mp_no"));
		System.out.println(mp_no);
		// 리뷰 번호
		int re_no = Integer.parseInt(multi.getParameter("re_no"));
		// 맛집에 대한 평점
		int grade = Integer.parseInt(multi.getParameter("grade"));
		System.out.println(grade);
		// 리뷰글 내용
		String content = multi.getParameter("content");
		System.out.println(content);
		// review img files
		Enumeration files = multi.getFileNames();
		int i = 0;
		int cnt = 0;
		int cnt2 = 0;
		String oriImgs = "";
		while(cnt2 < 10)
		{
			if(multi.getParameter("oriImg" + cnt2) != null)
			{
				if(cnt == 0)
					oriImgs += multi.getParameter("oriImg" + cnt2);
				else
					oriImgs += "*" + multi.getParameter("oriImg" + cnt2);
				cnt++;
			}
			cnt2++;
		}
		String imgurl = oriImgs;
		while(files.hasMoreElements())
		{
			file[i] = (String) files.nextElement();
			filename[i] = multi.getFilesystemName(file[i]);
			origfilename[i] = multi.getOriginalFileName(file[i]);
			if(filename[i] != null)
			{
				System.out.println(filename[i]);
				if(cnt == 0)
					imgurl += filename[i];
				else
					imgurl += "*" + filename[i];
				cnt++;
			}
			i++;
		}
		System.out.println(imgurl);
		try
		{
			Y_Review_DAO dao = new Y_Review_DAO();
			result = dao.updateReview(content, grade, imgurl, re_no);
			dao.updateMP(mp_no);
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		finally
		{
			String output = "";
			System.out.println(result);
			if(result == 0)
				output += "<script> alert('수정실패'); ";
			else
				output += "<script> alert('성공적으로 수정하였습니다.'); ";
			output += "location.href='../M_category/place.move?mp_no="+ mp_no +"'; </script>";
			PrintWriter out = response.getWriter();
			out.write(output);
		}
	}

}
