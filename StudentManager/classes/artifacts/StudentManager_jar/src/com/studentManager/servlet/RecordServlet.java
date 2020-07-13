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
			//=======================��ת���б�ҳ����ѯȱ��ҳ��==================
			
			Integer roleId =userCurr.getRoleId();			
			List<DormBuild> builds = new ArrayList<DormBuild>();			
			if(roleId.equals(0)) {
			//�û�Ϊ��������Ա���ܽ�ѧ����ӵ���������¥
				builds = dormBuildService.findAll();
			}else if(roleId.equals(1)) {
			//�û�Ϊ�������Ա��ֻ�����ѧ���������������¥
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
			//=======================��ת�����ҳ��========================
			request.setAttribute("mainRight","recordAddOrUpdate.jsp");
			request.getRequestDispatcher("/main/main.jsp").forward(request, response);		
	
		}else if (action != null && action.equals("ajaxStuCode")) {
			//�жϵ�ǰ�û��Ƿ�����Ӹ�ѧ��ѧ��ȱ�ڼ�¼��Ȩ��
			String stuCode = request.getParameter("stuCode");
			User user = userService.findByStuCodeAndManager(stuCode,userCurr);
			System.out.println("�Ƿ������Ȩ��user:"+user);
			if (user == null) {
				response.setCharacterEncoding("utf-8");
				PrintWriter writer = response.getWriter();
				writer.print("��û����Ӹ�ѧ��ѧ��ȱ�ڵ�Ȩ�ޣ��޷��޸ģ����������룡");
				writer.flush();
				writer.close();
			}
		}else if (action != null && action.equals("save")) {
			//=============================����ȱ�ڼ�¼======================
			String stuCode = request.getParameter("stuCode");
			String date = request.getParameter("date");
			String remark = request.getParameter("remark");
			System.out.println(" stuCode:"+ stuCode+"date:"+date+"remark:"+remark);
			//==============�鿴�û��Ƿ�����Ӹ�ѧ��ȱ�ڼ�¼��Ȩ��================
			User user = userService.findByStuCodeAndManager(stuCode,userCurr);
			System.out.println("��ѯ���ȱ��Ȩ��user:"+user);
			
			if (id != null && !id.isEmpty()) {
				//=============================����=======================
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
					//����
					response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
				}
				/*
				 * request.setAttribute("error", "��û����Ӹ�ѧ��ѧ��ȱ�ڵ�Ȩ�ޣ�");
				 * request.setAttribute("mainRight","recordAddOrUpdate.jsp");
				 * request.getRequestDispatcher("/main/main.jsp").forward(request, response);
				 */	}else {
				//����
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
					request.setAttribute("error", "��û����Ӹ�ѧ��ѧ��ȱ�ڵ�Ȩ�ޣ�");
					request.setAttribute("mainRight","recordAddOrUpdate.jsp");
					request.getRequestDispatcher("/main/main.jsp").forward(request, response);							
				}
			}
			
		}else if (action != null && action.equals("preUpdate")) {
			//================================��ת���޸�ҳ��===========================
			Record record = recordService.findById(Integer.parseInt(id));
			System.out.println("record:"+record);
			User user = userService.findByStuCodeAndManager(record.getUser().getStuCode(),userCurr);
			System.out.println("�޸�Ȩ��user��"+user);
			if (user == null) {
				response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");		
			}else {
				request.setAttribute("record",record);
				request.setAttribute("mainRight","recordAddOrUpdate.jsp");
				request.getRequestDispatcher("/main/main.jsp").forward(request, response);							
			}		
		}else if (action != null && action.equals("deleteOrAcive")) {
			//===========ɾ���򼤻�=========================
			String disabled = request.getParameter("disabled");
			Record record = recordService.findById(Integer.parseInt(id));
			User user = userService.findByStuCodeAndManager(record.getUser().getStuCode(),userCurr);
			System.out.println("�޸�Ȩ��user��"+user);
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
