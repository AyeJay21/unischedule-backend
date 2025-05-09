package com.example.unischedule.timetable;


import com.example.unischedule.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
@CrossOrigin(origins = " * ", allowedHeaders = " * ")
@Table(name = "timetable_entries")
public class TimeTableEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    private String day;

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    @NotBlank
    private String subject;

    private String color;
    private String colorType;

    // Getter und Setter hinzufügen
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public TimeTableEntry() {
    }

    public TimeTableEntry(User user, String day, String startTime, String endTime,
                          String subject, String color, String colorType) {
        this.user = user;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.color = color;
        this.colorType = colorType;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
