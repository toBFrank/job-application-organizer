package com.example.jobmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.jobmanager.repository")
@EnableTransactionManagement
public class DatabaseConfig {
    // Spring Boot auto-configuration will handle the rest
    // No need for manual DataSource, EntityManagerFactory, or TransactionManager beans
}
