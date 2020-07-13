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
import com.studentManager.service.DormBuildService;
import com.studentManager.service.DormBuildServiceImpl;

/**
 * Servlet implementation class DormBuildServlet
 */
@WebServlet("/dormBuild.action")
public class DormBuildServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DormBuildServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("===========dormBuild.action======");
		//防止乱码
		request.setCharacterEncoding("utf-8");		
		String action =	request.getParameter("action");
		String id = request.getParameter("id");
		System.out.println("action="+action);
		
		DormBuildService dormBuildService = new DormBuildServiceImpl();
		if (action != null&&action.equals("list")) {
			
			List<DormBuild> builds = new ArrayList<DormBuild>();
			if (id == null||id.isEmpty()) {
				//宿舍楼列表页面
				builds = dormBuildService.find();
				
			}else if(id != null&&!id.isEmpty()){
				//宿舍楼搜索页面
				DormBuild build = dormBuildService.findById(Integer.parseInt(id));
				builds.add(build);
			}
			//查询所有的宿舍楼，在select中遍历
			List<DormBuild> buildSelects = dormBuildService.find();
			request.setAttribute("buildSelects", buildSelects);
			request.setAttribute("id", id);
			request.setAttribute("builds", builds);
			request.setAttribute("mainRight", "/main/dormBuildList.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);;
		}else if(action != null&&action.equals("preAdd")) {
			//跳转到宿舍楼添加页面
			request.setAttribute("mainRight", "/main/dormBuildAddOrUpdate.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);;			
		}else if(action != null&&action.equals("save")) {
			//保存数据
			String name = request.getParameter("name");
			String remark = request.getParameter("remark");
			System.out.println("name="+name+"remark="+remark);
			//判断宿舍楼是否重复
			DormBuild dormBuild = dormBuildService.findByName(name);
			System.out.println("dormBuild"+dormBuild);
			if(id!=null &&!id.isEmpty()) {
				//更新
				if (dormBuild != null &&!dormBuild.getId().equals(Integer.parseInt(id))) {
					//宿舍楼名字存在,跳转到宿舍楼添加页面
					request.setAttribute("error", "宿舍楼被创建或已存在！请重新输入！");
					DormBuild build = dormBuildService.findById(Integer.parseInt(id));
					request.setAttribute("build", build);
					request.setAttribute("mainRight", "/main/dormBuildAddOrUpdate.jsp");
					request.getRequestDispatcher("/main/main.jsp").forward(request, response);
				}else {
					dormBuild = dormBuildService.findById(Integer.parseInt(id));	
					dormBuild.setName(name);
					dormBuild.setRemark(remark);
					
					dormBuildService.update(dormBuild);
					//更新完成，返回宿舍楼管理列表页，查询所有宿舍信息
					List<DormBuild> builds = dormBuildService.find();
					request.setAttribute("builds", builds);
					request.setAttribute("mainRight", "/main/dormBuildList.jsp");
					request.getRequestDispatcher("/main/main.jsp").forward(request, response);;			
				}
			}else {
				//保存											
				if (dormBuild != null) {
					//宿舍楼名字存在,跳转到宿舍楼添加页面
					request.setAttribute("error", "宿舍楼被创建或已存在！请重新输入！");
					request.setAttribute("mainRight", "/main/dormBuildAddOrUpdate.jsp");
					request.getRequestDispatcher("/main/main.jsp").forward(request, response);;			
				}else {
					//保存数据到数据库中
					DormBuild build = new DormBuild();
					build.setName(name);
					build.setRemark(remark);
					build.setDisabled(0);
					dormBuildService.save(build);
					//跳转到宿舍楼列表页面
					request.setAttribute("mainRight", "/main/dormBuildList.jsp");
					request.getRequestDispatcher("/main/main.jsp").forward(request, response);			
				}
			}
						
		}else if(action != null&&action.equals("preUpdate")) {
			//根据宿舍楼ID,查询宿舍信息
			DormBuild build = dormBuildService.findById(Integer.parseInt(id));
			request.setAttribute("build", build);
			//保存宿舍楼信息，到前端展示
			
			//跳转到宿舍楼修改页面
			request.setAttribute("mainRight", "/main/dormBuildAddOrUpdate.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);			
		
		}else if(action != null&&action.equals("deleteOrAcive")) {
			//删除或激活
			String disabled = request.getParameter("disabled");
			
			DormBuild dormBuild = dormBuildService.findById(Integer.parseInt(id));	
			System.out.println("---------------更新前-"+dormBuild);
			dormBuild.setDisabled(Integer.parseInt(disabled));	
			System.out.println("---------------更新后"+dormBuild);
			dormBuildService.update(dormBuild);
			//更新完成，返回宿舍楼管理列表页，查询所有宿舍信息
			List<DormBuild> builds = dormBuildService.find();
			request.setAttribute("builds", builds);
			request.setAttribute("mainRight", "/main/dormBuildList.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);;			
		
		}
		
		}

}
