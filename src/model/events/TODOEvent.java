package model.events;

public class TODOEvent extends Event{

	private int uniqueID;
	public String type;

	public TODOEvent(String name,String description, String type){
		super(name, description);
		this.type = type;
	}
	
	public TODOEvent(String name,String description, String type, int uniqueID){
		super(name, description);
		this.type = type;
		this.uniqueID = uniqueID;
	}
	
	public String getType(){
		return this.type;
	}

	public int getUniqueID() {
		return uniqueID;
	}
	
	public void setType(String type) {
		this.type = type;
	}

}
