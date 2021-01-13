package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.QnaDTO;
import model.ModelAndView;
import service.QnAService;

public class InsertQnAController implements Controller {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = (String) request.getSession().getAttribute("id");
		ModelAndView view = null;
		int count = QnAService.getInstance().insertQnA(new QnaDTO(title, content, writer));
 
		try {
			if (count == 0) {
				response.getWriter().append("<script>alert('문의 등록중 문제가 생각다.');" + "history.back();</script>");
			} else {
				view = new ModelAndView("qnaView.do", true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return view;
	}

}
