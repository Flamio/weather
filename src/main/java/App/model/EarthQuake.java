package App.model;

import javax.persistence.*;

@Entity
public class EarthQuake {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private float magnitudeScaleValue;

    @OneToOne
    @JoinColumn(name="weather_id", nullable = false)
    Weather weather;

    public float getMagnitudeScaleValue() {
        return magnitudeScaleValue;
    }

    public void setMagnitudeScaleValue(float magnitudeScaleValue) {
        this.magnitudeScaleValue = magnitudeScaleValue;
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
