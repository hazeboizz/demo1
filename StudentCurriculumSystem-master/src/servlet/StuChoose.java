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
import bean.TeacherBean;
/**
 * Servlet implementation class StuChoose
 */
@WebServlet("/StuChoose")
public class StuChoose extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StuChoose() {
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
		String num = "";
		String colnum=request.getParameter("colnum");
		Cookie cookie = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("num"))
					num = cookie.getValue();
			}
		}
		StuDao stuDao = new StuDao();
		boolean flag = true;
		if(colnum!=null) {
			int i=Integer.parseInt(colnum);
			flag=stuDao.chooseCourse(num, i);
			if(!flag) {
				RequestDispatcher rDispatcher = request.getRequestDispatcher("/WEB-INF/repeat.jsp");
				rDispatcher.forward(request, response);
			}
			else {
				ArrayList<CourseBean> cList = new ArrayList<>();
				cList = stuDao.getNoSelect(num);
				request.setAttribute("clist", cList);
				ArrayList<TeacherBean> tList = new ArrayList<TeacherBean>();
				for (i = 0; i < cList.size(); i++) {
					TeacherBean teacherBean = new TeacherBean();
					teacherBean = stuDao.getTeacher(cList.get(i).getTeachernum());
					tList.add(teacherBean);
				}
				request.setAttribute("tlist", tList);
				RequestDispatcher rDispatcher = request.getRequestDispatcher("/WEB-INF/stuchoose.jsp");
				rDispatcher.forward(request, response);
			}
		}
		else {
			ArrayList<CourseBean> cList = new ArrayList<>();
			cList = stuDao.getNoSelect(num);
			request.setAttribute("clist", cList);
			ArrayList<TeacherBean> tList = new ArrayList<TeacherBean>();
			for (int i = 0; i < cList.size(); i++) {
				TeacherBean teacherBean = new TeacherBean();
				teacherBean = stuDao.getTeacher(cList.get(i).getTeachernum());
				tList.add(teacherBean);
			}
			request.setAttribute("tlist", tList);
			if(cList.isEmpty()) {
				request.setAttribute("flag", "现无可选课程");
			}
			if(flag == true) {
				RequestDispatcher rDispatcher = request.getRequestDispatcher("/WEB-INF/stuchoose.jsp");
				rDispatcher.forward(request, response);
			}
			else {
					response.sendRedirect("/WEB-INF/stuchoose.jsp");
			}
		}
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
