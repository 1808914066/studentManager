package com.studentManager.util;
//��¼���ع�����
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.studentManager.bean.User;
import com.studentManager.service.UserService;
import com.studentManager.service.UserServiceImpl;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("*.action")
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//�ж��û��Ƿ��¼����¼���У��߳������󷽷�
		System.out.println("=============��¼������===========");
		HttpServletRequest httpServletRequest =(HttpServletRequest)request;
		HttpSession session = httpServletRequest.getSession();
		
		//��ȡ��¼�󱣴���session�е��û���Ϣ
		User user = (User)session.getAttribute("session_user");
		UserService userService = new UserServiceImpl();
		//�ж��û��Ƿ��¼
		if (user != null) {
			//��¼���У��߳������󷽷�
			//ͨ��user�жϵ�¼���û���ɫ�Ƿ��жԸ�����ķ���Ȩ��
			roleJudgment(user,httpServletRequest,chain,request,response);
			
			//chain.doFilter(request, response);
		}else {
			//�ж�cookie�Ƿ����û���Ϣ
			//����cookie���Ƿ��б����û���Ϣ
			Cookie cookie =CookieUtil.getCookieByName(httpServletRequest, "cookie_name_pass");
			if (cookie != null) {
				String stuCodePass =URLDecoder.decode(cookie.getValue(), "utf-8") ;				
				String[] stuCodePass2 = stuCodePass.split("#");
				//�ж�ѧ�ź������Ƿ���Ч
				User user2 = userService.findByStuCodeAndPass(stuCodePass2[0],stuCodePass2[1]);
			if (user2 != null) {
				
				roleJudgment(user2,httpServletRequest,chain,request,response);
				httpServletRequest.getSession().setAttribute("session_user", user2);
				
				//����Ϣ����¼�ɹ�
				/*httpServletRequest.getSession().setAttribute("session_user", user2);
				chain.doFilter(request, response);*/
			}else {
				request.setAttribute("error", "���ȵ�¼�û���");
				//��û�е�¼����ת����¼����
				request.getRequestDispatcher("/index.jsp").forward(httpServletRequest, response);
		
			}
			}else {
				request.setAttribute("error", "���ȵ�¼�û���");
				//��û�е�¼����ת����¼����
				request.getRequestDispatcher("/index.jsp").forward(httpServletRequest, response);		
			}
			
		}
		
	}

	private void roleJudgment(User user, HttpServletRequest httpServletRequest, FilterChain chain,
			ServletRequest request, ServletResponse response) throws IOException, ServletException {
		//��ɫId
		Integer roleId = user.getRoleId();
		//��ȡ�����ַ
		String requestUrl = httpServletRequest.getRequestURI();
		
		String path = httpServletRequest.getContextPath();
		System.out.println(path);
		if((requestUrl.startsWith(path+"/dormBuild.action")||requestUrl.startsWith(path+"/dormManager.action"))&& roleId.equals(0)) {
			httpServletRequest.getSession().setAttribute("session_user", user);			
			chain.doFilter(httpServletRequest, response);
		}else if(requestUrl.startsWith(path+"/student.action")&&!roleId.equals(2)) {
			httpServletRequest.getSession().setAttribute("session_user", user);			
			chain.doFilter(httpServletRequest, response);
		}else if(requestUrl.startsWith(path+"/record.action")||
				requestUrl.startsWith(path+"/password.action")||
				requestUrl.startsWith(path+"/loginOut.action")||
				requestUrl.startsWith(path+"/index.action")) {
			httpServletRequest.getSession().setAttribute("session_user", user);		
			chain.doFilter(httpServletRequest, response);			
		}else {
			httpServletRequest.getRequestDispatcher("/index.jsp").forward(httpServletRequest, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
