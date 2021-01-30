
package motogpAPI.model;

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
    "raceNumber",
    "eventNumber",
    "session",
    "event"
})
public class Race {

    @JsonProperty("raceNumber")
    private Integer raceNumber;
    @JsonProperty("eventNumber")
    private Integer eventNumber;
    @JsonProperty("session")
    private Session session;
    @JsonProperty("event")
    private Event event;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("raceNumber")
    public Integer getRaceNumber() {
        return raceNumber;
    }

    @JsonProperty("raceNumber")
    public void setRaceNumber(Integer raceNumber) {
        this.raceNumber = raceNumber;
    }

    @JsonProperty("eventNumber")
    public Integer getEventNumber() {
        return eventNumber;
    }

    @JsonProperty("eventNumber")
    public void setEventNumber(Integer eventNumber) {
        this.eventNumber = eventNumber;
    }

    @JsonProperty("session")
    public Session getSession() {
        return session;
    }

    @JsonProperty("session")
    public void setSession(Session session) {
        this.session = session;
    }

    @JsonProperty("event")
    public Event getEvent() {
        return event;
    }

    @JsonProperty("event")
    public void setEvent(Event event) {
        this.event = event;
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
