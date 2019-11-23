package App.dto;

public class WindDto extends Event {

    public WindDto(String type, float speed) {
        super(type);
        this.speed = speed;
    }

    private float speed;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
