package model.events;

public class TODOEvent extends Event{

	public enum Type {ShortTerm, MidTerm, LongTerm}
	
	public Type type;

	public TODOEvent(String name,String description,TODOEvent.Type type){
		super(name,description);
		this.type = type;
	}
	
	public Type getType(){
		return this.type;
	}

}
