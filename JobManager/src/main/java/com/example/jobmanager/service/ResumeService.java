package com.example.jobmanager.service;

import com.example.jobmanager.entity.Resume;
import com.example.jobmanager.repository.ResumeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Service
@Validated
@Transactional

public class ResumeService {

    private final ResumeRepository resumeRepository;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    // Create - Save new MASTER resume with validation
    public Resume createMasterResume(final Resume resume) {
        resume.setMaster(true); // setMaster boolean to True
        return resumeRepository.save(resume);
    }

    // Create - Save new TAILORED resume with validation
    public Resume createTailoredResume(Long masterResumeId, Resume overrides) {
        Resume masterResume = resumeRepository.findById(masterResumeId)
                .orElseThrow(() -> new IllegalArgumentException("No resume with ID " + masterResumeId + " found"));

        Resume tailoredResume = new Resume();

        tailoredResume.setMaster(false); // This is a tailored resume so we set master to false

        tailoredResume.setMasterResume(masterResume);

        tailoredResume.setName(masterResume.getName());
        tailoredResume.setEmail(masterResume.getEmail());
        tailoredResume.setFileName(masterResume.getFileName());
        tailoredResume.setPortfolioURL(masterResume.getPortfolioURL());
        tailoredResume.setTechnicalSkills(masterResume.getTechnicalSkills());
        tailoredResume.setWorkExperience(masterResume.getWorkExperience());
        tailoredResume.setEducation(masterResume.getEducation());
        tailoredResume.setProjects(masterResume.getProjects());
        // Apply overrides if provided
        if (overrides != null) {
            if (overrides.getName() != null) tailoredResume.setName(overrides.getName());
            if (overrides.getEmail() != null) tailoredResume.setEmail(overrides.getEmail());
            if (overrides.getFileName() != null) tailoredResume.setFileName(overrides.getFileName());
            if (overrides.getPortfolioURL() != null) tailoredResume.setPortfolioURL(overrides.getPortfolioURL());
            if (overrides.getTechnicalSkills() != null) tailoredResume.setTechnicalSkills(overrides.getTechnicalSkills());
            if (overrides.getWorkExperience() != null) tailoredResume.setWorkExperience(overrides.getWorkExperience());
            if (overrides.getEducation() != null) tailoredResume.setEducation(overrides.getEducation());
            if (overrides.getProjects() != null) tailoredResume.setProjects(overrides.getProjects());
        }

        // validateResumeData(tailoredResume); // Optional: reuse validation logic
        return resumeRepository.save(tailoredResume);
    }

    // Read - get all Resumes
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    // Read - get Resume by ID
    public Resume getResumeById(Long id) {
        return resumeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No resume with ID " + id + " found"));
    }

    // Update - update resume
    public Resume updateResume(Long id, Resume updatedResume) {
        return resumeRepository.findById(id).map(resume -> {
            resume.setFileName(updatedResume.getFileName());
            resume.setName(updatedResume.getName());
            resume.setEmail(updatedResume.getEmail());
            resume.setPortfolioURL(updatedResume.getPortfolioURL());
            resume.setTechnicalSkills(updatedResume.getTechnicalSkills());
            resume.setWorkExperience(updatedResume.getWorkExperience());
            resume.setEducation(updatedResume.getEducation());
            resume.setProjects(updatedResume.getProjects());
            resume.setTitle(updatedResume.getTitle());
            resume.setStatus(updatedResume.getStatus());
            return resumeRepository.save(resume);
        }).orElseThrow(() -> new IllegalArgumentException("No resume with ID " + id + " found"));
    }
    // Delete - delete resume
    public String deleteResume(Long id) {
        if  (resumeRepository.findById(id).isPresent()) {
            resumeRepository.deleteById(id);
            return "Resume with ID " + id + " deleted";
        } else {
            return "Resume with ID " + id + " not found";
        }
    }

}