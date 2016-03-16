package model.events;

import model.exceptions.IncorrectInputException;

import java.io.Serializable;

public abstract class Event implements Serializable {

	private String title;
	private String description;

	public Event(String title, String description) {

			this.title = title;
			this.description = description;

	}
	
	// methods
	private boolean checkIfInputIsCorrect(String title) {
		if (title.trim().isEmpty()) {
			return false;
		}
		return true;
	}
	
	
	// getters and setters
	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
