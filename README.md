# Java Auth API (JWT)

A lightweight and efficient Authentication & Authorization API built with Spring Boot 3, Spring Security 6, and JSON Web Token (JWT). This project focuses on a stateless security architecture and Role-Based Access Control (RBAC).

## Features

* **Stateless Authentication**: Uses JWT (JSON Web Tokens) for secure, scalable session management.
* **RBAC (Role-Based Access Control)**: Restrict access to endpoints based on user roles (e.g., `ROLE_USER`, `ROLE_ADMIN`).
* **Custom JWT Claims**: Token stores essential user data such as `id`, `role`, and `createdAt` (Instant) to reduce database hits.
* **Centralized Security Configuration**: Managed through a dedicated Security Config for easy scaling and maintenance.
* **In-Memory Database**: Currently using H2 Database for rapid development and testing (no external DB setup required).

## API Endpoints

### Auth Operations
| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Public | Register a new user |
| `POST` | `/api/auth/login` | Public | Login and receive a Bearer Token |

### User Information
| Method | Endpoint             | Access | Description |
| :--- |:---------------------| :--- | :--- |
| `GET` | `/api/users/profile` | Private | Extract and view claims from your JWT |

## ⚙️ How to Run

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/yourusername/java-auth-api.git](https://github.com/yourusername/java-auth-api.git)
   cd java-auth-api
   ```
   
2. **Using Maven**
    ```bash
   ./mvnw spring-boot:run
    ```
   The application will start on http://localhost:8080.

3. **Using the play/run button in IntelliJ**