package App.model;

import App.dto.WindDto;

import javax.persistence.*;

@Entity
public class Wind {

    private String name;

    public Wind(WindDto dto) {
        speed = dto.getSpeed();
        name = dto.getName();
    }

    public Wind() {
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private float speed;

    @OneToOne(cascade = CascadeType.ALL)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
