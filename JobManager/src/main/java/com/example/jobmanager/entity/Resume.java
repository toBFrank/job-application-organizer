package com.example.jobmanager.entity;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resume")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "resume")
    private List<Application> applications;

    @Column
    private boolean isMaster;

    @Column
    private String fileName;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String portfolioURL;

    @Lob
    @Column(name = "technical_skills")
    private String technicalSkills;

    @Lob
    @Column(name = "work_experience")
    private String workExperience;

    @Lob
    @Column(name = "education")
    private String education;

    @Lob
    @Column(name = "projects")
    private String projects;
}
