package App.dto;

public class EarthQuakeDto extends Event {

    public EarthQuakeDto(String type, float magnitudeScaleValue) {
        super(type);
        this.magnitudeScaleValue = magnitudeScaleValue;
    }

    private float magnitudeScaleValue;

    public float getMagnitudeScaleValue() {
        return magnitudeScaleValue;
    }

    public void setMagnitudeScaleValue(float magnitudeScaleValue) {
        this.magnitudeScaleValue = magnitudeScaleValue;
    }
}
