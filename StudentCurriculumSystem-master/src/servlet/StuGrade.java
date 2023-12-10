package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StuDao;
import bean.CourseBean;

/**
 * Servlet implementation class StuGrade
 */
@WebServlet("/StuGrade")
public class StuGrade extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StuGrade() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		float sum = 0;
		String num = "";
		Cookie cookie = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("num"))
					num = cookie.getValue();
			}
		}
		String year = (String) request.getParameter("year");
		String term = (String) request.getParameter("term");
		StuDao stuDao = new StuDao();
		if(year==null) {
			ArrayList<CourseBean> cList=new ArrayList<>();
			request.setAttribute("clist", cList);
		}
		else {
			ArrayList<CourseBean> cList=stuDao.getGrade(num, year, term);
			sum = stuDao.getTotalGrade(num, year, term);
			if(cList.isEmpty()) {
				request.setAttribute("flag", year+"年查询无结果");
			}
			request.setAttribute("clist", cList);
			
		}
		request.setAttribute("year", year);
		request.setAttribute("sum",sum);
		RequestDispatcher rDispatcher = request.getRequestDispatcher("/WEB-INF/stugrade.jsp");
		rDispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
