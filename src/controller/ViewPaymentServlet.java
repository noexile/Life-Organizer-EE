package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.events.DatedEvent;
import model.events.PaymentEvent;
import model.user.UserManager;


@WebServlet("/ViewPaymentServlet")
public class ViewPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getSession().getAttribute("loggedUserManager") == null || request.getSession().isNew()){
			response.sendRedirect("HomePage.jsp");
			return;
		}
		int id = Integer.parseInt(request.getParameter("currPayment"));
		PaymentEvent e = null;
		for(PaymentEvent event : ((UserManager)request.getSession().getAttribute("loggedUserManager")).getEvents()){
			if(event.getUniqueIDForPayment() == id){
				e = event;
				break;
			}
		}
		request.setAttribute("Payment",e);
		request.getRequestDispatcher("ViewPayment.jsp").forward(request, response);
		return;
	}
	
}
