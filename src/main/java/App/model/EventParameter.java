package App.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class EventParameter extends DaoEntity {

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Column(nullable = false)
    private int value;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="event_id", nullable=false)
    private Event event;
}
