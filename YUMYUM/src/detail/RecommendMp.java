package detail;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

@WebServlet("/M_category/Recommend_mp")
public class RecommendMp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setHeader("cache-control", "no-cache,no-store");
		
		//들어간 맛집 주소를 가져옴
		String mp_address = request.getParameter("mp_address");
		int mp_no = 
				Integer.parseInt(request.getParameter("mp_no"));
		//split으로 뽑아옴 0 = ㅇㅇ시, 1 = ㅇㅇ구
		String mp_address_s = mp_address.split("\\s")[0]; 
		System.out.println("뽑아온 주소 : " + mp_address_s);
		
		try {
		
			DetailDAO dao = new DetailDAO();
			//서울에 대해서 맛집3개 뽑아오기 + 이미지, 맛집이름, 
			JsonArray recomList = dao.recommend_mp(mp_address_s,mp_no);
			Gson gson = new Gson();
			String json = gson.toJson(recomList);
			PrintWriter out = response.getWriter();
			
			out.write(json);
			System.out.println(json);
			
			out.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
