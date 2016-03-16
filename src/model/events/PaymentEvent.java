package model.events;

import model.exceptions.IllegalAmountException;

import java.time.LocalDate;

public class PaymentEvent extends DatedEvent {

	private int uniqueIDPayment;
	private double amount;
	private boolean isIncome;
	private boolean isPaid;
	private boolean isOverdue;
	
	public PaymentEvent(String eventTitle,String description, double amount, boolean isIncome, boolean isPaid, LocalDate dateTime) throws IllegalAmountException {
        super(eventTitle, description, dateTime);
        this.isIncome = isIncome;
        this.isOverdue = checkIfOverdue();
        this.isPaid = isPaid;
        
        this.setAmount(amount);
    }
	
	public PaymentEvent(String eventTitle,String description, double amount, boolean isIncome, boolean isPaid, LocalDate dateTime,int uniqueIDPayment) throws IllegalAmountException {
        super(eventTitle, description, dateTime);
        this.isIncome = isIncome;
        this.isOverdue = checkIfOverdue();
        this.isPaid = isPaid;
        this.uniqueIDPayment = uniqueIDPayment;
        this.setAmount(amount);
    }

	// methods
    private boolean checkIfOverdue() {
		if (!this.isPaid && super.getDateTime().isBefore(LocalDate.now())) {
			return true;
		}
		return false;
	}

    public void setAmount(Double amount) throws IllegalAmountException {
        if (amount != null && amount >= 0) {
        	this.amount = amount;
        } else {
        	throw new IllegalAmountException("The entered amount for your payment must be positive!");	
        }
    }
    
    // getters and setters
	public double getAmount() {
        return this.amount;
    }
    
    public boolean getIsIncome(){
    	return this.isIncome;
    }

    public boolean getIsOverdue(){
    	return this.isOverdue;
    }
    
    public boolean getIsPaid(){
    	return this.isPaid;
    }

	public void setIncome(boolean isIncome) {
		this.isIncome = isIncome;
	}


	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}


	public void setOverdue(boolean isOverdue) {
		this.isOverdue = isOverdue;
	}
    
	public void setDateTime(LocalDate dateTime){
		super.setDate(dateTime);
	}
    
	public int getUniqueIDForPayment(){
		return this.uniqueIDPayment;
	}
	
	public String getTitle(){
		return super.getTitle();
	}
	
	public String getDescription(){
		return super.getDescription();
	}
}
