package App.model;

import javax.persistence.*;

@Entity
public class EventValue {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="parameter_id", nullable=false)
    private EventParameter parameter;

    @ManyToOne
    @JoinColumn(name="weather_id", nullable=false)
    private Weather weather;

    private String value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public EventParameter getParameter() {
        return parameter;
    }

    public void setParameter(EventParameter parameter) {
        this.parameter = parameter;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
