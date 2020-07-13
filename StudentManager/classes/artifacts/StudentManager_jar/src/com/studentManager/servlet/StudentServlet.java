package com.studentManager.servlet;

import java.io.IOException;
import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.studentManager.bean.DormBuild;
import com.studentManager.bean.User;
import com.studentManager.dao.UserDao;
import com.studentManager.service.DormBuildService;
import com.studentManager.service.DormBuildServiceImpl;
import com.studentManager.service.UserService;
import com.studentManager.service.UserServiceImpl;

/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/student.action")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("=========================进入student.action==================");
		request.setCharacterEncoding("utf-8");
		String action =	request.getParameter("action");
		String id =request.getParameter("id");
		String disabled =request.getParameter("disabled");
		User user =(User) request.getSession().getAttribute("session_user");
		Integer roleId =user.getRoleId();
		DormBuildService dormBuildService = new DormBuildServiceImpl();
		UserService userService = new UserServiceImpl();
		
		List<DormBuild> builds = new ArrayList<DormBuild>();			
		if(roleId.equals(0)) {
		//用户为超级管理员，能将学生添加到所有宿舍楼
			builds = dormBuildService.findAll();
		}else if(roleId.equals(1)) {
		//用户为宿舍管理员，只能添加学生到他管理的宿舍楼
			builds = dormBuildService.findByUserId(user.getId());
		}
		System.out.println("builds:"+builds);
		
		//=================================跳转到学生的添加页面===================================
		request.setAttribute("builds", builds);
		
		if (action != null & action.equals("list")) {
		//===================================查询学生在右侧展示===================================
			String dormBuildId = request.getParameter("dormBuildId");
			String searchType = request.getParameter("searchType");
			String keyword = request.getParameter("keyword");
			System.out.println("dormBuildId:"+dormBuildId);
						
			List<User> students = userService.findStudent(dormBuildId,searchType,keyword,user);
			request.setAttribute("dormBuildId", dormBuildId);
			request.setAttribute("searchType",searchType);
			request.setAttribute("keyword",  keyword);		
			request.setAttribute("students", students);
			System.out.println("students============"+students);
			request.setAttribute("mainRight","studentList.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);
		
		}else if (action != null & action.equals("preAdd")) {
			//===========================根据用户角色查询宿舍楼进行添加学生========================
			
			request.setAttribute("mainRight","studentAddOrUpdate.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);
		
		}else if (action != null & action.equals("save")) {
			//==================================保存学生===========================================
			String stuCode = request.getParameter("stuCode");			
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			String tel = request.getParameter("tel");
			String passWord = request.getParameter("passWord");
			String dormBuildId = request.getParameter("dormBuildId");
			String dormCode = request.getParameter("dormCode");
			
			System.out.println("====="+stuCode+"====="+name+"====="+sex+"====="+tel+"====="+passWord+"====="+dormBuildId+"====="+dormCode);			
			
			User student = userService.findByStuCode(stuCode);			
			
			if(id!=null && !id.isEmpty()) {
				//===============================更新=====================
				if (student != null && !student.getId().equals(Integer.parseInt(id))) {
					System.out.println("根据学生学号查询student："+student);
					//当前学号的学生已存在，重修改，跳转到修改页面 
					request.getRequestDispatcher("/student.action?action=preUpdate&id="+id).forward(request, response);
				}else {
					User studentUpdate =userService.findById(Integer.parseInt(id));
					studentUpdate.setStuCode(stuCode);	
					studentUpdate.setSex(sex);
					studentUpdate.setTel(tel);
					studentUpdate.setName(name);
					studentUpdate.setPassWord(passWord);
					studentUpdate.setDormBuildId(Integer.parseInt(dormBuildId));
					studentUpdate.setDormCode(dormCode);	
					
					userService.updateStudent(studentUpdate);
					response.sendRedirect(getServletContext().getContextPath()+"/student.action?action=list");
				}
			}else {
				//保存
				if (student != null) {
					response.sendRedirect(getServletContext().getContextPath()+"/student.action?action=preAdd");
				}else {
					User user2 = new User();
					user2.setStuCode(stuCode);	
					user2.setSex(sex);
					user2.setTel(tel);
					user2.setName(name);
					user2.setPassWord(passWord);
					user2.setDormBuildId(Integer.parseInt(dormBuildId));
					user2.setDormCode(dormCode);
					user2.setRoleId(2);
					user2.setCreatUserId(user.getId());				
					userService.saveStudent(user2);
					response.sendRedirect(getServletContext().getContextPath()+"/student.action?action=list");				
				}
			}
		
			
		}else if (action != null & action.equals("preUpdate")) {
			//==============================跳转到学生修改页面====================================
			User userUpdate = userService.findById(Integer.parseInt(id));
			//通过学生id查找学生并保存，以便在页面展示
			System.out.println("userUpdate:"+userUpdate);
			
			//判断当前登录的用户是否有修改某个学生的权限，没有返回学生管理列表页，有则跳转到修改页
			User user2 = userService.findByUserAndManager(userUpdate.getId(),user);
			System.out.println("查询管理权限user2："+user2);
			if(user2 != null) {
				request.setAttribute("userUpdate",userUpdate);
				request.setAttribute("mainRight","studentAddOrUpdate.jsp");
				request.getRequestDispatcher("/main/main.jsp").forward(request, response);
			
			}else {
				response.sendRedirect(getServletContext().getContextPath()+"/student.action?action=list");
				
			}
			
			
		}else if (action != null & action.equals("deleteOrActive")) {
			User studentUpdate =userService.findById(Integer.parseInt(id));
			studentUpdate.setDisabled(Integer.parseInt(disabled));	
			
			User user2 = userService.findByUserAndManager(studentUpdate.getId(),user);
			System.out.println("查询管理权限user2："+user2);
			if(user2 != null) {
				userService.updateStudent(studentUpdate);
				response.sendRedirect(getServletContext().getContextPath()+"/student.action?action=list");			
			}else {
				response.sendRedirect(getServletContext().getContextPath()+"/student.action?action=list");				
			}			
		}	
	}
}

