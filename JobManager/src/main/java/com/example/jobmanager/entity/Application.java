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
    private Boolean rejected;

    @Column(name = "job_offer")
    private Boolean jobOffer;

    // Many applications can use one resume
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    // One application can have many interviews
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Interviews> interviews = new ArrayList<>();

    // Constructors
    public Application() {
        // Default constructor required by JPA
    }

    public Application(String title, String company) {
        this.title = title;
        this.company = company;
        this.rejected = false;
        this.jobOffer = false;
    }

    public Application(String title, String company, Boolean rejected, Boolean jobOffer) {
        this.title = title;
        this.company = company;
        this.rejected = rejected;
        this.jobOffer = jobOffer;
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

    public Boolean getRejected() {
        return rejected;
    }

    public Boolean getJobOffer() {
        return jobOffer;
    }

    public Resume getResume() {
        return resume;
    }

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

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

    public void setJobOffer(Boolean jobOffer) {
        this.jobOffer = jobOffer;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

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