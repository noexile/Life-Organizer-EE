package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.events.TODOEvent;
import model.user.UserManager;

@WebServlet("/SaveToDoServlet")
public class SaveToDoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("ToDo.jsp");
		
		int id = Integer.valueOf((String) request.getSession().getAttribute("id"));
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String type = request.getParameter("type");

		UserManager manager = (UserManager) request.getSession().getAttribute("loggedUserManager");

		// update event
		manager.modifyTODO(title, description, type, id);
		
		rd.forward(request, response);
	}

}
