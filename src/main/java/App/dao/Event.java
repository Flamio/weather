package App.dao;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Event extends DaoEntity {

    public Set<EventParameter> getParameters() {
        return parameters;
    }

    public void setParameters(Set<EventParameter> parameters) {
        this.parameters = parameters;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "event_parameter_binding",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "parameter_id"))
    private Set<EventParameter> parameters;
}
