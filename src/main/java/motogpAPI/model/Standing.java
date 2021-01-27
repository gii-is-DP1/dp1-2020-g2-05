
package motogpAPI.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "eventClassified",
    "driver",
    "eventPoints",
    "totalPoints",
    "position"
})
public class Standing {

    @JsonProperty("eventClassified")
    private List<String> eventClassified = null;
    @JsonProperty("driver")
    private Driver driver;
    @JsonProperty("eventPoints")
    private List<Integer> eventPoints = null;
    @JsonProperty("totalPoints")
    private Integer totalPoints;
    @JsonProperty("position")
    private Integer position;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("eventClassified")
    public List<String> getEventClassified() {
        return eventClassified;
    }

    @JsonProperty("eventClassified")
    public void setEventClassified(List<String> eventClassified) {
        this.eventClassified = eventClassified;
    }

    @JsonProperty("driver")
    public Driver getDriver() {
        return driver;
    }

    @JsonProperty("driver")
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @JsonProperty("eventPoints")
    public List<Integer> getEventPoints() {
        return eventPoints;
    }

    @JsonProperty("eventPoints")
    public void setEventPoints(List<Integer> eventPoints) {
        this.eventPoints = eventPoints;
    }

    @JsonProperty("totalPoints")
    public Integer getTotalPoints() {
        return totalPoints;
    }

    @JsonProperty("totalPoints")
    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    @JsonProperty("position")
    public Integer getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(Integer position) {
        this.position = position;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
