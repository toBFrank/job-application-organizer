package com.example.jobmanager.controller;

import com.example.jobmanager.entity.Interviews;
import com.example.jobmanager.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviews")
public class InterviewsController {

    private final InterviewRepository repository;

    @Autowired
    public InterviewsController(InterviewRepository repository) {
        this.repository = repository;
    }

    // Create new Interview
    @PostMapping
    public Interviews createInterview(@RequestBody Interviews interview) {
        return repository.save(interview);
    }

    // Get all interviews
    @GetMapping
    public List<Interviews> getAllInterviews() {
        return repository.findAll();
    }

    // Get Interview by ID
    @GetMapping("/{id}")
    public ResponseEntity<Interviews> getInterviewById(@PathVariable Long id) {
        try {
            // Attempt to find the interview by ID from the repository
            return repository.findById(id)
                    // If found, return the interview wrapped in a 200 OK response
                    .map(ResponseEntity::ok)
                    // If not found, return a 404 Not Found response
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            // If an error occurs (e.g., invalid input), return a 400 Bad Request response
            return ResponseEntity.badRequest().build();
        }
    }


    // Update Interview
    @PutMapping("/{id}")
    public Interviews updateInterview(@PathVariable Long id, @RequestBody Interviews updatedInterview) {
        return repository.findById(id).map(interview -> {
            interview.setApplication(updatedInterview.getApplication());
            interview.setInterviewType(updatedInterview.getInterviewType());
            interview.setRound(updatedInterview.getRound());
            interview.setInterviewDate(updatedInterview.getInterviewDate());
            interview.setInterviewStatus(updatedInterview.getInterviewStatus());
            interview.setNotes(updatedInterview.getNotes());
            interview.setLocation(updatedInterview.getLocation());
            interview.setInterviewerName(updatedInterview.getInterviewerName());
            interview.setDurationMinutes(updatedInterview.getDurationMinutes());
            return repository.save(interview);
        }).orElse(null);
    }

    // Delete an interview by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInterview(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Deleted Interview with id: " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Interview with id: " + id + " not found");
        }
    }
}
