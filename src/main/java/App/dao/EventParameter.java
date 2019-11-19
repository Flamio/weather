package App.dao;

import javax.persistence.Entity;

@Entity
public class EventParameter extends DaoEntity {
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    int value;
}
