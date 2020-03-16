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

@WebServlet("/review/Y_Review_Write")
public class Y_Review_Write extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public Y_Review_Write()
    {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		String conType = request.getContentType();
		if(conType == null)
		{
			//PrintWriter out = response.getWriter();
			//out.write("<script>alert('잘못된 접근입니다.');history.back();</script>");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Y_Review_Write.jsp");
			dispatcher.forward(request, response);
		}
		else if(conType.contains("multi"))
		{
			doWrite(request, response);
		}
		else
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Y_Review_Write.jsp");
			dispatcher.forward(request, response);
		}
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	protected void doWrite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int result = 0;
		
		PrintWriter out = response.getWriter();
		out.write("<table id='loading' style='height:499px;margin:0 auto;text-align:center;'><th style='font-size:23pt;font-weight:bold;color:grey;text-align:center;vertical-align:middle'>작성중..</th></table>");
		
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
		// 맛집 이름
		String mp_name = multi.getParameter("mp_name");
		System.out.println(mp_name);
		// 맛집에 대한 평점
		int grade = Integer.parseInt(multi.getParameter("grade"));
		System.out.println(grade);
		// 작성자 닉네임
		String writer = multi.getParameter("writer");
		System.out.println(writer);
		// 리뷰글 내용
		String content = multi.getParameter("content");
		System.out.println(content);
		// review img files
		Enumeration files = multi.getFileNames();
		int i = 0;
		int cnt = 0;
		String imgurl = "";
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
			Y_Review_VO vo = new Y_Review_VO();
			vo.setRe_ref_no(mp_no);
			vo.setRe_ref_name(mp_name);
			vo.setRe_writer(writer);
			vo.setRe_content(content);
			vo.setRe_grade(grade);
			vo.setRe_img_url(imgurl);
			Y_Review_DAO dao = new Y_Review_DAO();
			result = dao.writeReview(vo);
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
			{
				output = "<script>document.getElementById('loading').onload = " + 
						"function(){alert('리뷰작성을 실패하였습니다.');top.location.reload();}()</script>";
			}
			else
			{
				output = "<script>document.getElementById('loading').onload = " + 
						"function(){alert('성공적으로 작성하였습니다.');top.location.reload();}()</script>";
			}
			response.getWriter().write(output);
		}
	}

}

// pstmt 관련 유용 정보 https://blog.outsider.ne.kr/266
