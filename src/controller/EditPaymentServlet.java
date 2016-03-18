package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.events.PaymentEvent;
import model.exceptions.IllegalAmountException;
import model.exceptions.IncorrectInputException;
import model.user.UserManager;

@WebServlet("/EditPaymentServlet")
public class EditPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("currPayment"));
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
		request.getRequestDispatcher("EditPayment.jsp").forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserManager manager = (UserManager) request.getSession().getAttribute("loggedUserManager");
		if(session.getAttribute("loggedUserManager") == null){
			response.sendRedirect("HomePage.jsp");
		}
		RequestDispatcher errorDispatcher = request.getRequestDispatcher("EditPayment.jsp");
		String name = request.getParameter("paymentName");
		String statusPayment = request.getParameter("statuspayment");
		String date = request.getParameter("date");
		String description = request.getParameter("description");
		String statusPaid = request.getParameter("statuspaid");
		String amount = request.getParameter("amount");
		this.setSessionMessage(session, null);
		amount.replace(',', '.');
		//find pe_id for update.
		
		int id = Integer.parseInt(request.getParameter("currPayment"));
		PaymentEvent eventForUpdate = null;
		for(PaymentEvent event : manager.getEvents()){
			if(event.getUniqueIDForPayment() == id){
				eventForUpdate = event;
				break;
			}
		}
		
		request.setAttribute("Payment",eventForUpdate);
		
		if(!(this.validateUsername(name, request))){
			this.setSessionMessage(session,"Invalid name of event !");
			errorDispatcher.forward(request, response);
			return;
		}
		
		if(!(this.validateUsername(description, request))){
			this.setSessionMessage(session,"Invalid description of event, must be more than 3 letters !");
			errorDispatcher.forward(request, response);
			return;
		}
		
		if(!(this.validateDate(date, request))){
			this.setSessionMessage(session,"Invalid date of event, please select date !");
			errorDispatcher.forward(request, response);
			return;
		}
		
		if(!(this.radioValidator(statusPayment, statusPaid))){
			this.setSessionMessage(session,"Invalid check of event, please check all !");
			errorDispatcher.forward(request, response);
			return;
		}
		
		//Everything is ok
		
		//Invoke method
		try {
			manager.modifyPaymentEvent(eventForUpdate, name, description, Double.parseDouble(amount), this.isIncome(statusPayment), this.isPaid(statusPaid), LocalDate.parse(date));
			request.getRequestDispatcher("ViewPayment.jsp").forward(request, response);
			this.setSessionMessage(session, " ");
			return;
		} catch (NumberFormatException | IllegalAmountException | IncorrectInputException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			System.out.println("Problem with update in DB in SQL query");
			e1.printStackTrace();
		}
		
	}
	
	

	private boolean isIncome(String statusPayment) throws IncorrectInputException{
		switch (statusPayment){
		case "expense":
			return false;
		case "earning":
			return true;
		}
		throw new IncorrectInputException("Incorect check for status of payment: must expense or earning !");
	}
	
	private boolean isPaid(String statusPaid) throws IncorrectInputException{
		switch(statusPaid){
		case "ispaid":
			return true;
		case "topay":
			return false;
		}
		throw new IncorrectInputException("Incorect check for status of payment: must is paid or to pay !");
	}

	private void setSessionMessage(HttpSession session,String message){
		session.setAttribute("ErrorMessage", message);
	}
	
	// name validator
		private boolean validateUsername(String name,HttpServletRequest request){
			if (name == null || name.trim().isEmpty()){
				return false;
			} else if (name.length() < 3){
				return false;
			}
			return true;
		}
	// date validator 
		
		private boolean validateDate(String date,HttpServletRequest request){
			if (date == null || date.trim().isEmpty())
				return false;
			return true;
			
		}
		
	// radio validator
		private boolean radioValidator(String statuspayment,String statuspaid){
			if(statuspaid == null || statuspayment == null){
				return false;
			}
			return true;
		}
	
}
