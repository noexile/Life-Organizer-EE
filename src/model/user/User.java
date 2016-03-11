package model.user;

import model.accounts.DebitAccount;
import model.events.DatedEvent;
import model.events.PaymentEvent;
import model.events.ShoppingList;
import model.events.TODOEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

public class User {

	private String userName;
	private String password;
    private int uniqueDBId;
	private String email;

	private TreeSet<DatedEvent> events;
	private HashMap<TODOEvent.Type, ArrayList<TODOEvent>> todos;//type -> events
	private ArrayList<ShoppingList> shoppingList;
	private ArrayList<DebitAccount> debitAccounts;

	public User(String userName, String password, int uniqueDBId, String email) {
        this.setUserName(userName);
        this.setPassword(password);
        this.uniqueDBId = uniqueDBId;
        this.email = email;
        
        generateCollectionsForUser();
	}
	
	public User(String userName, String password, String email) {
		this.setUserName(userName);
		this.setPassword(password);
		this.email = email;
		
		generateCollectionsForUser();
	}

	// methods
	
	private void generateCollectionsForUser() {
		this.debitAccounts = new ArrayList<>();
		this.todos = new HashMap<>();
		this.shoppingList = new ArrayList<>();

        this.events = new TreeSet<DatedEvent>(new Comparator<DatedEvent>() {
            @Override
            public int compare(DatedEvent de1, DatedEvent de2) {
                return de1.getDateTime().compareTo(de2.getDateTime());
            }
        });
	}

	/*-------------PAYMENT EVENT-------------*/
	void addPaymentEvent(PaymentEvent event) {
        this.events.add(event);
	}


	void removePaymentEvent(PaymentEvent event) {
		this.events.remove(event);
	}
	
	/*--------------SHOPPING LIST EVENT---------------*/

	void addShoppingList(ShoppingList shoppingList) {
        this.shoppingList.add(shoppingList);
	}

	void removeShoppingList(ShoppingList list) {
		this.shoppingList.remove(list);
	}

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

	public TreeSet<DatedEvent> getEvents() {
		return events;
	}

	public void addTODO(TODOEvent.Type type, TODOEvent event){
		if (!this.todos.containsKey(type)){
            this.todos.put(type, new ArrayList<TODOEvent>());
        }

        this.todos.get(type).add(event);
	}

    public ArrayList<TODOEvent> getTodos(TODOEvent.Type type){
        if (!this.todos.containsKey(type)){
            this.todos.put(type, new ArrayList<TODOEvent>());
        }

        return this.todos.get(type);
    }

	public void addEvent(DatedEvent event){
        if (event == null){
            return;
        }

        this.events.add(event);
    }

    public int getId(){
        return this.uniqueDBId;
    }

    public ArrayList<DebitAccount> getDebitAccounts(){
        return this.debitAccounts;
    }

    public void addDebitAccount(DebitAccount account){
        this.debitAccounts.add(account);
    }

    // TODO
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

}
