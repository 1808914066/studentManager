package com.studentManager.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.studentManager.bean.User;
import com.studentManager.service.UserService;
import com.studentManager.service.UserServiceImpl;

/**
 * Servlet implementation class PassWordServlet
 */
@WebServlet("/password.action")
public class PassWordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PassWordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("=========================password.action============================");
		String action =	request.getParameter("action");
		System.out.println("action:"+action);
		User userCurr =(User) request.getSession().getAttribute("session_user");
		UserService userService = new UserServiceImpl();
		
		if(action!=null && action.equals("preChange")) {
			//跳转到修改密码页面
			request.setAttribute("mainRight","passwordChange.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);		
		
		}else if (action!=null && action.equals("ajaxOldPassWord")) {
			//校验用户输入的原密码是否正确
			String oldPassWord = request.getParameter("oldPassWord");
			String passWord = userCurr.getPassWord();
			if(!passWord.equals(oldPassWord)) {
				response.setCharacterEncoding("utf-8");
				PrintWriter writer = response.getWriter();				
				writer.print("您输入的原始密码错误！请重新输入");
			}
		}else if (action!=null && action.equals("change")) {
			//修改密码
			String newPassWord = request.getParameter("newPassword");
			userCurr.setPassWord(newPassWord);
			userService.updatePassWord(userCurr);
			System.out.println("修改密码usercurr:"+userCurr);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

}
