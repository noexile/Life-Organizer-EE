package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.events.TODOEvent;
import model.user.UserManager;

@WebServlet("/AddToDoServlet")
public class AddToDoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("AddToDo.jsp");
		HttpSession session = request.getSession();
		
		String name = request.getParameter("name");
		String term = request.getParameter("term");
		String desc = request.getParameter("desc");
		
		if (name == null || name.trim().isEmpty()) {
			session.setAttribute("ToDoErrorMessage", "The name field cannot be left empty!");
			dispatcher.forward(request, response);
			return;
		} else if (term == null || term.trim().isEmpty()) {
			session.setAttribute("ToDoErrorMessage", "Shit just got real ?!");
			dispatcher.forward(request, response);
			return;
		} else if (desc == null || desc.trim().isEmpty()) {
			session.setAttribute("ToDoErrorMessage", "The description field cannot be left empty!");
			dispatcher.forward(request, response);
			return;
		}
		
		// add new todo
		dispatcher = request.getRequestDispatcher("ToDo.jsp");
		UserManager manager = (UserManager) session.getAttribute("loggedUserManager");	
		TODOEvent todo = new TODOEvent(name, desc, term);
		
		manager.addTODO(todo);
		dispatcher.forward(request, response);
	}

}
