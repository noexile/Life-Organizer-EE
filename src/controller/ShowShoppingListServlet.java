package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.events.ShoppingEntry;
import model.events.ShoppingList;
import model.user.UserManager;

@WebServlet("/ShowShoppingListServlet")
public class ShowShoppingListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserManager manager = (UserManager) session.getAttribute("loggedUserManager");
		if(session.getAttribute("loggedUserManager") == null || session.isNew()){
			response.sendRedirect("HomePage.jsp");
			return;
		}
		List<ShoppingList> listsToShow = manager.getShoppingLists();
		if(listsToShow.size() == 0){
			request.setAttribute("haveShoppingLists", false);
			request.getRequestDispatcher("MyShoppingList.jsp").forward(request, response);
			return;
		}else{
			request.setAttribute("haveShoppingLists", true);
			request.setAttribute("listsToShow", listsToShow);
			request.getRequestDispatcher("MyShoppingList.jsp").forward(request, response);
			return;
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserManager manager = (UserManager) session.getAttribute("loggedUserManager");
		if(session.getAttribute("loggedUserManager") == null){
			response.sendRedirect("HomePage.jsp");
		}
		int uniqueID = Integer.parseInt(request.getParameter("currList"));
		ShoppingList currentList = null;
		for(ShoppingList list : manager.getShoppingLists()){
			if(list.getUniqueIDForPayment() == uniqueID){
				currentList = list;
				break;
			}
		}
		
		request.setAttribute("ShoppingList", currentList);
		request.getRequestDispatcher("ViewShoppingList.jsp").forward(request, response);
		
		
	}

}
