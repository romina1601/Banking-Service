package utcn.labs.sd.bankingservice.domain.data.entity;


import utcn.labs.sd.bankingservice.domain.data.entity.enums.EmployeeType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "employee_table")

public class Employee
{
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EmployeeType type;

    @Column(name = "name")
    private String name;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "address")
    private String address;

    @Column(name = "hiring_date")
    private String hiring_date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private List<Activity> activityList;

    public Employee() {
    }

    public Employee(String username, String password, EmployeeType type, String name,
                    String telephone, String address, String hiring_date, List<Activity> activityList) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.hiring_date = hiring_date;
        this.activityList = activityList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHiring_date() {
        return hiring_date;
    }

    public void setHiring_date(String hiring_date) {
        this.hiring_date = hiring_date;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", hiring_date=").append(hiring_date);
        sb.append(", activityList=").append(activityList);
        sb.append('}');
        return sb.toString();
    }
}