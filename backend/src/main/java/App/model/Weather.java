package App.model;


import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "weather", indexes = @Index(name = "date_index", columnList = "date"))
public class Weather {

    public Weather() {
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(nullable = false, unique = true)
    private Date date;

    @OneToMany(mappedBy = "weather", cascade = CascadeType.ALL)
    private List<EventValue> eventValues;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<EventValue> getEventValues() {
        return eventValues;
    }

    public void setEventValues(List<EventValue> eventValues) {
        this.eventValues = eventValues;
    }
}