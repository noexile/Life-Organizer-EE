package model.accounts;

import model.events.PaymentEvent;
import model.interfaces.IAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Account implements IAccount {
	
	private String accountName;
	protected BigDecimal amount;
	private ArrayList<PaymentEvent> paymentHistory;

	public Account(String accountName, BigDecimal amount) {
        this.paymentHistory = new ArrayList<>();
		this.setAccountName(accountName);
		this.amount = amount;
	}

    public ArrayList<PaymentEvent> getPaymentHistory() {
        return (ArrayList<PaymentEvent>) Collections.unmodifiableList(this.paymentHistory);
    }

    public void addPaymentEvent(PaymentEvent payment) {
        this.paymentHistory.add(payment);
    }

    public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAccountName(String accountName) {
        if (accountName != null) {
            this.accountName = accountName;
        }
	}

    public String getAccountName() {
		return this.accountName;
	}

    public abstract void withdrawMoney(BigDecimal money);

    public abstract void insertMoney(BigDecimal money);

}