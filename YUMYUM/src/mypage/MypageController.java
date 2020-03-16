package mypage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import join.Member;
@WebServlet("*.co")
public class MypageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MypageController() {
        super();
    }
    public void doProcess(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException{
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html;charset=UTF-8");
    	String page = request.getParameter("page");
    	System.out.println("*.co옴");
    	HttpSession session = request.getSession();
    	mypageinfoDAO dao = new mypageinfoDAO();
    	
    	if(page==null) {//초기 페이지 
    		String id = (String)session.getAttribute("id");
    		System.out.println("여기는 MypageController~~Y_myinfo");
    		Member m = dao.user_info(id);
    		JsonObject json = new JsonObject();
    		json.addProperty("id", m.getUSER_ID());
    		json.addProperty("pass", m.getUSER_PASS());
    		json.addProperty("name", m.getUSER_NAME());
    		json.addProperty("gender", m.getUSER_GENDER());
    		json.addProperty("nickname", m.getUSER_NICKNAME());
    		json.addProperty("birthday", m.getUSER_BIRTHDAY());
    		json.addProperty("address", m.getUSER_ADDRESS());
    		json.addProperty("postcode", m.getUSER_POSTCODE());
    		json.addProperty("phone", m.getUSER_PHONE());
    		json.addProperty("email", m.getUSER_EMAIL());
    		Gson gson = new Gson();
    		String a = gson.toJson(json);
    		response.getWriter().append(a);
    	}else if(page.equals("Y_myinfo.co")) {//내 정보보기 눌렀을때
    		String id = (String)request.getAttribute("id");
    		System.out.println("여기는 MypageController~~Y_myinfo");
    		Member m = dao.user_info(id);
    		JsonObject json = new JsonObject();
    		json.addProperty("id", m.getUSER_ID());
    		json.addProperty("pass", m.getUSER_PASS());
    		json.addProperty("name", m.getUSER_NAME());
    		json.addProperty("gender", m.getUSER_GENDER());
    		json.addProperty("nickname", m.getUSER_NICKNAME());
    		json.addProperty("birthday", m.getUSER_BIRTHDAY());
    		json.addProperty("address", m.getUSER_ADDRESS());
    		json.addProperty("postcode", m.getUSER_POSTCODE());
    		json.addProperty("phone", m.getUSER_PHONE());
    		json.addProperty("email", m.getUSER_EMAIL());
    		Gson gson = new Gson();
    		String a = gson.toJson(json);
    		request.setAttribute("page", request.getParameter("page"));
    		response.getWriter().append(a);
    	}
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
