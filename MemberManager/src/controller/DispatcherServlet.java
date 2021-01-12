package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ModelAndView;

/**
 * Servlet implementation class DispatcherServlet
 */
@WebServlet({"/","*.do"})
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DispatcherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] arr = request.getRequestURI().split("/");
		System.out.println(arr[arr.length-1]);
		Controller controller = HandlerMapping.getInstance().createController(arr[arr.length-1]);
		ModelAndView view = null;
		if(controller != null)
			view = controller.execute(request, response);
		//결과 뷰로 이동
		if(view == null) view = new ModelAndView("main.jsp", true);
		if(view.isSendRedirect()) {
			response.sendRedirect(view.getPage());
		}else {
			request.getRequestDispatcher(view.getPage()).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}











