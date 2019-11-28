package App.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany(mappedBy = "event")
    private List<EventParameter> parameters;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EventParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<EventParameter> parameters) {
        this.parameters = parameters;
    }
}
