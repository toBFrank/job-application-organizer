package com.example.jobmanager.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "resume")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_master")
    private boolean isMaster;

}
