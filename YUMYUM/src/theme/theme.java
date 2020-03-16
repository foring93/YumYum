package theme;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import admin.adminDAO;
import admin.mpVO;
import owner.ownerDAO;

/**
 * Servlet implementation class theme
 */
@WebServlet("/Main/theme")
public class theme extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		int TH_NO=Integer.parseInt(request.getParameter("TH_NO"));
		String TH_TITLE=request.getParameter("change");
		System.out.println("바꿀테마이름"+TH_TITLE);
		System.out.println("원하는 위치:"+TH_NO);
		adminDAO dao= new adminDAO();
		int find=dao.TH_find(TH_TITLE);
		if(find==0){
			PrintWriter out= response.getWriter();
			out.write("<script>alert('입력한 테마는 존재하지 않습니다.');history.back();</script>");
			out.close();
			return;
		}
		int result=dao.TH_update(TH_NO, TH_TITLE);
		System.out.println("결과값:"+result);
		if(result==1) {
			response.sendRedirect("../Main/admin?page=TH_select");
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
				String realFolder="";
				int result=0;
				//WebContent아래에꼭 폴더 생성하세요
				String saveFolder="img";
				int fileSize=10*1024*1024;//업로드할 파일의 최대 사이즈 입니다.
				
				//실제 저장 경로를 지정합니다.
				ServletContext sc= request.getServletContext();
				realFolder= sc.getRealPath(saveFolder);
				
				System.out.println("realFolder="+ realFolder);
				
				
				try {
					MultipartRequest multi= null;
					multi = new MultipartRequest(request,
								realFolder,fileSize,"utf-8", new DefaultFileRenamePolicy());
				//boardbean객체에 글등록 폼에서 입력받은 정보들을 저장합니다.
					
					
					String TH_TITLE=multi.getParameter("TH_TITLE");
					String TH_IMG_URL=multi.getFilesystemName("TH_IMG_URL");
					String TH_HASH=multi.getParameter("TH_HASH");
					
					adminDAO dao= new adminDAO();
					result= dao.insert_TH(TH_TITLE, TH_IMG_URL, TH_HASH);
					System.out.println("theme="+TH_IMG_URL);
					
					//업로드한 파일명은 업로드한 파일의 전체경로에서 파일이름만 저장합니다.
					System.out.println("admin.java==="+multi.getFilesystemName("TH_IMG_URL"));
					
					//글등록 처리를 위해DAO의 boardInsert()메서드 호출합니다.
					//글등록 폼에서 입력한 정보가 저장되어있는 boarddata객체를 전달합니다.
					
					
					
					//글등록에 실패한 경우null을 반환 합니다.
					PrintWriter out= response.getWriter();
					
					if(result==0) {
						System.out.println("테마등록실패!");
					out.println("<script>alert('테마등록실패!');history.back();</script>");	
						
					}else {
						//승인 요청테이블에서 승인됐는지 안됐는지 
					System.out.println("테마등록완료");
					out.println("<script>alert('테마등록완료');location.href='../Main/admin?page=theme'</script>");
					}
					out.close();
					
				}catch(Exception e) {
					e.printStackTrace();
				}
		
	}
}


