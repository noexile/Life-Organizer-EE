package model.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import model.accounts.DebitAccount;
import model.events.DatedEvent;
import model.events.NotificationEvent;
import model.events.PaymentEvent;
import model.events.ShoppingList;
import model.events.TODOEvent;
import model.exceptions.IllegalAmountException;

public class userManager {
	
	private User user;
	
	public userManager(User user) {
		this.user = user;
	}
	
	/*-------------PAYMENT EVENT-------------*/
	public void addPaymentEvent(PaymentEvent event) {
       this.user.addPaymentEvent(event);
	}
	
	public void removePaymentEvent(PaymentEvent event) {
		this.user.removePaymentEvent(event);
	}
	
	public ArrayList<DatedEvent> getEvents() {
		return (ArrayList<DatedEvent>) Collections.unmodifiableList(this.user.getEvents());
	}
	
	public void addEvent(DatedEvent event){
		this.user.addEvent(event);
	}
	
	public void createPaymentEvent(String eventTitle, String description, double amount, boolean isIncome, boolean isPaid, LocalDate forDate) throws IllegalAmountException{
		this.user.createPaymentEvent(eventTitle, description, amount, isIncome, isPaid, forDate);
	}
	
	public void modifyPaymentEvent(PaymentEvent event, String eventTitle, String description, double amount, boolean isIncome, boolean isPaid, LocalDate forDate) throws IllegalAmountException{
		this.user.modifyPaymentEvent(event, eventTitle, description, amount, isIncome, isPaid, forDate);
	}
	
	public void pay(PaymentEvent event){
		this.user.pay(event);
	}
	
	/*--------------SHOPPING LIST EVENT---------------*/
	
	public void addShoppingList(ShoppingList shoppingList) {
        this.user.addShoppingList(shoppingList);
	}

	public void removeShoppingList(ShoppingList list) {
		this.user.removeShoppingList(list);
	}
	
	public ArrayList<ShoppingList> getShoppingLists(){
		return (ArrayList<ShoppingList>) Collections.unmodifiableList(this.user.getShoppingLists());
	}
	
	public void createShoppingList(String name){
		this.user.createShoppingList(name);
	}
	
	public void modifyShoppingList(ShoppingList entry, String name){
		this.user.modifyShoppingList(entry, name);
	}
	
	public void addItemToShoppingList(ShoppingList list, String itemName){
		this.user.addItemToShoppingList(list, itemName);
	}
	
	public void addItemToShoppingList(ShoppingList list, String itemName, double price){
		this.user.addItemToShoppingList(list, itemName, price);
	}
	
	public void removeItemFromShoppingList(ShoppingList list, String itemName){
		this.user.removeItemFromShoppingList(list, itemName);
	}
	
	public void payShoppingList(ShoppingList list, double amount){
		this.user.payShoppingList(list, amount);
	}
	
	/*--------------TODO LIST EVENT---------------*/

	public void addTODO(TODOEvent.Type type, TODOEvent event){
		this.user.addTODO(type, event);
	}

    public ArrayList<TODOEvent> getTodos(TODOEvent.Type type){
        return (ArrayList<TODOEvent>) Collections.unmodifiableList(this.user.getTodos(type));
    }

	public void removeTODOEvent(TODOEvent event){
		this.user.removeTODOEvent(event);
	}
	
	public void createTODO(String name, String description, TODOEvent.Type type){
		this.user.createTODO(name, description, type);
	}
	
	public void modifyTODO(TODOEvent todo, String name, String description){
		this.user.modifyTODO(todo, name, description);
	}
	
	
	/*--------------DebitAccounts ---------------*/
	
	 public ArrayList<DebitAccount> getDebitAccounts(){
	        return (ArrayList<DebitAccount>) Collections.unmodifiableList(this.user.getDebitAccounts());
	 }

	 public void addDebitAccount(DebitAccount account){
	        this.user.addDebitAccount(account);
	 }
	    
	 public void removeDebitAccount(DebitAccount account){
	    	this.user.removeDebitAccount(account);
	 }
	
	 /*--------------NOTIFICATION EVENTS LIST--------------*/
	 
	 public void modifyNotificationEvent(NotificationEvent event, String name, String description, LocalDate forDate){
			this.user.modifyNotificationEvent(event, name, description, forDate);
	 }
	 
	 public void createNotificationEvent(String name, String description, LocalDate forDate){
		 this.user.createNotificationEvent(name, description, forDate);
	 }
	 
	 public void removeNotificationEvent(NotificationEvent event) {
		 this.user.removeNotificationEvent(event);
	 }
	 
	 /*--------------GETTERS--------------*/
	 
	 public String getUserName(){
		 return this.user.getUserName();
	 }
	 
}
