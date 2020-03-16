package M_category;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

@WebServlet("/M_category/place.best")
public class Best extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// select
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		PlaceDAO dao = null;
		Gson gson = new Gson();
		
		String place = request.getParameter("place");
		String subplace = request.getParameter("subplace");
		String kind = request.getParameter("kind");
		
		
		if ((place == "" || place == null) && (subplace == "" || subplace == null) && (kind == "" || kind == null)) {
			System.out.println("※아무 것도 선택하지 않았으므로 기본 Best 맛집을 가져온다.");
			System.out.println("===기본 Best 맛집 끝===");
			dao = new PlaceDAO();
			JsonArray arr = dao.getBest();
			String bestPlace = gson.toJson(arr);
			
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("cache-control", "no-cache-,no-store");
			response.getWriter().append(bestPlace);
		} else {
			if ((subplace == "" || subplace == null) && (kind == "" || kind == null)) {
				System.out.println("※도시만 선택했으므로 도시 Best 맛집을 가져온다. " + place);
				System.out.println("===도시별 Best 맛집 끝===");
				dao = new PlaceDAO();
				JsonArray arr = dao.getBestCity(place);
				String bestCity = gson.toJson(arr);
				
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("cache-control", "no-cache-,no-store");
				response.getWriter().append(bestCity);
			} else if (kind == "" || kind == null) {
				System.out.println("※도시와 지역만 선택했으므로 도시+지역 Best 맛집을 가져온다. " + place + ", " + subplace);
				System.out.println("===도시, 지역별 Best 맛집 끝===");
				dao = new PlaceDAO(); 
				JsonArray arr = dao.getBestSubCity(place, subplace);
				String bestCitySub = gson.toJson(arr);
				
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("cache-control", "no-cache-,no-store");
				response.getWriter().append(bestCitySub);
			} else if ((place == "" || place == null) && (subplace == "" || subplace == null)) {
				System.out.println("※음식 종류만 선택했으므로 종류별 Best 맛집을 가져온다. " + kind);
				System.out.println("===종류별 Best 맛집 끝===");
				
				dao = new PlaceDAO();
				JsonArray arr = dao.getBestKind(kind);
				String bestKind = gson.toJson(arr);
				
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("cache-control", "no-cache-,no-store");
				response.getWriter().append(bestKind);
			} else if ((subplace == "" || subplace == null)) {
				System.out.println("※도시, 음식종류만 선택했으므로 해당 도시의 음식 종류별 Best 맛집을 가져온다. " + place + ", " + kind);
				System.out.println("===도시, 종류별 Best 맛집 끝===");
				dao = new PlaceDAO();
				JsonArray arr = dao.getBestCityKind(place, kind);
				String bestCityKind = gson.toJson(arr);
				
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("cache-control", "no-cache-,no-store");
				response.getWriter().append(bestCityKind);
			} else {
				System.out.println("※도시, 지역, 음식종류 모두 선택했으므로 모든 조건을 만족하는 Best 맛집을 가져온다. " + place + ", " + subplace + ", " + kind);
				System.out.println("===도시, 지역, 종류별 Best 맛집 끝===");
				dao = new PlaceDAO(); 
				JsonArray arr = dao.getBestAll(place, subplace, kind);
				String bestAll = gson.toJson(arr);
				
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("cache-control", "no-cache-,no-store");
				response.getWriter().append(bestAll);
			}
		}
	}
}
