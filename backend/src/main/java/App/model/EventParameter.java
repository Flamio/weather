package App.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class EventParameter {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String type;

    private String name;

    @ManyToOne
    @JoinColumn(name="event_id", nullable=false)
    private Event event;

    @OneToMany(mappedBy = "parameter")
    private List<EventValue> eventValues;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<EventValue> getEventValues() {
        return eventValues;
    }

    public void setEventValues(List<EventValue> eventValues) {
        this.eventValues = eventValues;
    }
}
