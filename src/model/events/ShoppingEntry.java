package model.events;

public class ShoppingEntry {
	
	private int uniqueID;
	private String name;
	private double amount;
	
	public ShoppingEntry(String name,double amount) {
		this.name = name;
		this.amount = amount;
	}
	
	public ShoppingEntry(String name,double amount,int uniqueID) {
		this.name = name;
		this.amount = amount;
		this.uniqueID = uniqueID;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	public String getName() {
		return name;
	}

	public double getAmount() {
		return amount;
	}
	
	
	
	
	
}
