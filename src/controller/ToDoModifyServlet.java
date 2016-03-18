package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ToDoModifyServlet")
public class ToDoModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getSession().getAttribute("loggedUserManager") == null || request.getSession().isNew()){
			response.sendRedirect("HomePage.jsp");
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("EditToDo.jsp");
		
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String type = request.getParameter("type");
		String id = request.getParameter("id");

		if (title == null || description == null || type == null) {
			// error -> rd = request.getRequestDispatcher("SOME ERROR.jsp");
			rd.forward(request, response);
		}		
		
		request.getSession().setAttribute("title", title);
		request.getSession().setAttribute("description", description);
		request.getSession().setAttribute("type", type);
		request.getSession().setAttribute("id", id);
		rd.forward(request, response);
	}

}
