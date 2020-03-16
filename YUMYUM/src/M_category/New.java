package M_category;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet("/M_category/place.new")
public class New extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		PlaceDAO dao = null;
		Gson gson = new Gson();
		
		String place = request.getParameter("place");
		String subplace = request.getParameter("subplace");
		String kind = request.getParameter("kind");
		
		// ##################### 페이지네이션 #####################
		// 1페이지에 6개의 게시물만 보여준다.
		int page = 1; 
		int limit = 6; 
				
		// 페이지 이동이 됐는지 안 됐는지 판단 
		if (request.getParameter("page") != null) { 
			page = Integer.parseInt(request.getParameter("page")); 
		}
		System.out.println("넘어온 페이지 = " + page);
		
		if ((place == "" || place == null) && (subplace == "" || subplace == null) && (kind == "" || kind == null)) {
			System.out.println("※아무 것도 선택하지 않았으므로 기본 New 맛집을 가져온다.");
			System.out.println("===기본 New 맛집 끝===");
			
			dao = new PlaceDAO();
			
			// 총 리스트 수를 받아온다. 
			int listcount = dao.getAllListCount();
			
			// 리스트를 받아온다. (JsonArray 타입)
			JsonArray arr = dao.getNew(page, limit);
			
			// 한 페이지에 6개의 게시물을 보여줄 거야
			int maxpage = (listcount + limit - 1) / limit;
			System.out.println("총 페이지 수 = " + maxpage);
			
			// 1페이지부터 5페이지까지만 자를 거야 
			int startpage = ((page-1)/5) * 5 + 1;
			System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);
			
			int endpage = startpage + 5 - 1;
			System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
			
			// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지 
			if (endpage > maxpage) endpage = maxpage;
			
			System.out.println("arr = " + arr);
			
			JsonObject obj = new JsonObject();
			obj.addProperty("page", page);
			obj.addProperty("maxpage", maxpage);
			obj.addProperty("startpage", startpage);
			obj.addProperty("endpage", endpage);
			obj.addProperty("listcount", listcount);
			obj.addProperty("limit", limit);
			obj.add("placelist", arr);
			
			String json = gson.toJson(obj);
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("cache-control", "no-cache-,no-store");
			System.out.println(json);
			response.getWriter().append(json);
		} else {
			if ((subplace == "" || subplace == null) && (kind == "" || kind == null)) {
				System.out.println("※도시만 선택했으므로 도시 New 맛집을 가져온다. " + place);
				System.out.println("===도시별 New 맛집 끝===");
				
				dao = new PlaceDAO();
				
				// 총 리스트 수를 받아온다. 
				int listcount = dao.getNewCityCount(place);
				
				// 리스트를 받아온다. (JsonArray 타입)
				JsonArray arr = dao.getNewCity(page, limit, place);
				
				// 한 페이지에 6개의 게시물을 보여줄 거야
				int maxpage = (listcount + limit - 1) / limit;
				System.out.println("총 페이지 수 = " + maxpage);
				
				// 1페이지부터 5페이지까지만 자를 거야 
				int startpage = ((page-1)/5) * 5 + 1;
				System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);
				
				int endpage = startpage + 5 - 1;
				System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
				
				// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지 
				if (endpage > maxpage) endpage = maxpage;
				
				System.out.println("arr = " + arr);
				
				JsonObject obj = new JsonObject();
				obj.addProperty("page", page);
				obj.addProperty("maxpage", maxpage);
				obj.addProperty("startpage", startpage);
				obj.addProperty("endpage", endpage);
				obj.addProperty("listcount", listcount);
				obj.addProperty("limit", limit);
				obj.add("placelist", arr);
				
				String json = gson.toJson(obj);
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("cache-control", "no-cache-,no-store");
				System.out.println(json);
				response.getWriter().append(json);
			} else if (kind == "" || kind == null) {
				System.out.println("※도시와 지역만 선택했으므로 도시+지역 New 맛집을 가져온다. " + place + ", " + subplace);
				System.out.println("===도시, 지역별 New 맛집 끝===");
				
				dao = new PlaceDAO();
				
				// 총 리스트 수를 받아온다. 
				int listcount = dao.getNewSubCityCount(place, subplace);
				
				// 리스트를 받아온다. (JsonArray 타입)
				JsonArray arr = dao.getNewSubCity(page, limit, place, subplace);
				
				// 한 페이지에 6개의 게시물을 보여줄 거야
				int maxpage = (listcount + limit - 1) / limit;
				System.out.println("총 페이지 수 = " + maxpage);
				
				// 1페이지부터 5페이지까지만 자를 거야 
				int startpage = ((page-1)/5) * 5 + 1;
				System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);
				
				int endpage = startpage + 5 - 1;
				System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
				
				// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지 
				if (endpage > maxpage) endpage = maxpage;
				
				System.out.println("arr = " + arr);
				
				JsonObject obj = new JsonObject();
				obj.addProperty("page", page);
				obj.addProperty("maxpage", maxpage);
				obj.addProperty("startpage", startpage);
				obj.addProperty("endpage", endpage);
				obj.addProperty("listcount", listcount);
				obj.addProperty("limit", limit);
				obj.add("placelist", arr);
				
				String json = gson.toJson(obj);
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("cache-control", "no-cache-,no-store");
				System.out.println(json);
				response.getWriter().append(json);
			} else if ((place == "" || place == null) && (subplace == "" || subplace == null)) {
				System.out.println("※음식 종류만 선택했으므로 종류별 New 맛집을 가져온다. " + kind);
				System.out.println("===종류별 New 맛집 끝===");
				
				dao = new PlaceDAO();

				// 총 리스트 수를 받아온다. 
				int listcount = dao.getNewKindCount(kind);
				
				// 리스트를 받아온다. (JsonArray 타입)
				JsonArray arr = dao.getNewKind(page, limit, kind);
				
				// 한 페이지에 6개의 게시물을 보여줄 거야
				int maxpage = (listcount + limit - 1) / limit;
				System.out.println("총 페이지 수 = " + maxpage);
				
				// 1페이지부터 5페이지까지만 자를 거야 
				int startpage = ((page-1)/5) * 5 + 1;
				System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);
				
				int endpage = startpage + 5 - 1;
				System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
				
				// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지 
				if (endpage > maxpage) endpage = maxpage;
				
				System.out.println("arr = " + arr);
				
				JsonObject obj = new JsonObject();
				obj.addProperty("page", page);
				obj.addProperty("maxpage", maxpage);
				obj.addProperty("startpage", startpage);
				obj.addProperty("endpage", endpage);
				obj.addProperty("listcount", listcount);
				obj.addProperty("limit", limit);
				obj.add("placelist", arr);
				
				String json = gson.toJson(obj);
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("cache-control", "no-cache-,no-store");
				System.out.println(json);
				response.getWriter().append(json);
			} else if ((subplace == "" || subplace == null)) {
				System.out.println("※도시, 음식종류만 선택했으므로 해당 도시의 음식 종류별 New 맛집을 가져온다. " + place + ", " + kind);
				System.out.println("===도시, 종류별 New 맛집 끝===");
				
				dao = new PlaceDAO();

				// 총 리스트 수를 받아온다. 
				int listcount = dao.getNewCityKindCount(place, kind);
				
				// 리스트를 받아온다. (JsonArray 타입)
				JsonArray arr = dao.getNewCityKind(page, limit, place, kind);
				
				// 한 페이지에 6개의 게시물을 보여줄 거야
				int maxpage = (listcount + limit - 1) / limit;
				System.out.println("총 페이지 수 = " + maxpage);
				
				// 1페이지부터 5페이지까지만 자를 거야 
				int startpage = ((page-1)/5) * 5 + 1;
				System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);
				
				int endpage = startpage + 5 - 1;
				System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
				
				// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지 
				if (endpage > maxpage) endpage = maxpage;
				
				System.out.println("arr = " + arr);
				
				JsonObject obj = new JsonObject();
				obj.addProperty("page", page);
				obj.addProperty("maxpage", maxpage);
				obj.addProperty("startpage", startpage);
				obj.addProperty("endpage", endpage);
				obj.addProperty("listcount", listcount);
				obj.addProperty("limit", limit);
				obj.add("placelist", arr);
				
				String json = gson.toJson(obj);
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("cache-control", "no-cache-,no-store");
				System.out.println(json);
				response.getWriter().append(json);
			} else {
				System.out.println("※도시, 지역, 음식종류 모두 선택했으므로 모든 조건을 만족하는 New 맛집을 가져온다. " + place + ", " + subplace + ", " + kind);
				System.out.println("===도시, 지역, 종류별 New 맛집 끝===");
				
				dao = new PlaceDAO();
				
				// 총 리스트 수를 받아온다. 
				int listcount = dao.getNewAllCount(place, subplace, kind);
				
				// 리스트를 받아온다. (JsonArray 타입)
				JsonArray arr = dao.getNewAll(page, limit, place, subplace, kind);
				
				// 한 페이지에 6개의 게시물을 보여줄 거야
				int maxpage = (listcount + limit - 1) / limit;
				System.out.println("총 페이지 수 = " + maxpage);
				
				// 1페이지부터 5페이지까지만 자를 거야 
				int startpage = ((page-1)/5) * 5 + 1;
				System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);
				
				int endpage = startpage + 5 - 1;
				System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
				
				// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지 
				if (endpage > maxpage) endpage = maxpage;
				
				System.out.println("arr = " + arr);
				
				JsonObject obj = new JsonObject();
				obj.addProperty("page", page);
				obj.addProperty("maxpage", maxpage);
				obj.addProperty("startpage", startpage);
				obj.addProperty("endpage", endpage);
				obj.addProperty("listcount", listcount);
				obj.addProperty("limit", limit);
				obj.add("placelist", arr);
				
				String json = gson.toJson(obj);
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("cache-control", "no-cache-,no-store");
				System.out.println(json);
				response.getWriter().append(json);
			}
		}
	}
}
