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
		System.out.println("=========================����student.action==================");
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
		//�û�Ϊ��������Ա���ܽ�ѧ����ӵ���������¥
			builds = dormBuildService.findAll();
		}else if(roleId.equals(1)) {
		//�û�Ϊ�������Ա��ֻ�����ѧ���������������¥
			builds = dormBuildService.findByUserId(user.getId());
		}
		System.out.println("builds:"+builds);
		
		//=================================��ת��ѧ�������ҳ��===================================
		request.setAttribute("builds", builds);
		
		if (action != null & action.equals("list")) {
		//===================================��ѯѧ�����Ҳ�չʾ===================================
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
			//===========================�����û���ɫ��ѯ����¥�������ѧ��========================
			
			request.setAttribute("mainRight","studentAddOrUpdate.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);
		
		}else if (action != null & action.equals("save")) {
			//==================================����ѧ��===========================================
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
				//===============================����=====================
				if (student != null && !student.getId().equals(Integer.parseInt(id))) {
					System.out.println("����ѧ��ѧ�Ų�ѯstudent��"+student);
					//��ǰѧ�ŵ�ѧ���Ѵ��ڣ����޸ģ���ת���޸�ҳ�� 
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
				//����
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
			//==============================��ת��ѧ���޸�ҳ��====================================
			User userUpdate = userService.findById(Integer.parseInt(id));
			//ͨ��ѧ��id����ѧ�������棬�Ա���ҳ��չʾ
			System.out.println("userUpdate:"+userUpdate);
			
			//�жϵ�ǰ��¼���û��Ƿ����޸�ĳ��ѧ����Ȩ�ޣ�û�з���ѧ�������б�ҳ��������ת���޸�ҳ
			User user2 = userService.findByUserAndManager(userUpdate.getId(),user);
			System.out.println("��ѯ����Ȩ��user2��"+user2);
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
			System.out.println("��ѯ����Ȩ��user2��"+user2);
			if(user2 != null) {
				userService.updateStudent(studentUpdate);
				response.sendRedirect(getServletContext().getContextPath()+"/student.action?action=list");			
			}else {
				response.sendRedirect(getServletContext().getContextPath()+"/student.action?action=list");				
			}			
		}	
	}
}

