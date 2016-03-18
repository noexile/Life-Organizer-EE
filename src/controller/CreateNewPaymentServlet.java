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

import model.events.DatedEvent;
import model.events.PaymentEvent;
import model.exceptions.IllegalAmountException;
import model.exceptions.IncorrectInputException;
import model.user.UserManager;

@WebServlet("/CreateNewPaymentServlet")
public class CreateNewPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher errorDispatcher = request.getRequestDispatcher("AddPayment.jsp");
		if(session.getAttribute("loggedUserManager") == null || session.isNew()){
			response.sendRedirect("HomePage.jsp");
			return;
		}
		String name = request.getParameter("paymentName");
		String statusPayment = request.getParameter("statuspayment");
		String date = request.getParameter("date");
		String description = request.getParameter("description");
		String statusPaid = request.getParameter("statuspaid");
		String amount = request.getParameter("amount");
		this.setSessionMessage(session, null);
		
		amount.replace(',', '.');
		
		
		request.setAttribute("nameForCreate",name);
		request.setAttribute("description",description);
		request.setAttribute("amount",amount);
		request.setAttribute("isPaid",statusPaid);
		request.setAttribute("isIncome",statusPayment);
		request.setAttribute("date",date);
		
		
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
		
		UserManager manager = (UserManager) session.getAttribute("loggedUserManager");
		try {
			try {
				manager.createPaymentEvent(name, description, Double.parseDouble(amount), this.isIncome(statusPayment), this.isPaid(statusPaid), LocalDate.parse(date));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAmountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IncorrectInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(DatedEvent e : manager.getEvents()){
			System.out.println(((PaymentEvent)e).getUniqueIDForPayment());
		}
		this.setSessionMessage(session, " ");
		response.sendRedirect("main.jsp");
		
		
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
