package com.studentManager.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static void addCookie(String cookName, int time,HttpServletRequest request, HttpServletResponse response, String stuCode, String password) {
		//根据保存学号和密码的cookie的名字获取cookie
		Cookie cookie =getCookieByName(request,cookName);
		//如果有项目保存学号和密码的cookie，更改cookie的值，没有则新建
		if (cookie != null) {
			cookie.setValue(stuCode+"#"+password);
		}else {
			 cookie = new Cookie(cookName, stuCode+"#"+password);
		}
		
		
		//设置cookie时间
		cookie.setMaxAge(time);
		//设置cookie作用范围
		cookie.setPath(request.getContextPath());
		System.out.println(request.getContextPath());
	}

	 public static Cookie getCookieByName(HttpServletRequest request, String cookName) {
		//遍历cookie 判断是否有 返回cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null&&cookies.length>0) {
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(cookName)) {
					return cookie;
				}
			}
		}
		return null;
	}

}
