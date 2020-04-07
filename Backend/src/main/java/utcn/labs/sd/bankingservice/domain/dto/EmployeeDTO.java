package utcn.labs.sd.bankingservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import utcn.labs.sd.bankingservice.domain.data.entity.Account;
import utcn.labs.sd.bankingservice.domain.data.entity.Activity;
import utcn.labs.sd.bankingservice.domain.data.entity.enums.EmployeeType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {

    private String username;
    private String password;
    private EmployeeType type;
    private String name;
    private String telephone;
    private String address;
    private String hiring_date;
    private List<Activity> activityList;

    @JsonCreator
    public EmployeeDTO(@JsonProperty("username") String username,
                       @JsonProperty("password") String password,
                       @JsonProperty("type") EmployeeType type,
                       @JsonProperty("name") String name,
                       @JsonProperty("telephone") String telephone,
                       @JsonProperty("address") String address,
                       @JsonProperty("hiring_date") String hiring_date,
                       @JsonProperty("activityList") List<Activity> activityList)

    {
        this.username = username;
        this.password = password;
        this.type = type;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.hiring_date = hiring_date;
        this.activityList = activityList;
    }

    @JsonProperty("username")
    @NotNull
    public String getUsername() {
        return username;
    }

    @JsonProperty("password")
    @NotNull
    public String getPassword() {
        return password;
    }

    @JsonProperty("type")
    @NotNull
    public EmployeeType getType() {
        return type;
    }

    @JsonProperty("name")
    @NotNull
    public String getName() {
        return name;
    }

    @JsonProperty("telephone")
    @Size(min = 10, max = 10,  message = "Telephone number must have exactly 10 digits")
    @Pattern(regexp = "[0-9]*")
    public String getTelephone() {
        return telephone;
    }

    @JsonProperty("address")
    @NotNull
    public String getAddress() {
        return address;
    }

    @JsonProperty("hiring_date")
    @NotNull
    public String getHiring_date() {
        return hiring_date;
    }

    @JsonProperty("activityList")
    public List<Activity> getActivityList() {
        return activityList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmployeeDto{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", hiring_date=").append(hiring_date).append('\'');
        sb.append(", activityList=").append(activityList);
        sb.append('}');
        return sb.toString();
    }

}
