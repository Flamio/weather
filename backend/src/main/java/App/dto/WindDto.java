package App.dto;

public class WindDto extends Event {

    private String name;

    public WindDto(String type, float speed, String name) {
        super(type);
        this.speed = speed;
        this.name = name;
    }

    private float speed;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
