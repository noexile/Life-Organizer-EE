package model.user;

import model.accounts.DebitAccount;
import model.events.DatedEvent;
import model.events.NotificationEvent;
import model.events.PaymentEvent;
import model.events.ShoppingList;
import model.events.TODOEvent;
import model.exceptions.IllegalAmountException;
import model.exceptions.IncorrectInputException;
import model.exceptions.NotExistException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;




public class User {

	private String userName;
	private String password;
    private int uniqueDBId;
	private String email;

	private ArrayList<PaymentEvent> events;
	private HashMap<String, ArrayList<TODOEvent>> todos;//type -> events
	private ArrayList<ShoppingList> shoppingList;
	private ArrayList<DebitAccount> debitAccounts;
	private ArrayList<NotificationEvent> notifications;
	private BigDecimal money;

	public User(String userName, String password, int uniqueDBId, String email) {
        this.setUserName(userName);
        this.setPassword(password);
        this.uniqueDBId = uniqueDBId;
        this.email = email;
        this.money = new BigDecimal(0.1);
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
		this.todos = new HashMap<>();
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
	
	protected ArrayList<ShoppingList> getShoppingLists(){
		return this.shoppingList;
	}

	protected void createShoppingList(String name) {
		if (name.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("You cannot create a shopping list without a name!");
			} catch (IncorrectInputException e) {}
		}
		
		this.shoppingList.add(new ShoppingList(name));
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
		
		entry.setName(name);
	}
	
	protected void addItemToShoppingList(ShoppingList list, String itemName) {
		if (list == null) {
			try {
				throw new NotExistException("Must select ShoppingList to add item in it!");
			} catch (NotExistException e) {}
		}
		
		if (itemName.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input item name must not be empty!");
			} catch (IncorrectInputException e) {}
		}
		
		list.addEntry(itemName,(double) 0);
	}
	
	protected void addItemToShoppingList(ShoppingList list, String itemName, double price) {
		if (list == null) {
			try {
				throw new NotExistException("Must select ShoppingList to add item in it!");
			} catch (NotExistException e) {}
		}
		
		if (itemName.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input item name must not be empty!");
			} catch (IncorrectInputException e) {}
		}
			list.addEntry(itemName, price);
		
	}
	
	protected void removeItemFromShoppingList(ShoppingList list, String itemName) {
		if (list == null) {
			try {
				throw new NotExistException("Must select ShoppingList to edit!");
			} catch (NotExistException e) {}
		}
		
		if (!list.getShoppingEntries().containsKey(itemName)) {
			try {
				throw new IncorrectInputException("The item does not exist in the shopping list!");
			} catch (IncorrectInputException e) {}
		}
		
		list.getShoppingEntries().get(itemName);
	}
	
	protected void payShoppingList(ShoppingList list, double amount) {
		if (list == null) {
			try {
				throw new NotExistException("Must select ShoppingList to edit!");
			} catch (NotExistException e) {}
		}
		
		double temp = this.money.doubleValue();
		if (temp < amount) {
			try {
				throw new IllegalAmountException("Not enough money in the account!");
			} catch (IllegalAmountException e) {}
		}

		BigDecimal tempDecimal = new BigDecimal(amount);
		this.money.subtract(tempDecimal);
		
	}
	
	
	/*--------------TODO LIST EVENT---------------*/
	
	protected void addTODO(String type, TODOEvent event){
		if (!this.todos.containsKey(type)){
            this.todos.put(type, new ArrayList<TODOEvent>());
        }

        this.todos.get(type).add(event);
	}

    protected ArrayList<TODOEvent> getTodos(String type){
        if (!this.todos.containsKey(type)){
            this.todos.put(type, new ArrayList<TODOEvent>());
        }

        return this.todos.get(type);
    }
    
    protected void removeTODOEvent(TODOEvent event){
    	if (event == null) {
			try {
				throw new NotExistException("Must select TODO to remove!");
			} catch (NotExistException e) {}
		}
    	
    	String type = event.getType();
    	for(TODOEvent currEvent : this.getTodos(type)){
    		if(currEvent.getTitle().equals(event.getTitle())){
    			this.getTodos(type).remove(currEvent);
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

		this.addTODO(type,new TODOEvent(name, description, type));
	}
    
    protected void modifyTODO(TODOEvent todo, String name, String description) {
		/*
		 * isIncome will be button (radio?) and will be a must to continue - no
		 * need for check in the OOP isPayed will be button (radio?) and will be a must to continue - no need for check in the OOP
		 */
		if (todo == null) {
			try {
				throw new NotExistException("Must select TODO to edit!");
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

		todo.setTitle(name);
		todo.setDescription(description);
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

	protected BigDecimal getMoney() {
		return money;
	}
	
	
}
