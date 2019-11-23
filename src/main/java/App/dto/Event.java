package App.dto;

import App.model.Wind;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EarthQuakeDto.class, name = "earthQuake"),
        @JsonSubTypes.Type(value = PrecipitationDto.class, name = "precipitation"),
        @JsonSubTypes.Type(value = WindDto.class, name = "wind")
})
public abstract class Event {

    public Event(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
