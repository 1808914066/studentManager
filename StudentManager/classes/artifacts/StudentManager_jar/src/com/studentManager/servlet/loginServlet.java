package com.studentManager.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.studentManager.bean.User;
import com.studentManager.service.UserService;
import com.studentManager.service.UserServiceImpl;
import com.studentManager.util.CookieUtil;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/login")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("=============================µÇÂ¼==============================================");
		request.setCharacterEncoding("utf-8");
		String stuCode = request.getParameter("stuCode");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		System.out.println("stuCode="+stuCode+"password="+password+"remember="+remember);	
		
		UserService userService = new UserServiceImpl();
		User user = userService.findByStuCodeAndPass(stuCode,password);
		
		System.out.println("user="+user);
		if (user == null) {
			request.setAttribute("error", "ÄúÊäÈëµÄÑ§ºÅ»òÃÜÂë´íÎó£¡");
			request.getRequestDispatcher("index.jsp").forward(request,response);
		}else {
			if(remember!=null && remember.equals("remember-me")) {
				CookieUtil.addCookie("cookie_name_pass",7*24*60*60,request,response,URLEncoder.encode(stuCode, "utf-8"),URLEncoder.encode(password, "utf-8"));			
				}
			request.getSession().setAttribute("session_user",user);
			request.getRequestDispatcher("/main/main.jsp").forward(request,response);
			
		}
	}

}
