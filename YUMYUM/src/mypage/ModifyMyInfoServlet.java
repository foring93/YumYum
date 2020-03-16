package mypage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import join.Member;


@WebServlet("/mypage/ModifyMyInfo")
public class ModifyMyInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ModifyMyInfoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 세션에 있는 아이디를 받아와 dao에서 내가 입력한 정보를 찾아옴
		// 정보 수정 페이지에서 처음에 띄워줄 때 doGet 방식 사용 
		String id = (String)session.getAttribute("id");
		mypageinfoDAO dao = new mypageinfoDAO();
		Member m = dao.user_info(id);
		request.setAttribute("id", m.getUSER_ID());
		request.setAttribute("name", m.getUSER_NAME());
		request.setAttribute("birthday", m.getUSER_BIRTHDAY());
		request.setAttribute("email", m.getUSER_EMAIL());
		request.setAttribute("gender", m.getUSER_GENDER());
		request.setAttribute("nickname", m.getUSER_NICKNAME());
		request.setAttribute("pass", m.getUSER_PASS());
		request.setAttribute("phone", m.getUSER_PHONE());
		request.setAttribute("postcode", m.getUSER_POSTCODE());
		request.setAttribute("address", m.getUSER_ADDRESS());
		request.setAttribute("user_is_owner", m.getUSER_IS_OWNER());
		request.setAttribute("business_no", m.getUSER_BUSINESS_NO());
		//request 들고 Y_myinfomodify.jsp로 이동 
		RequestDispatcher dispatcher = request.getRequestDispatcher("Y_myinfomodify.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 수정하기를 눌렀을때는 post 방식이므로 여기서 dao를 통해 정보 수정을 함
		// password, email, 우편번호, 주소, 휴대폰 번호, 닉네임, USER_IS_OWNER만 수정 가능
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		Member m = new Member();
		m.setUSER_ID(request.getParameter("id"));
		m.setUSER_PASS(request.getParameter("pass"));
		m.setUSER_EMAIL(request.getParameter("email"));
		m.setUSER_POSTCODE(request.getParameter("post1"));
		m.setUSER_ADDRESS(request.getParameter("address"));
		m.setUSER_PHONE(request.getParameter("phone"));
		m.setUSER_NICKNAME(request.getParameter("nickname"));
		m.setUSER_IS_OWNER(Integer.parseInt(request.getParameter("who")));
		m.setUSER_BUSINESS_NO(request.getParameter("business_no"));
		HttpSession session = request.getSession();
		String nickname = (String)session.getAttribute("nickname");
		mypageinfoDAO dao = new mypageinfoDAO();
		int result =dao.user_modify(m, nickname);
		if(result>0) {//정보 수정 성공 시 마이페이지 첫 화면으로 돌아감
			System.out.println("정보 수정 성공");
			session.setAttribute("nickname", m.getUSER_NICKNAME());
			session.setAttribute("user_is_owner", request.getParameter("who"));
			response.sendRedirect("Y_mypage.jsp");
		}else {
			System.out.println("정보 수정 실패");
		}
		
	}

}
