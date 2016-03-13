package model.events;

public class TODOEvent extends Event{

	public String type;

	public TODOEvent(String name,String description, String type){
		super(name, description);
		this.type = type;
	}
	
	public String getType(){
		return this.type;
	}

}
