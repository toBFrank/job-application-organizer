package com.example.jobmanager.entity;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resume")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isMaster;

    // if tailored resume, reference to master resume
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_resume_id")
    private Resume masterResume;

    @Column
    private String title;

    @Column
    private String status;

    @Column
    private String fileName;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String portfolioURL;

    @Column(name = "technical_skills")
    private String technicalSkills;

    @Column(name = "work_experience")
    private String workExperience;

    @Column(name = "education")
    private String education;

    @Column(name = "projects")
    private String projects;
}