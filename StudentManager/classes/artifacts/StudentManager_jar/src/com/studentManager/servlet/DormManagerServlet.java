package com.studentManager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.studentManager.bean.DormBuild;
import com.studentManager.bean.User;
import com.studentManager.service.DormBuildService;
import com.studentManager.service.DormBuildServiceImpl;
import com.studentManager.service.UserService;
import com.studentManager.service.UserServiceImpl;

/**
 * Servlet implementation class DormManagerServlet
 */
@WebServlet("/dormManager.action")
public class DormManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DormManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("===============dormManager==========");
		request.setCharacterEncoding("utf-8");
		String action =	request.getParameter("action");
		//================宿管ID=================
		String id =	request.getParameter("id");
		
		UserService userService = new UserServiceImpl();
		DormBuildService dormBuildService = new DormBuildServiceImpl();
		//===========================查询所有宿舍楼并保存================================
		List<DormBuild> builds = dormBuildService.find();
		request.setAttribute("builds", builds);
		
		if (action != null&&action.equals("list")) {
		//===========================宿舍管理员查询======================================
			String searchType= request.getParameter("searchType");
			String keyword = request.getParameter("keyword");
			System.out.println("searchType:"+searchType+"keyword:"+keyword);
			//查询宿舍管理员
			List<User> users = userService.findManager(searchType,keyword);
			
			request.setAttribute("searchType", searchType);
			request.setAttribute("keyword",  keyword);
			request.setAttribute("users", users);
			request.setAttribute("mainRight","dormManagerList.jsp" );
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);	
		}else if (action != null&&action.equals("preAdd")) {
		//==========================跳转到宿舍管理员添加页面================================
			
			request.setAttribute("mainRight","dormManagerAddOrUpdate.jsp" );
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);				
		}else if (action != null&&action.equals("save")) {
			System.out.println("=========保存宿舍管理员===========");
			//保存宿舍管理员
			String name = request.getParameter("name");
			String passWord = request.getParameter("passWord");
			String sex = request.getParameter("sex");
			String tel = request.getParameter("tel");
			//========================获取前端传过来的数据存入数组==============================
			String[] dormBuildIds = request.getParameterValues("dormBuildId");
			System.out.println("name:"+name+"password:"+passWord+"sex:"+sex+"tel"+tel);
			
			if(id==null||id.isEmpty()) {
				//===============================保存==============================
				User user =new User(name,passWord,sex,tel,null,1);
				user.setDisabled(0);
				//当前登录用户
				User user2 = (User) request.getSession().getAttribute("session_user");
				user.setCreatUserId(user2.getId());				
				userService.saveManager(user,dormBuildIds);				
			}else {
				//修改
				//通过宿管ID获取宿舍管理员
				User user =userService.findById(Integer.parseInt(id));
				user.setName(name);
				user.setPassWord(passWord);
				user.setSex(sex);
				user.setTel(tel);
				userService.updateManager(user);
				//修改还需修改宿舍管理员与宿舍楼的中间表
				
				//删除当前宿舍管理员管理的所有宿舍楼
				dormBuildService.deleteByUserId(user.getId());
				//新增当前宿舍管理员管理的所有宿舍楼
				dormBuildService.saveManagerAndBuild(user.getId(),dormBuildIds);
			}
			//跳转到宿舍管理员列表页面，查询所有宿舍管理员
			response.sendRedirect(getServletContext().getContextPath()+"/dormManager.action?action=list");
	
			}else if (action != null&&action.equals("preUpdate")) {
			//============================修改页面==============================
			//根据宿管id跳转页面
		
			User user =userService.findById(Integer.parseInt(id));
			//根据宿管ID获得管理楼栋
			List<DormBuild> userBuilds = dormBuildService.findByUserId(user.getId());
			user.setDormBuilds(userBuilds);
			System.out.println("user================="+user);
			//遍历当前宿舍管理员管理的所有宿舍，并获取
			List<Integer> userBuildids = new ArrayList<>();
			for(DormBuild userBuild:userBuilds) {
				userBuildids.add(userBuild.getId());
			}
			request.setAttribute("userBuildids", userBuildids);
			request.setAttribute("user", user);
			request.setAttribute("mainRight","dormManagerAddOrUpdate.jsp" );
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);;				
	
		}else if (action != null&&action.equals("deleteOrAcive")) {
			//删除或激活
			String disabled = request.getParameter("disabled");
			//修改
			//通过宿管ID获取宿舍管理员
			User user =userService.findById(Integer.parseInt(id));
			user.setDisabled(Integer.parseInt(disabled));
			userService.updateManager(user);
			response.sendRedirect(getServletContext().getContextPath()+"/dormManager.action?action=list");
			
		}
		}

}
