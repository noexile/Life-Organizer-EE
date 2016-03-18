package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import model.events.DatedEvent;
import model.events.PaymentEvent;
import model.user.UserManager;

/**
 * Servlet implementation class ShowInsidePaymentEventServlet
 */
@WebServlet("/ShowInsidePaymentEventServlet")
public class ShowInsidePaymentEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String date = request.getParameter("dateForShow");
		String status = request.getParameter("status");
		UserManager manager = (UserManager) request.getSession().getAttribute("loggedUserManager");
		if(manager == null || request.getSession().isNew()){
			response.sendRedirect("HomePage.jsp");
			return;
		}
		try{
			LocalDate paramDate = LocalDate.parse(date);
			ArrayList<PaymentEvent> events = new ArrayList<>();
			if(this.hasStatus(status)){
				request.setAttribute("dateForShow", date);
				boolean isPaid = this.getStatus(status);
					events = manager.getPaymentEventsByConcretDate(paramDate);
					if(events.size() == 0){
						request.setAttribute("haveEventForThisDate", false);
						request.getRequestDispatcher("MyPayments.jsp").forward(request, response);
						return;
					}else{
						request.setAttribute("haveEventForThisDate", true);
						events.clear();
						for(PaymentEvent e : manager.getPaymentEventsByConcretDate(paramDate)){
							if(e.getIsIncome() == isPaid){
								events.add(e);
							}
						}
					}
					if(events.size() == 0){
						request.setAttribute("haveEventForThisDate", false);
					}
					System.out.println(events.size());
					request.setAttribute("eventsToShow", events);	
					request.getRequestDispatcher("MyPayments.jsp").forward(request, response);
			}else{
				events = manager.getPaymentEventsByConcretDate(paramDate);
				if(events.size() == 0){
					request.setAttribute("haveEventForThisDate", false);
					request.getRequestDispatcher("MyPayments.jsp").forward(request, response);
					return;
				}else{
					request.setAttribute("haveEventForThisDate", true);
				}
				System.out.println(events.size());
				request.setAttribute("dateForShow", date);
				request.setAttribute("eventsToShow", events);	
				request.getRequestDispatcher("MyPayments.jsp").forward(request, response);
			}
		}catch(DateTimeParseException e){
			List<PaymentEvent> events = new ArrayList<>();
			if(!this.hasStatus(status)){
				events =  manager.getEvents();
				if(events.size() == 0){
					request.setAttribute("haveEventForThisDate", false);
					request.getRequestDispatcher("MyPayments.jsp").forward(request, response);
					return;
				}else{
					request.setAttribute("haveEventForThisDate", true);
				}
				System.out.println(events.size());
				request.setAttribute("dateForShow", " All Payment events:");
				request.setAttribute("eventsToShow", events);	
				request.getRequestDispatcher("MyPayments.jsp").forward(request, response);
			}else{
				boolean stat = this.getStatus(status);
				for(PaymentEvent even : manager.getEvents()){
					if(even.getIsPaid() == stat){
						events.add(even);
						System.out.println(even.getUniqueIDForPayment());
					}
				}
				if(events.size() == 0){
					request.setAttribute("haveEventForThisDate", false);
					request.getRequestDispatcher("MyPayments.jsp").forward(request, response);
					return;
				}else{
					request.setAttribute("haveEventForThisDate", true);
					System.out.println(events.size());
					request.setAttribute("dateForShow", " All Payment events:");
					request.setAttribute("eventsToShow", events);	
					request.getRequestDispatcher("MyPayments.jsp").forward(request, response);
					return;
				}		
			}
		}
	}
	
	private boolean hasStatus(String status){
		switch(status){
			case "all":
				return false;
		default:
				return true;
		}
	}
	
	private boolean getStatus(String status){
		switch(status){
			case "paid":
				return true;
			default:
				return false;
		}
	}

}
