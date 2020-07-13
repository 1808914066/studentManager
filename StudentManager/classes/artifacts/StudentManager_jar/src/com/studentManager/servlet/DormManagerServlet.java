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
		//================�޹�ID=================
		String id =	request.getParameter("id");
		
		UserService userService = new UserServiceImpl();
		DormBuildService dormBuildService = new DormBuildServiceImpl();
		//===========================��ѯ��������¥������================================
		List<DormBuild> builds = dormBuildService.find();
		request.setAttribute("builds", builds);
		
		if (action != null&&action.equals("list")) {
		//===========================�������Ա��ѯ======================================
			String searchType= request.getParameter("searchType");
			String keyword = request.getParameter("keyword");
			System.out.println("searchType:"+searchType+"keyword:"+keyword);
			//��ѯ�������Ա
			List<User> users = userService.findManager(searchType,keyword);
			
			request.setAttribute("searchType", searchType);
			request.setAttribute("keyword",  keyword);
			request.setAttribute("users", users);
			request.setAttribute("mainRight","dormManagerList.jsp" );
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);	
		}else if (action != null&&action.equals("preAdd")) {
		//==========================��ת���������Ա���ҳ��================================
			
			request.setAttribute("mainRight","dormManagerAddOrUpdate.jsp" );
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);				
		}else if (action != null&&action.equals("save")) {
			System.out.println("=========�����������Ա===========");
			//�����������Ա
			String name = request.getParameter("name");
			String passWord = request.getParameter("passWord");
			String sex = request.getParameter("sex");
			String tel = request.getParameter("tel");
			//========================��ȡǰ�˴����������ݴ�������==============================
			String[] dormBuildIds = request.getParameterValues("dormBuildId");
			System.out.println("name:"+name+"password:"+passWord+"sex:"+sex+"tel"+tel);
			
			if(id==null||id.isEmpty()) {
				//===============================����==============================
				User user =new User(name,passWord,sex,tel,null,1);
				user.setDisabled(0);
				//��ǰ��¼�û�
				User user2 = (User) request.getSession().getAttribute("session_user");
				user.setCreatUserId(user2.getId());				
				userService.saveManager(user,dormBuildIds);				
			}else {
				//�޸�
				//ͨ���޹�ID��ȡ�������Ա
				User user =userService.findById(Integer.parseInt(id));
				user.setName(name);
				user.setPassWord(passWord);
				user.setSex(sex);
				user.setTel(tel);
				userService.updateManager(user);
				//�޸Ļ����޸��������Ա������¥���м��
				
				//ɾ����ǰ�������Ա�������������¥
				dormBuildService.deleteByUserId(user.getId());
				//������ǰ�������Ա�������������¥
				dormBuildService.saveManagerAndBuild(user.getId(),dormBuildIds);
			}
			//��ת���������Ա�б�ҳ�棬��ѯ�����������Ա
			response.sendRedirect(getServletContext().getContextPath()+"/dormManager.action?action=list");
	
			}else if (action != null&&action.equals("preUpdate")) {
			//============================�޸�ҳ��==============================
			//�����޹�id��תҳ��
		
			User user =userService.findById(Integer.parseInt(id));
			//�����޹�ID��ù���¥��
			List<DormBuild> userBuilds = dormBuildService.findByUserId(user.getId());
			user.setDormBuilds(userBuilds);
			System.out.println("user================="+user);
			//������ǰ�������Ա������������ᣬ����ȡ
			List<Integer> userBuildids = new ArrayList<>();
			for(DormBuild userBuild:userBuilds) {
				userBuildids.add(userBuild.getId());
			}
			request.setAttribute("userBuildids", userBuildids);
			request.setAttribute("user", user);
			request.setAttribute("mainRight","dormManagerAddOrUpdate.jsp" );
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);;				
	
		}else if (action != null&&action.equals("deleteOrAcive")) {
			//ɾ���򼤻�
			String disabled = request.getParameter("disabled");
			//�޸�
			//ͨ���޹�ID��ȡ�������Ա
			User user =userService.findById(Integer.parseInt(id));
			user.setDisabled(Integer.parseInt(disabled));
			userService.updateManager(user);
			response.sendRedirect(getServletContext().getContextPath()+"/dormManager.action?action=list");
			
		}
		}

}
