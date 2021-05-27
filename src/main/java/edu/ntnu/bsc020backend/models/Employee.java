package edu.ntnu.bsc020backend.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Type(type = "int-array")
    @Column(name ="skill_ids",
            columnDefinition = "array")
    private int[] skillIds = new int[]{0};

    private int workingHoursInWeek = 40;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(example = "[\"2021-08-20\", \"2021-08-21\", \"2021-08-22\", \"2021-08-23\"]")
    @Type(type = "date-array")
    @Column(
            name = "holiday_days",
            columnDefinition = "array"
    )
    private Date[] holidayDays = new Date[0];

   // private LocalDate[] holidayDays;


    public Employee() {
    }

    public Employee(int[] skillIds, int workingHoursInWeek, Date[] holidayDays) {
        if (skillIds != null) this.skillIds = skillIds;
        if(holidayDays != null) this.holidayDays = holidayDays;
        this.workingHoursInWeek = workingHoursInWeek;
    }

    public Employee(int id) {
        this.id = id;
        holidayDays = new Date[0];
    }


    public int getId() {
        return id;
    }

    public int[] getSkillIds() {
        return skillIds;
    }

    public int getWorkingHoursInWeek() {
        return workingHoursInWeek;
    }

    public void setSkillIds(int[] skillIds) {
        this.skillIds = skillIds;
    }

    public void setWorkingHoursInWeek(int workingHoursInWeek) {
        this.workingHoursInWeek = workingHoursInWeek;
    }

    public  Date[] getHolidayDays() {
        return holidayDays;
    }

    public void setHolidayDays( Date[] holidayDays) {
        this.holidayDays = holidayDays;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", skillIds=" + Arrays.toString(skillIds) +
                ", holidayDays=" + Arrays.toString(holidayDays) +
                '}';
    }
}
