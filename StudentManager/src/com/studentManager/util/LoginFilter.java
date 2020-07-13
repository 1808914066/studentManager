package com.studentManager.util;
//登录拦截过滤器
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
		//判断用户是否登录，登录放行，走出该请求方法
		System.out.println("=============登录拦截器===========");
		HttpServletRequest httpServletRequest =(HttpServletRequest)request;
		HttpSession session = httpServletRequest.getSession();
		
		//获取登录后保存在session中的用户信息
		User user = (User)session.getAttribute("session_user");
		UserService userService = new UserServiceImpl();
		//判断用户是否登录
		if (user != null) {
			//登录放行，走出该请求方法
			//通过user判断登录的用户角色是否有对该请求的访问权限
			roleJudgment(user,httpServletRequest,chain,request,response);
			
			//chain.doFilter(request, response);
		}else {
			//判断cookie是否有用户信息
			//遍历cookie，是否有保存用户信息
			Cookie cookie =CookieUtil.getCookieByName(httpServletRequest, "cookie_name_pass");
			if (cookie != null) {
				String stuCodePass =URLDecoder.decode(cookie.getValue(), "utf-8") ;				
				String[] stuCodePass2 = stuCodePass.split("#");
				//判断学号和密码是否有效
				User user2 = userService.findByStuCodeAndPass(stuCodePass2[0],stuCodePass2[1]);
			if (user2 != null) {
				
				roleJudgment(user2,httpServletRequest,chain,request,response);
				httpServletRequest.getSession().setAttribute("session_user", user2);
				
				//有信息，登录成功
				/*httpServletRequest.getSession().setAttribute("session_user", user2);
				chain.doFilter(request, response);*/
			}else {
				request.setAttribute("error", "请先登录用户！");
				//如没有登录。跳转到登录界面
				request.getRequestDispatcher("/index.jsp").forward(httpServletRequest, response);
		
			}
			}else {
				request.setAttribute("error", "请先登录用户！");
				//如没有登录。跳转到登录界面
				request.getRequestDispatcher("/index.jsp").forward(httpServletRequest, response);		
			}
			
		}
		
	}

	private void roleJudgment(User user, HttpServletRequest httpServletRequest, FilterChain chain,
			ServletRequest request, ServletResponse response) throws IOException, ServletException {
		//角色Id
		Integer roleId = user.getRoleId();
		//获取请求地址
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
