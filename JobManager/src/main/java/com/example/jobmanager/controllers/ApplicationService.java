package com.example.jobmanager.controllers;

import com.example.jobmanager.entity.Application;
import com.example.jobmanager.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applications") // Base URL for all endpoints
public class ApplicationService {

    @Autowired
    private ApplicationRepository repository;

    // ================= CREATE =================
    @PostMapping
    public Application createApplication(@RequestBody Application application) {
        return repository.save(application);
    }

    // ================= READ ALL =================
    @GetMapping
    public List<Application> getAllApplications() {
        return repository.findAll();
    }

    // ================= READ BY ID =================
    @GetMapping("/{id}")
    public Application getApplicationById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public Application updateApplication(@PathVariable Long id, @RequestBody Application updatedApp) {
        return repository.findById(id).map(application -> {
            application.setTitle(updatedApp.getTitle());
            application.setCompany(updatedApp.getCompany());
            application.setRejected(updatedApp.getRejected());
            application.setJobOffer(updatedApp.getJobOffer());
            return repository.save(application);
        }).orElse(null);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Deleted application with ID: " + id;
        } else {
            return "Application not found with ID: " + id;
        }
    }

    // ================= EXTRA FILTERS =================
    // Search by company
    @GetMapping("/search/company")
    public List<Application> searchByCompany(@RequestParam String company) {
        return repository.findByCompanyContainingIgnoreCase(company);
    }

    // Search by title
    @GetMapping("/search/title")
    public List<Application> searchByTitle(@RequestParam String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    // Get rejected applications
    @GetMapping("/rejected")
    public List<Application> getRejectedApplications() {
        return repository.findByRejectedTrue();
    }

    // Get applications with job offers
    @GetMapping("/job-offers")
    public List<Application> getJobOfferApplications() {
        return repository.findByJobOfferTrue();
    }

    // Get applications with interviews
    @GetMapping("/with-interviews")
    public List<Application> getApplicationsWithInterviews() {
        return repository.findApplicationsWithInterviews();
    }
}



