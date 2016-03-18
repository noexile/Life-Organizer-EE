package model.user;

import model.accounts.DebitAccount;
import model.dataBase.DBManager;
import model.dataBase.DBUserDAO;
import model.events.DatedEvent;
import model.events.NotificationEvent;
import model.events.PaymentEvent;
import model.events.ShoppingEntry;
import model.events.ShoppingList;
import model.events.TODOEvent;
import model.exceptions.IllegalAmountException;
import model.exceptions.IncorrectInputException;
import model.exceptions.NotExistException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;




public class User {

	private String userName;
	private String password;
    private int uniqueDBId;
	private String email;

	private ArrayList<PaymentEvent> events;
	private ArrayList<TODOEvent> todos;//type -> events
	private ArrayList<ShoppingList> shoppingList;
	private ArrayList<DebitAccount> debitAccounts;
	private ArrayList<NotificationEvent> notifications;
	private BigDecimal money;

	public User(String userName, String password, int uniqueDBId, String email, double money) {
        this.setUserName(userName);
        this.setPassword(password);
        this.uniqueDBId = uniqueDBId;
        this.email = email;
        this.money = new BigDecimal(money);
        generateCollectionsForUser();
	}
	
	public User(String userName, String password, String email) {
		this.setUserName(userName);
		this.setPassword(password);
		this.email = email;
		this.money = new BigDecimal(0.1);
		 
		generateCollectionsForUser();
	}

	// methods
	
	private void generateCollectionsForUser() {
		this.debitAccounts = new ArrayList<>();
		this.todos = new ArrayList<>();
		this.shoppingList = new ArrayList<>();
		this.events = new ArrayList<PaymentEvent>();
		this.notifications = new ArrayList<>();
    }

	/*-------------PAYMENT EVENT-------------*/
	protected void addPaymentEvent(PaymentEvent event) {
        this.events.add(event);
	}


	protected void removePaymentEvent(PaymentEvent event) {
		if (event == null) {
			try {
				throw new NotExistException("Must select payment event to remove!");
			} catch (NotExistException e) {}
		}
		
		this.events.remove(event);
	}
	
	protected void createPaymentEvent(PaymentEvent Event) throws IllegalAmountException{
		/*
		 * amount is checked in the OOP in the PaymentEvent class 
		 * isIncome will be button (radio?) and will be a must to continue - no need for check
		 * in the OOP isPayed will be button (radio?) and will be a must to continue - no need for check in the OOP forDate will be chooser from
		 * the calendar and and will be a must to continue - no need for check in the OOP
		 */
		if (Event.getTitle() == null || Event.getTitle().trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input name must not be empty!");
			} catch (IncorrectInputException e) {}
		} else if (Event.getDescription() == null || Event.getDescription().trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input description must not be empty!");
			} catch (IncorrectInputException e) {}
		}
		this.events.add(Event);
		if(Event.getIsPaid() == true){
			this.pay(Event);
		}
	}
	
	protected void modifyPaymentEvent(PaymentEvent event, String eventTitle, String description, double amount, boolean isIncome, boolean isPaid, LocalDate forDate) throws IllegalAmountException {
		/*
		 * amount is checked in the OOP in the PaymentEvent class 
		 * isIncome will be button (radio?) and will be a must to continue - no need for check
		 * in the OOP isPayed will be button (radio?) and will be a must to continue - no need for check in the OOP forDate will be chooser from
		 * the calendar and and will be a must to continue - no need for check in the OOP
		 */
		
		if (event == null) {
			try {
				throw new NotExistException("Must select Payment Event to edit!");
			} catch (NotExistException e) {}
		}
		
		if (eventTitle == null || eventTitle.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input name must not be empty!");
			} catch (IncorrectInputException e) {
			}
		} else if (description == null || description.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input description must not be empty!");
			} catch (IncorrectInputException e) {}
		}

		event.setTitle(eventTitle);
		event.setDescription(description);
		event.setAmount(amount);
		event.setIncome(isIncome);
		event.setPaid(isPaid);
		event.setDateTime(forDate);
	}
	
	protected void pay(PaymentEvent event) {
		if (event == null) {
			try {
				throw new NotExistException("Must select payment event to pay!");
			} catch (NotExistException e) {}
		}
		
		double amount = (event.getIsIncome()) ? (event.getAmount()) : (-event.getAmount());
		
		payEvent(amount);
		event.setPaid(true);
	}

	private void payEvent(double amount) {
		double temp = this.money.doubleValue();
		
		BigDecimal tempDecimal = new BigDecimal(amount);
		this.money = this.money.add(tempDecimal);
		this.modifyMoneyAfterPayment(this.money.doubleValue());
	}
	
	/*--------------SHOPPING LIST EVENT---------------*/

	protected void addShoppingList(ShoppingList shoppingList) {
        this.shoppingList.add(shoppingList);
	}

	protected void removeShoppingList(ShoppingList list) {
		if (list == null) {
			try {
				throw new NotExistException("Must select a shopping list to remove!");
			} catch (NotExistException e) {}
		}
		this.shoppingList.remove(list);
	}

	protected void createShoppingList(ShoppingList list) throws IllegalAmountException {
		
		this.shoppingList.add(list);
	}
	
	protected ArrayList<ShoppingList> getShoppingLists(){
		return this.shoppingList;
	}
	
	protected void modifyShoppingList(ShoppingList entry, String name) {
		if (entry == null) {
			try {
				throw new NotExistException("Must select ShoppingList to edit!");
			} catch (NotExistException e) {}
		}
		
		if (name.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("You cannot edit a shopping list and leave it without a name!");
			} catch (IncorrectInputException e) {}
		}
		
		entry.setTitle(name);
	}
	
	protected void addItemToShoppingList(ShoppingList list, ShoppingEntry entry) {
		if (list == null) {
			try {
				throw new NotExistException("Must select ShoppingList to add item in it!");
			} catch (NotExistException e) {}
		}
		
		list.addEntry(entry);
		
	}
	
	
	protected void removeItemFromShoppingList(ShoppingList list,ShoppingEntry entry) {
		if (list == null) {
			try {
				throw new NotExistException("Must select ShoppingList to edit!");
			} catch (NotExistException e) {}
		}
		list.removeEntry(entry);
	}
	
	protected void payShoppingList(ShoppingList list, double amount) {
		if (list == null) {
			try {
				list.setAmount(amount);
				throw new NotExistException("Must select ShoppingList to edit!");
			} catch (NotExistException e) {} catch (IllegalAmountException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		list.setPaid(true);
		double temp = -amount;
		BigDecimal tempDecimal = new BigDecimal(temp);
		this.money.add(tempDecimal);
		this.modifyMoneyAfterPayment(this.money.doubleValue());
	}
	
	
	/*--------------TODO LIST EVENT---------------*/
	
	protected void addTODO(TODOEvent event){
        this.todos.add(event);
	}

    public ArrayList<TODOEvent> getTodos(){
        return this.todos;
    }
    
    protected void removeTODOEvent(TODOEvent event){
    	if (event == null) {
			try {
				throw new NotExistException("Must select TODO to remove!");
			} catch (NotExistException e) {}
		}
    	
    	int id = event.getUniqueID();
    	for(TODOEvent currEvent : this.getTodos()){
    		if(currEvent.getUniqueID() == id){
    			this.getTodos().remove(event);
    			return;
    		}
    	}
    }
	
    protected void createTODO(String name, String description, String type) {
		/*
		 * isIncome will be button (radio?) and will be a must to continue - no
		 * need for check in the OOP isPayed will be button (radio?) and will be a must to continue - no need for check in the OOP
		 */
		if (name == null || name.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input name must not be empty!");
			} catch (IncorrectInputException e) {}
		} else if (description == null || description.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input description must not be empty!");
			} catch (IncorrectInputException e) {}
		}

		this.addTODO(new TODOEvent(name, description, type));
	}
    
    
     protected void modifyTODO(String title, String description, String type, int id) {
    		 	
    		     	for (int i = 0; i < todos.size(); i++) {
    		 			if (todos.get(i).getUniqueID() == id) {
    		 				todos.get(i).setTitle(title);
    		 				todos.get(i).setDescription(description);
    		 				todos.get(i).setType(type);  
    		 				return;
    		 			}
    		 	}  	
    }
	
	
	/*--------------NOTIFICATION EVENTS LIST--------------*/
	
	protected void createNotificationEvent(String name, String description, LocalDate forDate) {
		/*
		 * the calendar and and will be a must to continue - no need for check in the OOP
		 */
		if (name == null || name.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input name must not be empty!");
			} catch (IncorrectInputException e) {}
		} else if (description == null || description.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input description must not be empty!");
			} catch (IncorrectInputException e) {}
		}

		this.notifications.add(new NotificationEvent(name, description, forDate));
	}

	protected void modifyNotificationEvent(NotificationEvent event, String name, String description, LocalDate forDate) {
		/*
		 * the calendar and and will be a must to continue - no need for check in the OOP
		 */
		if (event == null) {
			try {
				throw new NotExistException("Must select notification to edit!");
			} catch (NotExistException e) {}
		}
		
		if (name == null || name.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input name must not be empty!");
			} catch (IncorrectInputException e) {}
		} else if (description == null || description.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input description must not be empty!");
			} catch (IncorrectInputException e) {}
		}

		event.setTitle(name);
		event.setDescription(description);
		event.setDateTime(forDate);
	}

	protected void removeNotificationEvent(NotificationEvent event) {
		if (event == null) {
			try {
				throw new NotExistException("Must select a notification event to remove!");
			} catch (NotExistException e) {}
		}
		this.notifications.remove(event);
	}
    
	/*--------------DebitAccounts ---------------*/
	
	 protected ArrayList<DebitAccount> getDebitAccounts(){
	     return this.debitAccounts;
	 }

	 protected void addDebitAccount(DebitAccount account){
	     this.debitAccounts.add(account);
	 }
	    
	 protected void removeDebitAccount(DebitAccount account){
	   	this.debitAccounts.remove(account);
	 }
	
	
	/*------------------------------------*/

	// getters and setters
    
	private void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	private void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	protected ArrayList<PaymentEvent> getEvents() {
		return events;
	}
    
	protected void addEvent(PaymentEvent event){
        if (event == null){
            return;
        }

        this.events.add(event);
    }
    
    public int getUniqueDBId() {
		return this.uniqueDBId;
	}


	public String getEmail() {
		return this.email;
	}

	public double getMoney() {
		double money = this.money.doubleValue();
		return money;
	}
	
	private void modifyMoneyAfterPayment(double money){
		String statement = "UPDATE "+DBManager.getDbName()+"."+DBManager.ColumnNames.USERS.toString()+" SET money = "+money+" WHERE user_id = "+uniqueDBId;
		try(
				Statement st = DBManager.getInstance().getConnection().createStatement();
				){
			st.executeUpdate(statement);
			
		} catch (SQLException e) {
			System.out.println("problem with update money");
			e.printStackTrace();
		}
		
	}
	
}
