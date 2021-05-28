package edu.ntnu.bsc020backend.models;

import java.time.LocalDate;

/**
 * Class that containts the settings for the genetic algorithm to run
 */
public class GASettings {
    //What the algorithm should take height for
    private boolean percentageOfEmployments = false;
    private boolean maxWorkPerDay = false;
    private boolean maxWorkPerWeek = false;
    private boolean customWorkPerK = false;
    private boolean overlap = true;

    //Settings for the algorithm
    private int overlapValue = 0;
    private int employmentValue = 8;
    private boolean employmentOverworkAllowed = false;

    //Domain related settings
    private boolean holidays = true;
    private boolean pastDate = true;
    private boolean skills = true;

    //Working hours for regulation checking
    private int workingMinPerDay = 9*60;
    private int workingMinPerWeek = 40*60;
    private int customWorkMin = 0;
    private int customWorkDays = 0;

    //Today
    private LocalDate today = LocalDate.of(1998, 10, 6);


    public GASettings(){
    }

    public boolean isOverlap() {
        return overlap;
    }

    public void setOverlap(boolean overlap) {
        this.overlap = overlap;
    }

    public int getEmploymentValue() {
        return employmentValue;
    }

    public void setEmploymentValue(int employmentValue) {
        this.employmentValue = employmentValue;
    }

    public boolean isEmploymentOverworkAllowed() {
        return employmentOverworkAllowed;
    }

    public void setEmploymentOverworkAllowed(boolean employmentOverworkAllowed) {
        this.employmentOverworkAllowed = employmentOverworkAllowed;
    }

    public int getOverlapValue() {
        return overlapValue;
    }

    public void setOverlapValue(int overlapValue) {
        this.overlapValue = overlapValue;
    }

    public LocalDate getToday() {
        return today;
    }

    public void setToday(LocalDate today) {
        this.today = today;
    }

    public boolean isPercentageOfEmployments() {
        return percentageOfEmployments;
    }

    public void setPercentageOfEmployments(boolean percentageOfEmployments) {
        this.percentageOfEmployments = percentageOfEmployments;
    }

    public boolean isMaxWorkPerDay() {
        return maxWorkPerDay;
    }

    public void setMaxWorkPerDay(boolean maxWorkPerDay) {
        this.maxWorkPerDay = maxWorkPerDay;
    }

    public boolean isMaxWorkPerWeek() {
        return maxWorkPerWeek;
    }

    public void setMaxWorkPerWeek(boolean maxWorkPerWeek) {
        this.maxWorkPerWeek = maxWorkPerWeek;
    }

    public boolean isCustomWorkPerK() {
        return customWorkPerK;
    }

    public void setCustomWorkPerK(boolean customWorkPerK) {
        this.customWorkPerK = customWorkPerK;
    }

    public boolean isHolidays() {
        return holidays;
    }

    public void setHolidays(boolean holidays) {
        this.holidays = holidays;
    }

    public boolean isPastDate() {
        return pastDate;
    }

    public void setPastDate(boolean pastDate) {
        this.pastDate = pastDate;
    }

    public boolean isSkills() {
        return skills;
    }

    public void setSkills(boolean skills) {
        this.skills = skills;
    }

    public int getWorkingMinPerDay() {
        return workingMinPerDay;
    }

    public void setWorkingMinPerDay(int workingMinPerDay) {
        this.workingMinPerDay = workingMinPerDay;
    }

    public int getWorkingMinPerWeek() {
        return workingMinPerWeek;
    }

    public void setWorkingMinPerWeek(int workingMinPerWeek) {
        this.workingMinPerWeek = workingMinPerWeek;
    }

    public int getCustomWorkMin() {
        return customWorkMin;
    }

    public void setCustomWorkMin(int customWorkMin) {
        this.customWorkMin = customWorkMin;
    }

    public int getCustomWorkDays() {
        return customWorkDays;
    }

    public void setCustomWorkDays(int customWorkDays) {
        this.customWorkDays = customWorkDays;
    }
}
