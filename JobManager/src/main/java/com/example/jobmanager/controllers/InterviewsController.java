package com.example.jobmanager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interviews")
public class InterviewsController {

    @Autowired
    private final InterviewsRepository repository;

    // Create new Interview
    @PostMapping
    public Interview createInterview(@RequestBody Interview interview) {
        return repository.save(interview)
    }

    // Get all interviews
    @GetMapping
    List<Interviews> all() {
        return repository.findAll();
    }

    // Get Interview by ID
    @GetMapping("/id")
    public Interviews getInterviewbyId(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    // Update Interview
    @PutMapping("/{id}")
    public Interview updateInterview(@PathVariable Long id, @RequestBody Interview updatedInterview) {
        return repository.findbyId(id).map(interview -> {
            interview.setApplication(updatedInterview.getApplication());
            interview.setInterviewDate(updatedInterview.getInterviewDate());
            interview.setInterviewStatus(updatedInterview.getInterviewStatus());
            interview.setNotes(updatedInterview.getNotes());
            interview.setLocation(updatedInterview.getLocation());
            interview.setInterviewerName(updatedInterview.getInterviewerName());
            interview.setDurationMinutes(updatedInterview.getDurationMinutes());
            return repository.save(interview);
        }).orElse(null);
    }

    // Delete a SINGLE interview by its applicationId
    @DeleteMapping("/{id}")
    public String deleteInterview(@PathVariable Long id) {
        if (repository.existsbyId()) {
            repository.deletebyId(applicationId);
            return "Deleted Interview with id: " + id;
        } else {
            return "Interview with id: " + id + " not found";
        }
    }
}
