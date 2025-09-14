package com.example.jobmanager.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Interview")
public class Interviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many interviews belong to one application
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Enumerated(EnumType.STRING)
    @Column(name = "interview_type", nullable = false)
    private InterviewType interviewType;

    @Column(nullable = false)
    private Integer round = 1;

    @Column(name = "interview_date")
    private LocalDateTime interviewDate;

    @Column(name = "interview_status")
    private String interviewStatus = "SCHEDULED"; // SCHEDULED, COMPLETED, CANCELLED

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column
    private String location;

    @Column(name = "interviewer_name")
    private String interviewerName;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    // Constructors
    public Interviews() {
        // Default constructor required by JPA
    }

    public Interviews(Application application, InterviewType interviewType, Integer round) {
        this.application = application;
        this.interviewType = interviewType;
        this.round = round;
        this.interviewStatus = "SCHEDULED";
    }

    public Interviews(Application application, InterviewType interviewType, Integer round, LocalDateTime interviewDate) {
        this.application = application;
        this.interviewType = interviewType;
        this.round = round;
        this.interviewDate = interviewDate;
        this.interviewStatus = "SCHEDULED";
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Application getApplication() {
        return application;
    }

    public InterviewType getInterviewType() {
        return interviewType;
    }

    public Integer getRound() {
        return round;
    }

    public LocalDateTime getInterviewDate() {
        return interviewDate;
    }

    public String getInterviewStatus() {
        return interviewStatus;
    }

    public String getNotes() {
        return notes;
    }

    public String getLocation() {
        return location;
    }

    public String getInterviewerName() {
        return interviewerName;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void setInterviewType(InterviewType interviewType) {
        this.interviewType = interviewType;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public void setInterviewDate(LocalDateTime interviewDate) {
        this.interviewDate = interviewDate;
    }

    public void setInterviewStatus(String interviewStatus) {
        this.interviewStatus = interviewStatus;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setInterviewerName(String interviewerName) {
        this.interviewerName = interviewerName;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    // Utility methods
    public void complete(String notes) {
        this.interviewStatus = "COMPLETED";
        this.notes = notes;
    }

    public void cancel() {
        this.interviewStatus = "CANCELLED";
    }

    public void reschedule(LocalDateTime newDate) {
        this.interviewDate = newDate;
        this.interviewStatus = "SCHEDULED";
    }

    @Override
    public String toString() {
        return "Interview{" +
                "id=" + id +
                ", interviewType=" + interviewType +
                ", round=" + round +
                ", interviewDate=" + interviewDate +
                ", interviewStatus='" + interviewStatus + '\'' +
                '}';
    }
}

