package edu.ntnu.bsc020backend;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Since java lacks a time period class we made a custom one for use in this project.
 * Simply contains a start and end date, and then calculates the time inbetween.
 */
public class LocalDateTimeInterval {
    private LocalDateTime start;
    private LocalDateTime end;
    private long durationSec;

    /**
     * @param start The start date
     * @param end The end date
     * The time between gets calculated automatically
     */
    public LocalDateTimeInterval(LocalDateTime start, LocalDateTime end){
        this.start = start;
        this.end = end;
        setDurationBetween();
    }

    /**
     * @return Returns the start date
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * @param start Sets the start date
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
        setDurationBetween();
    }

    /**
     * @return Returns the end date
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * @param end Sets the end date
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
        setDurationBetween();
    }

    /**
     * @return Gets the duration between the dates in seconds
     */
    public long getDurationSec(){
        return durationSec;
    }

    /**
     * @return Gets the duration between the dates in minutes
     */
    public long getDurationMin(){
        return durationSec/60;
    }

    /**
     * @return Gets the duration between the dates in hours
     */
    public long getDurationHour(){
        return durationSec/3600;
    }

    /**
     * @return Gets the duration between the dates in days
     */
    public long getDurationDays(){
        return durationSec/86400;
    }

    /**
     * Helper method to get the time between 2 local date times
     */
    private void setDurationBetween(){
        durationSec = ChronoUnit.SECONDS.between(start, end);
    }
}
