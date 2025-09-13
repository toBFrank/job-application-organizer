package com.example.jobmanager.controller;

import com.example.jobmanager.entity.Resume;
import com.example.jobmanager.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeRepository repository;

    @Autowired
    public ResumeController(ResumeRepository repository) {
        this.repository = repository;
    }

    // Create new Master Resume
    @PostMapping
    public Resume save(@RequestBody Resume resume) {
        return repository.save(resume);
    }

    // Create a tailored resume from the Master Resume
    @PostMapping("/tailor/{id}")
    public Resume createTailoredResume(@PathVariable Long id, @RequestBody(required = false) Resume overrides) {
        return repository.findById(id).map(masterResume -> {
            Resume tailoredResume = new Resume();

            // Copy fields from master
            tailoredResume.setName(masterResume.getName());
            tailoredResume.setEmail(masterResume.getEmail());
            tailoredResume.setFileName(masterResume.getFileName());
            tailoredResume.setPortfolioURL(masterResume.getPortfolioURL());
            tailoredResume.setTechnicalSkills(masterResume.getTechnicalSkills());
            tailoredResume.setWorkExperience(masterResume.getWorkExperience());
            tailoredResume.setEducation(masterResume.getEducation());
            tailoredResume.setProjects(masterResume.getProjects());

            // Apply any overrides
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
            return repository.save(tailoredResume);
        }).orElse(null);
    }

    // Remove Master Resume by resume ID
    @DeleteMapping("/{id}")
    public String deleteInterview(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Deleted Interview with id: " + id;
        } else {
            return "Interview with id: " + id + " not found";
        }
    }

    // Update master resume
    @PutMapping("/{id}")
    public Resume updateMasterResume(@PathVariable Long id, @RequestBody Resume updatedMasterResume) {
        return repository.findById(id).map(resume -> {
            resume.setFileName(updatedMasterResume.getFileName());
            resume.setName(updatedMasterResume.getName());
            resume.setEmail(updatedMasterResume.getEmail());
            resume.setPortfolioURL(updatedMasterResume.getPortfolioURL());
            resume.setTechnicalSkills(updatedMasterResume.getTechnicalSkills());
            resume.setWorkExperience(updatedMasterResume.getWorkExperience());
            resume.setEducation(updatedMasterResume.getEducation());
            resume.setProjects(updatedMasterResume.getProjects());
            return repository.save(resume);
        }).orElse(null);
    }


}
