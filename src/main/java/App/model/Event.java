package App.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Event extends DaoEntity {

    public List<EventParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<EventParameter> parameters) {
        this.parameters = parameters;
    }

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventParameter> parameters;

    @Transient
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
