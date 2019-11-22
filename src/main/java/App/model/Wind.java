package App.model;

import javax.persistence.*;

@Entity
public class Wind {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private float speed;

    @OneToOne
    @JoinColumn(name="weather_id", nullable = false)
    Weather weather;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
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
