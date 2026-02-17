# Java Auth API (JWT)

A lightweight and efficient Authentication & Authorization API with Java. This project focuses on a stateless security architecture and Role-Based Access Control (RBAC).

---

## API Endpoints

### Auth Operations

| Method | Endpoint             | Access | Description                      |
|:-------|:---------------------|:-------|:---------------------------------|
| `POST` | `/api/auth/register` | Public | Register a new user              |
| `POST` | `/api/auth/login`    | Public | Login and receive a Bearer Token |

### User Information

| Method | Endpoint                     | Access  | Description                                           |
|:-------|:-----------------------------|:--------|:------------------------------------------------------|
| `GET`  | `/api/users/profile`         | Private | Extract and view claims from your JWT                 |
| `POST` | `/api/users/change-password` | Private | Extract claims from your JWT and change user password |

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

## Swagger OpenAPI
`http://localhost:8080/v1/api/swagger-ui/index.html`
