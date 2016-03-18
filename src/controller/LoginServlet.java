package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dataBase.DBUserDAO;
import model.user.User;
import model.user.UserManager;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User loggedUser = checkIfUserExists(request);
		
		if (loggedUser == null || loggedUser.getUserName().trim().isEmpty()) {
			response.sendRedirect("ErrorHomePage.jsp");
		} else {
			request.getSession().setMaxInactiveInterval(10*60);
			request.getSession().setAttribute("todos", loggedUser.getTodos());
			request.getSession().setAttribute("loggedUserManager", new UserManager(loggedUser));
			response.sendRedirect("main.jsp");
		}
		
	}
	
	private User checkIfUserExists(HttpServletRequest request) {
		
		String user = request.getParameter("user"); // = "admin";
		String pass = request.getParameter("pass"); // = "admin";
		
		DBUserDAO dbdao = new DBUserDAO();
		ArrayList<User> allUsers = (ArrayList<User>) dbdao.getAllUsers();
		
		for(User u : allUsers) {
			if (u.getUserName().equals(user)) {
				if (u.getPassword().equals(pass)) {
					User loggedUser = new User(u.getUserName(),u.getPassword(),u.getUniqueDBId(),u.getEmail(), u.getMoney());
					System.out.println("found user");
					return loggedUser;
				}
			}
		}
		System.out.println("user not found");
		return null;
	}
	
}
