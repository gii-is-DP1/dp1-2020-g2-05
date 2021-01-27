
package motogpAPI.model2;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "fastest",
    "lap",
    "time",
    "speed"
})
public class BestLap {

    @JsonProperty("fastest")
    private Boolean fastest;
    @JsonProperty("lap")
    private Integer lap;
    @JsonProperty("time")
    private Integer time;
    @JsonProperty("speed")
    private Object speed;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("fastest")
    public Boolean getFastest() {
        return fastest;
    }

    @JsonProperty("fastest")
    public void setFastest(Boolean fastest) {
        this.fastest = fastest;
    }

    @JsonProperty("lap")
    public Integer getLap() {
        return lap;
    }

    @JsonProperty("lap")
    public void setLap(Integer lap) {
        this.lap = lap;
    }

    @JsonProperty("time")
    public Integer getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(Integer time) {
        this.time = time;
    }

    @JsonProperty("speed")
    public Object getSpeed() {
        return speed;
    }

    @JsonProperty("speed")
    public void setSpeed(Object speed) {
        this.speed = speed;
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
