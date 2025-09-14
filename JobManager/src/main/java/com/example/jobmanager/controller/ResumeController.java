package com.example.jobmanager.controller;

import com.example.jobmanager.entity.Resume;
import com.example.jobmanager.repository.ResumeRepository;
import com.example.jobmanager.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeRepository repository, ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    // Get all resumes
    @GetMapping
    public List<Resume> getAllResumes() {
        return resumeService.getAllResumes();
    }

    // Get a resume by id
    @GetMapping("/{id}")
    public Resume getResumeById(@PathVariable Long id) {
        return resumeService.getResumeById(id);
    }

    // Create new Master Resume
    @PostMapping
    public Resume save(@RequestBody Resume resume) {
        return resumeService.createMasterResume(resume);
    }

    // Create a tailored resume from the Master Resume
    @PostMapping("/tailor/{id}")
    public Resume createTailoredResume(@PathVariable Long id, @RequestBody(required = false) Resume overrides) {
        return resumeService.createTailoredResume(id, overrides);
    }

    // Remove Master Resume by resume ID
    @DeleteMapping("/{id}")
    public String deleteInterview(@PathVariable Long id) {
        return resumeService.deleteResume(id);
    }

    // Update resume
    @PutMapping("/{id}")
    public Resume updateResume(@PathVariable Long id, @RequestBody Resume updatedResume) {
        return resumeService.updateResume(id, updatedResume);
    }


}
