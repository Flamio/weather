package App.model;

import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "weather", indexes = @Index(name = "date_index", columnList = "date"))
public class Weather extends DaoEntity {

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
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
