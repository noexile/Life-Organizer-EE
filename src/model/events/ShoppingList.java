package model.events;

import model.exceptions.IllegalAmountException;

import java.time.LocalDate;
import java.util.ArrayList;

public class ShoppingList extends PaymentEvent{

	private ArrayList<ShoppingEntry> shoppingEntries;

	public ShoppingList(String name) throws IllegalAmountException {
		super(name,"non",0,false,false,LocalDate.now());
		this.shoppingEntries = new ArrayList<ShoppingEntry>();
	}
	
	public ShoppingList(String name,boolean isPaid, int uniqueID,LocalDate date,ArrayList<ShoppingEntry> shoppingEntries) throws IllegalAmountException{
		super(name,"non",0,false,isPaid,date,uniqueID);
		double amount = 0;
		this.shoppingEntries = new ArrayList<ShoppingEntry>();
		StringBuilder description = new StringBuilder();
		for(ShoppingEntry entry : shoppingEntries){
			amount += entry.getAmount();
			description.append(entry.getName()+" : "+new Double(entry.getAmount()).toString()+"\n");
			this.shoppingEntries.add(entry);
		}
		super.setAmount(amount);
		if(description != null){
			super.setDescription(description.toString());
		}
	}
	
	// methods
	public void addEntry(ShoppingEntry entry){
		this.shoppingEntries.add(entry);
		try {
			this.setAmount(this.getAmountOfAllEntries());
			String newDesc = this.getDescription().concat(entry.getName()+" : "+entry.getAmount()+"\n");
			this.setDescription(newDesc);
		} catch (IllegalAmountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void removeEntry(ShoppingEntry entry) {
		this.shoppingEntries.remove(entry);
	}

	public double getAmountOfAllEntries() {
		double currentAmountOfList = 0;
		
		for(ShoppingEntry entry : this.shoppingEntries){
			currentAmountOfList += entry.getAmount();
		}
		
		return currentAmountOfList;
	}

	//for remove
	public void setAmountOfShoppingList(double amount) throws IllegalAmountException {
		if (amount > 0) {
			this.setAmount(amount);
		} else {
			throw new IllegalAmountException("The amount must be positive !");
		}
	}
	
	// getters and setters
	public boolean getIsPaid() {
		return super.getIsPaid();
	}
	
	public String getName(){
		return super.getTitle();
	}
	
	public ArrayList<ShoppingEntry> getShoppingEntries(){
		return this.shoppingEntries;
	}

}
