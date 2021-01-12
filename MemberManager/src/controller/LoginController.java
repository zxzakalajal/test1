package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ModelAndView;
import service.MemberService;
import vo.MemberVO;

public class LoginController implements Controller {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		MemberVO vo = MemberService.getInstance().login(id, pass);
		HttpSession session = request.getSession();
		ModelAndView view = new ModelAndView();
		if(vo != null) {
			//세션 처리
			session.setAttribute("login", true);
			session.setAttribute("id", vo.getId());
			session.setAttribute("name", vo.getName());
			session.setAttribute("grade", vo.getGrade().toLowerCase());
			view.setPage("main.jsp");
			view.setSendRedirect(true);
		}else {
			session.setAttribute("login", false);
			view.setPage("login.jsp");
			view.setSendRedirect(true);
		}
		return view;
	}

}



