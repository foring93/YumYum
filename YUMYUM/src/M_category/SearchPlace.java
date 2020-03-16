package M_category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import M_category.PlaceDAO;
import M_category.PlaceVO;

@WebServlet("/M_category/search.place")
public class SearchPlace extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		//PlaceDAO dao = new PlaceDAO();

		String searchWord = request.getParameter("place");
		String originWord = searchWord;
		searchWord = searchWord.replaceAll(" ", "");
		searchWord = searchWord.replaceAll("\\p{Z}", "");
		searchWord = searchWord.toUpperCase();
		searchWord = searchWord.replaceAll("#", "");
		System.out.println("검색어는 " + searchWord + "!!!");

		//List<PlaceVO> arr = new ArrayList<PlaceVO>();
		//arr = dao.searchPlace(searchWord);
		//int count = arr.size();

		//System.out.println("총 " + count + " 건의 검색결과 나옴");

		response.setContentType("text/html;charset=utf-8");
		request.setAttribute("searchWord", originWord);
		// request.setAttribute("searchedData", arr);
		//request.setAttribute("searchCount", count);

		RequestDispatcher dispatcher = request.getRequestDispatcher("../M_category/Y_All.jsp");
		dispatcher.forward(request, response);
	}
}
