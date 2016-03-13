package model.events;

import model.exceptions.IllegalAmountException;

import java.util.HashMap;
import java.util.Map;

public class ShoppingList {

	private String name;
	private boolean isPaid;
	private double amount;
	private HashMap<String, Double> shoppingEntries;

	public ShoppingList(String name) {
		this.setName(name);
		this.amount = 0;
		this.isPaid = false;
		this.shoppingEntries = new HashMap<String, Double>();
	}

	// methods
	public void addEntry(String name, Double amount) {
		if (setEntryAmount(amount)) {
			this.shoppingEntries.put(name, amount);
		}
	}
	
	private boolean setEntryAmount(double amount) {
		try {
			if (amount < 0) {
				throw new IllegalAmountException("Invalid value of entry !");
			}
		} catch (IllegalAmountException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}

	public void removeEntry(String name) {
		if (this.shoppingEntries.containsKey(name)) {
			this.shoppingEntries.remove(name);
		}
	}

	public double getAmountOfAllEntries() {
		double currentAmountOfList = 0;
		
		for (Map.Entry<String, Double> map : this.shoppingEntries.entrySet()) {
			if (map.getKey() == null) {
				continue;
			}
			
			currentAmountOfList += map.getValue();
		}
		
		return currentAmountOfList;
	}

	public void setAmountOfShoppingList(double amount) throws IllegalAmountException {
		if (this.amount > 0) {
			this.amount = amount;
		} else {
			throw new IllegalAmountException("The amount must be positive !");
		}
	}
	
	// getters and setters
	boolean getIsPaid() {
		return this.isPaid;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public HashMap<String, Double> getShoppingEntries() {
		return shoppingEntries;
	}

}
