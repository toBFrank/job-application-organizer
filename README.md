# Job Application Organizer

An application that helps job seekers manage their job applications efficiently. Users can:

- Track where they have applied  
- Monitor the status of applications  
- Customize resumes for different positions  
- Keep all job-related information organized  

---

## Quick Start

To get the project running locally:

```bash
docker-compose up --build
⚠️ Note: Do not change the ports; it is not necessary.

Frontend (Next.js): http://localhost:3000

Backend (Spring Boot): http://localhost:8080
```

# Job Application Organizer API

This API provides CRUD operations and search/filtering for managing entities in the Job Application Organizer project.

---

## Entities

### 1. Application

**Base URL:** `/applications`

#### API Endpoints

| HTTP Method | URL | Request Body | URL Params | Response | Description |
|------------|-----|-------------|------------|----------|-------------|
| POST | `/applications` | `Application` JSON object | None | `Application` JSON with ID | Create a new application |
| GET | `/applications` | None | None | List of `Application` JSON | Get all applications |
| GET | `/applications/{id}` | None | `id` (Long) | `Application` JSON or null | Get an application by ID |
| PUT | `/applications/{id}` | `Application` JSON object | `id` (Long) | Updated `Application` JSON or null | Update an existing application |
| DELETE | `/applications/{id}` | None | `id` (Long) | String message | Delete an application by ID |
| GET | `/applications/search/company` | None | `company` (String, query param) | List of `Application` JSON | Search applications by company name (case-insensitive) |
| GET | `/applications/search/title` | None | `title` (String, query param) | List of `Application` JSON | Search applications by job title (case-insensitive) |
| GET | `/applications/rejected` | None | None | List of `Application` JSON | Get all rejected applications |
| GET | `/applications/job-offers` | None | None | List of `Application` JSON | Get all applications with job offers |
| GET | `/applications/with-interviews` | None | None | List of `Application` JSON | Get all applications that have interviews |

#### Entity Fields

| Field Name | Type | Description |
|------------|------|-------------|
| `id` | Long | Auto-generated primary key |
| `title` | String | Job title |
| `company` | String | Company name |
| `rejected` | Boolean | True if application was rejected |
| `jobOffer` | Boolean | True if application resulted in a job offer |
| `interviews` | List<Interviews> | List of associated interviews |

---
## Interviews

**Base URL:** `/interviews`

#### API Endpoints

| HTTP Method | URL                | Request Body            | URL Params  | Response                         | Description                  |
| ----------- | ------------------ | ----------------------- | ----------- | -------------------------------- | ---------------------------- |
| POST        | `/interviews`      | `Interview` JSON object | None        | `Interview` JSON with ID         | Create a new interview       |
| GET         | `/interviews`      | None                    | None        | List of `Interview` JSON         | Get all interviews           |
| GET         | `/interviews/{id}` | None                    | `id` (Long) | `Interview` JSON or null         | Get an interview by ID       |
| PUT         | `/interviews/{id}` | `Interview` JSON object | `id` (Long) | Updated `Interview` JSON or null | Update an existing interview |
| DELETE      | `/interviews/{id}` | None                    | `id` (Long) | String message                   | Delete an interview by ID    |

#### Entity Fields

| Field Name        | Type        | Description                                                    |
| ----------------- | ----------- | -------------------------------------------------------------- |
| `id`              | Long        | Auto-generated primary key                                     |
| `application`     | Application | Associated application entity                                  |
| `interviewDate`   | DateTime    | Date and time of the interview                                 |
| `interviewStatus` | String      | Status of the interview (e.g., scheduled, completed, canceled) |
| `notes`           | String      | Additional notes about the interview                           |
| `location`        | String      | Location of the interview                                      |
| `interviewerName` | String      | Name of the interviewer                                        |
| `durationMinutes` | Integer     | Duration of the interview in minutes                           |

---


