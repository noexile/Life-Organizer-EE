package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.events.PaymentEvent;
import model.user.UserManager;


@WebServlet("/PayPaymentEventServlet")
public class PayPaymentEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserManager manager = (UserManager) request.getSession().getAttribute("loggedUserManager");
		if(manager == null || request.getSession().isNew()){
			response.sendRedirect("HomePage.jsp");
			return;
		}
		int id = Integer.parseInt(request.getParameter("currPayment"));
		PaymentEvent eventForPay = null;
		
		for(PaymentEvent event : manager.getEvents()){
			if(event.getUniqueIDForPayment() == id){
				eventForPay = event;
				break;
			}
		}
		
		manager.pay(eventForPay);
		System.out.println(manager.getMoney());
		request.getRequestDispatcher("main.jsp").forward(request, response);
	}

}
