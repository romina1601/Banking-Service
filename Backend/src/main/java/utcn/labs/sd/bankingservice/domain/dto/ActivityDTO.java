package utcn.labs.sd.bankingservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityDTO {

    private final int activityId;
    private final String username;
    private final String activityName;
    private final String timestamp;
    private final String description;


    @JsonCreator
    public ActivityDTO(@JsonProperty("activityId") int activityId,
                      @JsonProperty("username") String username,
                      @JsonProperty("acitvityName") String activityName,
                      @JsonProperty("timestamp") String timestamp,
                      @JsonProperty("description") String description) {

        this.activityId = activityId;
        this.username = username;
        this.activityName = activityName;
        this.timestamp = timestamp;
        this.description = description;
    }

    @JsonProperty("activityId")
    public int getActivityId() {
        return activityId;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("activityName")
    public String getActivityName() {
        return activityName;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AcitivtyDto{");
        sb.append("activityId='").append(activityId).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", activityName='").append(activityName).append('\'');
        sb.append(", timestamp='").append(timestamp).append('\'');
        sb.append(", description='").append(description);
        sb.append('}');
        return sb.toString();
    }
}
