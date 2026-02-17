# Java Auth API (JWT)

A robust and scalable RESTful Authentication API built with Spring Boot 4, Spring Security 6, and PostgreSQL. This
project implements modern security patterns, strict data validation, and clean architecture principles.

---

## Tech Stack

Language: Java 21 (LTS)

- Framework: Spring Boot 4, Spring Security 6
- Database: PostgreSQL
- Persistence: Spring Data JPA / Hibernate
- Security: JSON Web Token (JWT)
- Documentation: SpringDoc OpenAPI / Swagger

Utilities: Lombok, Maven

---

## How to Run

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/daffarez/java-auth-api.git](https://github.com/yourusername/java-auth-api.git)
   cd java-auth-api
   ```

2. **Using Maven**
    ```bash
   ./mvnw spring-boot:run
    ```

3. **Using the play/run button in IntelliJ**

**The application will start on `http://localhost:8080`.**

---

## Run the unit test

   ```bash
   ./mvnw clean test
   ```

---

## Swagger OpenAPI

`http://localhost:8080/v1/api/swagger-ui/index.html`
