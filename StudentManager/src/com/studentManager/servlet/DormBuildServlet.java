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
		//��ֹ����
		request.setCharacterEncoding("utf-8");		
		String action =	request.getParameter("action");
		String id = request.getParameter("id");
		System.out.println("action="+action);
		
		DormBuildService dormBuildService = new DormBuildServiceImpl();
		if (action != null&&action.equals("list")) {
			
			List<DormBuild> builds = new ArrayList<DormBuild>();
			if (id == null||id.isEmpty()) {
				//����¥�б�ҳ��
				builds = dormBuildService.find();
				
			}else if(id != null&&!id.isEmpty()){
				//����¥����ҳ��
				DormBuild build = dormBuildService.findById(Integer.parseInt(id));
				builds.add(build);
			}
			//��ѯ���е�����¥����select�б���
			List<DormBuild> buildSelects = dormBuildService.find();
			request.setAttribute("buildSelects", buildSelects);
			request.setAttribute("id", id);
			request.setAttribute("builds", builds);
			request.setAttribute("mainRight", "/main/dormBuildList.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);;
		}else if(action != null&&action.equals("preAdd")) {
			//��ת������¥���ҳ��
			request.setAttribute("mainRight", "/main/dormBuildAddOrUpdate.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);;			
		}else if(action != null&&action.equals("save")) {
			//��������
			String name = request.getParameter("name");
			String remark = request.getParameter("remark");
			System.out.println("name="+name+"remark="+remark);
			//�ж�����¥�Ƿ��ظ�
			DormBuild dormBuild = dormBuildService.findByName(name);
			System.out.println("dormBuild"+dormBuild);
			if(id!=null &&!id.isEmpty()) {
				//����
				if (dormBuild != null &&!dormBuild.getId().equals(Integer.parseInt(id))) {
					//����¥���ִ���,��ת������¥���ҳ��
					request.setAttribute("error", "����¥���������Ѵ��ڣ����������룡");
					DormBuild build = dormBuildService.findById(Integer.parseInt(id));
					request.setAttribute("build", build);
					request.setAttribute("mainRight", "/main/dormBuildAddOrUpdate.jsp");
					request.getRequestDispatcher("/main/main.jsp").forward(request, response);
				}else {
					dormBuild = dormBuildService.findById(Integer.parseInt(id));	
					dormBuild.setName(name);
					dormBuild.setRemark(remark);
					
					dormBuildService.update(dormBuild);
					//������ɣ���������¥�����б�ҳ����ѯ����������Ϣ
					List<DormBuild> builds = dormBuildService.find();
					request.setAttribute("builds", builds);
					request.setAttribute("mainRight", "/main/dormBuildList.jsp");
					request.getRequestDispatcher("/main/main.jsp").forward(request, response);;			
				}
			}else {
				//����											
				if (dormBuild != null) {
					//����¥���ִ���,��ת������¥���ҳ��
					request.setAttribute("error", "����¥���������Ѵ��ڣ����������룡");
					request.setAttribute("mainRight", "/main/dormBuildAddOrUpdate.jsp");
					request.getRequestDispatcher("/main/main.jsp").forward(request, response);;			
				}else {
					//�������ݵ����ݿ���
					DormBuild build = new DormBuild();
					build.setName(name);
					build.setRemark(remark);
					build.setDisabled(0);
					dormBuildService.save(build);
					//��ת������¥�б�ҳ��
					request.setAttribute("mainRight", "/main/dormBuildList.jsp");
					request.getRequestDispatcher("/main/main.jsp").forward(request, response);			
				}
			}
						
		}else if(action != null&&action.equals("preUpdate")) {
			//��������¥ID,��ѯ������Ϣ
			DormBuild build = dormBuildService.findById(Integer.parseInt(id));
			request.setAttribute("build", build);
			//��������¥��Ϣ����ǰ��չʾ
			
			//��ת������¥�޸�ҳ��
			request.setAttribute("mainRight", "/main/dormBuildAddOrUpdate.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);			
		
		}else if(action != null&&action.equals("deleteOrAcive")) {
			//ɾ���򼤻�
			String disabled = request.getParameter("disabled");
			
			DormBuild dormBuild = dormBuildService.findById(Integer.parseInt(id));	
			System.out.println("---------------����ǰ-"+dormBuild);
			dormBuild.setDisabled(Integer.parseInt(disabled));	
			System.out.println("---------------���º�"+dormBuild);
			dormBuildService.update(dormBuild);
			//������ɣ���������¥�����б�ҳ����ѯ����������Ϣ
			List<DormBuild> builds = dormBuildService.find();
			request.setAttribute("builds", builds);
			request.setAttribute("mainRight", "/main/dormBuildList.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);;			
		
		}
		
		}

}
