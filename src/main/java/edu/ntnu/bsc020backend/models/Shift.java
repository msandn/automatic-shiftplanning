package edu.ntnu.bsc020backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Shift extends BaseEntity implements Comparable<Shift>{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(example = "2021-05-19 09:00:00")
    private LocalDateTime startTime;
    private int durationMin;
    private int skillId;
    @ApiModelProperty(hidden = true)
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    public Shift(){
    }

    public Shift(LocalDateTime startTime, int durationMin, int skillId, Employee employee) {
        this.startTime = startTime;
        this.durationMin = durationMin;
        this.skillId = skillId;
        this.employee = employee;
    }
    public Shift(LocalDateTime startTime, int durationMin, int skillId) {
        this.startTime = startTime;
        this.durationMin = durationMin;
        this.skillId = skillId;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        String employeeId;
        if (employee!=null){
            employeeId = "id: " + employee.getId();
        } else {
            employeeId = "none";
        }
        return "Shift{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", durationMin=" + durationMin +
                ", skillId=" + skillId +
                ", employee=" + employeeId +
                '}';
    }

    @Override
    public int compareTo(Shift o) {
        return this.getStartTime().compareTo(o.getStartTime());
    }
}
