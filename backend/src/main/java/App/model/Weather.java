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


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(nullable = false, unique = true)
    private Date date;

    @OneToMany(mappedBy = "weather", cascade=CascadeType.ALL)
    private List<Precipitation> precipitations;

    @OneToOne(mappedBy = "weather", cascade=CascadeType.ALL)
    private EarthQuake earthQuake;

    @OneToOne(mappedBy = "weather", cascade=CascadeType.ALL)
    private Wind wind;

    public List<Precipitation> getPrecipitations() {
        return precipitations;
    }

    public void setPrecipitations(List<Precipitation> precipitations) {
        this.precipitations = precipitations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EarthQuake getEarthQuake() {
        return earthQuake;
    }

    public void setEarthQuake(EarthQuake earthQuake) {
        this.earthQuake = earthQuake;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}