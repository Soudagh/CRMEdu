[![codecov](https://codecov.io/gh/Soudagh/CRMEdu/graph/badge.svg?token=27CBSXJHDE)](https://codecov.io/gh/Soudagh/CRMEdu)
![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=postgresql&logoColor=white)
# CRMEdu

## Description
CRMEdu is a backend application for managing online school operations.  
It provides functionalities for:
- **Authentication** using **JWT**.
- Managing **organizations** within the system.
- Specify **subjects** that can be taught in a specific organization.
- Managing **users** and their roles in organization.
- Managing **tutors** in organization. Specify **subjects** and **grades** of education that they can teach.
- Managing **work shifts** for tutors to schedule their availability.

## Installation
#### 1. Clone the repository
```git clone https://github.com/Soudagh/CRMEdu.git```

#### 2. Set up configuration properties (env)
Add variables `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD` to configuration properties or env file.

#### 3. Initialize environment
```docker compose up -d```

#### 4. Start CrmeduApplication.java
```
./gradlew bootRun  # For macOS/Linux
gradlew.bat bootRun  # For Windows
``` 
Or open CrmeduApplication.java in IntelliJ IDEA and click Run.

## Documentation
Code documentation is available at [**JavaDoc**](https://soudagh.github.io/CRMEdu/javadoc/)

API documentation is available at [**Swagger UI (Read-only)**](https://soudagh.github.io/CRMEdu/swagger/index.html)

> ⚠️ The hosted Swagger UI is for viewing only and does not support API requests.
> To test API calls, use Postman, cURL, or run the server locally and access:
> http://localhost:8080/swagger-ui/index.html
