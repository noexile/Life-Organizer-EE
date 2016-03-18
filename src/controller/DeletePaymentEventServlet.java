package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import model.events.PaymentEvent;
import model.user.UserManager;

@WebServlet("/DeletePaymentEventServlet")
public class DeletePaymentEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserManager manager = ((UserManager)request.getSession().getAttribute("loggedUserManager"));
		if(manager == null || request.getSession().isNew()){
			response.sendRedirect("HomePage.jsp");
			return;
		}
		int id = Integer.parseInt(request.getParameter("currPayment"));
		PaymentEvent eventForDelete = null;
		
		for(PaymentEvent event : manager.getEvents()){
			if(event.getUniqueIDForPayment() == id){
				eventForDelete = event;
				break;
			}
		}
		
		try {
			manager.removePaymentEvent(eventForDelete);
		} catch (SQLException e) {
			System.out.println("Problem with delete in method remove payment event in data base");
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("main.jsp").forward(request, response);
		
	}

}
