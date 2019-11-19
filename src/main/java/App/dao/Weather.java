package App.dao;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Weather extends DaoEntity {
    private int temperature;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "weather_event_binding",
            joinColumns = @JoinColumn(name = "weather_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }




}
