package com.example.unischedule.timetable;

import jakarta.validation.constraints.NotBlank;

public class TimeTableEntryRequest {
    @NotBlank
    private String day;

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    @NotBlank
    private String subject;

    private String color;     // Neu
    private String colorType; // Neu

    // Getter und Setter hinzufügen
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getColorType() { return colorType; }
    public void setColorType(String colorType) { this.colorType = colorType; }

    // Getter und Setter
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
