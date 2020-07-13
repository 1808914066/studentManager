package com.studentManager.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.studentManager.bean.DormBuild;
import com.studentManager.bean.Record;
import com.studentManager.bean.User;
import com.studentManager.service.DormBuildService;
import com.studentManager.service.DormBuildServiceImpl;
import com.studentManager.service.RecordService;
import com.studentManager.service.RecordServiceImpl;
import com.studentManager.service.UserService;
import com.studentManager.service.UserServiceImpl;
import com.sun.org.apache.bcel.internal.generic.IFNULL;

/**
 * Servlet implementation class RecordServlet
 */
@WebServlet("/record.action")
public class RecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("======================record.action=====================");
		request.setCharacterEncoding("utf-8");
		String action =	request.getParameter("action");
		String id =	request.getParameter("id");
		User userCurr =(User) request.getSession().getAttribute("session_user");
		UserService userService = new UserServiceImpl();
		RecordService recordService = new RecordServiceImpl();
		DormBuildService dormBuildService = new DormBuildServiceImpl();
		if (action != null & action.equals("list")) {
			//=======================跳转到列表页。查询缺勤页面==================
			
			Integer roleId =userCurr.getRoleId();			
			List<DormBuild> builds = new ArrayList<DormBuild>();			
			if(roleId.equals(0)) {
			//用户为超级管理员，能将学生添加到所有宿舍楼
				builds = dormBuildService.findAll();
			}else if(roleId.equals(1)) {
			//用户为宿舍管理员，只能添加学生到他管理的宿舍楼
				builds = dormBuildService.findByUserId(userCurr.getId());
			}
			System.out.println("builds:"+builds);				
			request.setAttribute("builds", builds);	
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String dormBuildId = request.getParameter("dormBuildId");
			String searchType = request.getParameter("searchType");
			String keyword = request.getParameter("keyword");
			System.out.println("startDate:"+startDate+"endDate:"+endDate+"dormBuildId:"+dormBuildId+"searchType:"+searchType+"keyword:"+keyword);
			
			List<Record> records = recordService.findRecords(startDate,endDate,dormBuildId,searchType,keyword,userCurr);
			System.out.println("records:"+records);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("dormBuildId", dormBuildId);
			request.setAttribute("searchType", searchType);
			request.setAttribute("keyword", keyword);
			request.setAttribute("records", records);
			request.setAttribute("mainRight","recordList.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);		
		}else if (action != null && action.equals("preAdd")) {
			//=======================跳转到添加页面========================
			request.setAttribute("mainRight","recordAddOrUpdate.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);		
	
		}else if (action != null && action.equals("ajaxStuCode")) {
			//判断当前用户是否有添加该学号学生缺勤记录的权限
			String stuCode = request.getParameter("stuCode");
			User user = userService.findByStuCodeAndManager(stuCode,userCurr);
			System.out.println("是否有添加权限user:"+user);
			if (user == null) {
				response.setCharacterEncoding("utf-8");
				PrintWriter writer = response.getWriter();
				writer.print("您没有添加该学生学号缺勤的权限！无法修改！请重新输入！");
				writer.flush();
				writer.close();
			}
		}else if (action != null && action.equals("save")) {
			//=============================保存缺勤记录======================
			String stuCode = request.getParameter("stuCode");
			String date = request.getParameter("date");
			String remark = request.getParameter("remark");
			System.out.println(" stuCode:"+ stuCode+"date:"+date+"remark:"+remark);
			//==============查看用户是否有添加该学号缺勤记录的权限================
			User user = userService.findByStuCodeAndManager(stuCode,userCurr);
			System.out.println("查询添加缺勤权限user:"+user);
			
			if (id != null && !id.isEmpty()) {
				//=============================更新=======================
				if(user!=null) {
					Record record = recordService.findById(Integer.parseInt(id));
					
					try {
						record.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
						record.setRemark(remark);
						record.setStudentId(user.getId());
						recordService.update(record);					
						}catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
					
				}else {
					//保存
					response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
				}
				/*
				 * request.setAttribute("error", "您没有添加该学生学号缺勤的权限！");
				 * request.setAttribute("mainRight","recordAddOrUpdate.jsp");
				 * request.getRequestDispatcher("/main/main.jsp").forward(request, response);
				 */	}else {
				//保存
				if(user!=null){
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date date2 = null;
					try {
						date2 = formatter.parse(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Record record = new Record();
					record.setStudentId(user.getId());
					record.setDate(date2);
					record.setRemark(remark);
					record.setDisabled(0);
					recordService.save(record);
					response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");			
				}else {
					request.setAttribute("error", "您没有添加该学生学号缺勤的权限！");
					request.setAttribute("mainRight","recordAddOrUpdate.jsp");
					request.getRequestDispatcher("/main/main.jsp").forward(request, response);							
				}
			}
			
		}else if (action != null && action.equals("preUpdate")) {
			//================================跳转到修改页面===========================
			Record record = recordService.findById(Integer.parseInt(id));
			System.out.println("record:"+record);
			User user = userService.findByStuCodeAndManager(record.getUser().getStuCode(),userCurr);
			System.out.println("修改权限user："+user);
			if (user == null) {
				response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");		
			}else {
				request.setAttribute("record",record);
				request.setAttribute("mainRight","recordAddOrUpdate.jsp");
				request.getRequestDispatcher("/main/main.jsp").forward(request, response);							
			}		
		}else if (action != null && action.equals("deleteOrAcive")) {
			//===========删除或激活=========================
			String disabled = request.getParameter("disabled");
			Record record = recordService.findById(Integer.parseInt(id));
			User user = userService.findByStuCodeAndManager(record.getUser().getStuCode(),userCurr);
			System.out.println("修改权限user："+user);
			if (user == null) {
				response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");		
			}else {
			
			record.setDisabled(Integer.parseInt(disabled));
			recordService.update(record);
			response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
		}
	}
	}
}
