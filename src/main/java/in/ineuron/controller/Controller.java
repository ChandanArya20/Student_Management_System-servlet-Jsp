package in.ineuron.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.ineuron.dto.Student;
import in.ineuron.service.IStudentService;
import in.ineuron.serviceFactory.StudentServiceFactory;

@WebServlet("/controller/*")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		IStudentService studService=StudentServiceFactory.getStudentService();

		System.out.println("Request URI         : "+request.getRequestURI());
		System.out.println("Request pathInfo    : "+request.getPathInfo());
		System.out.println("Request servletPath : "+request.getServletPath());

		RequestDispatcher reqDisp=null;

		if(request.getPathInfo().endsWith("addform")) {

			String name=request.getParameter("sname");
			String age=request.getParameter("sage");
			String address=request.getParameter("saddress");

			Student student=new Student();
			student.setSname(name);
			student.setSage(Integer.parseInt(age));
			student.setSaddress(address);

			String status=studService.addStudent(student);
			request.setAttribute("status", status);

			reqDisp=request.getRequestDispatcher("/insertResult.jsp");
			reqDisp.forward(request, response);

		}

		if(request.getPathInfo().endsWith("searchform")) {

			String id=request.getParameter("sid");
			Student student=studService.searchStudent(Integer.parseInt(id));
			System.out.println(student);
			request.setAttribute("student", student);
			reqDisp=request.getRequestDispatcher("/display.jsp");
			reqDisp.forward(request, response);

		}

		if(request.getPathInfo().endsWith("deleteform")) {

			String id=request.getParameter("sid");
			String status=studService.deleteStudent(Integer.parseInt(id));

			request.setAttribute("status", status);
			reqDisp=request.getRequestDispatcher("/deleteResult.jsp");
			reqDisp.forward(request, response);

		}

		if(request.getPathInfo().endsWith("editform")) {

			String id=request.getParameter("sid");
			Student student=studService.searchStudent(Integer.parseInt(id));
			
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			
			request.setAttribute("student", student);
			reqDisp=request.getRequestDispatcher("/editForm.jsp");
			reqDisp.forward(request, response);

		}
		if(request.getPathInfo().endsWith("updateform")) {

			String id=(String) request.getSession().getAttribute("id");
			String name=request.getParameter("sname");
			String age=request.getParameter("sage");
			String address=request.getParameter("saddress");

			Student student=new Student();
			student.setSid(Integer.parseInt(id));
			student.setSname(name);
			student.setSage(Integer.parseInt(age));
			student.setSaddress(address);

			String status=studService.updateStudent(student);
			
			request.setAttribute("status", status);
			reqDisp=request.getRequestDispatcher("/updateResult.jsp");
			reqDisp.forward(request, response);

		}


	}

}
