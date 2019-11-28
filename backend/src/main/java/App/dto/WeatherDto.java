package App.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class WeatherDto {

    public WeatherDto() {
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", locale = "ru", timezone = "Europe/Samara")
    private Date date;
    private long id;
    private List<Map<String, String>> events;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Map<String, String>> getEvents() {
        return events;
    }

    public void setEvents(List<Map<String, String>> events) {
        this.events = events;
    }
}
