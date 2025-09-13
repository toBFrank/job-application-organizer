package com.example.jobmanager.service;

import com.example.jobmanager.entity.Application;
import com.example.jobmanager.exception.ApplicationNotFoundException;
import com.example.jobmanager.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@Transactional
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(final ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    // CREATE - Save new application with validation
    public Application createApplication(final Application application) {
        validateApplicationData(application);

        if (application.getRejected() == null) {
            application.setRejected(false);
        }
        if (application.getJobOffer() == null) {
            application.setJobOffer(false);
        }

        return applicationRepository.save(application);
    }

    // READ - Get all applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // READ - Get application by ID
    public Optional<Application> getApplicationById(final Long id) {
        return applicationRepository.findById(id);
    }

    // UPDATE - Update existing application
    public Application updateApplication(final Long id, final Application updatedApplication) throws Exception {
        return applicationRepository.findById(id)
                .map(existingApp -> {
                    validateApplicationData(updatedApplication);

                    // Update fields
                    existingApp.setTitle(updatedApplication.getTitle());
                    existingApp.setCompany(updatedApplication.getCompany());
                    existingApp.setRejected(updatedApplication.getRejected());
                    existingApp.setJobOffer(updatedApplication.getJobOffer());

                    return applicationRepository.save(existingApp);
                })
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + id));
    }

    // DELETE - Delete application
    public void deleteApplication(final Long id) throws Exception {
        if (!applicationRepository.existsById(id)) {
            throw new ApplicationNotFoundException("Application not found with id: " + id);
        }
        applicationRepository.deleteById(id);
    }

    // SEARCH METHODS
    public List<Application> findByCompany(final String company) {
        return applicationRepository.findByCompanyContainingIgnoreCase(company);
    }

    public List<Application> findByTitle(final String title) {
        return applicationRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Application> findRejectedApplications() {
        return applicationRepository.findByRejectedTrue();
    }

    public List<Application> findApplicationsWithJobOffers() {
        return applicationRepository.findByJobOfferTrue();
    }

    public List<Application> findApplicationsWithInterviews() {
        return applicationRepository.findApplicationsWithInterviews();
    }

    public Application markAsRejected(final Long id) {
        return applicationRepository.findById(id)
                .map(app -> {
                    app.setRejected(true);
                    app.setJobOffer(false); // Can't have both
                    return applicationRepository.save(app);
                })
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + id));
    }

    public Application markAsJobOffer(final Long id) {
        return applicationRepository.findById(id)
                .map(app -> {
                    app.setJobOffer(true);
                    app.setRejected(false); // Can't have both
                    return applicationRepository.save(app);
                })
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + id));
    }

    // VALIDATION HELPER METHOD
    private void validateApplicationData(final Application application) {
        if (application.getTitle() == null || application.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Job title cannot be empty");
        }

        if (application.getCompany() == null || application.getCompany().trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be empty");
        }

        if (application.getTitle().length() > 255) {
            throw new IllegalArgumentException("Job title cannot exceed 255 characters");
        }

        if (application.getCompany().length() > 255) {
            throw new IllegalArgumentException("Company name cannot exceed 255 characters");
        }

        // Can't be both rejected and have job offer
        if (Boolean.TRUE.equals(application.getRejected()) && Boolean.TRUE.equals(application.getJobOffer())) {
            throw new IllegalArgumentException("Application cannot be both rejected and have a job offer");
        }
    }
}
