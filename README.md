# Complaint Management System

This Spring Boot application is built with Java 21 and provides a robust and scalable backend foundation. It follows RESTful principles and includes OpenAPI-based documentation via Swagger UI.

## Requirements

- Java 21
- Maven 3.8+

##  Getting Started

To start working with this project, follow the steps below.

### Prerequisites

1. Ensure Java 21 is installed:
   ```bash
   java -version
   ```
2. (Optional) Verify Maven installation:
   ```bash
   mvn -v
   ```

---

##  Running the Application

### Using an IDE
1. Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).
2. Locate the class containing the `main` method (`ComplaintManagementSystemApplication.java`).
3. Run the application using the IDE’s tools.

Application will be accessible at:  
[http://localhost:8080](http://localhost:8080)

Swagger UI will be accessible at:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Testing

This project uses **JUnit 5** and **Mockito** for testing.

To run all tests:

```bash
./mvnw test
```

Test results will be displayed in the console.

---

## API Documentation

- [OpenAPI Spec (YAML)](./src/main/resources/inbound/webapi/complaints-openapi.yaml)