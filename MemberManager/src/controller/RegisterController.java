package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.MemberException;
import model.ModelAndView;
import service.MemberService;
import vo.MemberVO;

public class RegisterController implements Controller {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = null;
		String id = request.getParameter("id");
		String pass =request.getParameter("pass");
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		
		MemberVO vo = new MemberVO(id, pass, name, age);
		try {
			MemberService.getInstance().insertMemberVO(vo);
			view = new ModelAndView("login.jsp", true);
		} catch (MemberException e) {
			e.printStackTrace();
			try {
				response.getWriter().append("<script>alert('아이디에 해당하는 회원이 있습니다.');"
						+ "history.back();</script>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		return view;
	}

}








