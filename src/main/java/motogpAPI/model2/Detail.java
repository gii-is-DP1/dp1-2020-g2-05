
package motogpAPI.model2;

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
    "seriesClass",
    "classifiedStatus",
    "finishPosition",
    "laps",
    "team",
    "carNumber",
    "bestLap",
    "nationality",
    "gap",
    "time",
    "avgLapSpeed",
    "pitlane",
    "drivers"
})
public class Detail {

    @JsonProperty("seriesClass")
    private Object seriesClass;
    @JsonProperty("classifiedStatus")
    private String classifiedStatus;
    @JsonProperty("finishPosition")
    private Integer finishPosition;
    @JsonProperty("laps")
    private Integer laps;
    @JsonProperty("team")
    private Team team;
    @JsonProperty("carNumber")
    private String carNumber;
    @JsonProperty("bestLap")
    private BestLap bestLap;
    @JsonProperty("nationality")
    private Nationality nationality;
    @JsonProperty("gap")
    private Gap gap;
    @JsonProperty("time")
    private Integer time;
    @JsonProperty("avgLapSpeed")
    private Double avgLapSpeed;
    @JsonProperty("pitlane")
    private Object pitlane;
    @JsonProperty("drivers")
    private List<Driver> drivers = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("seriesClass")
    public Object getSeriesClass() {
        return seriesClass;
    }

    @JsonProperty("seriesClass")
    public void setSeriesClass(Object seriesClass) {
        this.seriesClass = seriesClass;
    }

    @JsonProperty("classifiedStatus")
    public String getClassifiedStatus() {
        return classifiedStatus;
    }

    @JsonProperty("classifiedStatus")
    public void setClassifiedStatus(String classifiedStatus) {
        this.classifiedStatus = classifiedStatus;
    }

    @JsonProperty("finishPosition")
    public Integer getFinishPosition() {
        return finishPosition;
    }

    @JsonProperty("finishPosition")
    public void setFinishPosition(Integer finishPosition) {
        this.finishPosition = finishPosition;
    }

    @JsonProperty("laps")
    public Integer getLaps() {
        return laps;
    }

    @JsonProperty("laps")
    public void setLaps(Integer laps) {
        this.laps = laps;
    }

    @JsonProperty("team")
    public Team getTeam() {
        return team;
    }

    @JsonProperty("team")
    public void setTeam(Team team) {
        this.team = team;
    }

    @JsonProperty("carNumber")
    public String getCarNumber() {
        return carNumber;
    }

    @JsonProperty("carNumber")
    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    @JsonProperty("bestLap")
    public BestLap getBestLap() {
        return bestLap;
    }

    @JsonProperty("bestLap")
    public void setBestLap(BestLap bestLap) {
        this.bestLap = bestLap;
    }

    @JsonProperty("nationality")
    public Nationality getNationality() {
        return nationality;
    }

    @JsonProperty("nationality")
    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    @JsonProperty("gap")
    public Gap getGap() {
        return gap;
    }

    @JsonProperty("gap")
    public void setGap(Gap gap) {
        this.gap = gap;
    }

    @JsonProperty("time")
    public Integer getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(Integer time) {
        this.time = time;
    }

    @JsonProperty("avgLapSpeed")
    public Double getAvgLapSpeed() {
        return avgLapSpeed;
    }

    @JsonProperty("avgLapSpeed")
    public void setAvgLapSpeed(Double avgLapSpeed) {
        this.avgLapSpeed = avgLapSpeed;
    }

    @JsonProperty("pitlane")
    public Object getPitlane() {
        return pitlane;
    }

    @JsonProperty("pitlane")
    public void setPitlane(Object pitlane) {
        this.pitlane = pitlane;
    }

    @JsonProperty("drivers")
    public List<Driver> getDrivers() {
        return drivers;
    }

    @JsonProperty("drivers")
    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
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
