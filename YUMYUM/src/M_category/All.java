package M_category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
// ############## 페이지네이션 테스트 클래스 
@WebServlet("/M_category/place.all")
public class All extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		PlaceDAO dao = null;
		Gson gson = new Gson();

		String place = request.getParameter("place"); // 도시
		String subplace = request.getParameter("subplace"); // 세부 지역
		String kind = request.getParameter("kind"); // 음식 종류
		String search = request.getParameter("search"); // 검색어
		
		search = search.replaceAll(" ", "");
		search = search.replaceAll("\\p{Z}", ""); // 위의 과정을 거쳐도 공백 제거가 안 되는 경우가 존재하므로 문자열에서 정규식 1칸의 공백을 -> 0칸의 공백으로 바꾼다.
		search = search.toUpperCase();

		// ##################### 페이지네이션 #####################
		// 1페이지에 6개의 게시물만 보여준다.
		int page = 1; 
		int limit = 6; 
		
		// 페이지 이동이 됐는지 안 됐는지 판단 
		if (request.getParameter("page") != null) { 
			page = Integer.parseInt(request.getParameter("page")); 
		}
		System.out.println("넘어온 페이지 = " + page);
		
		// # 아무 것도 선택하지 않았을 때
		if ((place == "" || place == null) && (subplace == "" || subplace == null) && (kind == "" || kind == null) && (search == "" || search == null)) {
			System.out.println("※아무 것도 선택하지 않았으므로 기본 All 맛집을 가져온다.");
			System.out.println("===기본 All 맛집 끝===");
			
			dao = new PlaceDAO();
			// 총 리스트 수를 받아온다. 
			int listcount = dao.getAllListCount();
			
			// 리스트를 받아온다. (JsonArray 타입)
			JsonArray arr = dao.getAll(page, limit);
			
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
		// # 뭔가 선택을 하긴 했을 때
		else {
			// 도시만 선택했을 때 (1개)
			if ((subplace == "" || subplace == null) && (kind == "" || kind == null) && (search == "" || search == null)) {
				System.out.println("※도시만 선택했으므로 도시 All 맛집을 가져온다. " + place);
				System.out.println("===도시별 All 맛집 끝===");
				
				dao = new PlaceDAO();
				
				// 총 리스트 수를 받아온다.
				int listcount = dao.getAllCityCount(place);
				
				// 리스트를 받아온다.
				JsonArray arr = dao.getAllCity(page, limit, place);
				
				// 한 페이지에 6개의 게시물을 보여줄 거야
				int maxpage = (listcount + limit - 1) / limit;
				System.out.println("총 페이지 수 = " + maxpage);
				
				// 1페이지부터 5페이지까지 자를 거야 
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
				// 음식 종류만 선택했을 때 (1개)
			} else if ((place == "" || place == null) && (subplace == "" || subplace == null) && (search == "" || search == null)) {
				System.out.println("※음식 종류만 선택했으므로 종류별 All 맛집을 가져온다. " + kind);
				System.out.println("===종류별 All 맛집 끝===");
				
				dao = new PlaceDAO();
				
				// 총 리스트 수를 받아온다.
				int listcount = dao.getAllKindCount(kind);

				// 리스트를 받아온다.
				JsonArray arr = dao.getAllKind(page, limit, kind);

				// 한 페이지에 6개의 게시물을 보여줄 거야
				int maxpage = (listcount + limit - 1) / limit;
				System.out.println("총 페이지 수 = " + maxpage);

				// 1페이지부터 5페이지까지 자를 거야
				int startpage = ((page - 1) / 5) * 5 + 1;
				System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

				int endpage = startpage + 5 - 1;
				System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);

				// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
				if (endpage > maxpage)
					endpage = maxpage;

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
				// 검색어만 있을 때 (1개)
			} else if ((place == "" || place == null) && (subplace == "" || subplace == null) && (kind == "" || kind == null)) {
				System.out.println("※검색어만 입력했으므로 검색어에 해당하는 All 맛집을 가져온다. " + search);
				System.out.println("===검색어 All 맛집 끝===");
				
				// #검색어 형태인 경우 
				if (search.contains("#")) { 
					System.out.println("===테마별 검색===");
					dao = new PlaceDAO();
					// #으로 글자를 나눈다.
					String[] hash = search.split("\\#");
					
					// 리스트를 받아온다.
					List<PlaceVO> list = new ArrayList<PlaceVO>();
					list = dao.SearchHash(page, limit, hash);
					
					// 총 리스트 수를 받아온다.
					int listcount = list.size();
					System.out.println("listcount = " + listcount);
					
					// 한 페이지에 6개의 게시물을 보여줄 거야
					int maxpage = (listcount + limit - 1) / limit;
					System.out.println("총 페이지 수 = " + maxpage);

					// 1페이지부터 5페이지까지 자를 거야
					int startpage = ((page - 1) / 5) * 5 + 1;
					System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

					int endpage = startpage + 5 - 1;
					System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
					
					// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
					if (endpage > maxpage)
						endpage = maxpage;
					
					JsonObject obj = new JsonObject();
					obj.addProperty("page", page);
					obj.addProperty("maxpage", maxpage);
					obj.addProperty("startpage", startpage);
					obj.addProperty("endpage", endpage);
					obj.addProperty("listcount", listcount);
					obj.addProperty("limit", limit);
					
					JsonArray arr = new Gson().toJsonTree(list).getAsJsonArray();
					obj.add("placelist", arr);
					
					String json = gson.toJson(obj);

					response.setContentType("text/html;charset=utf-8");
					response.setHeader("cache-control", "no-cache-,no-store");
					System.out.println(json);
					response.getWriter().append(json);
				} else { // 일반적인 검색인 경우 
					dao = new PlaceDAO();
					
					// 총 리스트 수를 받아온다.
					int listcount = dao.getAllWordCount(search);
					System.out.println("listcount = " + listcount);
					// 리스트를 받아온다.
					JsonArray arr = dao.getWord(page, limit, search);
	
					// 한 페이지에 6개의 게시물을 보여줄 거야
					int maxpage = (listcount + limit - 1) / limit;
					System.out.println("총 페이지 수 = " + maxpage);
	
					// 1페이지부터 5페이지까지 자를 거야
					int startpage = ((page - 1) / 5) * 5 + 1;
					System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);
	
					int endpage = startpage + 5 - 1;
					System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
					
					// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
					if (endpage > maxpage)
						endpage = maxpage;
					
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
				// 도시와 지역만 선택했을 때 (2개)
			} else if ((kind == "" || kind == null) && (search == "" || search == null)) {
				System.out.println("※도시와 지역만 선택했으므로 도시+지역 All 맛집을 가져온다. " + place + ", " + subplace);
				System.out.println("===도시, 지역별 All 맛집 끝===");
				
				dao = new PlaceDAO();
				
				// 총 리스트 수를 받아온다.
				int listcount = dao.getAll2CityCount(place, subplace);

				// 리스트를 받아온다.
				JsonArray arr = dao.getAll2City(page, limit, place, subplace);

				// 한 페이지에 6개의 게시물을 보여줄 거야
				int maxpage = (listcount + limit - 1) / limit;
				System.out.println("총 페이지 수 = " + maxpage);

				// 1페이지부터 5페이지까지 자를 거야
				int startpage = ((page - 1) / 5) * 5 + 1;
				System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

				int endpage = startpage + 5 - 1;
				System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
				
				// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
				if (endpage > maxpage)
					endpage = maxpage;
				
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
				// 도시와 음식종류만 선택했을 때 (2개)
			} else if ((subplace == "" || subplace == null) && (search == "" || search == null)) {
				System.out.println("※도시, 음식종류만 선택했으므로 해당 도시의 음식 종류별 All 맛집을 가져온다. " + place + ", " + kind);
				System.out.println("===도시, 종류별 All 맛집 끝===");
				
				dao = new PlaceDAO();
				
				// 총 리스트 수를 받아온다.
				int listcount = dao.getAllCityKindCount(place, kind);

				// 리스트를 받아온다.
				JsonArray arr = dao.getAllCityKind(page, limit, place, kind);

				// 한 페이지에 6개의 게시물을 보여줄 거야
				int maxpage = (listcount + limit - 1) / limit;
				System.out.println("총 페이지 수 = " + maxpage);

				// 1페이지부터 5페이지까지 자를 거야
				int startpage = ((page - 1) / 5) * 5 + 1;
				System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

				int endpage = startpage + 5 - 1;
				System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
				
				// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
				if (endpage > maxpage)
					endpage = maxpage;
				
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
				// 도시와 검색어만 선택했을 때 (2개)
			} else if ((subplace == "" || subplace == null) && (kind == "" || kind == null)) {
				System.out.println("※도시, 검색어만 선택했으므로 해당 도시의 검색어에 해당하는 All 맛집을 가져온다. " + place + ", " + search);
				System.out.println("===도시, 검색어별 All 맛집 끝===");
				
				dao = new PlaceDAO();
				
				// # 검색어 형태인 경우 
				if (search.contains("#")) {
					System.out.println("===테마별 검색===");
					
					dao = new PlaceDAO();
					
					// #으로 글자를 나눈다.
					String[] hash = search.split("\\#");
					
					// 리스트를 받아온다.
					List<PlaceVO> list = new ArrayList<PlaceVO>();
					list = dao.SearchCityHash(page, limit, place, hash);
					
					// 총 리스트 수를 받아온다.
					int listcount = list.size();
					System.out.println("listcount = " + listcount);
					
					// 한 페이지에 6개의 게시물을 보여줄 거야
					int maxpage = (listcount + limit - 1) / limit;
					System.out.println("총 페이지 수 = " + maxpage);

					// 1페이지부터 5페이지까지 자를 거야
					int startpage = ((page - 1) / 5) * 5 + 1;
					System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

					int endpage = startpage + 5 - 1;
					System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
					
					// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
					if (endpage > maxpage)
						endpage = maxpage;
					
					JsonObject obj = new JsonObject();
					obj.addProperty("page", page);
					obj.addProperty("maxpage", maxpage);
					obj.addProperty("startpage", startpage);
					obj.addProperty("endpage", endpage);
					obj.addProperty("listcount", listcount);
					obj.addProperty("limit", limit);
					
					JsonArray arr = new Gson().toJsonTree(list).getAsJsonArray();
					obj.add("placelist", arr);
					
					String json = gson.toJson(obj);

					response.setContentType("text/html;charset=utf-8");
					response.setHeader("cache-control", "no-cache-,no-store");
					System.out.println(json);
					response.getWriter().append(json);
				} else {
					// 총 리스트 수를 받아온다.
					int listcount = dao.cityWordCount(place, search);
	
					// 리스트를 받아온다.
					JsonArray arr = dao.cityWord(page, limit, place, search);
	
					// 한 페이지에 6개의 게시물을 보여줄 거야
					int maxpage = (listcount + limit - 1) / limit;
					System.out.println("총 페이지 수 = " + maxpage);
	
					// 1페이지부터 5페이지까지 자를 거야
					int startpage = ((page - 1) / 5) * 5 + 1;
					System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);
	
					int endpage = startpage + 5 - 1;
					System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
					
					// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
					if (endpage > maxpage)
						endpage = maxpage;
					
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
				// 음식종류와 검색어만 선택했을 때 (2개)
			} else if ((place == "" || place == null) && (subplace == "" || subplace == null)) {
				System.out.println("※음식종류, 검색어만 선택했으므로 해당 음식종류의 검색어에 해당하는 All 맛집을 가져온다. " + kind + ", " + search);
				System.out.println("===음식종류, 검색어별 All 맛집 끝===");
				
				dao = new PlaceDAO();

				// # 검색어 형태인 경우 
				if (search.contains("#")) {
					System.out.println("===테마별 검색===");
					
					dao = new PlaceDAO();
					
					// #으로 글자를 나눈다.
					String[] hash = search.split("\\#");
					
					// 리스트를 받아온다.
					List<PlaceVO> list = new ArrayList<PlaceVO>();
					list = dao.SearchKindHash(page, limit, kind, hash);
					
					// 총 리스트 수를 받아온다.
					int listcount = list.size();
					System.out.println("listcount = " + listcount);
					
					// 한 페이지에 6개의 게시물을 보여줄 거야
					int maxpage = (listcount + limit - 1) / limit;
					System.out.println("총 페이지 수 = " + maxpage);

					// 1페이지부터 5페이지까지 자를 거야
					int startpage = ((page - 1) / 5) * 5 + 1;
					System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

					int endpage = startpage + 5 - 1;
					System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
					
					// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
					if (endpage > maxpage)
						endpage = maxpage;
					
					JsonObject obj = new JsonObject();
					obj.addProperty("page", page);
					obj.addProperty("maxpage", maxpage);
					obj.addProperty("startpage", startpage);
					obj.addProperty("endpage", endpage);
					obj.addProperty("listcount", listcount);
					obj.addProperty("limit", limit);
					
					JsonArray arr = new Gson().toJsonTree(list).getAsJsonArray();
					obj.add("placelist", arr);
					
					String json = gson.toJson(obj);

					response.setContentType("text/html;charset=utf-8");
					response.setHeader("cache-control", "no-cache-,no-store");
					System.out.println(json);
					response.getWriter().append(json);
				} else {
					// 총 리스트 수를 받아온다.
					int listcount = dao.getKindWordCount(kind, search);
	
					// 리스트를 받아온다.
					JsonArray arr = dao.getKindWord(page, limit, kind, search);
	
					// 한 페이지에 6개의 게시물을 보여줄 거야
					int maxpage = (listcount + limit - 1) / limit;
					System.out.println("총 페이지 수 = " + maxpage);
	
					// 1페이지부터 5페이지까지 자를 거야
					int startpage = ((page - 1) / 5) * 5 + 1;
					System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);
	
					int endpage = startpage + 5 - 1;
					System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
					
					// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
					if (endpage > maxpage)
						endpage = maxpage;
					
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
				// 검색어만 입력하지 않았을 때 (3개)
			} else if (search == "" || search == null) {
				System.out.println("※검색어 빼고 모두 선택했으므로 도시, 세부지역, 종류에 해당하는 All 맛집을 가져온다. " + place + ", " + subplace + ", " + kind);
				System.out.println("===도시, 지역, 종류별 All 맛집 끝===");
				
				dao = new PlaceDAO();
				
				// 총 리스트 수를 받아온다.
				int listcount = dao.getNotWordCount(place, subplace, kind);

				// 리스트를 받아온다.
				JsonArray arr = dao.getNotWord(page, limit, place, subplace, kind);

				// 한 페이지에 6개의 게시물을 보여줄 거야
				int maxpage = (listcount + limit - 1) / limit;
				System.out.println("총 페이지 수 = " + maxpage);

				// 1페이지부터 5페이지까지 자를 거야
				int startpage = ((page - 1) / 5) * 5 + 1;
				System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

				int endpage = startpage + 5 - 1;
				System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
				
				// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
				if (endpage > maxpage)
					endpage = maxpage;
				
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
				// 음식종류만 선택하지 않았을 때 (3개)
			} else if (kind == "" || kind == null) {
				System.out.println("※음식종류 빼고 모두 선택했으므로 도시, 세부지역, 검색어에 해당하는 All 맛집을 가져온다. " + place + ", " + subplace + ", " + search);
				System.out.println("===도시, 지역, 검색어별 All 맛집 끝===");
				
				dao = new PlaceDAO();

				// # 검색어 형태인 경우 
				if (search.contains("#")) {
					System.out.println("===테마별 검색===");
					
					dao = new PlaceDAO();
					
					// #으로 글자를 나눈다.
					String[] hash = search.split("\\#");
					
					// 리스트를 받아온다.
					List<PlaceVO> list = new ArrayList<PlaceVO>();
					list = dao.SearchNotKindHash(page, limit, place, subplace, hash);
					
					// 총 리스트 수를 받아온다.
					int listcount = list.size();
					System.out.println("listcount = " + listcount);
					
					// 한 페이지에 6개의 게시물을 보여줄 거야
					int maxpage = (listcount + limit - 1) / limit;
					System.out.println("총 페이지 수 = " + maxpage);

					// 1페이지부터 5페이지까지 자를 거야
					int startpage = ((page - 1) / 5) * 5 + 1;
					System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

					int endpage = startpage + 5 - 1;
					System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
					
					// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
					if (endpage > maxpage)
						endpage = maxpage;
					
					JsonObject obj = new JsonObject();
					obj.addProperty("page", page);
					obj.addProperty("maxpage", maxpage);
					obj.addProperty("startpage", startpage);
					obj.addProperty("endpage", endpage);
					obj.addProperty("listcount", listcount);
					obj.addProperty("limit", limit);
					
					JsonArray arr = new Gson().toJsonTree(list).getAsJsonArray();
					obj.add("placelist", arr);
					
					String json = gson.toJson(obj);

					response.setContentType("text/html;charset=utf-8");
					response.setHeader("cache-control", "no-cache-,no-store");
					System.out.println(json);
					response.getWriter().append(json);
				} else {
					// 총 리스트 수를 받아온다.
					int listcount = dao.getNotKindCount(place, subplace, search);
	
					// 리스트를 받아온다.
					JsonArray arr = dao.getNotKind(page, limit, place, subplace, search);
	
					// 한 페이지에 6개의 게시물을 보여줄 거야
					int maxpage = (listcount + limit - 1) / limit;
					System.out.println("총 페이지 수 = " + maxpage);
	
					// 1페이지부터 5페이지까지 자를 거야
					int startpage = ((page - 1) / 5) * 5 + 1;
					System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);
	
					int endpage = startpage + 5 - 1;
					System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
					
					// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
					if (endpage > maxpage)
						endpage = maxpage;
					
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
				// 세부지역만 선택하지 않았을 때 (3개)
			} else if (subplace == "" || subplace == null) {
				System.out.println("※세부지역 빼고 모두 선택했으므로 도시, 종류, 검색어에 해당하는 All 맛집을 가져온다. " + place + ", " + kind + ", " + search);
				System.out.println("===도시, 음식종류, 검색어별 All 맛집 끝===");
				
				dao = new PlaceDAO();

				// # 검색어 형태인 경우 
				if (search.contains("#")) {
					System.out.println("===테마별 검색===");
					
					dao = new PlaceDAO();
					
					// #으로 글자를 나눈다.
					String[] hash = search.split("\\#");
					
					// 리스트를 받아온다.
					List<PlaceVO> list = new ArrayList<PlaceVO>();
					list = dao.SearchNotsCityHash(page, limit, place, kind, hash);
					
					// 총 리스트 수를 받아온다.
					int listcount = list.size();
					System.out.println("listcount = " + listcount);
					
					// 한 페이지에 6개의 게시물을 보여줄 거야
					int maxpage = (listcount + limit - 1) / limit;
					System.out.println("총 페이지 수 = " + maxpage);

					// 1페이지부터 5페이지까지 자를 거야
					int startpage = ((page - 1) / 5) * 5 + 1;
					System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

					int endpage = startpage + 5 - 1;
					System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
					
					// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
					if (endpage > maxpage)
						endpage = maxpage;
					
					JsonObject obj = new JsonObject();
					obj.addProperty("page", page);
					obj.addProperty("maxpage", maxpage);
					obj.addProperty("startpage", startpage);
					obj.addProperty("endpage", endpage);
					obj.addProperty("listcount", listcount);
					obj.addProperty("limit", limit);
					
					JsonArray arr = new Gson().toJsonTree(list).getAsJsonArray();
					obj.add("placelist", arr);
					
					String json = gson.toJson(obj);

					response.setContentType("text/html;charset=utf-8");
					response.setHeader("cache-control", "no-cache-,no-store");
					System.out.println(json);
					response.getWriter().append(json);
				} else {
					// 총 리스트 수를 받아온다.
					int listcount = dao.getNotsCityCount(place, kind, search);
	
					// 리스트를 받아온다.
					JsonArray arr = dao.getNotsCity(page, limit, place, kind, search);
	
					// 한 페이지에 6개의 게시물을 보여줄 거야
					int maxpage = (listcount + limit - 1) / limit;
					System.out.println("총 페이지 수 = " + maxpage);
	
					// 1페이지부터 5페이지까지 자를 거야
					int startpage = ((page - 1) / 5) * 5 + 1;
					System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);
	
					int endpage = startpage + 5 - 1;
					System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
					
					// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
					if (endpage > maxpage)
						endpage = maxpage;
					
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
				// 다 선택했을 때 (4개)
			} else {
				System.out.println("※도시, 지역, 음식종류, 검색어 모두 선택했으므로 모든 조건을 만족하는 All 맛집을 가져온다. " + place + ", " + subplace + ", " + kind + ", " + search);
				System.out.println("===도시, 지역, 종류, 검색어별 All 맛집 끝===");
				
				dao = new PlaceDAO();

				// # 검색어 형태인 경우 
				if (search.contains("#")) {
					System.out.println("===테마별 검색===");
					
					dao = new PlaceDAO();
					
					// #으로 글자를 나눈다.
					String[] hash = search.split("\\#");
					
					// 리스트를 받아온다.
					List<PlaceVO> list = new ArrayList<PlaceVO>();
					list = dao.SearchAllHash(page, limit, place, subplace, kind, hash);
					
					// 총 리스트 수를 받아온다.
					int listcount = list.size();
					System.out.println("listcount = " + listcount);
					
					// 한 페이지에 6개의 게시물을 보여줄 거야
					int maxpage = (listcount + limit - 1) / limit;
					System.out.println("총 페이지 수 = " + maxpage);

					// 1페이지부터 5페이지까지 자를 거야
					int startpage = ((page - 1) / 5) * 5 + 1;
					System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

					int endpage = startpage + 5 - 1;
					System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
					
					// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
					if (endpage > maxpage)
						endpage = maxpage;
					
					JsonObject obj = new JsonObject();
					obj.addProperty("page", page);
					obj.addProperty("maxpage", maxpage);
					obj.addProperty("startpage", startpage);
					obj.addProperty("endpage", endpage);
					obj.addProperty("listcount", listcount);
					obj.addProperty("limit", limit);
					
					JsonArray arr = new Gson().toJsonTree(list).getAsJsonArray();
					obj.add("placelist", arr);
					
					String json = gson.toJson(obj);

					response.setContentType("text/html;charset=utf-8");
					response.setHeader("cache-control", "no-cache-,no-store");
					System.out.println(json);
					response.getWriter().append(json);
				} else {
					// 총 리스트 수를 받아온다.
					int listcount = dao.getAllSelCount(place, subplace, kind, search);
	
					// 리스트를 받아온다.
					JsonArray arr = dao.getAllSel(page, limit, place, subplace, kind, search);
	
					// 한 페이지에 6개의 게시물을 보여줄 거야
					int maxpage = (listcount + limit - 1) / limit;
					System.out.println("총 페이지 수 = " + maxpage);
	
					// 1페이지부터 5페이지까지 자를 거야
					int startpage = ((page - 1) / 5) * 5 + 1;
					System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);
	
					int endpage = startpage + 5 - 1;
					System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
					
					// 마지막 페이지 수가 최대 페이지 수보다 크면 최대 페이지
					if (endpage > maxpage)
						endpage = maxpage;
					
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
}
