package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.exceptions.IllegalAmountException;
import model.user.UserManager;

@WebServlet("/CreateShoppingListServlet")
public class CreateShoppingListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserManager manager = (UserManager) session.getAttribute("loggedUserManager");
		String nameOfList = request.getParameter("nameList");
		if(session.getAttribute("loggedUserManager") == null || session.isNew()){
			response.sendRedirect("HomePage.jsp");
			return;
		}
		try {
			manager.createShoppingList(nameOfList);
			request.setAttribute("haveShoppingLists", true);
			request.setAttribute("listsToShow", manager.getShoppingLists());
			request.getRequestDispatcher("MyShoppingList.jsp").forward(request, response);
			return;
		} catch (IllegalAmountException e) {
			System.out.println("Invalid Input");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Problem in DB with create of list");
			e.printStackTrace();
		}
		
	}

}
