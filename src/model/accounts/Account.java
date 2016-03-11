package model.accounts;

import model.events.PaymentEvent;
import model.interfaces.IAccount;

import java.util.ArrayList;
import java.util.Collections;



public abstract class Account implements IAccount {
	
	private String accountName;
	protected double amount;
	private ArrayList<PaymentEvent> paymentHistory;
    private int dbUid;

	public Account(String accountName, double amount, int dbUid) {
        this.paymentHistory = new ArrayList<>();
		this.setAccountName(accountName);
		this.amount = amount;
        this.dbUid = dbUid;
	}

    public ArrayList<PaymentEvent> getPaymentHistory() {
        return (ArrayList<PaymentEvent>) Collections.unmodifiableList(this.paymentHistory);
    }

    public void addPaymentEvent(PaymentEvent payment) {
        this.paymentHistory.add(payment);
    }

    public double getAmount() {
		return amount;
	}
	
	public void setAccountName(String accountName) {
        if (accountName != null) {
            this.accountName = accountName;
        }
	}

    public int getDbUid() {
        return dbUid;
    }

    public String getAccountName() {
		return this.accountName;
	}

    public abstract void withdrawMoney(double money);

    public abstract void insertMoney(double money);
}