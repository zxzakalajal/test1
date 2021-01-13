package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ModelAndView;
import service.MemberService;

public class UpdateViewController implements Controller {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
		String id = (String) request.getSession().getAttribute("id");
		request.setAttribute("vo", MemberService.getInstance().selectMemberVO(id));	
		return new ModelAndView("member_update_view.jsp", false);
	}

}
