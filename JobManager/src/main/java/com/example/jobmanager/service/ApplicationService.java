package com.example.jobmanager.service;

import com.example.jobmanager.entity.Application;
import com.example.jobmanager.entity.ApplicationStatus;
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
        
        // Set default status if not provided
        if (application.getStatus() == null) {
            application.setStatus(ApplicationStatus.APPLIED.name());
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

    // UPDATE - Update existing application with status validation
    public Application updateApplication(final Long id, final Application updatedApplication) throws Exception {
        return applicationRepository.findById(id)
                .map(existingApp -> {
                    validateApplicationData(updatedApplication);
                    validateStatus(updatedApplication.getStatus());

                    // Update fields
                    existingApp.setTitle(updatedApplication.getTitle());
                    existingApp.setCompany(updatedApplication.getCompany());
                    existingApp.setStatus(updatedApplication.getStatus());

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

    public List<Application> findApplicationsWithInterviews() {
        return applicationRepository.findApplicationsWithInterviews();
    }

    // VALIDATION HELPER METHODS
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
    }

    private void validateStatus(final String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }

        if (!ApplicationStatus.isValidStatus(status)) {
            throw new IllegalArgumentException(
                "Invalid status: " + status + ". Allowed statuses are: " + ApplicationStatus.getAllowedStatuses()
            );
        }
    }
}
