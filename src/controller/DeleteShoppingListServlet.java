package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.events.ShoppingEntry;
import model.events.ShoppingList;
import model.user.UserManager;

@WebServlet("/DeleteShoppingListServlet")
public class DeleteShoppingListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserManager manager = (UserManager) session.getAttribute("loggedUserManager");
		if(session.getAttribute("loggedUserManager") == null || session.isNew()){
			response.sendRedirect("HomePage.jsp");
			return;
		}
		int uniqueID = Integer.parseInt(request.getParameter("idList"));
		
		ShoppingList currentList = null;
		
		ArrayList<ShoppingList> lists = manager.getShoppingLists();
		
		for(ShoppingList list : lists){
			if(list.getUniqueIDForPayment() == uniqueID){
				currentList = list;
				break;
			}
		}
		
		ArrayList<ShoppingEntry> entries = currentList.getShoppingEntries();
		
		try {
			manager.removeShoppingList(currentList);
		} catch (SQLException e) {
			System.out.println("problem with delete current list");
			e.printStackTrace();
		}
		
		for(int i=0; i<entries.size(); i++){
				try {
					manager.removeItemFromShoppingList(currentList, entries.get(i));
				} catch (SQLException e) {
					System.out.println("problem with delete entries of current list");
					e.printStackTrace();
				}	
		}
		
		if(manager.getShoppingLists().size() == 0){
			request.setAttribute("haveShoppingLists", false);
			request.getRequestDispatcher("MyShoppingList.jsp").forward(request, response);
			return;
		}
		request.setAttribute("haveShoppingLists", true);
		request.setAttribute("listsToShow", manager.getShoppingLists());
		request.getRequestDispatcher("MyShoppingList.jsp").forward(request, response);
	}

}
