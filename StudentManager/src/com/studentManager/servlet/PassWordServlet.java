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
			//��ת���޸�����ҳ��
			request.setAttribute("mainRight","passwordChange.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);		
		
		}else if (action!=null && action.equals("ajaxOldPassWord")) {
			//У���û������ԭ�����Ƿ���ȷ
			String oldPassWord = request.getParameter("oldPassWord");
			String passWord = userCurr.getPassWord();
			if(!passWord.equals(oldPassWord)) {
				response.setCharacterEncoding("utf-8");
				PrintWriter writer = response.getWriter();				
				writer.print("�������ԭʼ�����������������");
			}
		}else if (action!=null && action.equals("change")) {
			//�޸�����
			String newPassWord = request.getParameter("newPassword");
			userCurr.setPassWord(newPassWord);
			userService.updatePassWord(userCurr);
			System.out.println("�޸�����usercurr:"+userCurr);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

}
