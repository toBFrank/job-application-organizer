package com.example.jobmanager.repository;

import com.example.jobmanager.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    // find master resume by its ID
    List<Resume> findByMasterResumeId(Long masterResumeId);
    // Find the current master resume (should be at most one)
    List<Resume> findByStatus(String status);
    // find all resumes by title containing a string (case insensitive)
    List<Resume> findByTitleContainingIgnoreCase(String title);
}