package utcn.labs.sd.bankingservice.domain.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "activity_table")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_id")
    private int activityId;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "activity_name")
    @NotNull
    private String activityName;

    @Column(name = "timestamp")
    @NotNull
    private String timestamp;

    @Column(name = "description")
    @Size(max = 150, message = "Description must contain most 150 characters")
    private String description;


    public Activity() {

    }

    public Activity( String username, String activityName, String timestamp, String description) {
        this.username = username;
        this.activityName = activityName;
        this.timestamp = timestamp;
        this.description = description;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Activity{");
        sb.append("activityId='").append(activityId).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", activityName='").append(activityName).append('\'');
        sb.append(", timestamp='").append(timestamp).append('\'');
        sb.append(", description='").append(description);
        sb.append('}');
        return sb.toString();
    }

    public String toStringForCSV()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(activityId).append(",").append(username).append(",").append(activityName).append(",")
                .append(timestamp).append(",").append(description);

        return sb.toString();
    }
}
