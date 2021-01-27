
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
    "timeToLead",
    "lapsToNext",
    "timeToNext",
    "lapsToLead"
})
public class Gap {

    @JsonProperty("timeToLead")
    private Integer timeToLead;
    @JsonProperty("lapsToNext")
    private Integer lapsToNext;
    @JsonProperty("timeToNext")
    private Integer timeToNext;
    @JsonProperty("lapsToLead")
    private Integer lapsToLead;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("timeToLead")
    public Integer getTimeToLead() {
        return timeToLead;
    }

    @JsonProperty("timeToLead")
    public void setTimeToLead(Integer timeToLead) {
        this.timeToLead = timeToLead;
    }

    @JsonProperty("lapsToNext")
    public Integer getLapsToNext() {
        return lapsToNext;
    }

    @JsonProperty("lapsToNext")
    public void setLapsToNext(Integer lapsToNext) {
        this.lapsToNext = lapsToNext;
    }

    @JsonProperty("timeToNext")
    public Integer getTimeToNext() {
        return timeToNext;
    }

    @JsonProperty("timeToNext")
    public void setTimeToNext(Integer timeToNext) {
        this.timeToNext = timeToNext;
    }

    @JsonProperty("lapsToLead")
    public Integer getLapsToLead() {
        return lapsToLead;
    }

    @JsonProperty("lapsToLead")
    public void setLapsToLead(Integer lapsToLead) {
        this.lapsToLead = lapsToLead;
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
