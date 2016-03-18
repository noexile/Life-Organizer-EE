package controller;

import java.io.IOException;
import java.sql.SQLException;

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
		int uniqueID = Integer.parseInt(request.getParameter("idList"));
		
		ShoppingList currentList = null;
		for(ShoppingList list : manager.getShoppingLists()){
			if(list.getUniqueIDForPayment() == uniqueID){
				currentList = list;
				break;
			}
		}
		
		
		
		for(ShoppingEntry entry : currentList.getShoppingEntries()){
				try {
					manager.removeItemFromShoppingList(currentList, entry);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}
		
		try {
			manager.removeShoppingList(currentList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("MyShoppingList.jsp").forward(request, response);
	}

}
