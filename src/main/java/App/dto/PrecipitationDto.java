package App.dto;

public class PrecipitationDto extends Event{

    public PrecipitationDto(String type, int numberOfDailyAllowances, float intensity, float temperature) {
        super(type);
        this.numberOfDailyAllowances = numberOfDailyAllowances;
        this.intensity = intensity;
        this.temperature = temperature;
    }

    private int numberOfDailyAllowances;
    private float intensity;
    private float temperature;

    public int getNumberOfDailyAllowances() {
        return numberOfDailyAllowances;
    }

    public void setNumberOfDailyAllowances(int numberOfDailyAllowances) {
        this.numberOfDailyAllowances = numberOfDailyAllowances;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
