package com.studentManager.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static void addCookie(String cookName, int time,HttpServletRequest request, HttpServletResponse response, String stuCode, String password) {
		//���ݱ���ѧ�ź������cookie�����ֻ�ȡcookie
		Cookie cookie =getCookieByName(request,cookName);
		//�������Ŀ����ѧ�ź������cookie������cookie��ֵ��û�����½�
		if (cookie != null) {
			cookie.setValue(stuCode+"#"+password);
		}else {
			 cookie = new Cookie(cookName, stuCode+"#"+password);
		}
		
		
		//����cookieʱ��
		cookie.setMaxAge(time);
		//����cookie���÷�Χ
		cookie.setPath(request.getContextPath());
		System.out.println(request.getContextPath());
	}

	 public static Cookie getCookieByName(HttpServletRequest request, String cookName) {
		//����cookie �ж��Ƿ��� ����cookie
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
