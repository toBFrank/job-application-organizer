package com.example.jobmanager.config;

import com.example.jobmanager.entity.*;
import com.example.jobmanager.repository.ResumeRepository;
import com.example.jobmanager.repository.ApplicationRepository;
import com.example.jobmanager.repository.InterviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class SampleDataLoader {

    @Bean
    CommandLineRunner initDatabase(
            ResumeRepository resumeRepository,
            ApplicationRepository applicationRepository,
            InterviewRepository interviewRepository
    ) {
        return args -> {
            if (resumeRepository.count() == 0) { // Only load if empty

                // --- Master Resume ---
                Resume master = new Resume();
                master.setMaster(true);
                master.setTitle("Software Engineer Master Resume");
                master.setStatus("Active");
                master.setFileName("resume_master.pdf");
                master.setName("Alice Smith");
                master.setEmail("alice@example.com");
                master.setPortfolioURL("https://portfolio.example.com/alice");
                master.setTechnicalSkills("Java, Spring Boot, PostgreSQL");
                master.setWorkExperience("Company A (2020-2024)");
                master.setEducation("BSc Computer Science");
                master.setProjects("Project X, Project Y");
                master = resumeRepository.save(master);

                // --- Tailored Resumes ---
                Resume tailoredBackend = new Resume();
                tailoredBackend.setMaster(false);
                tailoredBackend.setMasterResume(master);
                tailoredBackend.setTitle("Backend Engineer Tailored Resume");
                tailoredBackend.setStatus("Active");
                tailoredBackend.setFileName("resume_backend.pdf");
                tailoredBackend.setName(master.getName());
                tailoredBackend.setEmail(master.getEmail());
                tailoredBackend.setPortfolioURL(master.getPortfolioURL());
                tailoredBackend.setTechnicalSkills("Java, Spring Boot, Docker, PostgreSQL");
                tailoredBackend.setWorkExperience(master.getWorkExperience());
                tailoredBackend.setEducation(master.getEducation());
                tailoredBackend.setProjects(master.getProjects());
                tailoredBackend = resumeRepository.save(tailoredBackend);

                Resume tailoredData = new Resume();
                tailoredData.setMaster(false);
                tailoredData.setMasterResume(master);
                tailoredData.setTitle("Data Engineer Tailored Resume");
                tailoredData.setStatus("Draft");
                tailoredData.setFileName("resume_data.pdf");
                tailoredData.setName(master.getName());
                tailoredData.setEmail(master.getEmail());
                tailoredData.setPortfolioURL(master.getPortfolioURL());
                tailoredData.setTechnicalSkills("Python, SQL, Spark");
                tailoredData.setWorkExperience(master.getWorkExperience());
                tailoredData.setEducation(master.getEducation());
                tailoredData.setProjects(master.getProjects());
                tailoredData = resumeRepository.save(tailoredData);

                // --- Applications ---
                Application app1 = new Application("Backend Developer", "TechCorp");
                app1.setResume(tailoredBackend);
                app1 = applicationRepository.save(app1);

                Application app2 = new Application("Data Engineer", "DataSolutions");
                app2.setResume(tailoredData);
                app2 = applicationRepository.save(app2);

                // --- Interviews for app1 ---
                Interviews int1 = new Interviews(app1, InterviewType.TECHNICAL, 1, LocalDateTime.now().plusDays(2));
                Interviews int2 = new Interviews(app1, InterviewType.BEHAVIORAL, 2, LocalDateTime.now().plusDays(5));
                app1.addInterview(int1);
                app1.addInterview(int2);
                interviewRepository.save(int1);
                interviewRepository.save(int2);
                applicationRepository.save(app1); // save updated interviews

                // --- Interviews for app2 ---
                Interviews int3 = new Interviews(app2, InterviewType.OA, 1, LocalDateTime.now().plusDays(1));
                Interviews int4 = new Interviews(app2, InterviewType.TECHNICAL, 2, LocalDateTime.now().plusDays(3));
                app2.addInterview(int3);
                app2.addInterview(int4);
                interviewRepository.save(int3);
                interviewRepository.save(int4);
                applicationRepository.save(app2);

                System.out.println("✅ Sample data loaded: resumes, applications, and interviews.");
            }
        };
    }
}
