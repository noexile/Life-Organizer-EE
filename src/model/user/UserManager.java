package model.user;

import java.sql.Connection;
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
import model.events.ShoppingEntry;
import model.events.ShoppingList;
import model.events.TODOEvent;
import model.exceptions.IllegalAmountException;

public class UserManager {
	
	private User user;
	private DBManager dbmanager;
	
	public UserManager(User user){
		this.user = user;
		this.dbmanager = DBManager.getInstance();
		generateAllPaymentEventFromDBForUser(this.user);
		generateAllShoppingListFromDBForUser(this.user);
		generateAllToDoFromDBForUser(this.user);
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
	
	synchronized public void addPaymentEventINDataBase(PaymentEvent event) throws SQLException{
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
	
	synchronized private PaymentEvent getTheLastAddedPaymentEventInDB(){
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
		String statment = "DELETE FROM "+DBManager.getDbName()+"."+DBManager.ColumnNames.PAYMENT_EVENTS.toString().toLowerCase()+" WHERE pe_id = "+event.getUniqueIDForPayment();
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
	
	
	public void createPaymentEvent(String eventTitle, String description, double amount, boolean isIncome, boolean isPaid, LocalDate forDate) throws IllegalAmountException, SQLException{
		PaymentEvent pe = new PaymentEvent(eventTitle, description, amount, isIncome, isPaid, forDate);
		addPaymentEventINDataBase(pe);
		this.user.createPaymentEvent(pe);
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
	
	private void updatePaymentEventINDB(String eventTitle, String description, double amount, boolean isIncome, boolean isPaid, LocalDate forDate, int uniqueIDForPayment) throws SQLException{
		String update = "UPDATE "+DBManager.getDbName()+"."+DBManager.ColumnNames.PAYMENT_EVENTS.toString().toLowerCase()+" SET pe_name = ?,description = ?, amount=?, is_paid=?, is_income=?, for_date=? WHERE pe_id = "+uniqueIDForPayment;
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
		String select ="SELECT pe_id,pe_name,description,amount,is_paid,is_income,for_date FROM "+DBManager.getDbName()+"."+DBManager.ColumnNames.PAYMENT_EVENTS.toString().toLowerCase()+" WHERE (user_id = "+this.user.getUniqueDBId()+")";
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
	
	private void updateShoppingListStatusINDB(ShoppingList list) throws SQLException{
		String statement = "UPDATE "+DBManager.getDbName()+"."+DBManager.ColumnNames.SHOPPING_LISTS+" SET is_paid = true WHERE sl_id = "+list.getUniqueIDForPayment();
		try(Statement st = this.dbmanager.getConnection().createStatement()){
			st.executeUpdate(statement);
		}
	}
	
	private void generateAllShoppingListFromDBForUser(User user){
		String statement = "SELECT sl_id,list_name,in_date,is_paid FROM "+DBManager.getDbName()+"."+DBManager.ColumnNames.SHOPPING_LISTS.toString()+" WHERE user_id = "+user.getUniqueDBId();
		try(Statement st = this.dbmanager.getConnection().createStatement();ResultSet rs = st.executeQuery(statement);){
			while(rs.next()){
				String name = rs.getString(2);
				int uniqueID = rs.getInt(1);
				LocalDate date = rs.getDate(3).toLocalDate();
				boolean isPaid = rs.getBoolean(4);
				
				ArrayList<ShoppingEntry> entriesFromDB = new ArrayList<>();
				String secondStatement = "SELECT se_id,item_name,item_value FROM "+DBManager.getDbName()+"."+DBManager.ColumnNames.SHOPPING_ENTRIES.toString()+" WHERE list_id = "+uniqueID;
				try(
						Statement state = this.dbmanager.getConnection().createStatement();
						ResultSet secondSet = state.executeQuery(secondStatement);
					){
					while(secondSet.next()){
						entriesFromDB.add(new ShoppingEntry(secondSet.getString(2), secondSet.getDouble(3),secondSet.getInt(1)));
					}
				}
				
				this.user.addShoppingList(new ShoppingList(name, isPaid, uniqueID, date, entriesFromDB));
			}
		}catch(SQLException | IllegalAmountException e){
			System.out.println("PRoblem with generate all shopping lists");
			System.out.println(e.getMessage());
		}
		
		
	}
	
	private void insertNewShoppingListINDB(String name) throws SQLException{
		String query = "INSERT INTO "+DBManager.getDbName()+"."+DBManager.ColumnNames.SHOPPING_LISTS.toString().toLowerCase()+" (list_name,in_date,is_paid,user_id) VALUES (?,?,?,?);";
		try(
				PreparedStatement st = (PreparedStatement) this.dbmanager.getConnection().prepareStatement(query);
				){
			st.setString(1, name);
			st.setDate(2, Date.valueOf(LocalDate.now()));
			st.setBoolean(3, false);
			st.setInt(4, this.user.getUniqueDBId());
			st.executeUpdate();
		}
	}
	
	private void deleteShoppingListFromDB(ShoppingList list) throws SQLException{
		String statement = "DELETE FROM "+DBManager.getDbName()+"."+DBManager.ColumnNames.SHOPPING_LISTS.toString().toLowerCase()+" WHERE sl_id = "+list.getUniqueIDForPayment();
		try(Statement st = this.dbmanager.getConnection().createStatement()){
			st.executeUpdate(statement);
		}
	}
	
	public void addShoppingList(ShoppingList shoppingList) {
        this.user.addShoppingList(shoppingList);
	}

	public void removeShoppingList(ShoppingList list) throws SQLException {
		this.deleteShoppingListFromDB(list);
		this.user.removeShoppingList(list);
	}
	
	public ArrayList<ShoppingList> getShoppingLists(){
		return this.user.getShoppingLists();
	}
	
	public void createShoppingList(String name) throws IllegalAmountException, SQLException{
		this.insertNewShoppingListINDB(name);
		this.user.createShoppingList(this.getLastAddedListINDB(this.user.getUniqueDBId()));
	}
	
	public void modifyShoppingList(ShoppingList entry, String name){
		this.user.modifyShoppingList(entry, name);
	}
	
	public void addItemToShoppingList(ShoppingList list, ShoppingEntry entry) throws SQLException{
		this.insertShoppingEntryINDB(entry, list.getUniqueIDForPayment());
		this.user.addItemToShoppingList(list,this.getLastAddedEntryINDB(list.getUniqueIDForPayment()));
	}
	
	public void removeItemFromShoppingList(ShoppingList list, ShoppingEntry entry) throws SQLException{
		this.deleteShoppingEntryFromDB(entry);
		this.user.removeItemFromShoppingList(list, entry);
	}
	
	public void payShoppingList(ShoppingList list, double amount) throws SQLException{
		this.updateShoppingListStatusINDB(list);
		this.user.payShoppingList(list, amount);
	}
	
	private void insertShoppingEntryINDB(ShoppingEntry newEntry,int listID) throws SQLException{
		String statement = "INSERT INTO "+DBManager.getDbName()+"."+DBManager.ColumnNames.SHOPPING_ENTRIES.toString().toLowerCase()+" (item_name,item_value,list_id) VALUES (?,?,?);";
		try(
				PreparedStatement stat = (PreparedStatement) this.dbmanager.getConnection().prepareStatement(statement);
				
				){
			
			stat.setString(1, newEntry.getName());
			stat.setDouble(2,newEntry.getAmount());
			stat.setInt(3, listID);
			stat.executeUpdate();
		}
	}
	
	private ShoppingEntry  getLastAddedEntryINDB(int listID) throws SQLException{
		ShoppingEntry entry = null;
		String statement = "SELECT se_id,item_name,item_value FROM "+DBManager.getDbName()+"."+DBManager.ColumnNames.SHOPPING_ENTRIES.toString().toLowerCase()+" WHERE se_id = (SELECT MAX(se_id) FROM "
																	+DBManager.getDbName()+"."+DBManager.ColumnNames.SHOPPING_ENTRIES.toString().toLowerCase()+" HAVING list_id = "+listID+")";
		try(
				Statement st = this.dbmanager.getConnection().createStatement();
				ResultSet rs = st.executeQuery(statement);
				){
			while(rs.next()){
				int uniqueID = rs.getInt(1);
				String name = rs.getString(2);
				double amount = rs.getDouble(3);
				System.out.println(uniqueID);
				entry = new ShoppingEntry(name, amount, uniqueID);
				break;
			}
		}
		return entry;
		
	}
	
	private void deleteShoppingEntryFromDB(ShoppingEntry entry) throws SQLException{
		String statement = "DELETE FROM "+DBManager.getDbName()+"."+DBManager.ColumnNames.SHOPPING_ENTRIES.toString().toLowerCase()+" WHERE se_id = "+entry.getUniqueID();
		try(
				Statement st = this.dbmanager.getConnection().createStatement();
				){
			st.executeUpdate(statement);
		}
	}
	
	private ShoppingList getLastAddedListINDB(int userID) throws IllegalAmountException, SQLException{
		ShoppingList list = null;
		String statement = "SELECT sl_id,list_name,in_date,is_paid FROM "+DBManager.getDbName()+"."+DBManager.ColumnNames.SHOPPING_LISTS.toString().toLowerCase()+" WHERE sl_id = (SELECT MAX(sl_id) FROM "
																		 +DBManager.getDbName()+"."+DBManager.ColumnNames.SHOPPING_LISTS.toString().toLowerCase()+" HAVING user_id = "+userID+")";		
		
		try(
				Statement st = this.dbmanager.getConnection().createStatement();
				ResultSet rs = st.executeQuery(statement);
				){
			while(rs.next()){
				int uniqueID = rs.getInt(1);
				String name = rs.getString(2);
				LocalDate date = rs.getDate(3).toLocalDate();
				boolean isPaid = rs.getBoolean(4);
				list = new ShoppingList(name,isPaid,uniqueID,date,new ArrayList<ShoppingEntry>());
				break;
			}
		}
		return list;
		
	}
	
	/*--------------TODO LIST EVENT---------------*/

	public void addTODO(TODOEvent event){
		try {
			this.dbmanager.getConnection().setAutoCommit(false);
			int user_id = this.user.getUniqueDBId();
			Statement st = this.dbmanager.getConnection().createStatement();
			String insertToDo = "INSERT INTO " + DBManager.getDbName() + "." + DBManager.ColumnNames.TODOS.toString().toLowerCase() + " (user_id, todo_name, todo_type, description) VALUES (" + user_id + ", '" + event.getTitle() + "', '" + event.getType() + "', '" + event.getDescription() + "');";
			st.execute(insertToDo);
			
			this.dbmanager.getConnection().commit();
			this.dbmanager.getConnection().setAutoCommit(false);
			
			this.user.addTODO(event);
		} catch (SQLException e) {
			System.out.println("Error in executing TODO import into DB: " + e.getMessage());
			try {
				this.dbmanager.getConnection().rollback();
			} catch (SQLException e1) {}
		}
	}

    public ArrayList<TODOEvent> getTodos(String type){
        return (ArrayList<TODOEvent>) Collections.unmodifiableList(this.user.getTodos());
    }

	public void removeTODOEvent(TODOEvent event){
		// remove todo from db
		 	Connection conn = null;
		 		
		 		try {
		 			String removeToDo = "DELETE FROM " + DBManager.getDbName() + "." + DBManager.ColumnNames.TODOS.toString().toLowerCase() + " WHERE todo_id = " + event.getUniqueID() + ";";
		 				
					conn = DBManager.getInstance().getConnection();
		 			conn.setAutoCommit(false);
		 			
		 			Statement st = conn.createStatement();
		 			st.executeUpdate(removeToDo);	
		 
		 			// remove todo from collection
		 			this.user.removeTODOEvent(event);
		 		
					conn.setAutoCommit(true);
		 			conn.commit();
		 		} catch (SQLException e) {
		 		System.out.println("Error in executing delete todo request: " + e.getMessage());
		 			try {
		 				conn.rollback();
		 			} catch (SQLException e1) {}
		 		}
		 		
		 		// close connection ???		
	}
	
	public void createTODO(String name, String description, String type){
		this.user.createTODO(name, description, type);
	}
	
	public void modifyTODO(String title, String description, String type, int id){
		Connection conn = null;
		 		
				try {
		 			String updateToDo = "UPDATE " + DBManager.getDbName() + "." + DBManager.ColumnNames.TODOS.toString().toLowerCase() + " SET todo_name = ?, todo_type = ?, description = ? WHERE todo_id =" + id + ";";
		 			
		 			conn = DBManager.getInstance().getConnection();
		 			conn.setAutoCommit(false);
		 			
		 			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(updateToDo);
		 			ps.setString(1, title);
		 			ps.setString(2, type);
		 			ps.setString(3, description);
		 			
		 			ps.executeUpdate();
		 		    
		 			// update todo in collection
		 			this.user.modifyTODO(title, description, type, id);
		 			
		 			conn.commit();
		 			conn.setAutoCommit(true);
		 			ps.close();
		 	} catch (SQLException e) {
		 		System.out.println("Error in executing delete todo request: " + e.getMessage());
		 			try {
		 				conn.rollback();
		 			} catch (SQLException e1) {}
		 		}
		 		
				// close connection ???	
	}
	
	private void generateAllToDoFromDBForUser(User user2) {
		String select ="SELECT todo_id, todo_name, todo_type, description FROM " + DBManager.getDbName() + "." + DBManager.ColumnNames.TODOS.toString().toLowerCase() + " WHERE (user_id = " + this.user.getUniqueDBId() + ");";
		ResultSet rs = null;
		try(Statement statement = this.dbmanager.getConnection().createStatement()){
			rs = statement.executeQuery(select);
			while(rs.next() && rs != null){
				Integer id = rs.getInt(1);
				String name = rs.getString(2);
				String type = rs.getString(3);
				String description = rs.getString(4);
				this.user.addTODO(new TODOEvent(name, description, type, id));
			}
		} catch (SQLException e) {
			System.out.println("Problem with select of payment events " + e.getMessage());
		}finally{
			try {
				rs.close();
			} catch (SQLException e1) {
				System.out.println("Error with close conection: " + e1.getMessage());
			}
		}
	}
	
	public TODOEvent getToDoById(int id) {
		
		 for (int i = 0; i < this.user.getTodos().size(); i++) {
		 	if (this.user.getTodos().get(i).getUniqueID() == id) {
		 			
		 			return this.user.getTodos().get(i);
		 		}
		 	}
		 	
		 	return null;
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
	 
	 
	
	 public User getUser() {
		 return user;	 
	 }
	 
	 public double getMoney(){
		 return this.user.getMoney();
	 }
}
