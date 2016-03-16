package model.events;

import java.time.LocalDate;

public abstract class DatedEvent extends Event {

    private LocalDate dateTime;

    DatedEvent(String title, String description, LocalDate dateTime) {
        super(title, description);

        this.dateTime = dateTime;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }
    
    public void setDate(LocalDate dateTime){
    	this.dateTime = dateTime;
    }
    
}
