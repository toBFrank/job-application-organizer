package com.example.jobmanager.repository;

import com.example.jobmanager.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Find applications by company name
    List<Application> findByCompanyContainingIgnoreCase(String company);

    // Find applications by job title
    List<Application> findByTitleContainingIgnoreCase(String title);

    // Custom query to find applications with interviews
    @Query("SELECT a FROM Application a WHERE SIZE(a.interviews) > 0")
    List<Application> findApplicationsWithInterviews();
}
