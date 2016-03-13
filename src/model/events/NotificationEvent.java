package model.events;


import java.time.LocalDate;
import java.util.Calendar;

public class NotificationEvent extends DatedEvent {

    public NotificationEvent(String title, String description, LocalDate dateTime) {
        super(title, description, dateTime);
    }
    
    public void setDateTime(LocalDate dateTime){
    	super.setDate(dateTime);
    }
}
