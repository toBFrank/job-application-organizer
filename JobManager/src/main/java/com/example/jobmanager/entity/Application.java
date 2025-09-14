package com.example.jobmanager.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String company;

    @Column
    private String status;

    // One application can have many interviews
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Interviews> interviews = new ArrayList<>();

    // Constructors
    public Application() {
        // Default constructor required by JPA
    }

    public Application(final String title, final String company, final String status) {
        this.title = title;
        this.company = company;
        this.status = status;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getStatus() { return status; }

    public List<Interviews> getInterviews() {
        return interviews;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setStatus(String status) { this.status = status; }

    public void setInterviews(List<Interviews> interviews) {
        this.interviews = interviews;
    }

    // Utility methods
    public void addInterview(Interviews interview) {
        interviews.add(interview);
        interview.setApplication(this);
    }

    public void removeInterview(Interviews interview) {
        interviews.remove(interview);
        interview.setApplication(null);
    }
}