package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.MemberException;
import model.ModelAndView;
import service.MemberService;
import vo.MemberVO;

public class UpdateController implements Controller {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String name = request.getParameter("name");
		ModelAndView view = null;;
		int age = Integer.parseInt(request.getParameter("age"));
			try {
				if(id == null || pass == null || name == null) {
					response.getWriter().append("<script>alert('데이터를 전부 입력해 주세요'); history.back();</script>");
				}
				MemberVO vo = new MemberVO(id, pass, name, age);
				MemberService.getInstance().updateManageMemberVO(vo);
				request.getSession().setAttribute("name", vo.getName());
				view = new ModelAndView("main.jsp",true);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (MemberException e) {
				e.printStackTrace();
			}
			
		return view;
	}

}



