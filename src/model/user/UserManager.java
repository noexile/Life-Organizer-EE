package model.user;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import model.accounts.DebitAccount;
import model.dataBase.DBManager;
import model.events.DatedEvent;
import model.events.NotificationEvent;
import model.events.PaymentEvent;
import model.events.ShoppingList;
import model.events.TODOEvent;
import model.exceptions.IllegalAmountException;

public class UserManager {
	
	private User user;
	private DBManager dbmanager;
	
	public UserManager(User user) {
		this.user = user;
		this.dbmanager = DBManager.getInstance();
	}
	
	/*-------------PAYMENT EVENT-------------*/
	public void addPaymentEvent(PaymentEvent event) {
       this.user.addPaymentEvent(event);
	}
	
	public void addPaymentEventINDataBase(PaymentEvent event) throws SQLException{
		
		String statement = "INSERT INTO " + DBManager.getDbName() + ".payment_events (user_id,pe_name,description,amount,is_paid,for_date) VALUES (?,?,?,?,?,?);";
		PreparedStatement st = (PreparedStatement) this.dbmanager.getConnection().prepareStatement(statement);
		st.setInt(1, this.user.getUniqueDBId());
		st.setString(2, event.getTitle());
		st.setString(3, event.getDescription());
		st.setDouble(4, event.getAmount());
		st.setBoolean(5, event.getIsPaid());
		st.setDate(6, Date.valueOf(event.getDateTime()));
		int n = st.executeUpdate();	
		
	}
	
	public void removePaymentEvent(PaymentEvent event) {
		this.user.removePaymentEvent(event);
	}
	
	public List<DatedEvent> getEvents() {
		return Collections.unmodifiableList((ArrayList<DatedEvent>)(this.user.getEvents()));
	}
	
	public void addEvent(DatedEvent event){
		this.user.addEvent(event);
	}
	
	public void createPaymentEvent(String eventTitle, String description, double amount, boolean isIncome, boolean isPaid, LocalDate forDate) throws IllegalAmountException, SQLException{
		this.addPaymentEventINDataBase(new PaymentEvent(eventTitle, description, amount, isIncome, isPaid, forDate));
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

	public void addTODO(String type, TODOEvent event){
		try {
			this.dbmanager.getConnection().setAutoCommit(false);
			int user_id = this.user.getUniqueDBId();
			Statement st = this.dbmanager.getConnection().createStatement();
			String insertToDo = "INSERT INTO lo.todos (user_id, todo_name, todo_type, description) VALUES (" + user_id + ", '" + event.getTitle() + "', '" + event.getType() + "', '" + event.getDescription() + "');";
			st.execute(insertToDo);
			
			this.dbmanager.getConnection().commit();
			this.dbmanager.getConnection().setAutoCommit(false);
			
			this.user.addTODO(type, event);
		} catch (SQLException e) {
			System.out.println("Error in executing TODO import into DB: " + e.getMessage());
			try {
				this.dbmanager.getConnection().rollback();
			} catch (SQLException e1) {}
		}
	}

    public ArrayList<TODOEvent> getTodos(String type){
        return (ArrayList<TODOEvent>) Collections.unmodifiableList(this.user.getTodos(type));
    }

	public void removeTODOEvent(TODOEvent event){
		this.user.removeTODOEvent(event);
	}
	
	public void createTODO(String name, String description, String type){
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
