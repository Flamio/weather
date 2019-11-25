package App.model;

import App.dto.PrecipitationDto;

import javax.persistence.*;

@Entity
public class Precipitation {

    public Precipitation() {
    }

    public Precipitation(PrecipitationDto dto) {
        numberOfDailyAllowances = dto.getNumberOfDailyAllowances();
        intensity = dto.getIntensity();
        temperature = dto.getTemperature();
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private int numberOfDailyAllowances;
    private float intensity;
    private float temperature;


    @ManyToOne(cascade = CascadeType.ALL)
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

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
