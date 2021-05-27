package edu.ntnu.bsc020backend.models;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

@Entity
public class ShiftSchedule {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private LocalDate startDate;

    @ManyToMany
    private List<Employee> employees;

    @OneToMany
    @Fetch(value = FetchMode.SELECT)
    private List<Shift> shifts;

    @Transient
    @ApiModelProperty(hidden = true)
    List<Solution> solutions;

    public ShiftSchedule(){
    }

    public ShiftSchedule(LocalDate startDate, List<Employee> employees, List<Shift> shifts, List<Solution> solutions) {
        this.startDate = startDate;
        this.employees = employees;
        this.shifts = shifts;
        this.solutions = solutions;
    }

    public ShiftSchedule(LocalDate startDate, List<Employee> employees, List<Shift> shifts) {
        this.startDate = startDate;
        this.employees = employees;
        this.shifts = shifts;
        this.solutions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee getEmployeeById(int id){
        for (Employee employee: employees) {
            if (employee.getId() == id){
                return employee;
            }
        }
        return null;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

    public void addSolution(Solution solution){
        solutions.add(solution);
    }

    public boolean updateSchedule(Solution solution) {
        if(solution.size() != shifts.size()){
            return false;
        }
        List<Integer> temp = solution.getSolution();
        for (int i = 0; i < temp.size(); i++) {
            shifts.get(i).setEmployee(employees.get(temp.get(i)));
        }
        return true;
    }


        @Override
    public String toString() {
        return "ShiftSchedule{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", employees=" + employees +
                ", shifts=" + shifts +
                '}';
    }
}
