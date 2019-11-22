package App.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Precipitation {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private int numberOfDailyAllowances;
    private float intensity;
    private float temperature;
    private String name;

    @ManyToOne
    @JoinColumn(name="weather_id", nullable=false)
    Weather weather;

    public int getNumberOfDailyAllowances() {
        return numberOfDailyAllowances;
    }

    public void setNumberOfDailyAllowances(int numberOfDailyAllowances) {
        this.numberOfDailyAllowances = numberOfDailyAllowances;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
