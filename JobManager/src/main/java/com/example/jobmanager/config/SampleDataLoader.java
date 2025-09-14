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
                master.setTitle("Software Development Resume - Franco Bonilla");
                master.setStatus("Active");
                master.setFileName("franco_bonilla_master.pdf");
                master.setName("Franco Bonilla");
                master.setEmail("bonilla.franco484@gmail.com");
                master.setPortfolioURL("https://linkedin.com/in/tobfrank");
                master.setTechnicalSkills("""
Languages: JavaScript, TypeScript, Python, Java, C#, HTML/CSS, SQL Server, MongoDB
Frameworks: Node.js, React.js, React Native, Angular, Cypress, Django
Developer Tools: Git, Kubernetes, Docker, Helm, GCP, Jenkins, AWS, Firebase, Linux, Jira
""");
                master.setWorkExperience("""
Software Development Engineer Intern, Formidable Designs (Aug 2023 – Aug 2024):
- Built 50+ cross-platform features for a smart lighting app using React Native TypeScript and Swift
- Integrated 30+ REST APIs for device pairing, control, and store downloads
- Engineered custom light pattern builder on Android

Software Development Engineer Intern, Formidable Designs (May 2023 – Aug 2023):
- Developed Bluetooth Mesh support for smart heater app (Android Java, nRF Mesh)
- Refactored SQL injection vulnerabilities in React/SQL Server web app
- Coded 30+ E2E Cypress tests

Computer Science Teaching Assistant, University of Alberta (Sep 2022 – Apr 2023):
- Mentored 240+ students in Python fundamentals and OOP
- Reviewed 25+ assignments weekly and improved software problem-solving
""");
                master.setEducation("""
University of Alberta, BSc in Computer Science with Specialization (GPA: 3.5/4.0)
Relevant Coursework: Software Engineering, Web Architecture, Info Retrieval, Data Structures and Algorithms
""");
                master.setProjects("""
Distributed Social Network App (Python, JavaScript, React, Django, PostgreSQL, GitHub API)
- Decentralized social network with markdown posts, images, federated nodes, and JWT auth

QR Code Scavenger Hunt App (Java, Android Studio, Firebase, Google Maps API)
- Location-based QR scavenger hunt with real-time Firebase sync and interactive maps
""");

                master = resumeRepository.save(master);

                // --- Tailored Resumes ---
                Resume tailoredBackend = new Resume();
                tailoredBackend.setMaster(false);
                tailoredBackend.setMasterResume(master);
                tailoredBackend.setTitle("Backend Engineer Resume - Franco Bonilla");
                tailoredBackend.setStatus("Active");
                tailoredBackend.setFileName("franco_bonilla_backend.pdf");
                tailoredBackend.setName(master.getName());
                tailoredBackend.setEmail(master.getEmail());
                tailoredBackend.setPortfolioURL(master.getPortfolioURL());
                tailoredBackend.setTechnicalSkills("""
Languages: Java, Python, SQL
Frameworks: Spring Boot, Django
Developer Tools: Git, Docker, Kubernetes, Jenkins
""");
                tailoredBackend.setWorkExperience("""
Software Development Engineer Intern, Formidable Designs:
- Backend API integration, database schema optimization
- RESTful service development and cloud deployment
""");
                tailoredBackend.setEducation(master.getEducation());
                tailoredBackend.setProjects(master.getProjects());

                tailoredBackend = resumeRepository.save(tailoredBackend);


                Resume tailoredFullstack = new Resume();
                tailoredFullstack.setMaster(false);
                tailoredFullstack.setMasterResume(master);
                tailoredFullstack.setTitle("Full-Stack Engineer Resume - Franco Bonilla");
                tailoredFullstack.setStatus("Draft");
                tailoredFullstack.setFileName("franco_bonilla_fullstack.pdf");
                tailoredFullstack.setName(master.getName());
                tailoredFullstack.setEmail(master.getEmail());
                tailoredFullstack.setPortfolioURL(master.getPortfolioURL());
                tailoredFullstack.setTechnicalSkills("""
Languages: JavaScript, TypeScript, Python
Frameworks: Node.js, React.js, React Native, Angular, Django
Developer Tools: Git, Docker, Firebase, AWS
""");
                tailoredFullstack.setWorkExperience("""
Software Development Engineer Intern, Formidable Designs:
- Frontend development for mobile and web
- Integrated REST APIs and managed cloud sync
""");
                tailoredFullstack.setEducation(master.getEducation());
                tailoredFullstack.setProjects(master.getProjects());

                tailoredFullstack = resumeRepository.save(tailoredFullstack);


                // --- Applications ---

                Application app1 = new Application("Backend Developer", "TechCorp", "APPLIED");
//                app1.setResume(tailoredBackend); // Backend-focused tailored resume
                app1 = applicationRepository.save(app1);

                Application app2 = new Application("Full-Stack Engineer", "InnovateX", "APPLIED");
//                app2.setResume(tailoredFullstack); // Full-stack-focused tailored resume
                app2 = applicationRepository.save(app2);

                Application app3 = new Application("Data Analyst", "DataSolutions", "APPLIED");
//                app3.setResume(tailoredBackend); // Using backend resume for data role
                app3 = applicationRepository.save(app3);

                // --- Interviews for app1 (Backend Developer) ---
                Interviews int1 = new Interviews(app1, InterviewType.TECHNICAL, 1, LocalDateTime.now().plusDays(2));
                Interviews int2 = new Interviews(app1, InterviewType.BEHAVIORAL, 2, LocalDateTime.now().plusDays(5));
                app1.addInterview(int1);
                app1.addInterview(int2);
                interviewRepository.save(int1);
                interviewRepository.save(int2);
                applicationRepository.save(app1);

                // --- Interviews for app2 (Full-Stack Engineer) ---
                Interviews int3 = new Interviews(app2, InterviewType.OA, 1, LocalDateTime.now().plusDays(1));
                Interviews int4 = new Interviews(app2, InterviewType.TECHNICAL, 2, LocalDateTime.now().plusDays(3));
                app2.addInterview(int3);
                app2.addInterview(int4);
                interviewRepository.save(int3);
                interviewRepository.save(int4);
                applicationRepository.save(app2);

                // --- Interviews for app3 (Data Analyst) ---
                Interviews int5 = new Interviews(app3, InterviewType.TECHNICAL, 1, LocalDateTime.now().plusDays(1));
                Interviews int6 = new Interviews(app3, InterviewType.BEHAVIORAL, 2, LocalDateTime.now().plusDays(4));
                app3.addInterview(int5);
                app3.addInterview(int6);
                interviewRepository.save(int5);
                interviewRepository.save(int6);
                applicationRepository.save(app3);



                System.out.println("✅ Sample data loaded: resumes, applications, and interviews.");
            }
        };
    }
}
