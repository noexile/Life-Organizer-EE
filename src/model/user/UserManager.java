package model.user;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		this.generateAllPaymentEventFromDBForUser(this.user);
	}
	
	/*-------------PAYMENT EVENT-------------*/
	public void addPaymentEvent(PaymentEvent event) {
       this.user.addPaymentEvent(event);
	}
	
	public ArrayList<PaymentEvent> getPaymentEventsByConcretDate(LocalDate date){
		ArrayList<PaymentEvent> byDate = new ArrayList<>();
		for(DatedEvent e : this.getEvents()){
			if(e.getDateTime().equals(date)){
				byDate.add((PaymentEvent) e);
			}
		}
		return byDate;
	}
	
	public void addPaymentEventINDataBase(PaymentEvent event) throws SQLException{
		PreparedStatement st = null;
		try{
			String statement = "INSERT INTO " + DBManager.getDbName() + ".payment_events (user_id,pe_name,description,amount,is_paid,is_income,for_date) VALUES (?,?,?,?,?,?,?);";
			st = (PreparedStatement) this.dbmanager.getConnection().prepareStatement(statement);
			st.setInt(1, this.user.getUniqueDBId());
			st.setString(2, event.getTitle());
			st.setString(3, event.getDescription());
			st.setDouble(4, event.getAmount());
			st.setBoolean(5, event.getIsPaid());
			st.setBoolean(6, event.getIsIncome());
			st.setDate(7, Date.valueOf(event.getDateTime()));
			st.executeUpdate();		
		}catch(SQLException e){
			throw e;
		}finally{
			st.close();
		}
	}
	
	private synchronized PaymentEvent getTheLastAddedPaymentEventInDB(){
		Statement st=null;
		ResultSet rs = null;
		PaymentEvent lastAdded = null;
		String select = "SELECT max(pe_id) FROM "+DBManager.getDbName()+"."+DBManager.ColumnNames.PAYMENT_EVENTS.toString()+" GROUP BY user_id HAVING user_id = "+this.user.getUniqueDBId()+";";
		try{
			st = this.dbmanager.getConnection().createStatement();
			rs = st.executeQuery(select);
			while(rs.next()){
				Integer peID = rs.getInt(1);
				lastAdded = this.getPaymentEventByID(peID);
			}
		}catch(SQLException e){
			System.out.println("Problem with get added last payment");
			e.printStackTrace();
		}
		finally{
			try {
				st.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lastAdded;
	}
	
	private PaymentEvent getPaymentEventByID(int uniqueID){
		Statement st = null;
		ResultSet rs= null;
		PaymentEvent newPayment = null;
		String statement = "SELECT pe_id,pe_name,description,amount,is_paid,is_income,for_date FROM "+DBManager.getDbName()+"."+DBManager.ColumnNames.PAYMENT_EVENTS.toString()+" WHERE pe_id = "+uniqueID;
		try{
			st = this.dbmanager.getConnection().createStatement();
			rs = st.executeQuery(statement);
			while(rs.next()){
				Integer uniID = rs.getInt(1);
				String name = rs.getString(2);
				String description = rs.getString(3);
				double amount = rs.getDouble(4);
				boolean isPaid = rs.getBoolean(5);
				boolean isIncome = rs.getBoolean(6);
				LocalDate dateTime = rs.getDate(7).toLocalDate();
				newPayment = new PaymentEvent(name, description, amount, isIncome, isPaid, dateTime,uniID);
			}
		}catch(SQLException e){
			System.out.println("PROBLEM WITH get payment event by id"+e.getMessage());
			e.printStackTrace();
		} catch (IllegalAmountException e) {
			System.out.println("Error in method get payment event by id");
			e.printStackTrace();
		}
		finally{
			try {
				st.close();
				rs.close();
			} catch (SQLException e) {
				System.out.println("Error with close connection"+e.getMessage());
				e.printStackTrace();
			}
		}
		return newPayment;
	}
	
	public void removePaymentEvent(PaymentEvent event) throws SQLException {
		this.removePaymentEventFromDB(event);
		this.user.removePaymentEvent(event);
	}
	
	private void removePaymentEventFromDB(PaymentEvent event) throws SQLException{
		String statment = "DELETE FROM "+DBManager.getDbName()+"."+DBManager.ColumnNames.PAYMENT_EVENTS.toString()+" WHERE pe_id = "+event.getUniqueIDForPayment();
		try(Statement st = this.dbmanager.getConnection().createStatement()){
			st.executeUpdate(statment);
		}
	}
	
	public List<PaymentEvent> getEvents() {
		List<PaymentEvent> sortedList = new ArrayList<PaymentEvent>(this.user.getEvents());
		Collections.sort(sortedList,new Comparator<PaymentEvent>() {

			@Override
			public int compare(PaymentEvent o1, PaymentEvent o2) {
				return o1.getDateTime().compareTo(o2.getDateTime());
			}
		});
		return Collections.unmodifiableList((ArrayList<PaymentEvent>)(sortedList));
	}
	
	public void addEvent(PaymentEvent event){
		this.user.addEvent(event);
	}
	
	public void createPaymentEvent(String eventTitle, String description, double amount, boolean isIncome, boolean isPaid, LocalDate forDate) throws IllegalAmountException, SQLException{
		this.addPaymentEventINDataBase(new PaymentEvent(eventTitle, description, amount, isIncome, isPaid, forDate));
		this.user.createPaymentEvent(this.getTheLastAddedPaymentEventInDB());
	}
	
	public void modifyPaymentEvent(PaymentEvent event, String eventTitle, String description, double amount, boolean isIncome, boolean isPaid, LocalDate forDate) throws IllegalAmountException, SQLException{
		this.updatePaymentEventINDB(eventTitle,description,amount,isIncome,isPaid,forDate,event.getUniqueIDForPayment());
		this.user.modifyPaymentEvent(event, eventTitle, description, amount, isIncome, isPaid, forDate);
	}

	public void pay(PaymentEvent event){
		this.user.pay(event);
		try {
			this.updatePaymentEventINDB(event.getTitle(), event.getDescription(), event.getAmount(), event.getIsIncome(), event.getIsPaid(), event.getDateTime(), event.getUniqueIDForPayment());
		} catch (SQLException e) {
			System.out.println("Problem to update in data base =pay payment event");
			e.printStackTrace();
		}
		
	}
	
	private void updatePaymentEventINDB(String eventTitle, String description, double amount, boolean isIncome,
			boolean isPaid, LocalDate forDate, int uniqueIDForPayment) throws SQLException{
		String update = "UPDATE "+DBManager.getDbName()+"."+DBManager.ColumnNames.PAYMENT_EVENTS.toString()+" SET pe_name = ?,description = ?, amount=?, is_paid=?, is_income=?, for_date=? WHERE pe_id = "+uniqueIDForPayment;
		try(PreparedStatement st = (PreparedStatement) this.dbmanager.getConnection().prepareStatement(update)){
			st.setString(1, eventTitle);
			st.setString(2, description);
			st.setDouble(3, amount);
			st.setBoolean(4, isPaid);
			st.setBoolean(5, isIncome);
			st.setDate(6, Date.valueOf(forDate));
			int n = st.executeUpdate();
			System.out.println(n);
		}catch(SQLException exception){
			exception.printStackTrace();
			throw exception;
		}
	}
	
	private void generateAllPaymentEventFromDBForUser(User user){
		String select ="SELECT pe_id,pe_name,description,amount,is_paid,is_income,for_date FROM "+DBManager.getDbName()+"."+DBManager.ColumnNames.PAYMENT_EVENTS.toString()+" WHERE (user_id = "+this.user.getUniqueDBId()+")";
		ResultSet rs = null;
		try(Statement statement = this.dbmanager.getConnection().createStatement()){
			rs = statement.executeQuery(select);
			if(!rs.wasNull()){
				while(rs.next()){
					int uniID = rs.getInt(1);
					String name = rs.getString(2);
					String description = rs.getString(3);
					double amount = rs.getDouble(4);
					boolean isPaid = rs.getBoolean(5);
					boolean isIncome = rs.getBoolean(6);
					LocalDate dateTime = rs.getDate(7).toLocalDate();
					this.addPaymentEvent(new PaymentEvent(name, description, amount, isIncome, isPaid, dateTime,uniID));
				}
			}
		} catch (IllegalAmountException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e1) {
			System.out.println("Problem with select of payment events " + e1.getMessage());
		}finally{
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("Error with close conection");
				e.printStackTrace();
			}
		}
		
		
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
	 
	 public double getMoney(){
		 return this.user.getMoney().doubleValue();
	 }
}
