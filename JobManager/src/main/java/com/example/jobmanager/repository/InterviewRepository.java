package com.example.jobmanager.repository;

import com.example.jobmanager.entity.Interviews;
import com.example.jobmanager.entity.InterviewType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interviews, Long> {

    // Find interviews by application ID
    List<Interviews> findByApplicationId(Long applicationId);

    // Find interviews by type
    List<Interviews> findByInterviewType(InterviewType interviewType);

    // Find interviews by status
    List<Interviews> findByInterviewStatus(String status);

    // Find interviews by round number
    List<Interviews> findByRound(Integer round);

    // Find interviews by application and round
    List<Interviews> findByApplicationIdAndRound(Long applicationId, Integer round);
}
