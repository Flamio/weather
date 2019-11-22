package App.model;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@MappedSuperclass
public abstract class DaoEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

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

    private String name;
}
