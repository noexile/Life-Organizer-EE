package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.events.ShoppingList;
import model.exceptions.IllegalAmountException;
import model.user.UserManager;


@WebServlet("/PayShoppingListServlet")
public class PayShoppingListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		
		request.setAttribute("ShoppingList",currentList);
		request.getRequestDispatcher("PayShoppingList.jsp").forward(request, response);
		return;
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserManager manager = (UserManager) session.getAttribute("loggedUserManager");
		if(session.getAttribute("loggedUserManager") == null){
			response.sendRedirect("HomePage.jsp");
		}
		int uniqueID = Integer.parseInt(request.getParameter("listId"));
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String date = request.getParameter("date");
		String testDate = date.replace("-","");
		if(Integer.parseInt(testDate) == 0){
			//request.setAttribute("ShoppingList");
			request.getRequestDispatcher("PayShoppingList.jsp").forward(request, response);
			return;
		}
		LocalDate dateToCreate = LocalDate.parse(date);
		
		double amount = Double.parseDouble(request.getParameter("amount"));
		if(amount < 0){
			amount*=-1;
		}
		for(ShoppingList list : manager.getShoppingLists()){
			if(list.getUniqueIDForPayment() == uniqueID){
				try {
					manager.payShoppingList(list, amount);
					manager.createPaymentEvent("ShopList: "+name, description, amount, false, true, dateToCreate);
					response.sendRedirect("main.jsp");
					return;
				} catch (SQLException | IllegalAmountException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		
	}

}
