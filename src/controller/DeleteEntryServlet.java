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

@WebServlet("/DeleteEntryServlet")
public class DeleteEntryServlet extends HttpServlet {
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
		for(ShoppingList list : manager.getShoppingLists()){
			if(list.getUniqueIDForPayment() == uniqueID){
				currentList = list;
				break;
			}
		}
		int idForDelete = Integer.parseInt(request.getParameter("currEntry"));
		ShoppingEntry entryForDelete = null;
		for(ShoppingEntry entry : currentList.getShoppingEntries()){
			if(entry.getUniqueID() == idForDelete){
				entryForDelete = entry;
				break;
			}
		}
		
		try {
			manager.removeItemFromShoppingList(currentList, entryForDelete);
			request.setAttribute("ShoppingList", currentList);
			request.getRequestDispatcher("ViewShoppingList.jsp").forward(request, response);
		} catch (SQLException e) {
			System.out.println("Problem with add Entry in SHoppingList !");
			e.printStackTrace();
		}
		
	}

}
