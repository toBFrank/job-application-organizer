package com.example.jobmanager.controller;

import com.example.jobmanager.entity.Application;
import com.example.jobmanager.service.ApplicationService;
import com.example.jobmanager.exception.ApplicationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(final ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<Application> createApplication(final @RequestBody Application application) {
        try {
            Application createdApp = applicationService.createApplication(application);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdApp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ================= READ ALL =================
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }

    // ================= READ BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(final @PathVariable Long id) {
        try {
            return applicationService.getApplicationById(id)
                    .map(application -> ResponseEntity.ok(application))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateApplication(final @PathVariable Long id,
                                               final @RequestBody Application updatedApp) {
        try {
            Application updated = applicationService.updateApplication(id, updatedApp);
            return ResponseEntity.ok(updated);
        } catch (ApplicationNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(final @PathVariable Long id) {
        try {
            applicationService.deleteApplication(id);
            return ResponseEntity.ok("Successfully deleted application with ID: " + id);
        } catch (ApplicationNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    // ================= SEARCH ENDPOINTS =================
//    @GetMapping("/search/company")
//    public ResponseEntity<List<Application>> searchByCompany(final @RequestParam String company) {
//        try {
//            List<Application> applications = applicationService.findByCompany(company);
//            return ResponseEntity.ok(applications);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    @GetMapping("/search/title")
//    public ResponseEntity<List<Application>> searchByTitle(final @RequestParam String title) {
//        try {
//            List<Application> applications = applicationService.findByTitle(title);
//            return ResponseEntity.ok(applications);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

//    @GetMapping("/rejected")
//    public ResponseEntity<List<Application>> getRejectedApplications() {
//        List<Application> rejectedApps = applicationService.findRejectedApplications();
//        return ResponseEntity.ok(rejectedApps);
//    }
//
//    @GetMapping("/job-offers")
//    public ResponseEntity<List<Application>> getJobOfferApplications() {
//        List<Application> jobOfferApps = applicationService.findApplicationsWithJobOffers();
//        return ResponseEntity.ok(jobOfferApps);
//    }
//
//    @GetMapping("/with-interviews")
//    public ResponseEntity<List<Application>> getApplicationsWithInterviews() {
//        List<Application> appsWithInterviews = applicationService.findApplicationsWithInterviews();
//        return ResponseEntity.ok(appsWithInterviews);
//    }

    // ================= EXCEPTION HANDLING =================
    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<String> handleApplicationNotFound(ApplicationNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(final IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + e.getMessage());
    }
}
